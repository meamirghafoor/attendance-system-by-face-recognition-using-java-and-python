/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 *
 * @author neutron
 */
public class Auto_Attendence_System_GUI extends javax.swing.JFrame {

    /**
     * Creates new form Auto_Attendence_System_GUI
     */
    int xMouse,yMouse;
    int seee=0;Color c,color;
    int et=0,count=0;
    int xm,ym;
    Color cl;
    Timer timer;
    File path;
    String teacher_login_id="";
    String teacher_login_name="";
    public Auto_Attendence_System_GUI() {
        initComponents();
        icon();
        fun();
        admin_sidebar();
        pass1.setEchoChar((char)0);
        user.requestFocus();
        user.setCaretPosition(0);
        UIManager.put("ToolTip.background", new Color(240,240,240));
        main_dash.setVisible(true);
        faculty.setVisible(false);
        student.setVisible(false);
        course.setVisible(false);
        home.setVisible(false);
        login.setVisible(true);
        fun_database_creation();
        dashboard_value(teacher_value,"SELECT count(id) as total FROM Teacher");
        dashboard_value(course_value,"SELECT count(name) as total FROM Course");
        dashboard_value(student_value,"SELECT count(id) as total FROM Student");
    }
    Connection_sql con=new Connection_sql();
    void fun_database_creation(){
        Statement s = null;
        try{
           s = con.con().createStatement();
           s.addBatch("CREATE TABLE if not exists Attendance ('id' INTEGER,'fullname' TEXT NOT NULL,'course' TEXT NOT NULL,'datetime' NUMERIC NOT NULL)");
           s.addBatch("CREATE TABLE if not exists Teacher ('id' INTEGER PRIMARY KEY, 'fullname' TEXT NOT NULL,'Address' TEXT NOT NULL, 'city' TEXT, 'country' TEXT, 'contact' TEXT, 'email' TEXT, 'image' TEXT, 'course' TEXT,'pswd' TEXT)");
           s.addBatch("CREATE TABLE if not exists Student ('id' INTEGER PRIMARY KEY, 'fullname' TEXT NOT NULL, 'fathername' TEXT NOT NULL,'Address' TEXT NOT NULL, 'course' TEXT, 'contact' TEXT, 'email' TEXT)");
           s.addBatch("CREATE TABLE if not exists Pid ('fid' TEXT, 'sid' TEXT, 'cor' TEXT)");
           s.addBatch("CREATE TABLE if not exists Course ('name' TEXT, 'teacher' TEXT,'NoofStu' TEXT)");
           s.executeBatch();
        }catch(ClassNotFoundException | SQLException e){
            System.out.print(e);
        }finally{
            try {
                s.close();
            } catch (SQLException ex) {
                Logger.getLogger(Connection_sql.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    final void admin_sidebar(){
        jPanel1.setBackground(Color.blue);
        jPanel8.setBackground(new Color(51,102,255));
        jPanel7.setBackground(new Color(51,102,255));
        jPanel5.setBackground(new Color(51,102,255));
        jLabel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jLabel21.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jLabel22.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jLabel20.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        fcl_dahs.setBackground(Color.blue);
        fc_atten.setBackground(new Color(51,102,255));
        jLabel60.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jLabel62.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    }
    final void fun(){
        timer=new Timer(510, (ActionEvent arg0) -> {
            count++;
            if(count==11){
                logo.setVisible(false);
                pass_penl.setVisible(true);
                user.requestFocus();
                user.setCaretPosition(0);
                titlr_logo.setText(null);
                timer.stop();
            }
            lod.setText(lod.getText()+"•");
            if(lod.getText().length()>7){
                lod.setText("");
            }
        });
        timer.start();
    }
void dashboard_value(JLabel lb,String query){
    String count=null;
    Statement pst = null;
    ResultSet rs = null;
    try{
        pst=con.con().createStatement();
        rs=pst.executeQuery(query);
       if(rs.next()){
          count = rs.getString("total");
       }
    }catch(SQLException e){
        System.out.print(e);
    } catch (ClassNotFoundException ex) {
            Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
        try {
            rs.close();
            pst.close();
        } catch (SQLException ex) {
            Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    if(count==null){
        lb.setText("0");
    }else{
        lb.setText(count);
    }
}
    int fetch_ID_value(String type){
        int val = 0;
    try {
        Statement pst = con.con().createStatement();
        String qury = "SELECT * FROM Pid";
        ResultSet rs = pst.executeQuery(qury);
        if(rs.next()){
            val = Integer.parseInt(rs.getString(type));
            val++;
            rs.close();
            pst.close();
        }
    }catch(SQLException e){
        System.out.print(e);
    }catch (ClassNotFoundException ex) {
            Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
     }
    return val;
}
    void Teacher_course_data(){
        String qr="Select * from Course where teacher='"+teacher_login_name+"'";
        dashboard_value(course_value1,"SELECT count(name) as total FROM Course where teacher='"+teacher_login_name+"'");
        Statement pst = null;
        ResultSet rs = null;
        try{
            at1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Course"}));
            pst = con.con().createStatement();
            rs = pst.executeQuery(qr);
            tc_course.setRowHeight(50);
            DefaultTableModel model = (DefaultTableModel) tc_course.getModel();
            model.setRowCount(0);
            tc_course.getColumnModel().getColumn(0).setPreferredWidth(40);
            tc_course.getColumnModel().getColumn(1).setPreferredWidth(100);
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            TableModel tableModel = tc_course.getModel();
            for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
                tc_course.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
            }
            JTableHeader tb = tc_course.getTableHeader();
            tb.setBackground(new Color(153, 153, 255));
            tb.setFont(new Font("Tahoma", Font.BOLD, 13));
            int i=1;
            while(rs.next()){
                Object[] tbl = {i,rs.getString("name")};
                at1.addItem(rs.getString("name"));
                model.addRow(tbl);
                i++;
            }
            
        }catch (ClassNotFoundException | SQLException ex) {
           System.out.print(ex);
        }finally{
            try {
                rs.close();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    void Teacher_data(String type,String word){
        String qr="";
        if("like".equals(type)){
            qr="Select * from teacher where fullname like'%"+word+"%' OR id like'%"+word+"%'";
        }else{
            qr="Select * from Teacher";
        }
        Statement pst = null;
        ResultSet rs = null;
        try{
            pst = con.con().createStatement();
            rs = pst.executeQuery(qr);
            fctable.setRowHeight(50);
            DefaultTableModel model = (DefaultTableModel) fctable.getModel();
            model.setRowCount(0);
            fctable.getColumnModel().getColumn(0).setPreferredWidth(35);
            fctable.getColumnModel().getColumn(1).setPreferredWidth(130);
            fctable.getColumnModel().getColumn(2).setPreferredWidth(135);
            fctable.getColumnModel().getColumn(3).setPreferredWidth(205);
            fctable.getColumnModel().getColumn(3).setPreferredWidth(205);
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            TableModel tableModel = fctable.getModel();
            for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
                fctable.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
            }
            JTableHeader tb = fctable.getTableHeader();
            tb.setBackground(new Color(153, 153, 255));
            tb.setFont(new Font("Tahoma", Font.BOLD, 13));
            while(rs.next()){
                String str=rs.getString("course");
                String val=null;
                if(str!=null){
                String[] course=str.split(",",8);
                for(int i=0;i<course.length;i++){
                    if(val==null){
                        val=course[i];
                    }else{
                        val=val+" <br> "+course[i];
                    }
                }}else{
                    val=".";
                }
                Object[] tbl = {rs.getString("id"), rs.getString("fullname"),rs.getString("contact"), rs.getString("email"), "<html>"+val+"</html"};
                model.addRow(tbl);
            }
            
        }catch (ClassNotFoundException | SQLException ex) {
           System.out.print(ex);
        }finally{
            try {
                rs.close();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    void attendence_data(String type,String word){
        String qr="";
        if("like".equals(type)){
            qr="Select * from Attendance where fullname like'%"+word+"%' OR id like'%"+word+"%'";
        }else{
            qr="Select * from Attendance";
        }
        Statement pst = null;
        ResultSet rs = null;
        try{
            pst = con.con().createStatement();
            rs = pst.executeQuery(qr);
            atn_table.setRowHeight(50);
            DefaultTableModel model = (DefaultTableModel) atn_table.getModel();
            model.setRowCount(0);
            atn_table.getColumnModel().getColumn(0).setPreferredWidth(35);
            atn_table.getColumnModel().getColumn(1).setPreferredWidth(130);
            atn_table.getColumnModel().getColumn(2).setPreferredWidth(135);
            atn_table.getColumnModel().getColumn(3).setPreferredWidth(135);
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            TableModel tableModel = atn_table.getModel();
            for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
                atn_table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
            }
            JTableHeader tb = atn_table.getTableHeader();
            tb.setBackground(new Color(153, 153, 255));
            tb.setFont(new Font("Tahoma", Font.BOLD, 13));
            while(rs.next()){
                Object[] tbl = {rs.getString("id"), rs.getString("fullname"),rs.getString("course"), rs.getString("datetime")};
                model.addRow(tbl);
            }
            
        }catch (ClassNotFoundException | SQLException ex) {
           System.out.print(ex);
        }finally{
            try {
                rs.close();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    void Student_data(String type,String word){
        String qr="";
        if("like".equals(type)){
            qr="Select * from Student where fullname like'%"+word+"%' OR id like'%"+word+"%'";
        }else{
            qr="Select * from Student";
        }
        Statement pst = null;
        ResultSet rs = null;
        try{
            pst = con.con().createStatement();
            rs = pst.executeQuery(qr);
            sttable.setRowHeight(50);
            DefaultTableModel model = (DefaultTableModel) sttable.getModel();
            model.setRowCount(0);
            sttable.getColumnModel().getColumn(0).setPreferredWidth(35);
            sttable.getColumnModel().getColumn(1).setPreferredWidth(130);
            sttable.getColumnModel().getColumn(1).setPreferredWidth(135);
            sttable.getColumnModel().getColumn(2).setPreferredWidth(205);
            sttable.getColumnModel().getColumn(3).setPreferredWidth(140);
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            TableModel tableModel = sttable.getModel();
            for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
                sttable.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
            }
            JTableHeader tb = sttable.getTableHeader();
            tb.setBackground(new Color(153, 153, 255));
            tb.setFont(new Font("Tahoma", Font.BOLD, 13));
            while(rs.next()){
                String str=rs.getString("course");
                String val=null;
                String[] course=str.split(",",8);
                for(int i=0;i<course.length;i++){
                    if(val==null){
                        val=course[i];
                    }else{
                        val=val+" <br> "+course[i];
                    }
                }
                Object[] tbl = {rs.getString("id"), rs.getString("fullname"),rs.getString("contact"), rs.getString("email"), "<html>"+val+"</html"};
                model.addRow(tbl);
            }
            
        }catch (ClassNotFoundException | SQLException ex) {
           System.out.print(ex);
        }finally{
            try {
                rs.close();
                pst.close();
            } catch (SQLException ex) {
                Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    void Course_data(String type,String word){
        Statement pt = null;
        ResultSet r = null;
        String qr="";
        if("like".equals(type)){
            qr="Select * from Course where name like'%"+word+"%' OR teacher like'%"+word+"%'";
        }else{
            qr="Select * from Course";
        }
        try{
            pt = con.con().createStatement();
            r = pt.executeQuery(qr);
            coursetable.setRowHeight(50);
            DefaultTableModel model = (DefaultTableModel) coursetable.getModel();
            model.setRowCount(0);
            coursetable.getColumnModel().getColumn(0).setPreferredWidth(35);
            coursetable.getColumnModel().getColumn(1).setPreferredWidth(130);
            coursetable.getColumnModel().getColumn(2).setPreferredWidth(135);
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            TableModel tableModel = coursetable.getModel();
            for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
                coursetable.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
            }
            JTableHeader tb = coursetable.getTableHeader();
            tb.setBackground(new Color(153, 153, 255));
            tb.setFont(new Font("Tahoma", Font.BOLD, 13));
            while(r.next()){
                String vl=r.getString("NoofStu");
                if(vl==null){
                    vl="0";
                }
                Object[] tbl = {r.getString("name"), r.getString("teacher"),vl};
                model.addRow(tbl);
            }
            
        }catch (ClassNotFoundException | SQLException ex) {
           System.out.print(ex);
        }finally{
            try {
                r.close();
                pt.close();
            } catch (SQLException ex) {
                Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    final void icon(){
        URL path37=getClass().getResource("icon/back.png");
        ImageIcon photo37=new ImageIcon(new ImageIcon(path37).getImage().getScaledInstance(back.getWidth(),back.getHeight(),java.awt.Image.SCALE_SMOOTH));
        back.setIcon(photo37);
        back1.setIcon(photo37);
        back2.setIcon(photo37);
        URL path120a=getClass().getResource("icon/team.png");
        ImageIcon photo1201a=new ImageIcon(new ImageIcon(path120a).getImage().getScaledInstance(card1.getWidth(),card1.getHeight(),java.awt.Image.SCALE_SMOOTH));
        card1.setIcon(photo1201a);
        URL path120=getClass().getResource("icon/stack.png");
        ImageIcon photo1201=new ImageIcon(new ImageIcon(path120).getImage().getScaledInstance(card2.getWidth(),card2.getHeight(),java.awt.Image.SCALE_SMOOTH));
        card2.setIcon(photo1201);
        card4.setIcon(photo1201);
        URL path121=getClass().getResource("icon/college.png");
        ImageIcon photo1202=new ImageIcon(new ImageIcon(path121).getImage().getScaledInstance(card3.getWidth(),card3.getHeight(),java.awt.Image.SCALE_SMOOTH));
        card3.setIcon(photo1202);
        URL path12=getClass().getResource("icon/s.png");
        ImageIcon photo12=new ImageIcon(new ImageIcon(path12).getImage().getScaledInstance(st1.getWidth(),st1.getHeight(),java.awt.Image.SCALE_SMOOTH));
        st1.setIcon(photo12);
        st2.setIcon(photo12);
        st3.setIcon(photo12);
        URL dash1=getClass().getResource("icon/menu.png");
        ImageIcon dashico1=new ImageIcon(new ImageIcon(dash1).getImage().getScaledInstance(dash.getWidth(),dash.getHeight(),Image.SCALE_SMOOTH));
        dash.setIcon(dashico1);
        dash_fc.setIcon(dashico1);
        URL dash2=getClass().getResource("icon/team.png");
        ImageIcon dashico2=new ImageIcon(new ImageIcon(dash2).getImage().getScaledInstance(dsh.getWidth(),dsh.getHeight(),Image.SCALE_SMOOTH));
        dsh.setIcon(dashico2);
        URL dash3=getClass().getResource("icon/college.png");
        ImageIcon dashico3=new ImageIcon(new ImageIcon(dash3).getImage().getScaledInstance(dsh1.getWidth(),dsh1.getHeight(),Image.SCALE_SMOOTH));
        dsh1.setIcon(dashico3);
        dsh_fc.setIcon(dashico3);
        URL dash4=getClass().getResource("icon/stack.png");
        ImageIcon dashico4=new ImageIcon(new ImageIcon(dash4).getImage().getScaledInstance(dsh2.getWidth(),dsh2.getHeight(),Image.SCALE_SMOOTH));
        dsh2.setIcon(dashico4);
        URL path119=getClass().getResource("icon/pro.png");
        ImageIcon photo119=new ImageIcon(new ImageIcon(path119).getImage().getScaledInstance(imageAvatar1.getWidth(),imageAvatar1.getHeight(),Image.SCALE_SMOOTH));
        imageAvatar1.setIcon(photo119);
        imageAvatar3.setIcon(photo119);
        URL path110=getClass().getResource("icon/email.png");
       URL path11=getClass().getResource("icon/pro.png");
        ImageIcon photo11=new ImageIcon(new ImageIcon(path11).getImage().getScaledInstance(lb.getWidth(),lb.getHeight(),Image.SCALE_SMOOTH));
        lb.setIcon(photo11);
        URL path1=getClass().getResource("icon/forget.png");
        ImageIcon photo1=new ImageIcon(new ImageIcon(path1).getImage().getScaledInstance(lb1.getWidth(),lb1.getHeight(),Image.SCALE_SMOOTH));
        lb1.setIcon(photo1);
        URL path10=getClass().getResource("icon/forget.png");
        ImageIcon photo10=new ImageIcon(new ImageIcon(path10).getImage().getScaledInstance(lock_icon.getWidth(),lock_icon.getHeight(),Image.SCALE_SMOOTH));
        lock_icon.setIcon(photo10);
        URL path12a=getClass().getResource("icon/see.png");
        ImageIcon photo=new ImageIcon(new ImageIcon(path12a).getImage().getScaledInstance(lb2.getWidth(),lb2.getHeight(),Image.SCALE_SMOOTH));
        lb2.setIcon(photo);
        URL path13=getClass().getResource("icon/then.png");
        ImageIcon photo0=new ImageIcon(new ImageIcon(path13).getImage().getScaledInstance(side_image.getWidth(),side_image.getHeight(),Image.SCALE_SMOOTH));
        side_image.setIcon(photo0);
    }
    void sign(){
        if(user.getText().equals("Enter Username") || pass1.getText().equals("Enter Password")){
            if(user.getText().equals("Enter Username")){
                err.setText("Enter Username");
            }
            if(pass1.getText().equals("Enter Password")){
                err1.setText("Enter password");
            }
        }else{
            if("admin".equals(user.getText()) && "admin123".equals(pass1.getText())){
                home.setVisible(true);
                login.setVisible(false);
                faculty_main.setVisible(false);
                admin.setVisible(true);
                fcl_bar.setVisible(false);
                admin_bar.setVisible(true);
            }else{
                try {
                    teacher_login_id=user.getText();
                    String pass=String.valueOf(pass1.getPassword());
                    Statement pst = con.con().createStatement();
                    String qury = "SELECT * FROM Teacher where id='"+teacher_login_id+"' and pswd='"+pass+"'";
                    ResultSet rs = pst.executeQuery(qury);
                    if(rs.next()){
                        teacher_login_name=rs.getString("fullname");
                        jLabel2.setText( teacher_login_name);
                        home.setVisible(true);
                        login.setVisible(false);
                        faculty_main.setVisible(true);
                        admin.setVisible(false);
                        fcl_bar.setVisible(true);
                        admin_bar.setVisible(false);
                        Teacher_course_data();
                        rs.close();pst.close();
                    }
                }catch(SQLException e){
                    System.out.print(e);
                }catch (ClassNotFoundException ex) {
                        Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
        }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main = new javax.swing.JPanel();
        home = new javax.swing.JPanel();
        sidebar = new javax.swing.JPanel();
        fcl_bar = new javax.swing.JPanel();
        fcl_dahs = new javax.swing.JPanel();
        dash_fc = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        fc_atten = new javax.swing.JPanel();
        dsh_fc = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        imageAvatar3 = new Main.ImageAvatar();
        admin_bar = new javax.swing.JPanel();
        imageAvatar1 = new Main.ImageAvatar();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        dash = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        dsh = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        dsh1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        dsh2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        dashboard = new javax.swing.JPanel();
        faculty_main = new javax.swing.JPanel();
        fc_dash = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        d2_3 = new javax.swing.JPanel();
        d2_l4 = new javax.swing.JLabel();
        card4 = new javax.swing.JLabel();
        course_value1 = new javax.swing.JLabel();
        d2_4 = new javax.swing.JPanel();
        d2_l5 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tc_course = new javax.swing.JTable();
        attendence = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        atn_table = new javax.swing.JTable();
        fcsearchd2 = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        at1 = new javax.swing.JComboBox<>();
        admin = new javax.swing.JPanel();
        main_dash = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        d3_1 = new javax.swing.JPanel();
        d3_l2 = new javax.swing.JLabel();
        card3 = new javax.swing.JLabel();
        student_value = new javax.swing.JLabel();
        d3_2 = new javax.swing.JPanel();
        d3_l3 = new javax.swing.JLabel();
        st3 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        d2_1 = new javax.swing.JPanel();
        d2_l2 = new javax.swing.JLabel();
        card2 = new javax.swing.JLabel();
        course_value = new javax.swing.JLabel();
        d2_2 = new javax.swing.JPanel();
        d2_l3 = new javax.swing.JLabel();
        st2 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        d1_1 = new javax.swing.JPanel();
        d1_l2 = new javax.swing.JLabel();
        card1 = new javax.swing.JLabel();
        teacher_value = new javax.swing.JLabel();
        d1_2 = new javax.swing.JPanel();
        d1_l3 = new javax.swing.JLabel();
        st1 = new javax.swing.JLabel();
        faculty = new javax.swing.JPanel();
        fc_main = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fctable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        fcsearchd1 = new javax.swing.JTextField();
        add_new_fc = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        fcaddress = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        fcname = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        fccity = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        fccountry = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        fccontact = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        fcemail = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        fcimage = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        back = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        student = new javax.swing.JPanel();
        st_main = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        sttable = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        add_new_st = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        staddress = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        stname = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        stphone = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        stemail = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        stimg = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        stfname = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        stcomo = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        back1 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        courses = new javax.swing.JTextField();
        course = new javax.swing.JPanel();
        course_main = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        coursetable = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jLabel68 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        add_new_cr = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        course1 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        courseteacher = new javax.swing.JComboBox<>();
        jPanel23 = new javax.swing.JPanel();
        back2 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        mini = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        cancle = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        login = new javax.swing.JPanel();
        close = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        minimize = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lock_icon = new javax.swing.JLabel();
        sky_logo = new javax.swing.JLabel();
        titlr_logo = new javax.swing.JLabel();
        side_image = new javax.swing.JLabel();
        panel1 = new javax.swing.JPanel();
        action_main = new javax.swing.JPanel();
        logo = new javax.swing.JPanel();
        ton = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lod = new javax.swing.JLabel();
        pass_penl = new javax.swing.JPanel();
        jPanel2 = new RoundedPanel(30);
        lb1 = new javax.swing.JLabel();
        lb2 = new javax.swing.JLabel();
        pass1 = new javax.swing.JPasswordField();
        jPanel3 = new RoundedPanel(30);
        user = new javax.swing.JTextField();
        lb = new javax.swing.JLabel();
        jPanel4 = new RoundedPanel(30);
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        err = new javax.swing.JLabel();
        err1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        main.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(63, 173, 204)));
        main.setLayout(new java.awt.CardLayout());

        home.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                homeMouseDragged(evt);
            }
        });
        home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                homeMousePressed(evt);
            }
        });
        home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sidebar.setLayout(new java.awt.CardLayout());

        fcl_bar.setBackground(new java.awt.Color(51, 102, 255));
        fcl_bar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fcl_dahs.setBackground(new java.awt.Color(51, 102, 255));
        fcl_dahs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fcl_dahs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fcl_dahsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                fcl_dahsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                fcl_dahsMouseExited(evt);
            }
        });
        fcl_dahs.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        fcl_dahs.add(dash_fc, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 15, 40, 30));

        jLabel59.setBackground(new java.awt.Color(255, 255, 255));
        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("Dashboard");
        fcl_dahs.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 120, -1));
        fcl_dahs.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 3, 80));

        fcl_bar.add(fcl_dahs, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 160, 80));

        fc_atten.setBackground(new java.awt.Color(51, 102, 255));
        fc_atten.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fc_atten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fc_attenMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                fc_attenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                fc_attenMouseExited(evt);
            }
        });
        fc_atten.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        fc_atten.add(dsh_fc, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 15, 40, 30));

        jLabel61.setBackground(new java.awt.Color(255, 255, 255));
        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setText("Attendence");
        fc_atten.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 120, -1));
        fc_atten.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 3, 80));

        fcl_bar.add(fc_atten, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 160, 80));

        jLabel63.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("Veiw Profile");
        jLabel63.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 0, 204), 1, true));
        fcl_bar.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 175, 80, 22));

        jLabel64.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("Teacher");
        fcl_bar.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 148, 160, -1));

        jLabel65.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setText("Aume Hani");
        fcl_bar.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 125, 160, 20));

        imageAvatar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/icon/pro.png"))); // NOI18N
        fcl_bar.add(imageAvatar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 120, 100));

        sidebar.add(fcl_bar, "card2");

        admin_bar.setBackground(new java.awt.Color(51, 102, 255));
        admin_bar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        imageAvatar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/icon/pro.png"))); // NOI18N
        admin_bar.add(imageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 120, 100));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Admin");
        admin_bar.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 125, 160, 20));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Admin");
        admin_bar.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 148, 160, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Veiw Profile");
        jLabel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 0, 204), 1, true));
        admin_bar.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 175, 80, 22));

        jPanel1.setBackground(new java.awt.Color(51, 102, 255));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel1MouseExited(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(dash, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 15, 40, 30));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Dashboard");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 120, -1));
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 3, 80));

        admin_bar.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 160, 80));

        jPanel5.setBackground(new java.awt.Color(51, 102, 255));
        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel5MouseExited(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel5.add(dsh, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 15, 40, 30));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Faculty");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 120, -1));
        jPanel5.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 3, 80));

        admin_bar.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 289, 160, 80));

        jPanel7.setBackground(new java.awt.Color(51, 102, 255));
        jPanel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel7MouseExited(evt);
            }
        });
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel7.add(dsh1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 15, 40, 30));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Student");
        jPanel7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 120, -1));
        jPanel7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 3, 80));

        admin_bar.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 367, 160, 80));

        jPanel8.setBackground(new java.awt.Color(51, 102, 255));
        jPanel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel8MouseExited(evt);
            }
        });
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel8.add(dsh2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 15, 40, 30));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Courses");
        jPanel8.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 120, -1));
        jPanel8.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 3, 80));

        admin_bar.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 446, 160, 80));

        sidebar.add(admin_bar, "card3");

        home.add(sidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, 540));

        dashboard.setLayout(new java.awt.CardLayout());

        faculty_main.setLayout(new java.awt.CardLayout());

        fc_dash.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel66.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel66.setText("Welcome on board !!");
        fc_dash.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 240, -1));

        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d2_3.setBackground(new java.awt.Color(102, 102, 255));
        d2_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        d2_3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d2_l4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        d2_l4.setForeground(new java.awt.Color(255, 255, 255));
        d2_l4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        d2_l4.setText("Courses");
        d2_3.add(d2_l4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 130, 30));
        d2_3.add(card4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, 60));

        course_value1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        course_value1.setForeground(new java.awt.Color(255, 255, 255));
        course_value1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        course_value1.setText("0");
        d2_3.add(course_value1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 155, 40));

        jPanel27.add(d2_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 110));

        d2_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        d2_4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d2_l5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        d2_l5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        d2_l5.setText("Assigned Courses");
        d2_4.add(d2_l5, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 5, 250, 30));

        jPanel27.add(d2_4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 280, 40));

        fc_dash.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 280, 150));

        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel69.setText("Assigned Courses");
        fc_dash.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 276, 110, 30));

        tc_course.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Course Name"
            }
        ));
        jScrollPane4.setViewportView(tc_course);

        fc_dash.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 600, 170));

        faculty_main.add(fc_dash, "card2");

        attendence.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        atn_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Date", "Course"
            }
        ));
        jScrollPane5.setViewportView(atn_table);

        attendence.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(42, 79, 590, 380));

        fcsearchd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fcsearchd2ActionPerformed(evt);
            }
        });
        fcsearchd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fcsearchd2KeyReleased(evt);
            }
        });
        attendence.add(fcsearchd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 42, 160, 30));

        jLabel71.setText("Search");
        attendence.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 46, 50, 20));

        jButton7.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton7.setText("Take Attendence");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        attendence.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 475, -1, -1));

        jButton10.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton10.setText("Refresh");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        attendence.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, -1, -1));

        at1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Course" }));
        attendence.add(at1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 475, 100, 25));

        faculty_main.add(attendence, "card3");

        dashboard.add(faculty_main, "card7");

        admin.setLayout(new java.awt.CardLayout());

        main_dash.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d3_1.setBackground(new java.awt.Color(102, 102, 255));
        d3_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        d3_1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d3_l2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        d3_l2.setForeground(new java.awt.Color(255, 255, 255));
        d3_l2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        d3_l2.setText("Students");
        d3_1.add(d3_l2, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 60, 120, 30));
        d3_1.add(card3, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 10, 50, 40));

        student_value.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        student_value.setForeground(new java.awt.Color(255, 255, 255));
        student_value.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        student_value.setText("0");
        d3_1.add(student_value, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 10, 155, 40));

        jPanel25.add(d3_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 92));

        d3_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        d3_2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        d3_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d3_2dash_h(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d3_2dash_e(evt);
            }
        });
        d3_2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d3_l3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        d3_l3.setText("View Details");
        d3_2.add(d3_l3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 5, 110, 20));
        d3_2.add(st3, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 8, 22, 15));

        jPanel25.add(d3_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 200, 30));

        main_dash.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, 200, 120));

        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d2_1.setBackground(new java.awt.Color(102, 102, 255));
        d2_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        d2_1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d2_l2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        d2_l2.setForeground(new java.awt.Color(255, 255, 255));
        d2_l2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        d2_l2.setText("Courses");
        d2_1.add(d2_l2, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 60, 130, 30));
        d2_1.add(card2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 10, 50, 40));

        course_value.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        course_value.setForeground(new java.awt.Color(255, 255, 255));
        course_value.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        course_value.setText("0");
        d2_1.add(course_value, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 10, 155, 40));

        jPanel22.add(d2_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 92));

        d2_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        d2_2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        d2_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d2_2dash_h(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d2_2dash_e(evt);
            }
        });
        d2_2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d2_l3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        d2_l3.setText("View Details");
        d2_2.add(d2_l3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 5, 120, 20));
        d2_2.add(st2, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 8, 22, 15));

        jPanel22.add(d2_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 200, 30));

        main_dash.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 200, 120));

        jPanel34.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel34.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d1_1.setBackground(new java.awt.Color(102, 102, 255));
        d1_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        d1_1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d1_l2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        d1_l2.setForeground(new java.awt.Color(255, 255, 255));
        d1_l2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        d1_l2.setText("Teachers");
        d1_1.add(d1_l2, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 60, 120, 30));
        d1_1.add(card1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 10, 50, 40));

        teacher_value.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        teacher_value.setForeground(new java.awt.Color(255, 255, 255));
        teacher_value.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        teacher_value.setText("0");
        d1_1.add(teacher_value, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 10, 155, 40));

        jPanel34.add(d1_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 92));

        d1_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        d1_2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        d1_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                d1_2dash_h(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                d1_2dash_e(evt);
            }
        });
        d1_2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        d1_l3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        d1_l3.setText("View Details");
        d1_2.add(d1_l3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 5, 120, 20));
        d1_2.add(st1, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 8, 22, 15));

        jPanel34.add(d1_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 200, 30));

        main_dash.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 200, 120));

        admin.add(main_dash, "card2");

        faculty.setLayout(new java.awt.CardLayout());

        fc_main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fctable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Contact", "Email", "Course"
            }
        ));
        fctable.setEnabled(false);
        fctable.setShowGrid(true);
        jScrollPane1.setViewportView(fctable);

        fc_main.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 650, 420));

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton1.setText("Add New Member");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        fc_main.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(537, 40, 130, 30));

        jLabel25.setText("Search");
        fc_main.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 46, 50, 20));

        fcsearchd1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fcsearchd1KeyReleased(evt);
            }
        });
        fc_main.add(fcsearchd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 42, 160, 30));

        faculty.add(fc_main, "card6");

        add_new_fc.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(102, 102, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("REGISTRATION FORM");
        jPanel12.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 40));

        jLabel27.setText("PLEASE FILL IN THE FORM BELOW");
        jPanel12.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 36, 190, 20));

        add_new_fc.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 690, 70));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Full Name*");
        add_new_fc.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 70, 20));

        fcaddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fcaddressActionPerformed(evt);
            }
        });
        add_new_fc.add(fcaddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 580, 25));

        jLabel29.setText("Full Name");
        add_new_fc.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setText("Address *");
        add_new_fc.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 60, -1));

        fcname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fcnameActionPerformed(evt);
            }
        });
        add_new_fc.add(fcname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 280, 25));

        jLabel32.setText("Complete  Address");
        add_new_fc.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        fccity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fccityActionPerformed(evt);
            }
        });
        add_new_fc.add(fccity, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 280, 25));

        jLabel33.setText("Province & Country");
        add_new_fc.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 340, 110, -1));
        add_new_fc.add(fccountry, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, 280, 25));

        jLabel34.setText("City");
        add_new_fc.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 30, -1));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setText("Contact*");
        add_new_fc.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 70, 20));

        fccontact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fccontactActionPerformed(evt);
            }
        });
        add_new_fc.add(fccontact, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 280, 25));

        jLabel36.setText("Phone Number");
        add_new_fc.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, -1, -1));

        jLabel37.setText("Email");
        add_new_fc.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 430, 70, -1));
        add_new_fc.add(fcemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 400, 280, 25));

        jButton2.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        add_new_fc.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 470, 70, -1));

        jButton3.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton3.setText("Register");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add_new_fc.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 470, 80, -1));

        fcimage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        fcimage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fcimageMouseClicked(evt);
            }
        });
        add_new_fc.add(fcimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, 70, 70));

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Image");
        add_new_fc.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, 70, 20));

        jPanel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel20MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel20MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel20MouseExited(evt);
            }
        });
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        back.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jPanel20.add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 27, 18));

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel45.setText("Back");
        jPanel20.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 40, 18));

        add_new_fc.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 20));

        faculty.add(add_new_fc, "card3");

        admin.add(faculty, "card3");

        student.setLayout(new java.awt.CardLayout());

        st_main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sttable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Contact", "Email", "Course"
            }
        ));
        jScrollPane2.setViewportView(sttable);

        st_main.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 650, 420));

        jButton4.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton4.setText("Add New Student");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        st_main.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(537, 40, 130, 30));

        jLabel38.setText("Search");
        st_main.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 46, 50, 20));

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });
        st_main.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 42, 160, 30));

        student.add(st_main, "card6");

        add_new_st.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(102, 102, 255));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("REGISTRATION FORM");
        jPanel13.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 40));

        jLabel40.setText("PLEASE FILL IN THE FORM BELOW");
        jPanel13.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 36, 190, 20));

        add_new_st.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 690, 70));

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel41.setText("Information  *");
        add_new_st.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 100, 20));

        staddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                staddressActionPerformed(evt);
            }
        });
        add_new_st.add(staddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 580, 25));

        jLabel42.setText("Full Name");
        add_new_st.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel43.setText("Course *");
        add_new_st.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 305, 60, -1));

        stname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stnameActionPerformed(evt);
            }
        });
        add_new_st.add(stname, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 180, 25));

        jLabel44.setText("Complete  Address");
        add_new_st.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        jLabel46.setText("Select Course");
        add_new_st.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 80, -1));

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel47.setText("Contact*");
        add_new_st.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 70, 20));

        stphone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stphoneActionPerformed(evt);
            }
        });
        add_new_st.add(stphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 280, 25));

        jLabel48.setText("Phone Number");
        add_new_st.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, -1, -1));

        jLabel49.setText("Email");
        add_new_st.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 440, 70, -1));
        add_new_st.add(stemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 410, 280, 25));

        jButton5.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton5.setText("Clear");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        add_new_st.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 470, 70, -1));

        jButton6.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton6.setText("Register");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        add_new_st.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 470, 80, -1));

        stimg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        stimg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stimgMouseClicked(evt);
            }
        });
        add_new_st.add(stimg, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, 70, 70));

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Image");
        add_new_st.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, 70, 20));

        stfname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stfnameActionPerformed(evt);
            }
        });
        add_new_st.add(stfname, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, 180, 25));

        jLabel51.setText("Father Name");
        add_new_st.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, -1, -1));

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel52.setText("Address *");
        add_new_st.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 60, -1));

        stcomo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Course" }));
        stcomo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stcomoMouseClicked(evt);
            }
        });
        stcomo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stcomoActionPerformed(evt);
            }
        });
        add_new_st.add(stcomo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 327, 280, 25));

        jPanel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel21MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel21MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel21MouseExited(evt);
            }
        });
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        back1.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jPanel21.add(back1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 27, 18));

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel53.setText("Back");
        jPanel21.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 40, 18));

        add_new_st.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 20));

        courses.setEditable(false);
        courses.setBorder(null);
        add_new_st.add(courses, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 327, 280, 25));

        student.add(add_new_st, "card3");

        admin.add(student, "card4");

        course.setLayout(new java.awt.CardLayout());

        course_main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        coursetable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Teacher", "No. Enrolled Student"
            }
        ));
        jScrollPane3.setViewportView(coursetable);

        course_main.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 650, 420));

        jButton9.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton9.setText("Add New Course");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        course_main.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(537, 40, 130, 30));

        jLabel68.setText("Search");
        course_main.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 46, 50, 20));

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });
        course_main.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 42, 160, 30));

        course.add(course_main, "card6");

        add_new_cr.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(102, 102, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("REGISTRATION FORM");
        jPanel15.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 40));

        jLabel55.setText("PLEASE FILL IN THE FORM BELOW");
        jPanel15.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 36, 190, 20));

        add_new_cr.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 690, 70));

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel56.setText("Information  *");
        add_new_cr.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 100, 20));

        jLabel57.setText("Course Fullname");
        add_new_cr.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, -1, -1));

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel58.setText("Course Teacher *");
        add_new_cr.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 100, -1));

        course1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                course1ActionPerformed(evt);
            }
        });
        add_new_cr.add(course1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 270, 25));

        jButton8.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jButton8.setText("Register");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        add_new_cr.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 330, 80, -1));

        courseteacher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Course Teacher" }));
        courseteacher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                courseteacherMouseClicked(evt);
            }
        });
        add_new_cr.add(courseteacher, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 280, 25));

        jPanel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel23MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel23MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel23MouseExited(evt);
            }
        });
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        back2.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        jPanel23.add(back2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 27, 18));

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel67.setText("Back");
        jPanel23.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 40, 18));

        add_new_cr.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 70, 20));

        course.add(add_new_cr, "card3");

        admin.add(course, "card5");

        dashboard.add(admin, "card6");

        home.add(dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 690, 510));

        mini.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                miniMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                miniMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                miniMouseExited(evt);
            }
        });
        mini.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 25)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("–");
        mini.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 37));

        home.add(mini, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, 50, 37));

        cancle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancleMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancleMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancleMouseExited(evt);
            }
        });
        cancle.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 25)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("×");
        cancle.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 35));

        home.add(cancle, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, -1, 37));

        main.add(home, "card3");

        login.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close.setBackground(new java.awt.Color(255, 255, 255));
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeMouseExited(evt);
            }
        });
        close.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 25)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("×");
        close.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 25));

        login.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, 40, 25));

        minimize.setBackground(new java.awt.Color(255, 255, 255));
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                minimizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                minimizeMouseExited(evt);
            }
        });
        minimize.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 25)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("–");
        minimize.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 25));

        login.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, 40, 25));
        login.add(lock_icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(398, 8, 15, 15));

        sky_logo.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
        sky_logo.setForeground(new java.awt.Color(0, 0, 153));
        sky_logo.setText("Secure Attendence System");
        login.add(sky_logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 8, 190, -1));

        titlr_logo.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        titlr_logo.setText("Attendence System by Face");
        login.add(titlr_logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 370, 320, 50));
        login.add(side_image, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 540));

        panel1.setBackground(new java.awt.Color(255, 255, 255));
        panel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panel1MouseDragged(evt);
            }
        });
        panel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel1MousePressed(evt);
            }
        });
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        action_main.setLayout(new java.awt.CardLayout());

        logo.setBackground(new java.awt.Color(255, 255, 255));
        logo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Main/icon/tenor (3) (3).gif"))); // NOI18N
        logo.add(ton, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 40, 140, 150));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Starting modules");
        logo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(105, 200, 120, 20));

        lod.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        logo.add(lod, new org.netbeans.lib.awtextra.AbsoluteConstraints(228, 202, 50, 20));

        action_main.add(logo, "card5");

        pass_penl.setBackground(new java.awt.Color(255, 255, 255));
        pass_penl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(lb1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 6, 16, 18));

        lb2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lb2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lb2MouseEntered(evt);
            }
        });
        jPanel2.add(lb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 6, 18, 18));

        pass1.setBackground(new java.awt.Color(240, 240, 240));
        pass1.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        pass1.setForeground(new java.awt.Color(153, 153, 153));
        pass1.setText("Enter Password");
        pass1.setBorder(null);
        pass1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pass1FocusGained(evt);
            }
        });
        pass1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pass1MouseClicked(evt);
            }
        });
        pass1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pass1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pass1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pass1KeyTyped(evt);
            }
        });
        jPanel2.add(pass1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 3, 160, 24));

        pass_penl.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 110, 250, 30));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        user.setBackground(new java.awt.Color(240, 240, 240));
        user.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        user.setForeground(new java.awt.Color(153, 153, 153));
        user.setText("Enter Username");
        user.setBorder(null);
        user.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                userFocusGained(evt);
            }
        });
        user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userMouseClicked(evt);
            }
        });
        user.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                userKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                userKeyTyped(evt);
            }
        });
        jPanel3.add(user, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 3, 195, 24));
        jPanel3.add(lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 6, 22, 18));

        pass_penl.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 60, 250, 30));

        jPanel4.setBackground(new java.awt.Color(63, 173, 204));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel4MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel4MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel4MouseReleased(evt);
            }
        });
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Login");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 4, 230, 20));

        pass_penl.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 170, 250, 30));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        pass_penl.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 100, 30));
        pass_penl.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -20, -1, -1));

        err.setForeground(new java.awt.Color(204, 0, 0));
        pass_penl.add(err, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 230, 20));

        err1.setForeground(new java.awt.Color(204, 0, 0));
        pass_penl.add(err1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 130, 20));

        action_main.add(pass_penl, "card2");

        panel1.add(action_main, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 140, 360, 330));

        login.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 540));

        main.add(login, "card2");

        getContentPane().add(main, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 540));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        // TODO add your handling code here
        System.exit(0);
    }//GEN-LAST:event_closeMouseClicked

    private void closeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseEntered
        // TODO add your handling code here:
        close.setToolTipText("Close program");
        color = close.getBackground();
        c = close.getForeground();
        jLabel11.setForeground(Color.white);
        close.setBackground(Color.red);
    }//GEN-LAST:event_closeMouseEntered

    private void closeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseExited
        // TODO add your handling code here:
        jLabel11.setForeground(c);
        close.setBackground(color);
    }//GEN-LAST:event_closeMouseExited

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void minimizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseEntered
        // TODO add your handling code here:
        c = minimize.getBackground();
        minimize.setBackground(new Color(204,204,204));
        minimize.setToolTipText("Minimize Program");
    }//GEN-LAST:event_minimizeMouseEntered

    private void minimizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseExited
        // TODO add your handling code here:
        minimize.setBackground(c);
    }//GEN-LAST:event_minimizeMouseExited

    private void lb2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb2MouseClicked
        // TODO add your handling code here:
        if(!(pass1.getText().equals("Enter Password"))){
            if(seee==0){
                pass1.setEchoChar((char)0);
                URL path30=getClass().getResource("icon/unsee.jpg");
                ImageIcon photo30=new ImageIcon(new ImageIcon(path30).getImage().getScaledInstance(lb2.getWidth(),lb2.getHeight(),java.awt.Image.SCALE_SMOOTH));
                lb2.setIcon(photo30);
                seee=1;
            }
            else if(seee==1){
                pass1.setEchoChar('•');
                URL path30=getClass().getResource("icon/see.png");
                ImageIcon photo30=new ImageIcon(new ImageIcon(path30).getImage().getScaledInstance(lb2.getWidth(),lb2.getHeight(),java.awt.Image.SCALE_SMOOTH));
                lb2.setIcon(photo30);
                seee=0;
            }
        }
    }//GEN-LAST:event_lb2MouseClicked

    private void lb2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lb2MouseEntered
        // TODO add your handling code here:
        if(seee==0){
            lb2.setToolTipText("Show Password");
        }else{
            lb2.setToolTipText("Hide Password");
        }
    }//GEN-LAST:event_lb2MouseEntered

    private void pass1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pass1FocusGained
        // TODO add your handling code here:
        err1.setText(null);
    }//GEN-LAST:event_pass1FocusGained

    private void pass1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pass1MouseClicked
        // TODO add your handling code here:
        if(pass1.getText().equals("Enter Password")){
            pass1.setCaretPosition(0);
        }
    }//GEN-LAST:event_pass1MouseClicked

    private void pass1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass1KeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jPanel4.setBackground(Color.green);
            jLabel6.setForeground(Color.white);
            jLabel6.setFont(new Font("Tahoma",1,14));
            sign();
        }
        else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_V) {
            if(pass1.getText().equals("Enter Password")){
                pass1.setText(null);
                pass1.setEchoChar('•');
                pass1.setForeground(Color.black);
                err1.setText(null);
            }
        }
        else if(pass1.getText().equals("Enter Password")){
            pass1.setCaretPosition(0);
        }
    }//GEN-LAST:event_pass1KeyPressed

    private void pass1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass1KeyReleased
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            jPanel4.setBackground(new Color(63,173,204));
            jLabel6.setForeground(Color.black);
            jLabel6.setFont(new Font("Tahoma",0,12));
        }
    }//GEN-LAST:event_pass1KeyReleased

    private void pass1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pass1KeyTyped
        // TODO add your handling code here:
        String dt=pass1.getText();
        char word=evt.getKeyChar();
        if(!((word==KeyEvent.VK_BACK_SPACE)||word==KeyEvent.VK_DELETE || word==KeyEvent.VK_ENTER)){
            if(dt.equals("Enter Password")){
                pass1.setText(null);
                pass1.setEchoChar('•');
                pass1.setForeground(Color.black);
                err1.setText(null);
            }
        }
        if(((word==KeyEvent.VK_BACK_SPACE)||word==KeyEvent.VK_DELETE)){
            if(dt.length()==0){
                pass1.setEchoChar((char)0);
                pass1.setText("Enter Password");
                pass1.setForeground(new Color(153,153,153));
                pass1.setCaretPosition(0);
                URL path30=getClass().getResource("icon/see.png");
                ImageIcon photo30=new ImageIcon(new ImageIcon(path30).getImage().getScaledInstance(lb2.getWidth(),lb2.getHeight(),java.awt.Image.SCALE_SMOOTH));
                lb2.setIcon(photo30);
                seee=0;
            }
        }
    }//GEN-LAST:event_pass1KeyTyped

    private void userFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_userFocusGained
        // TODO add your handling code here:
        err.setText(null);
    }//GEN-LAST:event_userFocusGained

    private void userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userMouseClicked
        // TODO add your handling code here:
        if(user.getText().equals("Enter Username")){
            user.setCaretPosition(0);
        }
    }//GEN-LAST:event_userMouseClicked

    private void userKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            pass1.requestFocus();
            pass1.setCaretPosition(0);
        }
        else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_V) {
            if(user.getText().equals("Enter Username")){
                user.setText(null);
                user.setForeground(Color.black);
                err.setText(null);
            }
        }
        else if(user.getText().equals("Enter Username")){
            user.setCaretPosition(0);
        }
    }//GEN-LAST:event_userKeyPressed

    private void userKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_userKeyTyped
        // TODO add your handling code here:
        String dt=user.getText();
        char word=evt.getKeyChar();
        if(!((word==KeyEvent.VK_BACK_SPACE)||word==KeyEvent.VK_DELETE || word==KeyEvent.VK_ENTER)){
            if(dt.equals("Enter Username")){
                user.setText(null);
                user.setForeground(Color.black);
                err.setText(null);
            }
        }
        if(((word==KeyEvent.VK_BACK_SPACE)||word==KeyEvent.VK_DELETE)){
            if(dt.length()==0){
                user.setText("Enter Username");
                user.setForeground(new Color(153,153,153));
                user.setCaretPosition(0);
            }
        }
    }//GEN-LAST:event_userKeyTyped

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        // TODO add your handling code here:
        sign();
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
        // TODO add your handling code here:
        jPanel4.setBackground(Color.blue);
        jLabel6.setForeground(Color.white);
        jLabel6.setFont(new Font("Tahoma",1,14));
    }//GEN-LAST:event_jPanel4MouseEntered

    private void jPanel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseExited
        // TODO add your handling code here:
        jPanel4.setBackground(new Color(63,173,204));
        jLabel6.setForeground(Color.black);
        jLabel6.setFont(new Font("Tahoma",0,12));
    }//GEN-LAST:event_jPanel4MouseExited

    private void jPanel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MousePressed
        // TODO add your handling code here:
        jPanel4.setBackground(Color.green);
        jLabel6.setForeground(Color.white);
        jLabel6.setFont(new Font("Tahoma",1,14));
    }//GEN-LAST:event_jPanel4MousePressed

    private void jPanel4MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseReleased
        // TODO add your handling code here:
        jPanel4.setBackground(new Color(63,173,204));
        jLabel6.setForeground(Color.black);
        jLabel6.setFont(new Font("Tahoma",0,12));
    }//GEN-LAST:event_jPanel4MouseReleased

    private void panel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel1MouseDragged
        // TODO add your handling code here:
        int x=evt.getXOnScreen();
        int y=evt.getYOnScreen();
        this.setLocation(x-xm, y-ym);
    }//GEN-LAST:event_panel1MouseDragged

    private void panel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel1MousePressed
        // TODO add your handling code here:
        xm=evt.getX();
        ym=evt.getY();
    }//GEN-LAST:event_panel1MousePressed

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
        // TODO add your handling code here:
        cl=jPanel1.getBackground();
        jPanel1.setBackground(Color.blue);
    }//GEN-LAST:event_jPanel1MouseEntered

    private void jPanel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseExited
        // TODO add your handling code here:
        jPanel1.setBackground(cl);
    }//GEN-LAST:event_jPanel1MouseExited

    private void jPanel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseEntered
        // TODO add your handling code here:
        cl=jPanel5.getBackground();
        jPanel5.setBackground(Color.blue);
    }//GEN-LAST:event_jPanel5MouseEntered

    private void jPanel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseEntered
        // TODO add your handling code here:
        cl=jPanel7.getBackground();
        jPanel7.setBackground(Color.blue);
    }//GEN-LAST:event_jPanel7MouseEntered

    private void jPanel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseEntered
        // TODO add your handling code here:
        cl=jPanel8.getBackground();
        jPanel8.setBackground(Color.blue);
    }//GEN-LAST:event_jPanel8MouseEntered

    private void jPanel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseExited
        // TODO add your handling code here:
        jPanel5.setBackground(cl);
    }//GEN-LAST:event_jPanel5MouseExited

    private void jPanel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseExited
        // TODO add your handling code here:
        jPanel7.setBackground(cl);
    }//GEN-LAST:event_jPanel7MouseExited

    private void jPanel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseExited
        // TODO add your handling code here:
        jPanel8.setBackground(cl);
    }//GEN-LAST:event_jPanel8MouseExited

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
        cl=jPanel1.getBackground();
        jPanel1.setBackground(cl);
        jPanel8.setBackground(new Color(51,102,255));
        jPanel7.setBackground(new Color(51,102,255));
        jPanel5.setBackground(new Color(51,102,255));
        jLabel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jLabel21.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jLabel20.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jLabel22.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        main_dash.setVisible(true);
        faculty.setVisible(false);
        student.setVisible(false);
        course.setVisible(false);
        dashboard_value(teacher_value,"SELECT count(id) as total FROM Teacher");
        dashboard_value(course_value,"SELECT count(name) as total FROM Course");
        dashboard_value(student_value,"SELECT count(id) as total FROM Student");
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked
        // TODO add your handling code here:
        cl=jPanel5.getBackground();
        jPanel5.setBackground(cl);
        jPanel8.setBackground(new Color(51,102,255));
        jPanel7.setBackground(new Color(51,102,255));
        jPanel1.setBackground(new Color(51,102,255));
        jLabel20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jLabel21.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jLabel22.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jLabel19.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        main_dash.setVisible(false);
        faculty.setVisible(true);
        student.setVisible(false);
        course.setVisible(false);
        Teacher_data("none","none");
    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        // TODO add your handling code here:
        cl=jPanel7.getBackground();
        jPanel7.setBackground(cl);
        jPanel8.setBackground(new Color(51,102,255));
        jPanel1.setBackground(new Color(51,102,255));
        jPanel5.setBackground(new Color(51,102,255));
        jLabel21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jLabel22.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jLabel20.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jLabel19.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        main_dash.setVisible(false);
        faculty.setVisible(false);
        student.setVisible(true);
        course.setVisible(false);
        Student_data("none","none");
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        // TODO add your handling code here:
        cl=jPanel8.getBackground();
        jPanel8.setBackground(cl);
        jPanel1.setBackground(new Color(51,102,255));
        jPanel7.setBackground(new Color(51,102,255));
        jPanel5.setBackground(new Color(51,102,255));
        jLabel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jLabel21.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jLabel20.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jLabel19.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        main_dash.setVisible(false);
        faculty.setVisible(false);
        student.setVisible(false);
        course.setVisible(true);
         Course_data("none","none");
    }//GEN-LAST:event_jPanel8MouseClicked

    private void miniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miniMouseClicked
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_miniMouseClicked

    private void miniMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miniMouseEntered
        // TODO add your handling code here:
        c = mini.getBackground();
        mini.setBackground(new Color(204, 204, 204));
        mini.setToolTipText("Minimize Program");
    }//GEN-LAST:event_miniMouseEntered

    private void miniMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_miniMouseExited
        // TODO add your handling code here:
        mini.setBackground(c);
    }//GEN-LAST:event_miniMouseExited

    private void cancleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancleMouseClicked
        // TODO add your handling code here:
        int u = JOptionPane.showConfirmDialog(null, "Confirm to Cancle?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (u == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_cancleMouseClicked

    private void cancleMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancleMouseEntered
        // TODO add your handling code here:
        color = cancle.getBackground();
        c = cancle.getForeground();
        jLabel11.setForeground(Color.white);
        cancle.setBackground(Color.red);
        cancle.setToolTipText("Close Program");
    }//GEN-LAST:event_cancleMouseEntered

    private void cancleMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancleMouseExited
        // TODO add your handling code here:
        jLabel11.setForeground(c);
        cancle.setBackground(color);
    }//GEN-LAST:event_cancleMouseExited

    private void homeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeMousePressed
        // TODO add your handling code here:
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_homeMousePressed

    private void homeMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeMouseDragged
        // TODO add your handling code here:
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_homeMouseDragged

    private void fcaddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fcaddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fcaddressActionPerformed

    private void fcnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fcnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fcnameActionPerformed

    private void fccityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fccityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fccityActionPerformed

    private void fccontactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fccontactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fccontactActionPerformed

    private void d3_2dash_h(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d3_2dash_h
        // TODO add your handling code here:
            d3_1.setBackground(new Color(240,240,240));
            student_value.setForeground(Color.black);
            d3_l2.setForeground(Color.black);
            d3_2.setBackground(new Color(102,102,255));
            d3_l3.setForeground(Color.white);
            d3_l3.setFont(new java.awt.Font("Tahoma", 1, 14));
            d3_2.setToolTipText("View record details");
    }//GEN-LAST:event_d3_2dash_h

    private void d3_2dash_e(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d3_2dash_e
        // TODO add your handling code here:
            d3_1.setBackground(new Color(102,102,255));
            student_value.setForeground(Color.white);
            d3_l2.setForeground(Color.white);
            d3_2.setBackground(new Color(240,240,240));
            d3_l3.setForeground(Color.black);
            d3_l3.setFont(new java.awt.Font("Tahoma", 1, 12));
    }//GEN-LAST:event_d3_2dash_e

    private void d2_2dash_h(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d2_2dash_h
        // TODO add your handling code here:
            d2_1.setBackground(new Color(240,240,240));
            course_value.setForeground(Color.black);
            d2_l2.setForeground(Color.black);
            d2_2.setBackground(new Color(102,102,255));
            d2_l3.setForeground(Color.white);
            d2_l3.setFont(new java.awt.Font("Tahoma", 1, 14));
            d2_2.setToolTipText("View record details");
    }//GEN-LAST:event_d2_2dash_h

    private void d2_2dash_e(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d2_2dash_e
        // TODO add your handling code here:
            d2_1.setBackground(new Color(102,102,255));
            course_value.setForeground(Color.white);
            d2_l2.setForeground(Color.white);
            d2_2.setBackground(new Color(240,240,240));
            d2_l3.setForeground(Color.black);
            d2_l3.setFont(new java.awt.Font("Tahoma", 1, 12));
    }//GEN-LAST:event_d2_2dash_e

    private void d1_2dash_h(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d1_2dash_h
        // TODO add your handling code here:
            d1_1.setBackground(new Color(240,240,240));
            teacher_value.setForeground(Color.black);
            d1_l2.setForeground(Color.black);
            d1_2.setBackground(new Color(102,102,255));
            d1_l3.setForeground(Color.white);
            d1_l3.setFont(new java.awt.Font("Tahoma", 1, 14));
            d1_2.setToolTipText("View record details");
    }//GEN-LAST:event_d1_2dash_h

    private void d1_2dash_e(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_d1_2dash_e
        // TODO add your handling code here:
            d1_1.setBackground(new Color(102,102,255));
            teacher_value.setForeground(Color.white);
            d1_l2.setForeground(Color.white);
            d1_2.setBackground(new Color(240,240,240));
            d1_l3.setForeground(Color.black);
            d1_l3.setFont(new java.awt.Font("Tahoma", 1, 12));
    }//GEN-LAST:event_d1_2dash_e
