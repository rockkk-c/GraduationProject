package com.rock.repository;

import com.rock.nodeEntity.Test;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TestRepository extends Neo4jRepository<Test,Long> {
}
