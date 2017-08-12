package com.lchrislee.longjourney.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.managers.DataManager;
import com.lchrislee.longjourney.model.Town;
import com.lchrislee.longjourney.model.creatures.Player;

import java.util.Locale;

public class TownActivity extends LongJourneyBaseActivity implements MenuItem.OnMenuItemClickListener {

    private static final String TAG = "TOWN_ACTIVITY";

    private String strengthTemplate;
    private String defenseTemplate;
    private String healthTemplate;

    private Town town;
    private Player player;

    private MenuItem strength;
    private MenuItem defense;
    private MenuItem health;

    private WearableActionDrawerView actionDrawerView;
    private TextView playerGold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town);

        actionDrawerView = findViewById(R.id.activity_town_action_drawer);
        actionDrawerView.setOnMenuItemClickListener(this);
        actionDrawerView.getController().peekDrawer();

        strength = actionDrawerView.getMenu().findItem(R.id.menu_town_action_strength);
        defense = actionDrawerView.getMenu().findItem(R.id.menu_town_action_defense);
        health = actionDrawerView.getMenu().findItem(R.id.menu_town_action_health);

        strengthTemplate = getString(R.string.menu_action_strength);
        defenseTemplate = getString(R.string.menu_action_defense);
        healthTemplate = getString(R.string.menu_action_health);

        setupPlayerInfo();
        setupTown();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        boolean shouldReloadMenu = false;
        String buyMessage = null;
        switch(menuItem.getItemId())
        {
            case R.id.menu_town_action_walk:
                Intent i = new Intent(getApplicationContext(), TravelActivity.class);
                startActivity(i);
                return true;
            case R.id.menu_town_action_strength:
                shouldReloadMenu = DataManager.get().purchaseStrength(getApplicationContext());
                buyMessage = getString(R.string.activity_town_purchase_success, strengthTemplate);
                break;
            case R.id.menu_town_action_defense:
                shouldReloadMenu = DataManager.get().purchaseDefense(getApplicationContext());
                buyMessage = getString(R.string.activity_town_purchase_success, defenseTemplate);
                break;
            case R.id.menu_town_action_health:
                shouldReloadMenu = DataManager.get().purchaseHealth(getApplicationContext());
                buyMessage = getString(R.string.activity_town_purchase_success, healthTemplate);
                break;
        }

        if (shouldReloadMenu) {
            updateBuyOptions();
            updateMoney();
            Toast.makeText(getApplicationContext(), buyMessage, Toast.LENGTH_SHORT).show();
            Log.e(TAG,
                    "Player data - gold: " + player.getGoldCarried()
                            + "str: " + player.getStrength()
                            + "def: " + player.getDefense()
                            + "max health: " + player.getMaxHealth()
            );
        }
        else
        {
            Toast.makeText(
                getApplicationContext(),
                R.string.activity_town_purchase_fail,
                Toast.LENGTH_SHORT
            ).show();
        }
        actionDrawerView.getController().peekDrawer();
        return true;
    }

    private void setupPlayerInfo()
    {
        playerGold = findViewById(R.id.activity_town_player_gold);
        final ProgressBar playerExperience = findViewById(R.id.activity_town_player_experience);
        player = DataManager.get().getPlayer(getApplicationContext());
        playerExperience.setMax(player.getExperienceForNextLevel());
        playerExperience.setProgress(player.getCurrentExperience());
        Log.e(TAG,
            "Player data - gold: " + player.getGoldCarried()
            + "str: " + player.getStrength()
            + "def: " + player.getDefense()
            + "max health: " + player.getMaxHealth()
        );
        updateMoney();
    }

    private void setupTown()
    {
        town = DataManager.get().getTown(getApplicationContext());
        String name = town.getName();
        final TextView townName = findViewById(R.id.activity_town_town_name);
        townName.setText(name);

        if (name.length() >= 16)
        {
            townName.setTextAppearance(android.R.style.TextAppearance_Small);
        }
        else if (name.length() >= 12)
        {
            townName.setTextAppearance(android.R.style.TextAppearance_Medium);
        }
        else
        {
            townName.setTextAppearance(android.R.style.TextAppearance_Large);
        }
        updateBuyOptions();
    }

    private void updateMoney()
    {
        playerGold.setText(String.valueOf(player.getGoldCarried()));
    }

    private void updateBuyOptions()
    {
        final String COST_TEMPLATE = "%s (%d G)";

        strength.setTitle(
            String.format(
                Locale.getDefault(),
                COST_TEMPLATE,
                strengthTemplate,
                town.getStrengthCost())
        );
        defense.setTitle(
            String.format(
                Locale.getDefault(),
                COST_TEMPLATE,
                defenseTemplate,
                town.getDefenseCost())
        );
        health.setTitle(
            String.format(
                Locale.getDefault(),
                COST_TEMPLATE,
                healthTemplate,
                town.getHealthCost())
        );
    }
}
