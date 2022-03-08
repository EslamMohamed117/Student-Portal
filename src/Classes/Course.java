/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author hp
 */
public class Course {
    public String courseID, name ,description;
    private static final DBInterface DB = new DBInterface();

    public Course() {
        
    }
    public Course(String courseID)
    {
        this.courseID = courseID;
    }
    public Course(String courseID, String name,String description)
    {
        this.courseID=courseID;
        this.name=name; 
        this.description=description ;
    }
    
    public boolean addCourse(){
        return DB.addCourse(this);
    }
    
    public boolean updateCourse(){
        return DB.updateCourse(this);
    }
    
    public static Course[] getCourses(String UserID){
        return DB.getCourses(UserID);
    }
    
    public boolean fillCourseInfo(){
        return DB.getCourseInfo(this);
    }
    
    public static boolean deleteCourse(String CourseID){
        return DB.deleteCourse(CourseID);
    }
    public static Course[] getCourses(){
        return DB.getCourses();
    }
}
