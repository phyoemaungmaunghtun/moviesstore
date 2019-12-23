package com.webstarterz.happycheetah.moviesstore;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Images> albumList;
    DatabaseHelper dbhelper;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        RelativeLayout parentLayout;
        CardView cardView;



        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            cardView = (CardView)view.findViewById(R.id.card_view);

        }
        }



    public AlbumsAdapter(Context mContext, List<Images> images) {
        this.mContext = mContext;
        this.albumList = images;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Images album = albumList.get(position);
        holder.title.setText(album.getImagename());
        holder.count.setText(album.getImageYear());

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getImagePath()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(album.getImagename(),album.getCodeNo(),album.getImagePath());
                //showPopupMenu(holder.overflow,album.getImagename(),album.getCodeNo(),album.getImagePath());
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));
                Images album = albumList.get(position);

                Intent intent = new Intent(mContext, detail_activity.class);
                intent.putExtra("image_name",album.getCodeNo());

                mContext.startActivity(intent);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view,String name,String year,String url) {
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        //popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(name,year,url));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        String name,year,url;

        public MyMenuItemClickListener(String name, String year,String url) {
            this.name = name;
            this.year = year;
            this.url = url;
        }

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    dbhelper = new DatabaseHelper(mContext);
                    Cursor cursor = dbhelper.getContactID(name,year);
                    if (cursor.getCount() != 1) {
                        boolean what = dbhelper.addContact(name,year,url);
                        if(what == true) {
                            Toast.makeText(mContext, "OK ", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, "Not OK", Toast.LENGTH_SHORT).show();
                        }
                    }else{

                        Toast.makeText(mContext, "You did it!", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                default:
            }
            return false;
        }
    }

    public void save(String name,String year,String url){
        dbhelper = new DatabaseHelper(mContext);
        Cursor cursor = dbhelper.getContactID(name,year);
        if (cursor.getCount() != 1) {
            boolean what = dbhelper.addContact(name,year,url);
            if(what == true) {
                Toast.makeText(mContext, "OK ", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mContext, "Not OK", Toast.LENGTH_SHORT).show();
            }
        }else{

            Toast.makeText(mContext, "You did it!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public void addImages(List<Images> images){

        int size = images.size();
        for(int i = 0;i<size;i++) {
            Images im = images.get(i);
            albumList.add(im);
        }
        notifyDataSetChanged();
    }

}
