package com.n1akai.e_commercetutorial.utils;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class Utils {
    public static void successToast(Context context, String message) {
        Toasty.success(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void errorToast(Context context, String message) {
        Toasty.error(context, message, Toast.LENGTH_SHORT).show();
    }

    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }
}
