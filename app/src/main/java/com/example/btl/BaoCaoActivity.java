package com.example.btl;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.dao.LinhKienDAO;
import com.example.btl.dao.LoaiLinhKienDAO;
import com.example.btl.model.LinhKien;
import com.example.btl.model.LoaiLinhKien;

import java.util.List;

public class BaoCaoActivity extends AppCompatActivity {
    Spinner spinnerLoaiFilter;
    Button btnLocTheoLoai, btnDuoi10;
    ListView listViewBaoCao;

    LinhKienDAO linhKienDAO;
    LoaiLinhKienDAO loaiDAO;
    List<LoaiLinhKien> danhSachLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao);

        spinnerLoaiFilter = findViewById(R.id.spinnerLoaiFilter);
        btnLocTheoLoai = findViewById(R.id.btnLocTheoLoai);
        btnDuoi10 = findViewById(R.id.btnDuoi10);
        listViewBaoCao = findViewById(R.id.listViewBaoCao);

        linhKienDAO = new LinhKienDAO(this);
        loaiDAO = new LoaiLinhKienDAO(this);

        loadSpinner();

        btnLocTheoLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiLinhKien loaiChon = (LoaiLinhKien) spinnerLoaiFilter.getSelectedItem();
                if (loaiChon == null) {
                    Toast.makeText(BaoCaoActivity.this, "Chưa có loại linh kiện nào", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<LinhKien> list = linhKienDAO.getByLoai(loaiChon.getId());
                ArrayAdapter<LinhKien> adapter = new ArrayAdapter<>(BaoCaoActivity.this,
                        android.R.layout.simple_list_item_1, list);
                listViewBaoCao.setAdapter(adapter);
            }
        });

        btnDuoi10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<LinhKien> list = linhKienDAO.getTonKhoDuoi10();
                ArrayAdapter<LinhKien> adapter = new ArrayAdapter<>(BaoCaoActivity.this,
                        android.R.layout.simple_list_item_1, list);
                listViewBaoCao.setAdapter(adapter);
            }
        });
    }

    void loadSpinner() {
        danhSachLoai = loaiDAO.getAll();
        ArrayAdapter<LoaiLinhKien> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, danhSachLoai);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoaiFilter.setAdapter(adapter);
    }
}