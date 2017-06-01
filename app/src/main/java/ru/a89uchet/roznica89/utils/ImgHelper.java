package ru.a89uchet.roznica89.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 24.02.13
 * Time: 12:29
 * To change this template use File | Settings | File Templates.
 */
public class ImgHelper {


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFilePath(String filePath,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static void makeThumbForPhoto(Context cont, int photoSize){  //photoSize = 150 , 400
        String pathPhotoDir= PreferenceManager.getDefaultSharedPreferences(cont).getString("photoDir","");
        File dir=new File(pathPhotoDir);
        dir.mkdirs();
        File imgNewDir=new File(pathPhotoDir+ String.valueOf(photoSize)+"/");
        imgNewDir.mkdirs();

      //  String[] listF     =  dir.list();

        String[] listF = dir.list(new FilenameFilter() {
            public boolean accept(File dir, String fileName) {
                return fileName.endsWith(".jpg");
            }
        });
        if(listF==null){
            return;// нет файлов
        }

        for(int i = 0; i < listF.length; i++)    {
            Bitmap bmRestricted=decodeSampledBitmapFromFilePath(pathPhotoDir+listF[i],photoSize,photoSize);
            File newF=new File(imgNewDir,listF[i]);
            try {
                FileOutputStream out = new FileOutputStream(newF)  ;
                bmRestricted.compress(Bitmap.CompressFormat.JPEG, 90, out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
