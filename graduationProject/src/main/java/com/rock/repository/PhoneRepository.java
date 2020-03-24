package com.rock.repository;

import com.rock.nodeEntity.Phone;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends CrudRepository<Phone,Long> {
    /**
     * 根据number查询Phone
     */
    @Query("match (n:Phone) where n.number={number} return n")
    Phone getPhoneByNumber(@Param("number") String number);

    /**
     * 根据number查询Phone
     */
    @Query("match (n:Phone) where n.flag={flag} return n")
    List<Phone> loadListOfPhoneByFlag(@Param("flag") String flag);

    /**
     * 根据number修改Phone的黑白名单状态
     */
    @Query("match (n:Phone) where n.number={number} SET n.flag={flag} return n")
    Phone updatePhoneByNumber(@Param("number") String number,@Param("flag") String flag);

    /**
     * 根据number删除Phone
     */
    @Query("match ()-[r1]-(n:Phone)-[r]-() where n.number={number} delete r1,r,n")
    void deletePhoneByNumber(@Param("number") String number);

    /**
     * 添加phone
     */
    @Query("MERGE(n:Phone {number:{number},flag :{flag}}) ")
    void addPhone(@Param("number") String number,@Param("flag") String flag);

}
