package com.webstarterz.happycheetah.moviesstore.Interface;

import com.webstarterz.happycheetah.moviesstore.DataResponse;
import com.webstarterz.happycheetah.moviesstore.DataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiItemList {
    @GET("itemList.php")
    Call<List<DataResponse>> getItemList(@Query("page_number") int page, @Query("item_count") int items,@Query("user_key") String key);
}
