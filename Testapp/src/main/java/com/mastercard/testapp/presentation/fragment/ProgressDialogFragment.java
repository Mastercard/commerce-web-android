package com.mastercard.testapp.presentation.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ProgressBar;

/**
 * A simple {@link Fragment} subclass.
 * Helps to show progress indicator on screen.
 * It can be used as :
 * <pre>
 * {@code
 *  //To show progress indicator
 *  ProgressDialogFragment.newInstance().showProgressDialog(fragmentManager);
 *
 *  //To dismiss progress indicator
 *  ProgressDialogFragment.newInstance().dismissProgressDialog();
 * }
 * </pre>
 */
public final class ProgressDialogFragment extends DialogFragment {

  private static ProgressDialogFragment progressDialogFragment;

  public ProgressDialogFragment() {
    // Required empty public constructor
  }

  /**
   * Shows progress indicator
   */
  static void showProgressDialog(FragmentManager fragmentManager) {
    if (!ProgressDialogFragment.getInstance().isAdded()) {
      ProgressDialogFragment.getInstance().show(fragmentManager, null);
    }
  }

  private static ProgressDialogFragment getInstance() {
    if (progressDialogFragment == null) {
      progressDialogFragment = new ProgressDialogFragment();
    }
    return progressDialogFragment;
  }

  /**
   * Dismisses progress indicator
   */
  static void dismissProgressDialog() {
    if (progressDialogFragment != null) {
      progressDialogFragment.dismissAllowingStateLoss();
    }
  }

  @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = new Dialog(getContext());
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    ProgressBar progressBar = new ProgressBar(getContext());
    progressBar.setIndeterminate(true);
    dialog.setContentView(progressBar);
    setCancelable(false);
    return dialog;
  }

  @Override public void show(FragmentManager manager, String tag) {
    FragmentTransaction fragmentTransaction = manager.beginTransaction();
    fragmentTransaction.add(this, tag);
    fragmentTransaction.commitAllowingStateLoss();
  }
}
