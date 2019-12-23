package com.webstarterz.happycheetah.moviesstore.Interface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiGetService {
    public static final String Base_url = "http://happycheetah.webstarterz.com/picupload/getService_items.php/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiclient(){
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(Base_url).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
