package com.example.btl.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.dao.LinhKienDAO;
import com.example.btl.dao.LoaiLinhKienDAO;
import com.example.btl.model.LinhKien;
import com.example.btl.model.LoaiLinhKien;

import java.util.List;

public class LinhKienAdapter extends BaseAdapter {
    Activity activity;
    List<LinhKien> list;
    LinhKienDAO dao;
    List<LoaiLinhKien> danhSachLoai;

    public LinhKienAdapter(Activity activity, List<LinhKien> list) {
        this.activity = activity;
        this.list = list;
        this.dao = new LinhKienDAO(activity);
        this.danhSachLoai = new LoaiLinhKienDAO(activity).getAll();
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int position) { return list.get(position); }

    @Override
    public long getItemId(int position) { return list.get(position).getId(); }

    static class ViewHolder {
        TextView txtTenLinhKien, txtSoLuongGia;
        Button btnEdit, btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(activity)
                    .inflate(R.layout.item_linh_kien, parent, false);
            holder = new ViewHolder();
            holder.txtTenLinhKien = convertView.findViewById(R.id.txtTenLinhKien);
            holder.txtSoLuongGia = convertView.findViewById(R.id.txtSoLuongGia);
            holder.btnEdit = convertView.findViewById(R.id.btnEdit);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LinhKien lk = list.get(position);
        holder.txtTenLinhKien.setText(lk.getMaLinhKien() + " - " + lk.getTenLinhKien());
        holder.txtSoLuongGia.setText("Tồn: " + lk.getSoLuongTon() + " | Giá: " + lk.getGiaNhap());

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(activity)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc muốn xóa linh kiện này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        dao.delete(lk.getId());
                        list.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(activity, "Đã xóa", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

        holder.btnEdit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Sửa linh kiện");

            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.dialog_linh_kien, null);

            EditText edtMaLinhKien = view.findViewById(R.id.edtMaLinhKien);
            EditText edtTenLinhKien = view.findViewById(R.id.edtTenLinhKien);
            EditText edtSoLuong = view.findViewById(R.id.edtSoLuong);
            EditText edtGiaNhap = view.findViewById(R.id.edtGiaNhap);
            Spinner spinnerLoai = view.findViewById(R.id.spinnerLoai);

            edtMaLinhKien.setText(lk.getMaLinhKien());
            edtTenLinhKien.setText(lk.getTenLinhKien());
            edtSoLuong.setText(String.valueOf(lk.getSoLuongTon()));
            edtGiaNhap.setText(String.valueOf(lk.getGiaNhap()));

            ArrayAdapter<LoaiLinhKien> spinnerAdapter = new ArrayAdapter<>(activity,
                    android.R.layout.simple_spinner_item, danhSachLoai);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLoai.setAdapter(spinnerAdapter);

            for (int i = 0; i < danhSachLoai.size(); i++) {
                if (danhSachLoai.get(i).getId() == lk.getLoaiId()) {
                    spinnerLoai.setSelection(i);
                    break;
                }
            }

            builder.setView(view);
            builder.setPositiveButton("Lưu", null);
            builder.setNegativeButton("Hủy", null);

            AlertDialog dialog = builder.create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v2 -> {
                String ma = edtMaLinhKien.getText().toString().trim();
                String ten = edtTenLinhKien.getText().toString().trim();
                String soLuongStr = edtSoLuong.getText().toString().trim();
                String giaNhapStr = edtGiaNhap.getText().toString().trim();

                if (ma.isEmpty()) { edtMaLinhKien.setError("Không được để trống"); return; }
                if (ten.isEmpty()) { edtTenLinhKien.setError("Không được để trống"); return; }
                if (soLuongStr.isEmpty()) { edtSoLuong.setError("Không được để trống"); return; }
                if (giaNhapStr.isEmpty()) { edtGiaNhap.setError("Không được để trống"); return; }

                LoaiLinhKien loaiChon = (LoaiLinhKien) spinnerLoai.getSelectedItem();

                lk.setMaLinhKien(ma);
                lk.setTenLinhKien(ten);
                lk.setSoLuongTon(Integer.parseInt(soLuongStr));
                lk.setGiaNhap(Double.parseDouble(giaNhapStr));
                lk.setLoaiId(loaiChon.getId());

                dao.update(lk);
                notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(activity, "Đã cập nhật", Toast.LENGTH_SHORT).show();
            });
        });

        return convertView;
    }
}