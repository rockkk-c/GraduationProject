package com.rock.repository;

import com.rock.nodeEntity.Applicant;
import com.rock.nodeEntity.Person;
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
            "WHERE n.colleague_phone = n1.colleague_phone\n" +
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
    @Query("MATCH (n:Applicant)-[r]-() WHERE n.id={applyId} delete r,n")
    void deleteApplicantById(@Param("applyId") String applyId);

    /**
     *  根据Applicant的id查询Applicant
     **/
    @Query("MATCH (n:Applicant) WHERE n.id={applyId} RETURN n")
    Applicant selectApplicantById(@Param("applyId") String applyId);

}
