package com.rock.service;

import com.rock.entity.FeatureData;
import com.rock.entity.Result;
import com.rock.mapper.FeatureDataMapper;
import com.rock.nodeEntity.Applicant;
import com.rock.nodeEntity.Person;
import com.rock.nodeEntity.Phone;
import com.rock.python.PyInterface;
import com.rock.python.PyResult;
import com.rock.repository.ApplicantRepository;
import com.rock.repository.PersonRepository;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.internal.shaded.io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.*;

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
     * 检验Applicacnt是否已经存在
     *
     * @return
     */
    @GraphQLQuery(name = "verifyTheOnlyApplicacnt", description = "检验Applicacnt是否已经存在")
    public boolean verifyTheOnlyApplicacnt(@GraphQLArgument(name = "id", description = "id") String id) {
        if (Objects.isNull(this.applicantRepository.selectApplicantById(id))) {
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
    public Result forFeatureDataTable() throws Exception {
        List<String> ids = applicantRepository.getAllApplicantId();
        for(String  id  :  ids)  {
            FeatureData f=this.BFCheck(id);
        }
        return Result.ok("得到特征数值表成功");
    }

    @GraphQLQuery(name = "BFPredict", description = "仅获取7个特征值，用来预测,训练模型用，实际项目不用这个接口")
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
    /**
     *
     *  预测结果分析-----------------start
     * */
    @GraphQLQuery(name = "overdueDetails", description = "1.预测结果分析表单-逾期详情")
    public List<Applicant> overdueDetails(@GraphQLArgument(name = "applyId", description = "Applicant的id:applyId") String applyId) throws Exception {
        return personRepository.overdueDetails(applyId);
    }
    @GraphQLQuery(name = "OneDimenRelationshipBFDetails", description = "4.预测结果分析表单一维关系中触碰黑名单的人-详情")
    public List<Person> OneDimenRelationshipBFDetails(@GraphQLArgument(name = "applyId", description = "Applicant的id:applyId") String applyId) throws Exception {
        return personRepository.OneDimenRelationshipBFDetails(applyId);
    }
    @GraphQLQuery(name = "OneDimenRelationshipPhoneBFDetails", description = "5.预测结果分析表单，一维关系中触碰黑名单的电话-详情")
    public List<Person> OneDimenRelationshipPhoneBFDetails(@GraphQLArgument(name = "applyId", description = "Applicant的id:applyId") String applyId) throws Exception {
        return personRepository.OneDimenRelationshipPhoneBFDetails(applyId);
    }
    @GraphQLQuery(name = "TwoDimenRelationshipBFDetails", description = "6.预测结果分析表单-二维关系中触碰黑名单的人-详情")
    public List<Person> TwoDimenRelationshipBFDetails(@GraphQLArgument(name = "applyId", description = "Applicant的id:applyId") String applyId) throws Exception {
        return personRepository.TwoDimenRelationshipBFDetails(applyId);
    }
    @GraphQLQuery(name = "TwoDimenRelationshipPhoneBFDetals", description = "7.预测结果分析表单-二维关系中触碰黑名单的电话-详情")
    public List<Person> TwoDimenRelationshipPhoneBFDetals(@GraphQLArgument(name = "applyId", description = "Applicant的id:applyId") String applyId) throws Exception {
        return personRepository.TwoDimenRelationshipPhoneBFDetals(applyId);
    }

    @GraphQLQuery(name = "resultDetails", description = "预测结果分析表单-详情")
    public Map<String,Object> resultDetails(@GraphQLArgument(name = "id", description = "Applicant的id:applyId") String id) throws Exception {
        List<Integer> list=this.BFPredict(id);
        Map<String,Object> map = new HashMap<>();
        map.put("predictResult",this.invokePython(id));
        if(list.get(0)!=0){
            map.put("overdueDetails",this.overdueDetails(id));

        }
        if(list.get(1)!=0){
            map.put("clientBF","此客户处于黑名单");
        }else{
            map.put("clientBF","此客户处于白名单");
        }
        if(list.get(2)!=0){
            map.put("clientPhoneBF","此客户手机号处于黑名单");
        }else{
            map.put("clientPhoneBF","此客户手机号处于白名单");
        }
        if(list.get(3)!=0){
            map.put("OneDimenRelationshipBFDetails",this.OneDimenRelationshipBFDetails(id));
        }
        if(list.get(4)!=0){
            map.put("OneDimenRelationshipPhoneBFDetails",this.OneDimenRelationshipPhoneBFDetails(id));
        }
        if(list.get(5)!=0){
            map.put("TwoDimenRelationshipBFDetails",this.TwoDimenRelationshipBFDetails(id));
        }
        if(list.get(6)!=0){
            map.put("TwoDimenRelationshipPhoneBFDetals",this.TwoDimenRelationshipPhoneBFDetals(id));
        }
        return map;
    }
    /**
     *
     *  预测结果分析-----------------end
    * */
    @GraphQLQuery(name = "invokePython", description = "调用python的http接口，返回预测结果")
    public PyResult invokePython(@GraphQLArgument(name = "id", description = "Applicant的id") String id) throws Exception {
        List<Integer> list=BFPredict(id);
        if(list.get(1)==1){
            PyResult res=new PyResult("0");
            return res;
        }
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
    public Result  addApplicant(@GraphQLArgument(name = "applicant", description = "进件")Applicant applicant){
        if(this.verifyTheOnlyApplicacnt(applicant.getId())==true){
            return Result.error("此Applicant的id已存在，请重新输入");
        }

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
        return Result.ok("添加进件成功");

    }

    /*
     * 若此进件的申请人还不存在于Person结点，同时添加Person和Applicant
     * */

    @GraphQLMutation(name = "addApplicant", description = "添加addApplicant实体结点,同时添加Person结点，并创建关系Person-[r:HAS_PHONE]->Phone")
    public Result  addApplicantAndPerson(@GraphQLArgument(name = "applicant", description = "进件")Applicant applicant,
                                         @GraphQLArgument(name = "person", description = "person")Person person){
        if(!this.verifyTheOnlyApplicacnt(applicant.getId())){
            return Result.error("此Applicant的id已存在，请重新输入");
        }
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
        return Result.ok("添加进件成功");

    }

    @GraphQLMutation(name = "deleteApplicantById", description = "根据Applicant的id删除Applicant")
    public Result deleteApplicantById(@GraphQLArgument(name="id",description = "Applicant的id")String id){
        if(!this.verifyTheOnlyApplicacnt(id)){
            return Result.error("此进件不存在！");
        }else if(StringUtil.isNullOrEmpty(id)){
            return Result.error("请选择要删除的进件！");
        }else {
            applicantRepository.deleteApplicantById(id);
            return Result.ok("删除成功");
        }
    }

    @GraphQLQuery(name="selectAllApplicant",description = "查询所有Applicant")
    public Page<Applicant> selectAllApplicant(@GraphQLArgument(name = "currentPage", description = "currentPage") int currentPage){
        return applicantRepository.selectAllApplicant( PageRequest.of(currentPage,10));
    }

    @GraphQLQuery(name="selectApplicant",description = "按条件查询Applicant")
    public Page<Applicant> selectApplicant(@GraphQLArgument(name="applicant",description = "applicant")Applicant applicant,
                                           @GraphQLArgument(name = "currentPage", description = "currentPage") int currentPage){
        return applicantRepository.selectApplicant(applicant.getId(),applicant.getAmount(),applicant.getTerm(),applicant.getJob(),applicant.getCity()
        ,applicant.getParent_phone(),applicant.getColleague_phone(),applicant.getCompany_phone(),applicant.getStatus(), PageRequest.of(currentPage,10));
    }
    @GraphQLMutation(name="updateApplicant",description = "修改Applicant")
    public Result updateApplicant(@GraphQLArgument(name="applicant",description = "applicant")Applicant applicant){
        applicantRepository.updateApplicant1(applicant.getId());
        applicantRepository.updateApplicant2(applicant.getId(),applicant.getAmount(),applicant.getTerm(),applicant.getJob(),applicant.getCity()
                ,applicant.getParent_phone(),applicant.getColleague_phone(),applicant.getCompany_phone(),applicant.getStatus());
        applicantRepository.createColleaguePhone();
        applicantRepository.createCompanyPhone();
        applicantRepository.createParentPhone();
        return Result.ok("修改成功！");
    }
    @GraphQLQuery(name="selectPersonByApplicant",description = "查看客户")
    public List<Person> selectPersonByApplicant(@GraphQLArgument(name="id",description = "Applicant.id")String id){
        return applicantRepository.selectPersonByApplicant(id);
    }
    @GraphQLQuery(name="countOfApplicant",description = "首页显示-Applicant数量")
    public int countOfApplicant(){
        return applicantRepository.countOfApplicant();
    }

    @GraphQLQuery(name="allNullStatus",description = "所有Applicant.status为空的Applicant")
    public Page<Applicant> allNullStatus(@GraphQLArgument(name = "currentPage", description = "currentPage") int currentPage){
        return applicantRepository.allNullStatus( PageRequest.of(currentPage,10));
    }

    @GraphQLQuery(name="selectNullStatus",description = "按条件查询Applicant.status为空的Applicant")
    public  Page<Applicant> selectNullStatus(@GraphQLArgument(name="applicant",description = "applicant")Applicant applicant,
                                             @GraphQLArgument(name = "currentPage", description = "currentPage") int currentPage){
        return applicantRepository.selectNullStatus(applicant.getId(),applicant.getAmount(),applicant.getTerm(),applicant.getJob(),applicant.getCity(),
                applicant.getApplicant(), PageRequest.of(currentPage,10));
    }
    @GraphQLQuery(name="selectThroughInfoTest",description = "按状态查询-查询通过信息检测的Applicant")
    public Page<Applicant> selectThroughInfoTest(@GraphQLArgument(name = "currentPage", description = "currentPage") int currentPage){
        return applicantRepository.selectThroughInfoTest("ThroughInfoTest", PageRequest.of(currentPage,10));
    }
    @GraphQLMutation(name="updateApplyInfoTest",description = "将Applicant状态改为通过信息检测")
    public Result updateApplyInfoTest(@GraphQLArgument(name="id",description = "id")String id){
        applicantRepository.updateApplyRiskStatus(id,"ThroughInfoTest");
        return Result.ok("修改成功");
    }
    @GraphQLMutation(name="updateApplyRiskStatus",description = "将Applicant状态改为通过风险预测,进件进入到申请中状态")
    public Result updateApplyRiskStatus(@GraphQLArgument(name="id",description = "id")String id){
        applicantRepository.updateApplyRiskStatus(id,"IN_PROGREESS");
        return Result.ok("修改成功");
    }
}
