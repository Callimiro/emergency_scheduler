package com.iedss_local_emergency_scheduler.emergency_scheduler.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue
    private int id;
    private int seniority;
    private int speciality;
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Doctor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeniority() {
        return seniority;
    }

    public void setSeniority(int seniority) {
        this.seniority = seniority;
    }

    public int getSpeciality() {
        return speciality;
    }

    public void setSpeciality(int speciality) {
        this.speciality = speciality;
    }
}
