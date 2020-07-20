package com.iedss_local_emergency_scheduler.emergency_scheduler.repository;

import com.iedss_local_emergency_scheduler.emergency_scheduler.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    Optional<Doctor> findDoctorById(int id);
    List<Doctor> findAllBySpeciality(int speciality);

}
