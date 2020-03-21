package com.rock.service;

import com.rock.nodeEntity.Test;
import com.rock.repository.TestRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@GraphQLApi
@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    @GraphQLMutation(name = "addTest", description = "执行rule.cypher得到其结果")
    public String  addTest(@GraphQLArgument(name = "test", description = "规则id") Test test){
        testRepository.save(test);
        return "添加Test成功";

    }
}
