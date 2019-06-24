package com.us.masterpass.merchantapp.presentation.view;

import com.us.masterpass.merchantapp.presentation.presenter.base.Presenter;

/**
 * Created by Sebastian Farias on 08-10-17.
 *
 * @param <T> the type parameter
 */
public interface View<T extends Presenter> {

    /**
     * Sets presenter.
     *
     * @param presenter the presenter
     */
    void setPresenter(T presenter);

}