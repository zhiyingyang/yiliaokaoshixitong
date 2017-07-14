package com.jsmosce.data;

import java.util.List;

/**
 * Created by lx on 2017/5/24.
 */

public class DataHomeTest {


    private String Message;
    /**
     * state : 1
     * Info : [{"Id":"10","name":"考案名称接口","number":"kaoanhao001","text_state":"0","scheduling":"0","grade_state":"0","design_state":"0","scheduling_state":"0"},{"Id":"5","name":"考案名称5","number":"kaoan005","text_state":"0","scheduling":"1","grade_state":"0","design_state":"1","scheduling_state":"0"},{"Id":"4","name":"考案名称4","number":"kaoan004","text_state":"0","scheduling":"0","grade_state":"0","design_state":"0","scheduling_state":"0"},{"Id":"3","name":"考案名称3","number":"kaoan003","text_state":"0","scheduling":"0","grade_state":"0","design_state":"0","scheduling_state":"0"},{"Id":"2","name":"考案名称2","number":"kaoan002","text_state":"0","scheduling":"0","grade_state":"0","design_state":"0","scheduling_state":"0"},{"Id":"1","name":"考案名称1","number":"kaoan001","text_state":"0","scheduling":"0","grade_state":"0","design_state":"0","scheduling_state":"0"}]
     */

    private int state;
    private List<InfoBean> Info;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<InfoBean> getInfo() {
        return Info;
    }

    public void setInfo(List<InfoBean> Info) {
        this.Info = Info;
    }


    public static class InfoBean {
        /**
         * Id : 10
         * name : 考案名称接口
         * number : kaoanhao001
         * text_state : 0
         * scheduling : 0
         * grade_state : 0
         * design_state : 0
         * scheduling_state : 0
         */

        private int Id;
        private String name;
        private String number;
        private int text_state;
        private String scheduling;
        private String grade_state;
        private String design_state;
        private String scheduling_state;

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

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getText_state() {
            return text_state;
        }

        public void setText_state(int text_state) {
            this.text_state = text_state;
        }

        public String getScheduling() {
            return scheduling;
        }

        public void setScheduling(String scheduling) {
            this.scheduling = scheduling;
        }

        public String getGrade_state() {
            return grade_state;
        }

        public void setGrade_state(String grade_state) {
            this.grade_state = grade_state;
        }

        public String getDesign_state() {
            return design_state;
        }

        public void setDesign_state(String design_state) {
            this.design_state = design_state;
        }

        public String getScheduling_state() {
            return scheduling_state;
        }

        public void setScheduling_state(String scheduling_state) {
            this.scheduling_state = scheduling_state;
        }
    }
}
