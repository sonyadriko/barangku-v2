package com.example.xdreamer.barangku_v2;

public class DataKategori {
    public String ml;
    public String toram;
    public String pubg;
    public String hayday;

    public DataKategori() {
    }

    public DataKategori(String ml, String toram, String pubg, String hayday) {
        this.ml = ml;
        this.toram = toram;
        this.pubg = pubg;
        this.hayday = hayday;
    }

    public String getMl() {
        return ml;
    }

    public void setMl(String ml) {
        this.ml = ml;
    }

    public String getToram() {
        return toram;
    }

    public void setToram(String toram) {
        this.toram = toram;
    }

    public String getPubg() {
        return pubg;
    }

    public void setPubg(String pubg) {
        this.pubg = pubg;
    }

    public String getHayday() {
        return hayday;
    }

    public void setHayday(String hayday) {
        this.hayday = hayday;
    }
}
