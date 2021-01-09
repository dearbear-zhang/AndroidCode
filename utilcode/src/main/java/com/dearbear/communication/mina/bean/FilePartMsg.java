package com.dearbear.communication.mina.bean;

import java.io.Serializable;

public class FilePartMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private int partId;
    private transient byte[] data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public byte[] getData() {
        return this.data.clone();
    }

    public void setData(byte[] data) {
        this.data = data.clone();
    }
}
