package com.oritmalki.newsapp1.networkapi;


import android.support.annotation.NonNull;

import com.oritmalki.newsapp1.QueryUtils;

import java.util.List;

public class Result implements Comparable<Result> {


    private String id;

    private String type;

    private String sectionId;

    private String sectionName;

    private String webPublicationDate;

    private String webTitle;

    private String webUrl;

    private String apiUrl;

    private Boolean isHosted;

    private String pillarId;

    private String pillarName;

    private List<Tag> tags = null;

    public Result() {

    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void setIsHosted(Boolean isHosted) {
        this.isHosted = isHosted;
    }

    public void setPillarId(String pillarId) {
        this.pillarId = pillarId;
    }

    public void setPillarName(String pillarName) {
        this.pillarName = pillarName;
    }

    @Override
    public int compareTo(@NonNull Result o) {
        return QueryUtils.convertDate(getWebPublicationDate()).compareTo(QueryUtils.convertDate(o.getWebPublicationDate()));
    }
}