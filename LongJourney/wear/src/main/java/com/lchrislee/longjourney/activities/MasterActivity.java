package com.lchrislee.longjourney.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.fragments.AvoidFragment;
import com.lchrislee.longjourney.fragments.BaseFragment;
import com.lchrislee.longjourney.fragments.BattleEngageFragment;
import com.lchrislee.longjourney.fragments.BattleLossFragment;
import com.lchrislee.longjourney.fragments.BattleRewardFragment;
import com.lchrislee.longjourney.fragments.BattleSelectFragment;
import com.lchrislee.longjourney.fragments.TownFragment;
import com.lchrislee.longjourney.fragments.TravelFragment;
import com.lchrislee.longjourney.utility.managers.DataManager;

public class MasterActivity extends BaseActivity
        implements BaseFragment.OnChangeFragment {

    private static final String TAG = "MasterActivity";

    public static final String NEW_LOCATION = "NEW_LOCATION";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Intent incomingIntent = getIntent();
        int flags = incomingIntent.getFlags();
        if (flags == (Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK))
        {
            finish();
            return;
        }

        if (incomingIntent.hasExtra(NEW_LOCATION))
        {
            int newLocation = incomingIntent.getIntExtra(NEW_LOCATION, DataManager.BATTLE);
            switch(newLocation)
            {
                case DataManager.SNEAK:
                    changeFragment(DataManager.SNEAK);
                    break;
                case DataManager.RUN:
                    changeFragment(DataManager.RUN);
                    break;
                default:
                    changeFragment(DataManager.BATTLE);
                    break;
            }
        }
        else
        {
            changeDisplayingFragment();
        }
    }

    private void changeDisplayingFragment()
    {
        FragmentManager manager = getFragmentManager();
        BaseFragment fragment = null;

        @DataManager.PlayerLocation int location
                = DataManager.get().loadLocation(getApplicationContext());
        switch (location) {
            case DataManager.BATTLE_LOST:
                fragment = new BattleLossFragment();
                break;
            case DataManager.BATTLE:
                fragment = new BattleEngageFragment();
                break;
            case DataManager.BATTLE_REWARD:
                fragment = new BattleRewardFragment();
                break;
            case DataManager.ENGAGE:
                fragment = new BattleSelectFragment();
                break;
            case DataManager.REST:
                break;
            case DataManager.RUN:
            case DataManager.SNEAK:
                fragment = new AvoidFragment();
                break;
            case DataManager.TOWN:
                fragment = new TownFragment();
                break;
            case DataManager.TRAVEL:
                fragment = new TravelFragment();
                break;
        }

        if (fragment != null)
        {
            fragment.setChangeFragmentListener(this);
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.activity_base_frame, fragment);
        transaction.commit();
    }

    @Override
    public void changeFragment(@DataManager.PlayerLocation int newLocation) {
        DataManager.get().changeLocation(getApplicationContext(), newLocation);
        changeDisplayingFragment();
    }
}
