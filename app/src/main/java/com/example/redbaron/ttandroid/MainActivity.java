package com.example.redbaron.ttandroid;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static ImageView current_state;
    public static int current_opposite;
    private DrawerLayout mDrawerLayout;

    private ArrayList<String> dwTitles;
    private ArrayList<String> dwDescriptions;
    private ArrayList<String> dwLinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.tt_nav_drawer_container);

        dwTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.dw_titles)));
        dwDescriptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.dw_descriptions)));
        dwLinks = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.dw_links)));

        initRecyclerView();

        Toolbar actionBar = (Toolbar) findViewById(R.id.tt_top_bar);
        setSupportActionBar(actionBar);

        ActionBar activeActionBar = (ActionBar) getSupportActionBar();
        activeActionBar.setDisplayHomeAsUpEnabled(true);
        activeActionBar.setHomeAsUpIndicator(R.mipmap.ic_nav_button);

        current_state = (ImageView) findViewById(R.id.nav_home);
        current_opposite = R.mipmap.ic_home;

    }

    public void bottom_nav_click(View v){
        current_state.setImageResource(current_opposite);
        switch(v.getId()){
            case R.id.nav_add:
                ((ImageView) v).setImageResource(R.mipmap.ic_add_event_click);
                current_state = (ImageView) v;
                current_opposite = R.mipmap.ic_add_event;
                break;
            case R.id.nav_search:
                ((ImageView) v).setImageResource(R.mipmap.ic_search_click);
                current_state = (ImageView) v;
                current_opposite = R.mipmap.ic_search;
                break;
            case R.id.nav_home:
                ((ImageView) v).setImageResource(R.mipmap.ic_home_click);
                current_state = (ImageView) v;
                current_opposite = R.mipmap.ic_home;
                break;
            case R.id.nav_bookmark:
                ((ImageView) v).setImageResource(R.mipmap.ic_bookmark_click);
                current_state = (ImageView) v;
                current_opposite = R.mipmap.ic_bookmark;
                break;
            case R.id.nav_location:
                ((ImageView) v).setImageResource(R.mipmap.ic_location_click);
                current_state = (ImageView) v;
                current_opposite = R.mipmap.ic_location;
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.tt_drawer_list);
        DrawerRecyclerAdapter adapter = new DrawerRecyclerAdapter(dwTitles, dwDescriptions, dwLinks, this);
        DividerItemDecoration itemDecor = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
