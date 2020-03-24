package com.rock.repository;

import com.rock.nodeEntity.Person;
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

    /*
     * 一维关系中触碰黑名单的电话的个数
     */
    @Query("match (a:Applicant)<-[]-(:Person)-[]->(:Person)-[]->(p:Phone) where a.id={applyId} and p.flag=\"BLACK\" return count(p)")
    int getOneDimenRelationshipPhoneBFCountByApplyId(@Param("applyId") String applyId);

    /*
     * 二维关系中触碰黑名单的人的个数
     */
    @Query("match (a:Applicant)<-[]-(:Person)-[]->(:Person)-[]->(p:Person) where a.id={applyId} and p.flag=\"BLACK\" return count(p)")
    int getTwoDimenRelationshipBFCountByApplyId(@Param("applyId") String applyId);

    /*
     * 二维关系中触碰黑名单的电话的个数
     */
    @Query("match (a:Applicant)<-[]-(:Person)-[]->(:Person)-[]->(:Person)-[]->(p:Phone) where a.id={applyId} and p.flag=\"BLACK\" return count(p)")
    int getTwoDimenRelationshipPhoneBFCountByApplyId(@Param("applyId") String applyId);

    /*
     * 不同申请人有相同的电话返回count
     */
    @Query("match (a1:Applicant)<-[]-(p1:Person)-[:HAS_PHONE]->(:Phone)<-[:HAS_PHONE]-(p:Person)-[]->(a:Applicant) where p1.number=p.number and a1.id={applyId} return count(p)+count(p1)")
    int fakeInfoCheckCount(@Param("applyId") String applyId);

    /*
     * 不同申请人有相同的电话返回List<Person>
     */
    @Query("match (a1:Applicant)<-[]-(p1:Person)-[:HAS_PHONE]->(:Phone)<-[:HAS_PHONE]-(p:Person)-[]->(a:Applicant) where p1.number=p.number and a1.id={applyId} return p,p1")
    List<Person> fakeInfoCheck(@Param("applyId") String applyId);

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



}
