package com.umflint.csc.earthmattersv2.model;

/**
 * Created by Benjamin on 2/6/2017.
 */

public class ScheduleModel {
    private String subEventStartTime;
    private String subEventEndTime;
    private String subEventName;
    private String subEventDescription;
    private String subEventDate;
    private String subEventId;

    public ScheduleModel() {
    }

    public ScheduleModel(String subEventStartTime, String subEventEndTime, String subEventName, String subEventDescription,
                         String subEventDate, String subEventId) {
        this.subEventStartTime = subEventStartTime;
        this.subEventEndTime = subEventEndTime;
        this.subEventName = subEventName;
        this.subEventDescription = subEventDescription;
        this.subEventDate = subEventDate;
        this.subEventId = subEventId;
    }

    public String getSubEventId() {
        return subEventId;
    }

    public void setSubEventId(String subEventId) {
        this.subEventId = subEventId;
    }

    public String getSubEventDate() {
        return subEventDate;
    }

    public void setSubEventDate(String subEventDate) {
        this.subEventDate = subEventDate;
    }

    public String getSubEventStartTime() {
        return subEventStartTime;
    }

    public void setSubEventStartTime(String subEventStartTime) {
        this.subEventStartTime = subEventStartTime;
    }

    public String getSubEventEndTime() {
        return subEventEndTime;
    }

    public void setSubEventEndTime(String subEventEndTime) {
        this.subEventEndTime = subEventEndTime;
    }

    public String getSubEventName() {
        return subEventName;
    }

    public void setSubEventName(String subEventName) {
        this.subEventName = subEventName;
    }

    public String getSubEventDescription() {
        return subEventDescription;
    }

    public void setSubEventDescription(String subEventDescription) {
        this.subEventDescription = subEventDescription;
    }
}
