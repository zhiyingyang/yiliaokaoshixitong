package com.jsmosce.data;

import java.util.List;

/**
 * Created by lx on 2017/6/12.
 */

public class DataScoreKind {

    /**
     * state : 1
     * Info : [{"Id":"88","describe":"评分类目一"},{"Id":"89","describe":"评分类目二"}]
     */

    private String state;
    private List<InfoBean> Info;

    public String getState() {
        return state;
    }

    public void setState(String state) {
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
         * Id : 88
         * describe : 评分类目一
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
}
