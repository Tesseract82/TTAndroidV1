package com.example.redbaron.ttandroid;

import android.graphics.Bitmap;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TTEvent {

    public String contactemail;
    public String contactphone;
    public String eventname;
    public String location;
    public String description;
    public String thumbpath;

    private Bitmap eventBitmap;
    public static StorageReference imagesRef;

    static {
        imagesRef = FirebaseStorage.getInstance().getReference().child("event_thumb_images");
    }

    public TTEvent(){
//        this.contactemail = contactemail;
//        this.contactphone = contactphone;
//        this.eventname = eventname;
//        this.location = location;
//        this.description = description;
//        this.thumbpath = thumbpath;
    }

    public Bitmap getEventBitmap(){
        return eventBitmap;
    }

    public void setEventBitmap(Bitmap eventBitmap) {
        this.eventBitmap = eventBitmap;
    }
}
