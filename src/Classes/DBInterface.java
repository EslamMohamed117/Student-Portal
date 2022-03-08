/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author raya
 */
public class DBInterface {
    private static Connection con;
    private static String sql;
    private static Statement stmt;
    private static PreparedStatement pstmt;
    private static ResultSet rs;
    final static int STUDENT_TABLE  = 0;   
    final static int STAFF_TABLE    = 1;
    final static int COURSE_TABLE   = 2;
    final static int ACTIVITY_TABLE = 3;
    final static int GRADES_TABLE   = 4;
    final static int ADMINHISTORY_TABLE   = 5;
    
    public DBInterface() {
        try{
            DBInterface.con = DriverManager.getConnection("jdbc:derby://localhost:1527/SWproject", "root","12345");
            stmt = con.createStatement();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * 
     * @param tableType 
     */
    private void selectTable(int tableType){
        switch (tableType) {
            case STUDENT_TABLE:
                sql="select*from Student ";//Page 26
                break;
            case STAFF_TABLE:
                sql="select*from Staff ";//Page 26
                break;
            case COURSE_TABLE:
                sql="select*from Course ";
                break;
            case ACTIVITY_TABLE:
                sql="select*from Activity ";
                break;
            case GRADES_TABLE:
                sql="SELECT*FROM GRADES ";
                break;
            case ADMINHISTORY_TABLE:
                sql="SELECT*From ADMINHISTORY ";
                break;
            default:
                break;
        }
    }
    /**
     * 
     * @param tableType 
     */
    private void InsertIntoTable(int tableType){
         switch (tableType) {
            case STUDENT_TABLE:
                sql="insert into  Student ";//Page 26
                break;
            case STAFF_TABLE:
                sql="insert into  Staff ";//Page 26
                break;
            case COURSE_TABLE:
                sql="insert into Course ";
                break;
            case ACTIVITY_TABLE:
                sql="insert into  Activity ";
                break;
            case GRADES_TABLE:
                sql="insert into GRADES ";
                break;
            case ADMINHISTORY_TABLE:
                sql="insert into ADMINHISTORY ";
                break;
            default:
                break;
        
            }
    }
    /**
     * 
     * @param tableType 
     */    
    private void updateInTable(int tableType){
        switch (tableType) {
            case STUDENT_TABLE:
                sql="Update Student  set FirstName= ? , LastName=? "
                        + ", gender= ?  , phone= ?  , birthDate= ?  ,age= ?"
                        + ", ClassNo = ? , Academicyear= ?, password=?  where ID = ?";//1
                break;
            case STAFF_TABLE:
                sql="Update Staff    set FirstName= ?  , LastName=?"
                        + ", gender= ? , phone= ?  , birthDate =? ,age=?"
                        + ",  salary = ? , coursecode = ?, password=? where ID=?   " ;
                break;
            case COURSE_TABLE:
                sql="Update Course   set NAME = ?,"
                        + " DESCRIPTION = ? WHERE ID = ? ";
                break;
             case ACTIVITY_TABLE:
                sql="Update Activity set NAME = ?, LINK = ?, TYPE = ?,"
                        + " INSTRUCTORID = ?, DATE = ?, COURSEID = ?  WHERE CODE = ? ";
                break;
            case GRADES_TABLE:
                sql="UPDATE GRADES set GRADE = ? WHERE STUDENTID = ? and CourseID = ? ";
                break; 
        }
    }
    /**
     * 
     * @param tableType 
     */    
    private void deleteFromTable(int tableType){
        switch (tableType) {
            case STUDENT_TABLE:
                sql="DELETE FROM Student WHERE  ";//Page 26
                break;
            case STAFF_TABLE:
                sql="DELETE FROM Staff WHERE " ;
                break;
            case COURSE_TABLE:
                sql="DELETE FROM Course WHERE ";
                break;
            case ACTIVITY_TABLE:
                sql="DELETE FROM Activity WHERE ";
                break;
            case GRADES_TABLE:
                sql="DELETE FROM Grades WHERE ";
                break;
        }
    }
    private String IDGenerator(int tableType)throws SQLException{
                switch (tableType) {
            case STUDENT_TABLE:
                pstmt = con.prepareStatement("SELECT MAX(ID+1) FROM STUDENT ");
                break;
            case STAFF_TABLE:
                pstmt = con.prepareStatement("SELECT MAX(ID+1) FROM STAFF ");
                break;
            case COURSE_TABLE:
                pstmt = con.prepareStatement("SELECT MAX(ID+1) FROM Course ");
                break;
            case ACTIVITY_TABLE:
                pstmt = con.prepareStatement("SELECT MAX(CODE+1) FROM ACTIVITY ");
                break;
            case ADMINHISTORY_TABLE:
                pstmt = con.prepareStatement("SELECT MAX(HISTORYID+1) FROM ADMINHISTORY ");
                break;
        }
        rs = pstmt.executeQuery();
        rs.next();
        return rs.getString(1);
    }
    /**
     * 
     * @param ID
     * @param password
     * @return
     */
    public boolean checkUserCredentials(String ID, String password) {//NEW CHANGES
        if(ID.startsWith("3"))
                selectTable(STUDENT_TABLE);
            else
                selectTable(STAFF_TABLE);
        try {
            pstmt = con.prepareStatement(sql + " WHERE ID = ? and PASSWORD = ?");//Changed " , " into " and ". working?
            pstmt.setString(1, ID);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            return rs.next(); 
        } 
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * 
     * @param 
     * g includes the new Course that Student has enrolled into.
     * 
     * @return 
     * true if no Errors Encountered
     * false if The process couldn't be done
     */
    public boolean addGrade(Grades g){
        try {
            selectTable(STUDENT_TABLE);
            pstmt=con.prepareStatement(sql+ "Where ID = ?");
            pstmt.setString(1, g.UserID);
            rs=pstmt.executeQuery();
            if(!rs.next()) return false;//Check if student Exists in Student Data base

            selectTable(COURSE_TABLE);
            pstmt=con.prepareStatement(sql+ "Where ID = ?");
            pstmt.setString(1, g.CourseID);
            rs=pstmt.executeQuery();
            if(!rs.next()) return false;//Check if Course Exists in Course Data base
            
            InsertIntoTable(GRADES_TABLE);
            pstmt=con.prepareStatement(sql+ " Values(?,?,?)");//3 Parameters
            pstmt.setString(1, g.UserID);
            pstmt.setString(2, g.CourseID);
            pstmt.setString(3, g.grade);
            pstmt.executeUpdate();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * 
     * @param staffMember 
     * @return  
     */
    public boolean addStaff(Staff staffMember){
        try {
            staffMember.UserID = IDGenerator(STAFF_TABLE);// NEW CHANGES!
            InsertIntoTable(STAFF_TABLE);// NEW CHANGES!
            addUser(staffMember);
            pstmt.setString(8, staffMember.salary);
            pstmt.setString(9, staffMember.courseID);
            pstmt.executeUpdate();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * 
     * @param 
     * studentMember: includes all Student's Information
     * @return
     * true if no Errors Encountered
     * false if The process couldn't be done
     */
    public boolean addStudent(Student studentMember){
        try {
            studentMember.UserID = IDGenerator(STUDENT_TABLE);// NEW CHANGES!
            InsertIntoTable(STUDENT_TABLE);// NEW CHANGES!
            addUser(studentMember);
            pstmt.setString(8, studentMember.classNo);
            pstmt.setString(9, studentMember.academicYear);
            pstmt.executeUpdate();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * 
     * @param member
     * @throws SQLException 
     */
    private void addUser(User member) throws SQLException{        //CommonData
        pstmt=con.prepareStatement(sql+ " Values(?,?,?,?,?,?,?,?,?,?)");//10 Parameters//'?' 
        // NEW CHANGES!!! NO MORE MEMBER.ID!
        //String userid = IDGenerator(STUDENT_TABLE);
        pstmt.setString(1, member.UserID);
        pstmt.setString(2, member.FirstName);
        pstmt.setString(3, member.LastName );
        pstmt.setString(4, member.gender   );
        pstmt.setString(5, member.phone    );
        pstmt.setString(6, member.birthDate);
        pstmt.setString(7, member.age      );
        pstmt.setString(10,member.password );//move it to 8 in database... later.
    }
    /**
     * 
     * @param course 
     * @return  
     */
    
    public boolean addCourse (Course course) {
        try {
            //course.courseID = IDGenerator(COURSE_TABLE);// NEW CHANGES!
            InsertIntoTable(COURSE_TABLE);// NEW CHANGES!
            pstmt=con.prepareStatement(sql + "  Values(?,?,?)");
            pstmt.setString(1,course.courseID);
            pstmt.setString(2,course.name);
            pstmt.setString(3,course.description);
            pstmt.executeUpdate();
            //System.out.println("Data has been inserted!");
            return true;
        }
        catch(SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
    }
    /**
     * 
     * @param activity 
     * @return  
     */
    public boolean addActivity (Activity activity) {
        
        try {
            activity.activityID = IDGenerator(ACTIVITY_TABLE);// NEW CHANGES!
            InsertIntoTable(ACTIVITY_TABLE);// NEW CHANGES!
            pstmt=con.prepareStatement(sql + "  Values(?,?,?,?,?,?,?)");//7 Parameters
            pstmt.setString(1,activity.activityID);
            pstmt.setString(2,activity.name);
            pstmt.setString(3,activity.link);
            pstmt.setString(4,activity.type);
            pstmt.setString(5,activity.instructorID);
            pstmt.setString(6,activity.date);
            pstmt.setString(7,activity.courseID);
            pstmt.execute();
            return true;
            //System.out.println("Data has been inserted!");
        } 
        catch(SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            //System.out.println(e.toString());
            
    // always remember to close database connections
        } 
    }
    
    public boolean deleteUserFromGrades (String userID){
        deleteFromTable(GRADES_TABLE);
        try{
            pstmt = con.prepareStatement(sql+" StudentID = ?");
            pstmt.setString(1, userID);
            pstmt.executeUpdate();
            return true;
        //System.out.println("Data has been deleted!");//not true!!
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }    
    }
    
    public boolean deleteCourseFromGrade (String courseID){
        deleteFromTable(GRADES_TABLE);
        try{
            pstmt = con.prepareStatement(sql + " CourseID = ?");
            pstmt.setString(1, courseID);
            pstmt.executeUpdate();
            return true;
        //System.out.println("Data has been deleted!");//not true!!
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }    
    }
    
    public boolean deleteGrade (String StudentID,String courseID){
        deleteFromTable(GRADES_TABLE);
        try{
            pstmt = con.prepareStatement(sql + " StudentID = ? and CourseID = ?");
            pstmt.setString(1, StudentID);
            pstmt.setString(2, courseID);
            pstmt.executeUpdate();
            return true;
        //System.out.println("Data has been deleted!");//not true!!
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }    
    }
        
    /**
     * 
     * @param userID
     * @return 
     */
    
    public boolean deleteUser (String userID){
        if (userID.startsWith("3")){
            deleteUserFromGrades(userID);//deletes all grades belonging to this student
            deleteFromTable(STUDENT_TABLE);
        }
        else{
            deleteInstructorFromActivity(userID);//deletes all activities belonging to this instructor
            deleteFromTable(STAFF_TABLE);
        
        }
        try{
        pstmt = con.prepareStatement(sql+" ID = ?");
        pstmt.setString(1, userID);
        pstmt.executeUpdate();
        return true;
        //System.out.println("Data has been deleted!");//not true!!
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }    
    }
    /**
     * 
     * @param courseID
     * @return 
     */
    public boolean deleteCourse (String courseID){
        deleteCourseFromGrade(courseID);//removes from grade all the foreign key belonging to course.
        try {    
            deleteFromTable(COURSE_TABLE);
            pstmt=con.prepareStatement(sql + " ID = ?");
            pstmt.setString(1,courseID);
            pstmt.executeUpdate();
            return true;
            //System.out.println("Data has been deleted!"); //not true!! 
        } 
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * 
     * @param activityID
     * @return 
     */
    public boolean deleteActivity(String activityID){
        deleteFromTable(ACTIVITY_TABLE); 
        try{
            pstmt=con.prepareStatement(sql + " Code = ?");
            pstmt.setString(1, activityID);
            pstmt.executeUpdate();
            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
    }
    
    public boolean deleteInstructorFromActivity(String InstructorID){
        deleteFromTable(ACTIVITY_TABLE); 
        try{
            pstmt=con.prepareStatement(sql + " InstructorID = ?");
            pstmt.setString(1, InstructorID);
            pstmt.executeUpdate();
            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
            
        }
    }
    
    public boolean updateGrade(Grades g ){
        updateInTable(GRADES_TABLE);
        try{
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1, g.grade);
            pstmt.setString(2, g.UserID);
            pstmt.setString(3, g.CourseID);
            pstmt.executeUpdate(); 
            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * 
     * @param std
     * @return 
     * 
     */
    public boolean updateStudent( Student std ){//
        updateInTable(STUDENT_TABLE);
        try{            
            UpdateUser(std);
            pstmt.setString(7, std.classNo);
            pstmt.setString(8, std.academicYear);
            pstmt.executeUpdate(); 
            //System.out.println("Data has been updated!");
            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * 
     * @param stf 
     * @return  
     */
    public boolean updateStaff(Staff stf ){
        updateInTable(STAFF_TABLE);
        try{
            UpdateUser(stf);
            pstmt.setString(7, stf.salary);  // HAS THIS BEEN ADDED TO THE DATA BASE TABLE?!?!
            pstmt.setString(8, stf.courseID );// HAS THIS BEEN ADDED TO THE DATA BASE TABLE?!?!
            pstmt.executeUpdate(); 
            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * 
     * @param usr
     * @throws SQLException 
     */
    private void UpdateUser(User usr) throws SQLException{
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1, usr.FirstName);
        pstmt.setString(2, usr.LastName ); 
        pstmt.setString(3, usr.gender   );
        pstmt.setString(4, usr.phone    );
        pstmt.setString(5, usr.birthDate); 
        pstmt.setString(6, usr.age      );
        pstmt.setString(9, usr.password );
        pstmt.setString(10,usr.UserID   );
    }
    
    public boolean updateCourse( Course c ){
        updateInTable(COURSE_TABLE);
        try{
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1, c.name);
            pstmt.setString(2, c.description);
            pstmt.setString(3, c.courseID);
            pstmt.executeUpdate(); 
            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean updateActivity( Activity a ){
        updateInTable(ACTIVITY_TABLE);
        try{
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1, a.name);
            pstmt.setString(2, a.link);
            pstmt.setString(3, a.type);
            pstmt.setString(4, a.instructorID);
            pstmt.setString(5, a.date);
            pstmt.setString(6, a.courseID);
            pstmt.setString(7, a.activityID);
            pstmt.executeUpdate(); 
            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
        
    /**
     * 
     * @return ALL ACTIVITIES in an Array of Activities
     */
    public Activity[] getActivities(){//
        Activity act[] = new Activity[5];
        selectTable(ACTIVITY_TABLE);
        try {
            pstmt=con.prepareStatement(sql);// 
            rs=pstmt.executeQuery();
            for(int i=0; i<5 && rs.next(); i++){
                act[i]=new Activity(
                rs.getString("Code"),
                rs.getString("Name"),
                rs.getString("Type"),
                rs.getString("Link"),
                rs.getString("InstructorID"),
                rs.getString("Date"),
                rs.getString("CourseID"));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return act;
        }
        return act;
    }
    /**
     * 
     * @param UserID
     * @return ALL Activities belonging to this User in an Array of Activities
     */
    public Activity[] getActivities(String UserID){//replace getActivities with this
        int  k;
        Activity act[] = new Activity[5];
        String CoursesID[] = getCoursesIDUsingStudentID(UserID);
        
        selectTable(ACTIVITY_TABLE);
        try {
            for(int i=0; i<5; i++){
                pstmt=con.prepareStatement(sql + " WHERE CourseID = ?");// 
                pstmt.setString(1, CoursesID[i]);
                rs=pstmt.executeQuery();
                k=0;
                while(rs.next()){
                    act[k++]=new Activity(
                    rs.getString("Code"),
                    rs.getString("Name"),
                    rs.getString("Type"),
                    rs.getString("Link"),
                    rs.getString("InstructorID"),
                    rs.getString("Date"),
                    rs.getString("CourseID")); 
                }
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return act;
        }
        return act;
    }
	public Activity[] getActivitiesUsingCourseID(String courseID){
        Activity act[] = new Activity[10];
        selectTable(ACTIVITY_TABLE);
        try {
            pstmt=con.prepareStatement(sql + " WHERE CourseID = ?");// 
            pstmt.setString(1,courseID);
            rs=pstmt.executeQuery();
            for(int i=0; i<10 && rs.next(); i++){

                    act[i]=new Activity(
                    rs.getString("Code"),
                    rs.getString("Name"),
                    rs.getString("Type"),
                    rs.getString("Link"),
                    rs.getString("InstructorID"),
                    rs.getString("Date"),
                    rs.getString("CourseID")); 
            
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return act;
        }
        return act;
    }	
		
		
    public String[] getCoursesIDUsingStudentID(String UserID) {//Add this as a new method
	String []str = new String[5];
	try {
		selectTable(GRADES_TABLE);
		pstmt = con.prepareStatement(sql + " Where StudentID = ?");
		pstmt.setString(1,UserID);
		rs = pstmt.executeQuery();
		for(int i=0; i<5 && rs.next(); i++)
			str[i] = rs.getString("CourseID");
	} catch (SQLException ex) {
		Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
		return str;
	}
	return str;
    }
    /**
     * 
     * @param UserID
     * @return
     */
    public Course[] getCourses(String UserID) {
        Grades[] g = getGrades(UserID);
        Course c[] = new Course[5];
        selectTable(COURSE_TABLE);
        try {
            for(int i=0; g[i]!=null ; i++){
                pstmt= con.prepareStatement(sql + " Where ID = ?"); //Show Enrolled Courses Only
                pstmt.setString(1, g[i].CourseID);
                rs = pstmt.executeQuery();
                System.out.println(g[i].CourseID);
                rs.next();
                c[i]=new Course(
                g[i].CourseID,
                rs.getString("NAME"),
                rs.getString("Description") 
                );
                
            }
        } 
        catch (SQLException ex) {//Course Wasn't Found in Courses DataBase
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return c;
        }
        return c;
    }
    public Staff[] getInstructorsInfoFromCourseID(String CourseID) {
        selectTable(STAFF_TABLE);
        Staff[] s = new Staff[5];
        try {
            pstmt= con.prepareStatement(sql + " Where CourseCode = ?"); 
            pstmt.setString(1, CourseID);
            rs = pstmt.executeQuery();
            
            for(int i=0; i<5 && rs.next(); i++){
                s[i]=new Staff(CourseID,
                rs.getString("Salary"),
                rs.getString("AGE"), 
                rs.getString("Phone"),
                rs.getString("ID"), 
                rs.getString("FIRSTNAME"), 
                rs.getString("LASTNAME"),
                rs.getString("BIRTHDATE"), 
                rs.getString("GENDER")
                );
            }
        }
        catch (SQLException ex) {//Course Wasn't Found in Courses DataBase
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return s;
        }
        return s;
    }
        public Staff getInstructorsNameFromInstructorID(String InstructorID) {
            selectTable(STAFF_TABLE);
            Staff s=new Staff(InstructorID);
            try {
                pstmt= con.prepareStatement(sql + " Where ID = ?"); 
                pstmt.setString(1, InstructorID);
                rs = pstmt.executeQuery();    
                rs.next();
                s.FirstName = rs.getString("FIRSTNAME"); 
                s.LastName = rs.getString("LASTNAME");
            }
            catch (SQLException ex) {//Course Wasn't Found in Courses DataBase
                Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
                return s;
            }
        return s;
    }
    /**
     * 
     * @return
     */
    public Student[] getStudents(){
        Student s[] = new Student[5];
        try {
            selectTable(STUDENT_TABLE);
            rs = stmt.executeQuery(sql);//
            for(int i=0; i<5 && rs.next(); i++)
                s[i]=new Student(
                rs.getString("ACADEMICYEAR"),
                rs.getString("CLASSNO"),
                rs.getString("AGE"),
                rs.getString("Phone"),
                rs.getString("ID"),
                rs.getString("FIRSTNAME"),
                rs.getString("LASTNAME"),
                rs.getString("BIRTHDATE"),
                rs.getString("GENDER"));   

        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return s;
        }
        return s;
    }   
    /**
     * 
     * @return
     */
    public Staff[] getStaff(){//
        Staff stf[] = new Staff[5];
        selectTable(STAFF_TABLE);
        try {
            rs = stmt.executeQuery(sql);
            for(int i=0; i<5 && rs.next(); i++){
                stf[i]=new Staff(
                rs.getString("CourseCode"),
                rs.getString("Salary"),
                rs.getString("AGE"), 
                rs.getString("Phone"),
                rs.getString("ID"), 
                rs.getString("FIRSTNAME"), 
                rs.getString("LASTNAME"),
                rs.getString("BIRTHDATE"), 
                rs.getString("GENDER")
            );
                if (stf[i].UserID.startsWith("1")){  //Use this code to deny Showing Admin's Data
                    stf[i--]=null;            //deletes object then repeats last step again with a new row.
                }}
            return stf;
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return stf;
        }
        //s.length() returns the length of the array in case there were only 3 rows found
        
    }
    
    public Grades[] getGrades(String UserID) {
        Grades []g = new Grades[5];
        try {
            selectTable(GRADES_TABLE);
            pstmt = con.prepareStatement(sql + " Where StudentID = ? ");
            pstmt.setString(1,UserID);
            rs = pstmt.executeQuery();
            
            for(int i=0; i<5 && rs.next(); i++)
                g[i]=new Grades(UserID, rs.getString("COURSEID"), rs.getString("Grade"));
            return g;
        } catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return g;
        }
    }
    public Course[] getCourses() throws NullPointerException{
        Course c[] = new Course[10];
        selectTable(COURSE_TABLE);
        try {
            pstmt= con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            for(int i=0; i<10 && rs.next(); i++){
                c[i]=new Course(
                rs.getString("ID"),
                rs.getString("NAME"),
                rs.getString("Description") 
                );
            }
        } 
        catch (SQLException ex) {//Course Wasn't Found in Courses DataBase
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return c;
        }
        return c;
    }
    public boolean getCourseInfo(Course c){
        try {
            selectTable(COURSE_TABLE);
            pstmt = con.prepareStatement(sql + " Where ID = ?");
            pstmt.setString(1, c.courseID);
            rs = pstmt.executeQuery();
            rs.next();
            c.name = rs.getString("NAME");
            c.description = rs.getString("DESCRIPTION");
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean getStudentInfo(Student s){//Can be used for searching, requires only ID
        try {
            selectTable(STUDENT_TABLE);
            pstmt = con.prepareStatement(sql + " Where ID = ?");
            pstmt.setString(1, s.UserID);
            rs = pstmt.executeQuery();
            rs.next(); 
            s.academicYear= rs.getString("ACADEMICYEAR");
            s.classNo     = rs.getString("CLASSNO");
            getUserInfo(s);
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean getStaffInfo(Staff stf){//Can be used for searching, requires only ID
        try {
            selectTable(STAFF_TABLE);
            pstmt = con.prepareStatement(sql + " Where ID = ?");
            pstmt.setString(1, stf.UserID);
            rs = pstmt.executeQuery();
            rs.next();
            stf.salary      = rs.getString("Salary");
            stf.courseID    = rs.getString("CourseCode");
            getUserInfo(stf);
            return true;
        }
        catch (SQLException ex) {
            return false;
        }
    }

    private void getUserInfo(User usr) throws SQLException {
        usr.age         = rs.getString("AGE");
        usr.phone       = rs.getString("Phone");
        usr.FirstName   = rs.getString("FIRSTNAME");
        usr.LastName    = rs.getString("LASTNAME");
        usr.birthDate   = rs.getString("BIRTHDATE");
        usr.gender      = rs.getString("GENDER");
        usr.password    = rs.getString("password");
    }
    //public boolean getActivityInfo(){}

    private String[] getCoursesID(String UserID) {
        String []str = new String[5];
        try {
            selectTable(COURSE_TABLE);
            pstmt = con.prepareStatement(sql + " Where ID = ?");
            pstmt.setString(1,UserID);
            rs = pstmt.executeQuery();
            for(int i=0; i<5 && rs.next(); i++)
                str[i]= rs.getString("ID");//was courseID
        } catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return str;
        }
        return str;
    }   
    public boolean newAdminActivity(String adminID, String description){
        try {
            String historyID=IDGenerator(ADMINHISTORY_TABLE);
            InsertIntoTable(ADMINHISTORY_TABLE);
            pstmt=con.prepareStatement(sql + " Values(?,?,?,?)");//4 Parameters
            pstmt.setString(1, historyID);
            pstmt.setString(2, adminID);
            pstmt.setString(3, "Recently");// use Date
            pstmt.setString(4, description);
            pstmt.executeUpdate();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public History[] getAdminHistory() {
        History []h = new History[5];
        selectTable(ADMINHISTORY_TABLE);
        try {
            pstmt=con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            for(int i=0; i<5 && rs.next(); i++){
                h[i] = new History();
                h[i].HistoryID   = rs.getString("HistoryID");
                h[i].adminID     = rs.getString("AdminID");
                h[i].Date        = rs.getString("Date");
                h[i].description = rs.getString("DESCRIPTION");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return h;
        }
        return h;
    }
    public Grades[] getGradesInfoUsingCourseID(String CourseID){
            Grades g[] = new Grades[100];
            try {
            selectTable(GRADES_TABLE);
            pstmt = con.prepareStatement(sql + " Where COURSEID = ?");
            pstmt.setString(1, CourseID);
            rs = pstmt.executeQuery();
            for(int i=0; i<100 && rs.next(); i++){
                g[i]= new Grades(
                rs.getString("StudentID"),
                CourseID,
                rs.getString("grade")
                );
            }
            return g;
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return g;
        }
            
    }
    public boolean getGradesInfo(Grades g){
        try {
            selectTable(GRADES_TABLE);
            pstmt = con.prepareStatement(sql + " Where COURSEID = ? and StudentID = ?");
            pstmt.setString(1, g.CourseID);
            pstmt.setString(2, g.UserID);
            rs = pstmt.executeQuery();
            rs.next();
            g.grade=rs.getString("grade");
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
}

//use SELECT INTO to Archieve Students by moving them to the "Graduated Students Table"
//We have assumed that one course may be teached by 1 or many instructors
//but one instructor can't teach more than one course.
//if we wanted to allow that, we will do a many to many relation between instructor and course
//creating a new table that includes instructor ID and Course ID as foreign keys.