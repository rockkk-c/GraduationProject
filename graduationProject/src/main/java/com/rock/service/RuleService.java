package com.rock.service;

import com.rock.entity.Rule;
import com.rock.mapper.RuleMapper;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@GraphQLApi
@Service
public class RuleService {

    @Autowired
    private RuleMapper ruleMapper;


    @GraphQLQuery(name = "getRuleById", description = "根据rid查询Rule")
    public Rule getRuleById(@GraphQLArgument(name = "rid") int rid){
        return ruleMapper.getRuleById(rid);
    }

    @GraphQLQuery(name = "getRuleResult", description = "获取规则引擎中规则执行的结果")
    public int getRuleResult(@GraphQLArgument(name = "rid", description = "规则id") int rid,
                             @GraphQLArgument(name = "applyId", description = "进件申请人id")  String applyId){
        String ruleCypher=ruleMapper.getRuleById(rid).getCypher();

        return 1;
    }

}
