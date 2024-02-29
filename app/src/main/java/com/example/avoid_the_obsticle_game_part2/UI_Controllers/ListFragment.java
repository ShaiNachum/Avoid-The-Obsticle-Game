package com.example.avoid_the_obsticle_game_part2.UI_Controllers;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.avoid_the_obsticle_game_part2.Adapters.GameAdapter;
import com.example.avoid_the_obsticle_game_part2.Adapters.GameAdapter;
import com.example.avoid_the_obsticle_game_part2.Interfaces.Callback_BackToOpeningClicked;
import com.example.avoid_the_obsticle_game_part2.Interfaces.Callback_highScoreClicked;
import com.example.avoid_the_obsticle_game_part2.Logic.RecordsManager;
import com.example.avoid_the_obsticle_game_part2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class ListFragment extends Fragment {
    private RecyclerView main_LST_games;
    private ShapeableImageView list_BTN_backToOpening;
    private MaterialTextView list_LBL_text;
    private Callback_highScoreClicked callbackHighScoreClicked;
    private Callback_BackToOpeningClicked callbackBackToOpeningClicked;


    public ListFragment() {
    }

    public void setCallbackHighScoreClicked(Callback_highScoreClicked callbackHighScoreClicked) {
        this.callbackHighScoreClicked = callbackHighScoreClicked;
    }

    public void setBackToOpeningClicked(Callback_BackToOpeningClicked callbackBackToOpeningClicked) {
        this.callbackBackToOpeningClicked = callbackBackToOpeningClicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        findViews(view);
        initViews();

        list_BTN_backToOpening.setOnClickListener(v -> backToOpeningClicked());

        return view;
    }

    private void backToOpeningClicked() {
        if(callbackBackToOpeningClicked != null)
            callbackBackToOpeningClicked.backToOpeningClicked();
    }

    private void initViews() {
        GameAdapter gameAdapter = new GameAdapter(this.getContext(), RecordsManager.getGamesArrayList());
        gameAdapter.setCallbackHighScoreClicked(callbackHighScoreClicked);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_games.setLayoutManager(linearLayoutManager);
        main_LST_games.setAdapter(gameAdapter);

    }

    private void findViews(View view) {
        list_BTN_backToOpening = view.findViewById(R.id.list_BTN_backToOpening);
        main_LST_games = view.findViewById(R.id.main_LST_games);
        list_LBL_text = view.findViewById(R.id.list_LBL_text);
    }
}