package com.jsmosce.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lx on 2017/6/21.
 */

public class DataAllCandiates {


    /**
     * state : 0
     * info : {"studentInfo":[{"id":"2","username":"实习生一","phone":"13545629874"},{"id":"3","username":"实习生二","phone":"13545629877"},{"id":"4","username":"实习生哈哈","phone":"13545629834"},{"id":"6","username":"实习宝宝","phone":"18574149685"},{"id":"11","username":"实习小妹","phone":"13555555555"},{"id":"12","username":"实习12","phone":"77777777777"},{"id":"13","username":"实习11","phone":"88888888888"},{"id":"14","username":"实习10","phone":"66666666666"}],"checkedInfo":[{"id":"2","username":"实习生一","phone":"13545629874"},{"id":"4","username":"实习生哈哈","phone":"13545629834"},{"id":"6","username":"实习宝宝","phone":"18574149685"}]}
     */

    private int state;
    private InfoBean info;
    private String Message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public static class InfoBean {
        private List<StudentInfoBean> studentInfo;
        private List<StudentInfoBean> checkedInfo;

        public List<StudentInfoBean> getStudentInfo() {
            return studentInfo;
        }

        public void setStudentInfo(List<StudentInfoBean> studentInfo) {
            this.studentInfo = studentInfo;
        }

        public List<StudentInfoBean> getCheckedInfo() {
            return checkedInfo;
        }

        public void setCheckedInfo(List<StudentInfoBean> checkedInfo) {
            this.checkedInfo = checkedInfo;
        }

        public static class StudentInfoBean implements Serializable {
            /**
             * id : 2
             * username : 实习生一
             * phone : 13545629874
             */

            private int id;
            private String username;
            private String phone;
            private boolean isSelected=false;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }
        }

    }
}
