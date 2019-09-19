package com.mastercard.mp.checkout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class) public class MasterpassServicesTest {

  private String dev = "http://ech-10-157-130-186.devcloud.mastercard.com/srci/";
  private String stage = "https://stage.src.mastercard.com/srci/";
  private String stage1 = "https://stage1.masterpass.com/srci/";
  private String stage2 = "https://stage2.masterpass.com/srci/";
  private String itf = "https://itf.masterpass.com/srci/";
  private String stage3 = "https://stage3.masterpass.com/srci/";
  private String sbox = "https://sandbox.masterpass.com/routing/v2/mobileapi/web-checkout";
  private String production = "https://masterpass.com/srci/";
  private String intEnv = "https://int.masterpass.com/srci/";
  private String defaultEnv = "https://masterpass.com/srci/";

  @Test public void testServices() {

    assertEquals(dev, MasterpassServices.getBaseUrl(MasterpassMerchantConfiguration.DEV));
    assertEquals(stage, MasterpassServices.getBaseUrl(MasterpassMerchantConfiguration.STAGE));
    assertEquals(stage1, MasterpassServices.getBaseUrl(MasterpassMerchantConfiguration.STAGE1));
    assertEquals(stage2, MasterpassServices.getBaseUrl(MasterpassMerchantConfiguration.STAGE2));
    assertEquals(itf, MasterpassServices.getBaseUrl(MasterpassMerchantConfiguration.ITF));
    assertEquals(stage3, MasterpassServices.getBaseUrl(MasterpassMerchantConfiguration.STAGE3));
    assertEquals(sbox, MasterpassServices.getBaseUrl(MasterpassMerchantConfiguration.SANDBOX));
    assertEquals(production,
        MasterpassServices.getBaseUrl(MasterpassMerchantConfiguration.PRODUCTION));
    assertEquals(intEnv, MasterpassServices.getBaseUrl(MasterpassMerchantConfiguration.INT));
    assertEquals(defaultEnv, MasterpassServices.getBaseUrl(""));
  }
}
