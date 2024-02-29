package com.example.avoid_the_obsticle_game_part2.UI_Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.avoid_the_obsticle_game_part2.Interfaces.Callback_BackToOpeningClicked;
import com.example.avoid_the_obsticle_game_part2.Interfaces.Callback_highScoreClicked;
import com.example.avoid_the_obsticle_game_part2.R;

public class RecordsActivity extends AppCompatActivity {
    private FrameLayout records_FRAME_list;
    private FrameLayout records_FRAME_map;
    private MapFragment mapFragment;
    private ListFragment listFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        findViews();

        listFragment = new ListFragment();
        listFragment.setCallbackHighScoreClicked(new Callback_highScoreClicked() {
            @Override
            public void highScoreClicked(double lat, double lon) {
                mapFragment.zoom(lat, lon);
            }
        });

        listFragment.setBackToOpeningClicked(new Callback_BackToOpeningClicked() {
            @Override
            public void backToOpeningClicked() {
                Intent intent = new Intent(RecordsActivity.this, OpeningActivity.class);
                startActivity(intent);
            }
        });

        mapFragment = new MapFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.records_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.records_FRAME_map, mapFragment).commit();

    }

    private void findViews() {
        records_FRAME_list = findViewById(R.id.records_FRAME_list);
        records_FRAME_map = findViewById(R.id.records_FRAME_map);
    }
}
