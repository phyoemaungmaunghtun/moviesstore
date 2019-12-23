package com.webstarterz.happycheetah.moviesstore;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class itemListAdapter extends RecyclerView.Adapter<itemListAdapter.ViewHolder> {
    private Context ctx;
    private List<ItemList> items;
    private OnClickListener onClickListener = null;


    private SparseBooleanArray selected_items;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView brand,model,price;
        public ImageView image;
        public View lyt_parent;

        public ViewHolder(View view) {
            super(view);
            brand = (TextView) view.findViewById(R.id.brand);
            model = (TextView) view.findViewById(R.id.model);
            price = (TextView) view.findViewById(R.id.price);
            image =  (ImageView) view.findViewById(R.id.media_image);
            lyt_parent = (View) view.findViewById(R.id.lyt_parent);
        }
    }

    public itemListAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public itemListAdapter(Context mContext, List<ItemList> items) {
        Log.d("ehllo","phyoemaung");
        this.ctx = mContext;
        this.items = items;
        selected_items = new SparseBooleanArray();
    }


    @NonNull
    @Override
    public itemListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.one_item_view, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull itemListAdapter.ViewHolder holder, final int i) {

        final ItemList inbox = items.get(i);

        // displaying text view data
        holder.brand.setText(inbox.getBrand());
        holder.model.setText(inbox.getModel());
        holder.price.setText(inbox.getPrice());
            Glide.with(ctx).load(inbox.getUrl()).into(holder.image);



        Log.d("Url",""+inbox.getUrl());

        holder.lyt_parent.setActivated(selected_items.get(i, false));

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null) return;
                onClickListener.onItemClick(v, inbox, i);
            }
        });

        holder.lyt_parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onClickListener == null) return false;
                onClickListener.onItemLongClick(v, inbox, i);
                return true;
            }
        });

        //toggleCheckedIcon(holder, i);
        //displayImage(holder, inbox);


    }

    /**private void displayImage(ViewHolder holder, ItemList inbox) {
        if (inbox.image != null) {
            holder.image.setImageResource(inbox.image);
            holder.image.setColorFilter(null);
            holder.image_letter.setVisibility(View.GONE);
        } else {
            holder.image.setImageResource(R.drawable.shape_circle);
            holder.image.setColorFilter(inbox.color);
            holder.image_letter.setVisibility(View.VISIBLE);
        }
    }

    private void toggleCheckedIcon(ViewHolder holder, int position) {
        if (selected_items.get(position, false)) {
            holder.lyt_image.setVisibility(View.GONE);
            holder.lyt_checked.setVisibility(View.VISIBLE);
            if (current_selected_idx == position) resetCurrentIndex();
        } else {
            holder.lyt_checked.setVisibility(View.GONE);
            holder.lyt_image.setVisibility(View.VISIBLE);
            if (current_selected_idx == position) resetCurrentIndex();
        }
    }**/

    @Override
    public int getItemCount() {

        int a ;

        if(items != null && !items.isEmpty()) {

            a = items.size();
        }
        else {

            a = 0;

        }

        return a;
    }

    public void removeItem() {
        if(items != null && !items.isEmpty()) {

            items.clear();
        }


    }

    public void addImages(List<ItemList> images){

        int size = images.size();
        for(int i = 0;i<size;i++) {
            ItemList im = images.get(i);
            items.add(im);
        }
        notifyDataSetChanged();
    }

    public int getId() {
        return items.size();

    }

    public interface OnClickListener {
        void onItemClick(View view, ItemList obj, int pos);

        void onItemLongClick(View view, ItemList obj, int pos);
    }


}
