package com.lchrislee.longjourney.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.activity.WearableActivity;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.fragments.AvoidFragment;
import com.lchrislee.longjourney.fragments.BaseFragment;
import com.lchrislee.longjourney.fragments.BattleEngageFragment;
import com.lchrislee.longjourney.fragments.BattleLossFragment;
import com.lchrislee.longjourney.fragments.BattleRewardFragment;
import com.lchrislee.longjourney.fragments.BattleSelectFragment;
import com.lchrislee.longjourney.fragments.TownFragment;
import com.lchrislee.longjourney.fragments.TravelFragment;
import com.lchrislee.longjourney.utility.DataPersistence;

public class MasterActivity extends WearableActivity
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
            int newLocation = incomingIntent.getIntExtra(NEW_LOCATION, DataPersistence.BATTLE);
            switch(newLocation)
            {
                case DataPersistence.SNEAK:
                    changeFragment(DataPersistence.SNEAK);
                    break;
                case DataPersistence.RUN:
                    changeFragment(DataPersistence.RUN);
                    break;
                default:
                    changeFragment(DataPersistence.BATTLE);
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

        @DataPersistence.PlayerLocation int location
                = DataPersistence.currentLocation(getApplicationContext());
        switch (location) {
            case DataPersistence.BATTLE_LOST:
                fragment = new BattleLossFragment();
                break;
            case DataPersistence.BATTLE:
                fragment = new BattleEngageFragment();
                break;
            case DataPersistence.BATTLE_REWARD:
                fragment = new BattleRewardFragment();
                break;
            case DataPersistence.ENGAGE:
                fragment = new BattleSelectFragment();
                break;
            case DataPersistence.REST:
                break;
            case DataPersistence.RUN:
            case DataPersistence.SNEAK:
                fragment = new AvoidFragment();
                break;
            case DataPersistence.TOWN:
                fragment = new TownFragment();
                break;
            case DataPersistence.TRAVEL:
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
    public void changeFragment(@DataPersistence.PlayerLocation int newLocation) {
        DataPersistence.saveCurrentLocation(getApplicationContext(), newLocation);
        changeDisplayingFragment();
    }
}
