package com.iedss_local_emergency_scheduler.emergency_scheduler.service;

import com.iedss_local_emergency_scheduler.emergency_scheduler.entity.Doctor;
import com.iedss_local_emergency_scheduler.emergency_scheduler.entity.Doctors_schedule;
import com.iedss_local_emergency_scheduler.emergency_scheduler.entity.Patient;
import com.iedss_local_emergency_scheduler.emergency_scheduler.entity.PatientScheduelingForm;
import com.iedss_local_emergency_scheduler.emergency_scheduler.repository.CreatePatientRepository;
import com.iedss_local_emergency_scheduler.emergency_scheduler.repository.DoctorRepository;
import com.iedss_local_emergency_scheduler.emergency_scheduler.repository.DoctorScheduleRepository;
import com.iedss_local_emergency_scheduler.emergency_scheduler.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchedulingRequestService {

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    DoctorScheduleRepository doctorScheduleRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    CreatePatientRepository createPatientRepository;
    @Autowired
    RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(SchedulingRequestService.class);

    public Doctor getDoctorEvaluationResult(Doctor doctor1, Doctor doctor2, Patient patient1,Patient patient2){
    	Map<String, Object> json = new HashMap<>();
    	LocalDateTime now = LocalDateTime.now();
    	Timestamp time = Timestamp.valueOf(now);
    	json.put("senioritiD1", doctor1.getSeniority());
    	json.put("specD1", doctor1.getSpeciality());
    	json.put("sevP1", patient1.getSevirity());
    	json.put("injP1", patient1.getInjurity());
    	long wt1 = time.getTime() - (patient1.getCreated_at()).getTime();
    	wt1 = wt1/(1000*60);
    	json.put("wt1",wt1);
    	json.put("senioritiD2", doctor2.getSeniority());
    	json.put("specD2", doctor2.getSpeciality());
    	json.put("sevP2", patient2.getSevirity());
    	json.put("injP2", patient2.getInjurity());
    	long wt2 = time.getTime() - (patient2.getCreated_at()).getTime();
    	wt2 = wt2/(1000*60);
    	json.put("wt2",wt2);
    	String ResourceUrl="http://localhost:5000";
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	

    	HttpEntity<Map<String, Object>> entity = new HttpEntity<>(json, headers);
    	ResponseEntity<HashMap> response = restTemplate.postForEntity(ResourceUrl+"/predict", entity, HashMap.class);
    	logger.info("------------------------"+response.getBody()+"-----------------");
        /*
        *Here we evaluate 2 doctors and return the best one by
        *  Calling the Local Emergency Machine learning Model
         */
    	
    	if(response.getBody().get("mgp") == "0"){
    		return doctor2;
    	}else{
    		return doctor1;
    	}
        
    }

    public Doctor doctorsSortingResult(List<Doctor> doctors, PatientScheduelingForm patientScheduelingForm){
        /*
        *Here we get the List Of Doctors and compare 2 of them each time
        * by calling the getDoctorEvaluationResult Method
         */
        if (doctors.size()>= 2){
            Doctor doctor1 = doctors.get(0);
            Doctor doctor2 = null;
            Doctors_schedule doctors_schedule1 = doctorScheduleRepository.getByDoctor_idAndPosition_in_queue(doctor1.getId(),1);
            logger.info(doctors_schedule1.toString());
            Patient patient1 = patientRepository.findPatientById(doctors_schedule1.getPatient_id())
                    .orElseThrow(() -> new RuntimeException("Error: patient is not found."));

            Patient patient2 = null;
            Doctors_schedule doctors_schedule2 = null;
            for (Doctor doctor: doctors ) {
                //logger.warn(patient1.toString());
                //logger.warn(patient2.toString());
                if (doctors.indexOf(doctor)+1<doctors.size()){
                    doctor2 = doctors.get(doctors.indexOf(doctor)+1);
                    doctors_schedule2 = doctorScheduleRepository.getByDoctor_idAndPosition_in_queue(doctor2.getId(),1);
                    patient2 = patientRepository.findPatientById(doctors_schedule2.getPatient_id())
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                }
                doctor1 = this.getDoctorEvaluationResult(doctor1,doctor2,patient1,patient2);
            }
            //Saving Patient to database
            Patient patient = new Patient();
            patient.setInjurity(patientScheduelingForm.getInjurityLevel());
            patient.setSevirity(patientScheduelingForm.getSevirityIndex());
            patient.setName(patientScheduelingForm.getPatientName());
            createPatientRepository.insertWithQuery(patient);
            return doctor1;
        }else {
            return doctors.get(0);
        }
    }

    public List<Doctor> getDoctorsList(int injurity){
        List<Doctor> doctors = doctorRepository.findAllBySpeciality(injurity);
        return doctors;
    }
}
