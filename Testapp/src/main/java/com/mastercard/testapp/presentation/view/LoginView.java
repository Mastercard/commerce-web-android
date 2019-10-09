package com.mastercard.testapp.presentation.view;

import com.mastercard.testapp.presentation.presenter.LoginPresenter;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public interface LoginView extends View<LoginPresenter> {
  /**
   * Do login.
   */
  void doLogin();

  /**
   * Alert empty.
   */
  void alertEmpty();

  /**
   * Alert wrong.
   */
  void alertWrong();

  /**
   * Show loading spinner.
   *
   * @param show the show
   */
  void showLoadingSpinner(boolean show);
}
