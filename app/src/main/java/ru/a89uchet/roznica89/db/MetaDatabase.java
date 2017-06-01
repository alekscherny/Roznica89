package ru.a89uchet.roznica89.db;

import android.provider.BaseColumns;

public final class MetaDatabase {
    private MetaDatabase(){}
	
	public static final class Goods implements BaseColumns {
		private Goods(){}
		
		public static final String GOODS_TABLE="goods";
		public static final String CODE="code";
		public static final String NAME="name";
		public static final String PRICE="price";
        public static final String INPUTPRICE="inputprice";
        public static final String LNAME="LNAME";
        public static final String BALANCE="balance";
        public static final String NOMGRP="nomgrp";
        public static final String PARENTCODE="parentcode";
        public static final String SITE_PRICE="site_price";

        public static final String CREATE_TABLE="CREATE TABLE " + Goods.GOODS_TABLE+ " ("
                + Goods._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Goods.CODE + " TEXT UNIQUE NOT NULL,"
                + Goods.NAME + " TEXT,"
                + Goods.PRICE + " REAL DEFAULT 0,"
                + Goods.INPUTPRICE + " REAL DEFAULT 0,"
                + Goods.LNAME + " TEXT,"
                + Goods.NOMGRP + " INTEGER DEFAULT 0,"
                + Goods.PARENTCODE + " TEXT,"
                + Goods.BALANCE + " REAL DEFAULT 0,"
                + Goods.SITE_PRICE + " REAL DEFAULT 0"
                + ");";

        public static final String GOODSPARENT_IDX_CREATE="CREATE INDEX parentIdx "+
                "ON "+Goods.GOODS_TABLE+" ("+Goods.PARENTCODE+")";

        public static final String GOODS_IDX_CODE_CREATE="CREATE INDEX codeIdx "+
                "ON "+Goods.GOODS_TABLE+" ("+Goods.CODE+")";

        public static final String GOODS_IDX_NOMGRP_CREATE="CREATE INDEX ngrpIdx "+
                "ON "+Goods.GOODS_TABLE+" ("+Goods.NOMGRP+")";
	}
	
	public static final class Barcodes implements BaseColumns {
		private Barcodes(){}
		
		public static final String BARCODES_TABLE="table_barcodes";
		public static final String BARCODE="barcodes_barcode";
		public static final String GOODS_CODE="barcodes_owner_code";
		public static final String QINDEX="barcodes_qindex";//last 4 digits

        public static final String CREATE_TABLE="CREATE TABLE " + Barcodes.BARCODES_TABLE+ " ("
                + Barcodes._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Barcodes.BARCODE + " TEXT UNIQUE NOT NULL,"
                + Barcodes.GOODS_CODE+ " TEXT REFERENCES "+Goods.GOODS_TABLE+"("+Goods.CODE+"),"
                + Barcodes.QINDEX + " TEXT"
                + ");";

        public static final String BARCODES_IDX_CREATE="CREATE INDEX qIdx "+
                "ON "+Barcodes.BARCODES_TABLE+" ("+Barcodes.QINDEX+")";

	}


	public static final class Docs implements BaseColumns {
		private Docs(){}
		
		public static final String DOCS_TABLE="table_docs";
		public static final String DATE_DOCS="docs_date";
		public static final String NAME="docs_name";
		public static final String COMMENT="docs_comment";
		public static final String SUM="docs_sum";
        public static final String DISCPERC="docs_persent";
        public static final String FLAGPRIHRASH="docs_prihrash";
        public static final String KLIENT="docs_klient";
        public static final String USER="docs_user";
        public static final String SKLAD="docs_sklad";
		public static final String SORT_DEF=DATE_DOCS+ " ASC";
        public static final String SITE_COMMENT="site_comment";
        public static final String UUID="uuid";    //1c UUID
        public static final String HAS_NDS="hasnds";
        public static final String PRICEWITHNDS="pricewithnds";
        public static final String UPLOADED="uploaded";

        public static final String IN_NUM="in_num";

        public static final String FIRM="docs_firm";
        public static final String FOPLAT="docs_foplat";

