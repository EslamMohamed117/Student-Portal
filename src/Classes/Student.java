/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Date;

/**
 *
 * @author raya
 */
public class Student extends User {
    
    public String academicYear;
    public String classNo; 
    /**
     * 
     * @param ID 
     */
    public Student(String ID) {
        super(ID);
    }
    /**
     * 
     * @param academicYear
     * @param classNo
     * @param member 
     */
    public Student(String academicYear, String classNo, User member) {
        super(member);
        this.academicYear = academicYear;
        this.classNo = classNo;
    }
    
    Student(String academicYear, String classNo, String UserID, String FirstName, String LastName, Date birthDate, String gender) {
        this.academicYear = academicYear;
        this.classNo = classNo;
        this.userID = UserID;
        this.firstName = FirstName;
        this.lastName = LastName;
        this.gender = gender;
        this.birthDate = birthDate;
    }
    public Student(String academicYear, String classNo, String FirstName, String LastName, String gender, String password) {
        this.academicYear = academicYear;
        this.classNo = classNo;
        this.firstName = FirstName;
        this.lastName = LastName;
        this.gender = gender;
        this.password = password;
    }
    
    public boolean fillstudentInfo(){
        return DB.getStudentInfo(this);
    }
    
    public boolean addStudent(){
        return DB.addStudent(this);  
    }
    public boolean updateStudent(){
        return DB.updateStudent(this);
    }
    
    public static Student[] getStudents(){
        return DB.getStudents();
    }
    public boolean deleteStudent(){
        return DB.deleteStudent(this.userID);
    }
    

    //Search for Instructor ID.
    //
    
}
