package com.lchrislee.longjourney.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.utility.DataPersistence;

public class AvoidFragment extends BaseFragment implements MenuItem.OnMenuItemClickListener {

    private static final String TAG = "AvoidFragment";

    private boolean isSuccessful;
    private boolean isShowingTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSuccessful = DataPersistence.isAvoidSuccessful(getContext());

        isShowingTitle = isSuccessful
            && (DataPersistence.currentLocation(getContext()) == DataPersistence.RUN);
    }

    @Nullable
    @Override
    public View onCreateView(
        LayoutInflater inflater,
        @Nullable ViewGroup container,
        Bundle savedInstanceState
    ) {
        final View masterView = inflater.inflate(R.layout.fragment_avoid, container, false);

        TextView text = masterView.findViewById(R.id.fragment_avoid_text);
        if (isShowingTitle)
        {
            TextView title = masterView.findViewById(R.id.fragment_avoid_title);
            title.setVisibility(View.VISIBLE);

            final int expectedDistance = DataPersistence.totalTownDistance(getContext());
            final int distanceLost = (int) (expectedDistance * 0.2f);
            DataPersistence.decreaseDistanceWalked(getContext(), distanceLost);
            text.setText(getString(R.string.fragment_avoid_run_backtrack, distanceLost));
        }
        else if (isSuccessful)
        {
            text.setText(R.string.fragment_avoid_success);
        }
        else
        {
            text.setText(R.string.fragment_avoid_caught);
        }

        final WearableActionDrawerView drawerView
                = masterView.findViewById(R.id.fragment_avoid_action_drawer);
        drawerView.setOnMenuItemClickListener(this);
        Menu menu = drawerView.getMenu();

        final int menuToInflate
            = isSuccessful ? R.menu.menu_avoid_success : R.menu.menu_avoid_caught;

        getActivity().getMenuInflater().inflate(menuToInflate, menu);
        drawerView.getController().peekDrawer();

        return masterView;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        DataPersistence.resetAvoidSuccess(getContext());
        switch(menuItem.getItemId())
        {
            case R.id.menu_avoid_caught:
                changeFragmentListener.changeFragment(DataPersistence.BATTLE);
                return true;
            case R.id.menu_avoid_success:
                changeFragmentListener.changeFragment(DataPersistence.TRAVEL);
                return true;
        }
        return true;
    }
}
