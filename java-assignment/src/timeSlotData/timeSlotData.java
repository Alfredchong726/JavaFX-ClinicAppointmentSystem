package timeSlotData;

public class timeSlotData {
    private String DoctorID;
    private String DoctorName;
    private String Days;
    private String Times;
    private String Image;

    public timeSlotData(String DoctorID, String DoctorName, String Days, String Times, String Image) {
        this.DoctorID = DoctorID;
        this.DoctorName = DoctorName;
        this.Days = Days;
        this.Times = Times;
        this.Image = Image;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public String getDays() {
        return Days;
    }

    public String getTimes() {
        return Times;
    }
    
    public String getImage() {
        return Image;
    }
    
    // @Override
    // public String toString() {
    //     return Days + " " + Times;
    // }
}
