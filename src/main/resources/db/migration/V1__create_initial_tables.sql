
CREATE SEQUENCE IF NOT EXISTS college_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS class_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS student_seq START WITH 1 INCREMENT BY 50;


CREATE TABLE IF NOT EXISTS college (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cnpj VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE IF NOT EXISTS class (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    course VARCHAR(255) NOT NULL,
    faculdade INT,
    CONSTRAINT fk_class_college FOREIGN KEY (faculdade) REFERENCES college(id)
);


CREATE TABLE IF NOT EXISTS student (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    matricula INT NOT NULL UNIQUE,
    college VARCHAR(255) NOT NULL,
    course VARCHAR(255) NOT NULL,
    class INT,
    CONSTRAINT fk_student_class FOREIGN KEY (class) REFERENCES class(id)
);


CREATE TABLE IF NOT EXISTS roles (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS student_roles (
    student_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (student_id, role_id),
    CONSTRAINT fk_student_roles_student FOREIGN KEY (student_id) REFERENCES student(id),
    CONSTRAINT fk_student_roles_role FOREIGN KEY (role_id) REFERENCES roles(id)
);
