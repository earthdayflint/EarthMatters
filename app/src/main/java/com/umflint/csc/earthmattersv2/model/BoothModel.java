package com.umflint.csc.earthmattersv2.model;

/**
 * Created by Benjamin on 1/23/2017.
 */

public class BoothModel {
    private String subEventName;
    private long subEventBoothNumber;
    private String subEventDescription;
    private String subEventWebSite;

    public BoothModel() {
    }

    public BoothModel(String boothName, long boothNumber, String boothDescription, String boothWebsite) {
        this.subEventName = boothName;
        this.subEventBoothNumber = boothNumber;
        this.subEventDescription = boothDescription;
        this.subEventWebSite = boothWebsite;
    }

    public String getSubEventWebSite() {
        return subEventWebSite;
    }

    public void setSubEventWebSite(String subEventWebSite) {
        this.subEventWebSite = subEventWebSite;
    }

    public String getSubEventName() {
        return subEventName;
    }

    public void setSubEventName(String subEventName) {
        this.subEventName = subEventName;
    }

    public long getSubEventBoothNumber() {
        return subEventBoothNumber;
    }

    public void setSubEventBoothNumber(long subEventBoothNumber) {
        this.subEventBoothNumber = subEventBoothNumber;
    }

    public String getSubEventDescription() {
        return subEventDescription;
    }

    public void setSubEventDescription(String subEventDescription) {
        this.subEventDescription = subEventDescription;
    }
}
