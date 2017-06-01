package ru.a89uchet.roznica89.db;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;

import java.io.File;

import jxl.write.WritableSheet;
import ru.a89uchet.roznica89.Config;
import ru.a89uchet.roznica89.WriteExcel;
import ru.a89uchet.roznica89.utils.DateHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 16.12.12
 * Time: 13:16
 * To change this template use File | Settings | File Templates.
 */
public class StaticReports {

    public static File Prodagi(Context mCont){
        Cursor mCursor;
        Cursor mCursorTotal;
        Cursor[] mCursorArr;
        String dateBegin= DateHelper.getNowFormatedDate();
        String dateEnd=dateBegin;
        Context trCont=mCont;

        String repName="Продажи "+" за период c "+dateBegin+" по "+dateEnd;
          //Config.globContext.getApplicationContext()
        if(trCont==null)  {
            if(Config.globContext!=null){
                trCont=Config.globContext.getApplicationContext();
            }  else {
                return null;
            }
        }
        mCursorArr=DBConnector.instance(trCont).ProdagiZakupkiTovara(dateBegin,dateEnd,-1);
        mCursor= mCursorArr[0];
        mCursorTotal= mCursorArr[1];
        String mFileName=exportToExcel(repName,"sales "+dateBegin,mCursor);
        if(mFileName!=null){
            return new File(mFileName);
        } else {
            return null;
        }
    }


    public static String exportToExcel(String repName, String mFName, Cursor mCursor){
        WriteExcel docSheet = new WriteExcel();
        String path= Environment.getExternalStorageDirectory().getPath()+"/"+"89Account/";
        File dir=new File(path);
        dir.mkdirs();
        if(!dir.exists()){
            return null;
        }

        String docPath=path+mFName+".xls";
        try{

            docSheet.setOutputFile(docPath);
            WritableSheet excelSheet=docSheet.init("Test");
            docSheet.addlabelNoBorder(excelSheet,2,0,repName);
            docSheet.setDoubleHeight(0);

            //заголовки таблицы
            docSheet.addLabelCenter(excelSheet,1,2,"Код");//код
            docSheet.addLabelCenter(excelSheet,2,2,"Наименование товара");//Наим
            docSheet.addLabelCenter(excelSheet,3,2,"Кол-во");//Кво
            docSheet.addLabelCenter(excelSheet,4,2,"Сумма");//Сумма
            docSheet.addLabelCenter(excelSheet,5,2,"Скидка");//Скидка

            double totDiskount=0;
            double sumNoDiskount=0;
            mCursor.moveToFirst();
            int col=2;int row=3;
            while (!mCursor.isAfterLast()){
                String s1=mCursor.getString(mCursor.getColumnIndex("CODE"));
                docSheet.addLabel(excelSheet,col-1,row,s1);
                String s11=mCursor.getString(mCursor.getColumnIndex("TOVAR"));
                docSheet.addLabel(excelSheet,col,row,s11);
                double s2=mCursor.getFloat(mCursor.getColumnIndex("QTY"));
                docSheet.addNumber3(excelSheet,col+1,row,s2);

                double s3=mCursor.getFloat(mCursor.getColumnIndex("SUM"));
                docSheet.addNumber2(excelSheet,col+2,row,s3);
                sumNoDiskount=sumNoDiskount+s3;
                double s4=mCursor.getFloat(mCursor.getColumnIndex("DISC"));
                totDiskount=totDiskount+s4;
                docSheet.addNumber2(excelSheet,col+3,row,s4);
                if(s11.length()>30){
                    docSheet.setDoubleHeight(row);
                }
                row++;
                // writer.write(s1+";"+s2+";"+s3+";"+s4+";"+s5 + eol);
                mCursor.moveToNext();
            }
            // Итоги в таблице
            docSheet.addlabelNoBorder(excelSheet,col+1,row,"Итого");
            docSheet.addNumber2Total(excelSheet,col+2,row,sumNoDiskount);
            docSheet.addNumber2Total(excelSheet,col+3,row,totDiskount);
            // writer.close();
            docSheet.close();
            // mCursor.close();

        } catch (Exception e) {
            docPath="";
            e.printStackTrace();
        }
        return   docPath;
    }
}
