package Kelly.Clinic.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Kelly.Clinic.AppointmentDTO.AppointmentDto;
import Kelly.Clinic.model.Appointment;
import Kelly.Clinic.model.Doctor;
import Kelly.Clinic.model.Patient;

@Repository 
public interface AppointmentRepository extends JpaRepository<Appointment,UUID> {
   Optional<Appointment> findByAppointmentDate(Date appointmentDate);
   Optional<Appointment> findByReason(String reason);
   Optional<Appointment> findByStatus(String status);
   Optional<Appointment> findByPatient(Patient patient);
   Optional<Appointment> findByDoctor(Doctor doctor);
   Optional<Appointment>findByPatient_PatientId(UUID patientId);
   Optional<Appointment>findByDoctor_DoctorId(UUID doctorId);
   List<Appointment>findByDoctor_DoctorIdAndStatusOrderByAppointmentDateAsc(UUID doctorId,String status);

@Query("""
   SELECT new Kelly.Clinic.AppointmentDTO.AppointmentDto(
      CONCAT(p.firstName, ' ', p.lastName),
      CONCAT(d.firstName, ' ', d.lastName),
      a.appointmentDate

   )

   FROM Appointment a
   JOIN a.patient p
   JOIN a.doctor d
   WHERE a.status='pending'
   AND a.appointmentDate < CURRENT_DATE
   ORDER BY a.appointmentDate
      
      """) 
      List<AppointmentDto>findByPendingAppointment();

     
boolean existsByDoctor_DoctorIdAndAppointmentDateAndStatusNotAndAppointmentIdNot(
        UUID doctorId,
        Date appointmentDate,
        String status,
        UUID appointmentId
);

}
