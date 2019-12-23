package com.webstarterz.happycheetah.moviesstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.webstarterz.happycheetah.moviesstore.Interface.ApiGetService;
import com.webstarterz.happycheetah.moviesstore.Interface.getService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Service_Fragment extends Fragment {
    Context mContext;
    private serviceAdapter personAdapter;
    ListView listView;
    private List<ServiceList> albumList;
    private com.webstarterz.happycheetah.moviesstore.Interface.getService apiservice;
    String userkey;
    View retView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        retView = inflater.inflate(R.layout.service_list, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        userkey = sharedPref.getString(getString(R.string.user_key),"key");
        mContext = getActivity();
        listView = (ListView) retView.findViewById(R.id.servicelist);
        initComponent();
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);



        return retView;
    }

    private void initComponent() {

        apiservice = ApiGetService.getApiclient().create(getService.class);
        Call<List<DataResponse>> call = apiservice.getServiceList(userkey);
        call.enqueue(new Callback<List<DataResponse>>() {

            @Override
            public void onResponse(Call<List<DataResponse>> call, Response<List<DataResponse>> response) {

                albumList =  response.body().get(0).getServiceList();
                personAdapter = new serviceAdapter(mContext, 0, albumList);
                listView = (ListView) retView.findViewById(R.id.servicelist);
                listView.setAdapter(personAdapter);
                //Toast.makeText(getActivity(), "Read: " + albumList.size(), Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call<List<DataResponse>> call, Throwable t) {
                Log.d("hello","Failure");
            }
        });
    }


}
