package com.mastercard.mp.switchservices.checkout;

import org.simpleframework.xml.Element;

/**
 * <p>Java class for Gender.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Gender">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="M"/>
 *     &lt;enumeration value="F"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@Element(required = false) public enum Gender {

  M, F;

  public static Gender fromValue(String v) {
    return valueOf(v);
  }

  public String value() {
    return name();
  }

}
