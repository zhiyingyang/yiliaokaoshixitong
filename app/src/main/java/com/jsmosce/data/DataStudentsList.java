package com.jsmosce.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lx on 2017/6/8.
 */

public class DataStudentsList {

    /**
     * state : 1
     * Info : {"caseInfo":{"Id":"62","name":"考案名称接口","number":"kaoanhao001","uId":"1","textTime":"2017-05-31","firstdoneTime":"2017-05-28","addTime":"2017-06-02 10:55:00","charge":"fuzeren","examiner":"0","scheduling":"1","rId":"1","am_pm":"2","target":"","work_unitId":"1","departmentId":"1","scheduling_state":"0","analyst":"0","text_state":"1","info":"做的不怎么样","analysisTime":"2017-06-07 17:56:24","grade_state":"1","duration":"1","design_state":"1","beiyong1":"0","exam_room":"考场1","work_unit":"测试医院"},"userInfo":[{"id":"2","username":"实习生一","state":"1"}]}
     *
     */

    private int state;
    private InfoBean Info;

    private String Message;
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public InfoBean getInfo() {
        return Info;
    }

    public void setInfo(InfoBean Info) {
        this.Info = Info;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public static class InfoBean {
        /**
         * caseInfo : {"Id":"62","name":"考案名称接口","number":"kaoanhao001","uId":"1","textTime":"2017-05-31","firstdoneTime":"2017-05-28","addTime":"2017-06-02 10:55:00","charge":"fuzeren","examiner":"0","scheduling":"1","rId":"1","am_pm":"2","target":"","work_unitId":"1","departmentId":"1","scheduling_state":"0","analyst":"0","text_state":"1","info":"做的不怎么样","analysisTime":"2017-06-07 17:56:24","grade_state":"1","duration":"1","design_state":"1","beiyong1":"0","exam_room":"考场1","work_unit":"测试医院"}
         * userInfo : [{"id":"2","username":"实习生一","state":"1"}]
         */

        private CaseInfoBean caseInfo;
        private List<UserInfoBean> userInfo;

        public CaseInfoBean getCaseInfo() {
            return caseInfo;
        }

        public void setCaseInfo(CaseInfoBean caseInfo) {
            this.caseInfo = caseInfo;
        }

        public List<UserInfoBean> getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(List<UserInfoBean> userInfo) {
            this.userInfo = userInfo;
        }

        public static class CaseInfoBean implements Serializable{
            /**
             * Id : 62
             * name : 考案名称接口
             * number : kaoanhao001
             * uId : 1
             * textTime : 2017-05-31
             * firstdoneTime : 2017-05-28
             * addTime : 2017-06-02 10:55:00
             * charge : fuzeren
             * examiner : 0
             * scheduling : 1
             * rId : 1
             * am_pm : 2
             * target :
             * work_unitId : 1
             * departmentId : 1
             * scheduling_state : 0
             * analyst : 0
             * text_state : 1
             * info : 做的不怎么样
             * analysisTime : 2017-06-07 17:56:24
             * grade_state : 1
             * duration : 1
             * design_state : 1
             * beiyong1 : 0
             * exam_room : 考场1
             * work_unit : 测试医院
             */

            private String Id;
            private String name;
            private String number;
            private String uId;
            private String textTime;
            private String firstdoneTime;
            private String addTime;
            private String charge;
            private String examiner;
            private String scheduling;
            private String rId;
            private String am_pm;
            private String target;
            private String work_unitId;
            private String departmentId;
            private String scheduling_state;
            private String analyst;
            private String text_state;
            private String info;
            private String analysisTime;
            private String grade_state;
            private String duration;
            private String design_state;
            private String beiyong1;
            private String exam_room;
            private String work_unit;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getUId() {
                return uId;
            }

            public void setUId(String uId) {
                this.uId = uId;
            }

            public String getTextTime() {
                return textTime;
            }

            public void setTextTime(String textTime) {
                this.textTime = textTime;
            }

            public String getFirstdoneTime() {
                return firstdoneTime;
            }

            public void setFirstdoneTime(String firstdoneTime) {
                this.firstdoneTime = firstdoneTime;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getCharge() {
                return charge;
            }

            public void setCharge(String charge) {
                this.charge = charge;
            }

            public String getExaminer() {
                return examiner;
            }

            public void setExaminer(String examiner) {
                this.examiner = examiner;
            }

            public String getScheduling() {
                return scheduling;
            }

            public void setScheduling(String scheduling) {
                this.scheduling = scheduling;
            }

            public String getRId() {
                return rId;
            }

            public void setRId(String rId) {
                this.rId = rId;
            }

            public String getAm_pm() {
                return am_pm;
            }

            public void setAm_pm(String am_pm) {
                this.am_pm = am_pm;
            }

            public String getTarget() {
                return target;
            }

            public void setTarget(String target) {
                this.target = target;
            }

            public String getWork_unitId() {
                return work_unitId;
            }

            public void setWork_unitId(String work_unitId) {
                this.work_unitId = work_unitId;
            }

            public String getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(String departmentId) {
                this.departmentId = departmentId;
            }

            public String getScheduling_state() {
                return scheduling_state;
            }

            public void setScheduling_state(String scheduling_state) {
                this.scheduling_state = scheduling_state;
            }

            public String getAnalyst() {
                return analyst;
            }

            public void setAnalyst(String analyst) {
                this.analyst = analyst;
            }

            public String getText_state() {
                return text_state;
            }

            public void setText_state(String text_state) {
                this.text_state = text_state;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getAnalysisTime() {
                return analysisTime;
            }

            public void setAnalysisTime(String analysisTime) {
                this.analysisTime = analysisTime;
            }

            public String getGrade_state() {
                return grade_state;
            }

            public void setGrade_state(String grade_state) {
                this.grade_state = grade_state;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getDesign_state() {
                return design_state;
            }

            public void setDesign_state(String design_state) {
                this.design_state = design_state;
            }

            public String getBeiyong1() {
                return beiyong1;
            }

            public void setBeiyong1(String beiyong1) {
                this.beiyong1 = beiyong1;
            }

            public String getExam_room() {
                return exam_room;
            }

            public void setExam_room(String exam_room) {
                this.exam_room = exam_room;
            }

            public String getWork_unit() {
                return work_unit;
            }

            public void setWork_unit(String work_unit) {
                this.work_unit = work_unit;
            }
        }

        public static class UserInfoBean implements Serializable {
            /**
             * id : 2
             * username : 实习生一
             * state : 1
             */

            private String id;
            private String username;
            private String state;
            private String phone;

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

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}
