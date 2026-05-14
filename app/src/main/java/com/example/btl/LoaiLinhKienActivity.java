package com.example.btl;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.adapter.LoaiLinhKienAdapter;
import com.example.btl.dao.LoaiLinhKienDAO;
import com.example.btl.model.LoaiLinhKien;

import java.util.List;

public class LoaiLinhKienActivity extends AppCompatActivity {
    EditText edtMaLoai, edtTenLoai;
    Button btnThem;
    ListView listViewLoai;

    LoaiLinhKienDAO dao;
    List<LoaiLinhKien> danhSach;
    LoaiLinhKienAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_linh_kien);

        edtMaLoai = findViewById(R.id.edtMaLoai);
        edtTenLoai = findViewById(R.id.edtTenLoai);
        btnThem = findViewById(R.id.btnThem);
        listViewLoai = findViewById(R.id.listViewLoai);

        dao = new LoaiLinhKienDAO(this);
        loadDanhSach();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = edtMaLoai.getText().toString().trim();
                String ten = edtTenLoai.getText().toString().trim();
                if (ma.isEmpty() || ten.isEmpty()) {
                    Toast.makeText(LoaiLinhKienActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                dao.insert(new LoaiLinhKien(0, ma, ten));
                Toast.makeText(LoaiLinhKienActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                edtMaLoai.setText("");
                edtTenLoai.setText("");
                loadDanhSach();
            }
        });
    }

    void loadDanhSach() {
        danhSach = dao.getAll();
        adapter = new LoaiLinhKienAdapter(this, danhSach);
        listViewLoai.setAdapter(adapter);
    }
}