        public static final String CREATE_TABLE="CREATE TABLE " + Docs.DOCS_TABLE+ " ("
                + Docs._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Docs.DATE_DOCS + " DATETIME,"
                + Docs.NAME + " TEXT,"
                + Docs.SUM + " REAL,"
                + Docs.COMMENT + " TEXT,"
                + Docs.FLAGPRIHRASH + " INTEGER DEFAULT 0,"
                + Docs.KLIENT + " TEXT,"
                + Docs.USER + " TEXT,"
                + Docs.SKLAD + " INTEGER DEFAULT 0,"
                + Docs.DISCPERC + " REAL,"
                + Docs.UUID + " TEXT,"
                + Docs.HAS_NDS + " INTEGER DEFAULT 1,"
                + Docs.PRICEWITHNDS + " INTEGER DEFAULT 0,"
                + Docs.UPLOADED + " INTEGER DEFAULT 0,"
                + Docs.IN_NUM + " TEXT,"
                + Docs.FIRM + " TEXT,"
                + Docs.FOPLAT + " TEXT,"
                + Docs.SITE_COMMENT + " TEXT"
                + ");";
	}
	
	public static final class RowDocs implements BaseColumns {
		private RowDocs(){}
		
		public static final String ROWDOCS_TABLE="table_rowdocs";
		public static final String DOCS_ID="rowdocs_docs_id";
		public static final String GOODS_CODE="row_goods_code";
		public static final String QTY="rowdocs_qty";
		public static final String PRICE="rowdocs_price";
		public static final String SUM="rowdocs_sum";
		public static final String DISCOUNT="rowdocs_disk";

        public static final String IN_QTY="in_disk";//electron qty
        public static final String IN_ROW_NUM="in_rownum";


        public static final String CREATE_TABLE="CREATE TABLE " + RowDocs.ROWDOCS_TABLE+ " ("
                + RowDocs._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + RowDocs.DOCS_ID + " INTEGER,"
                + RowDocs.IN_ROW_NUM + " INTEGER,"
                + RowDocs.GOODS_CODE + " TEXT,"
                + RowDocs.QTY + " REAL,"
                + RowDocs.IN_QTY + " REAL,"
                + RowDocs.PRICE + " REAL,"
                + RowDocs.SUM + " REAL,"
                + RowDocs.DISCOUNT + " REAL"
                + ");";

        public static final String ROW_DOCS_IDX_CREATE="CREATE INDEX rowdocsIdx "+
                "ON "+RowDocs.ROWDOCS_TABLE+" ("+RowDocs.DOCS_ID+")";
	}

    public static final class Agents implements BaseColumns {
        private Agents(){}
        public static final String Agents_TABLE="table_agents";
        public static final String CODE ="code";
        public static final String NAME ="name";
        public static final String COMMENT="comment";

        public static final String CREATE_TABLE  ="CREATE TABLE " + Agents.Agents_TABLE+ " ("
                + Agents._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Agents.CODE + " TEXT UNIQUE NOT NULL,"
                + Agents.NAME + " TEXT,"
                + Agents.COMMENT + " TEXT"
                + ");";

    }
    public static final class Marshruts implements BaseColumns {
        private Marshruts(){}
        public static final String Marshruts_TABLE="table_marshruts";
        public static final String CODE ="code";
        public static final String NAME ="name";
        public static final String COMMENT="comment";


        public static final String CREATE_TABLE  ="CREATE TABLE " + Marshruts.Marshruts_TABLE+ " ("
                + Marshruts._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Marshruts.CODE + " TEXT UNIQUE NOT NULL,"
                + Marshruts.NAME + " TEXT,"
                + Marshruts.COMMENT + " TEXT"
                + ");";

    }

    public static final class Firms implements BaseColumns {
        private Firms(){}
        public static final String Firms_TABLE="table_firms";
        public static final String CODE ="code";
        public static final String NAME ="name";
        public static final String FORMA_OPLAT="formoplat";

        public static final String CREATE_TABLE  ="CREATE TABLE " + Firms.Firms_TABLE+ " ("
                + Firms._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Firms.CODE + " TEXT UNIQUE NOT NULL,"
                + Firms.NAME + " TEXT,"
                + Firms.FORMA_OPLAT + " TEXT"
                + ");";

    }
    public static final class FormaOplat implements BaseColumns {
        private FormaOplat(){}
        public static final String FormaOplat_TABLE="table_formopl";
        public static final String CODE ="code";
        public static final String NAME ="name";

