package com.webstarterz.happycheetah.moviesstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity implements PlanetAdapter.OnItemClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    searchFragmetn searchFragment;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    MaterialSearchView searchView;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private Pager adapter;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = this;
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        title = sharedPref.getString(getString(R.string.title),"key");
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.user_key), "00002");
        editor.commit();
        getSupportActionBar().setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        searchView = (MaterialSearchView)findViewById(R.id.search_view);


        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (RecyclerView) findViewById(R.id.left_drawer);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // improve performance by indicating the list if fixed size.
        mDrawerList.setHasFixedSize(true);

        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new PlanetAdapter(mPlanetTitles, this));
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //tab id
        mTabLayout=(TabLayout)findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        //add the tabs
        mTabLayout.addTab(mTabLayout.newTab().setText("Movies"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Handsets"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Service"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);



        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),0);

        mViewPager.setAdapter(adapter);




        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setScrollPosition(position, 0, true);
                mTabLayout.setSelected(true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /**FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });**/
        search();
    }


    public void search(){

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),0);
                TabLayout.Tab tab = mTabLayout.getTabAt(0);
                tab.setText("Movies");
                mViewPager.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("query",query);
                adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),11,query);
                TabLayout.Tab tab = mTabLayout.getTabAt(0);
                tab.setText("Search");
                mViewPager.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed(){
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){ //replace this with actual function which returns if the drawer is open
            mDrawerLayout.closeDrawer(GravityCompat.START);    // replace this with actual function which closes drawer
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position) {
        selectItem(position);
    }

    private void selectItem(int position) {

        if(position ==0){
            adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),position);
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.setText("Movies");

        }else if(position ==1){
            adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),position);
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.setText("Hollywood");

        }else if(position ==2){
            adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),position);
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.setText("Bollywood");
        }else if(position ==3){
            adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),position);
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.setText("Korea");
        }else if(position ==4){
            adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),position);
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.setText("Japan");
        }else if(position ==5){
            adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),position);
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.setText("China");
        }else if(position ==6){
            adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),position);
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.setText("Cartoon");
        }else if(position ==7){
            adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),position);
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.setText("18++");
        }else if(position ==8){
            adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),position);
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.setText("Series");
        }else if(position ==9){
            adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),position);
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.setText("Other");
        }else if(position ==10){
            adapter=new Pager(getSupportFragmentManager(), mTabLayout.getTabCount(),position);
            TabLayout.Tab tab = mTabLayout.getTabAt(0);
            tab.setText("Select");
        }
        mViewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
