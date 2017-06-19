package com.example.administrator.retrofitdemo;

/**
 * date:2017/6/16 0016
 * authom:贾雪茹
 * function:
 */

public class Bean {

    /**
     * ret_code : 200
     * ret_msg : 登录成功
     * result : {"id":3,"password":"1","username":"11111111111"}
     */

    private int ret_code;
    private String ret_msg;
    private ResultBean result;

    public Bean(int ret_code, String ret_msg, ResultBean result) {
        this.ret_code = ret_code;
        this.ret_msg = ret_msg;
        this.result = result;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public String getRet_msg() {
        return ret_msg;
    }

    public void setRet_msg(String ret_msg) {
        this.ret_msg = ret_msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 3
         * password : 1
         * username : 11111111111
         */

        private int id;
        private String password;
        private String username;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
