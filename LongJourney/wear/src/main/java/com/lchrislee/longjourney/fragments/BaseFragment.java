package com.lchrislee.longjourney.fragments;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.lchrislee.longjourney.utility.DataPersistence;

public abstract class BaseFragment extends Fragment
{

    public interface ChangesLocation
    {
        void updateLocation(@DataPersistence.PlayerLocation int newLocation);
    }

    ChangesLocation locationListener;

    public void changeLocationListener(@NonNull ChangesLocation updateListener) {
        this.locationListener = updateListener;
    }

}