        public static final String CREATE_TABLE  ="CREATE TABLE " + FormaOplat.FormaOplat_TABLE+ " ("
                + FormaOplat._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + FormaOplat.CODE + " TEXT UNIQUE NOT NULL,"
                + FormaOplat.NAME + " TEXT"
                + ");";
    }
    public static final class Klients implements BaseColumns {
        private Klients(){}
        public static final String Klients_TABLE="table_klients";
        public static final String CODE ="kl_code";
        public static final String NAME ="kl_name";
        public static final String LNAME="LNAME";
        public static final String BALANCE="kl_balance";
        public static final String PHONE="kl_phone";
        public static final String EMAIL="kl_email";

        public static final String TRADE_AGENT="kl_agent";
        public static final String MARSHRUT="kl_marshrut";


        public static final String CREATE_TABLE="CREATE TABLE " + Klients.Klients_TABLE+ " ("
                + Klients._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Klients.CODE + " TEXT UNIQUE NOT NULL,"
                + Klients.NAME + " TEXT,"
                + Klients.LNAME + " TEXT,"
                + Klients.EMAIL + " TEXT,"
                + Klients.PHONE + " TEXT,"
                + Klients.TRADE_AGENT + " TEXT,"
                + Klients.MARSHRUT + " TEXT,"
                + Klients.BALANCE + " REAL DEFAULT 0"
                + ");";


    }
    public static final class DopRekvizitsRef implements BaseColumns {
        private DopRekvizitsRef(){}
        public static final String DopRekvizitsRef_TABLE="table_doprekvizitsref";
        public static final String OWNERTABLE ="drr_ownertable";
        public static final String NAME ="drr_name";
        public static final String TYPE ="drr_type";

        public static final String CREATE_TABLE="CREATE TABLE " + DopRekvizitsRef.DopRekvizitsRef_TABLE+ " ("
                + DopRekvizitsRef._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + DopRekvizitsRef.OWNERTABLE + " TEXT,"
                + DopRekvizitsRef.NAME + " TEXT,"
                + DopRekvizitsRef.TYPE + " TEXT"
                + ");";
    }
    public static final class DopRekvizitsVals implements BaseColumns {
        private DopRekvizitsVals(){}
        public static final String DopRekvizitsVals_TABLE="table_doprekvizitsvals";
        public static final String DRRID ="drv_drrid";
        public static final String VALUE ="drv_value";

        //значения для списковых типов таблицы DopRekvizitsRef
        public static final String CREATE_TABLE="CREATE TABLE " + DopRekvizitsVals.DopRekvizitsVals_TABLE+ " ("
                + DopRekvizitsVals._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + DopRekvizitsVals.DRRID + " INTEGER,"
                + DopRekvizitsVals.VALUE + " TEXT"
                + ");";

    }
    public static final class RegistrDopReks implements BaseColumns {
        private RegistrDopReks(){}
        public static final String RegistrDopReks_TABLE="table_regdoprek";
        public static final String OWNERID ="rdr_ownerid";
        public static final String DRRID ="rdr_drrid";
        public static final String VAL ="rdr_val";

        public static final String CREATE_TABLE="CREATE TABLE " + RegistrDopReks.RegistrDopReks_TABLE+ " ("
                + RegistrDopReks._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + RegistrDopReks.OWNERID + " INTEGER," //объект - товар, клиент,документ
                + RegistrDopReks.DRRID + " INTEGER,"    //здесь ссылка на DopRekvizitsRef
                + RegistrDopReks.VAL + " TEXT"
                + ");";
    }

    public static final class Users implements BaseColumns {
        private Users(){}
        public static final String Users_TABLE="t_users";
        public static final String UserName="username";
        public static final String UserPassword="userpasswd";

