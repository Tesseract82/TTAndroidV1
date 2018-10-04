package com.example.redbaron.ttandroid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class NetflixFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<Category> selectedCats;
    private HashMap<String, ArrayList<TTEvent>> catToEvents;
    private DatabaseReference mDatabase;

    public NetflixFragment() {
        // Required empty public constructor
    }

    public static NetflixFragment newInstance(ArrayList<Category> selectedCats) {
        NetflixFragment fragment = new NetflixFragment();
        fragment.catToEvents = new HashMap<>();
        fragment.mDatabase = FirebaseDatabase.getInstance().getReference().child("events");
        fragment.selectedCats = selectedCats;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_netflix, container, false);
        final int end = selectedCats.size();
        Category.numCatsLoaded = 0;

        for(Category c : selectedCats){
            final String tcatName = c.categoryName.toLowerCase();
            mDatabase.child(tcatName).child("events").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<TTEvent> curCatEvents = new ArrayList<>();
                    for(DataSnapshot eventDocSnapshot : dataSnapshot.getChildren()){
                        TTEvent newT = eventDocSnapshot.getValue(TTEvent.class);
                        Log.i("NEWT", newT.eventname);
                        curCatEvents.add(newT);
                    }
                    catToEvents.put(tcatName, curCatEvents);
                    Category.numCatsLoaded++;
                    if(Category.numCatsLoaded == end) {
                        RecyclerView netflixRecycler = v.findViewById(R.id.netflix_main_recycler);
                        NetflixMainRecyclerAdapter netflixAdapter = new NetflixMainRecyclerAdapter(catToEvents, getContext());
                        netflixRecycler.setAdapter(netflixAdapter);
                        netflixRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //Handle Errors
                }
            });
        }



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
}
