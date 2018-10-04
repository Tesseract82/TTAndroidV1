package com.example.redbaron.ttandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CategoriesRecyclerAdapter extends RecyclerView.Adapter {

    private static final String TAG = "CategoryRecyclerAdapter";

    private ArrayList<CategoryRow> rows = new ArrayList<>();
    private Context mContext;

    public CategoriesRecyclerAdapter(ArrayList<CategoryRow> rows, Context mContext){
        this.rows = rows;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row_layout, parent, false);
        final CategoriesRowHolder viewHolder = new CategoriesRowHolder(v);
        return viewHolder;
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryRow curRow = rows.get(position);
        final Category catLeft = curRow.getLeft();
        final Category catRight = curRow.getRight();
        final Category catCenter = curRow.getCenter();

        FrameLayout tileLeft = (FrameLayout) ((CategoriesRowHolder) holder).viewLeft;
        FrameLayout tileCenter = (FrameLayout) ((CategoriesRowHolder) holder).viewCenter;
        FrameLayout tileRight = (FrameLayout) ((CategoriesRowHolder) holder).viewRight;

        setUpTileBitmap(catLeft, tileLeft, position);
        setUpTileBitmap(catCenter, tileCenter, position);
        setUpTileBitmap(catRight, tileRight, position);

        tileLeft.setOnClickListener(catLeft.getCatClickCallback());
        if(catLeft.selected){
            tileLeft.getChildAt(1).setVisibility(View.VISIBLE);
        } else {
            tileLeft.getChildAt(1).setVisibility(View.INVISIBLE);
        }
        if(catCenter != null){
            tileCenter.setOnClickListener(catCenter.getCatClickCallback());
            if(catCenter.selected){
                tileCenter.getChildAt(1).setVisibility(View.VISIBLE);
            } else {
                tileCenter.getChildAt(1).setVisibility(View.INVISIBLE);
            }
        }
        if(catRight != null){
            tileRight.setOnClickListener(catRight.getCatClickCallback());
            if(catRight.selected){
                tileRight.getChildAt(1).setVisibility(View.VISIBLE);
            } else {
                tileRight.getChildAt(1).setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setUpTileBitmap(final Category curCat, FrameLayout curTile, int position){
        if(curCat.getCatBitmap() != null){
            ((ImageView) curTile.getChildAt(0)).setImageBitmap(curCat.getCatBitmap());
        } else {
            final int TMposition = position;
            Category.imagesRef.child(curCat.imageDir).getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    Bitmap oTile = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Bitmap sTile = Bitmap.createScaledBitmap(oTile, MainActivity.screen_width / 3, MainActivity.screen_width / 3, false);
                    oTile.recycle();
                    curCat.setCatBitmap(sTile);
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

    @Override
    public int getItemCount() {
        return rows.size() - 1;
    }

    public class CategoriesRowHolder extends RecyclerView.ViewHolder{

        View viewLeft;
        View viewCenter;
        View viewRight;

        public CategoriesRowHolder(View rowView){
            super(rowView);
            viewLeft = rowView.findViewById(R.id.tile_left);
            viewCenter = rowView.findViewById(R.id.tile_center);
            viewRight = rowView.findViewById(R.id.tile_right);
        }
    }
}
