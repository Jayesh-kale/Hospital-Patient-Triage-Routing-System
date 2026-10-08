package hospital_patientDaoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database_connectivity.ConnectionDb;
import entities.Patient;
import hospital_patientDao.PatientDAO;

public class PatientDaoImpl implements PatientDAO {

	private static final String INSERT_QUERY = "INSERT INTO hospital_patient_intake(patient_name, age, gender, disease, admission_type,condition_status, triage_score, doctor_name, mobile) VALUES(?,?,?,?,?,?,?,?,?)";

	private static final String QUERY = "SELECT * FROM hospital_patient_intake WHERE transfer_status = ?";

	private static final String GET_QUERY = "SELECT * FROM hospital_patient_intake";

	private static final String INSERT_CRITICAL_RECORD_QUERY = "INSERT INTO critical_care_patients (source_patient_id, patient_name, age, disease, admission_type, condition_status, triage_score, doctor_name) VALUES(?,?,?,?,?,?,?,?)";

	private static final String INSERT_GENERAL_RECORD_QUERY = "INSERT INTO general_care_patients(source_patient_id, patient_name, age, disease, admission_type, condition_status, triage_score, doctor_name) VALUES(?,?,?,?,?,?,?,?)";

	private static final String UPDATE_PROCESSED_DATE = "UPDATE hospital_patient_intake SET processed_at = CURRENT_TIMESTAMP, transfer_status = 'PROCESSED' WHERE patient_id = ?";

	@Override
	public ResultSet getAllPatientsRecord() {
		try {
			Connection con = ConnectionDb.getConnection();
			Statement st = con.createStatement();
			return st.executeQuery(GET_QUERY);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int getAllPendingPatientsRecordCount() {
	    int count = 0;
	    
	    try (Connection con = ConnectionDb.getConnection();
	         PreparedStatement ps = con.prepareStatement(QUERY)) {
	        
	        ps.setString(1, "PENDING");
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                count++; 
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return count;
	}

	@Override
	public int insertRecord(Patient patient) {

		try (Connection con = ConnectionDb.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT_QUERY)) {

			ps.setString(1, patient.getPatient_name());
			ps.setInt(2, patient.getAge());
			ps.setString(3, patient.getGender());
			ps.setString(4, patient.getDisease());
			ps.setString(5, patient.getAdmission_type());
			ps.setString(6, patient.getCondition_status());
			ps.setInt(7, patient.getTriage_score());
			ps.setString(8, patient.getDoctor_name());
			ps.setLong(9, patient.getMobile());

			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public int insertCriticalPatient(ResultSet record, Connection con) throws SQLException {

		try (PreparedStatement ps = con.prepareStatement(INSERT_CRITICAL_RECORD_QUERY)) {

			ps.setInt(1, record.getInt("patient_id"));
			ps.setString(2, record.getString("patient_name"));
			ps.setInt(3, record.getInt("age"));
			ps.setString(4, record.getString("disease"));
			ps.setString(5, record.getString("admission_type"));
			ps.setString(6, record.getString("condition_status"));
			ps.setInt(7, record.getInt("triage_score"));
			ps.setString(8, record.getString("doctor_name"));

			return ps.executeUpdate();
		}
	}

	@Override
	public int insertGeneralPatient(ResultSet record, Connection con) throws SQLException {

		try (PreparedStatement ps = con.prepareStatement(INSERT_GENERAL_RECORD_QUERY)) {
			ps.setInt(1, record.getInt("patient_id"));
			ps.setString(2, record.getString("patient_name"));
			ps.setInt(3, record.getInt("age"));
			ps.setString(4, record.getString("disease"));
			ps.setString(5, record.getString("admission_type"));
			ps.setString(6, record.getString("condition_status"));
			ps.setInt(7, record.getInt("triage_score"));
			ps.setString(8, record.getString("doctor_name"));

			return ps.executeUpdate();
		}
	}

	@Override
	public void markProcessed(int patientId, Connection con) throws SQLException {

		try (PreparedStatement ps = con.prepareStatement(UPDATE_PROCESSED_DATE)) {

			ps.setInt(1, patientId);

			ps.executeUpdate();
		}
	}

}
