package com.rock.service;


import com.rock.entity.Result;
import com.rock.mapper.FeatureDataMapper;
import com.rock.nodeEntity.Person;
import com.rock.nodeEntity.Phone;
import com.rock.repository.HasPhoneRepository;
import com.rock.repository.PersonRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@GraphQLApi
@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private HasPhoneRepository hasPhoneRepository;
    @Autowired
    private FeatureDataMapper featureDataMapper;
    @Autowired
    private PhoneService phoneService;
    @GraphQLMutation(name = "addPerson", description = "添加Person实体结点,同时添加Phone结点，并创建实体结点间关系")
    public Result addPerson(@GraphQLArgument(name = "person", description = "规则id")Person person){

       try{
           //唯一性检验，person不存在时添加
           if(!verifyTheOnlyPerson(person.getId())){
               personRepository.save(person);
           }

           //检验此人的电话是否已存在在Phone结点中
           if(phoneService.verifyTheOnlyPhone(person.getNumber())){
               //如果存在，按照号码新主人的黑名单状态更新号码的状态
               phoneService.updatePhoneByNumber(person.getNumber(),person.getFlag());
           }
           //若不存在，则将此人的电话存入到phone结点
           phoneService.addPhone(new Phone(person.getNumber(),person.getFlag()));
           personRepository.createHasPhone();

       }catch(Exception e){
           log.error("PersonService 发生的异常为：Person新增失败! {} 参数为：{}", e, person);
           throw new RuntimeException(e.getMessage(), e);
       }
        return Result.ok("添加Person成功");

    }
    @GraphQLQuery(name = "getoverdueCountByApplyId", description = "进件的申请人之前的逾期数")
    public int getoverdueCountByApplyId(@GraphQLArgument(name = "applyId", description = "applyId")String applyId){
        return personRepository.getoverdueCountByApplyId(applyId);
    }
    @GraphQLQuery(name = "getBFlagCountByApplyId", description = "进件的申请人为黑名单状态")
    public int getBFlagCountByApplyId(@GraphQLArgument(name = "applyId", description = "applyId")String applyId){
        return personRepository.getBFlagCountByApplyId(applyId);
    }
    @GraphQLQuery(name = "getPhoneBFlagCountByApplyId", description = "进件的申请人手机号处于黑名单状态")
    public int getPhoneBFlagCountByApplyId(@GraphQLArgument(name = "applyId", description = "applyId")String applyId){
        return personRepository.getPhoneBFlagCountByApplyId(applyId);
    }
    @GraphQLQuery(name = "getOneDimenRelationshipBFCountByApplyId", description = "一维关系中触碰黑名单的人的个数")
    public int getOneDimenRelationshipBFCountByApplyId(@GraphQLArgument(name = "applyId", description = "applyId")String applyId){
        return personRepository.getOneDimenRelationshipBFCountByApplyId(applyId);
    }
    @GraphQLQuery(name = "getOneDimenRelationshipPhoneBFCountByApplyId", description = "一维关系中触碰黑名单的电话的个数")
    public int getOneDimenRelationshipPhoneBFCountByApplyId(@GraphQLArgument(name = "applyId", description = "applyId")String applyId){
        return personRepository.getOneDimenRelationshipPhoneBFCountByApplyId(applyId);
    }
    @GraphQLQuery(name = "getTwoDimenRelationshipBFCountByApplyId", description = "二维关系中触碰黑名单的人的个数")
    public int getTwoDimenRelationshipBFCountByApplyId(@GraphQLArgument(name = "applyId", description = "applyId")String applyId){
        return personRepository.getTwoDimenRelationshipBFCountByApplyId(applyId);
    }
    @GraphQLQuery(name = "getTwoDimenRelationshipPhoneBFCountByApplyId", description = "二维关系中触碰黑名单的电话的个数")
    public int getTwoDimenRelationshipPhoneBFCountByApplyId(@GraphQLArgument(name = "applyId", description = "applyId")String applyId){
        return personRepository.getTwoDimenRelationshipPhoneBFCountByApplyId(applyId);
    }
    @GraphQLQuery(name = "fakeInfoCheck", description = "不同申请人有相同的电话")
    public List<Person> fakeInfoCheck(@GraphQLArgument(name = "applyId", description = "applyId")String applyId){

        //return CollectionUtils.arrayToList( personRepository.fakeInfoCheck(applyId) );
        return personRepository.fakeInfoCheck(applyId);
    }

    @GraphQLQuery(name = "getPersonById", description = "根据id查询Person")
    public Person getPersonById(@GraphQLArgument(name = "applyId", description = "applyId")String applyId){
        //return CollectionUtils.arrayToList( personRepository.fakeInfoCheck(applyId) );
        return personRepository.getPersonById(applyId);
    }
    /**
     * 检验Person是否已经存在
     *
     * @return true为存在
     */
    @GraphQLQuery(name = "verifyTheOnlyPerson", description = "检验Person是否已经存在")
    public boolean verifyTheOnlyPerson(@GraphQLArgument(name = "id", description = "id") String id) {
        if (Objects.isNull(this.getPersonById(id))) {
            return false;
        }
        return true;
    }


}
