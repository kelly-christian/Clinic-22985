package Kelly.Clinic.service;

import java.time.LocalDate;
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

        System.out.println("Appointment status: " + appo.getStatus());

        if(!"COMPLETED".equalsIgnoreCase(appo.getStatus())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"prescription is for only completed appointment");

        }
        
        System.out.println("Status check passed!");
        prescription.setAppointment(appo);
        prescription.setIssuedDate(LocalDate.now());
        prescriptionRepo.save(prescription);
        return "prescription saved successfull";

    }   


}
