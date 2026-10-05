package Kelly.Clinic.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Kelly.Clinic.model.Doctor;

@Repository 
public interface DoctorRepository extends JpaRepository<Doctor,UUID>{

        Optional<Doctor>  findByFirstName(String firstName) ;
        Optional<Doctor> findByLastName(String lastName);
        Optional<Doctor> findByOffice_OfficeId(UUID officeId);
        List<Doctor> findBySpecialization_SpecializationId(UUID specializationId);
}
