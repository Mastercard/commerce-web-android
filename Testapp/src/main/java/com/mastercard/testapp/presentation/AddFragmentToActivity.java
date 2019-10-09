package com.mastercard.testapp.presentation;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static com.mastercard.testapp.domain.Utils.checkNotNull;

/**
 * Created by Sebastian Farias on 08-10-17.
 */
public class AddFragmentToActivity {
  /**
   * Fragment for activity.
   *
   * @param fm the fm
   * @param fragment the fragment
   * @param layoutId the layout id
   */
  public static void fragmentForActivity(@NonNull FragmentManager fm, @NonNull Fragment fragment,
      int layoutId) {
    checkNotNull(fm);
    checkNotNull(fragment);
    FragmentTransaction ft = fm.beginTransaction();
    ft.add(layoutId, fragment);
    ft.commit();
  }

  /**
   * Fragment for fragment.
   *
   * @param fm the fm
   * @param fragment the fragment
   * @param layoutId the layout id
   */
  public static void fragmentForFragment(@NonNull FragmentManager fm, @NonNull Fragment fragment,
      int layoutId) {
    checkNotNull(fm);
    checkNotNull(fragment);
    FragmentTransaction ft = fm.beginTransaction();
    ft.replace(layoutId, fragment);
    //Added to fix DE94427
    //Handle illegalstateexception  :can not perform this action after onsaveinstancestate
    ft.commitAllowingStateLoss();
  }

  public static void addFragmentWithBackStack(@NonNull FragmentManager fm,
      @NonNull Fragment fragment, int layoutId) {
    checkNotNull(fm);
    checkNotNull(fragment);
    FragmentTransaction ft = fm.beginTransaction();
    ft.add(layoutId, fragment);
    ft.addToBackStack(null);
    ft.commit();
  }

  public static void replaceFragmentWithBackStack(@NonNull FragmentManager fm,
      @NonNull Fragment fragment, int layoutId) {
    checkNotNull(fm);
    checkNotNull(fragment);
    FragmentTransaction ft = fm.beginTransaction();
    ft.replace(layoutId, fragment);
    ft.addToBackStack(null);
    ft.commit();
  }
}