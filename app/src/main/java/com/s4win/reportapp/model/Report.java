package com.s4win.reportapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dariopellegrini on 11/09/17.
 */

public class Report {
    @SerializedName("id")
    private String id;

    @SerializedName("dt_create")
    private String createDate;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    public Report() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
