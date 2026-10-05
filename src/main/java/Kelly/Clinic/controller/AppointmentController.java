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

import Kelly.Clinic.AppointmentDTO.AppointmentDto;
import Kelly.Clinic.model.Appointment;
import Kelly.Clinic.service.AppointmentService;

@RestController 
@RequestMapping (value = "/api/appointment")
public class AppointmentController {

    @Autowired 
    private AppointmentService appointmentService;
    @PostMapping (value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<String> saveAppointment(@RequestBody Appointment appointment,@RequestParam UUID doctorId,@RequestParam UUID patientId){
        
        String returnedMessage=appointmentService.saveAppointment(appointment,doctorId,patientId);
        return new ResponseEntity<>(returnedMessage,HttpStatus.OK);
    }

 @DeleteMapping  (value = "/delete",consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity<String> deleteAppointment(@RequestBody Appointment appointment){
        
        String returnedMessage=appointmentService.deleteAppointment(appointment.getAppointmentId());
        return new ResponseEntity<>(returnedMessage,HttpStatus.OK);
    }
    
    @PostMapping (value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> updateAppointment(@RequestBody Appointment appointment,
        @RequestParam UUID doctorId,@RequestParam UUID patientId
    ){

        String returnedMessage=appointmentService.updateAppointment(appointment,doctorId,patientId);

        return new ResponseEntity<>(returnedMessage,HttpStatus.OK);
    }

    @GetMapping (value = "/display")

    public ResponseEntity<List<Appointment>> displayAllAppointment(){
         List<Appointment>displayAll=appointmentService.displayAllAppointment();
         return new ResponseEntity<>(displayAll,HttpStatus.OK);
    }

    @GetMapping (value= "/doctor/status"
    )
    public ResponseEntity<List<Appointment>> findByDoctorAndStatus(@RequestParam UUID doctorId,@RequestParam String status){
        List<Appointment>display=appointmentService.findByDoctorAndStatus(doctorId,status);
        return new ResponseEntity<>(display,HttpStatus.OK);
    }

    @GetMapping ("/pending")

    public List<AppointmentDto> getPendingAppointment(){

        return appointmentService.getPendingAppointment();
    }
}
