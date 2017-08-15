package com.lchrislee.longjourney.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lchrislee.longjourney.R;

public class RestFragment extends LongJourneyBaseFragment
        implements MenuItem.OnMenuItemClickListener
{

    private TextView timeRemaining;
    private ProgressBar health;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View masterView = inflater.inflate(R.layout.fragment_rest, container, false);
        final WearableActionDrawerView drawerView
                = masterView.findViewById(R.id.fragment_rest_action_drawer);
        drawerView.setOnMenuItemClickListener(this);
        drawerView.getController().peekDrawer();

        timeRemaining = masterView.findViewById(R.id.fragment_rest_time);
        health = masterView.findViewById(R.id.fragment_rest_player_health);

        return masterView;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        // TODO: Move to travel and stop regenerating health.
        return true;
    }
}