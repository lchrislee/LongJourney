package com.lchrislee.longjourney.fragments;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.utility.managers.DataManager;

public class LongJourneyBaseFragment extends Fragment
{

    public interface OnChangeFragment
    {
        void changeFragment(@DataManager.PlayerLocation int newLocation);
    }

    protected OnChangeFragment changeFragmentListener;

    public void setChangeFragmentListener(
            @NonNull OnChangeFragment changeFragmentListener) {
        this.changeFragmentListener = changeFragmentListener;
    }

}
