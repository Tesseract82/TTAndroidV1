package com.example.redbaron.ttandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class NetflixMainRecyclerAdapter extends RecyclerView.Adapter{

    private HashMap<String, ArrayList<TTEvent>> catNameToList;
    private ArrayList<String> catKeyset;
    private Context mContext;

    public NetflixMainRecyclerAdapter(HashMap<String, ArrayList<TTEvent>> catNameToList, Context mContext){
        this.catNameToList = catNameToList;
        catKeyset = new ArrayList<>();
        catKeyset.addAll(catNameToList.keySet());
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.netflix_main_item_layout, parent, false);
        NetflixMainItemHolder nmih = new NetflixMainItemHolder(v);
        return nmih;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NetflixMainItemHolder curItem = (NetflixMainItemHolder) holder;
        TextView curItemTitle = curItem.titleText;
        RecyclerView curSubitemRecycler = curItem.subitemRecycler;

        String curRawTitle = catKeyset.get(position);
        String curFormTitle = curRawTitle.substring(0, 1).toUpperCase() + curRawTitle.substring(1);
        curItemTitle.setText(curFormTitle);

        NetflixRecyclerAdapter netflixAdapter = new NetflixRecyclerAdapter(catNameToList.get(catKeyset.get(position)), mContext);
        curSubitemRecycler.setAdapter(netflixAdapter);
        curSubitemRecycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));


    }

    @Override
    public int getItemCount() {
        return catNameToList.size();
    }

    public class NetflixMainItemHolder extends RecyclerView.ViewHolder {
        TextView titleText;
        RecyclerView subitemRecycler;

        public NetflixMainItemHolder(View v){
            super(v);
            titleText = v.findViewById(R.id.netflix_main_recyler_title);
            subitemRecycler = v.findViewById(R.id.netflix_recycler_subitem);
        }
    }
}
