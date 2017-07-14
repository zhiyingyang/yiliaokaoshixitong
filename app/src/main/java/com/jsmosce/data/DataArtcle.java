package com.jsmosce.data;

import com.jsmosce.base.BaseData;

import java.util.List;

/**
 * Created by lx on 2017/5/18.
 */

public class DataArtcle extends BaseData {

    /**
     * Pack : Case
     * Interface : goodsAdd
     * caseId : 5
     * goodsInfo : [{"name":"考生物品一","number":"2","type":1},{"name":"考生物品二", "number":"5","type":2}]
     */

    private String Pack;
    private String Interface;
    private int caseId;
    private String message;
    private List<GoodsInfoBean> goodsInfo;

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

    public List<GoodsInfoBean> getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(List<GoodsInfoBean> goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    public static class GoodsInfoBean {
        /**
         * name : 考生物品一
         * number : 2
         * type : 1
         */

        private String name;
        private String number;
        private int type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
