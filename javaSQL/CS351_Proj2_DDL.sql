.mode columns 
.headers on 
.nullvalue NULL 
PRAGMA foreign_keys = ON; 

CREATE TABLE Student(
studentID INTEGER UNIQUE NOT NULL, --unique id required
studentName TEXT NOT NULL, --all students have names
major TEXT, --majors can be undeclared
class TEXT CHECK(class in('Freshman','Sophomore','Junior','Senior')), --must be one of these
gpa REAL CHECK(0.0 <= GPA <= 4.0), --must be between these
PRIMARY KEY(studentID),
FOREIGN KEY(major) REFERENCES dept(deptID)
	ON UPDATE CASCADE
	ON DELETE CASCADE
);

CREATE TABLE Course(
course_Number INTEGER NOT NULL, --course numbers are not unique 
deptID TEXT NOT NULL, --all courses have a department
courseName TEXT NOT NULL,--course must have a name
location TEXT,--courses may not have a location if online
meetDay TEXT CHECK(meetDay in('M','T','W','TR','F','MW','TTR')),--must be one of these
meetTime TEXT CHECK( '07:00' <= meetTime <= '17:00'),--must be between these. will this work?
PRIMARY KEY(course_Number, deptID),
FOREIGN KEY(deptID) REFERENCES dept(deptID)
	ON UPDATE CASCADE
	ON DELETE CASCADE
);

CREATE TABLE Dept(
deptID TEXT UNIQUE CHECK(length(deptID) <= 4), --unique id <4 chars
name TEXT UNIQUE NOT NULL, --needs a unique name
building TEXT NOT NULL, --a department must be in a building
PRIMARY KEY(deptID)
);

CREATE TABLE Enroll(
course_Number INTEGER NOT NULL,--cant enroll in a null course
deptID TEXT NOT NULL, -- cant enroll in a null department
studentID INTEGER NOT NULL, --cant enroll null student 
PRIMARY KEY(course_Number, deptID, studentID),
FOREIGN KEY(course_Number,deptID) REFERENCES Course(course_Number,deptID),
FOREIGN KEY(studentID) REFERENCES Student(studentID)
	ON UPDATE CASCADE
	ON DELETE CASCADE
);

CREATE TABLE Faculty(
facultyID INTEGER UNIQUE, --unique id 
name TEXT NOT NULL,--faculty must have names
phoneNo TEXT,--allow no phone
email TEXT,--allow no email
salary REAL CHECK(salary > 0),--must have a salary
rank TEXT CHECK(rank in('Assistant Professor','Associate Professor','Professor')),--must be one of these
deptID TEXT NOT NULL,--must have a primary dept.
PRIMARY KEY(facultyID),
FOREIGN KEY(deptID) REFERENCES Dept(deptID)
	ON UPDATE CASCADE
	ON DELETE CASCADE
);

CREATE TABLE SecondaryDepts(
facultyID INTEGER NOT NULL,--cant have null faculty in a dept
deptID TEXT NOT NULL,--cant belong to a null dept
PRIMARY KEY(facultyID, deptID),
FOREIGN KEY(facultyID) REFERENCES Faculty(facultyID),
FOREIGN KEY(deptID) REFERENCES Dept(deptID)
	ON UPDATE CASCADE
	ON DELETE CASCADE
);

CREATE TABLE Teaches(
facultyID INTEGER NOT NULL,--cant have a null teacher
course_Number INTEGER NOT NULL, --cant have a null course number
deptID TEXT NOT NULL,--cant be in the null department
PRIMARY KEY(facultyID, course_Number, deptID),
FOREIGN KEY(course_Number, deptID) REFERENCES Course(course_Number, deptID),
FOREIGN KEY(facultyID) REFERENCES Faculty(facultyID)
	ON UPDATE CASCADE
	ON DELETE CASCADE
);

CREATE TABLE Grants(
awardNo INTEGER UNIQUE NOT NULL, --unique number
awardAmt REAL NOT NULL CHECK(awardAmt > 0),--cant have null or negative awards
balance REAL NOT NULL CHECK(balance > 0),--cant have null or negative balance
awardTitle TEXT NOT NULL, --must have a title
beginDate TEXT NOT NULL, --must have a begin date
endDate TEXT NOT NULL, --must have an end date
PRIMARY KEY(awardNo)
);

CREATE TABLE FacultyGrants(
awardNo INTEGER NOT NULL, --must have an award
facultyID INTEGER NOT NULL,--must have an id
PRIMARY KEY(awardNo, facultyID),
FOREIGN KEY(awardNo) REFERENCES Grants(awardNo),
FOREIGN KEY(facultyID) REFERENCES Faculty(facultyID)
	ON UPDATE CASCADE
	ON DELETE CASCADE
);

CREATE TABLE Coordinators(
coordinatorID INTEGER UNIQUE NOT NULL, --must have a unique id
name TEXT NOT NULL, --must have a name
phoneNo TEXT, --allow no phone
email TEXT, --allow no email
salary NOT NULL CHECK(salary > 0),--must be  >0
building NOT NULL, --cant be in the null building
deptID NOT NULL,-- cant be in the null dept
PRIMARY KEY(coordinatorID),
FOREIGN KEY(deptID) REFERENCES Dept(deptID)
	ON UPDATE CASCADE
	ON DELETE CASCADE
);

CREATE TABLE Advises(
coordinatorID INTEGER NOT NULL,
studentID INTEGER UNIQUE NOT NULL,
PRIMARY KEY(coordinatorID, studentID),
FOREIGN KEY(coordinatorID) REFERENCES Coordinators(coordinatorID),
FOREIGN KEY(studentID) REFERENCES Student(studentID)
	ON UPDATE CASCADE
	ON DELETE CASCADE
);