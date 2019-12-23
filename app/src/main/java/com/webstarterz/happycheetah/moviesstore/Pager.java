package com.webstarterz.happycheetah.moviesstore;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by suraj on 23/6/17.
 */

public class Pager extends FragmentStatePagerAdapter {

    int tabCount;
    private int pos = 0;
    String name;

    public Pager(FragmentManager fm, int tabCount, int pos){

        super(fm);

        this.tabCount=tabCount;
        this.pos = pos;
    }
    public Pager(FragmentManager fm, int tabCount, int pos,String string){

        super(fm);

        this.tabCount=tabCount;
        this.pos = pos;
        this.name = string;
    }

    public void insert(int i){
        this.pos = i;
    }

    //@Override
    public Fragment getItem(int position,int i) {
        switch(position){
            case 0 :
                if(i == 0){
                    AllMovies tab4Fragment=new AllMovies();
                    return tab4Fragment;
                }else if(i == 1){
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "Hollywood");
                    // set Fragmentclass Arguments
                    android.support.v4.app.Fragment addedFragment = new searchFragmetn();
                    addedFragment.setArguments(bundle);
                    return addedFragment;
                }else if(i == 2){
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "Bollywood");
                    // set Fragmentclass Arguments
                    android.support.v4.app.Fragment addedFragment = new searchFragmetn();
                    addedFragment.setArguments(bundle);
                    return addedFragment;
                }else if(i == 3){
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "Korea");
                    // set Fragmentclass Arguments
                    android.support.v4.app.Fragment addedFragment = new searchFragmetn();
                    addedFragment.setArguments(bundle);
                    return addedFragment;
                }else if(i == 4){
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "Japan");
                    // set Fragmentclass Arguments
                    android.support.v4.app.Fragment addedFragment = new searchFragmetn();
                    addedFragment.setArguments(bundle);
                    return addedFragment;
                }else if(i == 5){
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "China");
                    // set Fragmentclass Arguments
                    android.support.v4.app.Fragment addedFragment = new searchFragmetn();
                    addedFragment.setArguments(bundle);
                    return addedFragment;
                }else if(i == 6){
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "Cartoon");
                    // set Fragmentclass Arguments
                    android.support.v4.app.Fragment addedFragment = new searchFragmetn();
                    addedFragment.setArguments(bundle);
                    return addedFragment;
                }else if(i == 7){
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "18");
                    // set Fragmentclass Arguments
                    android.support.v4.app.Fragment addedFragment = new searchFragmetn();
                    addedFragment.setArguments(bundle);
                    return addedFragment;
                }else if(i == 8){
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "Series");
                    // set Fragmentclass Arguments
                    android.support.v4.app.Fragment addedFragment = new searchFragmetn();
                    addedFragment.setArguments(bundle);
                    return addedFragment;

                }else if(i == 9){
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "Other");
                    // set Fragmentclass Arguments
                    android.support.v4.app.Fragment addedFragment = new searchFragmetn();
                    addedFragment.setArguments(bundle);
                    return addedFragment;

                }else if(i == 10){
                    Selected_Fragment tab2Fragment=new Selected_Fragment();
                    return tab2Fragment;
                }else if(i == 11){
                    Bundle bundle = new Bundle();
                    bundle.putString("name",name );
                    // set Fragmentclass Arguments
                    android.support.v4.app.Fragment addedFragment = new searchFragmetn();
                    addedFragment.setArguments(bundle);
                    return addedFragment;
                }



            case 1 :
                Handset_Fragment tab2Fragment=new Handset_Fragment();
                return tab2Fragment;
            case 2 :
                Service_Fragment tab3Fragment=new Service_Fragment();
                return tab3Fragment;
            default: return null;
        }
    }


    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public Fragment getItem(int i) {
        return getItem(i,pos);
    }
}
