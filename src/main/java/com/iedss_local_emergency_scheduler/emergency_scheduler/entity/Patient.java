package com.iedss_local_emergency_scheduler.emergency_scheduler.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name = "patients")
public class Patient {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Timestamp created_at;
    private Timestamp updated_at;
    private int injurity;
    private int sevirity;

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", injurity=" + injurity +
                ", sevirity=" + sevirity +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp update_at) {
        this.updated_at = update_at;
    }

    public int getInjurity() {
        return injurity;
    }

    public void setInjurity(int injurity) {
        this.injurity = injurity;
    }

    public int getSevirity() {
        return sevirity;
    }

    public void setSevirity(int sevirity) {
        this.sevirity = sevirity;
    }
}
