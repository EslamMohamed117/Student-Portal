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
public class User {
    public   String age, phone, UserID, FirstName, LastName, birthDate, gender, password;
    protected static final DBInterface DB = new DBInterface();
    
    public User() {
        UserID="-1";
    }

    public User(String ID) {
        this.UserID = ID;
    }

    public User(User member){
        this.UserID    = member.UserID;
        this.password  = member.password;
        this.FirstName = member.FirstName;
        this.LastName  = member.LastName;
        this.age       = member.age;
        this.phone     = member.phone;
        this.birthDate = member.birthDate;
        this.gender    = member.gender;
    }
    
    public User(String age, String phone, String ID, String FirstName, String LastName, String birthDate, String gender) {    
        this.UserID    = ID;
        this.FirstName = FirstName;
        this.LastName  = LastName;
        this.age       = age;
        this.phone     = phone;
        this.birthDate = birthDate;
        this.gender    = gender;
    }
        public User(String age, String phone, String FirstName, String LastName, String birthDate, String gender) {    
        this.FirstName = FirstName;
        this.LastName  = LastName;
        this.age       = age;
        this.phone     = phone;
        this.birthDate = birthDate;
        this.gender    = gender;
    }
    
    public static boolean login(String userID,String password) {
        return DB.checkUserCredentials(userID, password);
    }
    
    public static boolean deleteUser(String userID){
        return DB.deleteUser(userID);
    }
}
