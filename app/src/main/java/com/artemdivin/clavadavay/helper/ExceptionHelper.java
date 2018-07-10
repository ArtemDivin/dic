package com.artemdivin.clavadavay.helper;

import android.content.Context;
import android.graphics.Color;

import com.artemdivin.clavadavay.R;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

public class ExceptionHelper  {

    public static String getException(Throwable throwable, Context context){
        Exception exception;
        if (throwable instanceof HttpException) {
            exception = (HttpException) throwable;
            return exception.getMessage();
        }else if (throwable instanceof SocketTimeoutException){
            exception = (SocketTimeoutException)throwable;
            return exception.getMessage();
        }else
            return context.getString(R.string.label_error);
        }
    }
