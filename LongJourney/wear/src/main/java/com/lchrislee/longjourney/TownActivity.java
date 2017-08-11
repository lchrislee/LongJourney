package com.lchrislee.longjourney;

import android.app.Activity;
import android.os.Bundle;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.view.MenuItem;
import android.widget.Toast;

public class TownActivity extends Activity implements MenuItem.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_town);

        final WearableActionDrawerView actionDrawerView = findViewById(R.id.activity_town_action_drawer);
        actionDrawerView.getController().peekDrawer();
        actionDrawerView.setOnMenuItemClickListener(this);
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
