package hospital_patient_Services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import database_connectivity.ConnectionDb;
import entities.Patient;
import hospital_patientDao.PatientDAO;
import hospital_patientDao.PatientServiceDao;

public class PatientService implements PatientServiceDao {

	PatientDAO patientDao = null;
	PatientValidator patientValidator = null;
	
	int totalSuccessfullyProcessedRecord;
	int totalCriticalRecord;
	int totalGeneralRecord;
	int totalValidationFailed;
    int totalSkippedRecords;
    int totalPendingRecords;

	public PatientService(PatientDAO patientDao) {

		this.patientDao = patientDao;
		this.patientValidator = new PatientValidator();
	}

	@Override
	public void insertRecordInDb(Patient patient) {

		try {

			int row_affected = patientDao.insertRecord(patient);

			if (row_affected > 0) {

				System.out.println("Record Inserted Successfully...");

			} else {

				System.out.println("Record Not Inserted. Plz Try Again Later...");
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	@Override
	public void patientRoutingService() {

		ResultSet patientsRecord = null;
		ResultSet patientsPendingRecord = null;
		String transferRecord = null;
		Connection con = null;

		try {

			patientsRecord = patientDao.getAllPatientsRecord();
			totalPendingRecords =  patientDao.getAllPendingPatientsRecordCount();
			
			while (patientsRecord.next()) {
				transferRecord = patientsRecord.getString("transfer_status");
				int patientId = patientsRecord.getInt("patient_id");
				List<String> validationErrors = patientValidator.validatePatient(patientsRecord);

				if (!validationErrors.isEmpty()) {

					System.out.print("Patient " + patientId + " -> FAILED ");

					for (String error : validationErrors) {

						System.out.print(" -> " + error);
					}
					totalValidationFailed++;
					System.out.println();
					continue;
				}

				con = ConnectionDb.getConnection();

				con.setAutoCommit(false);

				try {
					if (transferRecord.equals("PENDING") && isCritical(patientsRecord)) {
						int insertCount = patientDao.insertCriticalPatient(patientsRecord, con);

						if (insertCount > 0) {

							patientDao.markProcessed(patientId, con);
							
							con.commit();
							totalCriticalRecord++;
							System.out.println("Patient " + patientId + " -> CRITICAL CARE -> SUCCESS");

						} else {

							con.rollback();

							System.out.println("Patient " + patientId + " -> CRITICAL CARE -> FAILED");
						}

					} else if (transferRecord.equals("PROCESSED")) {
						totalSkippedRecords++;
						System.out.println("Patient " + patientId + " -> SKIPPED -> Already processed");

					} else {
						int insertCount = patientDao.insertGeneralPatient(patientsRecord, con);
						if (insertCount > 0) {

							patientDao.markProcessed(patientId, con);

							con.commit();
							totalGeneralRecord++;
							System.out.println("Patient " + patientId + " -> GENERAL CARE -> SUCCESS");

						} else {

							con.rollback();

							System.out.println("Patient " + patientId + " -> GENERAL CARE -> FAILED");
						}
					}

				} catch (SQLException e) {

					if (con != null) {

						con.rollback();
					}

					System.out.println("Patient " + patientId + " -> FAILED -> Transaction Rolled Back");

					e.printStackTrace();

				} finally {

					if (con != null) {

						try {

							con.close();

						} catch (SQLException e) {

							e.printStackTrace();
						}

						con = null;
					}
				}
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			if (patientsRecord != null) {

				try {

					patientsRecord.close();
					displaySummary();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean isCritical(ResultSet pendingPatientsRecord) {

		try {

			String conditionStatus = pendingPatientsRecord.getString("condition_status");

			String admissionType = pendingPatientsRecord.getString("admission_type");

			int triageScore = pendingPatientsRecord.getInt("triage_score");

			if (conditionStatus.equals("Critical")) {
				return true;
			}

			if (admissionType.equals("Emergency") && triageScore >= 7) {

				return true;
			}

			if (conditionStatus.equals("Moderate") && triageScore >= 8) {

				return true;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void displaySummary() {
		System.out.println();
		System.out.println("PROCESSING SUMMARY");
		System.out.println("---------------------------------------");
		System.out.println("Total Pending Records :" + totalPendingRecords);
		System.out.println("Critical Care         :" + totalCriticalRecord);
		System.out.println("General Care          :" + totalGeneralRecord);
		System.out.println("Validation Failed     :" + totalValidationFailed);
		System.out.println("Skipped Duplicate     :" + totalSkippedRecords);
		System.out.println("Successfully Processed:" + (totalCriticalRecord+totalGeneralRecord));

	}
}