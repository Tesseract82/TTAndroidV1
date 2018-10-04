package com.example.redbaron.ttandroid;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class CategoriesFragment extends Fragment {

    private ArrayList<CategoryRow> catRows;
    private StorageReference tilesRef;

    public static ImageView gobutton;

    private OnFragmentInteractionListener mListener;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categories, container, false);

        gobutton = v.findViewById(R.id.gobutton);
        enableGoButton();

        catRows = new ArrayList<>();

        tilesRef = FirebaseStorage.getInstance().getReference().child("tiles_images");
        fetchTileList(v);

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void initCategoriesRecycler(View v){
        RecyclerView categoriesRecycler = v.findViewById(R.id.tt_categories_list);
        CategoriesRecyclerAdapter adapter = new CategoriesRecyclerAdapter(catRows, v.getContext());
        categoriesRecycler.setAdapter(adapter);
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(v.getContext()));
    }

    private void fetchTileList(View v){
        final long ONE_MEGABYTE = 1024 * 1024;
        final View vf = v;
        tilesRef.child("tile_index.txt").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                try {
                    String imgList = new String(bytes, "UTF-8");
                    ArrayList<String> tilesLocations = new ArrayList<String>(Arrays.asList(imgList.split("\n")));
                    for(int n = 0; n < tilesLocations.size(); n+=3){
                        Category catLeft = new Category(tilesLocations.get(n), tilesLocations.get(n).replace(".png", ""), false);
                        Category catCenter = null;
                        Category catRight = null;
                        if(n + 1 < tilesLocations.size()){
                            catCenter = new Category(tilesLocations.get(n + 1), tilesLocations.get(n + 1).replace(".png", ""), false);
                        }
                        if(n + 2 < tilesLocations.size()){
                            catRight = new Category(tilesLocations.get(n + 2), tilesLocations.get(n + 2).replace(".png", ""), false);
                        }
                        CategoryRow newCatRow = new CategoryRow(catLeft, catCenter, catRight);
                        catRows.add(newCatRow);
                    }
                    initCategoriesRecycler(vf);
                } catch(UnsupportedEncodingException uee){
                    uee.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Handle Errors
            }
        });
    }

    private void enableGoButton(){
        final Activity mnAct = getActivity();
        gobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mnAct).goButtonNextFragment();
            }
        });
    }
}
