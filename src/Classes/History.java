package Classes;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Eslam M.Ashour
 */
public class History {
    public String adminID, description;
    public Date modificationdate;
    private static final DBInterface DB = new DBInterface();
    public static final String STUDENT_ADDED      = " is added to student Table";
    public static final String INSTRUCTOR_ADDED   = " is added to instructor Table";
    public static final String ADMIN_ADDED        = " is added to admin Table";
    public static final String COURSE_ADDED       = " is added to course Table";

    public static final String STUDENT_UPDATED    = " is Updated in student Table";
    public static final String INSTRUCTOR_UPDATED = " is Updated in instructor Table";
    public static final String ADMIN_UPDATED      = " is Updated in admin Table";
    public static final String COURSE_UPDATED     = " is Updated in course Table";

    public static final String STUDENT_DELETED    = " is Deleted from student Table";
    public static final String INSTRUCTOR_DELETED = " is Deleted from instructor Table";
    public static final String ADMIN_DELETED      = " is Deleted from admin Table";
    public static final String COURSE_DELETED     = " is Deleted from course Table";
    

    public History(String AdminID) {
        this.adminID     = AdminID;
        this.description = "Empty..";
    }
    public History()
    {

    }
    public History(String AdminID, String Description) {
        this.adminID     = AdminID;
        this.description = Description;
    }
        /**
         * 
         * @param adminID current UserID
         * @param description Use the final variables defined in this class as static final
         * @return 
         */
    public static boolean historyAdd(String adminID ,String description){
        return DB.addAdminActivity(adminID, description);
    }
    
    public static History[] getAdminHistory(){
        return DB.getAdminHistory();
    }
    public String getDate() {
        return (new SimpleDateFormat("dd/MM/yyyy")).format(this.modificationdate);
    }
}
