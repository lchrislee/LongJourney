package com.lchrislee.longjourney.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.creatures.Monster;
import com.lchrislee.longjourney.model.creatures.Player;
import com.lchrislee.longjourney.utility.DataPersistence;

public class BattleEngageFragment extends BaseFragment {

    private static final String TAG = "BattleEngageFragment";

    private ImageView monsterImage;
    private TextView monsterHealthPrompt;
    private ProgressBar monsterHealth;
    private ProgressBar playerHealth;

    private Player player;
    private Monster monster;

    @Nullable
    @Override
    public View onCreateView(
        LayoutInflater inflater,
        @Nullable ViewGroup container,
        Bundle savedInstanceState
    ) {
        final View masterView = inflater.inflate(R.layout.fragment_battle_engage, container, false);

        monsterImage = masterView.findViewById(R.id.fragment_battle_engage_monster_image);
        monsterHealthPrompt
                = masterView.findViewById(R.id.fragment_battle_engage_monster_health_prompt);
        monsterHealth = masterView.findViewById(R.id.fragment_battle_engage_monster_health);
        playerHealth = masterView.findViewById(R.id.fragment_battle_engage_player_health);

        return masterView;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadMonster();
        loadPlayer();
        engageBattle();
    }

    private void loadMonster()
    {
        monster = DataPersistence.monster(getContext());
        monsterHealthPrompt.setText(getString(R.string.fragment_battle_engage_monster_health_prompt, monster.name()));
        monsterHealth.setMax(monster.maxHealth());
        monsterHealth.setProgress(monster.currentHealth());
        monsterHealth.setSecondaryProgress(monster.currentHealth());
    }

    private void loadPlayer()
    {
        player = DataPersistence.player(getContext());
        playerHealth.setProgress(player.currentHealth());
        playerHealth.setSecondaryProgress(player.currentHealth());
        playerHealth.setMax(player.maxHealth());
    }

    private void engageBattle()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int playerHealthMin = player.currentHealth();
                        int monsterHealthMin = monster.currentHealth();

                        while (playerHealthMin > 0 && monsterHealthMin > 0)
                        {
                            monsterHealthMin -= player.strength() - monster.defense();
                            if (monsterHealthMin <= 0)
                            {
                                break;
                            }
                            playerHealthMin -= monster.strength() - player.defense();
                        }
                        // TODO: Animate the outcome somehow!

                        boolean isPlayerWinner = monsterHealthMin <= 0;

                        DataPersistence.saveDidPlayerWin(getContext(), isPlayerWinner);
                        if (isPlayerWinner)
                        {
                            locationListener.updateLocation(DataPersistence.BATTLE_REWARD);
                        }
                        else
                        {
                            locationListener.updateLocation(DataPersistence.BATTLE_LOST);
                        }
                    }
                });
            }
        }).start();
    }

}
