package com.mastercard.mp.switchservices;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@code Scheduler} manages threading of {@link Runnable} tasks. A {@link Runnable} given to this
 * {@code Scheduler} will execute on a background thread defined by the {@link ThreadPoolExecutor}
 * used to construct this object.
 */

class Scheduler {
  private final ThreadPoolExecutor threadPoolExecutor;
  private final Handler handler = new Handler(Looper.getMainLooper());

  Scheduler(ThreadPoolExecutor threadPoolExecutor) {
    this.threadPoolExecutor = threadPoolExecutor;
  }

  void execute(Runnable runnable) {
    threadPoolExecutor.execute(runnable);
  }

  <T> void notifyResponse(final T response, final HttpCallback<T> httpCallback) {
    handler.post(new Runnable() {
      @Override public void run() {
        httpCallback.onResponse(response);
      }
    });
  }

  <T> void notifyError(final ServiceError error, final HttpCallback<T> httpCallback) {
    handler.post(new Runnable() {
      @Override public void run() {
        httpCallback.onError(error);
      }
    });
  }
}
