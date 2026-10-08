package entities;

import java.util.Scanner;

public class Patient {

	private String patient_name;
	private int age;
	private String gender;
	private String disease;
	private String admission_type;
	private String condition_status;
	private int triage_score;
	private String doctor_name;
	private long mobile;
	
	private static final Scanner sc = new Scanner(System.in);

	public Patient() { }

	public Patient(String patient_name, int age, String gender, String disease, String admission_type,
			String condition_status, int triage_score, String doctor_name, long mobile, String transfer_status) {
		this.patient_name = patient_name;
		this.age = age;
		this.gender = gender;
		this.disease = disease;
		this.admission_type = admission_type;
		this.condition_status = condition_status;
		this.triage_score = triage_score;
		this.doctor_name = doctor_name;
		this.mobile = mobile;
	}
	
	public void acceptProperties() {
		System.out.println("=======Enter Patients Details=======");
	    System.out.println("Enter Patient Name: ");
	    this.setPatient_name(sc.nextLine());

	    System.out.println("Enter Patient Age: ");
	    this.setAge(sc.nextInt());

	    sc.nextLine();

	    int input;

	    do {

	        System.out.println("Enter Choose for Patient Gender: ");
	        System.out.println("1. Male");
	        System.out.println("2. Female");
	        System.out.println("3. Other");

	        input = sc.nextInt();

	        switch (input) {

	        case 1:
	            this.setGender("Male");
	            break;

	        case 2:
	            this.setGender("Female");
	            break;

	        case 3:
	            this.setGender("Other");
	            break;

	        default:
	            System.out.println("Enter Correct Choice For Gender");
	            break;
	        }

	    } while (!(input >= 1 && input <= 3));


	    sc.nextLine();

	    System.out.println("Enter Patient Disease: ");
	    this.setDisease(sc.nextLine());

	    int admissType;

	    do {

	        System.out.println("Enter Patient Admission Type: ");
	        System.out.println("1. Emergency");
	        System.out.println("2. Regular");

	        admissType = sc.nextInt();

	        switch (admissType) {

	        case 1:
	            this.setAdmission_type("Emergency");
	            break;

	        case 2:
	            this.setAdmission_type("Regular");
	            break;

	        default:
	            System.out.println(
	                "Enter Correct Choice For Admission Type"
	            );
	            break;
	        }

	    } while (!(admissType >= 1 && admissType <= 2));


	    int condition;

	    do {

	        System.out.println("Enter Condition Status");
	        System.out.println("1. Critical");
	        System.out.println("2. Moderate");
	        System.out.println("3. Stable");

	        condition = sc.nextInt();

	        switch (condition) {

	        case 1:
	            this.setCondition_status("Critical");
	            break;

	        case 2:
	            this.setCondition_status("Moderate");
	            break;

	        case 3:
	            this.setCondition_status("Stable");
	            break;

	        default:
	            System.out.println(
	                "Enter Correct Choice For Condition Status"
	            );
	            break;
	        }

	    } while (!(condition >= 1 && condition <= 3));

	    int t_score;

	    do {

	        System.out.println("Enter Triage Score (1-10): ");

	        t_score = sc.nextInt();

	        if (t_score >= 1) {

	            this.setTriage_score(t_score);
	            break;
	        }

	        System.out.println("Triage Score must be between 1 and 10");

	    } while (true);


	    sc.nextLine();

	    System.out.println("Enter Doctor Name: ");
	    this.setDoctor_name(sc.nextLine());
	    
	    System.out.println("Enter Mobile Number: ");
	    this.setMobile(sc.nextLong());
	}


	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getAdmission_type() {
		return admission_type;
	}

	public void setAdmission_type(String admission_type) {
		this.admission_type = admission_type;
	}

	public String getCondition_status() {
		return condition_status;
	}

	public void setCondition_status(String condition_status) {
		this.condition_status = condition_status;
	}

	public int getTriage_score() {
		return triage_score;
	}

	public void setTriage_score(int triage_score) {
		this.triage_score = triage_score;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}


	@Override
	public String toString() {
		return "Patient [patient_name=" + patient_name + ", age=" + age + ", gender="
				+ gender + ", disease=" + disease + ", admission_type=" + admission_type + ", condition_status="
				+ condition_status + ", triage_score=" + triage_score + ", doctor_name=" + doctor_name + ", mobile="
				+ mobile + "]";
	}
	
	

}
