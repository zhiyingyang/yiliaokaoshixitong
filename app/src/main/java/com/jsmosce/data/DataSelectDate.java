package com.jsmosce.data;

import java.util.List;

/**
 * Created by lx on 2017/5/25.
 */

public class DataSelectDate {


    /**
     * state : 1
     * Info : {"date":["2017-06-02"]}
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
        private List<String> date;

        public List<String> getDate() {
            return date;
        }

        public void setDate(List<String> date) {
            this.date = date;
        }
    }
}
