package com.rock.python;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;
import java.util.Map;


public   interface PyInterface{

        @POST("/PredictByFeatures")
        public Call<PyResult> test(@Body Map<String, List<Integer>> requestFields);


}
