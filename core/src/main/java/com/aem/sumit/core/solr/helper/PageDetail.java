package com.aem.sumit.core.solr.helper;

public class PageDetail {
    public PageDetail(String title, String name, String description, String path) {
        this.title = title;
        this.name = name;
        this.description = description;
        this.path = path;
    }

    private String title;

    private String name;

    private String description;

    private String path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
