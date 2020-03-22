//package com.rock.service;
//
//import com.rock.nodeEntity.Person;
//import com.rock.python.PyInterface;
//import com.rock.python.PyResult;
//import com.rock.repository.PersonRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import retrofit2.Call;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//import java.util.HashMap;
//import java.util.List;
//
//
//@RestController
//public class TestController {
//    @Autowired
//    private PersonRepository personRepository;
//    @Autowired
//    private  ApplicantService applicantService;
//    @RequestMapping("/test/{applyId}")
//    public Person test(@PathVariable(name = "applyId") String applyId) {
//        return personRepository.test(applyId);
//    }
//
//
//    @RequestMapping("/PredictByFeatures")
//    public Response<PyResult> test1(@RequestParam(name="features") int[] features) throws Exception {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://127.0.0.1:8887/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        PyInterface pyInterface = retrofit.create(PyInterface.class);
//        HashMap<String, List<Integer>> hashMap = new HashMap<>();
//       // hashMap.put("features", features);
//        System.out.println("hashMap"+hashMap);
//        Call<PyResult> test = pyInterface.test(hashMap);
//        Response<PyResult> execute = test.execute();
//        System.out.println("execute:"+execute);
//        System.out.println("execute:"+execute.body());
//        return execute;
//    }
//
//
//}
