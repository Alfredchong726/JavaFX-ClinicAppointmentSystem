package AppointmentData;

public class AppointmentData {
    private String PatientId;
    private String PatientName;
    private String DoctorName;
    private String BookingDay;
    private String BookingTime;

    public AppointmentData(String PatientId, String PatientName, String DoctorName, String BookingDay, String BookingTime) {
        this.PatientId = PatientId;
        this.PatientName = PatientName;
        this.DoctorName = DoctorName;
        this.BookingDay = BookingDay;
        this.BookingTime = BookingTime;
    }

    public String getPatientId() {
        return PatientId;
    }

    public String getPatientName() {
        return PatientName;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public String getBookingDay() {
        return BookingDay;
    }

    public String getBookingTime() {
        return BookingTime;
    }
    
    // @Override
    // public String toString() {
    //     return PatientId + " " + PatientName + " " + DoctorName + " " + BookingDay + " " + BookingTime;
    // }
}
