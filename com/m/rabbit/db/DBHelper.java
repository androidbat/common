package com.m.rabbit.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {
    private static String mDataBaseDir;
    private static String mDataBaseName;
    private static int mVersion; 
    private static DBInitSql mDbInitSql=null;
    public static void init(DBInitSql dbInitSql){
        mDataBaseDir=dbInitSql.getDataBaseDir();
        mDataBaseName=dbInitSql.getDataBaseName();
        mVersion=dbInitSql.getVersion();
        mDbInitSql=dbInitSql;
    }
    public static SQLiteDatabase open() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(mDataBaseDir
                + mDataBaseName, null);
        if(mDbInitSql!=null){
            synchronized (DBHelper.class) {
                int version = db.getVersion();
                if (version < mVersion) {
                    db.beginTransaction();
                    try {
                        if (version == 0) {
                            onCreate(db);
                        } else {
                            onUpgrade(db);
                        }
                        db.setVersion(mVersion);
                        db.setTransactionSuccessful();
                        mDbInitSql=null;
                    } finally {
                        db.endTransaction();
//                        db.close();
                    }
                }else{
                    if(mDbInitSql!=null){
                        mDbInitSql.getCreateTableSqlList().clear();
                        mDbInitSql.getUpgradeSqlList().clear();
                        mDbInitSql=null;
                    }
                }
            }
        }
        return db;
    }
    private static void onCreate(SQLiteDatabase db) {
        if(mDbInitSql!=null){
            for(String sql:mDbInitSql.getCreateTableSqlList()){
                db.execSQL(sql);
            }
        }
        mDbInitSql.getCreateTableSqlList().clear();
    }
    private static void onUpgrade(SQLiteDatabase db) {
        if(mDbInitSql!=null){
            for(String sql:mDbInitSql.getUpgradeSqlList()){
                db.execSQL(sql);
            }
        }
        mDbInitSql.getUpgradeSqlList().clear();
    }
    
    
    public static void execSQL(String sql, Object[] bindArgs) throws Exception {
        SQLiteDatabase db=null;
        try {
            db=open();
            db.beginTransaction();
            try {
                db.execSQL(sql, bindArgs);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                throw e;
            } finally {
                db.endTransaction();
            }
        } finally {
            if(db!=null){
                db.close();
            }
        }
    }

    public static void execSQL(String sql) throws Exception {
        SQLiteDatabase db=null;
        try {
            db=open();
            db.beginTransaction();
            try {
                db.execSQL(sql);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                throw e;
            } finally {
                db.endTransaction();
            }
        } finally {
            if(db!=null){
                db.close();
            }
        }
    }

    public static Cursor rawQuery(String sql, String[] selectionArgs) throws Exception {
        final SQLiteDatabase db=open();
        try {
            Cursor c=db.rawQuery(sql, selectionArgs);
            c.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                }
                @Override
                public void onInvalidated() {
                    super.onInvalidated();
                    if(db!=null){
                        db.close();
                    }
                   
                }
            });
            return c;
        } catch (Exception e) {
            if(db!=null){
                db.close();
            }
            throw e;
        }
    }

    public static long replaceOrThrow(String table,ContentValues initialValues) throws Exception{
        SQLiteDatabase db=null;
        long l=0;
        try {
            db=open();
            db.beginTransaction();
            try {
                l=db.replaceOrThrow(table, null, initialValues);
                initialValues.clear();
                db.setTransactionSuccessful();
            } catch (Exception e) {
                throw e;
            } finally {
                db.endTransaction();
            }
        } finally {
            if(db!=null){
                db.close();
            }
        }
        return l;
    }
    
    public static long insert(String tableName, ContentValues values) throws Exception {
        SQLiteDatabase db=null;
        long l=0;
        try {
            db=open();
            db.beginTransaction();
            try {
                l=db.insert(tableName, null, values);
                values.clear();
                db.setTransactionSuccessful();
            } catch (Exception e) {
                throw e;
            } finally {
                db.endTransaction();
            }
        } finally {
            if(db!=null){
                db.close();
            }
        }
        return l;
    }
    public static long insert(String tableName, ArrayList<ContentValues> arrayList) throws Exception {
        SQLiteDatabase db=null;
        long l=0;
        try {
            db=open();
            db.beginTransaction();
            try {
                for (ContentValues values : arrayList) {
                    l=db.insert(tableName, null, values);
                    values.clear();
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                throw e;
            } finally {
                arrayList.clear();
                db.endTransaction();
            }
        } finally {
            if(db!=null){
                db.close();
            }
        }
        return l;
    }
//    static long insert(ArrayList<TablesContentValuesObject> tablesContentValuesObjectList) throws Exception {
//        SQLiteDatabase db=null;
//        long l=0;
//        try {
//            db=open();
//            db.beginTransaction();
//            try {
//                for(TablesContentValuesObject tablesContentValuesObject:tablesContentValuesObjectList){
//                    l=db.insert(tablesContentValuesObject.getTableName(), null, tablesContentValuesObject.getValues());
//                    tablesContentValuesObject.getValues().clear();
//                }
//                db.setTransactionSuccessful();
//            } catch (Exception e) {
//                throw e;
//            } finally {
//                db.endTransaction();
//            }
//        } finally {
//            if(db!=null){
//                db.close();
//            }
//        }
//        return l;
//    }
    
    public static int update(String tableName, ContentValues values, String whereClause, String[] whereArgs) throws Exception {
        SQLiteDatabase db=null;
        int n=0;
        try {
            db=open();
            db.beginTransaction();
            try {
                n=db.update(tableName, values, whereClause, whereArgs);
                values.clear();
                db.setTransactionSuccessful();
            } catch (Exception e) {
                throw e;
            } finally {
                db.endTransaction();
            }
        } finally {
            if(db!=null){
                db.close();
            }
        }
        return n;
    }

    public static int delete(String tableName, String whereClause, String[] whereArgs) throws Exception {
        SQLiteDatabase db=null;
        int n=0;
        try {
            db=open();
            db.beginTransaction();
            try {
                n=db.delete(tableName, whereClause, whereArgs);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                throw e;
            } finally {
                db.endTransaction();
            }
        } finally {
            if(db!=null){
                db.close();
            }
        }
        return n;
    }
}
