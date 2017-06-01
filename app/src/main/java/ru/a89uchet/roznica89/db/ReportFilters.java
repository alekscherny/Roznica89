package ru.a89uchet.roznica89.db;

import android.provider.BaseColumns;

/**
 * Created with IntelliJ IDEA.
 * User: Босс
 * Date: 20.08.12
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
public class ReportFilters {
    public static final class SortBy implements BaseColumns {
        private SortBy(){}
        public static final String Goods= " ORDER BY "+MetaDatabase.Goods.NAME;
        public static final String Klients= " ORDER BY "+MetaDatabase.Klients.NAME;
        public static final String DateDoc= " ORDER BY "+MetaDatabase.Docs.DATE_DOCS;
    }

    public static final class  TipSrav{
        private TipSrav(){}
        public static final String EQ= " = ";
        public static final String NEQ= " != ";
        public static final String IN= " IN ";
    }
}
