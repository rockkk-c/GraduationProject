
package com.rock.mapper;

import com.rock.entity.Rule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleMapper {
/*
     * 根据rid获取rule
     *
     */


    Rule getRuleById(@Param("rid") int rid);
}

