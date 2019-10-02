package com.mastercard.testapp.domain.usecase.base;

/**
 * Runs {@link UseCase}s using a {@link UseCaseScheduler}.
 */
public class UseCaseHandler {

  private static UseCaseHandler INSTANCE;

  private final UseCaseScheduler mUseCaseScheduler;

  /**
   * Instantiates a new Use case handler.
   *
   * @param useCaseScheduler the use case scheduler
   */
  public UseCaseHandler(UseCaseScheduler useCaseScheduler) {
    mUseCaseScheduler = useCaseScheduler;
  }

  /**
   * Gets instance.
   *
   * @return the instance
   */
  public static UseCaseHandler getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new UseCaseHandler(new UseCaseThreadPoolScheduler());
    }
    return INSTANCE;
  }

  /**
   * Execute.
   *
   * @param <T> the type parameter
   * @param <R> the type parameter
   * @param useCase the use case
   * @param values the values
   * @param callback the callback
   */
  public <T extends UseCase.RequestValues, R extends UseCase.ResponseValue> void execute(
      final UseCase<T, R> useCase, T values, UseCase.UseCaseCallback<R> callback) {
    useCase.setRequestValues(values);
    useCase.setUseCaseCallback(new UiCallbackWrapper(callback, this));

    mUseCaseScheduler.execute(new Runnable() {
      @Override public void run() {
        useCase.run();
      }
    });
  }

  /**
   * Notify response.
   *
   * @param <V> the type parameter
   * @param response the response
   * @param useCaseCallback the use case callback
   */
  public <V extends UseCase.ResponseValue> void notifyResponse(final V response,
      final UseCase.UseCaseCallback<V> useCaseCallback) {
    mUseCaseScheduler.notifyResponse(response, useCaseCallback);
  }

  private <V extends UseCase.ResponseValue> void notifyError(
      final UseCase.UseCaseCallback<V> useCaseCallback) {
    mUseCaseScheduler.onError(useCaseCallback);
  }

  private static final class UiCallbackWrapper<V extends UseCase.ResponseValue>
      implements UseCase.UseCaseCallback<V> {
    private final UseCase.UseCaseCallback<V> mCallback;
    private final UseCaseHandler mUseCaseHandler;

    /**
     * Instantiates a new Ui callback wrapper.
     *
     * @param callback the callback
     * @param useCaseHandler the use case handler
     */
    public UiCallbackWrapper(UseCase.UseCaseCallback<V> callback, UseCaseHandler useCaseHandler) {
      mCallback = callback;
      mUseCaseHandler = useCaseHandler;
    }

    @Override public void onSuccess(V response) {
      mUseCaseHandler.notifyResponse(response, mCallback);
    }

    @Override public void onError() {
      mUseCaseHandler.notifyError(mCallback);
    }
  }
}