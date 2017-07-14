package com.jsmosce.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lx on 2017/6/12.
 */
//已有的排程列表
public class DataCaseList {


    /**
     * state : 1
     * Info : [{"Id":"140","name":"Q","number":"XXX","text_state":"0","scheduling":"1","grade_state":"1","design_state":"0","scheduling_state":"0"},{"Id":"72","name":"考案名称接口","number":"kaoanhao001","text_state":"0","scheduling":"0","grade_state":"1","design_state":"0","scheduling_state":"0"},{"Id":"62","name":"考案名称接口","number":"kaoanhao001","text_state":"1","scheduling":"1","grade_state":"1","design_state":"1","scheduling_state":"0"}]
     */

    private int state;
    private List<InfoBean> Info;
    private String message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class InfoBean implements Serializable {
        /**
         * Id : 140
         * name : Q
         * number : XXX
         * text_state : 0
         * scheduling : 1
         * grade_state : 1
         * design_state : 0
         * scheduling_state : 0
         */

        private String Id;
        private String name;
        private String number;
        private String text_state;
        private String scheduling;
        private String grade_state;
        private String design_state;
        private String scheduling_state;

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

        public String getText_state() {
            return text_state;
        }

        public void setText_state(String text_state) {
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
