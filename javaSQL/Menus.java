package university;

import java.sql.*;
import java.util.Scanner;

public class Menus {
	static Scanner input=new Scanner(System.in);
	public static void mainText(){//prints main menu options
		System.out.println("Enter: 1.'EXIT'  2.'STU'  3.'FAC'  4.SQL query  5.'MORE' -for more detail about options");
	}
	public static void mainExtended(){//prints main menu option descriptions
		System.out.println("Enter 'EXIT' to exit the program");
		System.out.println("Enter 'STU' for the Student Menu");
		System.out.println("Enter 'FAC' for the Faculty Menu");
		System.out.println("You may also enter a customized SQL query:");
		System.out.println("However, this may only be a SELECT query, not a DDl,INSERT or UPDATE query");
	}
	public static void studentText(){//prints student menu options
		System.out.println("Enter: 1.'EXIT'  2.'ADD'  3.'REMOVE'  4.'FIND  5.'ENROLL'  6.'WITHDRAW'  7.'SCHEDULE'  8.'MORE' -for more detail about options");
	}
	public static void studentExtended(){//prints student menu option descriptions
		System.out.println("Enter 'EXIT' to exit to the Main Menu");
		System.out.println("Enter 'ADD' followed by: studentID, studentName, major, class, and GPA to add a student record");
		System.out.println("Enter 'REMOVE' followed by a studentID to remove a student");
		System.out.println("Enter 'FIND' followed by a studentID or studentName to pull up student's information");
		System.out.println("Enter 'ENROLL' followed by a studentID, deptID and course_Number to enroll a student");
		System.out.println("Enter 'WITHDRAW' followed by a studentID, deptID and course_Number to withdraw a student in a course");
		System.out.println("Enter 'SCHEDULE' followed by a studentID or studentName to list all courses student is enrolled in.");
	}
	public static void facultyText(){//prints faculty menu options
		System.out.println("Enter: 1.'EXIT'  2.'ADD'  3.'REMOVE'  4.'FIND'  5.'SCHEDULE'  6.'GRANTS'  7.'MORE' -for more detail about options");
	}
	public static void facultyExtended(){//prints faculty menu option descriptions
		System.out.println("Enter 'EXIT' to exit to the Main Menu");
		System.out.println("Enter 'ADD' followed by: faculty ID, name, major, class, and GPA to add a student record");
		System.out.println("Enter 'REMOVE' followed by a faculty ID to remove a student");
		System.out.println("Enter 'FIND' followed by a faculty ID or name to pull up student's information");
		System.out.println("Enter 'SCHEDULE' followed by a faculty ID or name to list all courses person is teaching");
		System.out.println("Enter 'GRANTS' followed by a faculty ID or name to list all grant information");
	}
	public static int mainQuery(String x) {//returns a number for the main menu switch stmt
		if (x.equalsIgnoreCase("EXIT") == true) return 1;
		if (x.equalsIgnoreCase("STU") == true) return 2;
		if (x.equalsIgnoreCase("FAC") == true) return 3;
		return 0;
	}
	public static int facultyQuery(String x){//returns a number for the faculty menu switch
		if(x.equalsIgnoreCase("EXIT") == true) return 1;
		if(x.equalsIgnoreCase("ADD") == true) return 2;
		if(x.equalsIgnoreCase("REMOVE") == true) return 3;
		if(x.equalsIgnoreCase("FIND") == true) return 4;
		if(x.equalsIgnoreCase("SCHEDULE") == true) return 5;
		if(x.equalsIgnoreCase("GRANTS") == true) return 6;
		return 0;
	}	
	public static int studentQuery(String x) {//returns a number for the student menu switch
		if(x.equalsIgnoreCase("EXIT") == true) return 1;
		if(x.equalsIgnoreCase("ADD") == true) return 2;
		if(x.equalsIgnoreCase("REMOVE") == true) return 3;
		if(x.equalsIgnoreCase("FIND") == true) return 4;
		if(x.equalsIgnoreCase("ENROLL") == true) return 5;
		if(x.equalsIgnoreCase("WITHDRAW") == true) return 6;
		if(x.equalsIgnoreCase("SCHEDULE") == true) return 7;
		return 0;
	}
	public static void mainMenu(Statement stmt){//main menu
		mainText();// display main menu text
		String x= input.nextLine();//get the user input
		String[] args = x.split(" ");//split input into arguments
		if(x.length() == 3 || x.length() == 4){//all valid commands are length 3 or 4 (except select)
			if(x.equalsIgnoreCase("MORE") == true){// if more info is needed, display more info
				mainExtended();
			}
			else{
				int next= mainQuery(x); //gets numerical value for the command
				switch(next){
					case 1:// exit
						return; 
					case 2:// go to student menu
						studentMenu(stmt); 
						break;
					case 3:// go to faculty menu
						facultyMenu(stmt);
						break;
					default: // did not match anything
						System.out.println("Invalid input");
						break;
				}
			}
		}
		else if(args[0].equalsIgnoreCase("select")){// deal with query
			long time = System.nanoTime();//get start time
			ResultSet rs;
			try{
				int from;
				if(args[1].charAt(0) != '*'){//not a select all stmt
					for(from = 1; !args[from].equalsIgnoreCase("from"); from++);//find the from clause
					rs = stmt.executeQuery(x);
					while (rs.next()) {//iterate through tuples
						for(int i=1; i<from; i++){//print the current tuple up to the from clause
							System.out.printf(rs.getString(args[i].replaceFirst(",","")) + " ");
						}
						System.out.println();	
					}
				}
				else{//select all stmt
					rs = stmt.executeQuery(x);
					ResultSetMetaData rsmd = rs.getMetaData();//get metadata for the query
					int columnCount = rsmd.getColumnCount();//find number of attributes
					String[] name = new String[columnCount];
//					// The column count starts from 1 (or so says guy on stackOverflow...)
					for (int i = 1; i < columnCount + 1; i++ ) {//pass column names to string array
					  name[i-1] = rsmd.getColumnName(i);
					}
					while (rs.next()) {//iterate through tuples
						for(int i=1; i<=columnCount; i++){//print the current tuple
							System.out.printf(rs.getString(i) + " ");
						}
						System.out.println();	
					}
				}
				rs.close(); // close result set
			}
			catch(SQLException e){
				System.err.println(e);
			}
			System.out.println("elapsed time: " + (System.nanoTime() - time) + " nanoseconds");//get execution time
		}
		else{//bad command
			System.out.println("Invalid input");
		}
		mainMenu(stmt);//loop on main menu
	}
	public static void studentMenu(Statement stmt){
		studentText();//display student menu options
		String x= input.nextLine();
		String[] args= x.split(" ");//split input into args
		if(args[0].equalsIgnoreCase("MORE") == true){//display student menu option text
			studentExtended();
		}
		else{// deal with student menu
			int next= studentQuery(args[0]); // deal with x
			ResultSet rs;
			switch(next){
				case 1://EXIT
					mainMenu(stmt); // go back to main menu
					break;
				case 2://ADD
					try{
						if(args.length== 1)//empty command. prompt user for all fields
							stmt.executeUpdate(Commands.addStudent());
						else if(args.length >= 6){//needs 6 args, or more if the name is multiple words.
							String recombine = "";
							for(int i = 1; i<args.length-4; i++){// reassembles multi word names
								recombine.concat(args[i] + ' ');					
							}
							recombine = recombine.concat(args[args.length-4]);
							try{
								stmt.executeUpdate(Commands.addStudent(Integer.parseInt(args[1]), recombine, 
										args[args.length-3], args[args.length-2], Float.parseFloat(args[args.length-1])));//executes the add stmt
							}
							catch(NumberFormatException e){
								System.err.println(e);
							}
						}
						else
							System.out.println("Insufficient arguments. Add student failed.");
					}
					catch(SQLException e){
						System.err.println(e);
					}
					break;
				case 3://REMOVE
					try{
						if(args.length==1)//empty stmt. prompt user for all fields
							stmt.executeUpdate(Commands.removeStudent());
						else if(args.length == 2){//if user supplies an integer. 
							try{
								stmt.executeUpdate(Commands.removeStudent(Integer.parseInt(args[1])));
							}
							catch(NumberFormatException e){
								System.err.println(e);
							}
						}
						else
							System.out.println("Too many arguments. Remove student failed.");
					}
					catch(SQLException e){
						System.err.println(e);
					}		
					break;
				case 4://FIND					
					try{
						if(args.length ==1) //empty find. prompt user for fields
							 rs = stmt.executeQuery(Commands.findStudent());
						else if('0'<=args[1].charAt(0) && args[1].charAt(0) <='9' ) //integer argument
							try{
								rs =  stmt.executeQuery(Commands.findStudent(Integer.parseInt(args[1])));
							}
							catch(NumberFormatException e){//arg was not an integer and just started with 0-9
								System.err.println(e);
								rs = stmt.executeQuery(Commands.findStudent(-235326367));//lazy way to skip adding error checking. I doubt there will ever be sid:-235326367, and names dont start with numbers.
							}
						else{//string argument
							String recombine= "";
							for(int i = 1; i<args.length -1; i++){// reassembles multi word names
								recombine.concat(args[i] + ' ');					
							}
							recombine = recombine.concat(args[args.length-1]);
							rs = stmt.executeQuery(Commands.findStudent(recombine));
						}
						while (rs.next()) {//print each tuple
							System.out.println(rs.getString("studentID") +' '+ rs.getString("studentName") +' '+ rs.getString("major") +' '+rs.getString("class") +' '+rs.getString("gpa") );
						}
						rs.close(); // close result set
					}
					catch(SQLException e){
						System.err.println(e);
					}
					break;
				case 5://ENROLL
					try{
						if(args.length ==1) //empty stmt, ask user
							stmt.executeUpdate(Commands.enrollStudent());
						else if(args.length == 4){//requires exactly 3 arguments to enroll
							try{
								stmt.executeUpdate(Commands.enrollStudent(Integer.parseInt(args[1]),
										args[2], Integer.parseInt(args[3])));
							}
							catch(NumberFormatException e){//not an int
								System.err.println(e);
							}
						}
						else 
							System.out.println("Enroll failed. 3 arguments required.");
					}
					catch(SQLException e){
						System.err.println(e);
					}
					break;
				case 6://WITHDRAW
					try{
						if(args.length ==1)//empty stmt, prompt user
							stmt.executeUpdate(Commands.withdrawStudent());
						else if(args.length == 4){//requires 3 args to withdraw
							try{
								stmt.executeUpdate(Commands.withdrawStudent(Integer.parseInt(args[1]),
										args[2], Integer.parseInt(args[3])));
							}
							catch(NumberFormatException e){//bad int
								System.err.println(e);
							}
						}
						else 
							System.out.println("Withdraw failed. 3 arguments required.");
					}
					catch(SQLException e){
						System.err.println(e);
					}
					break;
				case 7://SCHEDULE
					try{
						if(args.length==1)//empty stmt, prompt user.
							rs = stmt.executeQuery(Commands.scheduleStudent());
						else if('0'<=args[1].charAt(0) && args[1].charAt(0) <='9' ){//integer argument
							try{
								rs = stmt.executeQuery(Commands.scheduleStudent(Integer.parseInt(args[1])));
							}
							catch(NumberFormatException e){
								System.err.println(e);
								rs = stmt.executeQuery(Commands.findStudent(-257478));//lazy avoiding a null check later
							}
						}
						else{//string argument
							String recombine= "";
							for(int i = 1; i<args.length -1; i++){// reassembles multi word names
								recombine.concat(args[i] + ' ');					
							}
							recombine = recombine.concat(args[args.length-1]);
							rs = stmt.executeQuery(Commands.scheduleStudent(recombine));
						}
						while (rs.next()) {//print tuples
							System.out.println(rs.getString("studentID") +' '+ rs.getString("deptID") +' '+ rs.getString("course_Number")+' '+ rs.getString("courseName")+' '+ rs.getString("location")+' '+ rs.getString("meetDay")+' '+ rs.getString("meetTime"));
						}
						rs.close(); // close result set
					}
					catch(SQLException e){
						System.err.println(e);
					}
					break;
				default:
					System.out.println("Invalid input");
					
			}
		}
		studentMenu(stmt);//loop this menu
	}
	public static void facultyMenu(Statement stmt){
		facultyText();//display faculty menu options
		String x= input.nextLine();
		String[] args= x.split(" ");//split into args
		if(args[0].equalsIgnoreCase("MORE") == true){//display faculty menu option descriptions
			facultyExtended();
		}
		else{// deal with faculty menu
			int next= facultyQuery(args[0]);//get command value
			ResultSet rs;
			switch(next){
				case 1://EXIT
					mainMenu(stmt); // go back to main menu
					break;
				case 2://ADD
					try{
						if(args.length== 1)//empty stmt. ask user
							stmt.executeUpdate(Commands.addFaculty());
						else if(args.length >= 8){//requires at least 8 args, more if the name is multiple words and/or the rank is 2 words
							String rank;
							String name = "";
							int rankPos;
							//rank is either one or two words
							if('0' <= args[args.length-3].charAt(0) && args[args.length-3].charAt(0) <=9){//rank is 2 words
								rankPos = 2;
								rank = args[args.length-3].concat(args[args.length-2]);//recombine rank
							}
							else{//rank is 1 word
								rankPos = 1;
								rank = args[args.length-2];//pass to rank anyways to simplify function
							}
								
							for(int i = 2; i<args.length-5-rankPos; i++){// reassembles multi word names
								name.concat(args[i] + ' ');					
							}
							name = name.concat(args[args.length-5-rankPos]);
							try{
								stmt.executeUpdate(Commands.addFaculty(Integer.parseInt(args[1]), name, args[args.length-4-rankPos], 
									args[args.length-3-rankPos], Float.parseFloat(args[args.length-2-rankPos]), rank, args[args.length-1]));
							}
							catch(NumberFormatException e){//bad int
								System.err.println(e);
							}
						}
						else
							System.out.println("Insufficient arguments. Add student failed.");
					}
					catch(SQLException e){
						System.err.println(e);
					}
					break;
				case 3://REMOVE
					try{
						if(args.length==1)//empty stmt. ask user.
							stmt.executeUpdate(Commands.removeFaculty());
						else if(args.length == 2){//takes 1 int arg
							try{
								stmt.executeUpdate(Commands.removeFaculty(Integer.parseInt(args[1])));
							}
							catch(NumberFormatException e){//bad int
								System.err.println(e);
							}
						}
						else
							System.out.println("Too many arguments. Remove student failed.");
					}
					catch(SQLException e){
						System.err.println(e);
					}		
					break;
				case 4://FIND
					try{
						if(args.length ==1) //empty stmt ask user
							 rs = stmt.executeQuery(Commands.findFaculty());
						else if('0'<=args[1].charAt(0) && args[1].charAt(0) <='9' ) //int argument
							try{
								rs =  stmt.executeQuery(Commands.findFaculty(Integer.parseInt(args[1])));
							}
							catch(NumberFormatException e){
								System.err.println(e);
								rs = stmt.executeQuery(Commands.findStudent(-999999));//lazy null check avoidance
							}
						else{
							String recombine= "";
							for(int i = 1; i<args.length -1; i++){// reassembles multi word names
								recombine = recombine.concat(args[i] + ' ');					
							}
							recombine = recombine.concat(args[args.length-1]);
							rs = stmt.executeQuery(Commands.findFaculty(recombine));//gets tuples
						}
						while (rs.next()) {//prints tuples
							System.out.println(rs.getString("facultyID") +' '+ rs.getString("name") 
									+' '+ rs.getString("phoneNo") +' '+rs.getString("email") +' '
									+rs.getString("salary")+' '+rs.getString("rank")+' '+rs.getString("deptID"));
						}
						rs.close(); // close result set
					}
					catch(SQLException e){
						System.err.println(e);
					}
					break;
				case 5://SCHEDULE
					try{
						if(args.length ==1) //empty stmt ask user
							 rs = stmt.executeQuery(Commands.scheduleFaculty());
						else if('0'<=args[1].charAt(0) && args[1].charAt(0) <='9' ) //integer argument
							try{
								rs =  stmt.executeQuery(Commands.scheduleFaculty(Integer.parseInt(args[1])));
							}
							catch(NumberFormatException e){
								System.err.println(e);
								rs = stmt.executeQuery(Commands.findStudent(-9999999));//lazy null check avoidance
							}
						else{
							String recombine= "";
							for(int i = 1; i<args.length -1; i++){// reassembles multi word names
								recombine.concat(args[i] + ' ');					
							}
							recombine = recombine.concat(args[args.length-1]);
							rs = stmt.executeQuery(Commands.scheduleFaculty(recombine));//gets tuples
						}	
						while (rs.next()) {//prints tuples
							System.out.println(rs.getString("facultyID") +' '+ rs.getString("deptID")+' '+ rs.getString("course_Number")+' '+ rs.getString("courseName")
									+' '+ rs.getString("location")+' '+ rs.getString("meetDay")+' '+ rs.getString("meetTime"));
						}
						rs.close(); // close result set
					}
					catch(SQLException e){
						System.err.println(e);
					}
					break;
				case 6://GRANTS
					try{
						if(args.length ==1) //empty stmt ask user
							 rs = stmt.executeQuery(Commands.grantsFaculty());
						else if('0'<=args[1].charAt(0) && args[1].charAt(0) <='9' ) //integer argument
							 rs =  stmt.executeQuery(Commands.grantsFaculty(Integer.parseInt(args[1])));
						else{//string argument
							String recombine= "";
							for(int i = 1; i<args.length -1; i++){// reassembles multi word names
								recombine = recombine.concat(args[i] + ' ');	
							}
							recombine = recombine.concat(args[args.length-1]);
							rs = stmt.executeQuery(Commands.grantsFaculty(recombine));//get tuples
						}	
						while (rs.next()) {//print tuples
							System.out.println(rs.getString("facultyID") +' '+ rs.getString("awardNo")+' '+ rs.getString("awardAmt")
									+' '+ rs.getString("balance")+' '+ rs.getString("awardTitle")+' '+ rs.getString("beginDate")+' '+ rs.getString("endDate"));
						}
						rs.close(); // close result set
					}
					catch(SQLException e){
						System.err.println(e);
					}
					break;
				default:
					System.out.println("Invalid input");
					
			}
		}
		facultyMenu(stmt);//loop in this menu
	}
}