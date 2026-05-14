package com.example.btl.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl.database.DatabaseHelper;
import com.example.btl.model.LinhKien;

import java.util.ArrayList;
import java.util.List;

public class LinhKienDAO {
    private SQLiteDatabase db;

    public LinhKienDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public long insert(LinhKien lk) {
        ContentValues values = new ContentValues();
        values.put("maLinhKien", lk.getMaLinhKien());
        values.put("tenLinhKien", lk.getTenLinhKien());
        values.put("soLuongTon", lk.getSoLuongTon());
        values.put("giaNhap", lk.getGiaNhap());
        values.put("loaiId", lk.getLoaiId());
        return db.insert("LinhKien", null, values);
    }

    public int update(LinhKien lk) {
        ContentValues values = new ContentValues();
        values.put("maLinhKien", lk.getMaLinhKien());
        values.put("tenLinhKien", lk.getTenLinhKien());
        values.put("soLuongTon", lk.getSoLuongTon());
        values.put("giaNhap", lk.getGiaNhap());
        values.put("loaiId", lk.getLoaiId());
        return db.update("LinhKien", values, "id = ?", new String[]{String.valueOf(lk.getId())});
    }

    public int delete(int id) {
        return db.delete("LinhKien", "id = ?", new String[]{String.valueOf(id)});
    }

    public List<LinhKien> getAll() {
        List<LinhKien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM LinhKien", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new LinhKien(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getDouble(4),
                        cursor.getInt(5)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<LinhKien> getByLoai(int loaiId) {
        List<LinhKien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM LinhKien WHERE loaiId = ?", new String[]{String.valueOf(loaiId)});
        if (cursor.moveToFirst()) {
            do {
                list.add(new LinhKien(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getDouble(4),
                        cursor.getInt(5)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<LinhKien> getTonKhoDuoi10() {
        List<LinhKien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM LinhKien WHERE soLuongTon < 10", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new LinhKien(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getDouble(4),
                        cursor.getInt(5)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void nhapKho(int linhKienId, int soLuong) {
        db.execSQL("UPDATE LinhKien SET soLuongTon = soLuongTon + " + soLuong + " WHERE id = " + linhKienId);
        ContentValues values = new ContentValues();
        values.put("linhKienId", linhKienId);
        values.put("loai", "NHAP");
        values.put("soLuong", soLuong);
        values.put("ngay", android.os.SystemClock.currentThreadTimeMillis() + "");
        db.insert("NhapXuatKho", null, values);
    }

    public void xuatKho(int linhKienId, int soLuong) {
        db.execSQL("UPDATE LinhKien SET soLuongTon = soLuongTon - " + soLuong + " WHERE id = " + linhKienId);
        ContentValues values = new ContentValues();
        values.put("linhKienId", linhKienId);
        values.put("loai", "XUAT");
        values.put("soLuong", soLuong);
        values.put("ngay", android.os.SystemClock.currentThreadTimeMillis() + "");
        db.insert("NhapXuatKho", null, values);
    }
}