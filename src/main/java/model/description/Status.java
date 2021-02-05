package model.description;

import name.StatusName;

import java.util.Date;

public class Status {
    private long statusTime = 0L;
    private String statusName;
    private int statusIndex = 0;

    public Status(int statusIndex) {
        this.statusIndex = statusIndex;
        this.statusName = StatusName.LONG_NAME[statusIndex];
        this.statusTime = new Date().getTime();
    }
    public Status(int statusIndex, long time) {
        this.statusIndex = statusIndex;
        this.statusName = StatusName.LONG_NAME[statusIndex];
        this.statusTime = time;
    }

    public void setStatusIfCreate (int statusIndex) {
        this.statusIndex = statusIndex;
        this.statusName = StatusName.LONG_NAME[statusIndex];
        this.statusTime = new Date().getTime();
    }

    public void setStatusIfRestore (int statusIndex, long time) {
        this.statusIndex = statusIndex;
        this.statusName = StatusName.LONG_NAME[statusIndex];
        this.statusTime = time;
    }
/*
    public void setStatusIfChange (int statusIndex) {
        this.statusIndex = statusIndex;
        this.statusName = StatusName.LONG_NAME[statusIndex];
    }*/

    public long getStatusTime() {
        return statusTime;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusTime(long statusTime) {
        this.statusTime = statusTime;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getStatusIndex() {
        return statusIndex;
    }
}
