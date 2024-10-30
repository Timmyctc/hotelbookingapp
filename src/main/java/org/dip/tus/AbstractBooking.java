package org.dip.tus;

import java.sql.Time;
import java.util.Date;

public abstract class AbstractBooking {

    public AbstractBooking(Date startDate, Time startTime, Date endDate, Time endTime, int roomNumber) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    private int roomNumber;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;


    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
