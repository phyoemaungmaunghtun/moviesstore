package com.webstarterz.happycheetah.moviesstore.Interface;

import com.webstarterz.happycheetah.moviesstore.DataResponse;
import com.webstarterz.happycheetah.moviesstore.DataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface check_id {
    @GET("check_id.php")
    Call<List<DataResponse>> getID(@Query("user_key") String key);
}
