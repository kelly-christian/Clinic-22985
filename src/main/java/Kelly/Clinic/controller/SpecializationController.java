package Kelly.Clinic.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Kelly.Clinic.model.Specialization;
import Kelly.Clinic.service.SpecializationService;

@RestController 
@RequestMapping (value = "/api/specialization")
public class SpecializationController {

    @Autowired 
     private SpecializationService specializationService;

     @PostMapping (value = "/save",consumes =MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
     )

     public ResponseEntity<String> saveSpecialization(@RequestBody Specialization specialization){
         
        String returnedMessage=specializationService.saveSpecialization(specialization);
        return new ResponseEntity<>(returnedMessage,HttpStatus.OK);
}

 @PostMapping (value = "/update",consumes =MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
     )

     public ResponseEntity<String> updateSpecialization(@RequestBody Specialization specialization){
         
        String returnedMessage=specializationService.updateSpecialization(specialization);
        return new ResponseEntity<>(returnedMessage,HttpStatus.OK);
}

@DeleteMapping (value = "/delete",consumes =MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
     )

     public ResponseEntity<String> deleteSpecialization(@RequestBody Specialization specialization){
         
        String returnedMessage=specializationService.deleteSpecialization(specialization.getSpecializationId());
        return new ResponseEntity<>(returnedMessage,HttpStatus.OK);
}

@GetMapping  (value = "/display" )

     public ResponseEntity<List<Specialization>> displayAllSpecialization(){
         
        List<Specialization> returnedMessage=specializationService.displayAllSpecialization();
        return new ResponseEntity<>(returnedMessage,HttpStatus.OK);
}
}