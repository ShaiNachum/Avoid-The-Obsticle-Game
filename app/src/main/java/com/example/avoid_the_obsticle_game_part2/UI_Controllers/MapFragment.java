package com.example.avoid_the_obsticle_game_part2.UI_Controllers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.avoid_the_obsticle_game_part2.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.textview.MaterialTextView;


public class MapFragment extends Fragment {

    private GoogleMap mMap;

    //private MaterialTextView map_LBL_title;


    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        initViews();

        return view;
    }

    private void initViews() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_FRG_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
            }
        });

    }



//    public void zoom(double lat, double lon){
//        map_LBL_title.setText(lat + "\n" + lon);
//    }
}