package com.mine.dearbear.bean;

import java.io.Serializable;

public class BookAddResult implements Serializable {
    private int result;
    private String originalFilename;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }
}
