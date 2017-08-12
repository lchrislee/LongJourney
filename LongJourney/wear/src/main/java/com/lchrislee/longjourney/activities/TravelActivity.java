package com.lchrislee.longjourney.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.utility.managers.DataManager;
import com.lchrislee.longjourney.utility.managers.StepManager;
import com.lchrislee.longjourney.model.creatures.Player;

public class TravelActivity extends LongJourneyBaseActivity
        implements StepManager.StepReceived
{

    private static final String TAG = "TRAVEL_ACTIVITY";

    private TextView playerDistance;

//    private StepManager stepManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        playerDistance = findViewById(R.id.activity_travel_distance);
        final ProgressBar playerHealth = findViewById(R.id.activity_travel_player_health);
        final ProgressBar playerExperience = findViewById(R.id.activity_travel_player_experience);

        DataManager dm = DataManager.get();

        final Player player = dm.getPlayer(getApplicationContext());
        playerHealth.setProgress(player.getCurrentHealth());
        playerHealth.setMax(player.getMaxHealth());
        playerExperience.setMax(player.getExperienceForNextLevel());
        playerExperience.setProgress(player.getCurrentExperience());

//        stepManager = new StepManager(getApplicationContext(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        OnStepReceived();
//        stepManager.registerSensor(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
//        stepManager.unregisterSensor();
    }

    @Override
    public void OnStepReceived() {
//        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        if (pm.isInteractive()) {
            DataManager dm = DataManager.get();
            playerDistance.setText(String.valueOf(dm.loadDistanceToTown(getApplicationContext())));
//        }
    }
}
