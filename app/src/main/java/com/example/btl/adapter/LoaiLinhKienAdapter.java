package com.example.btl.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.dao.LoaiLinhKienDAO;
import com.example.btl.model.LoaiLinhKien;

import java.util.List;

public class LoaiLinhKienAdapter extends BaseAdapter {
    Activity activity;
    List<LoaiLinhKien> list;
    LoaiLinhKienDAO dao;

    public LoaiLinhKienAdapter(Activity activity, List<LoaiLinhKien> list) {
        this.activity = activity;
        this.list = list;
        this.dao = new LoaiLinhKienDAO(activity);
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int position) { return list.get(position); }

    @Override
    public long getItemId(int position) { return list.get(position).getId(); }

    static class ViewHolder {
        TextView txtTenLoai;
        Button btnEdit, btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(activity)
                    .inflate(R.layout.item_loai_linh_kien, parent, false);
            holder = new ViewHolder();
            holder.txtTenLoai = convertView.findViewById(R.id.txtTenLoai);
            holder.btnEdit = convertView.findViewById(R.id.btnEdit);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LoaiLinhKien loai = list.get(position);
        holder.txtTenLoai.setText(loai.getMaLoai() + " - " + loai.getTenLoai());

        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(activity)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc muốn xóa loại này?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        dao.delete(loai.getId());
                        list.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(activity, "Đã xóa", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

        holder.btnEdit.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Sửa loại linh kiện");

            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.dialog_loai_linh_kien, null);

            EditText edtMaLoai = view.findViewById(R.id.edtMaLoai);
            EditText edtTenLoai = view.findViewById(R.id.edtTenLoai);

            edtMaLoai.setText(loai.getMaLoai());
            edtTenLoai.setText(loai.getTenLoai());

            builder.setView(view);
            builder.setPositiveButton("Lưu", null);
            builder.setNegativeButton("Hủy", null);

            AlertDialog dialog = builder.create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v2 -> {
                String ma = edtMaLoai.getText().toString().trim();
                String ten = edtTenLoai.getText().toString().trim();

                if (ma.isEmpty()) {
                    edtMaLoai.setError("Không được để trống");
                    return;
                }
                if (ten.isEmpty()) {
                    edtTenLoai.setError("Không được để trống");
                    return;
                }

                loai.setMaLoai(ma);
                loai.setTenLoai(ten);
                dao.update(loai);
                notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(activity, "Đã cập nhật", Toast.LENGTH_SHORT).show();
            });
        });

        return convertView;
    }
}