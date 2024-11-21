
package com.example.webf1movil1704;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReservaSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reservas.db";
    private static final int DATABASE_VERSION = 1;

    public ReservaSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE reservas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombres TEXT, " +
                "apellidos TEXT, " +
                "numero TEXT, " +
                "correo TEXT, " +
                "fecha TEXT, " +
                "hora TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS reservas");
        onCreate(db);
    }
}
