package com.lchrislee.longjourney.fragments;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.creatures.Player;
import com.lchrislee.longjourney.utility.managers.DataManager;
import com.lchrislee.longjourney.utility.managers.StepCountManager;
import com.lchrislee.longjourney.utility.receivers.StepCountBroadcastReceiver;

public class TravelFragment extends LongJourneyBaseFragment
        implements StepCountManager.StepReceived
{

    private static final String TAG = "TRAVEL_FRAGMENT";

    private TextView playerDistance;

    private StepCountBroadcastReceiver stepBackground;
    private StepCountManager stepForeground;

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

        stepForeground = new StepCountManager(getContext(), this);
        registerForSteps();

        return masterView;
    }

    @Override
    public void onResume() {
        super.onResume();
        startForegroundSteps();
        OnStepReceived();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopForegroundSteps();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unregisterForSteps();
    }

    private void startForegroundSteps()
    {
        stepForeground.start();
    }

    private void stopForegroundSteps()
    {
        stepForeground.stop();
    }

    private void registerForSteps()
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);

        stepBackground = new StepCountBroadcastReceiver();
        getActivity().registerReceiver(stepBackground, filter);
    }

    private void unregisterForSteps()
    {
        getActivity().unregisterReceiver(stepBackground);
    }

    @Override
    public void OnStepReceived() {
        DataManager dm = DataManager.get();
        int distance = dm.loadDistanceToTown(getContext());
        if (distance == 0)
        {
            playerDistance.setText(R.string.fragment_travel_reached);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(500);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DataManager.get().enterTown(getContext());
                            changeFragmentListener.changeFragment(DataManager.TOWN);
                        }
                    });
                }
            }).start();
        }
        else
        {
            playerDistance.setText(String.valueOf(distance));
        }
    }

}
