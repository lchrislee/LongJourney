package com.lchrislee.longjourney.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.creatures.Monster;
import com.lchrislee.longjourney.model.creatures.Player;
import com.lchrislee.longjourney.utility.managers.DataManager;

public class BattleEngageFragment extends LongJourneyBaseFragment {

    private static final String TAG = "BattleEngageFragment";

    private ImageView monsterImage;
    private TextView monsterHealthPrompt;
    private ProgressBar monsterHealth;
    private ProgressBar playerHealth;

    private Player player;
    private Monster monster;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
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
        monster = DataManager.get().getMonster(getContext());
        monsterHealthPrompt.setText(getString(R.string.fragment_battle_engage_monster_health_prompt, monster.getName()));
        monsterHealth.setMax(monster.getMaxHealth());
        monsterHealth.setProgress(monster.getCurrentHealth());
        monsterHealth.setSecondaryProgress(monster.getCurrentHealth());
    }

    private void loadPlayer()
    {
        player = DataManager.get().getPlayer(getContext());
        playerHealth.setProgress(player.getCurrentHealth());
        playerHealth.setSecondaryProgress(player.getCurrentHealth());
        playerHealth.setMax(player.getMaxHealth());
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
                        int playerHealthMin = player.getCurrentHealth();
                        int monsterHealthMin = monster.getCurrentHealth();

                        while (playerHealthMin > 0 && monsterHealthMin > 0)
                        {
                            monsterHealthMin -= player.getStrength() - monster.getDefense();
                            if (monsterHealthMin <= 0)
                            {
                                break;
                            }
                            playerHealthMin -= monster.getStrength() - player.getDefense();
                        }
                        // TODO: Animate the outcome somehow!

                        boolean isPlayerWinner = monsterHealthMin <= 0;
                        Log.e(TAG, "winner is: " + isPlayerWinner);
                        DataManager.get().setBattleOutcome(getContext(), isPlayerWinner);
                        if (isPlayerWinner)
                        {
                            changeFragmentListener.changeFragment(DataManager.BATTLE_REWARD);
                        }
                        else
                        {
                            changeFragmentListener.changeFragment(DataManager.BATTLE_LOST);
                        }
                    }
                });
            }
        }).start();
    }

}
