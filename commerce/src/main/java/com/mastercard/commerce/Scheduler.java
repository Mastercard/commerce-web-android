package com.mastercard.commerce;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@code Scheduler} manages threading of {@link Runnable} tasks. A {@link Runnable} given to this
 * {@code Scheduler} will execute on a background thread defined by the {@link ThreadPoolExecutor}
 * used to construct this object.
 */

class Scheduler {
  private final ThreadPoolExecutor threadPoolExecutor;
  private final Handler postHandler;

  Scheduler(@NonNull ThreadPoolExecutor threadPoolExecutor) {
    this.threadPoolExecutor = threadPoolExecutor;
    postHandler = new Handler(Looper.getMainLooper());
  }

  void execute(Runnable runnable) {
    threadPoolExecutor.execute(runnable);
  }

  void notifyResponse(final String response, final HttpCallback httpCallback) {
    postHandler.post(new Runnable() {
      @Override
      public void run() {
        httpCallback.onResponse(response);
      }
    });
  }

  void notifyError(final String error, final HttpCallback httpCallback) {
    postHandler.post(new Runnable() {
      @Override
      public void run() {
        httpCallback.onErrorResponse(error);
      }
    });
  }
}
