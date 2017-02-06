package com.umflint.csc.earthmattersv2.model;

/**
 * Created by Benjamin on 1/23/2017.
 */

public class BoothModel {
    private String boothName;
    private long boothNumber;
    private String boothDescription;
    private String boothWebsite;

    public BoothModel() {
    }

    public BoothModel(String boothName, long boothNumber, String boothDescription, String boothWebsite) {
        this.boothName = boothName;
        this.boothNumber = boothNumber;
        this.boothDescription = boothDescription;
        this.boothWebsite = boothWebsite;
    }

    public String getBoothWebsite() {
        return boothWebsite;
    }

    public void setBoothWebsite(String boothWebsite) {
        this.boothWebsite = boothWebsite;
    }

    public String getBoothName() {
        return boothName;
    }

    public void setBoothName(String boothName) {
        this.boothName = boothName;
    }

    public long getBoothNumber() {
        return boothNumber;
    }

    public void setBoothNumber(long boothNumber) {
        this.boothNumber = boothNumber;
    }

    public String getBoothDescription() {
        return boothDescription;
    }

    public void setBoothDescription(String boothDescription) {
        this.boothDescription = boothDescription;
    }
}
