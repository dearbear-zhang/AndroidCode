package com.mine.dearbear.network.retrofit;

import java.io.Serializable;

/**
 * Created by on 2017/3/6.
 */

public class HttpResult<T> implements Serializable {
    private String result;
    private String message;
    private T data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
