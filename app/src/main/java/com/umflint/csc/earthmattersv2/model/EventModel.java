package com.umflint.csc.earthmattersv2.model;

import java.util.ArrayList;

/**
 * Created by Copenblend on 10/31/2016.
 */

public class EventModel {
    private String eventName;
    private String location;
    private String description;
    private String startDate;
    private String endDate;
    private String coverName;
    private String scheduleName;

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public EventModel() {

    }

    public EventModel(String eventName, String eventLocation, String eventDescription, String startDate, String endDate, String coverName, String scheduleName) {
        this.eventName = eventName;
        this.location = eventLocation;
        this.description = eventDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverName = coverName;
        this.scheduleName = scheduleName;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }
}
