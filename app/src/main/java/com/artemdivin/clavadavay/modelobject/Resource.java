package com.artemdivin.clavadavay.modelobject;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.artemdivin.clavadavay.modelobject.Resource.Status.ERROR;
import static com.artemdivin.clavadavay.modelobject.Resource.Status.SUCCESS;
import static com.artemdivin.clavadavay.modelobject.Resource.Status.LOADING;


public class Resource<T> {

    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    @NonNull
    public final Status status;
    @Nullable
    public final Throwable error;
    @Nullable
    public final T data;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(@Nullable Throwable error) {
        return new Resource<>(ERROR, null, error);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    public boolean isSucceed() {
        return status == SUCCESS;
    }

    public boolean isLoading() {
        return status == LOADING;
    }

    public boolean isError() {
        return status == ERROR;
    }
}
