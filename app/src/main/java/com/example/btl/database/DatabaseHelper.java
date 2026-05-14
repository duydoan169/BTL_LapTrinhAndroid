package com.example.btl.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "kho_linh_kien.db";
    public static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createLoaiLinhKien = "CREATE TABLE LoaiLinhKien (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maLoai TEXT, " +
                "tenLoai TEXT" +
                ")";
        db.execSQL(createLoaiLinhKien);

        String createLinhKien = "CREATE TABLE LinhKien (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "maLinhKien TEXT, " +
                "tenLinhKien TEXT, " +
                "soLuongTon INTEGER, " +
                "giaNhap REAL, " +
                "loaiId INTEGER" +
                ")";
        db.execSQL(createLinhKien);

        String createNhapXuat = "CREATE TABLE NhapXuatKho (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "linhKienId INTEGER, " +
                "loai TEXT, " +
                "soLuong INTEGER, " +
                "ngay TEXT" +
                ")";
        db.execSQL(createNhapXuat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NhapXuatKho");
        db.execSQL("DROP TABLE IF EXISTS LinhKien");
        db.execSQL("DROP TABLE IF EXISTS LoaiLinhKien");
        onCreate(db);
    }
}