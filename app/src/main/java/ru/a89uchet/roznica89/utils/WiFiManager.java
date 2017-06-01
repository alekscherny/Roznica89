package ru.a89uchet.roznica89.utils;

import android.content.Context;
import android.net.wifi.WifiManager;

import ua.cherny.kassir.Config;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 15.12.12
 * Time: 13:44
 * To change this template use File | Settings | File Templates.
 */
public class WiFiManager {

    public static void turnOnWiFi(){
        WifiManager wifiManager = (WifiManager) Config.globContext.getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled())
         wifiManager.setWifiEnabled(true);
    }
    public static void turnOfWiFi(){
        WifiManager wifiManager = (WifiManager) Config.globContext.getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled())
         wifiManager.setWifiEnabled(false);
    }

//    private BroadcastReceiver WifiStateChangedReceiver
//            = new BroadcastReceiver(){
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // TODO Auto-generated method stub
//
//            int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE ,
//                    WifiManager.WIFI_STATE_UNKNOWN);
//
//            switch(extraWifiState){
//                case WifiManager.WIFI_STATE_DISABLED:
//
//                    break;
//                case WifiManager.WIFI_STATE_DISABLING:
//
//                    break;
//                case WifiManager.WIFI_STATE_ENABLED:
//
//                    break;
//                case WifiManager.WIFI_STATE_ENABLING:
//
//                    break;
//                case WifiManager.WIFI_STATE_UNKNOWN:
//
//                    break;
//            }
//
//        }};

}
