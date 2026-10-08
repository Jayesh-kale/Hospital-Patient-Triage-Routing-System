package hospital_patientDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Patient;

public interface PatientDAO {

	public int getAllPendingPatientsRecordCount();
	
	public ResultSet getAllPatientsRecord();
	
	public int insertRecord(Patient patient);

	public int insertCriticalPatient(ResultSet pendingPatientsRecord, Connection con) throws SQLException;

	public int insertGeneralPatient(ResultSet record, Connection con) throws SQLException;
	
	public void markProcessed(int patientId, Connection con) throws SQLException;

}
