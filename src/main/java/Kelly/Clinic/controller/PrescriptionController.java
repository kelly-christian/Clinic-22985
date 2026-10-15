package Kelly.Clinic.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Kelly.Clinic.model.Prescription;
import Kelly.Clinic.service.PrescriptionService;

@RestController 
@RequestMapping (value = "api/prescription")
public class PrescriptionController {

    @Autowired 
    private PrescriptionService prescriptionService;

    @PostMapping (value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE )

    public ResponseEntity<String>savePrescription(@RequestBody Prescription prescription,@RequestParam UUID appointmentId){

        String returnedMessage=prescriptionService.savePrescription(prescription, appointmentId);
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED);
    }

    @GetMapping (value = "/appointment/{appointmentId}")

    public ResponseEntity<List<Prescription>>getPrescription(@PathVariable UUID appointmentId){

        List<Prescription>display=prescriptionService.getPrescription(appointmentId);
        return new ResponseEntity<>(display,HttpStatus.OK);
    }

    @GetMapping (value = "/patient/{patientId}")

    public ResponseEntity<List<Prescription>>getPatientPrescription(@PathVariable UUID patientId){
        List<Prescription>display=prescriptionService.getPatientPrescription(patientId);
        return new ResponseEntity<>(display,HttpStatus.OK);
    }
}
