package com.example.smartdevicemanipulator.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeDefinition {
    private Integer id;
    private String attribute;
    private String attributeType;
    private String cluster;
    private Boolean readable;
    private Boolean reportable;
    private Boolean writable;

    public AttributeDefinition() {
    }

    public AttributeDefinition(Integer id, String attribute, String attributeType, String cluster, Boolean readable, Boolean reportable, Boolean writable) {
        this.id = id;
        this.attribute = attribute;
        this.attributeType = attributeType;
        this.cluster = cluster;
        this.readable = readable;
        this.reportable = reportable;
        this.writable = writable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public Boolean getReadable() {
        return readable;
    }

    public void setReadable(Boolean readable) {
        this.readable = readable;
    }

    public Boolean getReportable() {
        return reportable;
    }

    public void setReportable(Boolean reportable) {
        this.reportable = reportable;
    }

    public Boolean getWritable() {
        return writable;
    }

    public void setWritable(Boolean writable) {
        this.writable = writable;
    }

    @Override
    public String toString() {
        return "AttributeDefinition{" +
                "id=" + id +
                ", attribute='" + attribute + '\'' +
                ", attributeType='" + attributeType + '\'' +
                ", cluster='" + cluster + '\'' +
                ", readable=" + readable +
                ", reportable=" + reportable +
                ", writable=" + writable +
                '}';
    }
}
