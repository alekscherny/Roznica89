package ru.a89uchet.roznica89.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import ua.cherny.kassir.DocObj;
import ua.cherny.kassir.GoodsActivity;
import ua.cherny.kassir.NomenGroupsActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 04.12.12
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
public class ActivityHelper {
    private static Activity currentActivity;
    private static OtborAdapter currentAdapter;
    public static final byte DATE_BEGIN = 1;
    public static final byte DATE_END = 2;
    public static final byte GOODS = 3;
    public static final byte NOMGRPUPS = 4;
    public static final byte DOCS = 11;
    public static final byte TIME_START = 12;
    public static final byte SIMPLE_STRING = 13;
    public static final byte FILE_CHOOSE = 14;

    private ActivityHelper() { }

    public static void startActivityUni(byte type, String dataUI, String dataName) {
        if (ActivityHelper.currentActivity == null) return;

        if (type == ActivityHelper.TIME_START){
            Bundle bundle1=new Bundle();
            //bundle1.putString("date1",dataName);
            ActivityHelper.currentActivity.removeDialog(type);
            ActivityHelper.currentActivity.showDialog(type,bundle1);
        }else if(type==SIMPLE_STRING){

        }

    }


    public static void startActivity(byte type, String data) {

        if (ActivityHelper.currentActivity == null) return;

        if (type == ActivityHelper.DATE_BEGIN || type == ActivityHelper.DATE_END) {
            Bundle bundle1=new Bundle();
            bundle1.putString("date1",data);
            ActivityHelper.currentActivity.removeDialog(type);
            ActivityHelper.currentActivity.showDialog(type,bundle1);
        }

        else if (type == ActivityHelper.GOODS){
            Intent gIntent = new Intent(ActivityHelper.currentActivity, GoodsActivity.class);
            gIntent.putExtra("forpodbor",1);
            ActivityHelper.currentActivity.startActivityForResult(gIntent,type);
        }else if(type == ActivityHelper.NOMGRPUPS){
            Intent gIntent = new Intent(ActivityHelper.currentActivity, NomenGroupsActivity.class);
            gIntent.putExtra("forpodbor",1);
            ActivityHelper.currentActivity.startActivityForResult(gIntent,type);
        }else if(type == ActivityHelper.DOCS){
            Intent gIntent = new Intent(ActivityHelper.currentActivity, DocObj.class);
            String[] mDocNameArr=data.split(";");
            String mDocName=mDocNameArr[0];
            String mDocid=mDocNameArr[1];
            gIntent.putExtra("docname", mDocName);
            gIntent.putExtra("docid", Long.valueOf(mDocid));
            ActivityHelper.currentActivity.startActivity(gIntent);
        }

    }

    public static void setActivity(Activity a) {
        ActivityHelper.currentActivity = a;
    }

    public static void setAdapter(OtborAdapter a) {
        ActivityHelper.currentAdapter = a;
    }

    public static OtborAdapter getAdapter(){
        return ActivityHelper.currentAdapter;
    }
}
