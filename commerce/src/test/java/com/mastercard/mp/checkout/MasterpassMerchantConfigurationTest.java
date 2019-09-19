package com.mastercard.mp.checkout;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static java.util.Locale.US;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(RobolectricTestRunner.class) @Config(sdk = 27)
public class MasterpassMerchantConfigurationTest {

  private String merchantName = "merchantName";
  private Context context;
  private Locale locale;
  private String checkoutUrl = "checkoutUrl";
  private boolean expressCheckoutEnabled = false;
  private String checkoutId = "checkoutId";
  private String merchantCountryCode = "merchantCountryCode";
  private List<NetworkType> allowedNetworkTypes;

  @Test public void testMerchantConfiguration() {

    allowedNetworkTypes = new ArrayList<>();
    allowedNetworkTypes.add(new NetworkType("networkType"));
    locale = new Locale(US.toString());
    context = RuntimeEnvironment.application;

    MasterpassMerchantConfiguration masterpassMerchantConfiguration =
        new MasterpassMerchantConfiguration.Builder().setMerchantName(merchantName)
            .setContext(context)
            .setEnvironment(checkoutUrl)
            .setExpressCheckoutEnabled(expressCheckoutEnabled)
            .setCheckoutId(checkoutId)
            .setLocale(locale)
            .setMerchantCountryCode(merchantCountryCode)
            .setAllowedNetworkTypes(allowedNetworkTypes)
            .build();

    assertEquals(merchantName, masterpassMerchantConfiguration.getMerchantName());
    assertEquals(context, masterpassMerchantConfiguration.getContext());
    assertEquals(locale, masterpassMerchantConfiguration.getLocale());
    assertEquals(checkoutUrl, masterpassMerchantConfiguration.getEnvironment());
    assertEquals(expressCheckoutEnabled,
        masterpassMerchantConfiguration.isExpressCheckoutEnabled());
    assertEquals(checkoutId, masterpassMerchantConfiguration.getCheckoutId());
    assertEquals(merchantCountryCode, masterpassMerchantConfiguration.getMerchantCountryCode());
    assertEquals(allowedNetworkTypes, masterpassMerchantConfiguration.getAllowedNetworkTypes());
  }
}
