package com.rock.service;

import com.rock.entity.FeatureData;
import com.rock.mapper.FeatureDataMapper;
import com.rock.nodeEntity.Applicant;
import com.rock.nodeEntity.Person;
import com.rock.nodeEntity.Phone;
import com.rock.python.PyInterface;
import com.rock.python.PyResult;
import com.rock.relationEntity.Apply;
import com.rock.repository.ApplicantRepository;
import com.rock.repository.PersonRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@GraphQLApi
@Slf4j
public class ApplicantService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private FeatureDataMapper featureDataMapper;
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private  PersonService personService;

    @GraphQLQuery(name = "BFCheck", description = "获取特征数值,用来训练模型")
    public FeatureData BFCheck(@GraphQLArgument(name = "applyId", description = "applyId") String applyId) throws Exception {
        FeatureData featureData = new FeatureData();
        featureData.setFid(applyId);
        featureData.setOverdueCount(personRepository.getoverdueCountByApplyId(applyId));
        featureData.setBFlagCount(personRepository.getBFlagCountByApplyId(applyId));
        featureData.setPhoneBFCount(personRepository.getPhoneBFlagCountByApplyId(applyId));
        featureData.setOneDimenRelationshipBFCount(personRepository.getOneDimenRelationshipBFCountByApplyId(applyId));
        featureData.setOneDimenRelationshipPhoneBFCount(personRepository.getoverdueCountByApplyId(applyId));
        featureData.setTwoDimenRelationshipBFCount(personRepository.getTwoDimenRelationshipBFCountByApplyId(applyId));
        featureData.setTwoDimenRelationshipPhoneBFCount(personRepository.getTwoDimenRelationshipPhoneBFCountByApplyId(applyId));
        String status = personRepository.selectStatusById(applyId);
        if ("OVERDUE".equals(status)) {
            featureData.setLabel(0);
        } else {
            featureData.setLabel(1);
        }
        //唯一性检验，检验featureData是否已经存在
        if (this.verifyTheOnlyFeatureData(applyId)) {
            return featureDataMapper.selectById(applyId);
        }
        try {
            featureDataMapper.insertFeatureData(featureData);
        } catch (Exception e) {
            log.error("ApplicantService 发生的异常为：featureData新增失败! {} 参数为：{}", e, featureData);
            throw new Exception("featureData新增失败!" + e.getMessage());
        }
        return featureData;
    }


    /**
     * 检验featureData是否已经存在
     *
     * @return
     */
    @GraphQLQuery(name = "verifyTheOnlyFeatureData", description = "检验featureData是否已经存在")
    public boolean verifyTheOnlyFeatureData(@GraphQLArgument(name = "applyId", description = "applyId") String applyId) {
        if (Objects.isNull(this.featureDataMapper.selectById(applyId))) {
            return false;
        }
        return true;
    }


    /**
     * 对数据集进行操作，将特征值保存到数据库得到其特征数值表
     *
     * @return
     */
    @GraphQLMutation(name = "forFeatureDataTable", description = "对数据集进行操作，得到其特征数值表")
    public String forFeatureDataTable() throws Exception {
        List<String> ids = applicantRepository.getAllApplicantId();
        for(String  id  :  ids)  {
            FeatureData f=this.BFCheck(id);
        }
        return "得到特征数值表成功";
    }

    @GraphQLQuery(name = "BFPredict", description = "仅获取7个特征值，用来预测")
    public List<Integer> BFPredict(@GraphQLArgument(name = "applyId", description = "Applicant的id:applyId") String applyId) throws Exception {
        int[] features=new int[7];
        List<Integer> list=new ArrayList<>();
        list.add(personRepository.getoverdueCountByApplyId(applyId));
        list.add(personRepository.getBFlagCountByApplyId(applyId));
        list.add(personRepository.getPhoneBFlagCountByApplyId(applyId));
        list.add(personRepository.getOneDimenRelationshipBFCountByApplyId(applyId));
        list.add(personRepository.getoverdueCountByApplyId(applyId));
        list.add(personRepository.getTwoDimenRelationshipBFCountByApplyId(applyId));
        list.add(personRepository.getTwoDimenRelationshipPhoneBFCountByApplyId(applyId));
        return list;
    }
    @GraphQLQuery(name = "invokePython", description = "调用python的http接口，返回预测结果")
    public PyResult invokePython(@GraphQLArgument(name = "applyId", description = "Applicant的id:applyId") String applyId) throws Exception {
        List<Integer> list=BFPredict(applyId);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:8887/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PyInterface pyInterface = retrofit.create(PyInterface.class);
        HashMap<String, List<Integer>> hashMap = new HashMap<>();
        hashMap.put("features", list);
        System.out.println("hashMap"+hashMap);
        Call<PyResult> test = pyInterface.test(hashMap);
        Response<PyResult> execute = test.execute();
        System.out.println("execute.body:"+execute.body());
        System.out.println("execute:"+execute);
        return execute.body();
    }

    /*此接口仅添加已经存在于Person实体结点中的进件
    *
    * */

    @GraphQLMutation(name = "addApplicant", description = "添加addApplicant实体结点,同时添加Person结点，并创建关系Person-[r:HAS_PHONE]->Phone")
    public String  addApplicant(@GraphQLArgument(name = "applicant", description = "进件")Applicant applicant){

        try{
            applicantRepository.save(applicant);

            /*检验此人所填写的的所有联系人电话是否已存在在Phone结点中
             */

            if(!phoneService.verifyTheOnlyPhone(applicant.getColleague_phone())){
                //如果不存在，则将其添加到Phone
                phoneService.addPhone(new Phone(applicant.getColleague_phone(),"WHITE"));
            }
            if(!phoneService.verifyTheOnlyPhone(applicant.getCompany_phone())){
                //如果不存在，则将其添加到Phone
                phoneService.addPhone(new Phone(applicant.getCompany_phone(),"WHITE"));
            }
            if(!phoneService.verifyTheOnlyPhone(applicant.getParent_phone())){
                //如果不存在，则将其添加到Phone
                phoneService.addPhone(new Phone(applicant.getParent_phone(),"WHITE"));
            }
           //还缺一个指向Person的关系！！！！！！！！！！！！！！！！
            applicantRepository.createApply();
            applicantRepository.createColleaguePhone();
            applicantRepository.createCompanyPhone();
            applicantRepository.createParentPhone();
            applicantRepository.createColleagueOf1();
            applicantRepository.createColleagueOf2();
            applicantRepository.createParentOf();

            //找出此进件对应的人Person
          //  Person appPerson=personService.getPersonById(applicant.getApplicant());
          //  Apply apply=Apply.builder().person(appPerson).applicant(applicant).build();

        }catch(Exception e){
            log.error("ApplicantService 发生的异常为：Person新增失败! {} 参数为：{}", e, applicant);
            throw new RuntimeException(e.getMessage(), e);
        }
        return "添加进件成功";

    }

    /*
     * 若此进件的申请人还不存在于Person结点，同时添加Person和Applicant
     * */

    @GraphQLMutation(name = "addApplicant", description = "添加addApplicant实体结点,同时添加Person结点，并创建关系Person-[r:HAS_PHONE]->Phone")
    public String  addApplicantAndPerson(@GraphQLArgument(name = "applicant", description = "进件")Applicant applicant,
                                         @GraphQLArgument(name = "person", description = "person")Person person){

       personService.addPerson(person);
        try{
            applicantRepository.save(applicant);

            /*检验此人所填写的的所有联系人电话是否已存在在Phone结点中
             */

            if(!phoneService.verifyTheOnlyPhone(applicant.getColleague_phone())){
                //如果不存在，则将其添加到Phone
                phoneService.addPhone(new Phone(applicant.getColleague_phone(),"WHITE"));
            }
            if(!phoneService.verifyTheOnlyPhone(applicant.getCompany_phone())){
                //如果不存在，则将其添加到Phone
                phoneService.addPhone(new Phone(applicant.getCompany_phone(),"WHITE"));
            }
            if(!phoneService.verifyTheOnlyPhone(applicant.getParent_phone())){
                //如果不存在，则将其添加到Phone
                phoneService.addPhone(new Phone(applicant.getParent_phone(),"WHITE"));
            }
            //还缺一个指向Person的关系！！！！！！！！！！！！！！！！
            applicantRepository.createApply();
            applicantRepository.createColleaguePhone();
            applicantRepository.createCompanyPhone();
            applicantRepository.createParentPhone();
            applicantRepository.createColleagueOf1();
            applicantRepository.createColleagueOf2();
            applicantRepository.createParentOf();

            //找出此进件对应的人Person
            //  Person appPerson=personService.getPersonById(applicant.getApplicant());
            //  Apply apply=Apply.builder().person(appPerson).applicant(applicant).build();

        }catch(Exception e){
            log.error("ApplicantService 发生的异常为：Person新增失败! {} 参数为：{}", e, applicant);
            throw new RuntimeException(e.getMessage(), e);
        }
        return "添加进件成功";

    }
}