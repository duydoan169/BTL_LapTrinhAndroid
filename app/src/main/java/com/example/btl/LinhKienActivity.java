package com.example.btl;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.adapter.LinhKienAdapter;
import com.example.btl.dao.LinhKienDAO;
import com.example.btl.dao.LoaiLinhKienDAO;
import com.example.btl.model.LinhKien;
import com.example.btl.model.LoaiLinhKien;

import java.util.List;

public class LinhKienActivity extends AppCompatActivity {
    EditText edtMaLinhKien, edtTenLinhKien, edtSoLuong, edtGiaNhap;
    Spinner spinnerLoai;
    Button btnThem;
    ListView listViewLinhKien;

    LinhKienDAO dao;
    LoaiLinhKienDAO loaiDAO;
    List<LinhKien> danhSach;
    List<LoaiLinhKien> danhSachLoai;
    LinhKienAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linh_kien);

        edtMaLinhKien = findViewById(R.id.edtMaLinhKien);
        edtTenLinhKien = findViewById(R.id.edtTenLinhKien);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        edtGiaNhap = findViewById(R.id.edtGiaNhap);
        spinnerLoai = findViewById(R.id.spinnerLoai);
        btnThem = findViewById(R.id.btnThem);
        listViewLinhKien = findViewById(R.id.listViewLinhKien);

        dao = new LinhKienDAO(this);
        loaiDAO = new LoaiLinhKienDAO(this);

        loadSpinner();
        loadDanhSach();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = edtMaLinhKien.getText().toString().trim();
                String ten = edtTenLinhKien.getText().toString().trim();
                String soLuongStr = edtSoLuong.getText().toString().trim();
                String giaNhapStr = edtGiaNhap.getText().toString().trim();

                if (ma.isEmpty() || ten.isEmpty() || soLuongStr.isEmpty() || giaNhapStr.isEmpty()) {
                    Toast.makeText(LinhKienActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoaiLinhKien loaiChon = (LoaiLinhKien) spinnerLoai.getSelectedItem();
                if (loaiChon == null) {
                    Toast.makeText(LinhKienActivity.this, "Vui lòng thêm loại linh kiện trước", Toast.LENGTH_SHORT).show();
                    return;
                }

                LinhKien lk = new LinhKien(0, ma, ten, Integer.parseInt(soLuongStr), Double.parseDouble(giaNhapStr), loaiChon.getId());
                dao.insert(lk);
                Toast.makeText(LinhKienActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                edtMaLinhKien.setText("");
                edtTenLinhKien.setText("");
                edtSoLuong.setText("");
                edtGiaNhap.setText("");
                loadDanhSach();
            }
        });
    }

    void loadSpinner() {
        danhSachLoai = loaiDAO.getAll();
        ArrayAdapter<LoaiLinhKien> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, danhSachLoai);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoai.setAdapter(spinnerAdapter);
    }

    void loadDanhSach() {
        danhSach = dao.getAll();
        adapter = new LinhKienAdapter(this, danhSach);
        listViewLinhKien.setAdapter(adapter);
    }
}