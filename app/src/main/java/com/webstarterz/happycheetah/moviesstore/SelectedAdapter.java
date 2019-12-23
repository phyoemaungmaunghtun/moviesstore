package com.webstarterz.happycheetah.moviesstore;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class SelectedAdapter extends ArrayAdapter<moviesAlblum>{
    private static final String TAG = SelectedAdapter.class.getSimpleName();
    public SparseBooleanArray selectedListItemsIds;
    List personList;
    DatabaseHelper dbhelper;
    private Context mContext;
    CardView parent_layout;

    public SelectedAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.personList = objects;
        this.selectedListItemsIds = new SparseBooleanArray();
        this.mContext = context;
    }




    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        final moviesAlblum currentPerson = getItem(position);

       if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.movies_list_item, parent, false);
       }

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.name_text_view);
        nameTextView.setText(currentPerson.getName());

        parent_layout = (CardView) listItemView.findViewById(R.id.card_view);
        TextView ageTextView = (TextView) listItemView.findViewById(R.id.age_text_view);
        ageTextView.setText(currentPerson.getCodeno());

        ImageView img = (ImageView) listItemView.findViewById(R.id.selectImage);
        Glide.with(listItemView).load(currentPerson.getUrl()).into(img);
        img.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(mContext, detail_activity.class);
        intent.putExtra("image_name",currentPerson.getCodeno());

        mContext.startActivity(intent);
    }
});



        return listItemView;
    }

    @Override
    public void remove(moviesAlblum object) {
        dbhelper = new DatabaseHelper(mContext);
        String str = object.getName();
        dbhelper.deleteContact(str);
        personList.remove(object);
        notifyDataSetChanged();
    }


    public void removeSelection() {
        selectedListItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public  void toggleSelection(int position) {
        selectView(position, !selectedListItemsIds.get(position));
    }

    public void selectView(int position, boolean value) {
        if (value)
            selectedListItemsIds.put(position, value);
        else
            selectedListItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedIds() {
        return selectedListItemsIds;
    }

}