package com.jsmosce.data;

import java.util.List;

/**
 * Created by lx on 2017/6/9.
 */

public class DataScoreing {


    /**
     * state : 1
     * Info : {"cate":[{"Id":"30","describe":"心里素质"},{"Id":"31","describe":"面谈技巧"}],"points":[{"Id":"19","describe":"特别好","score":"8"},{"Id":"20","describe":"很好","score":"5"},{"Id":"21","describe":"有点差","score":"4"}]}
     */

    private int state;
    private InfoBean Info;
    private String message;

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
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class InfoBean {
        private List<CateBean> cate;
        private List<PointsBean> points;

        public List<CateBean> getCate() {
            return cate;
        }

        public void setCate(List<CateBean> cate) {
            this.cate = cate;
        }

        public List<PointsBean> getPoints() {
            return points;
        }

        public void setPoints(List<PointsBean> points) {
            this.points = points;
        }

        public static class CateBean {
            /**
             * Id : 30
             * describe : 心里素质
             */

            private String Id;
            private String describe;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }
        }

        public static class PointsBean {
            /**
             * Id : 19
             * describe : 特别好
             * score : 8
             */

            private String Id;
            private String describe;
            private String score;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

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
}
