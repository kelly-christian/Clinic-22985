package Kelly.Clinic.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Kelly.Clinic.model.Specialization;
import Kelly.Clinic.model.Doctor;

import Kelly.Clinic.repository.SpecializationRepository;
import Kelly.Clinic.repository.DoctorRepository;

@Service 
public class SpecializationService {

    @Autowired 
    private SpecializationRepository specializationRepo;

    @Autowired 
    private DoctorRepository doctorRepo;

    public String saveSpecialization(Specialization specialization){
           specializationRepo.save(specialization);

        return "return speci saved successfull";
    }

    public String updateSpecialization(Specialization specialization){
         Optional<Specialization> speci=specializationRepo.findById(specialization.getSpecializationId());
        if(speci.isPresent()){
            Specialization speciO=speci.get();
            speciO.setSpecializationName(specialization.getSpecializationName());

            specializationRepo.save(speciO);
            return "speci updated success";
        }
       else{
        return "no id found";
       }
    }
    public String deleteSpecialization(UUID specializationId){
    
        Optional<Specialization>specI=specializationRepo.findById(specializationId);

        if(specI.isPresent()) {
            Specialization speci=specI.get();
            List<Doctor> doctor=speci.getDoctor();
            for(Doctor docT:doctor){
                docT.setSpecialization(null);
                doctorRepo.save(docT);
            }
            specializationRepo.deleteById(specializationId);
            return "speci deleted success";
        }
        else{
            return "id not found";
        }
    }

    public List<Specialization>displayAllSpecialization(){
        return specializationRepo.findAll();
    }
}
