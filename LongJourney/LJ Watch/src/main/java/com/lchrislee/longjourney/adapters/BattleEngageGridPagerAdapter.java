package com.lchrislee.longjourney.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.wearable.view.FragmentGridPagerAdapter;

import com.lchrislee.longjourney.fragments.BattleEngageDetailFragment;
import com.lchrislee.longjourney.fragments.BattleEngageOptionsFragment;
import com.lchrislee.longjourney.utility.BattleUtility;

/**
 * Created by Chris Lee on 7/16/16.
 */
public class BattleEngageGridPagerAdapter extends FragmentGridPagerAdapter {
    private static final int ROW_COUNT = 1;
    private static final int COLUMN_COUNT = 2;

    private static final int COLUMN_DETAIL = 0;
    private static final int COLUMN_OPTIONS = 1;

    private Context context;

    public BattleEngageGridPagerAdapter(@NonNull Context c, @NonNull FragmentManager fm) {
        super(fm);
        context = c;
    }

    @Override
    public Fragment getFragment(int row, int column) {
        Fragment fragment;
        switch(column){
            case COLUMN_DETAIL:
                fragment = new BattleEngageDetailFragment();
                break;
            case COLUMN_OPTIONS:
                fragment = new BattleEngageOptionsFragment();
                break;
            default:
                fragment = null;
                break;
        }
        return fragment;
    }

    @Override
    public int getRowCount() {
        return ROW_COUNT;
    }

    @Override
    public int getColumnCount(int row) {
        return COLUMN_COUNT;
    }

    @Override
    public Drawable getBackgroundForRow(int row) {
        return BattleUtility.getMonsterDrawable(context);
    }
}
