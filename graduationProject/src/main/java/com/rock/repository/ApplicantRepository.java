package com.rock.repository;

import com.rock.nodeEntity.Applicant;
import com.rock.nodeEntity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantRepository extends Neo4jRepository<Applicant, Long> {
    /*
     * 查询所有Applicant的id
     */
    @Query("match(n:Applicant) return n.id")
    List<String> getAllApplicantId();
    /*
     * 创建APPLY关系
     */
    @Query("MATCH (n:Person) ,(a:Applicant)\n" +
            "WHERE n.id =  a.applicant\n" +
            "merge (n)-[r:APPLY]->(a)")
    void createApply();

    /*
    * 创建COLLEAGUE_PHONE关系
    * */
    @Query("MATCH (n:Applicant) ,(p:Phone)\n" +
            "WHERE n.colleague_phone =  p.number\n" +
            "merge (n)-[r:COLLEAGUE_PHONE]->(p)")
    void createColleaguePhone();

    /*
     * 创建PARENT_PHONE关系
     * */
    @Query("MATCH (n:Applicant) ,(p:Phone)\n" +
            "WHERE n.parent_phone =  p.number\n" +
            "merge (n)-[r:PARENT_PHONE]->(p)")
    void createParentPhone();

    /*
     * 创建COMPANY_PHONE关系
     * */
    @Query("MATCH (n:Applicant) ,(p:Phone)\n" +
            "WHERE n.company_phone =  p.number\n" +
            "merge (n)-[r:COMPANY_PHONE]->(p)")
    void createCompanyPhone();

    /*
    * 创建COLLEAGUE_OF关系1
    * */
    @Query("MATCH (n:Applicant) ,(p:Person)\n" +
            "WHERE n.colleague_phone =  p.number\n" +
            "merge (n)<-[r:COLLEAGUE_OF]->(p)")
    void createColleagueOf1();

    /*
     * 创建COLLEAGUE_OF关系2
     * */
    @Query("MATCH (n:Applicant) ,(n1:Applicant)\n" +
            "WHERE n.colleague_phone = n1.colleague_phone AND n.id<>n1.id\n" +
            "merge (n)<-[r:COLLEAGUE_OF]->(n1)")
    void createColleagueOf2();

    /*创建PARENT_OF关系
    * */
    @Query("MATCH (n:Applicant) ,(p:Person)\n" +
            "WHERE n.parent_phone =  p.number\n" +
            "merge (n)<-[r:PARENT_OF]-(p)")
    void createParentOf();

    /**
     *  根据Applicant的id删除Applicant
     **/
    @Query("MATCH (n:Applicant)-[r]-() WHERE n.id={id} delete r,n")
    void deleteApplicantById(@Param("id") String id);

    /**
     *  根据Applicant的id查询Applicant
     **/
    @Query("MATCH (n:Applicant) WHERE n.id={id} RETURN n")
    Applicant selectApplicantById(@Param("id") String id);

    /**
     *  查询所有Applicant
     **/
    @Query(value = "MATCH (n:Applicant) RETURN n ORDER BY n.id",
    countQuery = "MATCH (n:Applicant) RETURN count(n)")
    Page<Applicant> selectAllApplicant(@Param("pageable") Pageable pageable);
    /**
     *  按条件查询Applicant
     **/
    @Query(value = "MATCH (n:Applicant) where n.id={id} or n.amount={amount} or n.term={term} or n.job={job} or n.city={city} or n.parent_phone={parent_phone}" +
            "or n.colleague_phone={colleague_phone} or n.company_phone={company_phone} or n.status={status}  RETURN n",
    countQuery = "MATCH (n:Applicant) where n.id={id} or n.amount={amount} or n.term={term} or n.job={job} or n.city={city} or n.parent_phone={parent_phone}\" +\n" +
            "            \"or n.colleague_phone={colleague_phone} or n.company_phone={company_phone} or n.status={status}  RETURN count(n)")
    Page<Applicant> selectApplicant(@Param("id") String id,@Param("amount")String amount,@Param("term")String term,
                                    @Param("job")String job,@Param("city")String city,@Param("parent_phone")String parent_phone,
                                    @Param("colleague_phone")String colleague_phone,@Param("company_phone")String company_phone,
                                    @Param("status")String status,@Param("pageable") Pageable pageable);

    /**
     *  修改Applicant
     **/
    @Query("MATCH (n:Applicant)-[r1]-(p:Phone) where n.id={id} with r1,n,p match(p:Phone)-[r2]-()  " +
            "with  r1,r2,n,p delete r1,r2,p return n")
    void updateApplicant1(@Param("id")String id);
    @Query("MATCH (n:Applicant) where n.id={id} set n.amount={amount},n.term={term},n.job={job},n.city={city},n.parent_phone={parent_phone}," +
            "n.colleague_phone={colleague_phone},n.company_phone={company_phone},n.status={status} ")
    void updateApplicant2(@Param("id") String id,@Param("amount")String amount,@Param("term")String term,
                          @Param("job")String job,@Param("city")String city,@Param("parent_phone")String parent_phone,
                          @Param("colleague_phone")String colleague_phone,@Param("company_phone")String company_phone,
                          @Param("status")String status);

    /**
     *  查看Applicant的客户信息
     **/
    @Query("MATCH (n:Person)-[:APPLY]-(a:Applicant) where a.id={id} return n")
    List<Person> selectPersonByApplicant(@Param("id") String id);

    /**
     *  首页显示-Applicant数量
     **/
    @Query("MATCH (n:Applicant) return count(n)")
    int countOfApplicant();

    /**
     *  查询所有未检测Applicant
     **/
//    @Query("MATCH (n:Applicant) where n.status ="" or n.status=~"(?i)NULL" RETURN n")
//    List<Applicant> allNullStatus1();
    @Query(value = "MATCH (n:Applicant) where n.status IS NULL RETURN n ORDER BY n.id",
    countQuery = "MATCH (n:Applicant) where n.status IS NULL RETURN count(n)")
    Page<Applicant> allNullStatus( @Param("pageable") Pageable pageable);
    /**
     *  按条件查询Applicant
     **/
    @Query(value = "MATCH (n:Applicant) where n.status IS NULL and (n.id={id} or n.amount={amount} or n.term={term} or n.job={job} or n.city={city} or n.applicant={applicant} RETURN n ORDER BY n.id",
    countQuery = "MATCH (n:Applicant) where n.status IS NULL and (n.id={id} or n.amount={amount} or n.term={term} or n.job={job} or n.city={city} or n.applicant={applicant} RETURN count(n)")
    Page<Applicant> selectNullStatus(@Param("id") String id,@Param("amount")String amount,@Param("term")String term,
                                    @Param("job")String job,@Param("city")String city,@Param("applicant")String applicant,
                                     @Param("pageable") Pageable pageable);

    /**
     *  按状态查询-查询通过信息检测的Applicant
     **/
    @Query(value = "MATCH (n:Applicant) where n.status={status} RETURN n",
    countQuery = "MATCH (n:Applicant) where n.status={status} RETURN count(n)")
    Page<Applicant> selectThroughInfoTest(@Param("status")String status,@Param("pageable") Pageable pageable);
    /**
     *  将Applicant状态改为通过信息检测/风险预测
     **/
    @Query("MATCH (n:Applicant) where n.id={id} set n.status={status} RETURN n")
    void updateApplyRiskStatus(@Param("id") String id,@Param("status")String status);

}