        public static final String CREATE_TABLE  ="CREATE TABLE " + Users.Users_TABLE+ " ("
                + Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Users.UserName + " TEXT UNIQUE NOT NULL,"
                + Users.UserPassword + " TEXT"
                + ");";
    }
    public static final class Sklads implements BaseColumns {
        private Sklads(){}
        public static final String Sklads_TABLE="table_sklads";
        public static final String CODE ="skl_code";
        public static final String NAME ="skl_name";
        public static final String EMAIL="skl_email";

        public static final String CREATE_TABLE  ="CREATE TABLE " + Sklads.Sklads_TABLE+ " ("
                + Sklads._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Sklads.CODE + " TEXT UNIQUE NOT NULL,"
                + Sklads.NAME + " TEXT ,"
                + Sklads.EMAIL + " TEXT"
                + ");";

    }
    // PARENT_ID=-1 - ROOT, 0 - NO PARENT
    public static final class NomenGroups implements BaseColumns {
        private NomenGroups(){}
        public static final String NomenGroups_TABLE="t_nomengroups";
        public static final String NAME ="ng_name";
        public static final String PARENT_ID ="ng_parentid";

        public static final String CREATE_TABLE  ="CREATE TABLE " + NomenGroups.NomenGroups_TABLE+ " ("
                + NomenGroups._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + NomenGroups.NAME + " TEXT , "
                + NomenGroups.PARENT_ID+ " INTEGER DEFAULT 0"
                + ");";
    }

    public static final class Groups implements BaseColumns {
        private Groups(){}

        public static final String TABLE_NAME="table_groups";
        public static final String CODE="code";
        public static final String PARENTCODE="parentcode";
        public static final String NAME="name";

        public static final String CREATE_TABLE="CREATE TABLE " + Groups.TABLE_NAME+ " ("
                + Groups._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Groups.CODE + " TEXT UNIQUE NOT NULL,"
                + Groups.PARENTCODE + " TEXT,"
                + Groups.NAME + " TEXT"
                + ");";
    }

    public static final class Tasks implements BaseColumns {
        private Tasks(){}
        public static final String TABLE_NAME="table_tasks";
        public static final String NAME="name";
        public static final String ISACTIVE="is_active";
        public static final String STARTON="start_time";
        public static final String REPEAT_EDINICA="rep_edin";
        public static final String REPEATE_INTERVAL="rep_val";
        public static final String DROPBOX_SINH="drop_sinh";
        public static final String EMAIL_TO="email_to";
        public static final String SMS_TO_PHONE="sms_to";


        public static final String CREATE_TABLE="CREATE TABLE " + Tasks.TABLE_NAME+ " ("
                + Tasks._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Tasks.NAME + " TEXT ,"
                + Tasks.ISACTIVE + " TEXT,"
                + Tasks.STARTON + " TEXT,"
                + Tasks.REPEAT_EDINICA + " TEXT,"
                + Tasks.REPEATE_INTERVAL + " TEXT,"
                + Tasks.EMAIL_TO + " TEXT,"
                + Tasks.DROPBOX_SINH + " TEXT,"
                + Tasks.SMS_TO_PHONE + " TEXT"
                + ");";

    }

    public static final class Vzaimoras implements BaseColumns {
        private Vzaimoras(){}
        public static final String TABLE_NAME="table_vzaim";
        public static final String DOCID="vz_docid";
        public static final String DOCDATE="vz_date";
        public static final String KLIENT_CODE="vz_client_code";
        public static final String SUMM="vzaim_sum";
        public static final String FLAGDVIG="vzaim_flag";  //1-приход -1 расход
        public static final String DOCTYPE="vz_doctype";


        public static final String CREATE_TABLE="CREATE TABLE " + Vzaimoras.TABLE_NAME+ " ("
                + Vzaimoras._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Vzaimoras.DOCID + " INTEGER ,"
                + Vzaimoras.DOCDATE + " DATETIME,"
                + Vzaimoras.KLIENT_CODE + " TEXT,"
                + Vzaimoras.SUMM + " REAL,"
                + Vzaimoras.FLAGDVIG + "  INTEGER DEFAULT 0,"
                + Vzaimoras.DOCTYPE + " TEXT"
                + ");";

        public static final String VZAIM_DOC_IDX_CREATE="CREATE INDEX vzDocIdx "+
                "ON "+ Vzaimoras.TABLE_NAME+" ("+ Vzaimoras.DOCID+")";
    }

