package com.dearbear.communication.mina.bean;

import java.io.Serializable;

public class RequestSendFileMsg implements Serializable {
    private String fileName;
    private String md5;
    private long length;
    private int id;
    private int fileSegmentSize;
    private int partNum;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFileSegmentSize() {
        return fileSegmentSize;
    }

    public void setFileSegmentSize(int fileSegmentSize) {
        this.fileSegmentSize = fileSegmentSize;
    }

    public int getPartNum() {
        return partNum;
    }

    public void setPartNum(int partNum) {
        this.partNum = partNum;
    }
}
