package ru.a89uchet.roznica89;

import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jxl.Range;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

//import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 08.06.12
 * Time: 15:38
 * To change this template use File | Settings | File Templates.
 */
public class WriteExcel {
    private WritableCellFormat totalBoldUnderline;
    private WritableCellFormat timesBoldUnderline;

    private WritableCellFormat timesCenter ;
    private WritableCellFormat times;

    private WritableCellFormat times2;
    private WritableCellFormat times3;
    private String inputFile;
    private WritableWorkbook workbook;
    private WritableSheet excelSheet;
    public WritableSheet excelSheetSecond=null;

    public WritableSheet init(String sheetTitle) throws IOException, WriteException,BiffException {
        String path= Environment.getExternalStorageDirectory().getPath()+"/"+"89Account/tmplts/doctmplt.xls";
        File fTmplts = new File(path);
        File file = new File(inputFile);

        if(fTmplts.exists()) {//есть шаблон
            Workbook workbookCop = Workbook.getWorkbook(fTmplts);
            this.workbook = Workbook.createWorkbook(file,workbookCop);
            workbookCop.close();
            this.excelSheet = workbook.getSheet(0);
        } else{
            this.workbook = Workbook.createWorkbook(file);
            this.excelSheet=this.workbook.createSheet(sheetTitle, 0);
        }

        this.excelSheet.setPageSetup(PageOrientation.PORTRAIT, PaperSize.A4,1,1);


        this.excelSheet.setColumnView(0,1);
        this.excelSheet.setColumnView(1,9);//kod
        this.excelSheet.setColumnView(2,28);     //name

       this.excelSheet.setColumnView(3,8);
       this.excelSheet.setColumnView(4,9);
       this.excelSheet.setColumnView(5,9);
       this.excelSheet.setColumnView(6,7);

        SheetSettings set1=new SheetSettings(this.excelSheet);
        set1.setPaperSize(PaperSize.A4);
        createLabel(excelSheet);
        return  this.excelSheet;

    }

    public WritableSheet initInvent(String sheetTitle) throws IOException, WriteException,BiffException {
        String path= Environment.getExternalStorageDirectory().getPath()+"/"+"89Account/tmplts/doctmplt.xls";
        File fTmplts = new File(path);
        File file = new File(inputFile);

        if(fTmplts.exists()) {//есть шаблон
            Workbook workbookCop = Workbook.getWorkbook(fTmplts);
            this.workbook = Workbook.createWorkbook(file,workbookCop);
            workbookCop.close();
            this.excelSheet = workbook.getSheet(0);
        } else{
            this.workbook = Workbook.createWorkbook(file);
            this.excelSheet=this.workbook.createSheet(sheetTitle, 0);
        }

        this.excelSheet.setPageSetup(PageOrientation.PORTRAIT, PaperSize.A4,1,1);


        this.excelSheet.setColumnView(0,1);
        this.excelSheet.setColumnView(1,9);//kod
        this.excelSheet.setColumnView(2,28);     //name

        this.excelSheet.setColumnView(3,8);
        this.excelSheet.setColumnView(4,9);
        this.excelSheet.setColumnView(5,9);
        this.excelSheet.setColumnView(6,14);    //barcode

        SheetSettings set1=new SheetSettings(this.excelSheet);
        set1.setPaperSize(PaperSize.A4);
        createLabel(excelSheet);
        return  this.excelSheet;

    }

    public WritableSheet initCheck(String sheetTitle) throws IOException, WriteException,BiffException {
        String path= Environment.getExternalStorageDirectory().getPath()+"/"+"89Account/tmplts/check.xls";
        File fTmplts = new File(path);
        File file = new File(inputFile);

        if(fTmplts.exists()) {//есть шаблон
            Workbook workbookCop = Workbook.getWorkbook(fTmplts);
            this.workbook = Workbook.createWorkbook(file,workbookCop);
            workbookCop.close();
            this.excelSheet = this.workbook.getSheet(0);
            this.excelSheetSecond=this.workbook.getSheet(1);
        } else{
            this.workbook = Workbook.createWorkbook(file);
            this.excelSheet=this.workbook.createSheet(sheetTitle, 0);
        }

        this.excelSheet.setPageSetup(PageOrientation.PORTRAIT, PaperSize.A4,1,1);

        SheetSettings set1=new SheetSettings(this.excelSheet);
        set1.setPaperSize(PaperSize.A4);
        createLabel(excelSheet);
        return  this.excelSheet;
    }

