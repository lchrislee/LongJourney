package com.lchrislee.longjourney.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.utility.DataPersistence;

public class BattleLossFragment extends BaseFragment implements MenuItem.OnMenuItemClickListener {

    @Nullable
    @Override
    public View onCreateView(
        LayoutInflater inflater,
        @Nullable ViewGroup container,
        Bundle savedInstanceState
    ) {
        final View masterView = inflater.inflate(R.layout.fragment_battle_loss, container, false);

        final WearableActionDrawerView drawerView
                = masterView.findViewById(R.id.fragment_battle_loss_action_drawer);
        drawerView.setOnMenuItemClickListener(this);
        drawerView.getController().peekDrawer();

        return masterView;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        DataPersistence.completeBattle(getContext());
        locationListener.updateLocation(DataPersistence.REST);
        return true;
    }
}
