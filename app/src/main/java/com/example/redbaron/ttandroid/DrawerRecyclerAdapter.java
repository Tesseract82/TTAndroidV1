package com.example.redbaron.ttandroid;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DrawerRecyclerAdapter extends RecyclerView.Adapter{

    private static final String TAG = "DrawerRecyclerAdapter";

    private ArrayList<String> settingsSubitemTitles = new ArrayList<>();
    private ArrayList<String> settingsSubitemDescriptions = new ArrayList<>();
    private ArrayList<String> settingsSubitemLinks = new ArrayList<>();
    private Context mContext;

    public DrawerRecyclerAdapter(ArrayList<String> titles, ArrayList<String> descriptions,
                                 ArrayList<String> links, Context mContext){
        settingsSubitemDescriptions = descriptions;
        settingsSubitemLinks = links;
        settingsSubitemTitles = titles;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public SettingsItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_layout, parent, false);
        SettingsItemHolder vHolder = new SettingsItemHolder(v);
        return vHolder;
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        ((SettingsItemHolder) holder).RHSViewContainer.removeAllViews();
        ((SettingsItemHolder) holder).subitemTitle.setTextSize(20);
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        //Makes first item (username) bigger
        if(settingsSubitemTitles.get(position).equals("Username")){
            ((SettingsItemHolder) holder).subitemTitle.setTextSize(28);
        }

        ((SettingsItemHolder) holder).subitemDescription.setText(settingsSubitemDescriptions.get(position));
        ((SettingsItemHolder) holder).subitemTitle.setText(settingsSubitemTitles.get(position));

        FrameLayout rightViewContainer = ((SettingsItemHolder) holder).RHSViewContainer;
        FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        flp.gravity = Gravity.CENTER;
        if(settingsSubitemLinks.get(position).equals("Switch")){
            Switch RHSswitch = new Switch(mContext);
            RHSswitch.setChecked(false);
            RHSswitch.setLayoutParams(flp);
            rightViewContainer.addView(RHSswitch);
        } else if(settingsSubitemLinks.get(position).equals("Username")) {
            ImageView RHSavatar = new ImageView(mContext);
            RHSavatar.setImageResource(R.drawable.fullsizerender);
            RHSavatar.setAdjustViewBounds(true);
            RHSavatar.setLayoutParams(flp);
            rightViewContainer.addView(RHSavatar);
        } else {
            //TODO: links components below
//            TextView RHStext = new TextView(mContext);
//            RHStext.setText(settingsSubitemLinks.get(position));
//            RHStext.setTextSize(20);
//            RHStext.setTextColor(Color.parseColor("#1348c4"));
//            RHStext.setLayoutParams(flp);
//            RHStext.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, ((SettingsItemHolder) holder).subitemTitle.getText().toString(), Toast.LENGTH_SHORT).show();
//                }
//            });
//            rightViewContainer.addView(RHStext);
        }
    }

    @Override
    public int getItemCount() {
        return settingsSubitemTitles.size();
    }

    public class SettingsItemHolder extends RecyclerView.ViewHolder{

        TextView subitemTitle;
        TextView subitemDescription;
        LinearLayout parentLayout;
        FrameLayout RHSViewContainer;

        public SettingsItemHolder(View itemView) {
            super(itemView);
            subitemTitle = itemView.findViewById(R.id.drawer_subitem_name);
            subitemDescription = itemView.findViewById(R.id.drawer_subitem_description);
            parentLayout = itemView.findViewById(R.id.drawer_parent);
            RHSViewContainer = itemView.findViewById(R.id.frame_switch_text);
        }
    }
}
