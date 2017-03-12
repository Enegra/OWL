package com.app.agnie.owl.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by agnie on 3/12/2017.
 */

public class DictionaryDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    public DictionaryDataSource(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public void createWord(String wordPicture){
        //// TODO: 3/12/2017
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PICTURE, wordPicture);
        long insertID = database.insert(DatabaseHelper.TABLE_WORD, null, values);
//        Cursor cursor = database.query(DatabaseHelper.TABLE_WORD, allColumns, )

    }

}
