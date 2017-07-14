package com.jsmosce.data;

import java.util.List;

/**
 * Created by lx on 2017/6/15.
 */

public class DataHospital {


    /**
     * state : 1
     * hospitalInfo : [{"Id":"1","name":"测试医院"}]
     */

    private int state;
    private List<HospitalInfoBean> hospitalInfo;
    private List<HospitalInfoBean> departmentInfo;
    private String Message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<HospitalInfoBean> getHospitalInfo() {
        return hospitalInfo;
    }

    public void setHospitalInfo(List<HospitalInfoBean> hospitalInfo) {
        this.hospitalInfo = hospitalInfo;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<HospitalInfoBean> getDepartmentInfo() {
        return departmentInfo;
    }

    public void setDepartmentInfo(List<HospitalInfoBean> departmentInfo) {
        this.departmentInfo = departmentInfo;
    }

    public static class HospitalInfoBean {
        /**
         * Id : 1
         * name : 测试医院
         */

        private int Id;
        private String name;
        private boolean isSelectd;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelectd() {
            return isSelectd;
        }

        public void setSelectd(boolean selectd) {
            isSelectd = selectd;
        }
    }


}
