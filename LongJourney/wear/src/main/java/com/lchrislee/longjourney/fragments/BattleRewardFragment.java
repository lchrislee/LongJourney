package com.lchrislee.longjourney.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.creatures.Monster;
import com.lchrislee.longjourney.model.creatures.Player;
import com.lchrislee.longjourney.utility.managers.DataManager;

public class BattleRewardFragment extends BaseFragment
        implements MenuItem.OnMenuItemClickListener
{

    private TextView gold;
    private ProgressBar experience;

    private Player player;
    private boolean canContinue;
    private int endGold;
    private int endExperience;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        canContinue = false;

        final DataManager dm = DataManager.get();
        final Monster monster = dm.getMonster(getContext());
        player = dm.getPlayer(getContext());
        endGold = player.goldCarried() + monster.goldCarried();
        endExperience = player.currentExperience() + monster.currentExperience();
    }

    @Nullable
    @Override
    public View onCreateView(
        LayoutInflater inflater,
        @Nullable ViewGroup container,
        Bundle savedInstanceState
    ) {
        final View masterView = inflater.inflate(R.layout.fragment_battle_reward, container, false);
        final WearableActionDrawerView drawerView
                = masterView.findViewById(R.id.fragment_battle_reward_action_drawer);
        drawerView.setOnMenuItemClickListener(this);
        drawerView.getController().peekDrawer();

        gold = masterView.findViewById(R.id.fragment_battle_reward_gold);
        gold.setText(player.goldCarried());

        experience = masterView.findViewById(R.id.fragment_battle_reward_experience);
        experience.setMax(player.getExperienceForNextLevel());
        experience.setProgress(player.currentExperience());
        experience.setSecondaryProgress(endExperience);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(500);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gold.setText(endGold);
                        experience.setProgress(endExperience);
                        canContinue = true;
                    }
                });
            }
        }).start();

        return masterView;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (canContinue)
        {
            DataManager.get().completeBattleSideEffects(getContext());
            changeFragmentListener.changeFragment(DataManager.TRAVEL);
        }
        return true;
    }
}
