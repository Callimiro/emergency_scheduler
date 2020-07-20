package com.iedss_local_emergency_scheduler.emergency_scheduler.repository;

import com.iedss_local_emergency_scheduler.emergency_scheduler.entity.Doctors_schedule;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.*;

@Repository
public class CreateDoctorScheduelRepository {
    @PersistenceContext
    private EntityManager entityManager;

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://iedss-cloud.cuovdwvtbrk4.us-east-1.rds.amazonaws.com:3306/iedss_cloud";
    // Database credentials
    static final String USER = "admin";
    static final String PASS = "admin1234";
    Connection conn = null;


    @Transactional
    public void insertWithQuery(Doctors_schedule doctors_schedule) {
        entityManager.createNativeQuery("INSERT INTO doctor_schedule (id, patient_id, doctor_id,position_in_queue ) VALUES (?,?,?,?)")
                .setParameter(1, doctors_schedule.getId())
                .setParameter(2, doctors_schedule.getPatient_id())
                .setParameter(3, doctors_schedule.getDoctor_id())
                .setParameter(4, doctors_schedule.getPosition_in_queue())
                .executeUpdate();

        try { //STEP 2: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO doctor_schedule (id, patient_id, doctor_id,position_in_queue )" +
                    " VALUES ("+doctors_schedule.getId()+","+doctors_schedule.getPatient_id()+","+doctors_schedule.getDoctor_id()+","+doctors_schedule.getPosition_in_queue()+")";
            Statement stm = conn.prepareStatement(sql);
            int rs = stm.executeUpdate(sql);
            stm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateWithQuery(int doctor_id) {
        entityManager.createNativeQuery("Update doctor_schedule SET position_in_queue = position_in_queue+1 WHERE doctor_id = ?1")
                .setParameter(1, doctor_id)
                .executeUpdate();

        try { //STEP 2: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "Update doctor_schedule SET position_in_queue = position_in_queue+1 WHERE doctor_id = "+doctor_id+"";
            Statement stm = conn.prepareStatement(sql);
            int rs = stm.executeUpdate(sql);
            stm.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
