package com.example.xdreamer.barangku_v2;

public class UserProfile {
    public String userNama;
    public String userEmail;
    public String userSlogan;

    public UserProfile() {
    }


    public UserProfile(String userNama, String userEmail, String userSlogan) {
        this.userNama = userNama;
        this.userEmail = userEmail;
        this.userSlogan = userSlogan;

    }

    public String getUserNama() {
        return userNama;
    }

    public void setUserNama(String userNama) {
        this.userNama = userNama;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserSlogan() {
        return userSlogan;
    }

    public void setUserSlogan(String userSlogan) {
        this.userSlogan = userSlogan;
    }

}
