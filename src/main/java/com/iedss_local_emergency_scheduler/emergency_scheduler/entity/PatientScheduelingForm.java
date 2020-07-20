package com.iedss_local_emergency_scheduler.emergency_scheduler.entity;

import java.sql.Timestamp;

public class PatientScheduelingForm {
    private String patientName;
    private int sevirityIndex;
    private int injurityLevel;
    private Timestamp entranceTime;

    public PatientScheduelingForm() {
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getSevirityIndex() {
        return sevirityIndex;
    }

    public void setSevirityIndex(int sevirityIndex) {
        this.sevirityIndex = sevirityIndex;
    }

    public int getInjurityLevel() {
        return injurityLevel;
    }

    public void setInjurityLevel(int injurityLevel) {
        this.injurityLevel = injurityLevel;
    }

    public Timestamp getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(Timestamp entranceTime) {
        this.entranceTime = entranceTime;
    }
}
