CREATE TABLE Student_module
(
  M_Code varchar(50),
  S_No int,
  Marks int,
 CONSTRAINT fr_student_module_student FOREIGN KEY (S_No) REFERENCES Student(S_No),
  CONSTRAINT fr_student_module_module FOREIGN KEY (M_Code) REFERENCES Module(M_Code),
  PRIMARY KEY (M_Code,S_No)
);
commit;
