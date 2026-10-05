package Kelly.Clinic.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Kelly.Clinic.model.Specialization;

@Repository 
public interface SpecializationRepository extends JpaRepository<Specialization,UUID>{

    Optional<Specialization> findBySpecializationName(String specializationName);
    Optional<Specialization> findByDoctor_DoctorId(UUID doctorId);

}
