package com.jsmosce.data;

import java.util.List;

/**
 * Created by lx on 2017/6/21.
 */

public class DataSubmitCandidates {

    /**
     * Pack : Case
     * Interface : examSubmit
     * caseId : 184
     * examInfo : [{"id":2},{"id":4},{"id":6}]
     */

    private String Pack;
    private String Interface;
    private int caseId;
    private List<ExamInfoBean> examInfo;

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

    public List<ExamInfoBean> getExamInfo() {
        return examInfo;
    }

    public void setExamInfo(List<ExamInfoBean> examInfo) {
        this.examInfo = examInfo;
    }

    public static class ExamInfoBean {
        /**
         * id : 2
         */

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
