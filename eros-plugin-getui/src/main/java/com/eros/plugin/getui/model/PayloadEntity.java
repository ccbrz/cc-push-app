package com.eros.plugin.getui.model;


import java.io.Serializable;

public class PayloadEntity implements Serializable {
    private String title;
    private String content;
    private String extraData;

    public PayloadEntity(String title, String content, String extraData) {
        this.title = title;
        this.content = content;
        this.extraData = extraData;
    }

    public PayloadEntity() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }
}
