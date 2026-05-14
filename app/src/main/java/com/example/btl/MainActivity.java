package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnLoaiLinhKien, btnLinhKien, btnNhapXuat, btnBaoCao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLoaiLinhKien = findViewById(R.id.btnLoaiLinhKien);
        btnLinhKien = findViewById(R.id.btnLinhKien);
        btnNhapXuat = findViewById(R.id.btnNhapXuat);
        btnBaoCao = findViewById(R.id.btnBaoCao);

        btnLoaiLinhKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoaiLinhKienActivity.class));
            }
        });

        btnLinhKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LinhKienActivity.class));
            }
        });

        btnNhapXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NhapXuatActivity.class));
            }
        });

        btnBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BaoCaoActivity.class));
            }
        });
    }
}