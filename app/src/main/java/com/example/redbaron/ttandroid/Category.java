package com.example.redbaron.ttandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Category {
    public String imageDir;
    public String categoryName;
    public Boolean selected = false;
    public static ArrayList<Category> selectedCats = new ArrayList<>();
    private View.OnClickListener catClickCallback;
    private Bitmap catBitmap;
    public static StorageReference imagesRef;
    public static int numCatsLoaded;

    static {
        imagesRef = FirebaseStorage.getInstance().getReference().child("tiles_images");
    }

    public Category(String imageDir, String categoryName, final Boolean selected){
        this.imageDir = imageDir;
        this.categoryName = categoryName;
        this.selected = selected;
        final Category selfCat = this;
        this.catClickCallback = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selfCat.selected = !selfCat.selected;
                Log.i(selfCat.categoryName, String.valueOf(selfCat.selected));
                if(selfCat.selected){
                    Category.selectedCats.add(selfCat);
                    ((FrameLayout) v).getChildAt(1).setVisibility(View.VISIBLE);
                } else {
                    Category.selectedCats.remove(selfCat);
                    ((FrameLayout) v).getChildAt(1).setVisibility(View.INVISIBLE);
                }
                if(Category.selectedCats.size() != 0){
                    CategoriesFragment.gobutton.setVisibility(View.VISIBLE);
                } else {
                    CategoriesFragment.gobutton.setVisibility(View.GONE);
                }
            }
        };
    }

    public View.OnClickListener getCatClickCallback() {
        return catClickCallback;
    }

    public Bitmap getCatBitmap() {
        return catBitmap;
    }

    public void setCatBitmap(Bitmap catBitmap) {
        this.catBitmap = catBitmap;
    }
}
