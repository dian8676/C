package com.example.sohyeon.dowazo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Sohyeon on 2017-04-29.
 */

public class PermissionUtil {

    public static boolean checkPermissions(Activity activity, String permission){
        int permissionResult= ActivityCompat.checkSelfPermission(activity,permission);
        if(permissionResult == PackageManager.PERMISSION_GRANTED) return true;
        else return false;
    }
    public static final int REQUEST_STORAGE =1;
    private static String[] PERMISSONS_STORAGE={
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE
    };
    public  static void requestExternalPermissions(Activity activity){
        ActivityCompat.requestPermissions(activity,PERMISSONS_STORAGE,REQUEST_STORAGE);
    }


    public static boolean verifyPermission(int[] grantresults){
        if(grantresults.length<1){
            return false;
        }
        for(int result : grantresults){
            if(result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }



}

