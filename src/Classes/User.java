/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;


/**
 *
 * @author raya
 */
public class User {
    public   String age, phone[], userID, firstName, lastName, gender, password;
    public Date birthDate;
    protected static final DBInterface DB = new DBInterface();
    
    public User() {
        userID=null;
    }

    public User(String ID) {
        this.userID = ID;
    }

    public User(String age, String[] phone, String userID, String firstName, String lastName, String gender, String password, Date birthDate) {
        this.age = age;
        this.phone = phone;
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.password = password;
        this.birthDate = birthDate;
    }
    
    public User(User u) {
        this.age = u.age;
        this.phone = u.phone;
        this.userID = u.userID;
        this.firstName = u.firstName;
        this.lastName = u.lastName;
        this.gender = u.gender;
        this.password = u.password;
        this.birthDate = u.birthDate;
    }
    
    
    
    
    public static boolean login(String userID,String password) {
        return DB.checkUserCredentials(userID, password);
    }
    
    public static boolean deleteUser(String userID){
        return DB.deleteUser(userID);
    }
    
    public void setPhone(String[] phones) {
        this.phone=phones;
    }
    
    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
        String strDate = dateFormat.format(this.birthDate);
        return strDate;
    }
    
    public void calc_age(){
        this.age = String.valueOf( LocalDate.now().getYear() - birthDate.getYear() );
    }
}
