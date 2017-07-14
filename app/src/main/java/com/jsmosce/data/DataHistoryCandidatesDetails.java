package com.jsmosce.data;

import java.util.List;

/**
 * Created by lx on 2017/6/20.
 */
//历史记录考试结果
public class DataHistoryCandidatesDetails {

    /**
     * state : 1
     * resultInfo : [{"Id":"82","result":"76","case_assessmentId":"190","assessment_standard":"未知","assessment":"tut萝莉控哦哦"}]
     */

    private int state;
    private List<ResultInfoBean> resultInfo;
    private String Message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<ResultInfoBean> getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(List<ResultInfoBean> resultInfo) {
        this.resultInfo = resultInfo;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public static class ResultInfoBean {
        /**
         * Id : 82
         * result : 76
         * case_assessmentId : 190
         * assessment_standard : 未知
         * assessment : tut萝莉控哦哦
         */

        private String Id;
        private String result;
        private String case_assessmentId;
        private String assessment_standard;
        private String assessment;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getCase_assessmentId() {
            return case_assessmentId;
        }

        public void setCase_assessmentId(String case_assessmentId) {
            this.case_assessmentId = case_assessmentId;
        }

        public String getAssessment_standard() {
            return assessment_standard;
        }

        public void setAssessment_standard(String assessment_standard) {
            this.assessment_standard = assessment_standard;
        }

        public String getAssessment() {
            return assessment;
        }

        public void setAssessment(String assessment) {
            this.assessment = assessment;
        }
    }
}
