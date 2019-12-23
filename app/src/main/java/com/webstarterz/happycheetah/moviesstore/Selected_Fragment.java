package com.webstarterz.happycheetah.moviesstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class Selected_Fragment extends Fragment{
    private ArrayList<moviesAlblum> personList;
    private SelectedAdapter personAdapter;
    ListView listView;
    View retView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        retView = inflater.inflate(R.layout.selected_movies, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        Cursor cursor = databaseHelper.getAllContacts();
        personList = new ArrayList<>();
        if(!cursor.moveToNext()){
            Toast.makeText(getActivity(), "There are no contacts to show", Toast.LENGTH_SHORT).show();
        }else{
            cursor.moveToFirst();
            personList.add(new moviesAlblum(
                    cursor.getString(1),//name
                    cursor.getString(2),//phone number
                    cursor.getString(3)
            ));
        }

        while(cursor.moveToNext()){
            personList.add(new moviesAlblum(
                    cursor.getString(1),//name
                    cursor.getString(2),//phone number
                    cursor.getString(3)
            ));
        }

        personAdapter = new SelectedAdapter(getActivity(), 0, personList);

        listView = (ListView) retView.findViewById(R.id.names_list_view);
        listView.setAdapter(personAdapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount = listView.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                personAdapter.toggleSelection(position);
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.delete_menu_option, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        // call getSelectedIds method from customListViewAdapter
                        SparseBooleanArray selected = personAdapter.getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                moviesAlblum selectedListItem = personAdapter.getItem(selected.keyAt(i));
                                // Remove selected items using ids
                                personAdapter.remove(selectedListItem);
                            }
                        }
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                personAdapter.removeSelection();
            }
        });

        return retView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //finish();
        } else {
            Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }





    }

