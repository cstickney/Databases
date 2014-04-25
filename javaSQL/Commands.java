package university;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Commands {
	static Scanner scanner = new Scanner(System.in);
	
	public static String addStudent(){//add student with user prompts for empty stmts
		try{
			System.out.println("What is the students ID number?");
			int studentID = scanner.nextInt();
			scanner.nextLine();
			System.out.println("What is the students name?");
			String name = scanner.nextLine();
			System.out.println("What is the students major?");
			String major = scanner.nextLine();
			System.out.println("What class is the student in?");
			String Class = scanner.nextLine();
			System.out.println("What is the students current GPA?");
			float GPA = scanner.nextFloat();	
			scanner.nextLine();
			return "INSERT INTO Student VALUES ('"+studentID+"','"+name+"','"+major+"','"+Class+"','"+GPA+"');";
		}
		catch(InputMismatchException e){//bad int or float
			System.err.println(e);
			scanner.nextLine();
			return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
		}
	}
	public static String addStudent(int studentID, String name, String major, String Class, float GPA){//preformatted add student stmt
		return "INSERT INTO Student VALUES ('"+studentID+"','"+name+"','"+major+"','"+Class+"','"+GPA+"');";
	}
	public static String addFaculty(){//empty add faculty stmt + user prompts
		try{
			System.out.println("What is the faculty's ID?");
			int facultyID = scanner.nextInt();
			scanner.nextLine();
			System.out.println("What is the faculty's name?");
			String name = scanner.nextLine();
			System.out.println("What is their phone number?");
			String phoneNo = scanner.nextLine();
			System.out.println("What is their email address?");
			String email = scanner.nextLine();
			System.out.println("What is their salary?");
			float salary = scanner.nextFloat();
			scanner.nextLine();
			System.out.println("What is their rank?");
			String rank = scanner.nextLine();
			System.out.println("What dept does the faculty belong to?");
			String dept = scanner.nextLine();
			return "INSERT INTO Faculty VALUES ("+facultyID+",'"+name+"','"+phoneNo+"','"+email+"',"+salary+",'"+rank+"','"+dept+"');";	
		}
		catch(InputMismatchException e){//bad int
			System.err.println(e);
			scanner.nextLine();
			return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
		}
	}
	public static String addFaculty(int facultyID,String name, String phoneNo, String email, Float salary, String rank, String dept){//preformatted add faculty stmt
		return "INSERT INTO Faculty VALUES ("+facultyID+",'"+name+"','"+phoneNo+"','"+email+"',"+salary+",'"+rank+"','"+dept+"');";	
	}
	public static String removeStudent(){//empty remove student stmt w/ user prompts
		try{
			System.out.println("what is the students ID?");
			int studentID = scanner.nextInt();
			scanner.nextLine();
			return "DELETE FROM Student WHERE studentID = "+studentID+";";
		}
		catch(InputMismatchException e){//bad int
			System.err.println(e);
			scanner.nextLine();
			return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
		}
	}
	public static String removeStudent(int studentID){//preformatted remove student
		return "DELETE FROM Student WHERE studentID = "+studentID+";";	
	}
	public static String removeFaculty(){//empty remove fac w/ prompts
		try{
			System.out.println("What is the faculty's ID?");
			int facultyID = scanner.nextInt();
			scanner.nextLine();
			return "DELETE FROM Faculty WHERE facultyID = "+facultyID+";";		
		}
		catch(InputMismatchException e){//bad int
			System.err.println(e);
			scanner.nextLine();
			return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
		}	
	}
	public static String removeFaculty(int facultyID){//preformat removefaculty
		return "DELETE FROM Faculty WHERE facultyID = "+facultyID+";";
	}
	public static String findStudent(){//empty findstudent w/ prompts
		System.out.println("Find by name or ID?");
		String x = scanner.nextLine();
		if(x.equalsIgnoreCase("name")){
			System.out.println("What is the name?");
			String studentName = scanner.nextLine();
			return "SELECT * FROM Student WHERE studentName ='"+studentName+"';";
		} else if(x.equalsIgnoreCase("id")) {
			try{
				System.out.println("What is the ID?");
				int studentID = scanner.nextInt();
				scanner.nextLine();//handles \n
				return "SELECT * FROM Student WHERE studentID ="+studentID+";";	
			}
			catch(InputMismatchException e){//bad int
				System.err.println(e);
				return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
			}
		}
		else{
			System.out.println("Invalid Input");
			return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
		}
	}
	public static String findStudent(int studentID){//preformat integer findstudent
		return "SELECT * FROM Student WHERE studentID ="+studentID+";";
	}
	public static String findStudent(String studentName){//preformat string findstudent
		return "SELECT * FROM Student WHERE studentName ='"+studentName+"';";
	}
	public static String findFaculty(){//empty findfaculty w/ prompts
		System.out.println("Would you like to search by ID or Name?");
		String x = scanner.nextLine();
		if(x.equalsIgnoreCase("ID")){
			try{
				System.out.println("What is the ID?");
				int facultyID = scanner.nextInt();
				scanner.nextLine();
				return "SELECT * FROM Faculty WHERE facultyID ="+facultyID+";";
			}
			catch(InputMismatchException e){//bad int
				System.err.println(e);
				return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
			}
		} else if(x.equalsIgnoreCase("name")){
			System.out.println("What is the name?");
			String name = scanner.nextLine();
			return "SELECT * FROM Faculty WHERE name ='"+name+"';";
		} else{
			System.out.println("Invalid Input");
			return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
		}                   
	}
	public static String findFaculty(int facultyID){//preformat int findfaculty
		return "SELECT * FROM Faculty WHERE facultyID ="+facultyID+";";
	}
	public static String findFaculty(String name){//preformat string findfaculty
		return "SELECT * FROM Faculty WHERE name ='"+name+"';";	
	}
	public static String enrollStudent(){//empty enroll with prompts
		try{
			System.out.println("What is the SID number?");
			int sid = scanner.nextInt();
			scanner.nextLine();	
			System.out.println("What department is the course in?");
			String dept =  scanner.nextLine();	
			System.out.println("What is the course number?");
			int number = scanner.nextInt();		
			scanner.nextLine();	
			return "INSERT INTO Enroll VALUES ("+sid+",'"+dept+"',"+number+");";
		}
		catch(InputMismatchException e){//bad int
			System.err.println(e);
			return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
		}
	}
	public static String enrollStudent(int sid, String dept, int number){//preformat enroll
		return "INSERT INTO Enroll VALUES ("+sid+",'"+dept+"',"+number+");";
	}
	public static String withdrawStudent(){//empty withdraw with prompts
		try{
			System.out.println("What is the students ID?");
			int studentID = scanner.nextInt();
			scanner.nextLine();
			System.out.println("What department is the course in?");
			String dept = scanner.nextLine();
			System.out.println("What is the course number?");
			int number = scanner.nextInt();
			scanner.nextLine();
			return "DELETE FROM Enroll WHERE studentID = "+studentID+
					" AND deptID = '"+dept+"' AND course_Number = "+number+";";	
		}
		catch(InputMismatchException e){//bad int
			System.err.println(e);
			return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
		}
	}
	public static String withdrawStudent(int sid, String dept, int number){//preformat withdraw
		return "DELETE FROM Enroll WHERE studentID = "+sid+
				" AND deptID = '"+dept+"' AND course_Number = "+number+";";
	}
	public static String scheduleStudent(){//empty schedulestudent with prompts
		System.out.println("Search schedule by ID or name?");
		String ans = scanner.nextLine();
		if(ans.equalsIgnoreCase("ID")){
			try{
				System.out.println("What is the students ID?");
				int student_ID = scanner.nextInt();
				scanner.nextLine();
				return "SELECT * FROM Enroll NATURAL JOIN Course WHERE studentID = "+student_ID+";";
			}
			catch(InputMismatchException e){//bad int
				System.err.println(e);
				return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
			}
		} else if (ans.equalsIgnoreCase("name")){
			System.out.println("What is the students name?");
			String name = scanner.nextLine();
			return "SELECT studentID, Enroll.deptID, Enroll.course_Number, courseName,location,meetDay, meetTime FROM Enroll NATURAL JOIN Student NATURAL JOIN Course WHERE studentName = '"+name+"';";	
		} else{
			System.out.println("Invalid Input");
			return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
		}
		
	}
	public static String scheduleStudent(int student_ID){//preformat schedulestudent integer
		return "SELECT * FROM Enroll NATURAL JOIN Course WHERE studentID = "+student_ID+";";		
	}
	public static String scheduleStudent(String name){//preformat schedulestudent string
		return "SELECT studentID, Enroll.deptID, Enroll.course_Number, courseName,location,meetDay, meetTime FROM Enroll NATURAL JOIN Student NATURAL JOIN Course WHERE studentName = '"+name+"';";	
	}
	public static String scheduleFaculty(){//empty schedulefaculty with prompts
		System.out.println("Would you like to search schedule by ID or Name?");
		String x = scanner.nextLine();
		if(x.equalsIgnoreCase("ID")){
			try{
				System.out.println("What is the ID?");
				int faculty_ID = scanner.nextInt();
				scanner.nextLine();
				return "SELECT * FROM Teaches WHERE facultyID ="+faculty_ID+";";
			}
			catch(InputMismatchException e){//bad int
				System.err.println(e);
				return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
			}
		} else if(x.equalsIgnoreCase("name")){
			System.out.println("What is the name?");
			String name = scanner.nextLine();
			return "SELECT Teaches.facultyID, Teaches.course_Number, Teaches.deptID, Course.courseName, Course.location, Course.meetDay, "
					+ "Course.meetTime FROM Teaches CROSS JOIN Faculty CROSS JOIN Course WHERE Teaches.facultyID = Faculty.facultyID AND "
					+ "Teaches.course_Number = Course.course_Number AND Teaches.deptID = Course.deptID AND Faculty.name = '"+name+"';";//the longest most needless query in the world
		} else{
			System.out.println("Invalid Input");
			return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
		}	
	}
	public static String scheduleFaculty(int faculty_ID){//preformat schedulefaculty int
		return "SELECT * FROM Teaches WHERE facultyID = "+faculty_ID+";";	
	}
	public static String scheduleFaculty(String name){//preformat schedulefaculty string
		return "SELECT Teaches.facultyID, Teaches.course_Number, Teaches.deptID, Course.courseName, Course.location, Course.meetDay, "
				+ "Course.meetTime FROM Teaches CROSS JOIN Faculty CROSS JOIN Course WHERE Teaches.facultyID = Faculty.facultyID AND "
				+ "Teaches.course_Number = Course.course_Number AND Teaches.deptID = Course.deptID AND Faculty.name = '"+name+"';";//the longest most needless query in the world
	}
	public static String grantsFaculty(){//empty grantsfaculty w prompts
		System.out.println("Would you like to search by ID or Name?");
		String x = scanner.nextLine();
		if(x.equalsIgnoreCase("ID")){
			try{
				System.out.println("What is the ID?");
				int faculty_ID = scanner.nextInt();
				scanner.nextLine();
				return "SELECT * FROM FacultyGrants NATURAL JOIN Grants WHERE facultyID ="+faculty_ID+";";
			}
			catch(InputMismatchException e){//bad int
				System.err.println(e);
				return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
			}
		} else if(x.equalsIgnoreCase("name")){
			System.out.println("What is the name?");
			String name = scanner.nextLine();
			return "SELECT Grants.awardNo, facultyID, awardAmt,balance,awardTitle,beginDate,endDate FROM FacultyGrants NATURAL JOIN Faculty NATURAL JOIN Grants WHERE name = '"+name+"';";
		} else{
			System.out.println("Invalid Input");
			return "SELECT * FROM Student WHERE studentID = -9999999;";	//lazy null check avoidance
		}
	}
	public static String grantsFaculty(int faculty_ID){//preformat grantsfaculty int
		return "SELECT * FROM FacultyGrants NATURAL JOIN Grants WHERE facultyID = "+faculty_ID+";";	
	}
	public static String grantsFaculty(String name){//preformat grantsfaculty string
		return "SELECT Grants.awardNo, facultyID, awardAmt,balance,awardTitle,beginDate,endDate FROM FacultyGrants NATURAL JOIN Faculty NATURAL JOIN Grants WHERE name = '"+name+"';";
	}
}
