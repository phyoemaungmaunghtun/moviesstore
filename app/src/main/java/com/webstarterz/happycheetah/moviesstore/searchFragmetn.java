package com.webstarterz.happycheetah.moviesstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.webstarterz.happycheetah.moviesstore.Interface.ApiSearch;
import com.webstarterz.happycheetah.moviesstore.Interface.searchInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class searchFragmetn extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Images> albumList;
    String userkey;
    String name;
    String search;
    private searchInterface searchInterface;
    private GridLayoutManager mLayoutManager;

    private int page_number = 1;
    private int item_count = 10;

    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();

    private AppBarLayout appBarLayout;

    private boolean isLoading = true;
    private int pastVisibleItem,visibleItemCount,totalItemCount,previous_total = 0;
    private int view_threshold = 10;

    ImageView imd;
    View retView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        retView = inflater.inflate(R.layout.fragment_dynamic, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        imd = (ImageView) retView.findViewById(R.id.backdrop);
        try {

            GlideApp.with(this)
                    .load(R.drawable.cover )
                    .placeholder(null)
                    .into(imd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initCollapsingToolbar();//Do just for collapsing
        SharedPreferences sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        userkey = sharedPref.getString(getString(R.string.user_key),"key");
        progressBar = retView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = (RecyclerView) retView.findViewById(R.id.recycler_view);//now that is adding album item
        albumList = new ArrayList<>();
        albumList.clear();

        name = getArguments().getString("name");
        if(name == "Hollywood" || name == "Bollywood" || name == "Korea" || name == "Japan" || name == "China"|| name == "Cartoon"|| name == "18"|| name == "Series"|| name == "Other"){
            search = "category";
        }else{
            search = "search";
        }
        appBarLayout = (AppBarLayout) retView.findViewById(R.id.appbar);
        adapter = new AlbumsAdapter(getActivity(), albumList);

        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        searchInterface = ApiSearch.getApiclient().create(searchInterface.class);
        Call<List<DataResponse>> call = searchInterface.getImages(name,search,page_number, item_count,userkey);

        call.enqueue(new Callback<List<DataResponse>>() {

            @Override
            public void onResponse(Call<List<DataResponse>> call, Response<List<DataResponse>> response) {
                if(response.body().get(0).getStatus().equals("ok")){
                    List<Images> images = response.body().get(1).getImages();
                    int size = images.size();
                    Log.d("size",""+size);
                    adapter = new AlbumsAdapter(getActivity(), images);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);


                }else{
                    Toast.makeText(getActivity(),"No more Images available..",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<List<DataResponse>> call, Throwable t) {

            }
        });

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

        //JSON_STRING = ((MainActivity) getActivity()).post_String();
        return retView;


    }

    private void performPagaination(){

        progressBar.setVisibility(View.VISIBLE);
        Call<List<DataResponse>> call = searchInterface.getImages(name,search,page_number, item_count,userkey);

        call.enqueue(new Callback<List<DataResponse>>() {
            @Override
            public void onResponse(Call<List<DataResponse>> call, Response<List<DataResponse>> response) {
                if(response.body().get(0).getStatus().equals("ok")){
                    List<Images> images = response.body().get(1).getImages();
                    adapter.addImages(images);
                    //Toast.makeText(getActivity(),"Page "+page_number+"is loaded...",Toast.LENGTH_SHORT).show();


                }else{
                    //Toast.makeText(getActivity(),"No more Images available..",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<DataResponse>> call, Throwable t) {

            }
        });

    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) retView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) retView.findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }



        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
