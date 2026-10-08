package mainApp;

import entities.Patient;
import hospital_patientDao.PatientDAO;
import hospital_patientDao.PatientServiceDao;
import hospital_patientDaoImpl.PatientDaoImpl;
import hospital_patient_Services.PatientService;

public class HospitalRoutingApp {

	public static void main(String[] args) {
		
		PatientDAO patientDao = new PatientDaoImpl();
		PatientServiceDao patientServiceDao = new PatientService(patientDao);
		System.out.println("KIRAN ACADEMY - HOSPITAL PATIENT ROUTING");
		System.out.println();
		System.out.println("----------------------------------------");
		System.out.println();
//		Patient pt = new Patient();
//		pt.acceptProperties();
//		System.out.println(pt);
//		
// 	    patientServiceDao.insertRecordInDb(pt);
	    patientServiceDao.patientRoutingService();

		
		
	}
}
