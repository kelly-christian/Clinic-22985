package Kelly.Clinic.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import Kelly.Clinic.model.Appointment;
import Kelly.Clinic.model.Prescription;
import Kelly.Clinic.repository.AppointmentRepository;
import Kelly.Clinic.repository.PrescriptionRepository;

@Service 
public class PrescriptionService {

       @Autowired 
       private PrescriptionRepository prescriptionRepo;

       @Autowired 
       private AppointmentRepository appointmentRepo;

    public String savePrescription(Prescription prescription,UUID appointmentId){
        
        Appointment appo=appointmentRepo.findById(appointmentId).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"appointment Id doesnt exist")
        );

       

        if(!"COMPLETED".equalsIgnoreCase(appo.getStatus())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"prescription is for only completed appointment");

        }
        
       
        prescription.setAppointment(appo);
        prescription.setIssuedDate(LocalDate.now());
        prescriptionRepo.save(prescription);
        return "prescription saved successfull";

    }   

    public List<Prescription> getPrescription(UUID appointmentId){
        Appointment appo=appointmentRepo.findById(appointmentId)
        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"appointment Id not found"));

        return prescriptionRepo.findByAppointment_AppointmentId(appointmentId);
    }

    public List<Prescription>getPatientPrescription(UUID patientId){

        return prescriptionRepo.findByPrescriptionBelongsToPatient(patientId);
    }
}
