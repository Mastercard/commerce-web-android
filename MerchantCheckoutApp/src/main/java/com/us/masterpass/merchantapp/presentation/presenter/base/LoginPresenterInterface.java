package com.us.masterpass.merchantapp.presentation.presenter.base;

/**
 * Created by Sebastian Farias on 17-10-17.
 */
public interface LoginPresenterInterface extends Presenter {
    /**
     * Do login.
     *
     * @param username        the username
     * @param password        the password
     * @param forceSaveConfig the force save config
     */
    void doLogin(String username, String password, boolean forceSaveConfig);
}
