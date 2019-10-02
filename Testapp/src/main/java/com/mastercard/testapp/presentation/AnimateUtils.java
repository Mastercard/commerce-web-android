package com.mastercard.testapp.presentation;

/**
 * Created by Sebastian Farias on 10/30/17.
 */
public class AnimateUtils implements android.view.animation.Interpolator {
  private double mAmplitude = 1;
  private double mFrequency = 10;

  /**
   * Instantiates a new Animate utils.
   *
   * @param amplitude the amplitude
   * @param frequency the frequency
   */
  public AnimateUtils(double amplitude, double frequency) {
    this.mAmplitude = amplitude;
    this.mFrequency = frequency;
  }

  public float getInterpolation(float time) {
    return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) * Math.cos(mFrequency * time) + 1);
  }
}