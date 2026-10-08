package hospital_patient_Services;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientValidator {

	public List<String> validatePatient(ResultSet rs) {

        List<String> errors = new ArrayList<String>();

        try {
            String patientName = rs.getString("patient_name");

            if (patientName == null || patientName.trim().isEmpty()) {
                errors.add("Patient name is blank");
            } else if (patientName.trim().length() < 3) {
                errors.add("Patient name must contain at least 3 characters");
            }

            int age = rs.getInt("age");

            if (rs.wasNull()) {
                errors.add("Age is mandatory");
            } else if (age < 0 || age > 120) {
                errors.add("Invalid age");
            }

            String gender = rs.getString("gender");

            if (gender == null ||
                    !(gender.equals("Male")
                    || gender.equals("Female")
                    || gender.equals("Other"))) {

                errors.add("Invalid gender");
            }

            String disease = rs.getString("disease");

            if (disease == null || disease.trim().isEmpty()) {
                errors.add("Disease cannot be blank");
            }

            String admissionType =
                    rs.getString("admission_type");

            if (admissionType == null ||
                    !(admissionType.equals("Emergency")
                    || admissionType.equals("Regular"))) {

                errors.add("Invalid admission type");
            }

            String conditionStatus =
                    rs.getString("condition_status");

            if (conditionStatus == null ||
                    !(conditionStatus.equals("Critical")
                    || conditionStatus.equals("Moderate")
                    || conditionStatus.equals("Stable"))) {

                errors.add("Invalid condition status");
            }

            int triageScore = rs.getInt("triage_score");

            if (rs.wasNull()) {
                errors.add("Triage score is mandatory");
            } else if (triageScore < 1 || triageScore > 10) {
                errors.add("Invalid triage score");
            }

            String doctorName = rs.getString("doctor_name");

            if (doctorName == null ||
                    doctorName.trim().isEmpty()) {

                errors.add("Doctor name cannot be blank");
            }

            Date admissionDate = rs.getDate("admission_date");

            if (admissionDate == null) {

                errors.add("Admission date is mandatory");

            } else if (admissionDate.toLocalDate().isAfter(LocalDate.now())) {

                errors.add("Admission date cannot be a future date");
            }

            String mobile = rs.getString("mobile");

            if (mobile == null || !mobile.matches("[0-9]{10}")) {

                errors.add("Mobile must contain exactly 10 digits");
            }

            String transferStatus = rs.getString("transfer_status");

            if (transferStatus.equals("null")) {

                errors.add("transferStatus is null");
            }

        } catch (Exception e) {
            errors.add("Validation error: " + e.getMessage());
        }

        return errors;
    }
}
