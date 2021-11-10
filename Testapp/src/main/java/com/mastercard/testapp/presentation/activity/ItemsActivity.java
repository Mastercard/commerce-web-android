package com.mastercard.testapp.presentation.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.mastercard.testapp.R;
import com.mastercard.testapp.data.ItemRepository;
import com.mastercard.testapp.data.device.ItemLocalDataSource;
import com.mastercard.testapp.data.external.ItemExternalDataSource;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.items.AddItemUseCase;
import com.mastercard.testapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.mastercard.testapp.domain.usecase.items.MockGetItemsUseCase;
import com.mastercard.testapp.domain.usecase.login.IsLoggedUseCase;
import com.mastercard.testapp.domain.usecase.login.RemoveLoginUseCase;
import com.mastercard.testapp.presentation.AddFragmentToActivity;
import com.mastercard.testapp.presentation.fragment.ItemsFragment;
import com.mastercard.testapp.presentation.presenter.ItemsPresenter;

/**
 * Created by Sebastian Farias on 07-10-17.
 *
 * Items activity handles the interactions with the item list, settings and login.
 */
public class ItemsActivity extends AppCompatActivity {

  private ItemsPresenter mItemsPresenter;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    ItemsFragment itemsFragment =
        (ItemsFragment) getSupportFragmentManager().findFragmentById(R.id.main_container);
    if (itemsFragment == null) {
      itemsFragment = ItemsFragment.newInstance();
      AddFragmentToActivity.fragmentForActivity(getSupportFragmentManager(), itemsFragment,
          R.id.main_container);
    }

    mItemsPresenter = new ItemsPresenter(UseCaseHandler.getInstance(), itemsFragment,
        new MockGetItemsUseCase(ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
            ItemLocalDataSource.getInstance(getApplicationContext())), getApplicationContext()),
        new AddItemUseCase(ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
            ItemLocalDataSource.getInstance(getApplicationContext()))), new GetItemsOnCartUseCase(
        ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
            ItemLocalDataSource.getInstance(getApplicationContext())), ItemsActivity.this),
        new IsLoggedUseCase(getApplicationContext()),
        new RemoveLoginUseCase(getApplicationContext()));
  }
}