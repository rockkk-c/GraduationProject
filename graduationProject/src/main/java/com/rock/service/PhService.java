package com.rock.service;

import com.rock.entity.Result;
import com.rock.nodeEntity.Person;
import com.rock.nodeEntity.Phone;
import com.rock.repository.PhoneRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@GraphQLApi
@Slf4j
public class PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;

    @GraphQLMutation(name = "addPhone", description = "添加Phone实体结点")
    public Result addPhone(@GraphQLArgument(name = "person", description = "规则id") Phone phone){

        try{
            phoneRepository.save(phone);
        }catch(Exception e){
            log.error("PhoneService 发生的异常为：Phone新增失败! {} 参数为：{}", e, phone);
            throw new RuntimeException(e.getMessage(), e);
        }



        return Result.ok("号码："+phone.getNumber()+"添加Phone成功");

    }

    /**
     * 检验Phone是否已经存在
     *
     * @return true为存在
     */
    @GraphQLQuery(name = "verifyTheOnlyPhone", description = "检验Phone是否已经存在")
    public boolean verifyTheOnlyPhone(@GraphQLArgument(name = "number", description = "applyId") String number) {
        if (Objects.isNull(this.phoneRepository.getPhoneByNumber(number))) {
            return false;
        }
        return true;
    }

    @GraphQLMutation(name = "updatePhoneByNumber", description = "根据Phone的number修改其黑白名单状态")
    public Result  updatePhoneByNumber(@GraphQLArgument(name = "number", description = "number") String number,
                                       @GraphQLArgument(name = "flag", description = "flag") String flag) {
        phoneRepository.updatePhoneByNumber(number,flag);
        return Result.ok("号码:"+number+",修改号码状态成功！");
    }

    @GraphQLMutation(name = "deletePhoneByNumber", description = "根据Phone的number删除Phone")
    public Result  deletePhoneByNumber(@GraphQLArgument(name = "number", description = "number") String number){
        phoneRepository.deletePhoneByNumber(number);
        return Result.ok("号码:"+number+",删除号码成功！");
    }
}
