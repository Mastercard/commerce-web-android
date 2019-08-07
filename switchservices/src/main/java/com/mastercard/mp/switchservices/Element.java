package com.mastercard.mp.switchservices;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation options defined for JSON objects to indicate required or optional fields
 */

@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.FIELD) public @interface Element {
  /**
   * Determines whether the object is required in the JSON string. A field marked as optional will
   * be ignored if it is missing from the JSON string when deserializing. If a field is optional, it
   * will not appear in the JSON string if it is set to {@code null}
   *
   * @return true if the element is required, otherwise false
   */
  boolean required() default false;
}
