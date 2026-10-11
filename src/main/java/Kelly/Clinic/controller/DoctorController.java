package Kelly.Clinic.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Kelly.Clinic.model.Doctor;
import Kelly.Clinic.model.Office;
import org.springframework.web.bind.annotation.PathVariable;
import Kelly.Clinic.service.DoctorService;

@RestController 
@RequestMapping (value="/api/doctor")
public class DoctorController {
    @Autowired 
      private DoctorService doctorService;

      @PostMapping (value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
      )

      public ResponseEntity<String> saveDoctor(@RequestBody Doctor doctor,@RequestParam UUID officeId,@RequestParam List<UUID> specializationIds){
               
           String returnedMessage=doctorService.saveDoctor(doctor,officeId,specializationIds);
           return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED);

      }

    @PostMapping (value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
    )
   
public ResponseEntity<String> updateDoctor(@RequestBody Doctor doctor,@RequestParam UUID officeId ,@RequestParam List<UUID> specializationIds){
      String returnedMessage=doctorService.updateDoctor(doctor, officeId,specializationIds);
      return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED);
}
    
@PostMapping(
    value = "/addSpecialization",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<String> addSpecializationToDoctor(
        @RequestParam UUID doctorId,
        @RequestParam UUID specializationId) {

    return doctorService.addSpecializationToDoctor(
            doctorId, specializationId);
}

@DeleteMapping("/removeSpecialization")
public ResponseEntity<String> removeSpecializationFromDoctor(
        @RequestParam UUID doctorId,
        @RequestParam UUID specializationId) {

    return doctorService.removeSpecializationFromDoctor(
            doctorId, specializationId);
}


  @DeleteMapping (value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
 public ResponseEntity<String> deleteDoctor(@RequestBody Doctor doctor){

       String returnedMessage=doctorService.deleteDoctor(doctor.getDoctorId());
       return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED);


 }


   @GetMapping (value="/display")

 public ResponseEntity<List<Doctor>> displayAllDctor(){

       List<Doctor> displayAll=doctorService.displayAllDoctor();
       return new ResponseEntity<>(displayAll,HttpStatus.CREATED);
 }
}
