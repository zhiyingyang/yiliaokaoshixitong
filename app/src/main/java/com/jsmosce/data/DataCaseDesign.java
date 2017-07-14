package com.jsmosce.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lx on 2017/6/2.
 */

public class DataCaseDesign implements Serializable {


    /**
     * Pack : Case
     * Interface : addcaseDesign
     * caseId : 62
     * designInfo : [{"name":"前言","name2":"前言2","message":"注意事项","orderby":0},{"name":"简介","name2":"简介2","message":"注意事项2","orderby":0}]
     */

    private int state;
    private String Pack;
    private String Interface;
    private int caseId;
    private List<DesignInfoBean> designInfo;
    private String Message;
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

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public List<DesignInfoBean> getDesignInfo() {
        return designInfo;
    }

    public void setDesignInfo(List<DesignInfoBean> designInfo) {
        this.designInfo = designInfo;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static class DesignInfoBean implements Serializable{
        /**
         * name : 前言
         * name2 : 前言2
         * message : 注意事项
         * orderby : 0
         */

        private String name;
        private String name2;
        private String message="";
        private int orderby;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName2() {
            return name2;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getOrderby() {
            return orderby;
        }

        public void setOrderby(int orderby) {
            this.orderby = orderby;
        }
    }
}
