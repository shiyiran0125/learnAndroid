package com.wtcl.learn01.util;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * @author WTCL
 */
public class MyContentProvider extends ContentProvider {
    private DatabaseHelper databaseHelper;
    private static final int USERS = 1;
    private static final int USER = 2;
    private static final String USER_TABLE = "user";
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    public MyContentProvider() {
    }

    static {
        //为UriMatcher注册两个uri
        matcher.addURI(Users.AUTHORITY,"users",USERS);
        matcher.addURI(Users.AUTHORITY,"user/#",USER);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(this.getContext(),"learn01",1);
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)){
            case USERS: return "vnd.android.cursor.dir/com.wtcl.user";
            case USER: return "vnd.android.cursor.item/com.wtcl.user";
            default:throw new IllegalArgumentException("未知uri");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db =  db = databaseHelper.getReadableDatabase();
        switch (matcher.match(uri)){
            case USERS:
                long rowId = db.insert(USER_TABLE, Users.User._ID,values);
                if (rowId>0){
                    Uri newUri = ContentUris.withAppendedId(uri,rowId);
                    return newUri;
                }
                break;
            default:throw new IllegalArgumentException("未知uri");
        }
       return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db =  db = databaseHelper.getReadableDatabase();
        int num =0;
        switch (matcher.match(uri)){
            case USERS:
                num = db.delete(USER_TABLE,selection,selectionArgs);
                break;
            case USER:
                long id = ContentUris.parseId(uri);
                String where = Users.User._ID + "=" + id;
                if (selection != null && !"".equals(selection)){
                    selection = selection + " and " + where;
                }
                num = db.delete(USER_TABLE,selection,selectionArgs);
                break;
            default:throw new IllegalArgumentException("未知uri");
        }
        return num;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        switch (matcher.match(uri)){
            case USERS:
                return db.query(USER_TABLE,projection,selection,selectionArgs,null,null,sortOrder);
            case USER:
                long id = ContentUris.parseId(uri);
                String where = Users.User._ID + "=" + id;
                if (selection != null && !"".equals(selection)){
                    selection = selection + " and " + where;
                }
                return db.query(USER_TABLE,projection,selection,selectionArgs,null,null,sortOrder);
            default:throw new IllegalArgumentException("未知uri");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db =  db = databaseHelper.getReadableDatabase();
        int num =0;
        switch (matcher.match(uri)){
            case USERS:
                num = db.update(USER_TABLE,values,selection,selectionArgs);
                break;
            case USER:
                long id = ContentUris.parseId(uri);
                String where = Users.User._ID + "=" + id;
                if (selection != null && !"".equals(selection)){
                    selection = selection + " and " + where;
                }
                num = db.update(USER_TABLE,values,selection,selectionArgs);
                break;
            default:throw new IllegalArgumentException("未知uri");
        }
        return num;
    }
}