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
import com.us.masterpass.merchantapp.data.device.SettingsSaveConfigurationSdk;
import com.us.masterpass.merchantapp.data.external.ItemExternalDataSource;
import com.us.masterpass.merchantapp.data.external.MasterpassExternalDataSource;
import com.us.masterpass.merchantapp.domain.masterpass.MasterpassSdkCoordinator;
import com.us.masterpass.merchantapp.domain.usecase.base.UseCaseHandler;
import com.us.masterpass.merchantapp.domain.usecase.items.AddItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.GetItemsOnCartUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveAllItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.items.RemoveItemUseCase;
import com.us.masterpass.merchantapp.domain.usecase.masterpass.ConfirmTransactionUseCase;
import com.us.masterpass.merchantapp.domain.usecase.paymentMethod.GetSelectedPaymentMethodUseCase;
import com.us.masterpass.merchantapp.domain.usecase.paymentMethod.IsPaymentMethodEnabledUseCase;
import com.us.masterpass.merchantapp.presentation.AddFragmentToActivity;
import com.us.masterpass.merchantapp.presentation.fragment.CartFragment;
import com.us.masterpass.merchantapp.presentation.presenter.CartPresenter;

import static com.mastercard.commerce.CommerceWebSdk.COMMERCE_REQUEST_CODE;

/**
 * Created by Sebastian Farias on 09-10-17.
 *
 * Cart activity manage the shopping cart flows
 */
public class CartActivity extends AppCompatActivity {

    /**
     * Presenter used for the first screen of shopping cart
     */
    private CartPresenter mCartPresenter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
      Log.d("CartActivity", "onCreate --------------------------");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
      if(getIntent() != null) {
        Log.d("CartActivity", "transactionId = " +getIntent().getStringExtra("transactionId"));
      }
        CartFragment cartFragment = (CartFragment)
                getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (cartFragment == null) {
            cartFragment = CartFragment.newInstance();
            AddFragmentToActivity.fragmentForActivity(getSupportFragmentManager(),
                    cartFragment, R.id.main_container);
        }
      SettingsSaveConfigurationSdk settingsSaveConfigurationSdk =
          SettingsSaveConfigurationSdk.getInstance(getApplicationContext());

        mCartPresenter = new CartPresenter(
                UseCaseHandler.getInstance(),
                cartFragment,
                new GetItemsOnCartUseCase(ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
                        ItemLocalDataSource.getInstance(getApplicationContext())
                ), CartActivity.this),
                new AddItemUseCase(ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
                        ItemLocalDataSource.getInstance(getApplicationContext())
                )),
                new RemoveItemUseCase(ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
                        ItemLocalDataSource.getInstance(getApplicationContext())
                )),
                new RemoveAllItemUseCase(ItemRepository.getInstance(ItemExternalDataSource.getInstance(),
                        ItemLocalDataSource.getInstance(getApplicationContext())
                )),
                new ConfirmTransactionUseCase(MasterpassExternalDataSource.getInstance()),

                new IsPaymentMethodEnabledUseCase(settingsSaveConfigurationSdk),

                new GetSelectedPaymentMethodUseCase(MasterpassSdkCoordinator.getInstance())
        );
    }

  @Override protected void onNewIntent(Intent intent) {
    Log.d("CartActivity", "onNewIntent");
    super.onNewIntent(intent);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    if (requestCode == COMMERCE_REQUEST_CODE && resultCode == Activity.RESULT_CANCELED) {
      Log.d("CartActivity", "User cancelled checkout with CommerceWeb");
    } else if (resultCode == Activity.RESULT_OK) {
      Log.d("CartActivity", "Checkout Success ");
      if (data != null) {
        Log.d("CartActivity", "transaction id =" + data.getStringExtra("transactionId"));
      }
    }
  }
}
