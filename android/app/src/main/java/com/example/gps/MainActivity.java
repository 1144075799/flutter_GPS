package com.example.gps;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

  private static final String BLUETOOTH_CHANNEL="samples.flutter.io/gps";

  boolean gps=false;
  boolean network=false;
  LocationManager locationManager=null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);


    new MethodChannel(getFlutterView(),BLUETOOTH_CHANNEL).setMethodCallHandler(
            new MethodChannel.MethodCallHandler() {
                @Override
                public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                     if(methodCall.method.equals("inspectionGPS")){
                         boolean isOpen=isOPen();
                         if (isOpen){
                             result.success("GPS已开启");
                         }
                         result.success("GPS未开启");
                     }
                }
            }
    );


  }

    private boolean isOPen(){
      locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
      gps=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
      network=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
      /**确认GPS是否开启**/
      if(gps||network){
          return true;
      }
      return false;
    }


}
