package com.example.btl.model;

public class LinhKien {
    private int id;
    private String maLinhKien;
    private String tenLinhKien;
    private int soLuongTon;
    private double giaNhap;
    private int loaiId;

    public LinhKien() {}

    public LinhKien(int id, String maLinhKien, String tenLinhKien, int soLuongTon, double giaNhap, int loaiId) {
        this.id = id;
        this.maLinhKien = maLinhKien;
        this.tenLinhKien = tenLinhKien;
        this.soLuongTon = soLuongTon;
        this.giaNhap = giaNhap;
        this.loaiId = loaiId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaLinhKien() { return maLinhKien; }
    public void setMaLinhKien(String maLinhKien) { this.maLinhKien = maLinhKien; }

    public String getTenLinhKien() { return tenLinhKien; }
    public void setTenLinhKien(String tenLinhKien) { this.tenLinhKien = tenLinhKien; }

    public int getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(int soLuongTon) { this.soLuongTon = soLuongTon; }

    public double getGiaNhap() { return giaNhap; }
    public void setGiaNhap(double giaNhap) { this.giaNhap = giaNhap; }

    public int getLoaiId() { return loaiId; }
    public void setLoaiId(int loaiId) { this.loaiId = loaiId; }

    @Override
    public String toString() {
        return maLinhKien + " - " + tenLinhKien;
    }
}