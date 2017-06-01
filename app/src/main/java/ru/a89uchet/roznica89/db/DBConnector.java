package ru.a89uchet.roznica89.db;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import ru.a89uchet.roznica89.utils.DateHelper;


public class DBConnector {
	private SQLiteDatabase db;
	private MyDatabaseHelper dbHelper;
	static private DBConnector _instance;
	Context mAppContext;

	protected DBConnector(Context _context){
		this.dbHelper=MyDatabaseHelper.instance(_context);
        mAppContext= _context;
	}
	
	public static DBConnector instance (Context _context){
		if (_instance == null) {
			_instance = new DBConnector(_context);
		}
		return _instance;	
	}
	
	public SQLiteDatabase getDB(){
		if(this.db==null){
			this.open();
		}
		return db;
	}
	
	public void clearDbAndCreate(){
		if(this.db==null){
			this.open();
		}
		this.dbHelper.clearDB(this.db);
	}
   /* public void clearGoods(){
        if(this.db==null){
            this.open();
        }
        delDopRekById(-1,MetaDatabase.Goods.GOODS_TABLE);
        this.dbHelper.clearGoods(this.db);
    }*/
    public void clearDocs(){
        if(this.db==null){
            this.open();
        }
        delDopRekById(-1,MetaDatabase.Docs.DOCS_TABLE);
        this.dbHelper.clearDocs(this.db);
    }

    public Cursor getSkladList(){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.Sklads._ID +", "
                +  MetaDatabase.Sklads.NAME+", "
                +  MetaDatabase.Sklads.EMAIL+", "
                +  MetaDatabase.Sklads.CODE
                + " FROM "
                + MetaDatabase.Sklads.Sklads_TABLE  ;
        return db.rawQuery(query,null);
    }

    public Cursor getFirmList(){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.Firms._ID +", "
                +  MetaDatabase.Firms.NAME+", "
                +  MetaDatabase.Firms.CODE+", "
                +  MetaDatabase.Firms.FORMA_OPLAT
                + " FROM "
                + MetaDatabase.Firms.Firms_TABLE  ;
        return db.rawQuery(query,null);
    }
    public Cursor getFOplatList(){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.FormaOplat._ID +", "
                +  MetaDatabase.FormaOplat.CODE+", "
                +  MetaDatabase.FormaOplat.NAME
                + " FROM "
                + MetaDatabase.FormaOplat.FormaOplat_TABLE  ;
        return db.rawQuery(query,null);
    }
    public ArrayList<HashMap<String, String>> getTaskList(){
        HashMap<String,String> obj;
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.Tasks._ID +" AS id, "
                +  MetaDatabase.Tasks.NAME+", "
                +  MetaDatabase.Tasks.ISACTIVE+", "
                +  MetaDatabase.Tasks.REPEATE_INTERVAL+", "
                +  MetaDatabase.Tasks.REPEAT_EDINICA+", "
                +  MetaDatabase.Tasks.DROPBOX_SINH+", "
                +  MetaDatabase.Tasks.EMAIL_TO+", "
                +  MetaDatabase.Tasks.SMS_TO_PHONE+", "
                +  MetaDatabase.Tasks.STARTON
                + " FROM "
                + MetaDatabase.Tasks.TABLE_NAME  ;
        Cursor mCursor=db.rawQuery(query,null);
        String[] nnames=mCursor.getColumnNames();
        ArrayList<HashMap<String,String>> res=new ArrayList<HashMap<String, String>>();

        while (mCursor.moveToNext()){
            obj=new HashMap<String, String>();

            for(String field:nnames){
                obj.put(field,mCursor.getString(mCursor.getColumnIndex(field)));
            }
            res.add(obj);
        }

        return  res;
    }

    public Cursor getNomGroupListForSite(){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.NomenGroups._ID +", "
                +  MetaDatabase.NomenGroups.PARENT_ID +", "
                +  MetaDatabase.NomenGroups.NAME
                + " FROM "
                + MetaDatabase.NomenGroups.NomenGroups_TABLE
                + " ORDER BY "+MetaDatabase.NomenGroups.NAME;
        return db.rawQuery(query,null);
    }

   /* public Cursor getNomGroupList(){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.NomenGroups._ID +", "
                +  MetaDatabase.NomenGroups.NAME
                + " FROM "
                + MetaDatabase.NomenGroups.NomenGroups_TABLE
                + " ORDER BY "+MetaDatabase.NomenGroups.NAME;
        return db.rawQuery(query,null);
    }*/
    public Cursor getNomGroupList1UrovAll(){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.NomenGroups._ID +", "
                +  MetaDatabase.NomenGroups.NAME
                + " FROM "
                + MetaDatabase.NomenGroups.NomenGroups_TABLE
                + " WHERE "+ MetaDatabase.NomenGroups.PARENT_ID+" = -1"
                + " ORDER BY "+MetaDatabase.NomenGroups.NAME;
        return db.rawQuery(query,null);
    }
    public Cursor getNomGroupList2UrovAll(){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.NomenGroups._ID +", "
                +  MetaDatabase.NomenGroups.NAME
                + " FROM "
                + MetaDatabase.NomenGroups.NomenGroups_TABLE
                + " WHERE "+ MetaDatabase.NomenGroups.PARENT_ID+" <> -1"
                + " ORDER BY "+MetaDatabase.NomenGroups.NAME;
        return db.rawQuery(query,null);
    }

    //if  parenid=0-без родителя !!
    public Cursor getNomGroupList2ByParent(long parenid){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.NomenGroups._ID +", "
                +  MetaDatabase.NomenGroups.NAME
                + " FROM "
                + MetaDatabase.NomenGroups.NomenGroups_TABLE
                + " WHERE "+ MetaDatabase.NomenGroups.PARENT_ID+" = "+parenid
                + " ORDER BY "+MetaDatabase.NomenGroups.NAME;
        return db.rawQuery(query,null);
    }
    public Cursor getUsersList(){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.Users._ID +", "
                +  MetaDatabase.Users.UserName
                + " FROM "
                + MetaDatabase.Users.Users_TABLE  ;
        return db.rawQuery(query,null);
    }
    public int delUser(String mUser){
        if(this.db==null){
            this.open();
        }
        if(mUser.equals("Admin")){
            return 0;
        }
        String where=MetaDatabase.Users.UserName +  " = ? ";
        String[] whereArgs={String.valueOf(mUser)};
        return this.db.delete(MetaDatabase.Users.Users_TABLE,where,whereArgs);
    }

    public long createUpdateUser(String mUser, String mPass, boolean isCreate){
        long rowId;
        if(this.db==null){
            this.open();
        }
        ContentValues dataStr=new ContentValues();
        dataStr.put(MetaDatabase.Users.UserName,mUser);
        dataStr.put(MetaDatabase.Users.UserPassword,mPass);
        if(isCreate){
         rowId=  this.db.insert(MetaDatabase.Users.Users_TABLE,null,dataStr);
        }else {
          rowId=db.replace(MetaDatabase.Users.Users_TABLE, null, dataStr);
        }
        return rowId;
    }

    public boolean checkIsAdminCreated(){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.Users._ID
                + " FROM "
                + MetaDatabase.Users.Users_TABLE
                + " WHERE "+MetaDatabase.Users.UserName+" = 'Admin'";
        Cursor mCursor=db.rawQuery(query,null);
        if (mCursor.moveToFirst()){
            mCursor.close();
            return true;
        }  else{
            mCursor.close();
            return false;
        }
    }

    public void insertAdminIfNotCreated(){
        if(!checkIsAdminCreated()){
          ContentValues dataStr=new ContentValues();
          dataStr.put(MetaDatabase.Users.UserName,"Admin");
          dataStr.put(MetaDatabase.Users.UserPassword,"");
          db.insert(MetaDatabase.Users.Users_TABLE,null,dataStr);
       }
    }
   // ContentValues dataStr=new ContentValues();
 //   dataStr.put(Users.UserName,"Admin");
 //   dataStr.put(Users.UserPassword,"");
 //   db.insert(Users.Users_TABLE,null,dataStr);

    public boolean userLoginOK(String mUser, String mPass){
        boolean res=false;
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.Users._ID
                + " FROM "
                + MetaDatabase.Users.Users_TABLE
                + " WHERE "+MetaDatabase.Users.UserName+" = '"+mUser+"'"
                + " AND "+MetaDatabase.Users.UserPassword+" = '"+mPass+"'";

        Cursor mCursor=db.rawQuery(query,null);
        if (mCursor.moveToFirst()){
            res=true;
        }
        mCursor.close();
        return res;
    }

    public Cursor getDopReksById(long mId, String mTableName){
        if(this.db==null){
            this.open();
        }
        String join="t1."+MetaDatabase.DopRekvizitsRef._ID+" = "+"t2."+ MetaDatabase.RegistrDopReks.DRRID;
        String where=" WHERE t1."+MetaDatabase.DopRekvizitsRef.OWNERTABLE+" = '"+mTableName+"'";
        join=join+" AND " + "  t2."+MetaDatabase.RegistrDopReks.OWNERID+" = "+mId+"";

        String query="SELECT "
                + "t1."+ MetaDatabase.DopRekvizitsRef._ID +", "
                + "t1."+ MetaDatabase.DopRekvizitsRef.NAME+", "
                + "t1."+ MetaDatabase.DopRekvizitsRef.TYPE+", "
                + "ifnull(t2."+ MetaDatabase.RegistrDopReks.VAL+",'') AS "+MetaDatabase.RegistrDopReks.VAL+", "
                + "ifnull(t2."+ MetaDatabase.RegistrDopReks._ID +",'') AS TMPID"
                + " FROM "
                + MetaDatabase.DopRekvizitsRef.DopRekvizitsRef_TABLE +" AS t1 "+" LEFT JOIN "
                + MetaDatabase.RegistrDopReks.RegistrDopReks_TABLE+" AS t2 ON " +join
                + where
                ;

        return db.rawQuery(query,null);
    }

