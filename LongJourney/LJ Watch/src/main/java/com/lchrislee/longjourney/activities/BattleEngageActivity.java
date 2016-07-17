package com.lchrislee.longjourney.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.GridViewPager;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.adapters.BattleEngageGridPagerAdapter;

public class BattleEngageActivity extends Activity {
    private static final String TAG = BattleEngageActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engage);

        final GridViewPager grid = (GridViewPager) findViewById(R.id.engage_grid);
        grid.setAdapter(new BattleEngageGridPagerAdapter(this, getFragmentManager()));

        final DotsPageIndicator dots = (DotsPageIndicator) findViewById(R.id.engage_dots);
        dots.setPager(grid);
    }
}
