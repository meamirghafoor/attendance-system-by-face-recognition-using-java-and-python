/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author neutron
 */
import java.sql.*;
public class Auto_Attendence_System {
   // private static final String val=String.valueOf(uri);
    public static void main(String[] args) throws ClassNotFoundException {  
//        try{
//        String py="AttendanceSystem";
//        ProcessBuilder pb = new ProcessBuilder("python", py + ".py");
//        String cmd="D:\\Bachelor(CS)\\Projects\\6th_Semester\\Atrificial_Intellegence\\Auto_Attendence_System\\src";
//        pb.directory(new File(cmd));
//        pb.redirectError();
//        pb.start();
//        }catch(IOException e){
//            System.out.print(e);
//        }
        Auto_Attendence_System_GUI obj=new Auto_Attendence_System_GUI();
        obj.setVisible(true);

    }
    
}
