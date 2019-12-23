package com.webstarterz.happycheetah.moviesstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.webstarterz.happycheetah.moviesstore.Interface.ApiItemList;
import com.webstarterz.happycheetah.moviesstore.Interface.Apigetitem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Handset_Fragment extends Fragment {
    private ProgressBar progressBar;
    private itemListAdapter adapter;
    private com.webstarterz.happycheetah.moviesstore.Interface.ApiItemList apiItemList;
    ListView listView;
    private RecyclerView recyclerView;
    private GridLayoutManager mLayoutManager;
    Context mContext;
    private List<ItemList> albumList;;
    private Call<List<DataResponse>> call1;
    int dele = 5;
    private int page_number = 1;
    private int item_count = 10;

    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();

    private AppBarLayout appBarLayout;
    String userkey;

    private boolean isLoading = true;
    private int pastVisibleItem,visibleItemCount,totalItemCount,previous_total = 0;
    private int view_threshold = 10;
    View retView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        retView = inflater.inflate(R.layout.item_list, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        recyclerView =  retView.findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        userkey = sharedPref.getString(getString(R.string.user_key),"key");
        recyclerView.setLayoutManager(mLayoutManager);
        progressBar = retView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        mContext = getActivity();
        adapter = new itemListAdapter(mContext);
        recyclerView.setAdapter(adapter);

            initComponent();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previous_total) {
                            isLoading = false;
                            previous_total = totalItemCount;
                        }
                    }
                    if (!isLoading && (totalItemCount - visibleItemCount <= (pastVisibleItem + view_threshold))) {

                        page_number++;
                        performPagaination();
                        isLoading = true;
                    }
                }
            }
        });


        return retView;
    }

    private void performPagaination(){
        Log.d("hello","hello");


        progressBar.setVisibility(View.VISIBLE);
        //apiItemList = Apigetitem.getApiclient().create(ApiItemList.class);
        Call<List<DataResponse>> call = apiItemList.getItemList(page_number,item_count,userkey);

        call.enqueue(new Callback<List<DataResponse>>() {
            @Override
            public void onResponse(Call<List<DataResponse>> call, Response<List<DataResponse>> response) {
                if(response.body().get(0).getStatus().equals("ok")){
                    albumList =  response.body().get(1).getItemList();
                    adapter.addImages(albumList);
                    Toast.makeText(getActivity(),"Page "+page_number+"is loaded...",Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(getActivity(),"No more Images available..",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<DataResponse>> call, Throwable t) {

            }
        });

    }

    private void initComponent() {

        apiItemList = Apigetitem.getApiclient().create(ApiItemList.class);
        Call<List<DataResponse>> call = apiItemList.getItemList(page_number,item_count,userkey);
        call.enqueue(new Callback<List<DataResponse>>() {

            @Override
            public void onResponse(Call<List<DataResponse>> call, Response<List<DataResponse>> response) {

                albumList =  response.body().get(1).getItemList();
                //Toast.makeText(getActivity(), "Read: " + albumList.size(), Toast.LENGTH_SHORT).show();
                //adapter.removeItem();
                adapter = new itemListAdapter(mContext, albumList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<List<DataResponse>> call, Throwable t) {
                Log.d("hello","Failure");
            }
        });
    }
}
