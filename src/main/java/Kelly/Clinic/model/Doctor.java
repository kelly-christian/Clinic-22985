package Kelly.Clinic.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity 
public class Doctor {

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID doctorId;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    @OneToOne 
    @JoinColumn (name="office_id")
	@JsonManagedReference 
    private Office office;

    @ManyToMany
	@JoinTable (
        name = "doctor_specialization",
        joinColumns = @JoinColumn (name="doctor_id"),
        inverseJoinColumns = @JoinColumn (name="specialization_id")
     )
    private List<Specialization> specialization = new ArrayList<>();

    @OneToMany (mappedBy = "doctor")
	@JsonIgnore
    private List<Appointment> appointment;

	public UUID getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(UUID doctorId) {
		this.doctorId = doctorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public List<Specialization> getSpecialization() {
		return specialization;
	}

	public void setSpecialization(List<Specialization> specialization) {
		this.specialization = specialization;
	}

	public List<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(List<Appointment> appointment) {
		this.appointment = appointment;
	}

	
    

}