void clear_fc_form(){
    fcname.setText(null);
    fccity.setText(null);
    fccountry.setText(null);
    fcaddress.setText(null);
    fccontact.setText(null);
    fcemail.setText(null);
    fcimage.setIcon(null);
}
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        clear_fc_form();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling 
        String sql = "INSERT INTO Teacher (id,fullname,Address,city,country,contact,email,image,pswd) VALUES(?,?,?,?,?,?,?,?,?)";
        int intid=fetch_ID_value("fid");
        int status=intid;
        if(intid==0){
            intid=101;
        }
        String id=String.valueOf(intid);
        String name=fcname.getText();
        String address=fcaddress.getText();
        String city=fccity.getText();
        String country=fccountry.getText();
        String contact=fccontact.getText();
        String email=fcemail.getText();
        String pass="riphah"+id;
        if(fcname.getText().isEmpty() || fcaddress.getText().isEmpty() || fccity.getText().isEmpty() || fccountry.getText().isEmpty() || fccontact.getText().isEmpty() || fcemail.getText().isEmpty() || fcimage.getIcon()==null){
            JOptionPane.showMessageDialog(this,"All fields required","Error",JOptionPane.ERROR_MESSAGE);
        }else{
            try (PreparedStatement pstmt = con.con().prepareStatement(sql)) {
                pstmt.setString(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, address);
                pstmt.setString(4, city);
                pstmt.setString(5, country);
                pstmt.setString(6, contact);
                pstmt.setString(7, email);
                pstmt.setString(8, id);
                 pstmt.setString(9, pass);
               int rs= pstmt.executeUpdate();
               if(rs>0){
                   JOptionPane.showMessageDialog(this,"Data added successfully","Info",JOptionPane.INFORMATION_MESSAGE);
                   String placeToSaveFile = "src/Teaacher_images/"+id+".png";
                   Files.copy(path.toPath(), new File(placeToSaveFile).toPath(), StandardCopyOption.REPLACE_EXISTING);
                   clear_fc_form();
                   pstmt.close();
                   String quiry;
                   if(status==0){
                       quiry="INSERT INTO Pid(fid,sid,cor) values('"+id+"','0','unknown')";
                   }else{
                       quiry="UPDATE Pid SET fid='"+id+"'";
                   }
                   try (PreparedStatement pst = con.con().prepareStatement(quiry)) {
                        pst.executeUpdate();
                        pst.close();
                    }
               }else{
                   JOptionPane.showMessageDialog(this,"Data is not added","Error",JOptionPane.ERROR_MESSAGE);
               }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void stnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stnameActionPerformed
void clear_form_st(){
    stname.setText(null);
    stfname.setText(null);
    staddress.setText(null);
    stphone.setText(null);
    stemail.setText(null);
    courses.setText(null);
    //stcomo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Course"}));
    stimg.setIcon(null);
}
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        String sql = "INSERT INTO Student (id,fullname,fathername,Address,course,contact,email) VALUES(?,?,?,?,?,?,?)";
        int intid=fetch_ID_value("sid");
        int status=intid;
        if (intid==1){
            intid=1101;
        }
        String id=String.valueOf(intid);
        String name=stname.getText();
        String fname=stfname.getText();
        String address=staddress.getText();
        String course=courses.getText();
        String contact=stphone.getText();
        String email=stemail.getText();
        String image_name=name+"_"+id;
        if(stname.getText().isEmpty() || staddress.getText().isEmpty() || courses.getText().isEmpty() || stfname.getText().isEmpty() || stphone.getText().isEmpty() || stemail.getText().isEmpty() || stimg.getIcon()==null){
            JOptionPane.showMessageDialog(this,"All fields required","Error",JOptionPane.ERROR_MESSAGE);
        }else{
            try (PreparedStatement pstmt = con.con().prepareStatement(sql)) {
                pstmt.setString(1, id);
                pstmt.setString(2, name);
                pstmt.setString(3, fname);
                pstmt.setString(4, address);
                pstmt.setString(5, course);
                pstmt.setString(6, contact);
                pstmt.setString(7, email);
               int rs= pstmt.executeUpdate();
               if(rs>0){
                   JOptionPane.showMessageDialog(this,"Data added successfully","Info",JOptionPane.INFORMATION_MESSAGE);
                   String placeToSaveFile = "src/ImagesAttendance/"+image_name+".png";
                   Files.copy(path.toPath(), new File(placeToSaveFile).toPath(), StandardCopyOption.REPLACE_EXISTING);
                   clear_form_st();
                   pstmt.close();
                   String quiry;
                   if(status==1){
                       quiry="INSERT INTO Pid(sid) values('"+id+"')";
                   }else{
                       quiry="UPDATE Pid SET sid='"+id+"'";
                   }
                   try (PreparedStatement pst = con.con().prepareStatement(quiry)) {
                        pst.executeUpdate();
                        pst.close();
                    }
               }else{
                   JOptionPane.showMessageDialog(this,"Data is not added","Error",JOptionPane.ERROR_MESSAGE);
               }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void fcimageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fcimageMouseClicked
        // TODO add your handling code here:
        JFileChooser ch=new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("jpg,png","jpg","png");
        ch.setFileFilter(filter);
        int choice=ch.showOpenDialog(this);
        if(choice==JFileChooser.APPROVE_OPTION){
            path=ch.getSelectedFile();
            String p=path.getAbsolutePath();
            ImageIcon pc=new ImageIcon(new ImageIcon(p).getImage().getScaledInstance(fcimage.getWidth(),fcimage.getHeight(),java.awt.Image.SCALE_SMOOTH));
            fcimage.setIcon(pc);
        }
    }//GEN-LAST:event_fcimageMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        fc_main.setVisible(false);
        add_new_fc.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPanel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseClicked
        // TODO add your handling code here:
        clear_fc_form();
        fc_main.setVisible(true);
        add_new_fc.setVisible(false);
        Teacher_data("none","none");

    }//GEN-LAST:event_jPanel20MouseClicked

    private void jPanel20MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseEntered
        // TODO add your handling code here:
        jLabel41.setForeground(Color.blue);
    }//GEN-LAST:event_jPanel20MouseEntered

    private void jPanel20MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseExited
        // TODO add your handling code here:
        
        jLabel41.setForeground(Color.black);
    }//GEN-LAST:event_jPanel20MouseExited

    private void fcsearchd1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fcsearchd1KeyReleased
        // TODO add your handling code here:
        String word=fcsearchd1.getText();
        Teacher_data("like",word);
    }//GEN-LAST:event_fcsearchd1KeyReleased

    private void jPanel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel21MouseClicked
        // TODO add your handling code here:
        Student_data("none","none");
        st_main.setVisible(true);
        add_new_st.setVisible(false);
        clear_form_st();
    }//GEN-LAST:event_jPanel21MouseClicked

    private void jPanel21MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel21MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel21MouseEntered

    private void jPanel21MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel21MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel21MouseExited

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        st_main.setVisible(false);
        add_new_st.setVisible(true);
        stcomo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Course"}));
        Statement pst = null;
        ResultSet rs = null ;
        try {
        pst = con.con().createStatement();
        String qury = "SELECT * FROM Course";
        rs = pst.executeQuery(qury);
        while(rs.next()){
            String v = rs.getString("name");
            stcomo.addItem(v); 
        }
    }catch(SQLException e){
        System.out.print(e);
    }catch (ClassNotFoundException ex) {
            Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
     }finally{  
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void stcomoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stcomoMouseClicked
        // TODO add your handling code here:
        stcomo.addItem("hy");
    }//GEN-LAST:event_stcomoMouseClicked

    private void staddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_staddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_staddressActionPerformed

    private void stfnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stfnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stfnameActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        clear_form_st();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void stphoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stphoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stphoneActionPerformed

    private void course1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_course1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_course1ActionPerformed
