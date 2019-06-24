package com.us.masterpass.merchantapp.domain.usecase.base;

/**
 * The interface Use case scheduler.
 */
public interface UseCaseScheduler {

    /**
     * Execute.
     *
     * @param runnable the runnable
     */
    void execute(Runnable runnable);

    /**
     * Notify response.
     *
     * @param <V>             the type parameter
     * @param response        the response
     * @param useCaseCallback the use case callback
     */
    <V extends UseCase.ResponseValue> void notifyResponse(
        final V response, final UseCase.UseCaseCallback<V> useCaseCallback);

    /**
     * On error.
     *
     * @param <V>             the type parameter
     * @param useCaseCallback the use case callback
     */
    <V extends UseCase.ResponseValue> void onError(
        final UseCase.UseCaseCallback<V> useCaseCallback);
}