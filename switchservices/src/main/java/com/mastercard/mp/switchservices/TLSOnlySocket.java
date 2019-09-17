package com.mastercard.mp.switchservices;

import javax.net.ssl.SSLSocket;

/**
 * The extension of SSLSocket providing secure protocols TLS (Transport Layer Security)
 * only.
 *
 * @author &copy;2014-2015 MasterCard. Proprietary. All rights reserved.
 * @version 5.2.0
 */

class TLSOnlySocket extends DelegateSSLSocket {

  TLSOnlySocket(SSLSocket socket) {
    super(socket);
  }

  @Override public void setEnabledProtocols(String[] protocols) {
    super.setEnabledProtocols(new String[] { "TLSv1.1", "TLSv1.2" });
  }
}