package com.mastercard.mp.switchservices;

import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * String definitions for the environment in which Switch is deployed
 */

public class Environments {
  public static final String DEV = "DEV";
  public static final String STAGE = "STAGE";
  public static final String STAGE1 = "STAGE1";
  public static final String STAGE2 = "STAGE2";
  public static final String STAGE3 = "STAGE3";
  public static final String SANDBOX = "SANDBOX";
  public static final String ITF = "ITF";
  public static final String PRODUCTION = "PRODUCTION";
  public static final String INT = "INT";

  @Retention(RetentionPolicy.SOURCE) @StringDef({
      DEV, STAGE, STAGE1, STAGE2, STAGE3, SANDBOX, ITF, PRODUCTION, INT
  }) @interface Environment {

  }
}
