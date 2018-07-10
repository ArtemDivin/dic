package com.artemdivin.clavadavay.helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.artemdivin.clavadavay.R;

public class DialogHelper {

    public static void showDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, (dialogInterface, i) -> {});
        builder.create().show();

    }

    public static void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.create().show();
    }

    public static void showDialog(Context context, Throwable throwable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(R.string.button_ok, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setMessage(throwable != null ? throwable.getMessage() : "Произошла непредвиденная ошибка");
        builder.create().show();

    }
}