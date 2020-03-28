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
import org.neo4j.driver.internal.shaded.io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@GraphQLApi
@Slf4j
public class PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;

    @GraphQLMutation(name = "addPhone", description = "添加Phone实体结点")
    public Result addPhone(@GraphQLArgument(name = "phone", description = "phone") Phone phone) {

        try {
            phoneRepository.save(phone);
        } catch (Exception e) {
            log.error("PhoneService 发生的异常为：Phone新增失败! {} 参数为：{}", e, phone);
            throw new RuntimeException(e.getMessage(), e);
        }


        return Result.ok("添加成功");

    }


    @GraphQLQuery(name = "verifyTheOnlyPhone", description = "检验Phone是否已经存在")
    public boolean verifyTheOnlyPhone(@GraphQLArgument(name = "number", description = "number") String number) {
        if (Objects.isNull(this.phoneRepository.getPhoneByNumber(number))) {
            return false;
        }
        return true;
    }
 /*   @GraphQLQuery(name = "getPhoneByNumber", description = "通过号码查询Phone")
    public Phone getPhoneByNumber(@GraphQLArgument(name = "number", description = "number") String number) {
        return phoneRepository.getPhoneByNumber(number);
    }*/

    @GraphQLQuery(name = "loadListOfPhone", description = "通过条件查询Phone")
    public List<Phone> loadListOfPhone(@GraphQLArgument(name = "flag", description = "flag") String flag,
                                             @GraphQLArgument(name = "number", description = "number") String number) {
        return phoneRepository.loadListOfPhone(flag,number);
    }

    @GraphQLQuery(name = "selectAllPhone", description = "查看机主")
    public List<Phone> selectAllPhone() {
        return phoneRepository.selectAllPhone();
    }

    @GraphQLQuery(name = "selectPhoneOwner", description = "查询所有Phone")
    public Person selectPhoneOwner(@GraphQLArgument(name = "number", description = "number") String number) {
        return phoneRepository.selectPhoneOwner(number);
    }

    @GraphQLMutation(name = "updatePhoneByNumber", description = "根据Phone的number修改其黑白名单状态")
    public Result updatePhoneByNumber(@GraphQLArgument(name = "number", description = "number") String number,
                                      @GraphQLArgument(name = "flag", description = "flag") String flag) {
        phoneRepository.updatePhoneByNumber(number, flag);
        return Result.ok("修改号码状态成功！");
    }

    @GraphQLMutation(name = "deletePhoneByNumber", description = "根据Phone的number删除Phone")
    public Result deletePhoneByNumber(@GraphQLArgument(name = "number", description = "number") String number) {
        if(!this.verifyTheOnlyPhone(number)){
            return Result.error("此号码不存在");
        }else if(StringUtil.isNullOrEmpty(number)){
            return Result.error("请选择要删除的号码");
        }else{
            phoneRepository.deletePhoneByNumber(number);
            return Result.ok("删除成功！");
        }

    }
    @GraphQLQuery(name="countOfPhone",description = "首页显示-Phone数量")
    public int countOfPhone(){
        return phoneRepository.countOfPhone();
    }

}


