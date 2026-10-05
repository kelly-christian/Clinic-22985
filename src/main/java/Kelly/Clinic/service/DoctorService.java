package Kelly.Clinic.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
              else{

                return "id not foun";
              }  
            
           
        
      }

      public String updateDoctor(Doctor doctor,UUID officeId){
         
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
              doctorRepo.save(existingDoctor);
              return "updated successfull";
             }

            
             return "office ot foud";
           }
            else{

            return "id not found";
           }
           

        
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


