package com.jsmosce.data;

import java.util.List;

/**
 * Created by lx on 2017/6/19.
 */

public class DataHistoryCase {

    /**
     * state : 1
     * Info : [{"Id":"180","name":"想","number":"111497406793093","text_state":"2"}]
     */

    private int state;
    private List<InfoBean> Info;

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
         * Id : 180
         * name : 想
         * number : 111497406793093
         * text_state : 2
         */

        private String Id;
        private String name;
        private String number;
        private String text_state;

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
    }
}
