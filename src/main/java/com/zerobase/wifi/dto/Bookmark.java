package com.zerobase.wifi.dto;

public class Bookmark {
    private int id;
    private String bookmarkName;
    private String bookmarkOrder;
    private String registerDatetime;
    private String modificationDatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookmarkName() {
        return bookmarkName;
    }

    public void setBookmarkName(String bookmarkName) {
        this.bookmarkName = bookmarkName;
    }

    public String getBookmarkOrder() {
        return bookmarkOrder;
    }

    public void setBookmarkOrder(String bookmarkOrder) {
        this.bookmarkOrder = bookmarkOrder;
    }

    public String getRegisterDatetime() {
        return registerDatetime;
    }

    public void setRegisterDatetime(String registerDatetime) {
        this.registerDatetime = registerDatetime;
    }

    public String getModificationDatetime() {
        return modificationDatetime;
    }

    public void setModificationDatetime(String modificationDatetime) {
        this.modificationDatetime = modificationDatetime;
    }
}
