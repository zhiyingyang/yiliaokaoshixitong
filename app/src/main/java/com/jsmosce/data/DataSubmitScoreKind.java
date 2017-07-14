package com.jsmosce.data;

import java.util.List;

/**
 * Created by lx on 2017/5/27.
 */
//提交考试项目
public class DataSubmitScoreKind {


    /**
     * Pack : Check
     * Interface : category
     * caseId : 9
     * cateInfo : [{"describe":"心里素质","describe1":"心里素质2"},{"describe":"面谈技巧","describe1":"面谈技巧2"}]
     *
     */

    private String Pack;
    private String Interface;
    private int caseId;
    private List<CateInfoBean> cateInfo;
    private int state;
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

    public List<CateInfoBean> getCateInfo() {
        return cateInfo;
    }

    public void setCateInfo(List<CateInfoBean> cateInfo) {
        this.cateInfo = cateInfo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }


    public static class CateInfoBean {
        /**
         * describe : 心里素质
         */

        private String describe;

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
    }
}
