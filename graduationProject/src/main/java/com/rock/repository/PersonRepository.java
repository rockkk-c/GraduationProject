package com.rock.repository;

import com.rock.nodeEntity.Applicant;
import com.rock.nodeEntity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository  extends CrudRepository<Person,Long> {
    /*
    * 进件的申请人之前的逾期数
    */
    @Query("match (a1:Applicant)<-[]-(:Person)-[]->(a:Applicant) where a1.id={applyId} and a.status=\"OVERDUE\" return count(a)")
    int getoverdueCountByApplyId(@Param("applyId") String applyId);
   //进件的申请人之前的逾期数-详情
    @Query("match (a1:Applicant)<-[]-(:Person)-[]->(a:Applicant) where a1.id={applyId} and a.status=\"OVERDUE\" return a")
    List<Applicant> overdueDetails(@Param("applyId") String applyId);

    /*
     * 进件的申请人为黑名单状态
     */
    @Query("match (a1:Applicant)<-[]-(p:Person) where a1.id={applyId} and p.flag=\"BLACK\" return count(p)")
    int getBFlagCountByApplyId(@Param("applyId") String applyId);


    /*
     * 进件的申请人手机号处于黑名单状态
     */
    @Query("match (a:Applicant)<-[]-(:Person)-[:HAS_PHONE]-(p:phone) where a.id={applyId} and p.flag=\"BLACK\" return count(p)")
    int getPhoneBFlagCountByApplyId(@Param("applyId") String applyId);


    /*
     * 一维关系中触碰黑名单的人的个数
     */
    @Query("match (a:Applicant)<-[]-(:Person)-[]->(p:Person) where a.id={applyId} and p.flag=\"BLACK\" return count(p)")
    int getOneDimenRelationshipBFCountByApplyId(@Param("applyId") String applyId);
    //一维关系中触碰黑名单的人-详情
    @Query("match (a:Applicant)<-[]-(:Person)-[]->(p:Person) where a.id={applyId} and p.flag=\"BLACK\" return p")
    List<Person> OneDimenRelationshipBFDetails(@Param("applyId") String applyId);

    /*
     * 一维关系中触碰黑名单的电话的个数
     */
    @Query("match (a:Applicant)<-[]-(:Person)-[]->(:Person)-[]->(p:Phone) where a.id={applyId} and p.flag=\"BLACK\" return count(p)")
    int getOneDimenRelationshipPhoneBFCountByApplyId(@Param("applyId") String applyId);
    //一维关系中触碰黑名单的电话-详情
    @Query("match (a:Applicant)<-[]-(:Person)-[]->(n:Person)-[]->(p:Phone) where a.id={applyId} and p.flag=\"BLACK\" return n")
    List<Person> OneDimenRelationshipPhoneBFDetails(@Param("applyId") String applyId);
    /*
     * 二维关系中触碰黑名单的人的个数
     */
    @Query("match (a:Applicant)<-[]-(:Person)-[]->(:Person)-[]->(p:Person) where a.id={applyId} and p.flag=\"BLACK\" return count(p)")
    int getTwoDimenRelationshipBFCountByApplyId(@Param("applyId") String applyId);
    //二维关系中触碰黑名单的人-详情
    @Query("match (a:Applicant)<-[]-(:Person)-[]->(:Person)-[]->(p:Person) where a.id={applyId} and p.flag=\"BLACK\" return p")
    List<Person> TwoDimenRelationshipBFDetails(@Param("applyId") String applyId);

    /*
     * 二维关系中触碰黑名单的电话的个数
     */
    @Query("match (a:Applicant)<-[]-(:Person)-[]->(:Person)-[]->(:Person)-[]->(p:Phone) where a.id={applyId} and p.flag=\"BLACK\" return count(p)")
    int getTwoDimenRelationshipPhoneBFCountByApplyId(@Param("applyId") String applyId);
    //二维关系中触碰黑名单的电话-详情
    @Query("match (a:Applicant)<-[]-(:Person)-[]->(:Person)-[]->(n:Person)-[]->(p:Phone) where a.id={applyId} and p.flag=\"BLACK\" return n")
    List<Person> TwoDimenRelationshipPhoneBFDetals(@Param("applyId") String applyId);
    /*
     * 不同申请人有相同的电话返回count
     */
    @Query("match (a1:Applicant)<-[]-(p1:Person)-[:HAS_PHONE]->(:Phone)<-[:HAS_PHONE]-(p:Person)-[]->(a:Applicant) where  a1.id={id} return count(p)")
    int fakeInfoCheckCount(@Param("id") String id);

    /*
     * 不同申请人有相同的电话返回List<Person>,环检测
     */
    @Query("match (a1:Applicant)<-[]-(p1:Person)-[:HAS_PHONE]->(:Phone)<-[:HAS_PHONE]-(p:Person)-[]->(a:Applicant) where a1.id={id} return p,p1")
    Page<Person> fakeInfoCheck(@Param("id") String id,@Param("pageable") Pageable pageable);
    @Query("match (p1:Person)-[]->(a1:Applicant)-[:COMPANY_PHONE]->(:Phone)<-[:COMPANY_PHONE]-(a:Applicant)<-[]-(p:Person) where a1.id={id} return p,p1")
    Page<Person> fakeInfoCheck2(@Param("id") String id,@Param("pageable") Pageable pageable);
    /*
     * 根据Applicant.id查询person
     */

    @Query("match(n:Person) where n.id={applyId} return n")
    Person getPersonById(@Param("applyId") String applyId);

    /*
     * 根据Applicant.id查询status
     */
    @Query("match(n:Applicant) where n.id={applyId} return n.status")
    String selectStatusById(@Param("applyId") String applyId);

    /*创建HAS_PHONE关系
    * */
    @Query("MATCH (n:Person) ,(p:Phone)\n" +
            "WHERE n.number =  p.number\n" +
            "merge (n)-[r:HAS_PHONE]->(p)")
    void createHasPhone();
    /**
     * 删除Person
     */
   @Query("MATCH (n:Person)-[r]-() where n.id={id} delete r,n")
    void deletePersonById(@Param("id") String id);

    /**
     * 修改Person
     */
    @Query("MATCH (n:Person)-[r1:HAS_PHONE]-(p:Phone) where n.id={id} with r1,n,p match(p:Phone)-[r2]-()  " +
            "with  r1,r2,n,p delete r1,r2,p return n")
    void updatePersonById1(@Param("id")String id);
    @Query("MATCH (n:Person) where n.id={id}  set n.name={name},n.sex={sex},n.flag={flag},n.number={number} ")
    void updatePersonById2(@Param("id") String id,@Param("name") String name,@Param("sex") String sex,
                           @Param("flag") String flag,@Param("number") String number);
    /**
     * 查询所有Person
     */
    @Query(value = "match (n:Person) return n",
    countQuery = "match (n:Person) return count(n)")
    Page<Person> selectAllPerson(@Param("pageable")Pageable pageable);

    /**
     * 按条件查询Person
     */
    @Query(value = "match (n:Person) WHERE n.id={id} or  n.name={name} or n.sex={sex} or n.number={number} or n.flag={flag} return n",
    countQuery = "match (n:Person) WHERE n.id={id} or  n.name={name} or n.sex={sex} or n.number={number} or n.flag={flag} return count(n)")
    Page<Person> selectPerson(@Param("id") String id,@Param("name") String name,@Param("sex") String sex,
                               @Param("number") String number,@Param("flag") String flag,@Param("pageable")Pageable pageable);

    /**
     * 根据Person的id查询其进件
     */
    @Query( "MATCH (n:Person)-[:APPLY]-(a:Applicant) where n.id={id} return a")
    List<Applicant> selecApplicantByPId(@Param("id") String id);

    /**
     *  首页显示-Person数量
     **/
    @Query("MATCH (n:Person) return count(n)")
    int countOfPerson();

}

