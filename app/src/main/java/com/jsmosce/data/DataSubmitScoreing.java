package com.jsmosce.data;

import java.util.List;

/**
 * Created by lx on 2017/6/9.
 */

public class DataSubmitScoreing {


    /**
     * Pack : Check
     * Interface : gradeSubmit
     * caseId : 5
     * studentId : 3
     * userId : 1
     * scoreInfo : [{"case_assessmentId":"1","result":"3"},{"case_assessmentId":"2","result":"4"},{"case_assessmentId":"3","result":"2"}]
     */

    private String Pack;
    private String Interface;
    private int caseId;
    private int studentId;
    private int userId;
    private List<ScoreInfoBean> scoreInfo;

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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<ScoreInfoBean> getScoreInfo() {
        return scoreInfo;
    }

    public void setScoreInfo(List<ScoreInfoBean> scoreInfo) {
        this.scoreInfo = scoreInfo;
    }

    public static class ScoreInfoBean {
        /**
         * case_assessmentId : 1
         * result : 3
         */

        private String case_assessmentId;
        private String result;

        public String getCase_assessmentId() {
            return case_assessmentId;
        }

        public void setCase_assessmentId(String case_assessmentId) {
            this.case_assessmentId = case_assessmentId;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
