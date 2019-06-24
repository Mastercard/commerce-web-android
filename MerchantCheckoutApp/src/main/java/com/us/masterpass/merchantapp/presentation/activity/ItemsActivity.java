package com.us.masterpass.merchantapp.presentation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.data.ItemRepository;
import com.us.masterpass.merchantapp.data.device.ItemLocalDataSource;
import com.us.masterpass.merchantapp.data.external.ItemExternalDataSource;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.AddItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.MockGetItemsUseCase;
import com.us.masterpass.merchantapp.domain.usecase.login.IsLoggedUseCase;
import com.us.masterpass.merchantapp.domain.usecase.login.RemoveLoginUseCase;
import com.us.masterpass.merchantapp.presentation.AddFragmentToActivity;
import com.us.masterpass.merchantapp.presentation.fragment.ItemsFragment;
import com.us.masterpass.merchantapp.presentation.presenter.ItemsPresenter;
/**
 * Created by Sebastian Farias on 07-10-17.
 *
 * Items activity handles the interactions with the item list, settings and login.
 */
public class ItemsActivity extends AppCompatActivity {

    private ItemsPresenter mItemsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ItemsFragment itemsFragment = (ItemsFragment)
                getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (itemsFragment == null) {
            itemsFragment = ItemsFragment.newInstance();
            AddFragmentToActivity.fragmentForActivity(getSupportFragmentManager(),itemsFragment, R.id.main_container);
        }

        mItemsPresenter = new ItemsPresenter(
                UseCaseHandler.getInstance(), itemsFragment, new MockGetItemsUseCase(
            ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
                ItemLocalDataSource.getInstance(getApplicationContext())), getApplicationContext()),
                new AddItemUseCase(ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
                        ItemLocalDataSource.getInstance(getApplicationContext())
                )),
                new GetItemsOnCartUseCase(ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
                        ItemLocalDataSource.getInstance(getApplicationContext())
                ), ItemsActivity.this),
                new IsLoggedUseCase(getApplicationContext()),
                new RemoveLoginUseCase(getApplicationContext())
        );
    }



}