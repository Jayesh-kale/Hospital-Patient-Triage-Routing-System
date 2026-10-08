package hospital_patientDao;

import java.sql.ResultSet;
import java.util.List;

import entities.Patient;

public interface PatientServiceDao {
	
	public void patientRoutingService();
	
	public boolean isCritical(ResultSet record);
	
	public void insertRecordInDb(Patient patient);
	
	public void displaySummary();

}
