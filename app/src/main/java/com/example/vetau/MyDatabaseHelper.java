package com.example.vetau;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "VeTau_Manager";

    // Table name: VeTau.
    private static final String TABLE_VETAU = "VeTau_2";

    private static final String COLUMN_VETAU_ID ="VeTau_Id";
    private static final String COLUMN_VETAU_GADEN ="VeTau_Ga_Den";
    private static final String COLUMN_VETAU_GADI ="VeTau_Ga_Di";
    private static final String COLUMN_VETAU_GIA ="VeTau_GIA";
    private static final String COLUMN_VETAU_LOAI_VE = "VETAU_Loai_Ve";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script.
        String script = "CREATE TABLE " + TABLE_VETAU + "("
                + COLUMN_VETAU_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_VETAU_GADEN + " TEXT,"
                + COLUMN_VETAU_GADI + " TEXT,"
                + COLUMN_VETAU_GIA + " INTEGER,"
                + COLUMN_VETAU_LOAI_VE + " INTEGER" + ")";
        // Execute Script.
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VETAU);

        // Create tables again
        onCreate(db);
    }
    // If VeTau table has no data
    // default, Insert 2 records.
    // If you wanna insert data when start project , you can insert here.
    public void createDefaultVeTausIfNeed()  {
        int count = this.getVeTauCount();
        if(count ==0 ) {
            Vetau vetau1 = new Vetau("Tuyen Quang",
                    "Ha noi",120000,1);
            Vetau vetau2 = new Vetau("Tuyen Quang",
                    "Quang Ninh",100000,2);
            this.addVetau(vetau1);
            this.addVetau(vetau2);
        }
    }

    public void addVetau(Vetau vetau) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_VETAU_GADEN, vetau.getGaDen());
        values.put(COLUMN_VETAU_GADI, vetau.getGaDi());
        values.put(COLUMN_VETAU_GIA, vetau.getDonGia());
        values.put(COLUMN_VETAU_GADI, vetau.getGaDi());
        values.put(COLUMN_VETAU_LOAI_VE,vetau.getLoaiVe());

        // Inserting Row
        db.insert(TABLE_VETAU, null, values);

        // Closing database connection
        db.close();
    }
    public Vetau getVeTau(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VETAU, new String[] {COLUMN_VETAU_ID,
                        COLUMN_VETAU_GADEN,
                        COLUMN_VETAU_GADI,
                        COLUMN_VETAU_GIA,
                        COLUMN_VETAU_LOAI_VE},
                COLUMN_VETAU_ID + "=?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null,
                null);
        if (cursor != null)
            cursor.moveToFirst();

        Vetau vetau = new Vetau(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)));
        // return VeTau
        return vetau;
    }

    public List<Vetau> getAllVe() {

        List<Vetau> arrVeTau = new ArrayList<Vetau>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VETAU;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Vetau vetau = new Vetau();
                vetau.setIdVe(Integer.parseInt(cursor.getString(0)));
                vetau.setGaDi(cursor.getString(1));
                vetau.setGaDen(cursor.getString(2));
                vetau.setDonGia(Integer.parseInt(cursor.getString(3)));
                vetau.setLoaiVe(Integer.parseInt(cursor.getString(4)));
                // Adding VeTau to list
                arrVeTau.add(vetau);
            } while (cursor.moveToNext());
        }

        // return VeTau list
        return arrVeTau;
    }
    public int getVeTauCount() {

        String countQuery = "SELECT  * FROM " + TABLE_VETAU;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    public int updateVeTau(Vetau vetau) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_VETAU_GADEN, vetau.getGaDen());
        values.put(COLUMN_VETAU_GADI, vetau.getGaDi());
        values.put(COLUMN_VETAU_GIA, vetau.getDonGia());
        values.put(COLUMN_VETAU_LOAI_VE, vetau.getLoaiVe());

        // updating row
        return db.update(TABLE_VETAU, values, COLUMN_VETAU_ID + " = ?",
                new String[]{String.valueOf(vetau.getIdVe())});
    }

    public void deleteVeTau(Vetau vetau) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VETAU, COLUMN_VETAU_ID + " = ?",
                new String[] { String.valueOf(vetau.getIdVe()) });
        db.close();
    }

}
