package com.example.android.movies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 2425115 on 19/12/2017.
 */

public class Utils {

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable());
    }

}
