package com.example.avoid_the_obsticle_game_part2.UI_Controllers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.avoid_the_obsticle_game_part2.Interfaces.Callback_highScoreClicked;
import com.example.avoid_the_obsticle_game_part2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;


public class ListFragment extends Fragment {
    private MaterialTextView list_LBL_title;
    private MaterialButton list_LBL_send;
    private Callback_highScoreClicked callbackHighScoreClicked;


    public ListFragment() {
    }

    public void setCallbackHighScoreClicked(Callback_highScoreClicked callbackHighScoreClicked) {
        this.callbackHighScoreClicked = callbackHighScoreClicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        findViews(view);

        list_LBL_send.setOnClickListener(v -> itemClicked(23.23423, 76.42344));

        return view;
    }

    private void itemClicked(double lat, double lon) {
        if(callbackHighScoreClicked != null)
            callbackHighScoreClicked.highScoreClicked(lat, lon);
    }

    private void findViews(View view) {
        list_LBL_title = view.findViewById(R.id.list_LBL_title);
        list_LBL_send = view.findViewById(R.id.list_LBL_send);
    }
}