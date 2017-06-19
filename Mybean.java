package com.example.administrator.retrofitdemo;

import static com.example.administrator.retrofitdemo.MainActivity.username;

/**
 * date:2017/6/16 0016
 * authom:贾雪茹
 * function:
 */

public class Mybean {
    private String username ;
    private String password ;
    private String postkey;

    public Mybean(String username, String password, String postkey) {
        this.username = username;
        this.password = password;
        this.postkey = postkey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPostkey() {
        return postkey;
    }

    public void setPostkey(String postkey) {
        this.postkey = postkey;
    }
}
