package com.webstarterz.happycheetah.moviesstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.webstarterz.happycheetah.moviesstore.Interface.ApiSearch;
import com.webstarterz.happycheetah.moviesstore.Interface.searchInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class detail_activity extends AppCompatActivity {
    private com.webstarterz.happycheetah.moviesstore.Interface.searchInterface searchInterface;
    String name;
    String type;
    String genre;
    String rating;
    String year;
    String about;
    String image;
    String imageUrl;
    String codeno;

    String de;
    String Detail;
    String userkey;
    DatabaseHelper dbhelper;
    private List<Images> albumList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        de = getIntent().getStringExtra("image_name");
        SharedPreferences sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        userkey = sharedPref.getString(getString(R.string.user_key),"key");
        searchInterface = ApiSearch.getApiclient().create(searchInterface.class);
        Detail = "detail";
        Call<List<DataResponse>> call = searchInterface.getImages(de,Detail,1,1,userkey);

        call.enqueue(new Callback<List<DataResponse>>() {

            @Override
            public void onResponse(Call<List<DataResponse>> call, Response<List<DataResponse>> response) {
                List<Images> images = response.body().get(1).getImages();
                Images im = images.get(0);
                name = im.getImagename();
                codeno = im.getCodeNo();
                type = im.getType();
                genre = im.getGenre();
                rating = im.getRating();
                year = im.getImageYear();
                about = im.getAbout();
                imageUrl = im.getImagePath();

                int size = images.size();
                //Log.d("size",""+size);
                setImage(imageUrl,name, year, genre, rating, about);
            }

            @Override
            public void onFailure(Call<List<DataResponse>> call, Throwable t) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dbhelper = new DatabaseHelper(detail_activity.this);
            Cursor cursor = dbhelper.getContactID(name,codeno);
            if (cursor.getCount() != 1) {
                boolean what = dbhelper.addContact(name,codeno,imageUrl);
                if(what == true) {
                    Toast.makeText(detail_activity.this, "OK ", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(detail_activity.this, "Not OK", Toast.LENGTH_SHORT).show();
                }
            }else{

                Toast.makeText(detail_activity.this, "You did it!", Toast.LENGTH_SHORT).show();
            }
        }
        });
    }


    private void setImage(String imageUrl, String imageName, String imageYear, String imageGenre,String imageRating,String imageAbout){

        TextView name = findViewById(R.id.image_description);
        TextView about = findViewById(R.id.About);
        TextView year = findViewById(R.id.year);
        TextView genre = findViewById(R.id.genre);
        TextView rating = findViewById(R.id.rating);
        name.setText(imageName);
        about.setText(imageAbout);
        year.setText(imageYear);
        genre.setText(imageGenre);
        rating.setText(imageRating);

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }
}


