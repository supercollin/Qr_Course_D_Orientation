package com.android.dev.qrcoursedorientation.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by iem on 18/01/2018.
 */

public class Course implements Serializable{

    private List<Checkpoint> checkpointList;
    private String status;
    private long timestamp;
    private String mailOrganizer;
    private String idRunner;
    private String date;

    public Course(String status, long timestamp, String mailOrganizer, String idRunner, List<Checkpoint>checkpointList) {
        this.status = status;
        this.timestamp = timestamp;
        this.mailOrganizer = mailOrganizer;
        this.idRunner = idRunner;
        this.checkpointList = checkpointList;
        Date date;
        date = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        this.date = formatter.format(date);

    }

    public Course(String status, long timestamp, String mailOrganizer, String idRunner) {
        this.status = status;
        this.timestamp = timestamp;
        this.mailOrganizer = mailOrganizer;
        this.idRunner = idRunner;
        this.checkpointList = new ArrayList<>();
        Date date;
        date = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        this.date = formatter.format(date);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMailOrganizer() {
        return mailOrganizer;
    }

    public void setMailOrganizer(String mailOrganizer) {
        this.mailOrganizer = mailOrganizer;
    }

    public String getIdRunner() {
        return idRunner;
    }

    public void setIdRunner(String idRunner) {
        this.idRunner = idRunner;
    }

    public Checkpoint getCheckpoint(int index){
        return checkpointList.get(index);
    }

    public void setCheckpointList(List<Checkpoint> checkpointList){
        this.checkpointList = checkpointList;
    }

    public List<Checkpoint> getCheckpointList() {
        return checkpointList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        date = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        this.date = formatter.format(date);
    }

    @Override
    public String toString() {
        return "Course{" +
                "checkpointList=" + checkpointList +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                ", mailOrganizer='" + mailOrganizer + '\'' +
                ", idRunner='" + idRunner + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
