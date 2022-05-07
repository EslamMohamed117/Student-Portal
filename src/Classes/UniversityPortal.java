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
        String s =Staff.getInstructors("13")[0].firstName+" "+Staff.getInstructors("13")[0].lastName;
        System.out.println(s);
//Student student = new Student("000");
        //Course[] list = Course.getCourses(student.userID);
        //System.out.println(list[0].name);
        
    }
}
