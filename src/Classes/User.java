/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    
    public void setPhone(String[] phones) {
        this.phone=phones;
    }
    
    public String getDate() {
        return (new SimpleDateFormat("dd/MM/yyyy")).format(this.birthDate);
    }

    public void setDate(String sDate1) throws Exception{
            this.birthDate = new java.sql.Date((new SimpleDateFormat("dd/MM/yyyy").parse(sDate1)).getTime());
    }
    public void calc_age() throws Exception{
        LocalDate curr = LocalDate.now();
        int yeardiff = curr.getYear() - birthDate.getYear() - 1900;
        if (curr.getMonthValue()< birthDate.getMonth() ||
           (curr.getMonthValue()== birthDate.getMonth() && curr.getDayOfMonth() < birthDate.getDate()))
            yeardiff--;
        this.age = String.valueOf(yeardiff);
    }
}
