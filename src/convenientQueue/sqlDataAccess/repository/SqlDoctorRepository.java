package convenientQueue.sqlDataAccess.repository;

import convenientQueue.logic.model.Doctor;
import convenientQueue.logic.model.DoctorVisit;
import convenientQueue.logic.repository.IDoctorRepository;
import convenientQueue.sqlDataAccess.SqlQueryExecutor;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SqlDoctorRepository implements IDoctorRepository {
    private final String connectionString;

    public SqlDoctorRepository(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public List<Doctor> getDoctors(int pageNum, int itemsPerPage) {
        List<Doctor> result = new ArrayList<>();
        SqlQueryExecutor.executePreparedStatement(connectionString, "GetDoctors (?, ?)",
                (preparedStatement)-> {
                    preparedStatement.setInt(1, pageNum);
                    preparedStatement.setInt(2, itemsPerPage);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()){
                        Doctor doctor = new Doctor();

                        doctor.setId(resultSet.getInt("Id"));
                        doctor.setName(resultSet.getString("Name"));
                        doctor.setSurname(resultSet.getString("Surname"));
                        doctor.setSpecialization(resultSet.getString("Specialization"));

                        result.add(doctor);
                    }
                });

        return result;
    }

    @Override
    public int getDoctorsCount() {
        AtomicInteger result = new AtomicInteger();
        SqlQueryExecutor.executePreparedStatement(connectionString, "GetDoctorsCount",
                (preparedStatement)-> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next())
                        result.set(resultSet.getInt(1));
                });

        return result.get();
    }

    @Override
    public void calculateDoctorVisits(int userId, List<Integer> doctorIds) {
        StringBuilder args = new StringBuilder();
        args.append(" ");
        doctorIds.forEach(id-> args.append(" ").append(id).append(" "));
        args.append(" ");
        SqlQueryExecutor.executePreparedStatement(connectionString, "CalculateDoctorVisits (?, ?)",
                (preparedStatement -> {
                    preparedStatement.setInt(1, userId);
                    preparedStatement.setString(2, args.toString());
                    preparedStatement.executeUpdate();
                }));
    }

    @Override
    public List<DoctorVisit> getDoctorVisits(int userId, int pageNum, int itemsPerPage) {
        List<DoctorVisit> result = new ArrayList<>();
        SqlQueryExecutor.executePreparedStatement(connectionString, "GetDoctorsVisits (?, ?, ?)",
                (preparedStatement)-> {
                    preparedStatement.setInt(1, userId);
                    preparedStatement.setInt(2, pageNum);
                    preparedStatement.setInt(3, itemsPerPage);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    while (resultSet.next()){
                        DoctorVisit visit = new DoctorVisit();
                        Doctor doctor = new Doctor();
                        visit.setDoctor(doctor);

                        visit.setId(resultSet.getInt("Id"));
                        Timestamp timestamp = resultSet.getTimestamp("Time");
                        if(timestamp != null)
                            visit.setDate(new Date(timestamp.getTime()));
                        doctor.setId(resultSet.getInt("DoctorId"));
                        doctor.setName(resultSet.getString("Name"));
                        doctor.setSurname(resultSet.getString("Surname"));
                        doctor.setSpecialization(resultSet.getString("Specialization"));

                        result.add(visit);
                    }
                });

        return result;
    }

    @Override
    public int getDoctorsVisitsCount(int userId) {
        AtomicInteger result = new AtomicInteger();
        SqlQueryExecutor.executePreparedStatement(connectionString, "GetDoctorsVisitsCount (?)",
                (preparedStatement)-> {
                    preparedStatement.setInt(1, userId);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next())
                        result.set(resultSet.getInt(1));
                });

        return result.get();
    }

    @Override
    public void removeDoctorsVisits(List<Integer> visitIds) {
        StringBuilder args = new StringBuilder();
        args.append(" ");
        visitIds.forEach(id-> args.append(" ").append(id).append(" "));
        args.append(" ");
        SqlQueryExecutor.executePreparedStatement(connectionString, "RemoveDoctorsVisits (?)",
                (preparedStatement -> {
                    preparedStatement.setString(1, args.toString());
                    preparedStatement.executeUpdate();
                }));
    }
}
