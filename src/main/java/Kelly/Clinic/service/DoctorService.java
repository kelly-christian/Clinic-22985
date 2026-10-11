package Kelly.Clinic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;


import Kelly.Clinic.model.Appointment;
import Kelly.Clinic.model.Doctor;
import Kelly.Clinic.model.Office;
import Kelly.Clinic.model.Specialization;

import Kelly.Clinic.repository.AppointmentRepository;
import Kelly.Clinic.repository.DoctorRepository;
import Kelly.Clinic.repository.OfficeRepository;
import Kelly.Clinic.repository.SpecializationRepository;


@Service 
public class DoctorService {

    @Autowired 
      private DoctorRepository doctorRepo;
    
    @Autowired 
      private OfficeRepository officeRepo;

    @Autowired 
    private AppointmentRepository appoRepo;

    @Autowired 
    private SpecializationRepository specializationRepo;

      public String saveDoctor(Doctor doctor,UUID officeId,List<UUID> specializationIds){
            Optional<Office> officeO=officeRepo.findById(officeId);
              if(officeO.isPresent()){

                doctor.setOffice(officeO.get());
              }
              else{
                return "office not found";
              }

                for(UUID specializationId:specializationIds){
                Optional<Specialization> speci=specializationRepo.findById(specializationId);
                 if(speci.isPresent()){

                  doctor.getSpecialization().add(speci.get());
                 }else{
                  return "speci not found";
                 }
                
              }
              
                doctorRepo.save(doctor);
                 return"saved successfull";
                
      }

      public String updateDoctor(Doctor doctor,UUID officeId,List<UUID>specializationIds){
         //System.out.println("Doctor ID: " + doctor.getDoctorId());
    //System.out.println("Office ID: " + officeId);
    //System.out.println("Specialization IDs: " + specializationIds);

        Optional<Doctor> doctorUpdate=doctorRepo.findById(doctor.getDoctorId());
           if(doctorUpdate.isPresent()){

            Doctor existingDoctor=doctorUpdate.get();
             existingDoctor.setFirstName(doctor.getFirstName());
             existingDoctor.setLastName(doctor.getLastName());
             existingDoctor.setDateOfBirth(doctor.getDateOfBirth());

             Optional<Office> officeOne=officeRepo.findById(officeId);
            if(officeOne.isPresent()){

            Office newOffice=officeOne.get();

            existingDoctor.setOffice(newOffice);
            //doctorRepo.save(existingDoctor);
            //return "updated successfull";
            }else{
                   return "office ot foud";
            }

            

             List<Specialization> newSpecialization=new ArrayList<>();
             for(UUID specializationId:specializationIds){
              Optional<Specialization>speci=specializationRepo.findById(specializationId);
              if(speci.isPresent()){
                newSpecialization.add(speci.get());
              }else{
              return "no speci found";
              }
             }
             existingDoctor.setSpecialization(newSpecialization);
              doctorRepo.save(existingDoctor);
              return "updated successfull ";
           }

            else{

            return "id not found";
           }
           

        
          }

       @Transactional

       public ResponseEntity<String> addSpecializationToDoctor(
        UUID doctorId, UUID specializationId) {

    
       Optional<Doctor> doctorOptional = doctorRepo.findById(doctorId);

      if (doctorOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The doctor with that id does not exist");
                    }

        Doctor doctor = doctorOptional.get();

    // 2. Find the specialization
       Optional<Specialization> specializationOptional =
            specializationRepo.findById(specializationId);

     if (specializationOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("That specialization does not exist");
    }

      Specialization specialization = specializationOptional.get();

    // 3. Check whether the doctor already has this specialization
       boolean alreadyHasSpecialization =
            doctor.getSpecialization().stream()
                    .anyMatch(s -> s.getSpecializationId()
                            .equals(specializationId));

    if (alreadyHasSpecialization) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("The doctor already has that specialization");
    }

    // 4. Add the specialization without removing existing ones
    doctor.getSpecialization().add(specialization);

    // 5. Save the updated doctor
    doctorRepo.save(doctor);

    // 6. Return success
    return ResponseEntity.ok(
            "Specialization added to the doctor");
}

@Transactional
public ResponseEntity<String> removeSpecializationFromDoctor(
        UUID doctorId, UUID specializationId) {

    // 1. Find the doctor
    Optional<Doctor> doctorOptional =
            doctorRepo.findById(doctorId);

    if (doctorOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The doctor with that id does not exist");
    }

    Doctor doctor = doctorOptional.get();

    // 2. Find the specialization
    Optional<Specialization> specializationOptional =
            specializationRepo.findById(specializationId);

    if (specializationOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("That specialization does not exist");
    }

    Specialization specialization = specializationOptional.get();

    // 3. Check whether the doctor has this specialization
    boolean hasSpecialization =
            doctor.getSpecialization().stream()
                    .anyMatch(s -> s.getSpecializationId()
                            .equals(specializationId));

    if (!hasSpecialization) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("The doctor does not have that specialization");
    }

    // 4. Remove only the relationship
    doctor.getSpecialization().removeIf(
            s -> s.getSpecializationId().equals(specializationId));

    // 5. Save the doctor
    doctorRepo.save(doctor);

    return ResponseEntity.ok(
            "Specialization removed from the doctor");
}


  @Transactional
  public ResponseEntity<String> moveDoctorToOffice(
        UUID doctorId, int officeNumber) {

  
    Optional<Doctor> doctorOptional =
            doctorRepo.findById(doctorId);

    if (doctorOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The doctor with that id does not exist");
    }

    
    Optional<Office> officeOptional =
            officeRepo.findByOfficeNumber(officeNumber);

    if (officeOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("The office with that office number does not exist");
    }

    Doctor doctor = doctorOptional.get();
    Office newOffice = officeOptional.get();

   
    if (officeRepo.existsByOfficeNumberAndDoctorIsNotNull(officeNumber)) {
        if (doctor.getOffice() == null ||
                !doctor.getOffice().getOfficeId()
                        .equals(newOffice.getOfficeId())) {

            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("That office is taken by other doctor");
        }
    }

    
    doctor.setOffice(newOffice);
    doctorRepo.save(doctor);

   
    return ResponseEntity.ok("Office changed successfully");
}



          public String deleteDoctor(UUID doctorId){

            Optional<Doctor> doctorO=doctorRepo.findById(doctorId);
             
            if(doctorO.isPresent()){

                 Doctor doctor=doctorO.get();
                 Office office=doctor.getOffice();
                
                 if(office !=null){
                    office.setDoctor(null);
                    officeRepo.save(office);

                 }
                 List <Appointment> appoO=doctor.getAppointment();
                 for(Appointment appoT:appoO){
                       appoT.setDoctor(null);
                       appoRepo.save(appoT);
                 }

                 doctorRepo.deleteById(doctorId);
                 return "deleted successfull";
            }
            else{
                 return "id not fount";
            }

            
          }

          public List<Doctor> displayAllDoctor(){

            return doctorRepo.findAll();
          }
        }


