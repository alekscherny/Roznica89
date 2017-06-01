package ru.a89uchet.roznica89.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 09.10.12
 * Time: 17:12
 * To change this template use File | Settings | File Templates.
 */
public class lic {

    public  static boolean checkLic1(Context cont){
        SharedPreferences sp = cont.getSharedPreferences("lic_db", Context.MODE_PRIVATE);
        String uuid=sp.getString("uuid","");
        String pin=sp.getString("pin","");
        String res=sp.getString("res","");
        //String real_uuid=getUUID(cont);
        if(res.equals(uuid+"_"+pin))
          return true;
        else
           return false;
    }

    public static String getUUID(Context cont){
        return android.provider.Settings.Secure.getString(cont.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }

    public static boolean setlic1(Context cont, String pin){
        String mUUID=  getUUID(cont);
        SharedPreferences sp = cont.getSharedPreferences("lic_db", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("pin", pin);
        e.putString("uuid", mUUID);
        e.putString("res", mUUID+"_"+pin);
        e.commit();
        return checkLic1(cont);
    }
}