    ///////////////////////////////////////////////////////////////
    public static final class DocsZakaz implements BaseColumns {
        private DocsZakaz(){}

        public static final String TABLE_NAME="zakaz_docs";
        public static final String DATE_DOCS="z_date";
        public static final String NOMER="z_num";
        public static final String STATUS="z_status";
        public static final String COMMENT="z_comment";
        public static final String SUM="z_sum";
        public static final String KLIENT="z_klient";
        public static final String USER="z_user";
        public static final String SKLAD="z_sklad";


        public static final String CREATE_TABLE="CREATE TABLE " + DocsZakaz.TABLE_NAME+ " ("
                + DocsZakaz._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + DocsZakaz.DATE_DOCS + " DATETIME,"
                + DocsZakaz.NOMER + " TEXT,"
                + DocsZakaz.STATUS + " TEXT,"
                + DocsZakaz.SUM + " REAL,"
                + DocsZakaz.COMMENT + " TEXT,"
                + DocsZakaz.KLIENT + " TEXT,"
                + DocsZakaz.USER + " TEXT,"
                + DocsZakaz.SKLAD + " INTEGER DEFAULT 0"
                + ");";
    }

    public static final class RowDocsZakaz implements BaseColumns {
        private RowDocsZakaz(){}

        public static final String TABLE_NAME="zakaz_rowdocs";
        public static final String DOCS_ID="z_docs_id";
        public static final String GOODS_CODE="row_goods_code";
        public static final String QTY="rowdocs_qty";
        public static final String PRICE="rowdocs_price";
        public static final String SUM="rowdocs_sum";

        public static final String CREATE_TABLE="CREATE TABLE " + RowDocsZakaz.TABLE_NAME+ " ("
                + RowDocsZakaz._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + RowDocsZakaz.DOCS_ID + " INTEGER,"
                + RowDocsZakaz.GOODS_CODE + " TEXT,"
                + RowDocsZakaz.QTY + " REAL,"
                + RowDocsZakaz.PRICE + " REAL,"
                + RowDocsZakaz.SUM + " REAL"
                + ");";

        public static final String Z_ROW_DOCS_IDX_CREATE="CREATE INDEX zrowdocsIdx "+
                "ON "+ RowDocsZakaz.TABLE_NAME+" ("+ RowDocsZakaz.DOCS_ID+")";


    }

    public static final class PKO implements BaseColumns {
        private PKO(){}

        public static final String TABLE_NAME="pko_docs";
        public static final String DATE_DOCS="z_date";
        public static final String NOMER="z_num";
        public static final String STATUS="z_status";
        public static final String COMMENT="z_comment";
        public static final String SUM="z_sum";
        public static final String KLIENT="z_klient";
        public static final String USER="z_user";


        public static final String CREATE_TABLE="CREATE TABLE " + PKO.TABLE_NAME+ " ("
                + PKO._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + PKO.DATE_DOCS + " DATETIME,"
                + PKO.NOMER + " TEXT,"
                + PKO.STATUS + " TEXT,"
                + PKO.SUM + " REAL,"
                + PKO.COMMENT + " TEXT,"
                + PKO.KLIENT + " TEXT,"
                + PKO.USER + " TEXT"
                + ");";
    }
    public static final class RKO implements BaseColumns {
        private RKO(){}

        public static final String TABLE_NAME="rko_docs";
        public static final String DATE_DOCS="z_date";
        public static final String NOMER="z_num";
        public static final String STATUS="z_status";
        public static final String COMMENT="z_comment";
        public static final String SUM="z_sum";
        public static final String KLIENT="z_klient";
        public static final String USER="z_user";


        public static final String CREATE_TABLE="CREATE TABLE " + RKO.TABLE_NAME+ " ("
                + RKO._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + RKO.DATE_DOCS + " DATETIME,"
                + RKO.NOMER + " TEXT,"
                + RKO.STATUS + " TEXT,"
                + RKO.SUM + " REAL,"
                + RKO.COMMENT + " TEXT,"
                + RKO.KLIENT + " TEXT,"
                + RKO.USER + " TEXT"
                + ");";
    }
    //////////////////////////////////////////////////////////////


}
