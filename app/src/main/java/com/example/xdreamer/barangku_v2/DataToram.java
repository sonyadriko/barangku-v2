package com.example.xdreamer.barangku_v2;

public class DataToram {
    public String judul;
    public String harga;
    public String stok;
    public String deskripsi;

    public DataToram() {
    }

    public DataToram(String judul, String harga, String stok, String deskripsi) {
        this.judul = judul;
        this.harga = harga;
        this.stok = stok;
        this.deskripsi = deskripsi;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
