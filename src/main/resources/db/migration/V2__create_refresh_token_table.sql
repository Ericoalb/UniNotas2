
CREATE TABLE IF NOT EXISTS refresh_token (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,
    student_id INT NOT NULL,
    CONSTRAINT fk_refresh_token_student FOREIGN KEY (student_id) REFERENCES student(id) ON DELETE CASCADE
);
