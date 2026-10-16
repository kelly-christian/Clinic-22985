package Kelly.Clinic.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Kelly.Clinic.model.Prescription;

@Repository 
public interface PrescriptionRepository extends JpaRepository<Prescription,UUID>{

    Optional<Prescription>findByMedicineName(String medicineName);
    Optional<Prescription>findByDosage(String dosage);
    Optional<Prescription>findByDurationDays(int durationDays);
    Optional<Prescription>findByIssuedDate(LocalDate issuedDate);
    List<Prescription>findByAppointment_AppointmentId(UUID appointmentId);
    Boolean existsByAppointment_AppointmentId(UUID appointmentId);
    @Query("""
            SELECT p FROM Prescription p
            JOIN p.appointment a
            JOIN a.patient pt
            WHERE pt.patientId= :patientId
            """)

    List<Prescription>findByPrescriptionBelongsToPatient(@Param ("patientId") UUID patientId);        
}
