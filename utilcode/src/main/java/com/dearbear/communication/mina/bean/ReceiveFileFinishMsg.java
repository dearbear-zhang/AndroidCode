package com.dearbear.communication.mina.bean;

import java.io.Serializable;

public class ReceiveFileFinishMsg implements Serializable {
    private long id;
    private boolean success;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
