package com.example.administrator.retrofitdemo;

/**
 * date:2017/6/16 0016
 * authom:贾雪茹
 * function:
 */

public class MyPhone {

    /**
     * ret_code : 200
     * ret_msg : 上传成功
     * path : /usr/local/apache-tomcat-8.0.36/webapps/Bwei/upload/1497618965011.jpg
     */

    private int ret_code;
    private String ret_msg;
    private String path;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
