package com.mastercard.mp.switchservices;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation options defined for JSON objects to indicate the serialized name from json.
 */

@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.FIELD) public @interface SerializedName {
  /**
   * Provides the serialized name for the json element. This field should be the same as represented
   * on the json file.
   * Example: {"key" : "value"}
   * then the serialzed name should be set to "key"
   *
   * @return true if the element is required, otherwise false
   */
  String name() default "";
}
