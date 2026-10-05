package Kelly.Clinic.repository;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Kelly.Clinic.model.Patient;

@Repository 
public interface PatientRepository extends JpaRepository<Patient,UUID>{

    Optional<Patient> findByFirstName(String firstName);
    Optional<Patient> findByLastName(String lastName);
    Optional<Patient> findByDateOfBirth(Date dateOfBirth);
}
