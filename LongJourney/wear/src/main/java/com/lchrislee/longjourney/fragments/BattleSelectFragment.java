package com.lchrislee.longjourney.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.utility.managers.DataManager;

public class BattleSelectFragment extends LongJourneyBaseFragment implements MenuItem.OnMenuItemClickListener {

    private static final String TAG = "BATTLE_SELECT_FRAGMENT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        final View masterView = inflater.inflate(R.layout.fragment_battle_select, container, false);

        final WearableActionDrawerView actionDrawerView
                = masterView.findViewById(R.id.fragment_battle_select_action_drawer);
        actionDrawerView.setOnMenuItemClickListener(this);
        actionDrawerView.getController().peekDrawer();

        final ImageView monster = masterView.findViewById(R.id.fragment_battle_select_monster);

        return masterView;
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        @DataManager.PlayerLocation int location;
        switch(menuItem.getItemId())
        {
            case R.id.menu_battle_select_sneak:
                location = DataManager.SNEAK;
                break;
            case R.id.menu_battle_select_run:
                location = DataManager.RUN;
                break;
            default:
                location = DataManager.BATTLE;
                break;
        }

        DataManager.get().changeLocation(getContext(), location);

        return true;
    }
}
