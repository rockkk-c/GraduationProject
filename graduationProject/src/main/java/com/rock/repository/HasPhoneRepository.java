package com.rock.repository;

import com.rock.relationEntity.HasPhone;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HasPhoneRepository extends Neo4jRepository<HasPhone, Long> { // 关系Repository
}
