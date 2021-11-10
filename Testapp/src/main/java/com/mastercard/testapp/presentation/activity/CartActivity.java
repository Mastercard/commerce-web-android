package com.mastercard.testapp.presentation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import com.mastercard.testapp.R;
import com.mastercard.testapp.data.ItemRepository;
import com.mastercard.testapp.data.device.ItemLocalDataSource;
import com.mastercard.testapp.data.device.SettingsSaveConfigurationSdk;
import com.mastercard.testapp.data.external.EnvironmentSettings;
import com.mastercard.testapp.data.external.ItemExternalDataSource;
import com.mastercard.testapp.data.external.MasterpassExternalDataSource;
import com.mastercard.testapp.domain.masterpass.MasterpassSdkCoordinator;
import com.mastercard.testapp.domain.usecase.base.UseCaseHandler;
import com.mastercard.testapp.domain.usecase.items.AddItemUseCase;
import com.mastercard.testapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.mastercard.testapp.domain.usecase.items.RemoveAllItemUseCase;
import com.mastercard.testapp.domain.usecase.items.RemoveItemUseCase;
import com.mastercard.testapp.domain.usecase.masterpass.ConfirmTransactionUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.GetSelectedPaymentMethodUseCase;
import com.mastercard.testapp.domain.usecase.paymentMethod.IsPaymentMethodEnabledUseCase;
import com.mastercard.testapp.presentation.AddFragmentToActivity;
import com.mastercard.testapp.presentation.fragment.CartFragment;
import com.mastercard.testapp.presentation.presenter.CartPresenter;

import static com.mastercard.commerce.CommerceWebSdk.COMMERCE_REQUEST_CODE;
import static com.mastercard.commerce.CommerceWebSdk.COMMERCE_TRANSACTION_ID;

/**
 * Created by Sebastian Farias on 09-10-17.
 *
 * Cart activity manage the shopping cart flows
 */
public class CartActivity extends AppCompatActivity {

  private static String TAG = CartActivity.class.getSimpleName();

  /**
   * Presenter used for the first screen of shopping cart
   */
  private CartPresenter mCartPresenter;

  protected void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "onCreate --------------------------");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    if (getIntent() != null) {
      Log.d("CartActivity",
          "TransactionId = " + getIntent().getStringExtra(COMMERCE_TRANSACTION_ID));
    }
    CartFragment cartFragment =
        (CartFragment) getSupportFragmentManager().findFragmentById(R.id.main_container);
    if (cartFragment == null) {

      cartFragment = CartFragment.newInstance(getIntent().getStringExtra(COMMERCE_TRANSACTION_ID));
      AddFragmentToActivity.fragmentForActivity(getSupportFragmentManager(), cartFragment,
          R.id.main_container);
    }
    SettingsSaveConfigurationSdk settingsSaveConfigurationSdk =
        SettingsSaveConfigurationSdk.getInstance(getApplicationContext());
    EnvironmentSettings.loadEnvironmentConfigurations(getApplicationContext());

    mCartPresenter = new CartPresenter(UseCaseHandler.getInstance(), cartFragment,
        new GetItemsOnCartUseCase(ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
            ItemLocalDataSource.getInstance(getApplicationContext())), CartActivity.this),
        new AddItemUseCase(ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
            ItemLocalDataSource.getInstance(getApplicationContext()))), new RemoveItemUseCase(
        ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
            ItemLocalDataSource.getInstance(getApplicationContext()))), new RemoveAllItemUseCase(
        ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
            ItemLocalDataSource.getInstance(getApplicationContext()))),
        new ConfirmTransactionUseCase(MasterpassExternalDataSource.getInstance()),

        new IsPaymentMethodEnabledUseCase(settingsSaveConfigurationSdk),

        new GetSelectedPaymentMethodUseCase(MasterpassSdkCoordinator.getInstance()));
  }

  @Override protected void onNewIntent(Intent intent) {
    Log.d(TAG, "onNewIntent");
    super.onNewIntent(intent);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (requestCode == COMMERCE_REQUEST_CODE && resultCode == Activity.RESULT_CANCELED) {
      Log.d(TAG, "User cancelled checkout with CommerceWeb");
    } else if (resultCode == Activity.RESULT_OK) {
      Log.d(TAG, "Checkout Success ");
      if (data != null) {
        Log.d(TAG, "transaction id =" + data.getStringExtra(COMMERCE_TRANSACTION_ID));
      }
    }
  }
}
