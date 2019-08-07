package com.mastercard.mp.switchservices.checkout;

/**
 * Response object from /pairingId switch call
 */

public class PairingIdResponse {
  private String pairingId;

  public String getPairingId() {
    return pairingId;
  }

  public void setPairingId(String pairingId) {
    this.pairingId = pairingId;
  }
}
