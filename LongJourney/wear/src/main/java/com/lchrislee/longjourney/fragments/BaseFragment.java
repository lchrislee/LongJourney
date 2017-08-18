package com.lchrislee.longjourney.fragments;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.utility.managers.DataManager;

public abstract class BaseFragment extends Fragment
{

    public interface OnChangeFragment
    {
        void changeFragment(@DataManager.PlayerLocation int newLocation);
    }

    OnChangeFragment changeFragmentListener;

    public void setChangeFragmentListener(
            @NonNull OnChangeFragment changeFragmentListener) {
        this.changeFragmentListener = changeFragmentListener;
    }

}
