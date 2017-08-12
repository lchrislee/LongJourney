package com.lchrislee.longjourney.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.managers.PersistenceManager;
import com.lchrislee.longjourney.model.creatures.Player;

public class TravelActivity extends Activity {

    private TextView playerDistance;
    private ProgressBar playerHealth;
    private ProgressBar playerExperience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);
        playerDistance = findViewById(R.id.activity_travel_distance);
        playerHealth = findViewById(R.id.activity_travel_player_health);
        playerExperience = findViewById(R.id.activity_travel_player_experience);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Player player = PersistenceManager.get().getPlayer(getApplicationContext());
        playerDistance.setText(String.valueOf(player.getCurrentDistanceToTown()));
        playerHealth.setProgress(player.getCurrentHealth());
        playerHealth.setMax(player.getMaxHealth());
        playerExperience.setMax(player.getExperienceForNextLevel());
        playerExperience.setProgress(player.getCurrentExperience());
    }
}