    public void updOrInsDopRek(String mValue, long ownerId, long doprekId, long thisId){
        if(this.db==null){
            this.open();
        }
        ContentValues dataStr=new ContentValues();
        dataStr.put(MetaDatabase.RegistrDopReks.VAL,mValue);
        dataStr.put(MetaDatabase.RegistrDopReks.OWNERID,ownerId);
        dataStr.put(MetaDatabase.RegistrDopReks.DRRID,doprekId);
        if(thisId==0){ //insert
            this.db.insert(MetaDatabase.RegistrDopReks.RegistrDopReks_TABLE,null,dataStr);
        }   else{           //update
            dataStr.put(MetaDatabase.RegistrDopReks._ID,thisId);
            this.db.replace(MetaDatabase.RegistrDopReks.RegistrDopReks_TABLE,null,dataStr) ;
        }
    }
    // удаляет доп реквизит и его связи с регистром элементов
    public void delDopRek(long mId){
        if(this.db==null){
            this.open();
        }

        db.beginTransaction();
        try {
          String whereRowDoc= MetaDatabase.RegistrDopReks.DRRID +  " = ?";
          String[] whereArgsRowDoc={String.valueOf(mId)};
          this.db.delete(MetaDatabase.RegistrDopReks.RegistrDopReks_TABLE,whereRowDoc,whereArgsRowDoc);

          String[] whereArgsDoc = {String.valueOf(mId)};
          this.db.delete(MetaDatabase.DopRekvizitsRef.DopRekvizitsRef_TABLE,"_ID=?",whereArgsDoc);
          db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    // удаляет доп.реквизит из регистра по ID владельца (товар,док,клиент)
    //если mId = -1 то для всех елементов указанной таблицы
    public void delDopRekById(long mId,String mTableName){
        String vectorStr="(";
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.DopRekvizitsRef._ID
                + " FROM "
                + MetaDatabase.DopRekvizitsRef.DopRekvizitsRef_TABLE
                + " WHERE "+MetaDatabase.DopRekvizitsRef.OWNERTABLE+" = '"+mTableName+"'";
        Cursor mCursor=db.rawQuery(query,null);

        //нет доп рек, принадлежащих данной таблице -выходим
        if(!mCursor.moveToFirst()){
            mCursor.close();
            return;
        }
        while (!mCursor.isAfterLast()){

            String mcID=  mCursor.getString(0);
            vectorStr=vectorStr+mcID+",";
            mCursor.moveToNext();
        }
        vectorStr=vectorStr.substring(0,vectorStr.length()-1)+")";
        mCursor.close();

        if(mId!=-1) {
         String where=MetaDatabase.RegistrDopReks.OWNERID +  " = ? AND "+MetaDatabase.RegistrDopReks.DRRID+" IN "+vectorStr;
         String[] whereArgs={String.valueOf(mId)};
         this.db.delete(MetaDatabase.RegistrDopReks.RegistrDopReks_TABLE,where,whereArgs);
        }else{
            String where=MetaDatabase.RegistrDopReks.DRRID+" IN "+vectorStr;
            String[] whereArgs={};
            this.db.delete(MetaDatabase.RegistrDopReks.RegistrDopReks_TABLE,where,whereArgs);
        }
    }
    ///////PKO RKO
    public void delPKORKO(long id, String tableName, String DOC_TYPE){
        if(this.db==null){
            this.open();
        }
        String[] whereArgsDoc = {String.valueOf(id)};
        this.db.delete(tableName,"_ID=?",whereArgsDoc);
        //удаляем взаиморасчеты
        delVzaimRecordAndUpdBalnce(id,DOC_TYPE);
    }

    public Cursor getDocPKORKOData(long id, String tableName){
        if(this.db==null){
            this.open();
        }

        String query="SELECT "
                + MetaDatabase.PKO.DATE_DOCS+", "
                + MetaDatabase.PKO.KLIENT+", "
                + MetaDatabase.PKO.STATUS+", "
                + MetaDatabase.PKO.SUM+", "
                + MetaDatabase.PKO.COMMENT+", "
                + MetaDatabase.PKO.NOMER+", "
                +MetaDatabase.PKO.USER
                + " FROM "
                + tableName
                + " WHERE _ID = "+id+""
                ;
        return db.rawQuery(query,null);
    }


    public Cursor getRKODocsList(String dateBegin, String dateEnd, String klientCode){
        if(this.db==null){
            this.open();
        }

        if(dateBegin==null || dateBegin.length()<8){
            dateBegin= "2000-01-01";
        }
        if(dateEnd==null || dateEnd.length()<8){
            dateEnd= "2099-01-01";
        }

        String join="t1."+MetaDatabase.RKO.KLIENT+" = "+"t2."+ MetaDatabase.Klients.CODE;

        String where=" WHERE date(t1."+MetaDatabase.RKO.DATE_DOCS+") BETWEEN  '"+dateBegin+"' AND '"+dateEnd+"'";
        if(klientCode!=null && klientCode.length()>0) {
            where= where+" AND t1."+MetaDatabase.RKO.KLIENT+" =  '"+klientCode+"' ";
        }
        String query="SELECT "
                + "t1."+ MetaDatabase.RKO._ID +", "
                + "t1."+ MetaDatabase.RKO.DATE_DOCS+", "
                + "t1."+ MetaDatabase.RKO.COMMENT +", "
                + "t1."+ MetaDatabase.RKO.NOMER +", "
                + "t1."+ MetaDatabase.RKO.STATUS +", "
                + "CAST(t1."+ MetaDatabase.RKO.SUM+" AS TEXT) AS "+MetaDatabase.RKO.SUM+", "
                + "t1."+ MetaDatabase.RKO.KLIENT+", "
                + "t2."+ MetaDatabase.Klients.NAME+", "
                + "t1."+ MetaDatabase.RKO.USER+" "
                + " FROM "
                + MetaDatabase.RKO.TABLE_NAME +" AS t1 "+" LEFT JOIN "
                + MetaDatabase.Klients.Klients_TABLE+" AS t2 ON " +join
                + where
                + " ORDER BY "+MetaDatabase.RKO.DATE_DOCS
                ;
        return db.rawQuery(query,null);
    }
    public Cursor getPKODocsList(String dateBegin, String dateEnd, String klientCode){
        if(this.db==null){
            this.open();
        }

        if(dateBegin==null || dateBegin.length()<8){
            dateBegin= "2000-01-01";
        }
        if(dateEnd==null || dateEnd.length()<8){
            dateEnd= "2099-01-01";
        }

        String join="t1."+MetaDatabase.PKO.KLIENT+" = "+"t2."+ MetaDatabase.Klients.CODE;

        String where=" WHERE date(t1."+MetaDatabase.PKO.DATE_DOCS+") BETWEEN  '"+dateBegin+"' AND '"+dateEnd+"'";

        if(klientCode!=null && klientCode.length()>0) {
                where= where+" AND t1."+MetaDatabase.PKO.KLIENT+" =  '"+klientCode+"' ";
        }

        String query="SELECT "
                + "t1."+ MetaDatabase.PKO._ID +", "
                + "t1."+ MetaDatabase.PKO.DATE_DOCS+", "
                + "t1."+ MetaDatabase.PKO.COMMENT +", "
                + "t1."+ MetaDatabase.PKO.NOMER +", "
                + "t1."+ MetaDatabase.PKO.STATUS +", "
                + "CAST(t1."+ MetaDatabase.PKO.SUM+" AS TEXT) AS "+MetaDatabase.PKO.SUM+", "
                + "t1."+ MetaDatabase.PKO.KLIENT+", "
                + "t2."+ MetaDatabase.Klients.NAME+", "
                + "t1."+ MetaDatabase.PKO.USER+" "
                + " FROM "
                + MetaDatabase.PKO.TABLE_NAME +" AS t1 "+" LEFT JOIN "
                + MetaDatabase.Klients.Klients_TABLE+" AS t2 ON " +join
                + where
                + " ORDER BY "+MetaDatabase.PKO.DATE_DOCS
                ;
        return db.rawQuery(query,null);
    }


    public Cursor getAllDocsListToServer(String docname, String dateBegin, String dateEnd){
        if(this.db==null){
            this.open();
        }

        if(dateBegin==null || dateBegin.length()<8){
            dateBegin= "2000-01-01";
        }
        if(dateEnd==null || dateEnd.length()<8){
            dateEnd= "2099-01-01";
        }

        String where=" WHERE date(t1."+MetaDatabase.Docs.DATE_DOCS+") BETWEEN  '"+dateBegin+"' AND '"+dateEnd+"'";
        String join="t1."+MetaDatabase.Docs.KLIENT+" = "+"t2."+ MetaDatabase.Klients.CODE;
        String join2="t1."+MetaDatabase.Docs.SKLAD+" = "+"t3."+ MetaDatabase.Sklads._ID;
        if(docname.length()>0){
            where=where+" AND t1."+MetaDatabase.Docs.NAME+" LIKE '"+docname+"' ";
            //where=where+" AND t1."+MetaDatabase.Docs.KLIENT+" = "+"t2."+ MetaDatabase.Klients.CODE;
        }
        //   if(where.length()==0){
        //       where=" WHERE date(t1."+MetaDatabase.Docs.DATE_DOCS+") BETWEEN  '"+dateBegin+"' AND '"+dateEnd+"'";
        //  }  else{

        //  }


        String query="SELECT "
                + "t1."+ MetaDatabase.Docs._ID +", "
                + "t1."+ MetaDatabase.Docs.DATE_DOCS+", "
                + "t1."+ MetaDatabase.Docs.NAME +", "
                + "t1."+ MetaDatabase.Docs.IN_NUM +", "
                + "t1."+ MetaDatabase.Docs.COMMENT +", "
                + "CAST(t1."+ MetaDatabase.Docs.SUM+" AS TEXT) AS "+MetaDatabase.Docs.SUM+", "
                + "t1."+ MetaDatabase.Docs.KLIENT+", "
                + "t2."+ MetaDatabase.Klients.NAME+", "
                + "t1."+ MetaDatabase.Docs.USER+", "
                + "t1."+ MetaDatabase.Docs.SKLAD +", "
                + "t1."+ MetaDatabase.Docs.FIRM +", "
                + "t3."+ MetaDatabase.Sklads.NAME
                + " FROM "
                + MetaDatabase.Docs.DOCS_TABLE +" AS t1 "+" LEFT JOIN "
                + MetaDatabase.Klients.Klients_TABLE+" AS t2 ON " +join
                + " LEFT JOIN "+MetaDatabase.Sklads.Sklads_TABLE+" AS t3 ON "+join2
                + where
                + " ORDER BY "+MetaDatabase.Docs.SORT_DEF
                ;
        return db.rawQuery(query,null);
    }


    public Cursor getDocsListNotUploadedToServer(String docname, String dateBegin, String dateEnd){
        if(this.db==null){
            this.open();
        }

        if(dateBegin==null || dateBegin.length()<8){
            dateBegin= "2000-01-01";
        }
        if(dateEnd==null || dateEnd.length()<8){
            dateEnd= "2099-01-01";
        }

        String where=" WHERE t1."+MetaDatabase.Docs.UPLOADED+" <> 1";
        String join="t1."+MetaDatabase.Docs.KLIENT+" = "+"t2."+ MetaDatabase.Klients.CODE;
        String join2="t1."+MetaDatabase.Docs.SKLAD+" = "+"t3."+ MetaDatabase.Sklads._ID;
        if(docname.length()>0){
            where=where+" AND t1."+MetaDatabase.Docs.NAME+" LIKE '"+docname+"' ";
            //where=where+" AND t1."+MetaDatabase.Docs.KLIENT+" = "+"t2."+ MetaDatabase.Klients.CODE;
        }
     //   if(where.length()==0){
     //       where=" WHERE date(t1."+MetaDatabase.Docs.DATE_DOCS+") BETWEEN  '"+dateBegin+"' AND '"+dateEnd+"'";
      //  }  else{
            where= where+" AND date(t1."+MetaDatabase.Docs.DATE_DOCS+") BETWEEN  '"+dateBegin+"' AND '"+dateEnd+"'";
      //  }


        String query="SELECT "
                + "t1."+ MetaDatabase.Docs._ID +", "
                + "t1."+ MetaDatabase.Docs.DATE_DOCS+", "
                + "t1."+ MetaDatabase.Docs.NAME +", "
                + "t1."+ MetaDatabase.Docs.IN_NUM +", "
                + "t1."+ MetaDatabase.Docs.COMMENT +", "
                + "CAST(t1."+ MetaDatabase.Docs.SUM+" AS TEXT) AS "+MetaDatabase.Docs.SUM+", "
                + "t1."+ MetaDatabase.Docs.KLIENT+", "
                + "t2."+ MetaDatabase.Klients.NAME+", "
                + "t1."+ MetaDatabase.Docs.USER+", "
                + "t1."+ MetaDatabase.Docs.SKLAD +", "
                + "t1."+ MetaDatabase.Docs.FIRM +", "
                + "t3."+ MetaDatabase.Sklads.NAME
                + " FROM "
                + MetaDatabase.Docs.DOCS_TABLE +" AS t1 "+" LEFT JOIN "
                + MetaDatabase.Klients.Klients_TABLE+" AS t2 ON " +join
                + " LEFT JOIN "+MetaDatabase.Sklads.Sklads_TABLE+" AS t3 ON "+join2
                + where
                + " ORDER BY "+MetaDatabase.Docs.SORT_DEF
                ;
        return db.rawQuery(query,null);
    }


    public Cursor getOneDocToServer(long docID){
        if(this.db==null){
            this.open();
        }



        String where=" WHERE t1."+MetaDatabase.Docs._ID+" =  "+ String.valueOf(docID)+" ";
        String join="t1."+MetaDatabase.Docs.KLIENT+" = "+"t2."+ MetaDatabase.Klients.CODE;
        String join2="t1."+MetaDatabase.Docs.SKLAD+" = "+"t3."+ MetaDatabase.Sklads._ID;


        String query="SELECT "
                + "t1."+ MetaDatabase.Docs._ID +", "
                + "t1."+ MetaDatabase.Docs.DATE_DOCS+", "
                + "t1."+ MetaDatabase.Docs.NAME +", "
                + "t1."+ MetaDatabase.Docs.IN_NUM +", "
                + "t1."+ MetaDatabase.Docs.COMMENT +", "
                + "CAST(t1."+ MetaDatabase.Docs.SUM+" AS TEXT) AS "+MetaDatabase.Docs.SUM+", "
                + "t1."+ MetaDatabase.Docs.KLIENT+", "
                + "t2."+ MetaDatabase.Klients.NAME+", "
                + "t1."+ MetaDatabase.Docs.USER+", "
                + "t1."+ MetaDatabase.Docs.SKLAD +", "
                + "t1."+ MetaDatabase.Docs.FIRM +", "
                + "t3."+ MetaDatabase.Sklads.NAME
                + " FROM "
                + MetaDatabase.Docs.DOCS_TABLE +" AS t1 "+" LEFT JOIN "
                + MetaDatabase.Klients.Klients_TABLE+" AS t2 ON " +join
                + " LEFT JOIN "+MetaDatabase.Sklads.Sklads_TABLE+" AS t3 ON "+join2
                + where
                + " ORDER BY "+MetaDatabase.Docs.SORT_DEF
                ;
        return db.rawQuery(query,null);
    }


    public Cursor getDocsList(String docname, String dateBegin, String dateEnd, String klientCode){
        if(this.db==null){
            this.open();
        }

        if(dateBegin==null || dateBegin.length()<8){
            dateBegin= "2000-01-01";
        }
        if(dateEnd==null || dateEnd.length()<8){
            dateEnd= "2099-01-01";
        }

        String where="";
        String join="t1."+MetaDatabase.Docs.KLIENT+" = "+"t2."+ MetaDatabase.Klients.CODE;
        String join2="t1."+MetaDatabase.Docs.SKLAD+" = "+"t3."+ MetaDatabase.Sklads._ID;
        if(docname.length()>0){
             where=" WHERE t1."+MetaDatabase.Docs.NAME+" LIKE '"+docname+"' ";
            //where=where+" AND t1."+MetaDatabase.Docs.KLIENT+" = "+"t2."+ MetaDatabase.Klients.CODE;
        }
        if(where.length()==0){
            where=" WHERE date(t1."+MetaDatabase.Docs.DATE_DOCS+") BETWEEN  '"+dateBegin+"' AND '"+dateEnd+"'";
        }  else{
            where= where+" AND date(t1."+MetaDatabase.Docs.DATE_DOCS+") BETWEEN  '"+dateBegin+"' AND '"+dateEnd+"'";
        }

        if(klientCode!=null && klientCode.length()>0) {
            if(where.length()==0){
                where=" WHERE t2."+MetaDatabase.Klients.CODE+" =  '"+klientCode+"' ";
            }  else{
                where= where+" AND t2."+MetaDatabase.Klients.CODE+" =  '"+klientCode+"' ";
            }
        }

        String query="SELECT "
                + "t1."+ MetaDatabase.Docs._ID +", "
                + "t1."+ MetaDatabase.Docs.DATE_DOCS+", "
                + "t1."+ MetaDatabase.Docs.NAME +", "
                + "t1."+ MetaDatabase.Docs.COMMENT +", "
                + "CAST(t1."+ MetaDatabase.Docs.SUM+" AS TEXT) AS "+MetaDatabase.Docs.SUM+", "
                + "t1."+ MetaDatabase.Docs.KLIENT+", "
                + "t2."+ MetaDatabase.Klients.NAME+", "
                + "t1."+ MetaDatabase.Docs.USER+", "
                + "t1."+ MetaDatabase.Docs.SKLAD +", "
                + "t3."+ MetaDatabase.Sklads.NAME +", "
                + "t1."+MetaDatabase.Docs.UPLOADED
                + " FROM "
                + MetaDatabase.Docs.DOCS_TABLE +" AS t1 "+" LEFT JOIN "
                + MetaDatabase.Klients.Klients_TABLE+" AS t2 ON " +join
                + " LEFT JOIN "+MetaDatabase.Sklads.Sklads_TABLE+" AS t3 ON "+join2
                + where
                + " ORDER BY "+MetaDatabase.Docs.SORT_DEF
                ;
        return db.rawQuery(query,null);
    }

    public long saveDoc(String docname, String docTag, String sum, String diskperc, long mId, String mdate, int prihRash, String klient, long skladId, String firmCode, String mOplata) {
        if(this.db==null){
            this.open();
        }
        long rowId=-1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues tmpdata=new ContentValues();

        tmpdata.put(MetaDatabase.Docs.NAME, docname);
        tmpdata.put(MetaDatabase.Docs.COMMENT,docTag);
        tmpdata.put(MetaDatabase.Docs.SUM,sum);
        tmpdata.put(MetaDatabase.Docs.DISCPERC,diskperc);
        tmpdata.put(MetaDatabase.Docs.KLIENT,klient);
        tmpdata.put(MetaDatabase.Docs.FLAGPRIHRASH,prihRash);
        tmpdata.put(MetaDatabase.Docs.SKLAD,skladId);
        tmpdata.put(MetaDatabase.Docs.FIRM,firmCode);
        tmpdata.put(MetaDatabase.Docs.FOPLAT,mOplata);

        if(mId==0){
            mdate= dateFormat.format(date);//не удалять передаем обратно
           tmpdata.put(MetaDatabase.Docs.DATE_DOCS,  dateFormat.format(date));
            //Вставим Юзера тоько при первой вставке !!!!
            SharedPreferences sp =  mAppContext.getApplicationContext().getSharedPreferences("global_db", Context.MODE_PRIVATE);
            String curUser=sp.getString("lastLogin","Admin");
            tmpdata.put(MetaDatabase.Docs.USER,curUser);

            rowId=db.insert(MetaDatabase.Docs.DOCS_TABLE, null, tmpdata);
        }else{
            if(mdate==""){
                mdate=dateFormat.format(date);
            }
            tmpdata.put(MetaDatabase.Docs.DATE_DOCS, mdate);
            String whereClause=MetaDatabase.Docs._ID +  " = ?";
            String[] whereArgs={String.valueOf(mId)};

             //tmpdata.put(MetaDatabase.Docs._ID, mId);
            //rowId=db.replace(MetaDatabase.Docs.DOCS_TABLE, null, tmpdata);
            rowId=db.update(MetaDatabase.Docs.DOCS_TABLE,  tmpdata,whereClause,whereArgs);
        }

        return rowId;
    }
 //////// ELECTRON DOC


    public long findElecronDocID(String docTag, String clientCode, String sum){

            if(this.db==null){
                this.open();
            }


            String query="SELECT "
                    +  MetaDatabase.Docs._ID
                    + " FROM "
                    + MetaDatabase.Docs.DOCS_TABLE
                    + " WHERE "+MetaDatabase.Docs.COMMENT+" = '"+docTag+"'"
                    + " AND "+ MetaDatabase.Docs.KLIENT+" = '"+clientCode+"'"
                    + " AND "+ MetaDatabase.Docs.SUM+" = '"+sum+"'"
                    ;
            Cursor mCursor=db.rawQuery(query,null);

            if (mCursor.moveToFirst()){

                return (mCursor.getLong(0));

            }

            return 0;


    }

    // тип документа docTag=входящий номер=коммент

    public long createElectronDoc(String docname, String docTag, String sum, String diskperc, String mdate, int prihRash, String klient, long skladId){
        if(this.db==null){
            this.open();
        }
        long rowId=0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        ContentValues tmpdata=new ContentValues();

        tmpdata.put(MetaDatabase.Docs.NAME, docname);
        tmpdata.put(MetaDatabase.Docs.COMMENT,docTag);
        tmpdata.put(MetaDatabase.Docs.SUM,sum);
        tmpdata.put(MetaDatabase.Docs.DISCPERC,diskperc);
        tmpdata.put(MetaDatabase.Docs.KLIENT,klient);
        tmpdata.put(MetaDatabase.Docs.FLAGPRIHRASH,prihRash);
        tmpdata.put(MetaDatabase.Docs.SKLAD,skladId);
        tmpdata.put(MetaDatabase.Docs.IN_NUM,docTag);

        mdate= dateFormat.format(date);//не удалять передаем обратно
        tmpdata.put(MetaDatabase.Docs.DATE_DOCS,  dateFormat.format(date));
        //Вставим Юзера тоько при первой вставке !!!!
        SharedPreferences sp =  mAppContext.getApplicationContext().getSharedPreferences("global_db", Context.MODE_PRIVATE);
        String curUser=sp.getString("lastLogin","Admin");
        tmpdata.put(MetaDatabase.Docs.USER,curUser);

        rowId=db.insert(MetaDatabase.Docs.DOCS_TABLE, null, tmpdata);

        return rowId;
    }



   /////////// ELECTRON DOC


    public long saveDocPKORKO(long mId, String docTablename, String mdate, String docNumber, String docComment, String sum, String klient) {
       //mId=0 если это новый !
        if(this.db==null){
            this.open();
        }
        long rowId=-1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues tmpdata=new ContentValues();

        tmpdata.put(MetaDatabase.PKO.COMMENT,docComment);
        tmpdata.put(MetaDatabase.PKO.SUM,sum);
        tmpdata.put(MetaDatabase.PKO.NOMER,docNumber);
        tmpdata.put(MetaDatabase.PKO.KLIENT,klient);



        if(mId==0){
            mdate= dateFormat.format(date);//не удалять передаем обратно
            tmpdata.put(MetaDatabase.PKO.DATE_DOCS,  dateFormat.format(date));
            //Вставим Юзера тоько при первой вставке !!!!
            SharedPreferences sp =  mAppContext.getApplicationContext().getSharedPreferences("global_db", Context.MODE_PRIVATE);
            String curUser=sp.getString("lastLogin","Admin");
            tmpdata.put(MetaDatabase.PKO.USER,curUser);

            rowId=db.insert(docTablename, null, tmpdata);
        }else{
            if(mdate==""){
                mdate=dateFormat.format(date);
            }
            tmpdata.put(MetaDatabase.PKO.DATE_DOCS, mdate);
            String whereClause= "_ID  = ?";
            String[] whereArgs={String.valueOf(mId)};

            //tmpdata.put(MetaDatabase.Docs._ID, mId);
            //rowId=db.replace(MetaDatabase.Docs.DOCS_TABLE, null, tmpdata);
            rowId=db.update(docTablename,  tmpdata,whereClause,whereArgs);
        }

        return rowId;
    }


    public boolean updDocUploadedOnServer(long[] mDocArrID){
        if(this.db==null){
            this.open();
        }

        boolean res=false;

        db.beginTransaction();
        try{

            for(long mDocId:mDocArrID){
                String query="UPDATE "
                        + MetaDatabase.Docs.DOCS_TABLE
                        + " SET "
                        + MetaDatabase.Docs.UPLOADED+" = 1 "
                        + " WHERE "+MetaDatabase.Docs._ID+" = "+ String.valueOf(mDocId)
                        ;
                this.db.execSQL(query);
            }

            // Transaction is successful and all the records have been inserted
            db.setTransactionSuccessful();
            res=true;
        }catch(Exception e){
            res=false;
            //Log.e(“Error in transaction”,e.toString());
        }finally{
            //End the transaction
            db.endTransaction();
        }

        return res;
    }

    public void updDocShapka(long mDocID, String mComment, String mKlientCode, String mDate, long skladId, String firmCode, String mOplata){
        if(this.db==null){
            this.open();
        }
        String query="UPDATE "
                + MetaDatabase.Docs.DOCS_TABLE
                + " SET "
                + MetaDatabase.Docs.COMMENT+" = '"+mComment + "', "
                + MetaDatabase.Docs.DATE_DOCS+" = '"+mDate+ "',"
                + MetaDatabase.Docs.SKLAD+" = "+skladId+ ","
                + MetaDatabase.Docs.FIRM+" = '"+firmCode + "', "
                + MetaDatabase.Docs.FOPLAT+" = '"+mOplata + "', "
                + MetaDatabase.Docs.KLIENT+" = '"+mKlientCode + "'"

                + " WHERE "+MetaDatabase.Docs._ID+" = "+mDocID
                ;
        this.db.execSQL(query);

    }
    public String saveGood(ContentValues dataStr){
        if(this.db==null){
            this.open();
        }
        String oCode= (String) dataStr.get(MetaDatabase.Goods.CODE);
        if (oCode.length()>=1){
            this.db.replace(MetaDatabase.Goods.GOODS_TABLE,null,dataStr)  ;
            return  oCode;
        }else
        {
         long LastId=getLastID(MetaDatabase.Goods.GOODS_TABLE)  ;
         String nCode="89."+ String.valueOf(LastId);
         dataStr.put(MetaDatabase.Goods.CODE,nCode);
          this.db.insert(MetaDatabase.Goods.GOODS_TABLE,null,dataStr);
            return  nCode;
        }
    }

    public long   insertGoodWithCode(ContentValues dataStr){
        if(this.db==null){
            this.open();
        }
        return this.db.insert(MetaDatabase.Goods.GOODS_TABLE,null,dataStr);       //Rowid
    }

    public String saveKlient(ContentValues dataStr){
        if(this.db==null){
            this.open();
        }
        String oCode= (String) dataStr.get(MetaDatabase.Klients.CODE);
        if (oCode.length()>=1){
            this.db.replace(MetaDatabase.Klients.Klients_TABLE,null,dataStr)  ;
            return  oCode;
        }else
        {
            long LastId=getLastID(MetaDatabase.Klients.Klients_TABLE)  ;
            String nCode="x000"+ String.valueOf(LastId+1);
            dataStr.put(MetaDatabase.Klients.CODE,nCode);
            this.db.insert(MetaDatabase.Klients.Klients_TABLE,null,dataStr);
            return  nCode;
        }
    }

    public Cursor getAgentList(){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +  MetaDatabase.Agents._ID +", "
                +  MetaDatabase.Agents.CODE+", "
                +  MetaDatabase.Agents.NAME+", "
                +  MetaDatabase.Agents.COMMENT
                + " FROM "
                + MetaDatabase.Agents.Agents_TABLE  ;
        return db.rawQuery(query,null);
    }

    public void   saveNomGroup(ContentValues dataStr){
        if(this.db==null){
            this.open();
        }
        long mId= dataStr.getAsLong (MetaDatabase.NomenGroups._ID);

        if (mId>0){
            this.db.replace(MetaDatabase.NomenGroups.NomenGroups_TABLE,null,dataStr)  ;
            // return  oCode;
        }else
        {
            dataStr.remove(MetaDatabase.NomenGroups._ID);
            this.db.insert(MetaDatabase.NomenGroups.NomenGroups_TABLE,null,dataStr);
        }
    }

    public void   NomGroup2levSetParent(long parentId,long itemId){
        if(this.db==null){
            this.open();
        }

        String query;
            query="UPDATE "
                    + MetaDatabase.NomenGroups.NomenGroups_TABLE
                    + " SET "
                    + MetaDatabase.NomenGroups.PARENT_ID+" = "+parentId
                    + " WHERE "+MetaDatabase.NomenGroups._ID+" = "+itemId
            ;
        //this.db.insert(MetaDatabase.NomenGroups.NomenGroups_TABLE,null,dataStr);
        this.db.execSQL(query);
    }


    public void addNewNomGroupIfNotExist(String mName){
        if(this.db==null){
            this.open();
        }
       long resID= findByName(MetaDatabase.NomenGroups.NomenGroups_TABLE,MetaDatabase.NomenGroups.NAME,mName);
        //нет с таким именем
        if(resID==0){
            ContentValues dataStr=new ContentValues();
            dataStr.put(MetaDatabase.NomenGroups.NAME,mName);
            this.db.insert(MetaDatabase.NomenGroups.NomenGroups_TABLE,null,dataStr);
       }
    }

    public void   saveSklad(ContentValues dataStr){
        if(this.db==null){
            this.open();
        }

        long mId= dataStr.getAsLong (MetaDatabase.Sklads._ID);

        if (mId>0){
            this.db.replace(MetaDatabase.Sklads.Sklads_TABLE,null,dataStr)  ;
           // return  oCode;
        }else
        {
            dataStr.remove(MetaDatabase.Sklads._ID);

            if(!dataStr.containsKey(MetaDatabase.Sklads.CODE))
               dataStr.put(MetaDatabase.Sklads.CODE, String.valueOf(Math.random()));


            this.db.insert(MetaDatabase.Sklads.Sklads_TABLE,null,dataStr);
        }
    }

    public String getKlientName(String oCode){
        if(this.db==null){
            this.open();
        }
        String res="";
       String where=" WHERE "+MetaDatabase.Klients.CODE+" = '"+oCode+"'";
        String query="SELECT "
                + MetaDatabase.Klients.NAME
                + " FROM "
                + MetaDatabase.Klients.Klients_TABLE
                + where
                ;
        Cursor mCursor=db.rawQuery(query,null);
        if(mCursor.moveToFirst()){
            res= mCursor.getString(0);
        }
        mCursor.close();
        return res;
    }
    public String getSkladName(long oID){
        if(this.db==null){
            this.open();
        }
        String res="";
        String where=" WHERE "+MetaDatabase.Sklads._ID+" = "+oID+"";
        String query="SELECT "
                + MetaDatabase.Sklads.NAME
                + " FROM "
                + MetaDatabase.Sklads.Sklads_TABLE
                + where
                ;
        Cursor mCursor=db.rawQuery(query,null);
        if(mCursor.moveToFirst()){
            res= mCursor.getString(0);
        }
        mCursor.close();
        return res;
    }

    public String getFirmName(String oCode){
        if(this.db==null){
            this.open();
        }
        String res="";
        String where=" WHERE "+MetaDatabase.Firms.CODE+" = '"+oCode+"'";
        String query="SELECT "
                + MetaDatabase.Firms.NAME
                + " FROM "
                + MetaDatabase.Firms.Firms_TABLE
                + where
                ;
        Cursor mCursor=db.rawQuery(query,null);
        if(mCursor.moveToFirst()){
            res= mCursor.getString(0);
        }
        mCursor.close();
        return res;
    }
    public String getFoplat(String oCode){
        if(this.db==null){
            this.open();
        }
        String res="";
        String where=" WHERE "+MetaDatabase.FormaOplat.CODE+" = '"+oCode+"'";
        String query="SELECT "
                + MetaDatabase.FormaOplat.NAME
                + " FROM "
                + MetaDatabase.FormaOplat.FormaOplat_TABLE
                + where
                ;
        Cursor mCursor=db.rawQuery(query,null);
        if(mCursor.moveToFirst()){
            res= mCursor.getString(0);
        }
        mCursor.close();
        return res;
    }
    public String getKlientMail(String oCode){
        if(this.db==null){
            this.open();
        }
        String res="";
        String where=" WHERE "+MetaDatabase.Klients.CODE+" = '"+oCode+"'";
        String query="SELECT "
                + MetaDatabase.Klients.EMAIL
                + " FROM "
                + MetaDatabase.Klients.Klients_TABLE
                + where
                ;
        Cursor mCursor=db.rawQuery(query,null);
        if(mCursor.moveToFirst()){
            res= mCursor.getString(0);
        }
        mCursor.close();
        return res;
    }
    public Cursor getDocData(long id){
        if(this.db==null){
            this.open();
        }

        String query="SELECT "
                + MetaDatabase.Docs._ID +", "
                + MetaDatabase.Docs.DATE_DOCS+", "
                + MetaDatabase.Docs.NAME+", "
                + MetaDatabase.Docs.COMMENT+", "
                + MetaDatabase.Docs.FLAGPRIHRASH+", "
                + MetaDatabase.Docs.KLIENT+", "
                + MetaDatabase.Docs.DISCPERC+", "
                + MetaDatabase.Docs.SKLAD+", "
                + MetaDatabase.Docs.FIRM+", "
                + MetaDatabase.Docs.FOPLAT+", "
                +MetaDatabase.Docs.SUM
                + " FROM "
                + MetaDatabase.Docs.DOCS_TABLE
                + " WHERE "+MetaDatabase.Docs._ID+" = '"+id+"'"
                + " ORDER BY "+MetaDatabase.Docs.SORT_DEF
                ;
        return db.rawQuery(query,null);
    }

    public void delGood(long mId,String mCode){
        ///Удалим доп реквизиты объекта
        delDopRekById(mId,MetaDatabase.Goods.GOODS_TABLE);
        ///удалим штрихкоды объекта
        delBarsFromGood(mCode);
        ///удалим объект
        String where=MetaDatabase.Goods.CODE+" = ?"  ;
        String[] arg={mCode};
        this.db.delete(MetaDatabase.Goods.GOODS_TABLE,where,arg);
    }
    public void delSklad(long mId){

        ///удалим объект
        String where=MetaDatabase.Sklads._ID+" = ?"  ;
        String[] arg={String.valueOf(mId)};
        this.db.delete(MetaDatabase.Sklads.Sklads_TABLE,where,arg);
    }
    public void delNomGroup(long mId){

        ///удалим объект
        String where=MetaDatabase.NomenGroups._ID+" = ?"  ;
        String[] arg={String.valueOf(mId)};
        this.db.delete(MetaDatabase.NomenGroups.NomenGroups_TABLE,where,arg);
    }

    public void DelDoc(long id,int fDvig,String DOC_TYPE){
        if(this.db==null){
            this.open();
        }
        if(fDvig!=0){
          float mBalance;
          float mQtyLine;
          final String fieldCode=MetaDatabase.RowDocs.GOODS_CODE;
          final String fieldQty=MetaDatabase.RowDocs.QTY;
          // изменим баланс товаров
          Cursor mCurs=getDocTable(id);
          mCurs.moveToFirst();
          while (!mCurs.isAfterLast()){
             String mCode=  mCurs.getString(mCurs.getColumnIndex(fieldCode));
             mBalance=getGoodBalanceByCode(mCode);
             mQtyLine=mCurs.getFloat(mCurs.getColumnIndex(fieldQty));
             setNewGoodBalance(mCode,mBalance-fDvig*mQtyLine)  ;
             mCurs.moveToNext();
          }
          mCurs.close();
        }

        String whereRowDoc=MetaDatabase.RowDocs.DOCS_ID +  " = ?";
        String[] whereArgsRowDoc={String.valueOf(id)};
        this.db.delete(MetaDatabase.RowDocs.ROWDOCS_TABLE,whereRowDoc,whereArgsRowDoc);

        String[] whereArgsDoc = {String.valueOf(id)};
        this.db.delete(MetaDatabase.Docs.DOCS_TABLE,"_ID=?",whereArgsDoc);
        //удаляем доп реквизиты текущего документа
        delDopRekById(id,MetaDatabase.Docs.DOCS_TABLE);

        //удаляем взаиморасчеты
        delVzaimRecordAndUpdBalnce(id,DOC_TYPE);

    }

    public Cursor getDocTable(long id){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                + "t1."+MetaDatabase.RowDocs._ID+", "
                + "t1."+MetaDatabase.RowDocs.GOODS_CODE+", "
                + "t2."+MetaDatabase.Goods.NAME+", "
                + "t1."+MetaDatabase.RowDocs.PRICE+", "
                + "t1."+MetaDatabase.RowDocs.QTY+", "
                + "t1."+MetaDatabase.RowDocs.DISCOUNT+", "
                + "t1."+MetaDatabase.RowDocs.SUM+", "
                + "t1."+MetaDatabase.RowDocs.IN_ROW_NUM
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = '"+id+"'"
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                + " ORDER BY t1."+MetaDatabase.RowDocs._ID
                ;

        return db.rawQuery(query,null);
    }
    public Cursor getDocTableForExport(long id){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                + "t1."+MetaDatabase.RowDocs._ID+", "
                + "t1."+MetaDatabase.RowDocs.GOODS_CODE+", "
                + "t2."+MetaDatabase.Goods.NAME+", "
                + "t1."+MetaDatabase.RowDocs.PRICE+", "
                + "t1."+MetaDatabase.RowDocs.QTY+", "
                + "t1."+MetaDatabase.RowDocs.DISCOUNT+", "
                + "t1."+MetaDatabase.RowDocs.SUM+", "
                + "t1."+MetaDatabase.RowDocs.IN_ROW_NUM
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = '"+id+"'"
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                + " ORDER BY t1."+MetaDatabase.RowDocs.IN_ROW_NUM
                ;

        return db.rawQuery(query,null);
    }

    public Cursor getElectronDocRow(long id, String goodsCode){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                + "t1."+MetaDatabase.RowDocs._ID+", "
                + "t1."+MetaDatabase.RowDocs.GOODS_CODE+", "
                + "t2."+MetaDatabase.Goods.NAME+" AS GOODSNAME, "
                + "t1."+MetaDatabase.RowDocs.PRICE+", "
                + "t1."+MetaDatabase.RowDocs.QTY+" AS QTY, "
                + "t1."+MetaDatabase.RowDocs.IN_QTY+" AS INQTY, "
                + "t1."+MetaDatabase.RowDocs.IN_ROW_NUM+" AS INROWNUM, "
                + "t1."+MetaDatabase.RowDocs.DISCOUNT+", "
                + "t1."+MetaDatabase.RowDocs.SUM
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = '"+id+"'"
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = '"+goodsCode+"'"
                ;

        return db.rawQuery(query,null);
    }

    public Cursor getElectronDocRowByDocNumber(long id, String docnum){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                + "t1."+MetaDatabase.RowDocs._ID+", "
                + "t1."+MetaDatabase.RowDocs.GOODS_CODE+", "
                + "t2."+MetaDatabase.Goods.NAME+ " AS GOODSNAME, "
                + "t1."+MetaDatabase.RowDocs.PRICE+", "
                + "t1."+MetaDatabase.RowDocs.QTY+" AS QTY, "
                + "t1."+MetaDatabase.RowDocs.IN_QTY+" AS INQTY, "
                + "t1."+MetaDatabase.RowDocs.IN_ROW_NUM+" AS INROWNUM, "
                + "t1."+MetaDatabase.RowDocs.DISCOUNT+", "
                + "t1."+MetaDatabase.RowDocs.SUM
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = '"+id+"'"
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                + " AND t1."+MetaDatabase.RowDocs.IN_ROW_NUM +" = '"+docnum+"'"
                ;

        return db.rawQuery(query,null);
    }

    public Cursor getElectronDocRowsForAnaliz(long id){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                + "t1."+MetaDatabase.RowDocs._ID+", "
                + "t1."+MetaDatabase.RowDocs.GOODS_CODE+", "
                + "t2."+MetaDatabase.Goods.NAME+ " AS GOODSNAME, "
                + "t1."+MetaDatabase.RowDocs.PRICE+", "
                + "t1."+MetaDatabase.RowDocs.QTY+" AS QTY, "
                + "t1."+MetaDatabase.RowDocs.IN_QTY+" AS INQTY, "
                + "t1."+MetaDatabase.RowDocs.IN_ROW_NUM+" AS INROWNUM, "
                + "t1."+MetaDatabase.RowDocs.DISCOUNT+", "
                + "t1."+MetaDatabase.RowDocs.SUM
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = '"+id+"'"
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                + " AND t1."+MetaDatabase.RowDocs.QTY +" <> t1."+MetaDatabase.RowDocs.IN_QTY+""
                + " ORDER BY t1."+MetaDatabase.RowDocs.IN_ROW_NUM
                ;

        return db.rawQuery(query,null);
    }



    public void updateElectronRowQty(long id,String newQty){
        if(this.db==null){
            this.open();
        }
        String query;

            query="UPDATE "
                    + MetaDatabase.RowDocs.ROWDOCS_TABLE
                    + " SET "
                    + MetaDatabase.RowDocs.QTY+" = "+newQty
                    + " WHERE "+MetaDatabase.RowDocs._ID+" = '"+id+"'"
            ;

        this.db.execSQL(query);
    }



    public void updPriceFromDoc(long id,int mFlagPrixRasx){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                + "t1."+MetaDatabase.RowDocs._ID+", "
                + "t1."+MetaDatabase.RowDocs.GOODS_CODE+", "
                + "t1."+MetaDatabase.RowDocs.PRICE
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = '"+id+"'"
                + " ORDER BY t1."+MetaDatabase.RowDocs._ID
                ;
        Cursor mCursor=db.rawQuery(query,null);

        if(mFlagPrixRasx==-1){ //расходная
            while (mCursor.moveToNext()){
                setNewGoodPrices(mCursor.getString(1),false,true,0, mCursor.getFloat(2));
            }
        }   else if(mFlagPrixRasx==1){ //приходная
            while (mCursor.moveToNext()){
                setNewGoodPrices(mCursor.getString(1),true,false, mCursor.getFloat(2),0);
            }
        }

        mCursor.close();

    }

    public boolean getSvertkaDocTable(long id){
        if(this.db==null){
            this.open();
        }
        boolean resTr =false;

        String query="SELECT "
                + "t1."+MetaDatabase.RowDocs.GOODS_CODE+", "
                + "t1."+MetaDatabase.RowDocs.PRICE+", "
                + "total(t1."+MetaDatabase.RowDocs.QTY+"), "
                + "total(t1."+MetaDatabase.RowDocs.DISCOUNT+"), "
                + "total(t1."+MetaDatabase.RowDocs.SUM+")"
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = "+id+""
               + " GROUP BY "+"t1."+MetaDatabase.RowDocs.GOODS_CODE+","+"t1."+MetaDatabase.RowDocs.PRICE
                ;
        Cursor mCursor=db.rawQuery(query,null);

         if(mCursor.getCount()==0){
             //Log.d("NO NO CURSOR","NO NO CURSOR");
             return false;
         }
        // Begin the transaction
        db.beginTransaction();
        try{
            clearDocTable(String.valueOf(id));//чистим документ в базе после запроса выше
            while (mCursor.moveToNext()) {
               // double sum=mCursor.getDouble(4)*mCursor.getDouble(5);
                //sum *= 100;
                //sum = (double)Math.round(sum)/100;

                ContentValues contentValues=new ContentValues();
                contentValues.put(MetaDatabase.RowDocs.DOCS_ID ,id);//docId
                contentValues.put(MetaDatabase.RowDocs.GOODS_CODE ,mCursor.getString(0));//code
                contentValues.put(MetaDatabase.RowDocs.QTY,mCursor.getString(2));//qty
                contentValues.put(MetaDatabase.RowDocs.PRICE, mCursor.getString(1));//price
                contentValues.put(MetaDatabase.RowDocs.SUM, mCursor.getString(4));//sum
                contentValues.put(MetaDatabase.RowDocs.DISCOUNT, mCursor.getString(3));//disk
                //contentValues.put(COLUMN_NAME,record);
                db.insert(MetaDatabase.RowDocs.ROWDOCS_TABLE,null,contentValues);
               // Log.d("INSERTED",mCursor.getString(3));
            }
            // Transaction is successful and all the records have been inserted
            db.setTransactionSuccessful();
           // Log.d("SUCSES","TRANS");
            resTr=true;
        }catch(Exception e){
            resTr=false;
           // Log.e("Error in transaction",e.toString());
        }finally{
            //End the transaction
            db.endTransaction();
        }

        mCursor.close();


        return resTr;
    }

    /**
     * Заполнение инвентаризации остатками
     * @param id -docId
     * @param mVariant if 1-ostaki>0 -1<0
     * @return  true-ok
     */
    public boolean fillDocByOstatok(long id,int mVariant){
        if(this.db==null){
            this.open();
        }
        boolean resTr =false;
        String where="";
        ///получим остатки
        if (mVariant==1){
           where=" WHERE "+MetaDatabase.Goods.BALANCE+" > 0";
        }else if(mVariant==-1){
            where=" WHERE "+MetaDatabase.Goods.BALANCE+" < 0";
        }
        String query="SELECT "
                + MetaDatabase.Goods._ID +", "
                + MetaDatabase.Goods.NAME+", "
                + MetaDatabase.Goods.CODE+", "
                + MetaDatabase.Goods.PRICE+", "
                +  MetaDatabase.Goods.BALANCE+", "
                + MetaDatabase.Goods.INPUTPRICE
                + " FROM "
                + MetaDatabase.Goods.GOODS_TABLE
                + where
                +  " ORDER BY "+MetaDatabase.Goods.NAME
                ;
        Cursor mCursor=db.rawQuery(query,null);

        // Begin the transaction
        db.beginTransaction();
        try{
            while (mCursor.moveToNext()) {
                double sum=mCursor.getDouble(4)*mCursor.getDouble(5);
                sum *= 100;
                sum = (double) Math.round(sum)/100;

                ContentValues contentValues=new ContentValues();
                contentValues.put(MetaDatabase.RowDocs.DOCS_ID ,id);//docId
                contentValues.put(MetaDatabase.RowDocs.GOODS_CODE ,mCursor.getString(2));//code
                contentValues.put(MetaDatabase.RowDocs.QTY,mCursor.getString(4));//qty
                contentValues.put(MetaDatabase.RowDocs.PRICE, mCursor.getString(5));//price
                contentValues.put(MetaDatabase.RowDocs.SUM, sum);//sum
                contentValues.put(MetaDatabase.RowDocs.DISCOUNT, 0);//disk
                //contentValues.put(COLUMN_NAME,record);
                db.insert(MetaDatabase.RowDocs.ROWDOCS_TABLE,null,contentValues);

            }
             // Transaction is successful and all the records have been inserted
            db.setTransactionSuccessful();
            resTr=true;
        }catch(Exception e){
            resTr=false;
            //Log.e(“Error in transaction”,e.toString());
        }finally{
       //End the transaction
            db.endTransaction();
        }

        mCursor.close();
        return resTr;
    }

    public long newBarcode(String oCode, String oBar){
        if(this.db==null){
            this.open();
        }
        Cursor tmpCurs=getGoodByBarcode(oBar);
        if(tmpCurs.moveToFirst()){
            tmpCurs.close();
            return -1;
        }
        tmpCurs.close();
        String last4bar;
        int barstrlen=oBar.length();
        int firstDigit=barstrlen-4;
        if(firstDigit>=0){
            last4bar=oBar.substring(firstDigit);
        }else{
            last4bar=oBar;
        }


        ContentValues dataStr=new ContentValues();
        dataStr.put(MetaDatabase.Barcodes.BARCODE,oBar);
        dataStr.put(MetaDatabase.Barcodes.GOODS_CODE,oCode);
        dataStr.put(MetaDatabase.Barcodes.QINDEX,last4bar);
        return this.db.insert(MetaDatabase.Barcodes.BARCODES_TABLE,null,dataStr);
    }

    public Cursor getBarcodesByGood(String oCode) {
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                + MetaDatabase.Barcodes._ID+", "
                + MetaDatabase.Barcodes.BARCODE+", "
                + MetaDatabase.Barcodes.QINDEX
                + " FROM "
                + MetaDatabase.Barcodes.BARCODES_TABLE
                + " WHERE "+MetaDatabase.Barcodes.GOODS_CODE+" = '"+oCode+"'"
          //      + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
          //      + " ORDER BY t1."+MetaDatabase.RowDocs._ID
                ;
        return db.rawQuery(query,null);
    }

    public String getBarFirstByGoodsCode(String oCode){
        if(oCode==null||oCode.length()<1)
            return "";

        String rez="";
        Cursor mCur=getBarcodesByGood(oCode);
        if(mCur.moveToFirst()){
            rez=mCur.getString(mCur.getColumnIndex(MetaDatabase.Barcodes.BARCODE));
            mCur.close();
        }   else{
            mCur.close();
        }
        return rez;
    }

    public Cursor getGoodByBarcode(String bar){
        if(this.db==null){
            this.open();
        }
            String query="SELECT "
                    + "t1."+MetaDatabase.Barcodes.GOODS_CODE+", "
                    + "t2."+MetaDatabase.Goods.NAME+", "
                    + "t2."+MetaDatabase.Goods.PRICE+", "
                    + "t2."+MetaDatabase.Goods.INPUTPRICE
                    + " FROM "
                    + MetaDatabase.Barcodes.BARCODES_TABLE +" AS t1 "+", "
                    + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                    + " WHERE t1."+MetaDatabase.Barcodes.BARCODE+" = '"+bar+"'"
                    + " AND t1."+MetaDatabase.Barcodes.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                    //+ " ORDER BY t1."+MetaDatabase.RowDocs.DOCS_ID
                    ;

        return db.rawQuery(query,null);

    }

    public Cursor getGoodsBy4Dig(String bar){
        if(this.db==null){
            this.open();
        }
        //case when fieldname is null then 0 else fieldname end
        String query="SELECT "
                + "t1."+MetaDatabase.Barcodes.GOODS_CODE+", "
                + "t2."+MetaDatabase.Goods.NAME+", "
                + "t2."+MetaDatabase.Goods.PRICE+", "
                + "t2."+MetaDatabase.Goods.INPUTPRICE
                + " FROM "
                + MetaDatabase.Barcodes.BARCODES_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t1."+MetaDatabase.Barcodes.QINDEX+" = '"+bar+"'"
                + " AND t1."+MetaDatabase.Barcodes.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                //+ " ORDER BY t1."+MetaDatabase.RowDocs.DOCS_ID
                ;

        return db.rawQuery(query,null);
    }

    public Cursor getGoodByCode(String codeG){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                + "t2."+MetaDatabase.Goods.CODE+", "
                + "t2."+MetaDatabase.Goods.NAME+", "
                + "t2."+MetaDatabase.Goods.PRICE+", "
                + "t2."+MetaDatabase.Goods.INPUTPRICE
                + " FROM "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t2."+MetaDatabase.Goods.CODE+" = '"+codeG+"'"
                ;

        return db.rawQuery(query,null);

    }
/*    public Cursor getGoodByName(String nameG){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                + "t2."+MetaDatabase.Goods.CODE+", "
                + "t2."+MetaDatabase.Goods.NAME+", "
                + "t2."+MetaDatabase.Goods.PRICE+", "
                + "t2."+MetaDatabase.Goods.INPUTPRICE
                + " FROM "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t2."+MetaDatabase.Goods.NAME+" = '"+nameG+"'"
                ;

        return db.rawQuery(query,null);

    }*/

    public Cursor getAllKlients(String partialName) {
        String joinAgent="t1."+MetaDatabase.Klients.TRADE_AGENT+" = "+"t2."+ MetaDatabase.Agents.CODE;
        String joinMarsh="t1."+MetaDatabase.Klients.MARSHRUT+" = "+"t3."+ MetaDatabase.Marshruts.CODE;

        String where;
        if(this.db==null){
            this.open();
        }
        if (partialName == null  ||  partialName.length () == 0)  {
            where="";
        }  else{
            String lowPartialName =partialName.toLowerCase();
            where=" WHERE t1.LNAME "+ " like '%"+lowPartialName+"%' COLLATE NOCASE" ; //COLLATE NOCASE
        }




        String query="SELECT "
                + "t1."+ MetaDatabase.Klients._ID +", "
                +"t1."+ MetaDatabase.Klients.NAME+", "
                + "t1."+MetaDatabase.Klients.CODE+", "
                + "t1."+MetaDatabase.Klients.EMAIL+", "
                + "t1."+MetaDatabase.Klients.PHONE+", "
                +  "t1."+MetaDatabase.Klients.BALANCE +", "
                +  "t1."+MetaDatabase.Klients.TRADE_AGENT +", "
                +  "t1."+MetaDatabase.Klients.MARSHRUT +", "
                + "t2."+ MetaDatabase.Agents.NAME +" AS Agent, "
                +  "t3."+MetaDatabase.Marshruts.NAME +" AS Marshrut "
                + " FROM "
                + MetaDatabase.Klients.Klients_TABLE +" AS t1" +" LEFT JOIN "
                + MetaDatabase.Agents.Agents_TABLE+" AS t2 ON " +joinAgent
                +" LEFT JOIN "+ MetaDatabase.Marshruts.Marshruts_TABLE+" AS t3 ON " +joinMarsh
                + where
                +  " ORDER BY "+MetaDatabase.Klients.NAME
                ;

        return db.rawQuery(query,null);
    }


    public Cursor getAllKlientsByAgent(String partialName, String agentCode) {
        String joinAgent="t1."+MetaDatabase.Klients.TRADE_AGENT+" = "+"t2."+ MetaDatabase.Agents.CODE;
        String joinMarsh="t1."+MetaDatabase.Klients.MARSHRUT+" = "+"t3."+ MetaDatabase.Marshruts.CODE;

        String whereAgent;

        String where;
        if(this.db==null){
            this.open();
        }

        whereAgent=" WHERE "+MetaDatabase.Klients.TRADE_AGENT+" = '"+agentCode+"'";

        if (partialName == null  ||  partialName.length () == 0)  {
            where="";
        }  else{
            String lowPartialName =partialName.toLowerCase();
            where=" AND LNAME "+ " like '%"+lowPartialName+"%' COLLATE NOCASE" ; //COLLATE NOCASE
        }




        String query="SELECT "
                +"t1."+ MetaDatabase.Klients._ID +", "
                + "t1."+MetaDatabase.Klients.NAME+", "
                + "t1."+MetaDatabase.Klients.CODE+", "
                +"t1."+ MetaDatabase.Klients.EMAIL+", "
                + "t1."+MetaDatabase.Klients.PHONE+", "
                + "t1."+ MetaDatabase.Klients.BALANCE +", "
                +  "t1."+MetaDatabase.Klients.TRADE_AGENT +", "
                +  "t1."+MetaDatabase.Klients.MARSHRUT +", "
                + "t2."+ MetaDatabase.Agents.NAME +" AS Agent, "
                + "t3."+MetaDatabase.Marshruts.NAME +" AS Marshrut "
                + " FROM "
                + MetaDatabase.Klients.Klients_TABLE +" AS t1" +" LEFT JOIN "
                + MetaDatabase.Agents.Agents_TABLE+" AS t2 ON " +joinAgent
                +" LEFT JOIN "+ MetaDatabase.Marshruts.Marshruts_TABLE+" AS t3 ON " +joinMarsh
                + whereAgent
                + where
                +  " ORDER BY "+MetaDatabase.Klients.NAME
                ;

        return db.rawQuery(query,null);
    }

    public Cursor getAllGoods(String partialName, boolean onlyOstatok, long mNonGroupId) {
        String whereOstatok;
        String where;
        String whereNomGrp;
        if(this.db==null){
            this.open();
        }
        if(onlyOstatok){
            whereOstatok= " WHERE "+MetaDatabase.Goods.BALANCE+" > 0 ";
        }   else {
            whereOstatok="";
        }
        if (partialName == null  ||  partialName.length () == 0)  {
            where="";
        }  else{
            String lowPartialName =partialName.toLowerCase();
            if(onlyOstatok){
              where=" AND LNAME "+ " like '%"+lowPartialName+"%' COLLATE NOCASE " ; //COLLATE NOCASE
            } else {
                where=" WHERE LNAME "+ " like '%"+lowPartialName+"%' COLLATE NOCASE " ;
            }
        }
        if(mNonGroupId==0){
            whereNomGrp="";
        } else{
            if(onlyOstatok || where.length()>0){
                whereNomGrp=  " AND "+MetaDatabase.Goods.NOMGRP+" =  "+mNonGroupId+" ";
            }else{
                whereNomGrp=  " WHERE "+MetaDatabase.Goods.NOMGRP+" =  "+mNonGroupId+" ";
            }
        }

        String query="SELECT "
                + MetaDatabase.Goods._ID +", "
                + MetaDatabase.Goods.NAME+", "
                + MetaDatabase.Goods.CODE+", "
                + MetaDatabase.Goods.PRICE+", "
                +  MetaDatabase.Goods.BALANCE+", "
                + MetaDatabase.Goods.INPUTPRICE +", "
                + MetaDatabase.Goods.NOMGRP
                + " FROM "
                + MetaDatabase.Goods.GOODS_TABLE
                + whereOstatok
                + where
                + whereNomGrp
                +  " ORDER BY "+MetaDatabase.Goods.NAME
                ;

        return db.rawQuery(query,null);
    }

    public Cursor getAllGoodsForPodbor(String partialName, boolean onlyOstatok, long mNonGroupId, long mDocID) {
        String whereOstatok;
        String where;
        String whereNomGrp;
        String whereDoc;

        String joinExp="(t1."+MetaDatabase.Goods.CODE+" = "+"t2."+ MetaDatabase.RowDocs.GOODS_CODE+")";

        if(this.db==null){
            this.open();
        }
        if(onlyOstatok){
            whereOstatok= " WHERE t1."+MetaDatabase.Goods.BALANCE+" > 0 ";
        }   else {
            whereOstatok="";
        }
        if (partialName == null  ||  partialName.length () == 0)  {
            where="";
        }  else{
            String lowPartialName =partialName.toLowerCase();
            if(onlyOstatok){
                where=" AND LNAME "+ " like '%"+lowPartialName+"%' COLLATE NOCASE " ; //COLLATE NOCASE
            } else {
                where=" WHERE LNAME "+ " like '%"+lowPartialName+"%' COLLATE NOCASE " ;
            }
        }
        if(mNonGroupId==0){
            whereNomGrp="";
        } else{
            if(onlyOstatok || where.length()>0){
                whereNomGrp=  " AND t1."+MetaDatabase.Goods.NOMGRP+" =  "+mNonGroupId+" ";
            }else{
                whereNomGrp=  " WHERE t1."+MetaDatabase.Goods.NOMGRP+" =  "+mNonGroupId+" ";
            }
        }
//
//        if(whereNomGrp.length()>0 || whereOstatok.length()>0 || where.length()>0)  {
//            whereDoc=  " AND "+ "t2."+MetaDatabase.RowDocs.DOCS_ID+" =  "+mDocID+" ";
//
//        }   else{
//            whereDoc=  " WHERE "+ "t2."+MetaDatabase.RowDocs.DOCS_ID+" =  "+mDocID+" ";
//        }

        String queryDoc="SELECT "
                +"t2."+ MetaDatabase.RowDocs.GOODS_CODE  +", "
                + "total(t2."+MetaDatabase.RowDocs.QTY+") AS rowdocs_qty "
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE+" AS t2 "  +
                " WHERE "+ "t2."+MetaDatabase.RowDocs.DOCS_ID+" =  "+mDocID+" "
                +  " GROUP BY "+"t2."+MetaDatabase.RowDocs.GOODS_CODE;
                //   + whereDoc

                ;




        String query="SELECT "
                + "t1."+MetaDatabase.Goods._ID +", "
                + "t1."+MetaDatabase.Goods.NAME+", "
                + "t1."+MetaDatabase.Goods.CODE+", "
                + "t1."+MetaDatabase.Goods.PRICE+", "
                + "t1."+MetaDatabase.Goods.BALANCE+", "
                + "t1."+MetaDatabase.Goods.INPUTPRICE +", "
                + "t1."+MetaDatabase.Goods.NOMGRP +", "
                + "t2.rowdocs_qty"
                + " FROM "
                + MetaDatabase.Goods.GOODS_TABLE +" AS t1" +" LEFT JOIN ( "+queryDoc+ " ) AS t2 "
                +" ON "+joinExp
                + whereOstatok
                + where
                + whereNomGrp
             //   + whereDoc
                +  " ORDER BY "+MetaDatabase.Goods.NAME
                ;









        return db.rawQuery(query,null);
    }

    public void clearDocTable(String docid){
        if(this.db==null){
            this.open();
        }
        String sqlDel  = "DELETE FROM "+MetaDatabase.RowDocs.ROWDOCS_TABLE
                +" WHERE "+MetaDatabase.RowDocs.DOCS_ID+" = "+docid;
        this.db.execSQL(sqlDel);
    }

    public float getKlientBalanceByCode(String codeG){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                + "t2."+MetaDatabase.Klients.BALANCE
                + " FROM "
                + MetaDatabase.Klients.Klients_TABLE+" AS t2"
                + " WHERE t2."+MetaDatabase.Klients.CODE+" = '"+codeG+"'"
                ;
        Cursor mCursor=db.rawQuery(query,null);
        if(mCursor.moveToFirst()){
            float rez=mCursor.getFloat(mCursor.getColumnIndex(MetaDatabase.Klients.BALANCE));
            mCursor.close();
            return rez;
        }  else{
            return 0;
        }
    }

    public float getGoodBalanceByCode(String codeG){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                + "t2."+MetaDatabase.Goods.BALANCE
                + " FROM "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t2."+MetaDatabase.Goods.CODE+" = '"+codeG+"'"
                ;
        Cursor mCursor=db.rawQuery(query,null);
        if(mCursor.moveToFirst()){
            float rez=mCursor.getFloat(mCursor.getColumnIndex(MetaDatabase.Goods.BALANCE));
            mCursor.close();
            return rez;
        }  else{
            return 0;
        }
    }
    public String[] getBalanceAndPricesByCode(String codeG){
        if(this.db==null){
            this.open();
        }
        String[] res=new String[3];
        String query="SELECT "
                + "t2."+MetaDatabase.Goods.BALANCE +", "
                + "t2."+MetaDatabase.Goods.PRICE +", "
                + "t2."+MetaDatabase.Goods.INPUTPRICE
                + " FROM "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t2."+MetaDatabase.Goods.CODE+" = '"+codeG+"'"
                ;
        Cursor mCursor=db.rawQuery(query,null);
        if(mCursor.moveToFirst()){
            res[0]=mCursor.getString(mCursor.getColumnIndex(MetaDatabase.Goods.BALANCE));
            res[1]=mCursor.getString(mCursor.getColumnIndex(MetaDatabase.Goods.PRICE));
            res[2]=mCursor.getString(mCursor.getColumnIndex(MetaDatabase.Goods.INPUTPRICE));
            //float rez=mCursor.getFloat(mCursor.getColumnIndex(MetaDatabase.Goods.BALANCE));
            mCursor.close();
            return res;
        }  else{
            return res;
        }
    }

    public void setNewGoodPrices(String codeG, boolean zak, boolean prod, float newZak, float newProd){
        if(this.db==null){
            this.open();
        }
        String query;
        if(zak && !prod){
            query="UPDATE "
                + MetaDatabase.Goods.GOODS_TABLE
                + " SET "
                + MetaDatabase.Goods.INPUTPRICE+" = "+newZak
                + " WHERE "+MetaDatabase.Goods.CODE+" = '"+codeG+"'"
                ;
        } else if(!zak && prod) {
            query="UPDATE "
                    + MetaDatabase.Goods.GOODS_TABLE
                    + " SET "
                    + MetaDatabase.Goods.PRICE+" = "+newProd
                    + " WHERE "+MetaDatabase.Goods.CODE+" = '"+codeG+"'"
            ;
        }  else if(zak && prod){
            query="UPDATE "
                    + MetaDatabase.Goods.GOODS_TABLE
                    + " SET "
                    + MetaDatabase.Goods.PRICE+" = "+newProd +","
                    + MetaDatabase.Goods.INPUTPRICE+" = "+newZak
                    + " WHERE "+MetaDatabase.Goods.CODE+" = '"+codeG+"'"
            ;
        } else {
            return;
        }
        this.db.execSQL(query);

    }

    public void updTaskParams(long taskId, String active, String dropbox, String start){
        if(this.db==null){
            this.open();
        }
        String query;

        query="UPDATE "
                + MetaDatabase.Tasks.TABLE_NAME
                + " SET "
                + MetaDatabase.Tasks.ISACTIVE+" = '"+active +"',"
                + MetaDatabase.Tasks.STARTON+" = '"+start +"',"
                + MetaDatabase.Tasks.DROPBOX_SINH+" = '"+dropbox+"'"
                + " WHERE "+MetaDatabase.Tasks._ID+" = "+taskId+""
        ;
        this.db.execSQL(query);
    }

    public void setNewKlientBalance(String codeG, float newVal){
        if(this.db==null){
            this.open();
        }
        String query="UPDATE "
                + MetaDatabase.Klients.Klients_TABLE
                + " SET "
                + MetaDatabase.Klients.BALANCE+" = "+newVal
                + " WHERE "+MetaDatabase.Klients.CODE+" = '"+codeG+"'"
                ;
        this.db.execSQL(query);

    }
    public void setNewGoodBalance(String codeG, float newVal){
        if(this.db==null){
            this.open();
        }
        String query="UPDATE "
                + MetaDatabase.Goods.GOODS_TABLE
                + " SET "
                + MetaDatabase.Goods.BALANCE+" = "+newVal
                + " WHERE "+MetaDatabase.Goods.CODE+" = '"+codeG+"'"
                ;
        this.db.execSQL(query);

    }
    public void clearGoodBalance(){
        if(this.db==null){
            this.open();
        }
        String query="UPDATE "
                + MetaDatabase.Goods.GOODS_TABLE
                + " SET "
                + MetaDatabase.Goods.BALANCE+" = 0 "
                ;
        this.db.execSQL(query);

    }
    public long saveDopRek(ContentValues dataStr) {
        if(this.db==null){
            this.open();
        }

        return this.db.insert(MetaDatabase.DopRekvizitsRef.DopRekvizitsRef_TABLE,null,dataStr);
    }
    //fDvig флаг движений +1 приход 0-нет -1 расход
    public Long saveDocLine(ContentValues dataStr, int fDvig){
        if(this.db==null){
            this.open();
        }
        if(fDvig!=0){
          String goodsCode= dataStr.getAsString(MetaDatabase.RowDocs.GOODS_CODE);
          float qtyVal=dataStr.getAsFloat(MetaDatabase.RowDocs.QTY);
          float curBalance=getGoodBalanceByCode(goodsCode);
          setNewGoodBalance(goodsCode,curBalance+fDvig*qtyVal) ;
        }
               //Row id returned or -1
       // Log.d("INSERTED","ff");
        return this.db.insert(MetaDatabase.RowDocs.ROWDOCS_TABLE,null,dataStr);

    }

    /// ВЗАИМОРАСЧЕТЫ
    public void insertRecordVzaimo(ContentValues dataStr, int flag){
        dataStr.put(MetaDatabase.Vzaimoras.FLAGDVIG,flag);
        this.db.insert(MetaDatabase.Vzaimoras.TABLE_NAME,null,dataStr);
    }

    public void updRecordVzaimo(long docId, String docdate, String klCode, float sum, int fDvig, String DOC_TYPE){
        if(this.db==null){
            this.open();
        }
        String query;

        query="UPDATE "
                + MetaDatabase.Vzaimoras.TABLE_NAME
                + " SET "
                + MetaDatabase.Vzaimoras.DOCDATE+" = '"+docdate +"',"
                + MetaDatabase.Vzaimoras.KLIENT_CODE+" = '"+klCode +"',"
                + MetaDatabase.Vzaimoras.FLAGDVIG+" = "+fDvig +","
                + MetaDatabase.Vzaimoras.SUMM+" = '"+sum+"'"
                + " WHERE "+MetaDatabase.Vzaimoras.DOCID+" = "+docId+" AND " +MetaDatabase.Vzaimoras.DOCTYPE+" = '"+DOC_TYPE+"'"
        ;
        this.db.execSQL(query);
    }

    private void delVzaimRecord(long mDocId,String DOC_TYPE){
        if(this.db==null){
            this.open();
        }
        String where=MetaDatabase.Vzaimoras.DOCID+" = ? AND "+MetaDatabase.Vzaimoras.DOCTYPE+" = ? " ;
        String[] arg={String.valueOf(mDocId),DOC_TYPE};
        this.db.delete(MetaDatabase.Vzaimoras.TABLE_NAME,where,arg);
    }

    public void delVzaimRecordAndUpdBalnce(long mDocId,String DOC_TYPE){
        if(this.db==null){
            this.open();
        }

        Cursor mCur= getVzaimByDocID(mDocId,DOC_TYPE);
        if(mCur.moveToFirst()){
            String oldKlientsCode=mCur.getString(mCur.getColumnIndex(MetaDatabase.Vzaimoras.KLIENT_CODE));
            float oldSumOnRegistr=  mCur.getFloat(mCur.getColumnIndex(MetaDatabase.Vzaimoras.SUMM)) ;
            int fDvig=mCur.getInt(mCur.getColumnIndex(MetaDatabase.Vzaimoras.FLAGDVIG)) ;
            mCur.close();
            float oldBalance=getKlientBalanceByCode(oldKlientsCode);

            //важно!!! меняем знак сторно на противоположный - запись будет удалятся
            setNewKlientBalance(oldKlientsCode,oldBalance-fDvig*oldSumOnRegistr);
            delVzaimRecord(mDocId,DOC_TYPE);
        } else mCur.close();
    }

    public Cursor getVzaimByDocID(long docId, String DOC_TYPE){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                + "t2."+MetaDatabase.Vzaimoras.KLIENT_CODE +", "
                + "t2."+MetaDatabase.Vzaimoras.DOCDATE +", "
                + "t2."+MetaDatabase.Vzaimoras.FLAGDVIG +", "
                + "t2."+MetaDatabase.Vzaimoras.SUMM +", "
                + "t2."+MetaDatabase.Vzaimoras.DOCTYPE
                + " FROM "
                + MetaDatabase.Vzaimoras.TABLE_NAME+" AS t2"
                + " WHERE t2."+MetaDatabase.Vzaimoras.DOCID+" = "+docId+" AND " +MetaDatabase.Vzaimoras.DOCTYPE+" = '"+DOC_TYPE+"'"
                ;
        return db.rawQuery(query,null);
    }


    public void rewriteDocVzaim(ContentValues dataStr, int fDvig){
        //Взаиморасчеты
        if(fDvig!=0){
            boolean hasOldRecord=false;
            //обновим старую запись - получаем ее
            long docId=dataStr.getAsLong(MetaDatabase.Vzaimoras.DOCID);
            String DOC_TYPE=dataStr.getAsString(MetaDatabase.Vzaimoras.DOCTYPE);

            Cursor mCur=getVzaimByDocID(docId,DOC_TYPE);

            if(mCur.moveToFirst()){
                hasOldRecord=true;
                String oldKlientsCode=mCur.getString(mCur.getColumnIndex(MetaDatabase.Vzaimoras.KLIENT_CODE));
                float oldSumOnRegistr=  mCur.getFloat(mCur.getColumnIndex(MetaDatabase.Vzaimoras.SUMM)) ;

                mCur.close();
                float oldBalance=getKlientBalanceByCode(oldKlientsCode);

                //важно!!! меняем знак сторно на противоположный - запись будет удалятся
                setNewKlientBalance(oldKlientsCode,oldBalance-fDvig*oldSumOnRegistr);
            } else mCur.close();

            //Теперь новая запись
            String klientsCode= dataStr.getAsString(MetaDatabase.Vzaimoras.KLIENT_CODE);
            String dateDoc= dataStr.getAsString(MetaDatabase.Vzaimoras.DOCDATE);

            if (klientsCode==null||klientsCode.length()<1){  //клиент не указан - проверим была ли запись
                   //если была удалим старую - новую писать нет смысла бз клиента
                if(hasOldRecord)
                   delVzaimRecord(docId,DOC_TYPE);

            }else{
                float sumInDoc=dataStr.getAsFloat(MetaDatabase.Vzaimoras.SUMM );
                float curBalance=getKlientBalanceByCode(klientsCode);
                setNewKlientBalance(klientsCode,curBalance+fDvig*sumInDoc);
                if(hasOldRecord){
                  updRecordVzaimo(docId,dateDoc,klientsCode,sumInDoc,fDvig,DOC_TYPE);
                }else{
                   insertRecordVzaimo(dataStr,fDvig);
                }
            }
        }
    }

    public void saveDocRegisters(ContentValues dataStr, int fDvig) {
        if(this.db==null){
            this.open();
        }

        rewriteDocVzaim(dataStr,fDvig);

    }

   /* public boolean saveDocLinesTrans(List<String> dataList,int fDvig){
        if(this.db==null){
            this.open();
        }
        boolean res=true;
        this.db.beginTransaction();
        for (String curstr : dataList) {
            String sql  = "";

            try {
                db.execSQL(sql);
            }
            catch (SQLException e) {
                res = false;
                db.endTransaction();	//  завершим транзакцию
                break;
            }
        }
        if(res){
            this.db.setTransactionSuccessful(); // всё прошло успешно
            this.db.endTransaction(); // и завершаем транзакцию
        }
        return res;
    }*/
    //fDvig флаг движений +1 приход 0-нет -1 расход
    public Long replaceDocLine(ContentValues dataStr, int fDvig){
        if(this.db==null){
            this.open();
        }
        boolean res=true;
        long rowId=-1;
        db.beginTransaction();
        try {
          if(fDvig!=0){
            float oldKvoinLine=dataStr.getAsFloat("OLDKVO");
            String goodsCode= dataStr.getAsString(MetaDatabase.RowDocs.GOODS_CODE);
            float qtyVal=dataStr.getAsFloat(MetaDatabase.RowDocs.QTY);
            float curBalance=getGoodBalanceByCode(goodsCode);
            setNewGoodBalance(goodsCode,curBalance+fDvig*(qtyVal-oldKvoinLine)) ;
          }
        dataStr.remove("OLDKVO");
        rowId=   this.db.replace(MetaDatabase.RowDocs.ROWDOCS_TABLE,null,dataStr);
        }catch (SQLException e){
            res = false;
            db.endTransaction();
        }
        if(res){
            db.setTransactionSuccessful(); // всё прошло успешно
            db.endTransaction(); // и завершаем транзакцию
        }

        //Row id returned or -1
        return rowId;

    }

    public void delDocLine(long rowid, float kvoInLine, String gCode, int fDvig){
        if(this.db==null){
            this.open();
        }
        if(fDvig!=0){
          float curBalance=getGoodBalanceByCode(gCode);
          setNewGoodBalance(gCode,curBalance-fDvig*kvoInLine) ;
        }
        String where="_ID = ?"  ;
        String[] arg={String.valueOf(rowid)};
         this.db.delete(MetaDatabase.RowDocs.ROWDOCS_TABLE,where,arg);
    }

    public void delBarsFromGood(String mGoodCode){
        if(this.db==null){
            this.open();
        }
        String where=MetaDatabase.Barcodes.GOODS_CODE+" = ?"  ;
        String[] arg={mGoodCode};
        this.db.delete(MetaDatabase.Barcodes.BARCODES_TABLE,where,arg);
    }
    public void updateBarCode(long mBarId,String mNewBar){
        if(this.db==null){
            this.open();
        }

        String last4bar;
        int barstrlen=mNewBar.length();
        int firstDigit=barstrlen-4;
        if(firstDigit>=0){
            last4bar=mNewBar.substring(firstDigit);
        }else{
            last4bar=mNewBar;
        }

        String query="UPDATE "
                + MetaDatabase.Barcodes.BARCODES_TABLE
                + " SET "
                + MetaDatabase.Barcodes.BARCODE+" = '"+mNewBar+"'" +","
                + MetaDatabase.Barcodes.QINDEX +" = '"+last4bar+"'"
                + " WHERE "+MetaDatabase.Barcodes._ID+" = "+mBarId+""
                ;
        this.db.execSQL(query);

    }
    /*public void delBarCode(String mBarCode){
        if(this.db==null){
            this.open();
        }
        String where=MetaDatabase.Barcodes.BARCODE+" = ?"  ;
        String[] arg={mBarCode};
        this.db.delete(MetaDatabase.Barcodes.BARCODES_TABLE,where,arg);
    }*/
    public void delBarCodeById(long mBarCodeID){
        if(this.db==null){
            this.open();
        }
        String where=MetaDatabase.Barcodes._ID+" = ?"  ;
        String[] arg={String.valueOf(mBarCodeID)};
        this.db.delete(MetaDatabase.Barcodes.BARCODES_TABLE,where,arg);
    }


    public long getLastID(String tableName){
        if(this.db==null){
            this.open();
        }
        long  lastId=-1;
        String query = "SELECT ROWID from "+tableName+" order by ROWID DESC limit 1";
        Cursor c = this.db.rawQuery(query,null);
        if (c != null && c.moveToFirst()) {
            lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
            c.close();
        }

        return lastId;
    }

    public String getNameById(String tableName, String fieldName, long mId){
        if(this.db==null){
            this.open();
        }
        String res="";
        String query = "SELECT  "+fieldName+" FROM "+tableName+" WHERE _ID="+mId;
        Cursor c = this.db.rawQuery(query,null);
        if (c != null && c.moveToFirst()) {
            res = c.getString(0); //The 0 is the column index, we only have 1 column, so the index is 0
            c.close();
        }


        return res;
    }
    //ID - returned
    public long findByName(String tableName, String fieldName, String valName){
        if(this.db==null){
            this.open();
        }
        long   res=0;
        String query = "SELECT  _ID FROM "+tableName+" WHERE "+fieldName+"= '"+valName+"'";
        Cursor c = this.db.rawQuery(query,null);
        if (c != null && c.moveToFirst()) {
            res = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
            c.close();
        }

        return res;
    }



	private void open() throws SQLException {
		try{
			this.db=this.dbHelper.getWritableDatabase();
		}catch(SQLiteException ex) {
			//Log.v("Open database exception caught", ex.getMessage());
			this.db = this.dbHelper.getReadableDatabase();
		} 
	}


    public SQLiteDatabase getDbForProvider(){
          return this.dbHelper.getReadableDatabase();
    }

  /*  public  String[] getMetaTables(){

        if(this.db==null){
            this.open();
        }
        String query = "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name";
        Cursor mCursor=db.rawQuery(query,null);
        String[] res=new String[mCursor.getCount()];

        mCursor.moveToFirst();
        int i=0;
        while (!mCursor.isAfterLast()){
            res[i]=  mCursor.getString(0);
            i++;
            mCursor.moveToNext();
        }
        mCursor.close();
       return res;
    }*/


	public void close() {
		this.dbHelper.close();
		//this.db.close();
	}

   ///Защишенные функции

    public String getDocIdVectorByTag(String mTag, String dateBeg, String dateEnd){
        String vectorStr="(";
        if(this.db==null){
            this.open();
        }

        if(dateBeg.length()==0){
            dateBeg= DateHelper.getNowFormatedDate();
        }
        if(dateEnd.length()==0){
            dateEnd=DateHelper.getNowFormatedDate();
        }
        String query="SELECT "
                +  MetaDatabase.Docs._ID
                + " FROM "
                + MetaDatabase.Docs.DOCS_TABLE
                + " WHERE "+MetaDatabase.Docs.FLAGPRIHRASH+" = '"+mTag+"'"+" AND date("+MetaDatabase.Docs.DATE_DOCS+") BETWEEN  '"+dateBeg+"' AND '"+dateEnd+"'";
        Cursor mCursor=db.rawQuery(query,null);

        while (mCursor.moveToNext()){

            String mcID=  mCursor.getString(0);
            vectorStr=vectorStr+mcID+",";

        }
        vectorStr=vectorStr.substring(0,vectorStr.length()-1)+")";
        return vectorStr;
    }

    public Cursor[] Pribil(String dateBegin, String dateEnd)  {
        Cursor[] res=new Cursor[2];
        if(this.db==null){
            this.open();
        }
        String docFiltr=getDocIdVectorByTag("-1",dateBegin,dateEnd);
        Cursor mCursor;
        Cursor mCursor2 ;
        if(docFiltr.length()<2){
            docFiltr="(-1)";
        }
        String query="SELECT "
                + "t2."+MetaDatabase.Goods._ID+", "
                + "t2."+MetaDatabase.Goods.CODE+" AS CODE, "
                + "t2."+MetaDatabase.Goods.NAME+" AS TOVAR, "
                + "total(t1."+MetaDatabase.RowDocs.SUM+") AS PRODAGA,"
                //              + "t1."+MetaDatabase.RowDocs.PRICE+", "
                + "total(t1."+MetaDatabase.RowDocs.QTY+") AS QTY, "
                + "total(t1."+MetaDatabase.RowDocs.SUM+")-total(t1."+MetaDatabase.RowDocs.QTY+")*ifnull(t2."+MetaDatabase.Goods.INPUTPRICE+",0) AS SUM"
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                //  + MetaDatabase.Docs.DOCS_TABLE+" AS t3"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" IN "+docFiltr
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                //   + " AND t3."+MetaDatabase.Docs._ID +" = t1."+MetaDatabase.RowDocs.DOCS_ID
                + " GROUP BY "+"t2."+MetaDatabase.Goods.CODE
                + " ORDER BY SUM DESC"
                ;
        mCursor=db.rawQuery(query,null);
        res[0]=mCursor;

        //for TOTAL
        String query2="SELECT 'total' AS total,"
                + "total(q1.SUM) AS SUM"
                + " FROM ("+ query+") AS q1"
                + " GROUP BY total"
                ;

        mCursor2=db.rawQuery(query2,null);
        res[1]=mCursor2;
        return res;
    }

    public Cursor[] KartaKlienta(String dateBegin, String dateEnd, String Where){
        Cursor[] res=new Cursor[2];
        if(this.db==null){
            this.open();
        }
        if(dateBegin==null||dateBegin.length()==0){
            dateBegin= DateHelper.getMonthBeforeNow(120);
        }
        if(dateEnd==null||dateEnd.length()==0){
            dateEnd=DateHelper.getNowFormatedDate();
        }

  /*      String queryGrouped="SELECT "
                + "t2."+MetaDatabase.Klients._ID+", "
                + "t1."+MetaDatabase.Vzaimoras.KLIENT_CODE+" AS CODE, "
                + "t2."+MetaDatabase.Klients.NAME+" AS KLIENT, "
                + "t1."+MetaDatabase.Vzaimoras.FLAGDVIG+" AS PRIHRASH, "
                + "total(t1."+MetaDatabase.RowDocs.SUM+") AS SUM"
                + " FROM "
                + MetaDatabase.Vzaimoras.TABLE_NAME +" AS t1 "+", "
                + MetaDatabase.Klients.Klients_TABLE +" AS t2 "+", "
                + " WHERE t1."+MetaDatabase.Vzaimoras.KLIENT_CODE+" = t2."+ MetaDatabase.Klients.CODE
                + " AND date(t1."+MetaDatabase.Vzaimoras.DOCDATE+") BETWEEN  '"+dateBegin+"' AND '"+dateEnd+"' "
                + Where
                + " GROUP BY KLIENT,PRIHRASH"
                + " ORDER BY KLIENT";*/

        String query="SELECT "
                + "t3."+MetaDatabase.Goods._ID+", "
                + "t3."+MetaDatabase.Goods.CODE+" AS CODE, "
                + "t3."+MetaDatabase.Goods.NAME+" AS TOVAR, "
                + "t2."+MetaDatabase.Docs.DATE_DOCS+" AS DATEDOC, "
                + "t2."+MetaDatabase.Docs._ID+" AS DOCID, "
                + "t2."+MetaDatabase.Docs.NAME+" AS DOCTYPE, "
                + "t2."+MetaDatabase.Docs.COMMENT+" AS COMMENT, "
                + "ifnull(t4."+MetaDatabase.Klients.NAME+",'') AS KLIENT, "
                + "ifnull(t5."+MetaDatabase.Sklads.NAME+",'') AS SKLAD, "
                + "t2."+MetaDatabase.Docs.FLAGPRIHRASH+" AS PRIHRASH, "
                + "t1."+MetaDatabase.RowDocs.QTY+" AS QTY, "
                + "t1."+MetaDatabase.RowDocs.PRICE+" AS PRICE,"
                + "t1."+MetaDatabase.RowDocs.DISCOUNT+" AS DISC, "
                + "t1."+MetaDatabase.RowDocs.SUM+" AS SUM"
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Docs.DOCS_TABLE +" AS t2 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t3 "
                +" LEFT JOIN "+ MetaDatabase.Klients.Klients_TABLE+" AS t4 "
                +" ON "+"t2."+MetaDatabase.Docs.KLIENT+"="+"t4."+  MetaDatabase.Klients.CODE
                +" LEFT JOIN "+ MetaDatabase.Sklads.Sklads_TABLE+" AS t5 "
                +" ON "+"t2."+MetaDatabase.Docs.SKLAD+"="+"t5."+  MetaDatabase.Sklads._ID
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = t2."+ MetaDatabase.Docs._ID
                + " AND t2."+MetaDatabase.Docs.FLAGPRIHRASH +" <>'0' "+"AND date(t2."+MetaDatabase.Docs.DATE_DOCS+") BETWEEN  '"+dateBegin+"' AND '"+dateEnd+"'"

                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t3."+MetaDatabase.Goods.CODE
                + Where

                + " ORDER BY TOVAR,DATEDOC";

        Cursor mCursor=db.rawQuery(query,null);

        res[0]=mCursor;
        return res;
    }

    public Cursor[] KartaDvigeniaTovara(String dateBegin, String dateEnd, String Where){
        Cursor[] res=new Cursor[2];
        if(this.db==null){
            this.open();
        }
        if(dateBegin==null||dateBegin.length()==0){
            dateBegin= DateHelper.getMonthBeforeNow(120);
        }
        if(dateEnd==null||dateEnd.length()==0){
            dateEnd=DateHelper.getNowFormatedDate();
        }

      /*  String queryGrouped="SELECT "
                + "t3."+MetaDatabase.Goods._ID+", "
                + "t3."+MetaDatabase.Goods.CODE+" AS CODE, "
                + "t3."+MetaDatabase.Goods.NAME+" AS TOVAR, "
                + "t2."+MetaDatabase.Docs.FLAGPRIHRASH+" AS PRIHRASH, "
                + "total(t1."+MetaDatabase.RowDocs.QTY+") AS QTY, "
                + "total(t1."+MetaDatabase.RowDocs.SUM+") AS SUM"
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Docs.DOCS_TABLE +" AS t2 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t3"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = t2."+ MetaDatabase.Docs._ID
                + " AND t2."+MetaDatabase.Docs.FLAGPRIHRASH +" <>'0' "+"AND date(t2."+MetaDatabase.Docs.DATE_DOCS+") BETWEEN  '"+dateBegin+"' AND '"+dateEnd+"'"
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t3."+MetaDatabase.Goods.CODE
                + Where
                + " GROUP BY TOVAR,PRIHRASH"
                + " ORDER BY TOVAR";*/

        String query="SELECT "
                + "t3."+MetaDatabase.Goods._ID+", "
                + "t3."+MetaDatabase.Goods.CODE+" AS CODE, "
                + "t3."+MetaDatabase.Goods.NAME+" AS TOVAR, "
                + "t2."+MetaDatabase.Docs.DATE_DOCS+" AS DATEDOC, "
                + "t2."+MetaDatabase.Docs._ID+" AS DOCID, "
                + "t2."+MetaDatabase.Docs.NAME+" AS DOCTYPE, "
                + "t2."+MetaDatabase.Docs.COMMENT+" AS COMMENT, "
                + "ifnull(t4."+MetaDatabase.Klients.NAME+",'') AS KLIENT, "
                + "ifnull(t5."+MetaDatabase.Sklads.NAME+",'') AS SKLAD, "
                + "t2."+MetaDatabase.Docs.FLAGPRIHRASH+" AS PRIHRASH, "
                + "t1."+MetaDatabase.RowDocs.QTY+" AS QTY, "
                + "t1."+MetaDatabase.RowDocs.PRICE+" AS PRICE,"
                + "t1."+MetaDatabase.RowDocs.DISCOUNT+" AS DISC, "
                + "t1."+MetaDatabase.RowDocs.SUM+" AS SUM"
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Docs.DOCS_TABLE +" AS t2 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t3 "
                +" LEFT JOIN "+ MetaDatabase.Klients.Klients_TABLE+" AS t4 "
                +" ON "+"t2."+MetaDatabase.Docs.KLIENT+"="+"t4."+  MetaDatabase.Klients.CODE
                +" LEFT JOIN "+ MetaDatabase.Sklads.Sklads_TABLE+" AS t5 "
                +" ON "+"t2."+MetaDatabase.Docs.SKLAD+"="+"t5."+  MetaDatabase.Sklads._ID
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = t2."+ MetaDatabase.Docs._ID
                + " AND t2."+MetaDatabase.Docs.FLAGPRIHRASH +" <>'0' "+"AND date(t2."+MetaDatabase.Docs.DATE_DOCS+") BETWEEN  '"+dateBegin+"' AND '"+dateEnd+"'"

                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t3."+MetaDatabase.Goods.CODE
                + Where

                + " ORDER BY TOVAR,DATEDOC";

        Cursor mCursor=db.rawQuery(query,null);

        res[0]=mCursor;
        return res;
    }


    public boolean fillDocByOstatokTEST(long id,int mVariant,long SkladCode){
        boolean resTr;
        ///получим остатки
       // if (mVariant==1){
       //     where=" WHERE "+MetaDatabase.Goods.BALANCE+" > 0";
       // }else if(mVariant==-1){
       //     where=" WHERE "+MetaDatabase.Goods.BALANCE+" < 0";
       // }

/*        String query="SELECT "
                + MetaDatabase.Goods._ID +", "
                + MetaDatabase.Goods.NAME+", "
                + MetaDatabase.Goods.CODE+", "
                + MetaDatabase.Goods.PRICE+", "
                +  MetaDatabase.Goods.BALANCE+", "
                + MetaDatabase.Goods.INPUTPRICE
                + " FROM "
                + MetaDatabase.Goods.GOODS_TABLE
                + where
                +  " ORDER BY "+MetaDatabase.Goods.NAME
                ;*/
        Cursor mCursor=OstatkiTovaraSklad(SkladCode);

        // Begin the transaction
        db.beginTransaction();
        try{
            while (mCursor.moveToNext()) {
                double qty= mCursor.getDouble(4);
                if (mVariant==1 && qty<=0){
                     continue;
                } else if(mVariant==-1 && qty>=0){
                   continue;
                }
                double sum=qty*mCursor.getDouble(3);
                sum *= 100;
                sum = (double) Math.round(sum)/100;

                ContentValues contentValues=new ContentValues();
                contentValues.put(MetaDatabase.RowDocs.DOCS_ID ,id);//docId
                contentValues.put(MetaDatabase.RowDocs.GOODS_CODE ,mCursor.getString(1));//code
                contentValues.put(MetaDatabase.RowDocs.QTY,mCursor.getString(4));//qty
                contentValues.put(MetaDatabase.RowDocs.PRICE, mCursor.getString(3));//price
                contentValues.put(MetaDatabase.RowDocs.SUM, sum);//sum
                contentValues.put(MetaDatabase.RowDocs.DISCOUNT, 0);//disk
                //contentValues.put(COLUMN_NAME,record);
                db.insert(MetaDatabase.RowDocs.ROWDOCS_TABLE,null,contentValues);

            }
            // Transaction is successful and all the records have been inserted
            db.setTransactionSuccessful();
            resTr=true;
        }catch(Exception e){
            resTr=false;
            //Log.e(“Error in transaction”,e.toString());
        }finally{
            //End the transaction
            db.endTransaction();
        }

        mCursor.close();
        return resTr;
    }

    public Cursor OstatkiTovaraSklad(long SkladCode){

        if(this.db==null){
            this.open();
        }

        String Where=" AND t2."+MetaDatabase.Docs.SKLAD+" = "+SkladCode+"";


        String queryGrouped="SELECT "
                + "t3."+MetaDatabase.Goods._ID+", "
                + "t3."+MetaDatabase.Goods.CODE+" AS CODE, "
                + "t3."+MetaDatabase.Goods.NAME+" AS TOVAR, "
                + "t3."+MetaDatabase.Goods.INPUTPRICE+", "
   //             + "t2."+MetaDatabase.Docs.FLAGPRIHRASH+" AS PRIHRASH, "
                + "total(t1."+MetaDatabase.RowDocs.QTY+"*t2."+MetaDatabase.Docs.FLAGPRIHRASH+") AS QTY "

                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Docs.DOCS_TABLE +" AS t2 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t3"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = t2."+ MetaDatabase.Docs._ID
                + " AND t2."+MetaDatabase.Docs.FLAGPRIHRASH +" <>'0' "
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t3."+MetaDatabase.Goods.CODE
                + Where
                + " GROUP BY TOVAR"
                + " ORDER BY TOVAR";
        return db.rawQuery(queryGrouped,null);
    }

    public Cursor OstatkiTovaraByDoc(){
        if(this.db==null){
            this.open();
        }

        String queryGrouped="SELECT "
                + "t3."+MetaDatabase.Goods._ID+", "
                + "t3."+MetaDatabase.Goods.CODE+" AS CODE, "
                + "t3."+MetaDatabase.Goods.NAME+" AS TOVAR, "
                //             + "t2."+MetaDatabase.Docs.FLAGPRIHRASH+" AS PRIHRASH, "
                + "total(t1."+MetaDatabase.RowDocs.QTY+"*t2."+MetaDatabase.Docs.FLAGPRIHRASH+") AS QTY "

                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Docs.DOCS_TABLE +" AS t2 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t3"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = t2."+ MetaDatabase.Docs._ID
                + " AND t2."+MetaDatabase.Docs.FLAGPRIHRASH +" <>'0' "
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t3."+MetaDatabase.Goods.CODE
       //         + Where
                + " GROUP BY TOVAR"
                + " ORDER BY TOVAR";
        return db.rawQuery(queryGrouped,null);
    }

    public Cursor[] ProdagiZakupkiTovara(String dateBegin, String dateEnd, int mFlag){
        Cursor[] res=new Cursor[2];
        if(this.db==null){
            this.open();
        }
        ///получим остатки
      //  if (mVariant==1){
       //     where=" WHERE "+MetaDatabase.Goods.BALANCE+" > 0";
        //}else if(mVariant==-1){
       //     where=" WHERE "+MetaDatabase.Goods.BALANCE+" < 0";
        //}
        String docFiltr="";
        if(mFlag==-1){
            docFiltr=getDocIdVectorByTag("-1",dateBegin,dateEnd);
        } else if (mFlag==1){
            docFiltr=getDocIdVectorByTag("1",dateBegin,dateEnd);
        }


        Cursor mCursor;
        Cursor mCursor2 ;
        if(docFiltr.length()<2){
            docFiltr="(-1)";
        }
        String query="SELECT "
                + "t2."+MetaDatabase.Goods._ID+", "
                + "t2."+MetaDatabase.Goods.CODE+" AS CODE, "
                + "t2."+MetaDatabase.Goods.NAME+" AS TOVAR, "
  //              + "t1."+MetaDatabase.RowDocs.PRICE+", "
                + "sum(t1."+MetaDatabase.RowDocs.QTY+") AS QTY, "
                + "sum(t1."+MetaDatabase.RowDocs.DISCOUNT+") AS DISC, "
                + "sum(t1."+MetaDatabase.RowDocs.SUM+") AS SUM"
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
              //  + MetaDatabase.Docs.DOCS_TABLE+" AS t3"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" IN "+docFiltr
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
             //   + " AND t3."+MetaDatabase.Docs._ID +" = t1."+MetaDatabase.RowDocs.DOCS_ID
                + " GROUP BY "+"t2."+MetaDatabase.Goods.CODE
                ;
        mCursor=db.rawQuery(query,null);
        res[0]=mCursor;

        //for TOTAL
        String query2="SELECT 'total' AS total, "
                + "sum(t1."+MetaDatabase.RowDocs.QTY+") AS QTY, "
                + "sum(t1."+MetaDatabase.RowDocs.DISCOUNT+") AS DISC, "
                + "sum(t1."+MetaDatabase.RowDocs.SUM+") AS SUM"
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "  //+", "
               // + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                //  + MetaDatabase.Docs.DOCS_TABLE+" AS t3"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" IN "+docFiltr
                //   + " AND t3."+MetaDatabase.Docs._ID +" = t1."+MetaDatabase.RowDocs.DOCS_ID
                + " GROUP BY total"
                ;
        mCursor2=db.rawQuery(query2,null);
        res[1]=mCursor2;
        return res;
    }

    public Cursor RashogdeniaInv(long uchetID, long factID){
        if(this.db==null){
            this.open();
        }

        String queryTFact="SELECT "
                + "t2."+MetaDatabase.Goods._ID+" AS id, "
                + "t2."+MetaDatabase.Goods.CODE+" AS CODE, "
                + "t2."+MetaDatabase.Goods.NAME+" AS TOVAR, "
                + "total(t1."+MetaDatabase.RowDocs.QTY+") AS FACTQTY "
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = "+factID
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                + " GROUP BY "+"t2."+MetaDatabase.Goods.CODE
                ;

        String queryTUChet="SELECT "
                + "t2."+MetaDatabase.Goods._ID+" AS id, "
                + "t2."+MetaDatabase.Goods.CODE+" AS CODE, "
                + "t2."+MetaDatabase.Goods.NAME+" AS TOVAR, "
                + "total(t1."+MetaDatabase.RowDocs.QTY+") AS UCHETQTY "
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = "+uchetID
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                + " GROUP BY "+"t2."+MetaDatabase.Goods.CODE
                ;


        String query="SELECT "
                + "res1.id AS _id,"
                + "res1.CODE AS CODE, "
                + "res1.TOVAR AS TOVAR, "
                + "res1.FACTQTY AS FACT, "
                + "ifnull(res2.UCHETQTY,0) AS UCHET,"
                + "res1.FACTQTY-ifnull(res2.UCHETQTY,0) AS RAZH"
                + " FROM ( "+ queryTFact+" ) AS res1 "
                + " LEFT JOIN ( "+queryTUChet+ " ) AS res2 "
                + " ON res1.CODE=res2.CODE "
                + " WHERE RAZH<>0"
                + " UNION ALL "
                + " SELECT "
                + "res22.id AS _id,"
                + "res22.CODE AS CODE, "
                + "res22.TOVAR AS TOVAR, "
                + "0 AS FACT, "
                + "res22.UCHETQTY AS UCHET,"
                + "-res22.UCHETQTY  AS RAZH"
                + " FROM ( "+ queryTUChet+" ) AS res22 "
                + " LEFT JOIN ( "+queryTFact+ " ) AS res11 "
                + " ON res22.CODE=res11.CODE "
                + " WHERE res11.CODE IS NULL"
                + " ORDER BY RAZH"
                ;

        return db.rawQuery(query,null);
    }

    public Cursor RashogdeniaInvNedost(long uchetID, long factID){
        if(this.db==null){
            this.open();
        }

        String queryTFact="SELECT "
                + "t2."+MetaDatabase.Goods._ID+" AS id, "
                + "t2."+MetaDatabase.Goods.CODE+" AS CODE, "
                + "t2."+MetaDatabase.Goods.NAME+" AS TOVAR, "
                + "t2."+MetaDatabase.Goods.INPUTPRICE+" AS PRICE, "
                + "total(t1."+MetaDatabase.RowDocs.QTY+") AS FACTQTY "
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = "+factID
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                + " GROUP BY "+"t2."+MetaDatabase.Goods.CODE
                ;

        String queryTUChet="SELECT "
                + "t2."+MetaDatabase.Goods._ID+" AS id, "
                + "t2."+MetaDatabase.Goods.CODE+" AS CODE, "
                + "t2."+MetaDatabase.Goods.NAME+" AS TOVAR, "
                + "t2."+MetaDatabase.Goods.INPUTPRICE+" AS PRICE, "
                + "total(t1."+MetaDatabase.RowDocs.QTY+") AS UCHETQTY "
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = "+uchetID
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                + " GROUP BY "+"t2."+MetaDatabase.Goods.CODE
                ;


        String query="SELECT "
                + "res1.id AS _id,"
                + "res1.CODE AS CODE, "
                + "res1.TOVAR AS TOVAR, "
                + "res1.FACTQTY AS FACT, "
                + "ifnull(res2.UCHETQTY,0) AS UCHET,"
                + "ifnull(res2.UCHETQTY,0)-res1.FACTQTY AS RAZH,"
                + "ifnull(res1.PRICE,0) AS PRICE, "
                + "(ifnull(res2.UCHETQTY,0)-res1.FACTQTY)*ifnull(res1.PRICE,0) AS SUMRAS"
                + " FROM ( "+ queryTFact+" ) AS res1 "
                + " LEFT JOIN ( "+queryTUChet+ " ) AS res2 "
                + " ON res1.CODE=res2.CODE "
                + " WHERE RAZH>0"
                + " UNION ALL "
                + " SELECT "
                + "res22.id AS _id,"
                + "res22.CODE AS CODE, "
                + "res22.TOVAR AS TOVAR, "
                + "0 AS FACT, "
                + "res22.UCHETQTY AS UCHET, "
                + "res22.UCHETQTY  AS RAZH, "
                + "ifnull(res22.PRICE,0)  AS PRICE, "
                + "res22.UCHETQTY*ifnull(res22.PRICE,0) AS SUMRAS"
                + " FROM ( "+ queryTUChet+" ) AS res22 "
                + " LEFT JOIN ( "+queryTFact+ " ) AS res11 "
                + " ON res22.CODE=res11.CODE "
                + " WHERE res11.CODE IS NULL"
                + " ORDER BY RAZH"
                ;

        return db.rawQuery(query,null);
    }


    public Cursor RashogdeniaInvIzlish(long uchetID, long factID){
        if(this.db==null){
            this.open();
        }

        String queryTFact="SELECT "
                + "t2."+MetaDatabase.Goods._ID+" AS id, "
                + "t2."+MetaDatabase.Goods.CODE+" AS CODE, "
                + "t2."+MetaDatabase.Goods.NAME+" AS TOVAR, "
                + "t2."+MetaDatabase.Goods.INPUTPRICE+" AS PRICE, "
                + "total(t1."+MetaDatabase.RowDocs.QTY+") AS FACTQTY "
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = "+factID
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                + " GROUP BY "+"t2."+MetaDatabase.Goods.CODE
                ;

        String queryTUChet="SELECT "
                + "t2."+MetaDatabase.Goods._ID+" AS id, "
                + "t2."+MetaDatabase.Goods.CODE+" AS CODE, "
                + "t2."+MetaDatabase.Goods.NAME+" AS TOVAR, "
                + "t2."+MetaDatabase.Goods.INPUTPRICE+" AS PRICE, "
                + "total(t1."+MetaDatabase.RowDocs.QTY+") AS UCHETQTY "
                + " FROM "
                + MetaDatabase.RowDocs.ROWDOCS_TABLE +" AS t1 "+", "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t2"
                + " WHERE t1."+MetaDatabase.RowDocs.DOCS_ID+" = "+uchetID
                + " AND t1."+MetaDatabase.RowDocs.GOODS_CODE +" = t2."+MetaDatabase.Goods.CODE
                + " GROUP BY "+"t2."+MetaDatabase.Goods.CODE
                ;


        String query="SELECT "
                + "res1.id AS _id,"
                + "res1.CODE AS CODE, "
                + "res1.TOVAR AS TOVAR, "
                + "res1.FACTQTY AS FACT, "
                + "ifnull(res2.UCHETQTY,0) AS UCHET,"
                + "res1.FACTQTY-ifnull(res2.UCHETQTY,0) AS RAZH,"
                + "ifnull(res1.PRICE,0) AS PRICE, "
                + "(res1.FACTQTY-ifnull(res2.UCHETQTY,0))*ifnull(res1.PRICE,0) AS SUMRAS"
                + " FROM ( "+ queryTFact+" ) AS res1 "
                + " LEFT JOIN ( "+queryTUChet+ " ) AS res2 "
                + " ON res1.CODE=res2.CODE "
                + " WHERE RAZH>0"
//                + " UNION ALL "
//                + " SELECT "
//                + "res22.id AS _id,"
//                + "res22.CODE AS CODE, "
//                + "res22.TOVAR AS TOVAR, "
//                + "0 AS FACT, "
//                + "res22.UCHETQTY AS UCHET, "
//                + "res22.UCHETQTY  AS RAZH, "
//                + "ifnull(res22.PRICE,0)  AS PRICE, "
//                + "res22.UCHETQTY*ifnull(res22.PRICE,0) AS SUMRAS"
//                + " FROM ( "+ queryTUChet+" ) AS res22 "
//                + " LEFT JOIN ( "+queryTFact+ " ) AS res11 "
//                + " ON res22.CODE=res11.CODE "
//                + " WHERE res11.CODE IS NULL"
                + " ORDER BY RAZH"
                ;

        return db.rawQuery(query,null);
    }

    public Cursor getGoodRefsInDocs(String mGoodCode){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
        +"t1."+MetaDatabase.Docs._ID
        +" FROM "
        + MetaDatabase.Docs.DOCS_TABLE+" AS t1"+", "
        + MetaDatabase.RowDocs.ROWDOCS_TABLE+" AS t2"
        +" WHERE t2."+MetaDatabase.RowDocs.GOODS_CODE+" = '"+mGoodCode+"'"
        +" AND t1."+MetaDatabase.Docs._ID +" = t2."+MetaDatabase.RowDocs.DOCS_ID
        + " GROUP BY t1."+MetaDatabase.Docs._ID;
        return db.rawQuery(query,null);
    }

    public Cursor getSkladRefsInDocs(long skladID){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +"t1."+MetaDatabase.Docs._ID
                +" FROM "
                + MetaDatabase.Docs.DOCS_TABLE+" AS t1"
                +" WHERE t1."+MetaDatabase.Docs.SKLAD+" = "+skladID
                + " GROUP BY t1."+MetaDatabase.Docs._ID;
        return db.rawQuery(query,null);
    }

    public String getSkladCodeByID(long mID){
        String res="";
        if(mID<=0){

        }   else{
            String queryTemp = "SELECT  "+MetaDatabase.Sklads.CODE+" FROM "+ MetaDatabase.Sklads.Sklads_TABLE+" WHERE "+ MetaDatabase.Sklads._ID+"= "+mID;
            Cursor ctemp = db.rawQuery(queryTemp,null);
            if (ctemp != null && ctemp.moveToFirst()) {
                res=ctemp.getString(0);

            }
            ctemp.close();
        }
       return res;
    }
    public long getSkladIDByCode(String mCode){
        long res=0;
        if(mCode.length()<=0){

        }   else{
            String queryTemp = "SELECT  "+MetaDatabase.Sklads._ID+" FROM "+ MetaDatabase.Sklads.Sklads_TABLE+" WHERE "+ MetaDatabase.Sklads.CODE+"= '"+mCode+"'";
            Cursor ctemp = db.rawQuery(queryTemp,null);
            if (ctemp != null && ctemp.moveToFirst()) {
                res=ctemp.getLong(0);

            }
            ctemp.close();
        }
        return res;
    }

    public Cursor getNomGroupRefsInGoods(long mID){
        if(this.db==null){
            this.open();
        }
        String query="SELECT "
                +"t1."+MetaDatabase.Goods._ID
                +" FROM "
                + MetaDatabase.Goods.GOODS_TABLE+" AS t1"
                +" WHERE t1."+MetaDatabase.Goods.NOMGRP+" = "+mID
                + " GROUP BY t1."+MetaDatabase.Goods._ID;
        return db.rawQuery(query,null);
    }
}
