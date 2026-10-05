package Kelly.Clinic.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Kelly.Clinic.model.Appointment;
import Kelly.Clinic.model.Patient;
import Kelly.Clinic.repository.AppointmentRepository;
import Kelly.Clinic.repository.PatientRepository;

@Service 
public class PatientService {

    @Autowired 
    private PatientRepository patientRepo;
    @Autowired 
    private AppointmentRepository appoRepo;
    public String savePatient(Patient patient){
        patientRepo.save(patient);

        return "patient saved successfull";
    }

    public String deletePatient(UUID patientId){
        Optional<Patient> deleteOnePatient=patientRepo.findById(patientId);
         
        if(deleteOnePatient.isPresent()){
          
            Patient patientO=deleteOnePatient.get();
            List<Appointment> appoO=patientO.getAppointment();
            for(Appointment appoT:appoO){
                appoT.setPatient(null);
                appoRepo.save(appoT);
            }

              patientRepo.deleteById(patientId);
              return "deleted successfull";
        }
        else{
             return "id not found";
        }

        
    }

    public String updatePatient(Patient patient){

        Optional<Patient> updateOnePatient=patientRepo.findById(patient.getPatientId());
        if(updateOnePatient.isPresent()){
             
            Patient existingPatient=updateOnePatient.get();
            existingPatient.setFirstName(patient.getFirstName());
            existingPatient.setLastName(patient.getLastName());
            existingPatient.setDateOfBirth(patient.getDateOfBirth());

            patientRepo.save(patient);
            return "updated successfull";

        }
        else{
            return "no id found";
        }
    }

    public List<Patient> displayAllPatient(){

        return patientRepo.findAll();
    }
}
