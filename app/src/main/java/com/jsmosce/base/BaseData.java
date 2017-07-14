package com.jsmosce.base;

/**
 * Created by lx on 2017/5/24.
 */

public class BaseData {

    /**
     * state : 1
     * Info : 47
     */

    private int state = -1;
    private Object Info;
    private String Message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getInfo() {
        return Info;
    }

    public void setInfo(Object Info) {
        this.Info = Info;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

}
