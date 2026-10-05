package Kelly.Clinic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity 
public class Specialization {
 @Id 
@GeneratedValue (strategy = GenerationType.UUID)
     private UUID specializationId;

     private String specializationName;

     @ManyToMany(mappedBy = "specialization")
     private List<Doctor> doctor = new ArrayList<>();

	 public UUID getSpecializationId() {
		 return specializationId;
	 }

	 public void setSpecializationId(UUID specializationId) {
		 this.specializationId = specializationId;
	 }

	 public String getSpecializationName() {
		 return specializationName;
	 }

	 public void setSpecializationName(String specializationName) {
		 this.specializationName = specializationName;
	 }

	 public List<Doctor> getDoctor() {
		 return doctor;
	 }

	 public void setDoctor(List<Doctor> doctor) {
		 this.doctor = doctor;
	 }

     
}
