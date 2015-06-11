/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk_mgnt_manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JTextArea;


public class ImportSecDeposit {
    private static String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
    private static String DB_URL = "jdbc:mysql://192.168.130.212/mgex_riskmgnt";
    private static String USER = "root";
    private static String PASS = "mgex";
    private Connection connection;
    
    private JTextArea j;
    
    public ImportSecDeposit(JTextArea j) {
       this.j = j;
    }
    
    public boolean startTime(){
        int timeCompare;
        boolean start = false;

        SimpleDateFormat sdf = new SimpleDateFormat("kkmmssS");
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        timeCompare = Integer.parseInt(sdf.format(cal.getTime()));      
        
        if (dayOfWeek != 7) {
            if(timeCompare >= 190000000 || timeCompare < 74500000){
                start = true;
            } else if (timeCompare >= 83000000 && timeCompare < 133000000){
                start = true; 
            } else {
                start = false;
            }
        } else {
            start = false;
        }
        
        return start;
    }
    
    public void DataTransfer2(boolean a){
        if (a == true){
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask(){
     public void run(){
         if(startTime() == true){
          ActivityLogger act = new ActivityLogger(j);
          act.logger("Security Deposits", 1, true);
     
            connection = null;
            try {
                    Class.forName(JDBC_CONNECTION_URL);
                    connection = DriverManager.getConnection(DB_URL,USER,PASS);
                  
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            CallableStatement cStmt = null;

            try{
                //STEP 2: Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");

                connection = DriverManager.getConnection(DB_URL,USER,PASS);
            
                act.logger("sp_import_secDeposit()", 3, true);
                
                cStmt = connection.prepareCall("call sp_import_secDeposit()");
                cStmt.execute();

                cStmt.close();
                connection.close();
                } catch(SQLException sqle) {
		   System.out.println("SQLException : " + sqle); 
                   act.logger("sp_import_secDeposit()", 5, false);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    act.logger("sp_import_secDeposit()", 5, false);
                }

                act.logger("Security Deposits", 2, true);
                }
                }
               },5 * 60 * 1000, 3*60*60*1000); //,0, 60*1000)
            }
        }
 }