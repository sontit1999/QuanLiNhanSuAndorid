package com.example.quanlinhansu.model;

import java.io.Serializable;

public class NhanVien implements Serializable {
    String manv;
    String tennv;
    String diachi;
    String maPB;
    String maCV;
    String soCMT;
    String SDT;
    String gioitinh;
    String ngaysinh;
    String email;
    String ngayvaolam;
    String trinhdo;
    String hsLuong;
    public NhanVien() {
    }

    public NhanVien(String manv, String tennv, String diachi, String maPB, String maCV, String soCMT, String SDT, String gioitinh, String ngaysinh, String email, String ngayvaolam, String trinhdo, String hsLuong) {
        this.manv = manv;
        this.tennv = tennv;
        this.diachi = diachi;
        this.maPB = maPB;
        this.maCV = maCV;
        this.soCMT = soCMT;
        this.SDT = SDT;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.email = email;
        this.ngayvaolam = ngayvaolam;
        this.trinhdo = trinhdo;
        this.hsLuong = hsLuong;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMaPB() {
        return maPB;
    }

    public void setMaPB(String maPB) {
        this.maPB = maPB;
    }

    public String getMaCV() {
        return maCV;
    }

    public void setMaCV(String maCV) {
        this.maCV = maCV;
    }

    public String getSoCMT() {
        return soCMT;
    }

    public void setSoCMT(String soCMT) {
        this.soCMT = soCMT;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgayvaolam() {
        return ngayvaolam;
    }

    public void setNgayvaolam(String ngayvaolam) {
        this.ngayvaolam = ngayvaolam;
    }

    public String getTrinhdo() {
        return trinhdo;
    }

    public void setTrinhdo(String trinhdo) {
        this.trinhdo = trinhdo;
    }

    public String getHsLuong() {
        return hsLuong;
    }

    public void setHsLuong(String hsLuong) {
        this.hsLuong = hsLuong;
    }
}
