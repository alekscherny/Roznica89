package ru.a89uchet.roznica89.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.preference.PreferenceManager;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 13.04.13
 * Time: 6:57
 * To change this template use File | Settings | File Templates.
 */
public class ShopProvider extends ContentProvider {
    private static final String AUTHORITY = "ua.cherny.kassir.db.contentprovider.Shop";  //как в манифесте

    private static final String GOODS_PATH = "goods";
    private static final String GROUPS_PATH = "groups" ;
    private static final String KLIENTS_PATH = "klients";
    private static final String BUNDLE_PATH = "bundle";

    // Общие Uri
    public static final Uri GOODS_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + GOODS_PATH);
    public static final Uri GROUPS_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + GROUPS_PATH);
    public static final Uri KLIENTS_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + KLIENTS_PATH);

    public static final Uri BUNDLE_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + BUNDLE_PATH);

    // Used for the UriMacher
    private static final int GOODS = 10;
   // private static final int GOODS_ID = 11;
    private static final int GROUPS = 20;

    private static final int KLIENTS = 30;


    private static final int MYBUNDLE = 40;

    // описание и создание UriMatcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, GOODS_PATH, GOODS);
       // uriMatcher.addURI(AUTHORITY, GOODS_PATH + "/#", GOODS_ID);
        uriMatcher.addURI(AUTHORITY, GROUPS_PATH , GROUPS);

        uriMatcher.addURI(AUTHORITY, KLIENTS_PATH , KLIENTS);
        uriMatcher.addURI(AUTHORITY, BUNDLE_PATH , MYBUNDLE);
    }

    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        db=DBConnector.instance(getContext()).getDbForProvider();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();


        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case GOODS:
                queryBuilder.setTables(MetaDatabase.Goods.GOODS_TABLE);
                break;
            case GROUPS:
                queryBuilder.setTables(MetaDatabase.NomenGroups.NomenGroups_TABLE);
                break;
            case KLIENTS:
                queryBuilder.setTables(MetaDatabase.Klients.Klients_TABLE);
                break;
            case MYBUNDLE:
                MatrixCursor mc=new MatrixCursor(new String[] {"name","value"});

                mc.addRow(new Object[]{"photoDir",
                        PreferenceManager.getDefaultSharedPreferences(getContext()).getString("photoDir","")});
                mc.addRow(new Object[]{"test",
                        "test value"});
                return mc;
                //queryBuilder.setTables(MetaDatabase.Klients.Klients_TABLE);
               // break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);

        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }


   /* public Bundle getApp89Bundle(Uri uri,String arg1,Bundle arg2){
        Bundle returnedBundle=new Bundle();
        returnedBundle.putString("photoDir",
                PreferenceManager.getDefaultSharedPreferences(Config.globContext).getString("photoDir",""));



        return  returnedBundle;
    }*/

   /* private void checkColumns(String[] projection) {
        String[] available = { TodoTable.COLUMN_CATEGORY,
                TodoTable.COLUMN_SUMMARY, TodoTable.COLUMN_DESCRIPTION,
                TodoTable.COLUMN_ID };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // Check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }*/
}
