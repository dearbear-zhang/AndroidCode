package com.mine.dearbear.bean;

import java.io.Serializable;

/**
 * Created by zhanggx2 on 2017/3/6.
 */

public class AppKeyGson implements Serializable {
    private int ret;
    private String msg;
    private String token;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
