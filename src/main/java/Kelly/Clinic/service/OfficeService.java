package Kelly.Clinic.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Kelly.Clinic.model.Doctor;
import Kelly.Clinic.model.Office;
import Kelly.Clinic.repository.DoctorRepository;
import Kelly.Clinic.repository.OfficeRepository;


@Service 
public class OfficeService {
 @Autowired 
 private OfficeRepository officeRepo;
 @Autowired DoctorRepository doctorRepo;

 public String saveOffice(Office office){
    Optional<Office> oneOffice=officeRepo.findByOfficeNumber(office.getOfficeNumber());

  if(oneOffice.isEmpty()){
    
    officeRepo.save(office);
     return "saved successfull";
  }
  else{
    return "office already there";
  }

 }

 public String deleteOffice(int officeNumber){

       Optional<Office>deleteOneOffice=officeRepo.findByOfficeNumber(officeNumber);
       if(deleteOneOffice.isPresent()){
        
               Office officeO=deleteOneOffice.get();
               Doctor doctorO=officeO.getDoctor();
 
                if(doctorO!=null){
                    doctorO.setOffice(null);
                    doctorRepo.save(doctorO);

                }

           officeRepo.deleteById(deleteOneOffice.get().getOfficeId());
           return "deleted successfull";


       }
       else{
        return "we cannot find office";
       }
  
 }

 public String updateOffice(Office office){
    Optional<Office>updateOneOffice=officeRepo.findById(office.getOfficeId());
    if(updateOneOffice.isPresent()){
       
      Office existingOffice=updateOneOffice.get();
      existingOffice.setOfficeName(office.getOfficeName());
      existingOffice.setOfficeNumber(office.getOfficeNumber());

      officeRepo.save(office);
      return "updated successfull";
    }
    else{
      return "id not found";
    }

 }

 public List<Office>displayAllOffice(){
  

  return officeRepo.findAll();
 }

 public List<Office> getFreeOffices() {
    return officeRepo.findFreeOffices();
}


}
