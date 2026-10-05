package Kelly.Clinic.controller;

import java.util.List;

import javax.print.attribute.standard.Media;
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

import Kelly.Clinic.model.Patient;
import Kelly.Clinic.service.PatientService;

@RestController 
@RequestMapping (value = "/api/patient")
public class PatientController {

    @Autowired 
    private PatientService patientService;

    @PostMapping (value="/save",consumes =MediaType.APPLICATION_JSON_VALUE,
    produces =MediaType.APPLICATION_JSON_VALUE )

    public ResponseEntity<String> savePatient(@RequestBody Patient patient){
        
        String returnedMessage=patientService.savePatient(patient);
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED);
    }

    @DeleteMapping (value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<String> deletePatient(@RequestBody Patient patient){

        String returnedMessage=patientService.deletePatient(patient.getPatientId());
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED);
    }

    @PostMapping (value="/update",consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<String> updatePatient(@RequestBody Patient patient){
             
        String returnedMessage=patientService.updatePatient(patient);
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED);

    }

    @GetMapping (value="/display")

    public ResponseEntity<List<Patient>> displayAllPatient(){
 
             List<Patient> displayAll=patientService.displayAllPatient();
             return new ResponseEntity<>(displayAll,HttpStatus.CREATED);
        
    }
}