int fetch_course_stu(String type){
        int val = 0;
    try {
        Statement pst = con.con().createStatement();
        String qury = "SELECT * FROM Course where name'"+type+"'";
        ResultSet rs = pst.executeQuery(qury);
        if(rs.next()){
            String v = rs.getString(qury);
            if(v.length()==0 || v.isEmpty() || v==null){
                val=0;
            }else{
                val=Integer.parseInt(rs.getString("NoofStu"));
                val++;
            }
            rs.close();pst.close();
        }
    }catch(SQLException e){
        System.out.print(e);
    }catch (ClassNotFoundException ex) {
            Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
     }
    return val;
}
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        
        String sql = "INSERT INTO Course (name,Teacher) VALUES(?,?)";
        String name=course1.getText();
        String address=courseteacher.getSelectedItem().toString();
        if(course1.getText().isEmpty() || courseteacher.getSelectedItem().toString()=="Select Course Teacher"){
            JOptionPane.showMessageDialog(this,"All fields required","Error",JOptionPane.ERROR_MESSAGE);
        }else{
            try (PreparedStatement pstmt = con.con().prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, address);
               int rs= pstmt.executeUpdate();
               if(rs>0){
                   JOptionPane.showMessageDialog(this,"Course added successfully","Info",JOptionPane.INFORMATION_MESSAGE);
                   clear_fc_form();
                   pstmt.close();
                   course1.setText(null);
               }else{
                   JOptionPane.showMessageDialog(this,"Course is not added","Error",JOptionPane.ERROR_MESSAGE);
               }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void courseteacherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_courseteacherMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_courseteacherMouseClicked

    private void jPanel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel23MouseClicked
        // TODO add your handling code here:
        course_main.setVisible(true);
        add_new_cr.setVisible(false);
        Course_data("none","none");
    }//GEN-LAST:event_jPanel23MouseClicked

    private void jPanel23MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel23MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel23MouseEntered

    private void jPanel23MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel23MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel23MouseExited

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        course1.setText(null);
        courseteacher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Select Course Teacher"}));
        course_main.setVisible(false);
        add_new_cr.setVisible(true);
        Statement pst = null;
        ResultSet rs = null ;
        try {
        pst = con.con().createStatement();
        String qury = "SELECT * FROM Teacher";
        rs = pst.executeQuery(qury);
        while(rs.next()){
            String v = rs.getString("fullname");
            courseteacher.addItem(v); 
        }
    }catch(SQLException e){
        System.out.print(e);
    }catch (ClassNotFoundException ex) {
            Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
     }finally{  
            try {
                pst.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
         Course_data("like",jTextField4.getText());
    }//GEN-LAST:event_jTextField4KeyReleased

    private void stimgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stimgMouseClicked
        // TODO add your handling code here:
        JFileChooser ch=new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("jpg,png","jpg","png");
        ch.setFileFilter(filter);
        int choice=ch.showOpenDialog(this);
        if(choice==JFileChooser.APPROVE_OPTION){
            path=ch.getSelectedFile();
            String p=path.getAbsolutePath();
            ImageIcon pc=new ImageIcon(new ImageIcon(p).getImage().getScaledInstance(stimg.getWidth(),stimg.getHeight(),java.awt.Image.SCALE_SMOOTH));
            stimg.setIcon(pc);
        }
    }//GEN-LAST:event_stimgMouseClicked

    private void stcomoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stcomoActionPerformed
        // TODO add your handling code here:
        String vl=stcomo.getSelectedItem().toString();
        if(!vl.equals("Select Course"))
        if(courses.getText().isEmpty()){
            courses.setText(vl);
        }else{
        courses.setText(courses.getText()+","+vl);
        }
    }//GEN-LAST:event_stcomoActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        // TODO add your handling code here:
        Student_data("like",jTextField2.getText());
    }//GEN-LAST:event_jTextField2KeyReleased

    private void fcl_dahsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fcl_dahsMouseClicked
        // TODO add your handling code here:
        cl=fcl_dahs.getBackground();
        fcl_dahs.setBackground(cl);
        fc_atten.setBackground(new Color(51,102,255));
        jLabel60.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jLabel62.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        fc_dash.setVisible(true);
        attendence.setVisible(false);
    }//GEN-LAST:event_fcl_dahsMouseClicked

    private void fcl_dahsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fcl_dahsMouseEntered
        // TODO add your handling code here:
        cl=fcl_dahs.getBackground();
        fcl_dahs.setBackground(Color.blue);
    }//GEN-LAST:event_fcl_dahsMouseEntered

    private void fcl_dahsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fcl_dahsMouseExited
        // TODO add your handling code here:
        fcl_dahs.setBackground(cl);
    }//GEN-LAST:event_fcl_dahsMouseExited

    private void fc_attenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fc_attenMouseClicked
        // TODO add your handling code here:
        cl=fc_atten.getBackground();
        fc_atten.setBackground(cl);
        fcl_dahs.setBackground(new Color(51,102,255));
        jLabel62.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jLabel60.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        fc_dash.setVisible(false);
        attendence.setVisible(true);
    }//GEN-LAST:event_fc_attenMouseClicked

    private void fc_attenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fc_attenMouseEntered
        // TODO add your handling code here:
        cl=fc_atten.getBackground();
        fc_atten.setBackground(Color.blue);
    }//GEN-LAST:event_fc_attenMouseEntered

    private void fc_attenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fc_attenMouseExited
        // TODO add your handling code here:
        fc_atten.setBackground(cl);
    }//GEN-LAST:event_fc_attenMouseExited

    private void fcsearchd2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fcsearchd2KeyReleased
        // TODO add your handling code here:
        attendence_data("like",fcsearchd2.getText());
    }//GEN-LAST:event_fcsearchd2KeyReleased

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        if(at1.getSelectedItem().toString().equals("Select Course")){
            JOptionPane.showMessageDialog(this, "Please select course");
            
        }else{
            String cour=at1.getSelectedItem().toString();
            String quiry="UPDATE Pid SET cor='"+cour+"'";
            try (PreparedStatement pst = con.con().prepareStatement(quiry)) {
                 pst.executeUpdate();
                 pst.close();
             } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            try{
                String py="AttendanceSystem";
                ProcessBuilder pb = new ProcessBuilder("python", py + ".py");
                String cmd="src";
                pb.directory(new File(cmd));
                pb.redirectError();
                pb.start();
                }catch(IOException e){
                    System.out.print(e);
                }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        attendence_data("none","none");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void fcsearchd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fcsearchd2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fcsearchd2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Auto_Attendence_System_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Auto_Attendence_System_GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel action_main;
    private javax.swing.JPanel add_new_cr;
    private javax.swing.JPanel add_new_fc;
    private javax.swing.JPanel add_new_st;
    private javax.swing.JPanel admin;
    private javax.swing.JPanel admin_bar;
    private javax.swing.JComboBox<String> at1;
    private javax.swing.JTable atn_table;
    private javax.swing.JPanel attendence;
    private javax.swing.JLabel back;
    private javax.swing.JLabel back1;
    private javax.swing.JLabel back2;
    private javax.swing.JPanel cancle;
    private javax.swing.JLabel card1;
    private javax.swing.JLabel card2;
    private javax.swing.JLabel card3;
    private javax.swing.JLabel card4;
    private javax.swing.JPanel close;
    private javax.swing.JPanel course;
    private javax.swing.JTextField course1;
    private javax.swing.JPanel course_main;
    public javax.swing.JLabel course_value;
    public javax.swing.JLabel course_value1;
    private javax.swing.JTextField courses;
    private javax.swing.JTable coursetable;
    private javax.swing.JComboBox<String> courseteacher;
    private javax.swing.JPanel d1_1;
    private javax.swing.JPanel d1_2;
    private javax.swing.JLabel d1_l2;
    private javax.swing.JLabel d1_l3;
    private javax.swing.JPanel d2_1;
    private javax.swing.JPanel d2_2;
    private javax.swing.JPanel d2_3;
    private javax.swing.JPanel d2_4;
    private javax.swing.JLabel d2_l2;
    private javax.swing.JLabel d2_l3;
    private javax.swing.JLabel d2_l4;
    private javax.swing.JLabel d2_l5;
    private javax.swing.JPanel d3_1;
    private javax.swing.JPanel d3_2;
    private javax.swing.JLabel d3_l2;
    private javax.swing.JLabel d3_l3;
    private javax.swing.JLabel dash;
    private javax.swing.JLabel dash_fc;
    private javax.swing.JPanel dashboard;
    private javax.swing.JLabel dsh;
    private javax.swing.JLabel dsh1;
    private javax.swing.JLabel dsh2;
    private javax.swing.JLabel dsh_fc;
    private javax.swing.JLabel err;
    private javax.swing.JLabel err1;
    private javax.swing.JPanel faculty;
    private javax.swing.JPanel faculty_main;
    private javax.swing.JPanel fc_atten;
    private javax.swing.JPanel fc_dash;
    private javax.swing.JPanel fc_main;
    private javax.swing.JTextField fcaddress;
    private javax.swing.JTextField fccity;
    private javax.swing.JTextField fccontact;
    private javax.swing.JTextField fccountry;
    private javax.swing.JTextField fcemail;
    private javax.swing.JLabel fcimage;
    private javax.swing.JPanel fcl_bar;
    private javax.swing.JPanel fcl_dahs;
    private javax.swing.JTextField fcname;
    private javax.swing.JTextField fcsearchd1;
    private javax.swing.JTextField fcsearchd2;
    private javax.swing.JTable fctable;
    private javax.swing.JPanel home;
    private Main.ImageAvatar imageAvatar1;
    private Main.ImageAvatar imageAvatar3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lb1;
    private javax.swing.JLabel lb2;
    private javax.swing.JLabel lock_icon;
    private javax.swing.JLabel lod;
    private javax.swing.JPanel login;
    private javax.swing.JPanel logo;
    private javax.swing.JPanel main;
    private javax.swing.JPanel main_dash;
    private javax.swing.JPanel mini;
    private javax.swing.JPanel minimize;
    private javax.swing.JPanel panel1;
    private javax.swing.JPasswordField pass1;
    private javax.swing.JPanel pass_penl;
    private javax.swing.JLabel side_image;
    private javax.swing.JPanel sidebar;
    private javax.swing.JLabel sky_logo;
    private javax.swing.JLabel st1;
    private javax.swing.JLabel st2;
    private javax.swing.JLabel st3;
    private javax.swing.JPanel st_main;
    private javax.swing.JTextField staddress;
    private javax.swing.JComboBox<String> stcomo;
    private javax.swing.JTextField stemail;
    private javax.swing.JTextField stfname;
    private javax.swing.JLabel stimg;
    private javax.swing.JTextField stname;
    private javax.swing.JTextField stphone;
    private javax.swing.JTable sttable;
    private javax.swing.JPanel student;
    public javax.swing.JLabel student_value;
    private javax.swing.JTable tc_course;
    public javax.swing.JLabel teacher_value;
    private javax.swing.JLabel titlr_logo;
    private javax.swing.JLabel ton;
    public static javax.swing.JTextField user;
    // End of variables declaration//GEN-END:variables

}
class RoundedPanel extends JPanel {
    protected int strokeSize = 0;
    protected Color shadowColor = Color.black;
    protected boolean shady = false;
    protected boolean highQuality =true;
    protected Dimension arcs;
    protected int shadowGap = 5;
    protected int shadowOffset = 4;
    protected int shadowAlpha = 0;
    public RoundedPanel(int d) {
       super();
       arcs = new Dimension(d, d);
       setOpaque(false);
      }
    @Override
    protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       int width = getWidth();
       int height = getHeight();
       int shadowGap = this.shadowGap;
       Graphics2D graphics = (Graphics2D) g;
       if (highQuality) {
           graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
           RenderingHints.VALUE_ANTIALIAS_ON);
       }
       shadowGap = 1;
       graphics.setColor(getBackground());
       graphics.fillRoundRect(0, 0, width - shadowGap,
       height - shadowGap, arcs.width, arcs.height);
       graphics.setColor(getForeground());
       graphics.setStroke(new BasicStroke(strokeSize));
       graphics.drawRoundRect(0, 0, width - shadowGap,
       height - shadowGap, arcs.width, arcs.height);
       graphics.setStroke(new BasicStroke());
   }
}
