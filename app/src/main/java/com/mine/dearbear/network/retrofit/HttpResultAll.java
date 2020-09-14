package com.mine.dearbear.network.retrofit;

import java.io.Serializable;

/**
 * Created by on 2017/3/6.
 */

public class HttpResultAll implements Serializable {
    private int status;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
