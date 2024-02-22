package com.example.avoid_the_obsticle_game_part2.UI_Controllers;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.bumptech.glide.Glide;
import com.example.avoid_the_obsticle_game_part2.R;
import com.example.avoid_the_obsticle_game_part2.Utilities.SignalManager;
import com.google.android.material.imageview.ShapeableImageView;

public class OpeningActivity extends AppCompatActivity {
    private static final int SMALL_VIBRATE = 50;
    private ShapeableImageView main_IMG_background;
    private EditText opening_LBL_name;
    private ShapeableImageView opening_BTN_start;
    private ShapeableImageView opening_BTN_leaderboard;
    private SwitchCompat opening_SWC_tilt;
    private RadioGroup opening_RG_mode;
    private RadioButton radioButton;
    private Button opening_BTN_apply;
    private boolean fastMode = false;
    private boolean slowMode = true;
    private boolean tiltMode;
    private String name;
    private int radioID;


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

        this.name = opening_LBL_name.getText().toString();

        opening_SWC_tilt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tiltMode =isChecked;
            }
        });

        opening_BTN_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton = findViewById(radioID);

                if(radioButton.getText().toString().equals("opening_RBTN_fast")){
                    fastMode = true;
                    slowMode = false;
                }
            }
        });

        opening_BTN_start.setOnClickListener(View -> startClicked());

    }

    private void leaderBoardClicked() {
        SignalManager.getInstance().vibrate(SMALL_VIBRATE);

    }

    private void startClicked() {
        SignalManager.getInstance().vibrate(SMALL_VIBRATE);





    }

    public void checkButton(View v){
        this.radioID = opening_RG_mode.getCheckedRadioButtonId();
        radioButton = findViewById(this.radioID);
    }



    public void setTiltMode(boolean tiltMode) {
        this.tiltMode = tiltMode;
    }


    private void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);
        opening_BTN_start = findViewById(R.id.opening_BTN_start);
        opening_LBL_name = findViewById(R.id.opening_LBL_name);
        opening_SWC_tilt = findViewById(R.id.opening_SWC_tilt);
        opening_RG_mode = findViewById(R.id.opening_RG_mode);
        opening_BTN_apply = findViewById(R.id.opening_BTN_apply);
        opening_BTN_leaderboard = findViewById(R.id.opening_BTN_leaderboard);
    }
}
