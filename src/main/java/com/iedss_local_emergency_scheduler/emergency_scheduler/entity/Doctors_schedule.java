package com.iedss_local_emergency_scheduler.emergency_scheduler.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "doctor_schedule")
public class Doctors_schedule {
    @Id
    @GeneratedValue
    private int id;
    private int doctor_id;
    private int patient_id;
    private int position_in_queue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getPosition_in_queue() {
        return position_in_queue;
    }

    public void setPosition_in_queue(int position_in_queue) {
        this.position_in_queue = position_in_queue;
    }

    @Override
    public String toString() {
        return "Doctors_schedule{" +
                "id=" + id +
                ", doctor_id=" + doctor_id +
                ", patient_id=" + patient_id +
                ", position_in_queue=" + position_in_queue +
                '}';
    }
}
