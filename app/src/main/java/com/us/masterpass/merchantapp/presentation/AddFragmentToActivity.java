package com.us.masterpass.merchantapp.presentation;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.us.masterpass.merchantapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 */
public class AddFragmentToActivity {

    private AddFragmentToActivity() {
    }

    /**
     * Fragment for activity.
     *
     * @param fm       the fm
     * @param fragment the fragment
     * @param layoutId the layout id
     */
    public static void fragmentForActivity(
            @NonNull FragmentManager fm,
            @NonNull Fragment fragment, int layoutId) {
        checkNotNull(fm);
        checkNotNull(fragment);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(layoutId, fragment);
        ft.commit();
    }

    /**
     * Fragment for fragment.
     *
     * @param fm       the fm
     * @param fragment the fragment
     * @param layoutId the layout id
     */
    public static void fragmentForFragment(
            @NonNull FragmentManager fm,
            @NonNull Fragment fragment, int layoutId) {
        checkNotNull(fm);
        checkNotNull(fragment);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(layoutId, fragment);
        ft.commitAllowingStateLoss();
    }

}