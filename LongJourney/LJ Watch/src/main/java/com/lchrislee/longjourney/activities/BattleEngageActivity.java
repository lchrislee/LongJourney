package com.lchrislee.longjourney.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.adapters.BattleEngageGridPagerAdapter;

public class BattleEngageActivity extends Activity {
    public static final String ENTRANCE = "com.lchrislee.longjourney.activities.BattleEngageActivity.ENTRANCE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engage);

        int entrance = -1;
        Intent i = getIntent();
        if (i != null){
            entrance = i.getIntExtra(ENTRANCE, -1);
        }

        final GridViewPager grid = (GridViewPager) findViewById(R.id.engage_grid);
        grid.setAdapter(new BattleEngageGridPagerAdapter(this, getFragmentManager(), entrance));

        final DotsPageIndicator dots = (DotsPageIndicator) findViewById(R.id.engage_dots);
        dots.setPager(grid);
    }
}
