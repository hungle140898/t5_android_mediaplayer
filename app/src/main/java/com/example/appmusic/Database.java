package com.example.appmusic;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public Database(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //Truy van k ket qua
    public void QueryData(String sql)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor getData(String sql)
    {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    // Truy van tra kqua

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
