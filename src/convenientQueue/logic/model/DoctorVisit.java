package convenientQueue.logic.model;

import java.util.Date;

public class DoctorVisit {
    private Doctor doctor;
    private Date date;

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
