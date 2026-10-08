Hospital Patient Triage & Routing System
📌 Project Overview

The **Hospital Patient Triage & Routing System** is a Core Java + JDBC + MySQL console-based application developed as an industrial practical project for **Kiran Academy**.

The system reads patient records from a source table, validates the patient information, applies hospital triage business rules, and routes each valid patient to the appropriate destination table.

### Technology Stack

- **Java** – Core Java
- **JDBC** – Database connectivity
- **MySQL** – Database
- **MySQL Workbench** – Database management and verification
- **Console Application** – Application interface

No frontend, REST API, mobile application, or cloud deployment is required for this project.

---

## 🎯 Project Objective

The main objectives of this project are:

- Read pending patient records using JDBC.
- Validate patient information.
- Apply patient triage and routing rules.
- Route critical patients to `critical_care_patients`.
- Route other valid patients to `general_care_patients`.
- Prevent duplicate patient transfers.
- Use `PreparedStatement` for database operations.
- Implement transaction management using `commit()` and `rollback()`.
- Update the source record only after successful routing.
- Display a complete processing summary in the console.

---

## Project Flow

```text
hospital_patient_intake
          |
          v
   Java JDBC Processor
          |
          v
    Validate Patient
          |
          v
    Apply Triage Rules
       /          \
      /            \
 Critical          General
      |                |
      v                v
critical_care     general_care
_patients         _patients
      \                /
       \              /
        v            v
       Update Source
       as PROCESSED
```

---

# 🗄️ Database Configuration

## Database Name

```text
hospital_database
```

Create the database using:

```sql
CREATE DATABASE hospital_database;
```

Select the database:

```sql
USE hospital_database;
```

## MySQL Configuration
```text
URL      : jdbc:mysql://localhost:3306/hospital_database
Username : root
Password : YOUR_MYSQL_PASSWORD
---

# 📊 Database Tables

The project uses three main tables:

### 1. hospital_patient_intake

This is the **source table** containing patient intake records.

Important columns include:

- `patient_id`
- `patient_name`
- `age`
- `gender`
- `disease`
- `admission_type`
- `condition_status`
- `triage_score`
- `doctor_name`
- `admission_date`
- `mobile`
- `transfer_status`
- `processed_at`
- `created_at`

Only records having:

```text
transfer_status = PENDING
```
are selected for processing.

---

### 2. critical_care_patients

Stores patients who satisfy the critical-care routing rules.

The destination table contains information copied from the source patient, including:

- Patient ID reference
- Patient name
- Age
- Disease
- Admission type
- Condition status
- Triage score
- Doctor name
- Routing timestamp

---

### 3. general_care_patients

Stores valid patients who do not satisfy the critical-care conditions.

The destination table contains:

- Patient ID reference
- Patient name
- Age
- Disease
- Admission type
- Condition status
- Triage score
- Doctor name
- Routing timestamp

---

# 🚨 Business Routing Rules

The routing is based on **patient condition and triage information**, not treatment cost.

| Condition                                             | Destination   |
| ----------------------------------------------------- | ------------- |
| `condition_status = Critical`                         | Critical Care |
| `admission_type = Emergency` AND `triage_score >= 7`  | Critical Care |
| `condition_status = Moderate` AND `triage_score >= 8` | Critical Care |
| All other valid records                               | General Care  |

### Rule Precedence

Critical routing always has priority.

For example:

```text
Condition      = Stable
Admission      = Emergency
Triage Score   = 9
```

Result:

```text
CRITICAL CARE
```

because an Emergency patient with a triage score of 7 or higher must be routed to critical care.

---

# ✅ Validation Rules

Before routing a patient, the application validates:

- Patient name must not be blank and must contain at least 3 characters.
- Age must be between 0 and 120.
- Gender must be `Male`, `Female`, or `Other`.
- Disease must not be blank.
- Admission type must be `Emergency` or `Regular`.
- Condition status must be `Critical`, `Moderate`, or `Stable`.
- Triage score must be between 1 and 10.
- Doctor name must not be blank.
- Admission date must not be a future date.
- Mobile number must contain exactly 10 digits.
- Transfer status must be `PENDING`.
- Duplicate destination transfers must be prevented.

---

# 🔄 Transaction Processing

The application uses JDBC transaction management.

The processing sequence is:

```text
1. Open JDBC Connection
2. Disable Auto Commit
3. Fetch PENDING patients
4. Validate patient
5. Determine destination
6. Insert into destination table
7. Update source record as PROCESSED
8. Commit transaction
```

If an error occurs during the destination insertion or source update:

```text
ROLLBACK
```

is performed.

This ensures that a patient is not marked as processed if the destination insertion fails.

---

# 📁 Project Structure

Java project structure:

```text
src/
└── main/
     └── java
        └── database_connectivity
            ├── ConnectionDb.java
        └── entities
            ├── Patients.java
        └── hospital_patients_service
            ├──PatientService.java
            ├──PatientValidator.java
        └── hospital_patientDao
            ├──PatientDAO.java
            ├──PatientServiceDao.java
        └── hospital_patientDaoImpl
            ├──PatientDaoImpl.java
        └──mainApp
            ├──HospitalRoutingApp.java
