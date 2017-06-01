package ru.a89uchet.roznica89.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.a89uchet.roznica89.db.MetaDatabase.*;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "mainuchet.db";
	private static final int DATABASE_VERSION = 1;
	static private MyDatabaseHelper _instance;
	Context mContext;














	protected MyDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext=context;
		// TODO Auto-generated constructor stub
	}
	// ВЫЗОВ ВСЕГДА ЭТОГО МЕТОДА
		public static MyDatabaseHelper instance(Context context){
			if (_instance == null) {
				_instance = new MyDatabaseHelper(context);
			}
			return _instance;
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
            db.execSQL(Groups.CREATE_TABLE);
            db.execSQL(NomenGroups.CREATE_TABLE);
			db.execSQL(Goods.CREATE_TABLE);
            db.execSQL(Goods.GOODS_IDX_CODE_CREATE);
            db.execSQL(Goods.GOODS_IDX_NOMGRP_CREATE);
            db.execSQL(Goods.GOODSPARENT_IDX_CREATE);
			db.execSQL(Barcodes.CREATE_TABLE);
			db.execSQL(Barcodes.BARCODES_IDX_CREATE);
			db.execSQL(Docs.CREATE_TABLE);
			db.execSQL(RowDocs.CREATE_TABLE);
			db.execSQL(RowDocs.ROW_DOCS_IDX_CREATE);

            db.execSQL(Marshruts.CREATE_TABLE);
            db.execSQL(Agents.CREATE_TABLE);
            db.execSQL(Klients.CREATE_TABLE);
            db.execSQL(Firms.CREATE_TABLE);
            db.execSQL(FormaOplat.CREATE_TABLE);

            db.execSQL(DopRekvizitsRef.CREATE_TABLE);
            db.execSQL(DopRekvizitsVals.CREATE_TABLE);
            db.execSQL(RegistrDopReks.CREATE_TABLE);

            createUserTable(db);
            db.execSQL(Sklads.CREATE_TABLE);

            createTaskTable(db);
            createVzaimoTable(db);

            createZak_PKO_RKO(db);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			//clearDB(db);



        }

    public void createUserTable(SQLiteDatabase db){
        db.execSQL(Users.CREATE_TABLE);

        ContentValues dataStr=new ContentValues();
        dataStr.put(Users.UserName,"Admin");
        dataStr.put(Users.UserPassword,"");
        db.insert(Users.Users_TABLE,null,dataStr);

    }

    public void createTaskTable(SQLiteDatabase db){
        db.execSQL(Tasks.CREATE_TABLE);
        ContentValues dataStr=new ContentValues();
        dataStr.put(Tasks.NAME,"Архивация");
        dataStr.put(Tasks.ISACTIVE,"0");
        dataStr.put(Tasks.STARTON,"");
        dataStr.put(Tasks.REPEAT_EDINICA,"день");
        dataStr.put(Tasks.REPEATE_INTERVAL,"1");
        dataStr.put(Tasks.DROPBOX_SINH,"0");
        dataStr.put(Tasks.EMAIL_TO,"");
        dataStr.put(Tasks.SMS_TO_PHONE,"");
        db.insert(Tasks.TABLE_NAME,null,dataStr);

        ContentValues dataStr2=new ContentValues();
        dataStr2.put(Tasks.NAME,"Отчет о продажах");
        dataStr2.put(Tasks.ISACTIVE,"0");
        dataStr2.put(Tasks.STARTON,"");
        dataStr2.put(Tasks.REPEAT_EDINICA,"день");
        dataStr2.put(Tasks.REPEATE_INTERVAL,"1");
        dataStr2.put(Tasks.DROPBOX_SINH,"0");
        dataStr2.put(Tasks.EMAIL_TO,"");
        dataStr2.put(Tasks.SMS_TO_PHONE,"");
        db.insert(Tasks.TABLE_NAME,null,dataStr2);

        ContentValues dataStr3=new ContentValues();
        dataStr3.put(Tasks.NAME,"Загрузка заказов");
        dataStr3.put(Tasks.ISACTIVE,"0");
        dataStr3.put(Tasks.STARTON,"");
        dataStr3.put(Tasks.REPEAT_EDINICA,"день");
        dataStr3.put(Tasks.REPEATE_INTERVAL,"1");
        dataStr3.put(Tasks.DROPBOX_SINH,"0");
        dataStr3.put(Tasks.EMAIL_TO,"");
        dataStr3.put(Tasks.SMS_TO_PHONE,"");
        db.insert(Tasks.TABLE_NAME,null,dataStr3);
    }

    public void createVzaimoTable(SQLiteDatabase db){
        db.execSQL(Vzaimoras.CREATE_TABLE);
        db.execSQL(Vzaimoras.VZAIM_DOC_IDX_CREATE);
    }
    public void deleteVzaimoTable(SQLiteDatabase db){
        db.execSQL("DROP INDEX IF EXISTS vzDocIdx");
        db.execSQL("DROP TABLE IF EXISTS "+Vzaimoras.TABLE_NAME);
    }

    public void createZak_PKO_RKO(SQLiteDatabase db){
        db.execSQL(DocsZakaz.CREATE_TABLE);
        db.execSQL(RowDocsZakaz.CREATE_TABLE);
        db.execSQL(RowDocsZakaz.Z_ROW_DOCS_IDX_CREATE);
        db.execSQL(PKO.CREATE_TABLE);
        db.execSQL(RKO.CREATE_TABLE);
    }

    public void deleteZak_PKO_RKO(SQLiteDatabase db){
        db.execSQL("DROP INDEX IF EXISTS zrowdocsIdx");
        db.execSQL("DROP TABLE IF EXISTS "+RowDocsZakaz.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+DocsZakaz.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+PKO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+RKO.TABLE_NAME);
;
    }

    public void clearGoods(SQLiteDatabase db){
        db.execSQL("DROP INDEX IF EXISTS qIdx");
        db.execSQL("DROP INDEX IF EXISTS codeIdx");
        db.execSQL("DROP INDEX IF EXIST ngrpIdx");
        db.execSQL("DROP INDEX IF EXIST parentIdx");
        // DROP TABLES
        db.execSQL("DROP TABLE IF EXISTS "+Groups.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+Barcodes.BARCODES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+Goods.GOODS_TABLE);
        db.execSQL(Groups.CREATE_TABLE);
        db.execSQL(Goods.CREATE_TABLE);
        db.execSQL(Goods.GOODS_IDX_CODE_CREATE);
        db.execSQL(Goods.GOODS_IDX_NOMGRP_CREATE);
        db.execSQL(Goods.GOODSPARENT_IDX_CREATE);
        db.execSQL(Barcodes.CREATE_TABLE);
        db.execSQL(Barcodes.BARCODES_IDX_CREATE);
    }

		public void clearDB(SQLiteDatabase db){
			db.execSQL("DROP INDEX IF EXISTS rowdocsIdx");
			db.execSQL("DROP INDEX IF EXISTS qIdx");
            db.execSQL("DROP INDEX IF EXISTS codeIdx");
            db.execSQL("DROP INDEX IF EXIST ngrpIdx");
            db.execSQL("DROP INDEX IF EXIST parentIdx");
			// DROP TABLES
			db.execSQL("DROP TABLE IF EXISTS "+RowDocs.ROWDOCS_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "+Docs.DOCS_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "+Barcodes.BARCODES_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+NomenGroups.NomenGroups_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+Sklads.Sklads_TABLE);

            db.execSQL("DROP TABLE IF EXISTS "+Groups.TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS "+Goods.GOODS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+Klients.Klients_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+Marshruts.Marshruts_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+Agents.Agents_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+Firms.Firms_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+FormaOplat.FormaOplat_TABLE);


            db.execSQL("DROP TABLE IF EXISTS "+RegistrDopReks.RegistrDopReks_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+DopRekvizitsVals.DopRekvizitsVals_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+DopRekvizitsRef.DopRekvizitsRef_TABLE);

            db.execSQL("DROP TABLE IF EXISTS "+Users.Users_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+Tasks.TABLE_NAME);

            deleteVzaimoTable(db);

            deleteZak_PKO_RKO(db);
			onCreate(db);
		}


        public void clearDocs(SQLiteDatabase db)   {
            db.execSQL("DROP INDEX IF EXISTS rowdocsIdx");
            db.execSQL("DROP TABLE IF EXISTS "+RowDocs.ROWDOCS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+Docs.DOCS_TABLE);

            deleteZak_PKO_RKO(db);

            db.execSQL(Docs.CREATE_TABLE);
            db.execSQL(RowDocs.CREATE_TABLE);
            db.execSQL(RowDocs.ROW_DOCS_IDX_CREATE);

            createZak_PKO_RKO(db);

            deleteVzaimoTable(db);
            createVzaimoTable(db);

            String codeG="-1";
            String query="UPDATE "
                    + MetaDatabase.Goods.GOODS_TABLE
                    + " SET "
                    + MetaDatabase.Goods.BALANCE+" = 0"
                    + " WHERE "+MetaDatabase.Goods.CODE+" <> '"+codeG+"'"
                    ;
           db.execSQL(query);

            String query2="UPDATE "
                    + Klients.Klients_TABLE
                    + " SET "
                    + Klients.BALANCE+" = 0"
                    + " WHERE "+ Klients.CODE+" <> '"+codeG+"'"
                    ;
            db.execSQL(query2);

        }


    /// UPGRADES HERE----------------------------------------------------------

}
