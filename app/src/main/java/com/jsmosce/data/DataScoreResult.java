package com.jsmosce.data;

import java.util.List;

/**
 * Created by lx on 2017/6/8.
 */

public class DataScoreResult {

    /**
     * Pack : Check
     * Interface : points
     * caseId : 10
     * pointsInfo : [{"describe":"特别好","score":8},{"describe":"很好","score":5},{"describe":"有点差","score":4}]
     */

    private String Pack;
    private String Interface;
    private int caseId;
    private List<PointsInfoBean> pointsInfo;
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

    public List<PointsInfoBean> getPointsInfo() {
        return pointsInfo;
    }

    public void setPointsInfo(List<PointsInfoBean> pointsInfo) {
        this.pointsInfo = pointsInfo;
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
        this.Message = message;
    }

    public static class PointsInfoBean {
        /**
         * describe : 特别好
         * score : 8
         */

        private String describe;
        private String score;

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }
}
