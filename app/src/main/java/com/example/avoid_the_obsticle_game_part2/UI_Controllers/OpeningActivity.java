package com.example.avoid_the_obsticle_game_part2.UI_Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.bumptech.glide.Glide;
import com.example.avoid_the_obsticle_game_part2.R;
import com.example.avoid_the_obsticle_game_part2.Utilities.SignalManager;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class OpeningActivity extends AppCompatActivity {
    private static final int SMALL_VIBRATE = 50;
    private ShapeableImageView main_IMG_background;
    private MaterialTextView opening_LBL_text;
    private EditText opening_LBL_name;
    private ShapeableImageView opening_BTN_start;
    private ShapeableImageView opening_BTN_leaderboard;
    private SwitchCompat opening_SWC_tilt;
    private SwitchCompat opening_SWC_fast;
    private boolean fastMode;
    private boolean tiltMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        findViews();

        Glide
                .with(this)
                .load(R.drawable.space)
                .centerCrop()
                .placeholder(R.drawable.space)
                .into(main_IMG_background);


        opening_BTN_leaderboard.setOnClickListener(View -> leaderBoardClicked());

        opening_SWC_tilt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tiltMode =isChecked;
            }
        });

        opening_SWC_fast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fastMode = isChecked;
            }
        });

        opening_BTN_start.setOnClickListener(View -> startClicked());
    }

    private void leaderBoardClicked() {
        SignalManager.getInstance().vibrate(SMALL_VIBRATE);
        Intent intent = new Intent(OpeningActivity.this, RecordsActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void startClicked() {
        SignalManager.getInstance().vibrate(SMALL_VIBRATE);
        String playerName = opening_LBL_name.getText().toString();

        Intent intent = new Intent(OpeningActivity.this, GameActivity.class);
        intent.putExtra("playerName", playerName);
        intent.putExtra("gameSpeed", fastMode);
        intent.putExtra("tiltMode", tiltMode);

        startActivity(intent);
        this.finish();
    }

    private void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);
        opening_BTN_start = findViewById(R.id.opening_BTN_start);
        opening_LBL_name = findViewById(R.id.opening_LBL_name);
        opening_SWC_tilt = findViewById(R.id.opening_SWC_tilt);
        opening_SWC_fast = findViewById(R.id.opening_SWC_fast);
        opening_BTN_leaderboard = findViewById(R.id.opening_BTN_leaderboard);
        opening_LBL_text = findViewById(R.id.opening_LBL_text);
    }
}