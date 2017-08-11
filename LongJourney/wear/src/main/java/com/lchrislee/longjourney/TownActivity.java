package com.lchrislee.longjourney;

import android.app.Activity;
import android.os.Bundle;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lchrislee.longjourney.managers.GameStateManager;
import com.lchrislee.longjourney.managers.PersistenceManager;
import com.lchrislee.longjourney.model.Town;
import com.lchrislee.longjourney.model.creatures.Player;

public class TownActivity extends Activity implements MenuItem.OnMenuItemClickListener {

    private Town town;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town);

        final WearableActionDrawerView actionDrawerView = findViewById(R.id.activity_town_action_drawer);
        actionDrawerView.getController().peekDrawer();
        actionDrawerView.setOnMenuItemClickListener(this);

        final TextView townName = findViewById(R.id.activity_town_town_name);
        final TextView playerGold = findViewById(R.id.activity_town_player_gold);
        final ProgressBar playerExperience = findViewById(R.id.activity_town_player_experience);

        town = GameStateManager.get().generateRandomTown(getApplicationContext());
        townName.setText(town.getName());
        player = PersistenceManager.get().getPlayer(getApplicationContext());
        playerGold.setText(String.valueOf(player.getGoldCarried()));
        playerExperience.setMax(player.getExperienceForNextLevel());
        playerExperience.setProgress(player.getCurrentExperience());
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch(menuItem.getItemId())
        {
            case R.id.menu_town_action_walk:
                Toast
                    .makeText(getApplicationContext(), "Start walking!", Toast.LENGTH_SHORT)
                    .show();
                return true;
        }
        return false;
    }
}
