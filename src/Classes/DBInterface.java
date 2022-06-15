/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
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
    
    /*The numbers do not actually matter*/
    final static int STUDENT_TABLE  = 0;   
    final static int USERS_TABLE    = 1;
    final static int COURSE_TABLE   = 2;
    final static int ACTIVITY_TABLE = 3;
    final static int STUDIES_TABLE   = 4;
    final static int ADMIN_HISTORY_TABLE   = 5;
    final static int INSTRUCTOR_TABLE    = 6;
    final static int ADMIN_TABLE    = 7;
    final static int PHONE_TABLE    = 8;


    
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
            case USERS_TABLE:
                sql="select*from Users ";//Page 26
                break;
            case STUDENT_TABLE:
                sql="select*from Student ";//Page 26
                break;
            case ADMIN_TABLE:
                sql="select*from Admin ";//Page 26
                break;
            case INSTRUCTOR_TABLE:
                sql="select*from Instructor ";//Page 26
                break;
            case COURSE_TABLE:
                sql="select*from Course ";
                break;
            case ACTIVITY_TABLE:
                sql="select*from Activity ";
                break;
            case STUDIES_TABLE:
                sql="SELECT*FROM Studies ";
                break;
            case ADMIN_HISTORY_TABLE:
                sql="SELECT*From ADMIN_HISTORY ";
                break;
            case PHONE_TABLE:
                sql="SELECT*From Phone ";
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
            case USERS_TABLE:
                sql="insert into Users VALUES(?,?,?,?,?,?) "; //6 Parameters
                break;
            case STUDENT_TABLE:
                sql="insert into Student VALUES(?,?,?) ";//Page 26
                break;
            case ADMIN_TABLE:
                sql="insert into Admin VALUES(?)";//Page 26
                break;    
            case INSTRUCTOR_TABLE:
                sql="insert into Instructor VALUES(?,?,?)";//Page 26
                break;
            case COURSE_TABLE:
                sql="insert into Course VALUES(?,?,?)";
                break;
            case ACTIVITY_TABLE:
                sql="insert into Activity Values(?,?,?,?,?,?,?)";
                break;
            case STUDIES_TABLE:
                sql="insert into Studies  Values(?,?,?)";
                break;
            case ADMIN_HISTORY_TABLE:
                sql="INSERT INTO Admin_History(Description, ACTIVITY_DATE, AdminID) VALUES (?, ?, ?)";
                break;
            case PHONE_TABLE:
                sql="INSERT INTO Phone(UserID, Phone) VALUES (?, ?)";
                break; 
           
                
        
            }
    }
    /**
     * 
     * @param tableType 
     */    
    private void updateInTable(int tableType){
        switch (tableType) {
            case ACTIVITY_TABLE:
                sql = "UPDATE Activity SET Name = ?, Type = ?, Link = ?,  activity_date  = ? WHERE ActivityID = ? ";
                break;
            case ADMIN_HISTORY_TABLE:
                sql = "UPDATE Admin_History SET Description = ?, activity_date = ? WHERE AdminID = ?";
                break;
            case COURSE_TABLE:
                sql = "UPDATE Course SET Name = ?, Description = ? WHERE CourseID = ?";
                break;
            case INSTRUCTOR_TABLE:
                sql = "UPDATE Instructor SET Salary = ?, CourseID = ? WHERE InstructorID = ?";
                break;
            case PHONE_TABLE:
                sql = "UPDATE Phone SET Phone = ? where USERID = ? AND Phone = ?";//<< ARE YOU SURE OF THIS ?
                break;
            case STUDENT_TABLE:
                sql = "UPDATE Student SET AcadimicYear = ?, ClassNo = ? WHERE StudentID = ?";
                break;
            case STUDIES_TABLE:
                sql = "UPDATE Studies SET Grade = ? WHERE StudentID = ? AND CourseID = ?";
                break;
            case USERS_TABLE:
                sql = "UPDATE Users SET FName = ?, LName = ?, DateOfBirth = ?, Gender = ?, Password = ? WHERE UserID = ?";
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
                sql="DELETE FROM STUDENT WHERE  ";//Page 26
                break;
            case INSTRUCTOR_TABLE:
                sql="DELETE FROM INSTRUCTOR WHERE  ";//Page 26
                break;
            case ADMIN_TABLE:
                sql="DELETE FROM ADMIN WHERE  ";//Page 26
                break;
            case USERS_TABLE:
                sql="DELETE FROM USERS WHERE  ";//Page 26
                break;
            case STUDIES_TABLE:
                sql="DELETE FROM STUDIES WHERE  ";//Page 26
                break;
            case COURSE_TABLE:
                sql="DELETE FROM COURSE WHERE ";
                break;
            case ACTIVITY_TABLE:
                sql="DELETE FROM ACTIVITY WHERE ";
                break;
            case PHONE_TABLE:
                sql="DELETE FROM PHONE WHERE ";
                break;
            
        }
    }
    
    private int CountRecords(String table, String cond) throws SQLException{
        pstmt = con.prepareStatement("SELECT COUNT( * ) FROM " + table +  cond);
        rs = pstmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    
    private String IDGenerator(int tableType)throws SQLException{
        switch (tableType) {
            case STUDENT_TABLE:
               pstmt = con.prepareStatement("SELECT MAX(STUDENTID+1) FROM STUDENT ");
                break;
            case USERS_TABLE:
                pstmt = con.prepareStatement("SELECT MAX(USERID+1) FROM USERS ");
                break;
            case INSTRUCTOR_TABLE:
                pstmt = con.prepareStatement("SELECT MAX(INSTRUCTORID+1) FROM INSTRUCTOR ");
                break;
            case ADMIN_TABLE:
                pstmt = con.prepareStatement("SELECT MAX(ADMINID+1) FROM ADMIN");
                break;
            case ACTIVITY_TABLE:
                pstmt = con.prepareStatement("SELECT COUNT(*) FROM ACTIVITY");
                break;
                
                
        }
        rs = pstmt.executeQuery();
        rs.next();
        return rs.getString(1);
    }
    /**
     * 
     * @param userID
     * @param password
     * @return
     */
public boolean checkUserCredentials(String userID, String password) {
        selectTable(USERS_TABLE);
        try {

            pstmt = con.prepareStatement(sql + " WHERE USERID = ? and PASSWORD = ?");
            pstmt.setInt(1,Integer.parseInt( userID));
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            return rs.next(); 
        } 
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch (NumberFormatException e){
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
            pstmt=con.prepareStatement(sql+ " Where STUDENTID = ?");
            pstmt.setString(1, g.StudentID);
            rs=pstmt.executeQuery();
            if(!rs.next()) return false;//Check if student Exists in Student Data base

            selectTable(COURSE_TABLE);
            pstmt=con.prepareStatement(sql+ " Where COURSEID = ?");
            pstmt.setString(1, g.CourseID);
            rs=pstmt.executeQuery();
            if(!rs.next()) return false;//Check if Course Exists in Course Data base
            
            InsertIntoTable(STUDIES_TABLE);
            pstmt=con.prepareStatement(sql);//3 Parameters
            pstmt.setInt(1,Integer.parseInt( g.StudentID));
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
    public boolean addInstructor(Staff staffMember){
        try {
            String year = String.valueOf(LocalDate.now().getYear());
            
            staffMember.userID = IDGenerator(INSTRUCTOR_TABLE);
            if(staffMember.userID==null || !staffMember.userID.startsWith("2" + year))//Contains null or Newest year User
                staffMember.userID = "2" + year + "0000";
            System.out.println(staffMember.userID);
            addUser(staffMember);
            InsertIntoTable(INSTRUCTOR_TABLE);
            pstmt= con.prepareStatement(sql);
            pstmt.setInt(1,Integer.parseInt(staffMember.salary));
            pstmt.setInt(2,Integer.parseInt(staffMember.userID));
            pstmt.setString(3, staffMember.courseID);
            pstmt.executeUpdate();
            return true;
        }
        catch (SQLException ex) {
            deleteUser(staffMember.userID);
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);  
            return false;
        }
    }
    
    public boolean addAdmin(Staff staffMember){
        try {
            LocalDate date = LocalDate.now();
            String year = String.valueOf(date.getYear());
            
            staffMember.userID = IDGenerator(ADMIN_TABLE);
            if(staffMember.userID==null || !staffMember.userID.startsWith("1" + year))//Contains null or Newest year User
                staffMember.userID = "1" + year + "0000";
            
            addUser(staffMember);
            InsertIntoTable(ADMIN_TABLE);// NEW CHANGES!
            pstmt= con.prepareStatement(sql);
            pstmt.setInt(1,Integer.parseInt(staffMember.userID));
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
            
            LocalDate date = LocalDate.now();
            String year = String.valueOf(date.getYear());
            
            studentMember.userID = IDGenerator(STUDENT_TABLE);
            if(studentMember.userID==null || !studentMember.userID.startsWith("3" + year))//Contains null or Newest year User
                studentMember.userID = "3" + year + "0000";
            addUser(studentMember);
            InsertIntoTable(STUDENT_TABLE);// NEW CHANGES!
            pstmt= con.prepareStatement(sql);
            pstmt.setInt(1,Integer.parseInt(studentMember.academicYear));
            pstmt.setInt(2,Integer.parseInt(studentMember.classNo));
            pstmt.setInt(3,Integer.parseInt(studentMember.userID));
            pstmt.executeUpdate();
            return true;
        }
        catch (SQLException ex) {
            deleteUser(studentMember.userID);
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    /**
     * 
     * @param member
     * @throws SQLException 
     * Adds a tuple into User table.
     */
    private String reverseGender(String gender)
    {
        if(gender.equals("Male")) return "m";
        else                      return "f";
    }
    private void addUser(User member) throws SQLException{
        InsertIntoTable(USERS_TABLE);
        pstmt=con.prepareStatement(sql);
        pstmt.setInt(1,Integer.parseInt(member.userID));
        pstmt.setString(2, member.firstName);
        pstmt.setString(3, member.lastName );
        pstmt.setDate  (4, (Date) member.birthDate);
        pstmt.setString(5, reverseGender(member.gender)   );
        pstmt.setString(6, member.password );
        pstmt.executeUpdate();
        try{
        addPhone(member.phone[0], member.userID);
        addPhone(member.phone[1], member.userID);
        }catch(NullPointerException e){
            System.out.println("User has less than two Phones");
        }
        
        
    }
    /**
     * 
     * @param course 
     * @return  
     */
    public static boolean isPureAscii(String v) {
    return Charset.forName("US-ASCII").newEncoder().canEncode(v);
    }
    public boolean addCourse (Course course) {
        if(course.name.isEmpty() || course.courseID.isEmpty() 
            || !isPureAscii(course.name) || !isPureAscii(course.courseID))
                return false;
        try {
            InsertIntoTable(COURSE_TABLE);
            pstmt=con.prepareStatement(sql);
            
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
     * @return  True if no problem was encountered
     * Used to add a new activity.
     */
    public boolean addActivity (Activity activity) {
        try {
            activity.activityID = IDGenerator(ACTIVITY_TABLE);
            InsertIntoTable(ACTIVITY_TABLE);
            pstmt=con.prepareStatement(sql);//7 Parameters
            pstmt.setInt   (1,Integer.parseInt(activity.activityID));//This line should be removed.
            pstmt.setString(2,activity.name);
            pstmt.setString(3,activity.type);
            pstmt.setString(4,activity.link);
            pstmt.setDate  (5, (Date) activity.date);
            pstmt.setInt   (6,Integer.parseInt(activity.instructorID));
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
    
    public boolean deleteUserFromStudies (String userID){
        deleteFromTable(STUDIES_TABLE);
        try{
            pstmt = con.prepareStatement(sql+" StudentID = ?");
            pstmt.setInt(1,Integer.parseInt(userID));
            return pstmt.executeUpdate() != 0;
        //System.out.println("Data has been deleted!");//not true!!
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch(Exception e){
        return false;
        }
    }
    
    public boolean deleteCourseFromGrade (String courseID){
        deleteFromTable(STUDIES_TABLE);
        try{
            pstmt = con.prepareStatement(sql + " CourseID = ?");
            pstmt.setString(1, courseID);
            return pstmt.executeUpdate() != 0;
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }    
    }
    
    public boolean deleteGrade (String StudentID,String courseID){
        deleteFromTable(STUDIES_TABLE);
        try{
            pstmt = con.prepareStatement(sql + " StudentID = ? AND CourseID = ?");
            pstmt.setInt(1,Integer.parseInt(StudentID));
            pstmt.setString(2, courseID);
            return pstmt.executeUpdate() != 0;
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
    
    public boolean deleteStudent(String userID){
        deleteUserFromStudies(userID);//deletes all grades belonging to this student
        try {
            deleteFromTable(STUDENT_TABLE);
            pstmt= con.prepareStatement(sql + " STUDENTID= ?");
            pstmt.setInt(1, Integer.parseInt(userID));
            if(pstmt.executeUpdate()==0)
                return false;
            return deleteUser(userID);
        } catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean deleteInstructor(String userID){
        deleteInstructorFromActivity(userID);
        deleteFromTable(INSTRUCTOR_TABLE);
        try {
            pstmt= con.prepareStatement(sql + " INSTRUCTORID = ?");
            pstmt.setInt(1, Integer.parseInt(userID));
            if(pstmt.executeUpdate()==0)
                return false;
            return deleteUser(userID);
        } catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean deleteUser (String userID){
        try{
        deletePhone(userID);
        deleteFromTable(USERS_TABLE);
        pstmt= con.prepareStatement(sql + " USERID= ?");
        pstmt.setInt(1, Integer.parseInt(userID));
        return pstmt.executeUpdate() != 0;
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
            pstmt=con.prepareStatement(sql + " CourseID = ?");
            pstmt.setString(1,courseID);
            return pstmt.executeUpdate() != 0 ;
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
            pstmt=con.prepareStatement(sql + " ActivityID = ?");
            pstmt.setInt(1, Integer.parseInt(activityID));
            return pstmt.executeUpdate() != 0;
            
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
            pstmt.setInt(1,Integer.parseInt(InstructorID));
            return pstmt.executeUpdate() != 0;
        }
        catch(SQLException ex){
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
    
    public boolean updateGrade(Grades g){
        Double.parseDouble(g.grade);//new
        updateInTable(STUDIES_TABLE);
        try{
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(2,Integer.parseInt( g.StudentID));
            pstmt.setString(3, g.CourseID);
            pstmt.setInt(1,Integer.parseInt(g.grade));
            pstmt.executeUpdate(); 
            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
    /**
     * 
     * @param std
     * @return 
     * 
     */
    public boolean updateStudent( Student std){//
        try{
            if( std.birthDate.after(Date.valueOf(LocalDate.now())) || std.birthDate.before(Date.valueOf(LocalDate.of(1920, 1, 1)))
            || std.firstName.isEmpty() || std.lastName.isEmpty() || std.gender.isEmpty() || std.classNo.isEmpty() || std.academicYear.isEmpty()
            || !isPureAscii(std.firstName) || !isPureAscii(std.lastName) || !isPureAscii(std.gender))
                return false;            
            UpdateUser(std); 
            updateInTable(STUDENT_TABLE);
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,Integer.parseInt(std.academicYear));
            pstmt.setInt(2,Integer.parseInt(std.classNo));
            pstmt.setInt(3,Integer.parseInt(std.userID));
            pstmt.executeUpdate(); 
            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch(Exception e){
            return false;
        }
    }
    /**
     * 
     * @param stf 
     * @return  
     */
    public boolean updateInstructor(Staff stf){
        try{
            if( stf.birthDate.after(Date.valueOf(LocalDate.now())) || stf.birthDate.before(Date.valueOf(LocalDate.of(1920, 1, 1)))
            || stf.firstName.isEmpty() || stf.lastName.isEmpty() || stf.gender.isEmpty() || stf.salary.isEmpty()
            || !isPureAscii(stf.firstName) || !isPureAscii(stf.lastName) || !isPureAscii(stf.gender))
                return false;
            UpdateUser(stf);
            updateInTable(INSTRUCTOR_TABLE);
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,Integer.parseInt(stf.salary));  
            pstmt.setString(2, stf.courseID );
            pstmt.setInt(3, Integer.parseInt(stf.userID));//Instructor ID  
            pstmt.executeUpdate(); 
            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch(Exception e){
            return false;
        }
    }
    
    public boolean updateAdmin(Staff stf){//Changes in GUI will be required.
        try{
            if( stf.birthDate.after(Date.valueOf(LocalDate.now())) || stf.birthDate.before(Date.valueOf(LocalDate.of(1920, 1, 1)))
            || stf.firstName.isEmpty() || stf.lastName.isEmpty()
            || !isPureAscii(stf.firstName) || !isPureAscii(stf.lastName) || !isPureAscii(stf.gender))
                return false;
            UpdateUser(stf);
            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    /**
     * 
     * @param usr
     * @throws SQLException 
     */
    private void UpdateUser(User usr) throws SQLException{
        updateInTable(USERS_TABLE);
        pstmt=con.prepareStatement(sql);
        pstmt.setString(1, usr.firstName);
        pstmt.setString(2, usr.lastName ); 
        pstmt.setDate  (3, (Date) usr.birthDate); 
        pstmt.setString(4, reverseGender(usr.gender)   );
        System.out.println(usr.password);
        pstmt.setString(5, usr.password );
        pstmt.setInt(6, Integer.parseInt(usr.userID));
        pstmt.executeUpdate(); 
        
        deletePhone(usr.userID);
        try{
        addPhone(usr.phone[0],usr.userID);
        addPhone(usr.phone[1],usr.userID);
        }
        catch(Exception e){}
                
    }
    private void updatePhone(String newPhone[], String userID) throws SQLException{
        deletePhone(userID);
        for (String newPhone1 : newPhone)
            addPhone(newPhone1, userID);
    }
    
    private void addPhone(String newPhone, String userID) throws SQLException{
        if(newPhone != null && !newPhone.isEmpty()){//Don't try to add empty field!
            InsertIntoTable(PHONE_TABLE);       
            pstmt=con.prepareStatement(sql);
            pstmt.setString(2, newPhone);
            pstmt.setInt(1, Integer.parseInt(userID));
            System.out.println("YALAHWY");
            pstmt.executeUpdate();
        }
    }
    
    private void deletePhone(String userID) throws SQLException{
        deleteFromTable(PHONE_TABLE);
        pstmt=con.prepareStatement(sql + "UserID = ?");
        pstmt.setString(1, userID);
        pstmt.executeUpdate();
    }

    public String[] getPhones(String userID){
        String Phones[] = new String[2];
        PreparedStatement pstmt2;
        ResultSet rs2;
        int i=0;
        try {
            pstmt2=con.prepareStatement("Select Phone.Phone from Phone where UserID = ?");
            pstmt2.setInt(1,Integer.parseInt(userID));
            rs2 = pstmt2.executeQuery();
            while(rs2.next()){
                Phones[i++]=String.valueOf(rs2.getInt("Phone"));
            }
            
        }catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Phones; 
    }
    public boolean updateCourse( Course course ){
        if(course.name.isEmpty() || course.courseID.isEmpty() 
            || !isPureAscii(course.name) || !isPureAscii(course.courseID))
                return false;
        try{
            updateInTable(COURSE_TABLE);
            pstmt=con.prepareStatement(sql);
            pstmt.setString(3, course.courseID);
            pstmt.setString(1, course.name);
            pstmt.setString(2, course.description);
            pstmt.executeUpdate(); 
            return true;
        }
        catch(SQLException ex){
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean updateActivity(Activity activity ){
        if(activity.name.isEmpty() || activity.courseID.isEmpty()  || activity.date.after(java.util.Date.from(Instant.now()))
            || !isPureAscii(activity.name) || !isPureAscii(activity.link) || !isPureAscii(activity.type))
                return false;
        try{
            updateInTable(ACTIVITY_TABLE);
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,Integer.parseInt( activity.activityID));
            pstmt.setString(2, activity.name);
            pstmt.setString(3, activity.type);
            pstmt.setString(4, activity.link);
            pstmt.setDate  (5, (Date) activity.date);
            pstmt.setInt(6, Integer.parseInt(activity.instructorID));
            pstmt.setString(7, activity.courseID);
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
        Activity act[] = null;
        try {
            act = new Activity[CountRecords("ACTIVITY","")];
            selectTable(ACTIVITY_TABLE);
            pstmt=con.prepareStatement(sql);// 
            rs=pstmt.executeQuery();
            for(int i=0; i<100 && rs.next(); i++){
                act[i]=new Activity(
                    String.valueOf(rs.getInt("ACTIVITYID")),
                    rs.getString("NAME"),
                    rs.getString("Type"),
                    rs.getString("Link"),
                    String.valueOf(rs.getInt("INSTRUCTORID")),
                    rs.getDate("activity_date"),
                    rs.getString("CourseID"));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return act;
    }
    /**
     * 
     * @param UserID
     * @return ALL Activities belonging to this User in an Array of Activities
     */
    public Activity[] getActivities(String UserID){
        int  k;
        Activity act[] = new Activity[100];
        String CoursesID[] = getCoursesIDUsingStudentID(UserID);
        
        selectTable(ACTIVITY_TABLE);
        try {
            for(int i=0; i<CoursesID.length; i++){
                pstmt=con.prepareStatement(sql + " WHERE CourseID = ?");// 
                pstmt.setString(1, CoursesID[i]);
                rs=pstmt.executeQuery();
                k=0;
                while(rs.next()){
                    act[k++]=new Activity(
                        String.valueOf(rs.getInt("ACTIVITYID")),
                        rs.getString("NAME"),
                        rs.getString("Type"),
                        rs.getString("Link"),
                        String.valueOf(rs.getInt("INSTRUCTORID")),
                        rs.getDate("activity_date"),//Review later
                        rs.getString("CourseID"));
                }
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return act;
    }
    
	public Activity[] getActivitiesUsingCourseID(String courseID){
        Activity act[] = null;
        try {
            act = new Activity[CountRecords("ACTIVITY"," Where CourseID = '"+courseID+"'")];
            
            selectTable(ACTIVITY_TABLE);
            pstmt=con.prepareStatement(sql + " WHERE CourseID = ?");// 
            pstmt.setString(1,courseID);
            rs=pstmt.executeQuery();
            for(int i=0; rs.next(); i++){
                act[i]=new Activity(                    
                    String.valueOf(rs.getInt("ACTIVITYID")),
                    rs.getString("NAME"),
                    rs.getString("Type"),
                    rs.getString("Link"),
                    String.valueOf(rs.getInt("INSTRUCTORID")),
                    rs.getDate("activity_date"),//Review later
                    rs.getString("CourseID"));
            
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return act;
    }	
		
		
    public String[] getCoursesIDUsingStudentID(String UserID) {
	String []str = null;
	try {
                str = new String[CountRecords("STUDIES"," Where StudentID = "+UserID)];
		selectTable(STUDIES_TABLE);
		pstmt = con.prepareStatement(sql + " Where StudentID = ?");
		pstmt.setInt(1,Integer.parseInt(UserID));
		rs = pstmt.executeQuery();
		for(int i=0; i<50 && rs.next(); i++)
			str[i] = rs.getString("CourseID");
	} catch (SQLException ex) {
		Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
	}
	return str;
    }
    /**
     * 
     * @param UserID
     * @return
     */
    public Course[] getCourses(String UserID) {
        Grades g[] = getGrades(UserID);
        Course c[] = new Course[g.length];
        selectTable(COURSE_TABLE);
        try {
            for(int i=0; i<g.length ; i++){ 
                pstmt= con.prepareStatement(sql + " Where CourseID = ?"); //Show Enrolled Courses Only
                pstmt.setString(1, g[i].CourseID);
                rs = pstmt.executeQuery();
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
        }
        return c;
    }
    
    
    public String[] getInstructorsIDFromCourseID(String CourseID){
        selectTable(INSTRUCTOR_TABLE);
        String InstID[] = null;
        try{
            InstID = new String[CountRecords("INSTRUCTOR"," Where CourseID = '"+CourseID+"'")];
            
            pstmt= con.prepareStatement(sql + " Where CourseID = ?"); 
            pstmt.setString(1, CourseID);
            rs = pstmt.executeQuery();
            for(int i=0; rs.next(); i++){
                InstID[i] = String.valueOf(rs.getInt("INSTRUCTORID"));
            }
        }
        catch (SQLException ex) {//Course Wasn't Found in Courses DataBase
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return InstID;
    }
    
    
    public Staff[] getInstructorsInfoFromCourseID(String CourseID) {
        String ID[] = getInstructorsIDFromCourseID(CourseID);
        selectTable(USERS_TABLE);
        Staff[] s = new Staff[ID.length];
        try {
            for(int i=0; i<ID.length; i++){
                pstmt= con.prepareStatement(sql + " INNER JOIN INSTRUCTOR ON INSTRUCTOR.INSTRUCTORID = USERS.USERID Where USERS.USERID = ?"); 
                pstmt.setString(1, ID[i]);
                rs = pstmt.executeQuery();
                rs.next();
                s[i]=new Staff(
                    rs.getString("UserID"), 
                    rs.getString("FName"), 
                    rs.getString("LNAME"),
                    rs.getDate("DATEOFBIRTH"), 
                    checkGender(rs.getString("GENDER")),
                    rs.getString("Salary")
                    );
                s[i].setPhone(getPhones(s[i].userID));
                s[i].calc_age();
            }
        }
        catch (SQLException ex) {//Course Wasn't Found in Courses DataBase
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception e){}
        return s;
    }
    
    public Staff getInstructorsNameFromInstructorID(String InstructorID) {
        selectTable(USERS_TABLE);
        Staff s = new Staff(InstructorID);
        try {
            pstmt= con.prepareStatement(sql + " Where UserID = ?"); 
            pstmt.setInt(1,Integer.parseInt( InstructorID));
            rs = pstmt.executeQuery();    
            rs.next();
            s.firstName = rs.getString("FNAME");
            s.lastName =  rs.getString("LNAME");
        }
        catch (SQLException ex) {//Course Wasn't Found in Courses DataBase
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }
    
    //Afifi will change this.
    /**
     * 
     * @return
     */
    public Student[] getStudents(){
        Student s[] =  null;
        try {
            s = new Student[CountRecords("Student"," INNER JOIN USERS ON STUDENT.STUDENTID = USERS.USERID")];
            selectTable(STUDENT_TABLE);
            rs = stmt.executeQuery(sql+" INNER JOIN USERS ON STUDENT.STUDENTID = USERS.USERID ");
            for(int i=0; rs.next(); i++){
                s[i]=new Student(
                String.valueOf(rs.getInt("ACADIMICYEAR")),
                String.valueOf(rs.getInt("CLASSNO")),
                String.valueOf(rs.getInt("STUDENTID")),
                rs.getString("FNAME"),
                rs.getString("LNAME"),
                rs.getDate("DATEOFBIRTH"),
                checkGender(rs.getString("GENDER")));
                s[i].setPhone(getPhones(s[i].userID));
                s[i].calc_age();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        catch(Exception e){}
        return s;
    }   
    //Afifi will change this.
    /**
     * 
     * @return
     */
    
    public Staff[] getInstructors(){
        Staff inst[] = null;
        try {
            inst = new Staff[CountRecords("INSTRUCTOR"," INNER JOIN USERS ON INSTRUCTOR.INSTRUCTORID = USERS.USERID ")];
            
            selectTable(INSTRUCTOR_TABLE);
            rs = stmt.executeQuery(sql+" INNER JOIN USERS ON INSTRUCTOR.INSTRUCTORID = USERS.USERID ");
            for(int i=0; rs.next(); i++){               
                inst[i] = new Staff(
                    rs.getString("CourseID"),
                    String.valueOf(rs.getInt("Salary")),
                    String.valueOf(rs.getInt("INSTRUCTORID")), 
                    rs.getString("FNAME"), 
                    rs.getString("LNAME"),
                    rs.getDate("DATEOFBIRTH"), 
                    checkGender(rs.getString("GENDER"))
                );
                inst[i].setPhone(getPhones(inst[i].userID));
                inst[i].calc_age();
            }
            
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        catch(Exception e){}
        return inst;
    }
    
    public Staff[] getAdmins(){
        Staff admins[] = null;
        try {
            admins = new Staff[CountRecords("Users"," WHERE USERID BETWEEN 100000000 AND 199999999")];
            selectTable(USERS_TABLE);//This is correct!
            rs = stmt.executeQuery(sql + " WHERE USERID BETWEEN 100000000 AND 199999999");//inclusive
            for(int i=0; rs.next(); i++){
                admins[i]=new Staff(
                    String.valueOf(rs.getInt("USERID")), 
                    rs.getString("FNAME"), 
                    rs.getString("LNAME"),
                    rs.getDate  ("DATEOFBIRTH"), 
                    checkGender(rs.getString("GENDER"))
                );
                admins[i].setPhone(getPhones(admins[i].userID));
                admins[i].calc_age();
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception e){}
        return admins;
    }
    
    
    public Grades[] getGrades(String UserID) {
        Grades []g = null;
        try {
            g = new Grades[CountRecords("STUDIES"," WHERE StudentID = "+UserID)];
            selectTable(STUDIES_TABLE);
            pstmt = con.prepareStatement(sql + " Where StudentID = ? ");
            pstmt.setInt(1,Integer.parseInt(UserID));
            rs = pstmt.executeQuery();
            
            for(int i=0; i<50 && rs.next(); i++)
                g[i]=new Grades(UserID, rs.getString("COURSEID"), String.valueOf(rs.getInt("Grade")));
            
        } catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return g;
    }
    public Course[] getCourses() throws NullPointerException{
        Course c[] = null;
        
        try {
            c = new Course[CountRecords("COURSE","")];
            selectTable(COURSE_TABLE);
            pstmt= con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            for(int i=0; rs.next(); i++){
                c[i]=new Course(
                rs.getString("CourseID"),
                rs.getString("NAME"),
                rs.getString("Description") 
                );
            }
        } 
        catch (SQLException ex) {//Course Wasn't Found in Courses DataBase
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }
    public boolean getCourseInfo(Course c){
        try {
            selectTable(COURSE_TABLE);
            pstmt = con.prepareStatement(sql + " Where CourseID = ?");
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
    
    private String checkGender(String gender)
    {
        if(gender.equals("m")) return "Male";
        else                   return "Female";
    }
    public boolean getStudentInfo(Student s){//Can be used for searching, requires only ID
        try {
            selectTable(STUDENT_TABLE);
            System.out.println("STUDENT ID : " + s.userID);
            pstmt = con.prepareStatement(sql + " INNER JOIN USERS ON STUDENT.STUDENTID = USERS.USERID WHERE STUDENT.STUDENTID = ?");
            pstmt.setInt(1,Integer.parseInt(s.userID)); 
            rs = pstmt.executeQuery();
            rs.next();
            s.firstName   = rs.getString("FNAME");
            s.lastName    = rs.getString("LNAME");
            s.birthDate   = rs.getDate("DATEOFBIRTH");
            s.gender      = checkGender(rs.getString("GENDER"));
            s.password    = rs.getString("password");
            s.academicYear= String.valueOf(rs.getInt("ACADIMICYEAR"));
            s.classNo     = String.valueOf(rs.getInt("CLASSNO"));
            s.phone       = getPhones(s.userID);
            s.calc_age();
            return true;
        }
        catch (SQLException ex) {

            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }catch(Exception e){
            return false;
        }
    }
    
    public boolean getInstructorInfo(Staff stf){//Can be used for searching, requires only ID
        try {
            selectTable(INSTRUCTOR_TABLE);
            pstmt = con.prepareStatement(sql + " INNER JOIN USERS ON INSTRUCTOR.INSTRUCTORID = USERS.USERID WHERE INSTRUCTOR.INSTRUCTORID = ?");
            pstmt.setInt(1,Integer.parseInt(stf.userID)); 
            rs = pstmt.executeQuery();
            System.out.println("result set is : " + rs.next());
            stf.firstName   = rs.getString("FNAME");
            stf.lastName    = rs.getString("LNAME");
            stf.birthDate   = rs.getDate("DATEOFBIRTH");
            stf.gender      = checkGender(rs.getString("GENDER"));
            stf.password    = rs.getString("password");
            stf.salary      = String.valueOf(rs.getInt("Salary"));
            stf.courseID    = rs.getString("CourseID");
            stf.phone       = getPhones(stf.userID);
            stf.calc_age();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch(Exception e){
            return false;
        }
    }
    public boolean getAdminInfo(Staff stf){//Can be used for searching, requires only ID
        try {
            selectTable(USERS_TABLE);
            pstmt = con.prepareStatement(sql + " WHERE USERID = ?");
            pstmt.setInt(1,Integer.parseInt(stf.userID)); 
            rs = pstmt.executeQuery();
            rs.next();
            stf.firstName   = rs.getString("FNAME");
            stf.lastName    = rs.getString("LNAME");
            stf.birthDate   = rs.getDate("DATEOFBIRTH");
            stf.gender      = checkGender(rs.getString("GENDER"));
            //stf.password    = rs.getString("password"); // << Should we not return this ??
            //stf.phone       = getPhones(stf.userID);
            stf.calc_age();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        catch(Exception e){return false;}
    }
    private String[] getCoursesID(String UserID) {
        String []str = null;
        try {
            str = new String[CountRecords("Course", " WHERE COURSEID = ?")];
            selectTable(COURSE_TABLE);
            pstmt = con.prepareStatement(sql + " Where COURSEID = ?");
            pstmt.setInt(1,Integer.parseInt(UserID));
            rs = pstmt.executeQuery();
            for(int i=0; rs.next(); i++)
                str[i]= rs.getString("COURSEID");
        } catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }   
    public boolean addAdminActivity(String adminID, String description){
        try {
            InsertIntoTable(ADMIN_HISTORY_TABLE);
            pstmt=con.prepareStatement(sql);
           
            pstmt.setString(1, description);
            pstmt.setDate(2, Date.valueOf(LocalDate.now()));
            pstmt.setInt(3,Integer.parseInt(adminID));
            pstmt.executeUpdate();
            return true;
        }
        catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public History[] getAdminHistory() {
        History []h = null;
        
        try {
            h = new History[CountRecords("ADMIN_HISTORY","")];
            selectTable(ADMIN_HISTORY_TABLE);
            pstmt=con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            for(int i=0; rs.next(); i++){
                h[i] = new History();
                h[i].description = rs.getString("DESCRIPTION");
                h[i].adminID     = String.valueOf(rs.getInt("ADMINID"));
                h[i].modificationdate        = rs.getDate("activity_date");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBInterface.class.getName()).log(Level.SEVERE, null, ex);
            return h;
        }
        return h;
    }
    public Grades[] getGradesInfoUsingCourseID(String CourseID){
            Grades g[] = null;
            try {
            g = new Grades[CountRecords("STUDIES"," WHERE COURSEID = '"+CourseID+"'")];
            selectTable(STUDIES_TABLE);
            pstmt = con.prepareStatement(sql + " Where COURSEID = ?");
            pstmt.setString(1, CourseID);
            rs = pstmt.executeQuery();
            for(int i=0; rs.next(); i++){
                g[i]= new Grades(
                String.valueOf(rs.getInt("STUDENTID")),
                CourseID,
                String.valueOf(rs.getInt("GRADE"))
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
            selectTable(STUDIES_TABLE);
            pstmt = con.prepareStatement(sql + " Where COURSEID = ? AND STUDENTID = ?");
            pstmt.setString(1, g.CourseID);
            pstmt.setInt(2,Integer.parseInt(g.StudentID));
            rs = pstmt.executeQuery();
            rs.next();
            g.grade=String.valueOf(rs.getInt("GRADE"));
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