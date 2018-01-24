package com.android.dev.qrcoursedorientation.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by iem on 18/01/2018.
 */

public class Course implements Serializable{

    private List<Checkpoint> checkpointList;
    private String status;
    private String timestamp;
    private String mailOrganizer;
    private String idRunner;
    private Date date;

    public Course(String status, String timestamp, String mailOrganizer, String idRunner, List<Checkpoint>checkpointList) {
        this.status = status;
        this.timestamp = timestamp;
        this.mailOrganizer = mailOrganizer;
        this.idRunner = idRunner;
        this.checkpointList = checkpointList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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
    public void addCheckpoint(Checkpoint checkpoint){
        this.checkpointList.add(checkpoint);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
