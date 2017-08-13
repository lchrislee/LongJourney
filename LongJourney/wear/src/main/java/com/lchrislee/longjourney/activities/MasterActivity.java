package com.lchrislee.longjourney.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lchrislee.longjourney.R;
import com.lchrislee.longjourney.fragments.BattleSelectFragment;
import com.lchrislee.longjourney.fragments.LongJourneyBaseFragment;
import com.lchrislee.longjourney.fragments.TownFragment;
import com.lchrislee.longjourney.fragments.TravelFragment;
import com.lchrislee.longjourney.utility.managers.DataManager;

public class MasterActivity extends LongJourneyBaseActivity
        implements LongJourneyBaseFragment.OnChangeFragment {

    private static final String TAG = "MasterActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Intent incomingIntent = getIntent();
        int flags = incomingIntent.getFlags();
        if (flags == (Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK))
        {
            finish();
        }

        changeDisplayingFragment();
    }

    private void changeDisplayingFragment()
    {
        FragmentManager manager = getFragmentManager();
        LongJourneyBaseFragment fragment = null;

        @DataManager.PlayerLocation int location
                = DataManager.get().loadLocation(getApplicationContext());
        switch (location) {
            case DataManager.BATTLE_LOST:
                break;
            case DataManager.BATTLE:
                break;
            case DataManager.BATTLE_REWARD:
                break;
            case DataManager.BATTLE_OPTION:
                fragment = new BattleSelectFragment();
                break;
            case DataManager.REST:
                break;
            case DataManager.RUN:
                break;
            case DataManager.SNEAK:
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
