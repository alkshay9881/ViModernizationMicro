package com.mobicule.authserver.model;

public class UserDetails {

    private String id;

    private String mobNo;   // username

    private String loginPassword;

    public UserDetails() {
    }

    public UserDetails(String id, String mobNo, String loginPassword) {
        this.id = id;
        this.mobNo = mobNo;
        this.loginPassword = loginPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id='" + id + '\'' +
                ", mobNo='" + mobNo + '\'' +
                '}';
    }
}