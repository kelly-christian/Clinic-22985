package Kelly.Clinic.AppointmentDTO;

import java.sql.Date;

public class AppointmentDto {

private String patientName;
private String doctorName;
private Date appointmentDate;

     public AppointmentDto(String patientName,
                           String doctorName,
                           Date appointmentDate ){

            this.patientName=patientName;
            this.doctorName=doctorName;
            this.appointmentDate=appointmentDate;
 }

	 public String getPatientName() {
		 return patientName;
	 }

	 public void setPatientName(String patientName) {
		 this.patientName = patientName;
	 }

	 public String getDoctorName() {
		 return doctorName;
	 }

	 public void setDoctorName(String doctorName) {
		 this.doctorName = doctorName;
	 }

	 public Date getAppointmentDate() {
		 return appointmentDate;
	 }

	 public void setAppointmentDate(Date appointmentDate) {
		 this.appointmentDate = appointmentDate;
	 }



}
