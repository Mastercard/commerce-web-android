package com.mastercard.testapp.domain;

import android.support.annotation.Nullable;

/**
 * Created by Sebastian Farias on 08-10-17.
 * Check if selection is null
 */
public class Utils {

  /**
   * Check not null t.
   *
   * @param <T> the type parameter
   * @param reference the reference
   * @return the t
   */
  public static <T> T checkNotNull(T reference) {
    if (reference == null) {
      throw new NullPointerException();
    } else {
      return reference;
    }
  }

  /**
   * Check not null t.
   *
   * @param <T> the type parameter
   * @param reference the reference
   * @param errorMessage the error message
   * @return the t
   */
  public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
    if (reference == null) {
      throw new NullPointerException(String.valueOf(errorMessage));
    } else {
      return reference;
    }
  }
}
