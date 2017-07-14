package com.jsmosce.data;

/**
 * Created by lx on 2017/6/13.
 */

public class DataTestDesign {


    /**
     * state : 1
     * caseInfo : {"Id":"160","name":"324e","number":"111497234322392","firstdoneTime":"2017-06-20","textTime":"2017-06-20","charge":"123","rId":"2","exam_room":"考场2"}
     */

    private int state;
    private CaseInfoBean caseInfo;
    private String Message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public CaseInfoBean getCaseInfo() {
        return caseInfo;
    }

    public void setCaseInfo(CaseInfoBean caseInfo) {
        this.caseInfo = caseInfo;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public static class CaseInfoBean {
        /**
         * Id : 160
         * name : 324e
         * number : 111497234322392
         * firstdoneTime : 2017-06-20
         * textTime : 2017-06-20
         * charge : 123
         * rId : 2
         * exam_room : 考场2
         */

        private String Id;
        private String name;
        private String number;
        private String firstdoneTime;
        private String textTime;
        private String charge;
        private String rId;
        private String am_pm;
        private String exam_room;

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

        public String getFirstdoneTime() {
            return firstdoneTime;
        }

        public void setFirstdoneTime(String firstdoneTime) {
            this.firstdoneTime = firstdoneTime;
        }

        public String getTextTime() {
            return textTime;
        }

        public void setTextTime(String textTime) {
            this.textTime = textTime;
        }

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }

        public String getRId() {
            return rId;
        }

        public void setRId(String rId) {
            this.rId = rId;
        }

        public String getExam_room() {
            return exam_room;
        }

        public void setExam_room(String exam_room) {
            this.exam_room = exam_room;
        }

        public String getAm_pm() {
            return am_pm;
        }

        public void setAm_pm(String am_pm) {
            this.am_pm = am_pm;
        }
    }
}
