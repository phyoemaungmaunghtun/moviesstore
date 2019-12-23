package com.webstarterz.happycheetah.moviesstore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class serviceAdapter extends ArrayAdapter<ServiceList> {

   // private static final String TAG = SelectedAdapter.class.getSimpleName();
    public SparseBooleanArray selected_items;
    private SparseBooleanArray selected_id;
    private int current_selected_idx = -1;
    List<ServiceList>  personList;
    private Context mContext;

    public serviceAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.personList = objects;
        this.selected_items = new SparseBooleanArray();
        selected_id = new SparseBooleanArray();
        this.mContext = context;
    }




    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        ServiceList currentPerson = getItem(position);

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.service_item_list, parent, false);
        }

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.service_text_view);
        nameTextView.setText(currentPerson.getService());


        return listItemView;
    }

    @Override
    public void remove(ServiceList object) {
    }



    public ServiceList getItem(int position) {
        return personList.get(position);
    }
}
