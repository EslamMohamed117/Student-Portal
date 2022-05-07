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
public class Grades {
    public String StudentID, CourseID, grade;
    private static DBInterface DB = new DBInterface();
    public Grades() {
    }

    
    public Grades(String UserID, String CourseID) {
        this.StudentID = UserID;
        this.CourseID = CourseID;
        this.grade="0";
    }

    public Grades(String UserID, String CourseID, String grade) {
        this.StudentID = UserID;
        this.CourseID = CourseID;
        this.grade = grade;
    }
    
    /**
     * 
     * @return 
     */
    public boolean addGrade(){
        return DB.addGrade(this);
    }
    public boolean UpdateGrade(){
        return DB.updateGrade(this);
    }
    
    public static boolean DeleteGradeUsingUserID(String UserID){
        return DB.deleteUserFromStudies(UserID);//deletes all of the grades belonging to this USERID
    }
    public static boolean DeleteGradeUsingCourseID(String CourseID){
        return DB.deleteCourseFromGrade(CourseID);//deletes all of the grades belonging to this USERID
    }
    public static boolean DeleteGrades(String UserID, String CourseID){
        return DB.deleteGrade(UserID, CourseID);//deletes all of the grades belonging to this USERID
    }
    
    public Grades[] getGrades(String UserID){
        return DB.getGrades(UserID);
    }
    public static Grades[] getGradesUsingCourseID(String courseID){
        return DB.getGradesInfoUsingCourseID(courseID);
    }
    public boolean fillGradesInfo(){
        return DB.getGradesInfo(this);
    }
}
