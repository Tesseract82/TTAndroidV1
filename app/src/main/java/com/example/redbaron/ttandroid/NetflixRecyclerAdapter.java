package com.example.redbaron.ttandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class NetflixRecyclerAdapter extends RecyclerView.Adapter{

    private ArrayList<TTEvent> eventList;
    private Context mContext;

    public NetflixRecyclerAdapter(ArrayList<TTEvent> eventList, Context mContext){
        this.eventList = eventList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.netflix_item_layout, parent, false);
        NetflixSubitemHolder nshv = new NetflixSubitemHolder(v);
        return nshv;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        setUpEventThumbBitmap(eventList.get(position), ((NetflixSubitemHolder) holder).thumbnail, position);
        ((NetflixSubitemHolder) holder).thumbnail.setImageBitmap(eventList.get(position).getEventBitmap());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    private void setUpEventThumbBitmap(final TTEvent curEvent, ImageView curThumbnail, int position){
        if(curEvent.getEventBitmap() != null){
            curThumbnail.setImageBitmap(curEvent.getEventBitmap());
        } else {
            final int TMposition = position;
            TTEvent.imagesRef.child(curEvent.thumbpath).getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap oThumb = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Bitmap sThumb = Bitmap.createScaledBitmap(oThumb, MainActivity.screen_width / 2, MainActivity.screen_width / 2, false);
                    oThumb.recycle();
                    curEvent.setEventBitmap(sThumb);
                    notifyItemChanged(TMposition);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public class NetflixSubitemHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;

        public NetflixSubitemHolder(View v){
            super(v);
            this.thumbnail = v.findViewById(R.id.netflix_subitem_thumb);
        }
    }
}
