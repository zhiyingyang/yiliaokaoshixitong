package com.jsmosce.data;

/**
 * Created by lx on 2017/6/15.
 */

public class DataSubmitRegister {

    /**
     * Pack : Login
     * Interface : userAdd
     * phone : 18734740537
     * hospitalId : 1
     * departmentId : 2
     * code : 776593
     * username : dddd
     * role : 院长
     */

    private String Pack;
    private String Interface;
    private String phone;
    private int hospitalId;
    private int departmentId;
    private String code;
    private String username;
    private String role;

    public String getPack() {
        return Pack;
    }

    public void setPack(String Pack) {
        this.Pack = Pack;
    }

    public String getInterface() {
        return Interface;
    }

    public void setInterface(String Interface) {
        this.Interface = Interface;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
