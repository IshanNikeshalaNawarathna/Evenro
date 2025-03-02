package lk.evenro.even.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Wishlist extends SQLiteOpenHelper {

    public Wishlist(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE wishlist (\n" +
                "    eventID            TEXT PRIMARY KEY,\n" +
                "    eventName          TEXT NOT NULL,\n" +
                "    eventLoaction      TEXT NOT NULL,\n" +
                "    eventPrice         TEXT NOT NULL,\n" +
                "    eventDate          TEXT NOT NULL,\n" +
                "    eventImageUri      TEXT NOT NULL\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}