    public void close() throws IOException, WriteException {
        this.workbook.write();
        this.workbook.close();
    }

    public WritableWorkbook getWorkbook() {
        return workbook;
    }

    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }
    private void createLabel(WritableSheet sheet)
            throws WriteException {

        NumberFormat dps2 = new NumberFormat("0.00");
        NumberFormat dps3 = new NumberFormat("0.000");

        WritableFont times10center = new WritableFont(WritableFont.TIMES, 10);
        timesCenter = new WritableCellFormat(times10center);
        timesCenter.setWrap(true);
        timesCenter.setAlignment(Alignment.CENTRE);
        timesCenter.setVerticalAlignment(VerticalAlignment.CENTRE);
        timesCenter.setBorder(Border.ALL, BorderLineStyle.THIN);

        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        times = new WritableCellFormat(times10pt);
        times.setWrap(true);
        times.setBorder(Border.ALL, BorderLineStyle.THIN);

        // Define the cell format
        times2 = new WritableCellFormat(times10pt,dps2);
       // times2 = new WritableCellFormat(dps2);
        // Lets automatically wrap the cells
        times2.setWrap(true);
        times2.setBorder(Border.ALL, BorderLineStyle.THIN);

        //times3 = new WritableCellFormat(dps3);
        times3 = new WritableCellFormat(times10pt,dps3);
        // Lets automatically wrap the cells
        times3.setWrap(true);
        times3.setBorder(Border.ALL, BorderLineStyle.THIN);

        // Create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 9, WritableFont.BOLD, false,
              UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setShrinkToFit(true);

        totalBoldUnderline=new WritableCellFormat(times10ptBoldUnderline,dps2);
        totalBoldUnderline.setWrap(true);
        totalBoldUnderline.setBorder(Border.ALL, BorderLineStyle.THIN);

    }

    public void setDoubleHeight(  int row)
            throws WriteException {
        this.excelSheet.setRowView(row,2*this.excelSheet.getRowView(row).getSize());
    }

    public void addlabelNoBorder(WritableSheet sheet, int column, int row, String s)
            throws WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }

    public void addNumber2(WritableSheet sheet, int column, int row,
                           Double val) throws WriteException {
        Number number;
        number = new Number(column, row, val, times2); //
        sheet.addCell(number);
    }



    public void addNumber2Total(WritableSheet sheet, int column, int row,
                                Double val) throws WriteException {
        Number number;
        number = new Number(column, row, val, totalBoldUnderline); //  times2
        sheet.addCell(number);
    }
    public void addNumber3(WritableSheet sheet, int column, int row,
                           Double val) throws WriteException {
        Number number;
        number = new Number(column, row, val, times3); //
        sheet.addCell(number);
    }

    public void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times); //, times

        sheet.addCell(label);
    }

    public void addLabelCenter(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, timesCenter); //, times
        sheet.addCell(label);
    }

    public void replaceInTmpltLabel(WritableSheet sheetForRelace, Range[] ranges, HashMap<String,String> keyVal){
        boolean  txtUpdated=false;
        Label curCell;
        String cellText ;

        for(Range curArea : ranges){
            int rowStart=curArea.getTopLeft().getRow();
            int rowEnd=curArea.getBottomRight().getRow();
            int colStart=curArea.getTopLeft().getColumn();
            int colEnd=curArea.getBottomRight().getColumn();
            for (int i = rowStart ; i <= rowEnd ; i++)   //строки
            {
                for (int j = colStart ; j <= colEnd ; j++)
                {
                   curCell=(Label)sheetForRelace.getWritableCell(j,i);
                   cellText=curCell.getString();
                    if(cellText==null||cellText.length()==0)
                         continue;

                    txtUpdated=false;
                    for (Map.Entry<String, String> entry : keyVal.entrySet()) {
                        if(cellText.contains(entry.getKey())) {
                            cellText=cellText.replace(entry.getKey(),entry.getValue());
                            txtUpdated=true;
                        }
                    }
                    if(txtUpdated)
                      curCell.setString(cellText);
                }
        }   }
    }

   public void replaceInCellLabel(Label cell, HashMap<String,String> keyVal){
       boolean  txtUpdated=false;
       String cellText ;
       cellText=cell.getString();

       for (Map.Entry<String, String> entry : keyVal.entrySet()) {
           if(cellText.contains(entry.getKey())) {
               cellText=cellText.replace(entry.getKey(),entry.getValue());
               txtUpdated=true;
           }
       }
       if(txtUpdated)
           cell.setString(cellText);
   }
}
