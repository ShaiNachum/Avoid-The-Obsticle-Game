package com.example.avoid_the_obsticle_game_part2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avoid_the_obsticle_game_part2.Interfaces.GameCallback;
import com.example.avoid_the_obsticle_game_part2.Models.Game;
import com.example.avoid_the_obsticle_game_part2.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {
    private Context context;
    private ArrayList<Game> games;
    private GameCallback gameCallback;

    public GameAdapter(Context context, ArrayList<Game> games) {
        this.context = context;
        this.games = games;
    }

    public GameAdapter setGameCallback(GameCallback gameCallback) {
        this.gameCallback = gameCallback;
        return this;
    }

    @NonNull
    @Override
    public GameAdapter.GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_game_item, parent, false);
        GameViewHolder gameViewHolder = new GameViewHolder(view);
        return gameViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GameAdapter.GameViewHolder holder, int position) {
        Game game = getItem(position);
        holder.game_LBL_playerName.setText("Name: " + game.getPlayerName());
        holder.game_LBL_playerScore.setText("Score: " + String.valueOf(game.getScore()));
        holder.game_LBL_playerPlace.setText("#" + String.valueOf((game.getPlace() + 1)));
    }

    @Override
    public int getItemCount() {
        return games == null ? 0 : games.size();
    }

    private Game getItem(int position){
        return games.get(position);
    }

    public class GameViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView game_LBL_playerName;
        private MaterialTextView game_LBL_playerScore;
        private MaterialTextView game_LBL_playerPlace;


        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            game_LBL_playerName = itemView.findViewById(R.id.game_LBL_playerName);
            game_LBL_playerScore = itemView.findViewById(R.id.game_LBL_playerScore);
            game_LBL_playerPlace = itemView.findViewById(R.id.game_LBL_playerPlace);
        }
    }
}
