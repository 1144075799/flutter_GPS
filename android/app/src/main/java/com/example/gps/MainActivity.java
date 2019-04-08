package com.example.gps;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

    private static final String BLUETOOTH_CHANNEL = "samples.flutter.io/gps";

    boolean gps = false;
    boolean network = false;
    LocationManager locationManager = null;
    boolean gpsEnabled = false;
    Context context = null;
    String locationProvider=null;

    // 纬度
    public static double latitude = 0.0;
    // 经度
    public static double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);


        new MethodChannel(getFlutterView(), BLUETOOTH_CHANNEL).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                        if (methodCall.method.equals("inspectionGPS")) {
                            boolean isOpen = isOPen();
                            if (isOpen) {
                                result.success("GPS已开启");
                            }
                            result.success("GPS未开启");
                        } else if (methodCall.method.equals("openGPS")) {
                            openGPSSettings(context);
                        } else if (methodCall.method.equals("getDate")) {
                            
                        }
                    }
                }
        );


    }

    private boolean isOPen() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        /**确认GPS是否开启**/
        if (gps || network) {
            return true;
        }
        return false;
    }

    private boolean checkGpsIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    private void openGPSSettings(Context context) {
        if (checkGpsIsOpen()) {
            Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
        } else {
            new AlertDialog.Builder(this).setTitle("open GPS")
                    .setMessage("go to open")
                    //  取消选项
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT).show();
                            // 关闭dialog
                            dialogInterface.dismiss();
                        }
                    })
                    //  确认选项
                    .setPositiveButton("setting", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //跳转到手机原生设置页面
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, 1);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }



}

