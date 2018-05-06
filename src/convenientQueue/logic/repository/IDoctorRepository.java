package convenientQueue.logic.repository;

import convenientQueue.logic.model.Doctor;
import convenientQueue.logic.model.DoctorVisit;

import java.util.List;

public interface IDoctorRepository {
    List<Doctor> getDoctors(int pageNum, int itemsPerPage);
    int getDoctorsCount();
    void calculateDoctorVisits(int userId, List<Integer> doctorIds);
    List<DoctorVisit> getDoctorVisits(int userId, int pageNum, int itemsPerPage);
    int getDoctorsVisitsCount(int userId);
    void removeDoctorsVisits(List<Integer> visitIds);
}
