package com.iedss_local_emergency_scheduler.emergency_scheduler.controller;

import com.iedss_local_emergency_scheduler.emergency_scheduler.entity.Doctor;
import com.iedss_local_emergency_scheduler.emergency_scheduler.entity.Doctors_schedule;
import com.iedss_local_emergency_scheduler.emergency_scheduler.entity.Patient;
import com.iedss_local_emergency_scheduler.emergency_scheduler.entity.PatientScheduelingForm;
import com.iedss_local_emergency_scheduler.emergency_scheduler.repository.CreateDoctorScheduelRepository;
import com.iedss_local_emergency_scheduler.emergency_scheduler.repository.DoctorScheduleRepository;
import com.iedss_local_emergency_scheduler.emergency_scheduler.repository.PatientRepository;
import com.iedss_local_emergency_scheduler.emergency_scheduler.service.SchedulingRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class LocalEmergencySchedulingController {
    String ResourceUrl ="http://localhost:9002";
    @Autowired
    SchedulingRequestService schedulingRequestService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CreateDoctorScheduelRepository createDoctorScheduelRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    DoctorScheduleRepository doctorScheduleRepository;

    Logger logger = LoggerFactory.getLogger(LocalEmergencySchedulingController.class);

    @GetMapping("/local_schedueling")
    public String getThis(){
        logger.info("------------- it is working -------------");
        return "THIS IS WORKING";
    }

    @PostMapping("/local_scheduel")
    public void LocalScheduling(@RequestBody PatientScheduelingForm patientScheduelingForm){
        //getting the list of doctors
    	logger.info("----------------------------------- before doctors----------------------------------");
        List<Doctor> doctors = schedulingRequestService.
                getDoctorsList(patientScheduelingForm.getInjurityLevel());
        //Finding the most compatible doctor
        logger.info("the size of doctors list is "+doctors.size());
        logger.info(patientScheduelingForm.toString());
        Doctor doctor = schedulingRequestService.
                doctorsSortingResult(doctors,patientScheduelingForm);
        logger.info("We are done a new scheduel has been affected to the doctor");
        //restTemplate.postForObject(ResourceUrl+"/notifications",patientScheduelingForm,PatientScheduelingForm.class);
        Doctors_schedule doctors_schedule = new Doctors_schedule();
        Patient patient = new Patient();
        patient = patientRepository.findPatientByName(patientScheduelingForm.getPatientName());
        doctors_schedule.setDoctor_id(doctor.getId());
        doctors_schedule.setPatient_id(patient.getId());
        doctors_schedule.setPosition_in_queue(1);
        createDoctorScheduelRepository.updateWithQuery(doctor.getId());
        createDoctorScheduelRepository.insertWithQuery(doctors_schedule);
    }
}
