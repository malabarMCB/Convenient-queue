package convenientQueue.logic.repository;

import convenientQueue.logic.model.Doctor;
import convenientQueue.logic.model.DoctorVisit;

import java.util.List;

public interface IDoctorRepository {
    List<Doctor> GetDoctors();
    List<DoctorVisit> CalculateDoctorVisits();
    int AddDoctorVisits(int userId, List<DoctorVisit> visits);
    List<DoctorVisit> GetDoctorVisits(int userId);
    void RemoveVisits(List<Integer> visitIds );
}
