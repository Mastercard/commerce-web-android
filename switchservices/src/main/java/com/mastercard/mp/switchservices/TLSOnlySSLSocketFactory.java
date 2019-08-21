package com.mastercard.mp.switchservices;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * A <code>SSLSocketFacotry</code> implementation to create {@link SSLSocket}s.<br>
 * <b>By default the <code>SSLSocket</code> created by this factory are enabled
 * with TLS protocols only and do not support SSLv3 protocol</b>.
 *
 * @author &copy;2014-2015 MasterCard. Proprietary. All rights reserved.
 * @version 5.2.0
 */
class TLSOnlySSLSocketFactory extends SSLSocketFactory {
  private final SSLSocketFactory delegate;

  /**
   * Creates a new instance.
   *
   * @param sf SSLSocketFactory instance
   */
  public TLSOnlySSLSocketFactory(SSLSocketFactory sf) {
    this.delegate = sf;
  }

  @Override public String[] getDefaultCipherSuites() {
    return delegate.getDefaultCipherSuites();
  }

  @Override public String[] getSupportedCipherSuites() {
    return delegate.getSupportedCipherSuites();
  }

  /**
   * Enables TLS protocols over the given Socket.<br>
   *
   * @param s the socket
   * @return the created ssl socket enabled with TLS protocols only .
   */
  private static Socket makeTLSOnlySSLSocket(Socket s) {
    if (s instanceof SSLSocket) {
      s = new TLSOnlySocket((SSLSocket) s);

      // Explicitly calling setEnabledProtocols since from 4.4 and + does not get called
      // automatically
      ((SSLSocket) s).setEnabledProtocols(((SSLSocket) s).getEnabledProtocols());
    }

    return s;
  }

  @Override public Socket createSocket(Socket s, String host, int port, boolean autoClose)
      throws IOException {
    return makeTLSOnlySSLSocket(delegate.createSocket(s, host, port, autoClose));
  }

  @Override public Socket createSocket(String host, int port) throws IOException {
    return makeTLSOnlySSLSocket(delegate.createSocket(host, port));
  }

  @Override public Socket createSocket(String host, int port, InetAddress localHost, int localPort)
      throws IOException {
    return makeTLSOnlySSLSocket(delegate.createSocket(host, port, localHost, localPort));
  }

  @Override public Socket createSocket(InetAddress host, int port) throws IOException {
    return makeTLSOnlySSLSocket(delegate.createSocket(host, port));
  }

  @Override
  public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
      throws IOException {
    return makeTLSOnlySSLSocket(delegate.createSocket(address, port, localAddress, localPort));
  }
}