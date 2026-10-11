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


import Kelly.Clinic.model.Office;
import Kelly.Clinic.service.OfficeService;

@RestController 
@RequestMapping(value ="/api/office") 
public class OfficeController {
   
    @Autowired 
    private OfficeService officeService;

    @PostMapping(value="/save",consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> saveOffice(@RequestBody Office office){

             String returnedMessage=officeService.saveOffice(office);
             return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED);

    }
    @DeleteMapping(value="/delete", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<String> deleteOffice(@RequestBody Office office){
         String returnedMessage=  officeService.deleteOffice(office.getOfficeNumber());
      return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED);
    }

    @PostMapping(value="/update",consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    ) 

    public ResponseEntity<String> updateOffice(@RequestBody Office office){

        String returnedMessage=officeService.updateOffice(office);
        return new ResponseEntity<>(returnedMessage,HttpStatus.CREATED);
    }

    @GetMapping(value="/display") 
    
    public ResponseEntity<List<Office>> displayAllOffice(){
 
         List<Office> displayAll=officeService.displayAllOffice();
         return new ResponseEntity<>(displayAll,HttpStatus.CREATED);
    }

    @GetMapping("/free")
    
    public ResponseEntity<List<Office>> getFreeOffices() {
    List<Office> freeOffices = officeService.getFreeOffices();
    return ResponseEntity.ok(freeOffices);

}

 

}
