package com.dearbear.communication.mina.bean;

import java.io.Serializable;

public class FileTask implements Serializable {
    public long id;
    public String fileName;
    public String filePath;
    public String md5;
    public long startTime;
    public long endTime;
    public long length;
    public int fileSegmentSize;
    public int lastSegmentSize;
    public int partId;
    public int partNum;
}
