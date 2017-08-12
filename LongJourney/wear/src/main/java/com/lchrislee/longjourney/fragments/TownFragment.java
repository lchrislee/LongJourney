package com.lchrislee.longjourney.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.model.Town;
import com.lchrislee.longjourney.model.creatures.Player;
import com.lchrislee.longjourney.utility.managers.DataManager;

import java.util.Locale;

public class TownFragment extends LongJourneyBaseFragment
        implements MenuItem.OnMenuItemClickListener
{

    private static final String TAG = "TOWN_FRAGMENT";

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
    private View masterView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        strengthTemplate = getString(R.string.menu_action_strength);
        defenseTemplate = getString(R.string.menu_action_defense);
        healthTemplate = getString(R.string.menu_action_health);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState
    ) {
        masterView = inflater.inflate(R.layout.fragment_town, container, false);
        actionDrawerView = masterView.findViewById(R.id.fragment_town_action_drawer);
        actionDrawerView.setOnMenuItemClickListener(this);
        actionDrawerView.getController().peekDrawer();

        strength = actionDrawerView.getMenu().findItem(R.id.menu_town_action_strength);
        defense = actionDrawerView.getMenu().findItem(R.id.menu_town_action_defense);
        health = actionDrawerView.getMenu().findItem(R.id.menu_town_action_health);

        setupPlayerInfo();
        setupTown();

        return masterView;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        boolean shouldReloadMenu = false;
        String buyMessage = null;
        final DataManager dm = DataManager.get();

        switch(menuItem.getItemId())
        {
            case R.id.menu_town_action_walk:
                dm.leaveTown(getContext());
                changeFragmentListener.changeFragment(DataManager.TRAVEL);
                return true;
            case R.id.menu_town_action_strength:
                shouldReloadMenu = dm.purchaseStrength(getContext());
                buyMessage = getString(R.string.fragment_town_purchase_success, strengthTemplate);
                break;
            case R.id.menu_town_action_defense:
                shouldReloadMenu = dm.purchaseDefense(getContext());
                buyMessage = getString(R.string.fragment_town_purchase_success, defenseTemplate);
                break;
            case R.id.menu_town_action_health:
                shouldReloadMenu = dm.purchaseHealth(getContext());
                buyMessage = getString(R.string.fragment_town_purchase_success, healthTemplate);
                break;
        }

        if (shouldReloadMenu) {
            updateBuyOptions();
            updateMoney();
            Toast.makeText(getContext(), buyMessage, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(
                getContext(),
                R.string.fragment_town_purchase_fail,
                Toast.LENGTH_SHORT
            ).show();
        }
        actionDrawerView.getController().peekDrawer();
        return true;
    }

    private void setupPlayerInfo()
    {
        playerGold = masterView.findViewById(R.id.fragment_town_player_gold);
        final ProgressBar playerExperience
                = masterView.findViewById(R.id.fragment_town_player_experience);
        player = DataManager.get().getPlayer(getContext());
        playerExperience.setMax(player.getExperienceForNextLevel());
        playerExperience.setProgress(player.getCurrentExperience());
        updateMoney();
    }

    private void setupTown()
    {
        town = DataManager.get().getTown(getContext());
        String name = town.getName();
        final TextView townName = masterView.findViewById(R.id.fragment_town_town_name);
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
