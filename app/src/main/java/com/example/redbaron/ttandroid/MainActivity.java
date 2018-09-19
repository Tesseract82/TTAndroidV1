package com.example.redbaron.ttandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static ImageView current_state;
    public static int current_opposite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
