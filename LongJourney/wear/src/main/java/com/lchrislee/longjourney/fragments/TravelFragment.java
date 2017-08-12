package com.lchrislee.longjourney.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.creatures.Player;
import com.lchrislee.longjourney.utility.managers.DataManager;
import com.lchrislee.longjourney.utility.managers.StepManager;

public class TravelFragment extends LongJourneyBaseFragment
        implements StepManager.StepReceived
{

    private static final String TAG = "TRAVEL_FRAGMENT";

    private TextView playerDistance;

//    private StepManager stepManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        final View masterView = inflater.inflate(R.layout.fragment_travel, container, false);
        playerDistance = masterView.findViewById(R.id.activity_travel_distance);
        final ProgressBar playerHealth = masterView.findViewById(R.id.activity_travel_player_health);
        final ProgressBar playerExperience
                = masterView.findViewById(R.id.activity_travel_player_experience);

        DataManager dm = DataManager.get();

        final Player player = dm.getPlayer(getContext());
        playerHealth.setProgress(player.getCurrentHealth());
        playerHealth.setMax(player.getMaxHealth());
        playerExperience.setMax(player.getExperienceForNextLevel());
        playerExperience.setProgress(player.getCurrentExperience());

//        stepManager = new StepManager(getApplicationContext(), this);
        return masterView;
    }

    @Override
    public void onResume() {
        super.onResume();
        OnStepReceived();
//        stepManager.registerSensor(getApplicationContext());
    }

    @Override
    public void onPause() {
        super.onPause();
//        stepManager.unregisterSensor();
    }

    @Override
    public void OnStepReceived() {
//        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        if (pm.isInteractive()) {
            DataManager dm = DataManager.get();
            playerDistance.setText(String.valueOf(dm.loadDistanceToTown(getContext())));
//        }
    }
}
