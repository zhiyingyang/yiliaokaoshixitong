package com.jsmosce.data;

import java.util.List;

/**
 * Created by lx on 2017/5/25.
 */

public class DataSelectRoom {


    private String Message;
    private int state;
    private InfoBean Info;


    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

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

    public static class InfoBean {
        private List<AmBean> am;
        private List<PmBean> pm;

        public List<AmBean> getAm() {
            return am;
        }

        public void setAm(List<AmBean> am) {
            this.am = am;
        }

        public List<PmBean> getPm() {
            return pm;
        }

        public void setPm(List<PmBean> pm) {
            this.pm = pm;
        }

        public static class AmBean {
            /**
             * Id : 1
             * name : 考场1
             * location : 地址1
             */

            private String Id;
            private String name;
            private String location;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }
        }

        public static class PmBean {
            /**
             * Id : 1
             * name : 考场1
             * location : 地址1
             */

            private String Id;
            private String name;
            private String location;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }
        }
    }
}
