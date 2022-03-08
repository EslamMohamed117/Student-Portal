/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
/**
 *
 * @author raya
 */
public class Staff extends User{
    public String courseID;
    public String salary;
    public Staff(String ID) {
        super(ID);
    }

    public Staff(String courseID, String salary, String age, String phone, String ID, String FirstName, String LastName, String birthDate, String gender) {
        super(age, phone, ID, FirstName, LastName, birthDate, gender);
        this.courseID = courseID;
        this.salary = salary;
    }
    public Staff(String courseID, String salary, String age, String phone, String FirstName, String LastName, String birthDate, String gender, String password , String dummy) {
        super(age, phone, FirstName, LastName, birthDate, gender);
        this.courseID = courseID;
        this.salary = salary;
    }

    /**
     *
     * @param member contains user Information
     * @param courseCode // A Foreign Key to Courses Table
     * @param salary    // Determines how much this staff member earns per month
     */
    public Staff(User member,String courseCode, String salary) {
        super(member);
        this.courseID = courseCode;
        this.salary = salary;
    }

    public boolean addStaff(){
        return DB.addStaff(this);
        
    }
    public boolean updateStaff(){
        return DB.updateStaff(this);
    }
    
    public boolean fillStaffInfo(String StaffID){
        this.UserID = StaffID;
        return DB.getStaffInfo(this);
    }
    public boolean fillStaffInfo(){
        return DB.getStaffInfo(this);
    }
    public static Staff[] getStaff(){
        return DB.getStaff();
    }
    
    public static Staff[] getStaff(String courseID){
        return DB.getInstructorsInfoFromCourseID(courseID);
    }
    public static Staff getStaffName(String ID){
        return DB.getInstructorsNameFromInstructorID(ID);
    }
    
    public static boolean deleteStaff(String courseID){
        return DB.deleteInstructorFromActivity(courseID);
    }
    
    
    
}
