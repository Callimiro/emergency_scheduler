package com.iedss_local_emergency_scheduler.emergency_scheduler.repository;

import com.iedss_local_emergency_scheduler.emergency_scheduler.entity.Doctors_schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorScheduleRepository extends JpaRepository<Doctors_schedule,Long> {

    @Query(value = "SELECT * FROM doctor_schedule WHERE doctor_id = ?1 and position_in_queue = ?2",nativeQuery = true)
    Doctors_schedule getByDoctor_idAndPosition_in_queue(int doctor_id,int position_in_queue);
    
    @Query(value = "Update doctor_schedule SET position_in_queue = position_in_queue+1 WHERE doctor_id = ?1",nativeQuery=true)
    void updatePositionsPlusOne(int doctor_id);

}
