package com.us.masterpass.merchantapp;


import android.content.Context;
import android.content.SharedPreferences;

import com.us.masterpass.merchantapp.data.ItemRepository;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.presentation.presenter.base.Presenter;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * A class that contain common elements that at a business level are primitive and might be used in various kinds of
 * use cases.
 */

abstract class AbsTest {

    @Mock
    MasterpassExternalDataSource mMasterpassExternalDataSource;

    @Mock
    ItemRepository mItemsRepository;

    @Mock
    Context mContext;

    Presenter presenter;

    UseCaseHandler useCaseHandler;

    @Mock
    SharedPreferences sharedPreferences;

    @Mock
    SharedPreferences.Editor editor;

    /**
     * Method to initialize the fields
     */
    public void initUseCases() {
        MockitoAnnotations.initMocks(this);
        useCaseHandler = new UseCaseHandler(new BackgroundForTestUseCaseScheduler());
        when(mContext.getSharedPreferences(any(String.class), any(Integer.class))).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.putString(any(String.class), any(String.class))).thenReturn(editor);
    }
    @Test
    public void doEmptyMethodTest() {
        if(presenter != null) {
            presenter.pause();
            presenter.resume();
            presenter.destroy();
        }
    }
}