```

### Class Responsibilities

#### `HospitalRoutingApp.java`

Main application class responsible for starting the application.

#### `DBConnection.java`

Responsible for establishing the JDBC connection with MySQL.

#### `Patient.java`

POJO/model class representing a patient.

#### `PatientDAO.java`

Responsible for database operations such as:

- Fetching pending patients
- Inserting critical patients
- Inserting general patients
- Updating patient status
- Checking duplicate transfers

#### `PatientRoutingService.java`

Contains the main routing/business logic.

#### `PatientValidator.java`

Validates patient information before processing.

#### `displaySummary Mathod`

Maintains and displays processing statistics.

---

# 🔌 JDBC Requirements

The project uses:

```java
DriverManager
Connection
PreparedStatement
ResultSet
```

Database modifications should use `PreparedStatement`.

Example:

```java
PreparedStatement ps =
    connection.prepareStatement(sql);
```

Transactions should use:

```java
connection.setAutoCommit(false);

connection.commit();

connection.rollback();
```

Try-with-resources used wherever.

---

# ▶️ How to Run the Project

## Step 1: Install Requirements

Install:

- Java JDK
- MySQL Server
- MySQL Workbench
- Eclipse / IntelliJ IDEA / another Java IDE

---

## Step 2: Create Database

Open MySQL Workbench and execute:

```sql
CREATE DATABASE hospital_database;
```

Then:

```sql
USE hospital_database;
```

---

## Step 3: Create Tables

Execute the table creation SQL scripts.

Required tables:

```text
hospital_patient_intake
critical_care_patients
general_care_patients
```

---

## Step 4: Insert Sample Data

Insert the sample patient records into:

```text
hospital_patient_intake
```

At least 20 source patient records should be available for testing.

---

## Step 5: Configure JDBC

Update:

```text
DBConnection.java
```

with your MySQL credentials.

Example:

```java
String url = "jdbc:mysql://localhost:3306/HospitalDB";
String username = "root";
String password = "your_password";
```

---

## Step 6: Run the Application

Run:

```text
HospitalRoutingApp.java
```

The application will:

1. Fetch pending records.
2. Validate each patient.
3. Apply routing rules.
4. Insert the patient into the correct destination table.
5. Update the source status.
6. Commit successful transactions.
7. Roll back failed transactions.
8. Display the processing summary.

---

# 🖥️ Expected Console Output

Example:

```text
KIRAN ACADEMY - HOSPITAL PATIENT ROUTING
----------------------------------------

Patient 101 -> CRITICAL CARE -> SUCCESS
Patient 102 -> GENERAL CARE -> SUCCESS
Patient 103 -> FAILED -> Invalid triage score
Patient 104 -> CRITICAL CARE -> SUCCESS
Patient 105 -> SKIPPED -> Already processed

PROCESSING SUMMARY
----------------------------------------
Total Pending Records : 20
Critical Care         : 7
General Care          : 10
Validation Failed     : 2
Skipped Duplicate     : 1
Successfully Processed: 17
```

---

# 🔍 Database Verification

After running the application, verify the source table:

```sql
SELECT * 
FROM hospital_patient_intake;
```

Check critical patients:

```sql
SELECT * 
FROM critical_care_patients;
```

Check general patients:

```sql
SELECT * 
FROM general_care_patients;
```

Every successfully processed patient should:

- Exist in exactly one destination table.
- Have `transfer_status = 'PROCESSED'`.
- Have a populated `processed_at` value.

---

# 🧪 Test Scenarios

The application should be tested with:

1. Critical condition patient.
2. Emergency patient with triage score ≥ 7.
3. Moderate patient with triage score ≥ 8.
4. Stable regular patient.
5. Invalid triage score.
6. Blank patient name.
7. Invalid age.
8. Invalid admission type.
9. Invalid condition status.
10. Future admission date.
11. Invalid mobile number.
12. Already processed patient.
13. Duplicate destination insertion.
14. Destination insertion failure and rollback.
15. Validation failure followed by successful processing of remaining records.

---

# 🎯 Final Project Goal

```text
SOURCE PATIENT INTAKE
        ↓
VALIDATION
        ↓
TRIAGE DECISION
        ↓
ROUTE TO CORRECT TABLE
        ↓
TRANSACTION COMMIT
        ↓
SOURCE STATUS UPDATE
```

The primary goal is to demonstrate practical knowledge of:

- Core Java
- JDBC
- MySQL
- SQL
- PreparedStatement
- DAO architecture
- Validation
- Business logic
- Transactions
- Commit/Rollback
- Exception handling
- Duplicate prevention

---

## 👨‍💻 Project Type

**Industrial Core Java + JDBC Capstone Project**

**Organization:** Kiran Academy

**Application Type:** Console Application

**Database:** MySQL
