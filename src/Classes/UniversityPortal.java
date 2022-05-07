/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

public class UniversityPortal {
    /**
     * 
     * @param args the command line arguments 
     */
    
    
    public static void main(String[] args){
        DBInterface db=new DBInterface();
        String s =Staff.getStaff("13")[0].FirstName+" "+Staff.getStaff("13")[0].LastName;
        System.out.println(s);
//Student student = new Student("000");
        //Course[] list = Course.getCourses(student.UserID);
        //System.out.println(list[0].name);
        
    }
}
