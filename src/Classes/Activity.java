/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class Activity {
    public String activityID, name, type, link, instructorID, courseID;
    public Date date;
    private final static DBInterface DB = new DBInterface();
    
    public Activity(String name, String type, String link, String instructorID, Date date, String courseID) {
        this.name = name;
        this.type = type;
        this.link = link;
        this.instructorID = instructorID;
        this.date = date;
        this.courseID=courseID;
    }
        public Activity(String ActivityID,String name, String type, String link, String instructorID, Date date, String courseID) {
        this.activityID = ActivityID;
        this.name = name;
        this.type = type;
        this.link = link;
        this.instructorID = instructorID;
        this.date = date;
        this.courseID=courseID;
    }
    public Activity(String name, String type, String link, String instructorID, String courseID) {
        this.name = name;
        this.type = type;
        this.link = link;
        this.instructorID = instructorID;
        this.courseID=courseID;
    }
    
    public Activity(Activity TempActivity){
        this.activityID = TempActivity.activityID;
        this.name = TempActivity.name;
        this.type = TempActivity.type;
        this.link = TempActivity.link;
        this.instructorID = TempActivity.instructorID;
        this.date = TempActivity.date;
        this.courseID = TempActivity.courseID;
    }
    
    public boolean createActivity(){
        return DB.addActivity(this);
    }
    
    public static boolean deleteActivity(String ActivityID){
        return DB.deleteActivity(ActivityID);
    }
    
    public boolean updateActivity(){
        return DB.updateActivity(this);
    }
    /**
     * 
     * @param UserID
     * @return ALL Activities belonging to this User in an Array of Activities
     */
    public static Activity[] getActivities(String UserID){
        return DB.getActivities(UserID);
    }
    /**
     * 
     * @return ALL Activities 
     */
    public static Activity[] getActivities(){
        return DB.getActivities();
    }
    public static Activity[] getActivitiesUsingCourseID(String courseID){
        return DB.getActivitiesUsingCourseID(courseID);
    }
    public String getDate() {
        return new SimpleDateFormat("dd/MM/yyyy").format(this.date);
    }

    public void setDate(String sDate1){
        try {
            this.date = new java.sql.Date((new SimpleDateFormat("dd/MM/yyyy").parse(sDate1)).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
