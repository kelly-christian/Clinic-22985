package Kelly.Clinic.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.query.sqm.sql.internal.AsWrappedExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Kelly.Clinic.AppointmentDTO.AppointmentDto;
import Kelly.Clinic.model.Appointment;
import Kelly.Clinic.model.Doctor;
import Kelly.Clinic.model.Patient;
import Kelly.Clinic.repository.AppointmentRepository;
import Kelly.Clinic.repository.DoctorRepository;
import Kelly.Clinic.repository.PatientRepository;
import tools.jackson.databind.deser.jdk.UUIDDeserializer;

@Service 
public class AppointmentService {
   @Autowired 
   private AppointmentRepository appointmentRepo;
   @Autowired 
   private PatientRepository patientRepo;
   @Autowired 
   private DoctorRepository doctorRepo;

   public String saveAppointment(Appointment appointment,UUID doctorId,UUID patientId)
   {
    Optional<Patient> getPatient=patientRepo.findById(patientId);
    Optional<Doctor> getDoctor=doctorRepo.findById(doctorId);
     
    if(getPatient.isPresent() || getDoctor.isPresent() ){

           appointment.setPatient(getPatient.get());
           appointment.setDoctor(getDoctor.get());
           appointmentRepo.save(appointment);
           return "appo saved successfull";

    }
     else{
            return " ids ot foud";

     }

   }
   public String deleteAppointment(UUID appointmentId){
      
       Optional<Appointment>appo=appointmentRepo.findById(appointmentId);
       if(appo.isPresent()){

              Appointment appoO=appo.get();
              Doctor doctorO=appoO.getDoctor();
              Patient patientO=appoO.getPatient();

              if(doctorO!=null || patientO!=null){
                  
                     doctorO.setAppointment(null);
                     patientO.setAppointment(null);
                     doctorRepo.save(doctorO);
                     patientRepo.save(patientO);
                     
              }
              appointmentRepo.deleteById(appointmentId);
              return "appo deleted successfull";

       }
       else{
              return "id not found";
       }

   }

   public String updateAppointment(Appointment appointment,UUID doctorId,UUID patientId){
       Optional<Appointment> updateAppo=appointmentRepo.findById(appointment.getAppointmentId());
     if(updateAppo.isPresent()){
        Appointment appoT=updateAppo.get();
        appoT.setAppointmentDate(appointment.getAppointmentDate());
        appoT.setReason(appointment.getReason());
        appoT.setStatus(appointment.getStatus());


        Optional<Doctor> doctorO=doctorRepo.findById(doctorId);
        Optional<Patient> patientO=patientRepo.findById(patientId);
          
        if(doctorO.isPresent() && patientO.isPresent()){
              Doctor doctor=doctorO.get();
              Patient patient=patientO.get();

              appoT.setDoctor(doctor);
              appoT.setPatient(patient);

              appointmentRepo.save(appoT);
              return "appo updated successfull";
        }
       return " doctor id and patiet id  not found";
     }
             
       return "appo id not found";
   }

   public List<Appointment>displayAllAppointment(){
       return appointmentRepo.findAll();
   }

   public List<Appointment>findByDoctorAndStatus(UUID doctorId,String status){
       return appointmentRepo.findByDoctor_DoctorIdAndStatusOrderByAppointmentDateAsc(doctorId,status);
       
   }

   public List<AppointmentDto>getPendingAppointment(){

       return appointmentRepo.findByPendingAppointment();
   }
    
}
