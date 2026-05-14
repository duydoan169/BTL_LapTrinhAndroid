package com.example.btl.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.btl.database.DatabaseHelper;
import com.example.btl.model.LoaiLinhKien;

import java.util.ArrayList;
import java.util.List;

public class LoaiLinhKienDAO {
    private SQLiteDatabase db;

    public LoaiLinhKienDAO(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public long insert(LoaiLinhKien loai) {
        ContentValues values = new ContentValues();
        values.put("maLoai", loai.getMaLoai());
        values.put("tenLoai", loai.getTenLoai());
        return db.insert("LoaiLinhKien", null, values);
    }

    public int update(LoaiLinhKien loai) {
        ContentValues values = new ContentValues();
        values.put("maLoai", loai.getMaLoai());
        values.put("tenLoai", loai.getTenLoai());
        return db.update("LoaiLinhKien", values, "id = ?", new String[]{String.valueOf(loai.getId())});
    }

    public int delete(int id) {
        return db.delete("LoaiLinhKien", "id = ?", new String[]{String.valueOf(id)});
    }

    public List<LoaiLinhKien> getAll() {
        List<LoaiLinhKien> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM LoaiLinhKien", null);
        if (cursor.moveToFirst()) {
            do {
                LoaiLinhKien loai = new LoaiLinhKien(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
                list.add(loai);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}