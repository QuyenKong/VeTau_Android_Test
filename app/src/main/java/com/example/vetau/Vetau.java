package com.example.vetau;

import java.io.Serializable;

public class Vetau implements Serializable {
    int idVe;
    String gaDi;
    String gaDen;
    int donGia;
    int loaiVe;

    public int getLoaiVe() {
        return loaiVe;
    }

    public void setLoaiVe(int loaiVe) {
        this.loaiVe = loaiVe;
    }

    public Vetau() {
    }

    public Vetau(String gaDi, String gaDen, int donGia, int loaiVe) {
        this.gaDi = gaDi;
        this.gaDen = gaDen;
        this.donGia = donGia;
        this.loaiVe = loaiVe;
    }

    public Vetau(int idVe, String gaDi, String gaDen, int donGia, int loaiVe) {
        this.idVe = idVe;
        this.gaDi = gaDi;
        this.gaDen = gaDen;
        this.donGia = donGia;
        this.loaiVe = loaiVe;
    }

    public int getIdVe() {
        return idVe;
    }

    public void setIdVe(int idVe) {
        this.idVe = idVe;
    }

    public String getGaDi() {
        return gaDi;
    }

    public void setGaDi(String gaDi) {
        this.gaDi = gaDi;
    }

    public String getGaDen() {
        return gaDen;
    }

    public void setGaDen(String gaDen) {
        this.gaDen = gaDen;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }


}
