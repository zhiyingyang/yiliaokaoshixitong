package com.jsmosce.data;

/**
 * Created by lx on 2017/5/18.
 */

public class DataUser {


    /**
     * Info : {"id":"1","username":"闫瑞","phone":"15535730438","password":"d0970714757783e6cf17b26fb8e2298f","work_unitId":"1","departmentId":"1","roleId":"1","state":"1","info":"还行","power":null,"registerTime":"0000-00-00 00:00:00","lastLoginTime":"2017-05-23 10:24:09","loginOutTime":"0000-00-00 00:00:00","age":"24","beiyong2":null,"work_unitName":"测试医院1","departmentName":"口腔科"}
     * Message : 登录成功
     * State : 1
     */

    private InfoBean Info;
    private String Message;
    private int State;

    public InfoBean getInfo() {
        return Info;
    }

    public void setInfo(InfoBean Info) {
        this.Info = Info;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public static class InfoBean {
        /**
         * id : 1
         * username : 闫瑞
         * phone : 15535730438
         * password : d0970714757783e6cf17b26fb8e2298f
         * work_unitId : 1
         * departmentId : 1
         * roleId : 1
         * state : 1
         * info : 还行
         * power : null
         * registerTime : 0000-00-00 00:00:00
         * lastLoginTime : 2017-05-23 10:24:09
         * loginOutTime : 0000-00-00 00:00:00
         * age : 24
         * beiyong2 : null
         * work_unitName : 测试医院1
         * departmentName : 口腔科
         */

        private String id;
        private String username;
        private String phone;
        private String password;
        private int work_unitId;
        private int departmentId;
        private int roleId;
        private String state;
        private String info;
        private Object power;
        private String registerTime;
        private String lastLoginTime;
        private String loginOutTime;
        private String age;
        private Object beiyong2;
        private String work_unitName;
        private String departmentName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getWork_unitId() {
            return work_unitId;
        }

        public void setWork_unitId(int work_unitId) {
            this.work_unitId = work_unitId;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public Object getPower() {
            return power;
        }

        public void setPower(Object power) {
            this.power = power;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getLoginOutTime() {
            return loginOutTime;
        }

        public void setLoginOutTime(String loginOutTime) {
            this.loginOutTime = loginOutTime;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public Object getBeiyong2() {
            return beiyong2;
        }

        public void setBeiyong2(Object beiyong2) {
            this.beiyong2 = beiyong2;
        }

        public String getWork_unitName() {
            return work_unitName;
        }

        public void setWork_unitName(String work_unitName) {
            this.work_unitName = work_unitName;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }
    }
}
