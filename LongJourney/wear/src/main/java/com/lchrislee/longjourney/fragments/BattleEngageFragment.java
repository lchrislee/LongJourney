package com.lchrislee.longjourney.fragments;

import android.os.Bundle;
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
import com.lchrislee.longjourney.utility.managers.DataManager;

public class BattleEngageFragment extends LongJourneyBaseFragment {

    private ImageView monsterImage;
    private TextView monsterHealthPrompt;
    private ProgressBar monsterHealth;
    private ProgressBar playerHealth;

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
        monsterHealthPrompt = masterView.findViewById(R.id.fragment_battle_engage_monster_health_prompt);
        monsterHealth = masterView.findViewById(R.id.fragment_battle_engage_monster_health);
        playerHealth = masterView.findViewById(R.id.fragment_battle_engage_player_health);

        return masterView;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadMonster();
        loadPlayer();
    }

    private void loadMonster()
    {
        Monster monster = DataManager.get().getMonster(getContext());
        monsterHealthPrompt.setText(getString(R.string.fragment_battle_engage_monster_health_prompt, monster.getName()));
        monsterHealth.setMax(monster.getMaxHealth());
        monsterHealth.setProgress(monster.getCurrentHealth());
    }

    private void loadPlayer()
    {
        Player player = DataManager.get().getPlayer(getContext());
        playerHealth.setProgress(player.getCurrentHealth());
        playerHealth.setMax(player.getMaxHealth());
    }

}
