CREATE DATABASE hospital_database;
use hospital_database;
show tables;
CREATE TABLE hospital_patient_intake (
    patient_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    disease VARCHAR(120) NOT NULL,
    admission_type VARCHAR(20) NOT NULL,
    condition_status VARCHAR(20) NOT NULL,
    triage_score INT NOT NULL,
    doctor_name VARCHAR(100) NOT NULL,
    admission_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    mobile VARCHAR(15) NOT NULL,
    transfer_status VARCHAR(20) DEFAULT 'PENDING',
    processed_at DATETIME NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_patient_name
        CHECK (CHAR_LENGTH(TRIM(patient_name)) >= 3),
    CONSTRAINT chk_gender
        CHECK (gender IN ('Male', 'Female', 'Other')),
    CONSTRAINT chk_admission_type
        CHECK (admission_type IN ('Emergency', 'Regular')),
    CONSTRAINT chk_condition_status
        CHECK (condition_status IN ('Critical', 'Moderate', 'Stable'))
);

CREATE TABLE critical_care_patients (
    critical_id INT PRIMARY KEY AUTO_INCREMENT,

    source_patient_id INT NOT NULL UNIQUE,

    patient_name VARCHAR(100) NOT NULL,

    age INT NOT NULL,

    disease VARCHAR(120) NOT NULL,

    admission_type VARCHAR(20) NOT NULL,

    condition_status VARCHAR(20) NOT NULL,

    triage_score INT NOT NULL,

    doctor_name VARCHAR(100) NOT NULL,

    routed_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_critical_source
        FOREIGN KEY (source_patient_id)
        REFERENCES hospital_patient_intake(patient_id)
);
CREATE TABLE general_care_patients (
    general_id INT PRIMARY KEY AUTO_INCREMENT,

    source_patient_id INT NOT NULL UNIQUE,

    patient_name VARCHAR(100) NOT NULL,

    age INT NOT NULL,

    disease VARCHAR(120) NOT NULL,

    admission_type VARCHAR(20) NOT NULL,

    condition_status VARCHAR(20) NOT NULL,

    triage_score INT NOT NULL,

    doctor_name VARCHAR(100) NOT NULL,

    routed_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_general_source
        FOREIGN KEY (source_patient_id)
        REFERENCES hospital_patient_intake(patient_id)
);




