package com.us.masterpass.merchantapp.presentation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.us.masterpass.merchantapp.R;
import com.us.masterpass.merchantapp.data.ItemRepository;
import com.us.masterpass.merchantapp.data.device.ItemLocalDataSource;
import com.us.masterpass.merchantapp.data.external.ItemExternalDataSource;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.AddItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveAllItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.ConfirmTransactionUseCase;
import com.us.masterpass.merchantapp.presentation.AddFragmentToActivity;
import com.us.masterpass.merchantapp.presentation.fragment.CartFragment;
import com.us.masterpass.merchantapp.presentation.presenter.CartPresenter;

import static com.mastercard.commerce.CommerceWebSdk.COMMERCE_REQUEST_CODE;

/**
 * Created by Sebastian Farias on 09-10-17.
 */
public class CartActivity extends AppCompatActivity {

  private static final String TAG = CartActivity.class.getSimpleName();
  public static final String TRANSACTION_ID = "transactionId";

  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "inside CartActivity oncreate");

    setContentView(R.layout.main_activity);

    CartFragment cartFragment =
            (CartFragment) getSupportFragmentManager().findFragmentById(R.id.main_container);

    if(cartFragment == null) {
      cartFragment = CartFragment.newInstance(getIntent().getStringExtra(TRANSACTION_ID));
    }

    AddFragmentToActivity.fragmentForActivity(getSupportFragmentManager(), cartFragment,
            R.id.main_container);

    new CartPresenter(UseCaseHandler.getInstance(), cartFragment, new GetItemsOnCartUseCase(
        ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
            ItemLocalDataSource.getInstance(getApplicationContext()))), new AddItemUseCase(
        ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
            ItemLocalDataSource.getInstance(getApplicationContext()))), new RemoveItemUseCase(
        ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
            ItemLocalDataSource.getInstance(getApplicationContext()))), new RemoveAllItemUseCase(
        ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
            ItemLocalDataSource.getInstance(getApplicationContext()))),
        new ConfirmTransactionUseCase(MasterpassExternalDataSource.getInstance()));
  }

  @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (requestCode == COMMERCE_REQUEST_CODE && resultCode == Activity.RESULT_CANCELED) {
      Log.d(TAG, "User cancelled checkout with CommerceWeb");
    } else if (resultCode == Activity.RESULT_OK) {
      Log.d(TAG, "Checkout Success ");
      if (data != null) {
        Log.d(TAG, "transaction id =" + data.getStringExtra(TRANSACTION_ID));
      }
    }
  }
}