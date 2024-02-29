package com.example.avoid_the_obsticle_game_part2.UI_Controllers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.example.avoid_the_obsticle_game_part2.R;
import com.example.avoid_the_obsticle_game_part2.Utilities.PermissionsManager;
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
    private PermissionsManager permissionsManager;
    private Location location;


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
                tiltMode = isChecked;
            }
        });

        opening_SWC_fast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fastMode = isChecked;
            }
        });

        opening_BTN_start.setOnClickListener(View -> startClicked());

        permissionsManager = new PermissionsManager(this);
        permissionsManager.setLocationRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            permissionsManager.setLastLocation();
            permissionsManager.checkSettingsAndStartLocationUpdates();

        }else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10001);
                }else{
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10001);
                }
            }
        }
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


        location = permissionsManager.getLast_location();

        if(location == null) {
            location = new Location("");
            location.setLatitude(40.730610);
            location.setLongitude(-73.935242);
        }

        Log.d("a123", location + "");

        Intent intent = new Intent(OpeningActivity.this, GameActivity.class);
        intent.putExtra("playerName", playerName);
        intent.putExtra("gameSpeed", fastMode);
        intent.putExtra("tiltMode", tiltMode);
        intent.putExtra("location", location);

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