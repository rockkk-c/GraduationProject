package com.rock.repository;

import org.springframework.stereotype.Repository;
import com.rock.nodeEntity.Test;
import org.springframework.data.neo4j.repository.Neo4jRepository;
@Repository
public interface TestRepository extends Neo4jRepository<Test,Long> {
}
