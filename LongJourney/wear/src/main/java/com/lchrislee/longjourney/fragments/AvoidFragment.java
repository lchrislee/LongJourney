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
import com.lchrislee.longjourney.utility.managers.DataManager;

public class AvoidFragment extends LongJourneyBaseFragment implements MenuItem.OnMenuItemClickListener {

    private static final String TAG = "Avoid_Fragment";

    private boolean isSuccessful;
    private boolean isShowingTitle;
    private int menuToInflate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSuccessful = DataManager.get().getAvoidSuccess(getContext());

        if (isSuccessful) {
            menuToInflate = R.menu.menu_avoid_success;
            isShowingTitle = (DataManager.get().loadLocation(getContext()) == DataManager.RUN);
        }
        else
        {
            menuToInflate = R.menu.menu_avoid_caught;
            isShowingTitle = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View masterView = inflater.inflate(R.layout.fragment_avoid, container, false);

        TextView text = masterView.findViewById(R.id.fragment_avoid_text);
        if (isShowingTitle)
        {
            TextView title = masterView.findViewById(R.id.fragment_avoid_title);
            title.setVisibility(View.VISIBLE);
            int distanceLost = DataManager.get().loseDistanceTraveled(getContext());
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

        getActivity().getMenuInflater().inflate(menuToInflate, menu);
        drawerView.getController().peekDrawer();

        return masterView;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        DataManager.get().clearAvoidSuccess(getContext());
        switch(menuItem.getItemId())
        {
            case R.id.menu_avoid_caught:
                changeFragmentListener.changeFragment(DataManager.BATTLE);
                return true;
            case R.id.menu_avoid_success:
                changeFragmentListener.changeFragment(DataManager.TRAVEL);
                return true;
        }
        return true;
    }
}
