package org.mockito.configuration;

/**
 * Disable cache to resolve powermock and robolectric issue
 * Refer to https://stackoverflow.com/questions/33008255/classcastexception-exception-when-running-robolectric-test-with-power-mock-on-mu
 */
public class MockitoConfiguration extends DefaultMockitoConfiguration {

  @Override
  public boolean enableClassCache() {
    return false;
  }
}
