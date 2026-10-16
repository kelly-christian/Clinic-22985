package Kelly.Clinic.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.sql.Date;
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
import Kelly.Clinic.repository.PrescriptionRepository;
import tools.jackson.databind.deser.jdk.UUIDDeserializer;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.ZoneId;

@Service 
public class AppointmentService {
   @Autowired 
   private AppointmentRepository appointmentRepo;
   @Autowired 
   private PatientRepository patientRepo;
   @Autowired 
   private DoctorRepository doctorRepo;
   @Autowired 
   private PrescriptionRepository prescriptionRepo;

   public String saveAppointment(Appointment appointment,UUID doctorId,UUID patientId)
   {
        LocalDate today = LocalDate.now();

        if (appointment.getAppointmentDate() ==null) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Appointment date cannot be in the past"
        );
       }

        LocalDate appointmentDay =
        ((java.sql.Date) appointment.getAppointmentDate()).toLocalDate();

    if (appointmentDay.isBefore(today)) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Appointment date cannot be in the past"
        );
    }


    Optional<Patient> getPatient=patientRepo.findById(patientId);
    Optional<Doctor> getDoctor=doctorRepo.findById(doctorId);
     
    if(getPatient.isPresent() && getDoctor.isPresent() ){

           appointment.setPatient(getPatient.get());
           appointment.setDoctor(getDoctor.get());
           appointment.setStatus("SCHEDULED");
           appointmentRepo.save(appointment);
           return "appo saved successfull";

    }
     else{
            return " ids ot foud";

     }

   }
   public String deleteAppointment(UUID appointmentId){
      
       Optional<Appointment>appo=appointmentRepo.findById(appointmentId);
       if(appo.isEmpty()){

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"appointment Id not found");
       }

              Appointment appoO=appo.get();
              Doctor doctorO=appoO.getDoctor();
              Patient patientO=appoO.getPatient();

              if(doctorO!=null || patientO!=null){
                  
                     doctorO.setAppointment(null);
                     patientO.setAppointment(null);
                     doctorRepo.save(doctorO);
                     patientRepo.save(patientO);
                     
              }
             
              Boolean hasPrescription=prescriptionRepo.existsByAppointment_AppointmentId(appointmentId);
              
              if(hasPrescription){

                throw new ResponseStatusException(HttpStatus.CONFLICT,"the appointment has prescription first remove prescription");
              }
              
              appointmentRepo.deleteById(appointmentId);
              return "appo deleted successfull";

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


  public String rescheduleAppointment(UUID appointmentId, Date newDate) {

    Appointment appointment = appointmentRepo.findById(appointmentId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "The appointment with that id does not exist"
        ));

   
    if ("COMPLETED".equalsIgnoreCase(appointment.getStatus())) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "A COMPLETED appointment cannot be rescheduled"
        );
    }

    
    if ("CANCELLED".equalsIgnoreCase(appointment.getStatus())) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "A CANCELLED appointment cannot be rescheduled"
        );
    }

    // 3. Reject null or past dates
    if (newDate == null ||
        newDate.toLocalDate().isBefore(LocalDate.now())) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Appointment date cannot be in the past"
        );
    }

    
    boolean doctorIsBooked =
        appointmentRepo
            .existsByDoctor_DoctorIdAndAppointmentDateAndStatusNotAndAppointmentIdNot(
                appointment.getDoctor().getDoctorId(),
                newDate,
                "CANCELLED",
                appointmentId
            );

    if (doctorIsBooked) {
        throw new ResponseStatusException(
            HttpStatus.CONFLICT,
            "Doctor is already booked on that date"
        );
    }

   
    appointment.setAppointmentDate(newDate);
    appointment.setStatus("SCHEDULED");

    appointmentRepo.save(appointment);

    return "Appointment rescheduled successfully";
}

}
