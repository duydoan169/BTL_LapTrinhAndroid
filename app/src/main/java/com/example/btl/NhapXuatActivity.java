package com.example.btl;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.dao.LinhKienDAO;
import com.example.btl.model.LinhKien;

import java.util.List;

public class NhapXuatActivity extends AppCompatActivity {
    Spinner spinnerLinhKien;
    EditText edtSoLuong;
    Button btnNhap, btnXuat;

    LinhKienDAO dao;
    List<LinhKien> danhSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_xuat);

        spinnerLinhKien = findViewById(R.id.spinnerLinhKien);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        btnNhap = findViewById(R.id.btnNhap);
        btnXuat = findViewById(R.id.btnXuat);

        dao = new LinhKienDAO(this);
        loadSpinner();

        btnNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String soLuongStr = edtSoLuong.getText().toString().trim();
                if (soLuongStr.isEmpty()) {
                    Toast.makeText(NhapXuatActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    return;
                }
                LinhKien lk = (LinhKien) spinnerLinhKien.getSelectedItem();
                if (lk == null) {
                    Toast.makeText(NhapXuatActivity.this, "Chưa có linh kiện nào", Toast.LENGTH_SHORT).show();
                    return;
                }
                dao.nhapKho(lk.getId(), Integer.parseInt(soLuongStr));
                Toast.makeText(NhapXuatActivity.this, "Nhập kho thành công", Toast.LENGTH_SHORT).show();
                edtSoLuong.setText("");
                loadSpinner();
            }
        });

        btnXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String soLuongStr = edtSoLuong.getText().toString().trim();
                if (soLuongStr.isEmpty()) {
                    Toast.makeText(NhapXuatActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    return;
                }
                LinhKien lk = (LinhKien) spinnerLinhKien.getSelectedItem();
                if (lk == null) {
                    Toast.makeText(NhapXuatActivity.this, "Chưa có linh kiện nào", Toast.LENGTH_SHORT).show();
                    return;
                }
                int soLuong = Integer.parseInt(soLuongStr);
                if (soLuong > lk.getSoLuongTon()) {
                    Toast.makeText(NhapXuatActivity.this, "Số lượng xuất vượt quá tồn kho!", Toast.LENGTH_SHORT).show();
                    return;
                }
                dao.xuatKho(lk.getId(), soLuong);
                Toast.makeText(NhapXuatActivity.this, "Xuất kho thành công", Toast.LENGTH_SHORT).show();
                edtSoLuong.setText("");
                loadSpinner();
            }
        });
    }

    void loadSpinner() {
        danhSach = dao.getAll();
        ArrayAdapter<LinhKien> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, danhSach);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLinhKien.setAdapter(adapter);
    }
}