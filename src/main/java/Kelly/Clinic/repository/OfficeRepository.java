package Kelly.Clinic.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Kelly.Clinic.model.Doctor;
import Kelly.Clinic.model.Office;

@Repository 
public interface OfficeRepository extends JpaRepository<Office,UUID>{
List<Office> findByOfficeName(String officeName);
Optional<Office> findByOfficeNumber(int officeNumber);
Optional<Office> findByOfficeNameAndOfficeNumber(String officeName,int officeNumber);
Optional<Office> findByDoctor(Doctor doctor);
boolean existsByOfficeNumberAndDoctorIsNotNull(int officeNumber);

@Query("SELECT o FROM Office o " +
       "WHERE o.doctor IS NULL " +
       "ORDER BY o.officeNumber ASC")
List<Office> findFreeOffices();
}

