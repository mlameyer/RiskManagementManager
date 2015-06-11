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
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextArea;

class ImportSettles extends Risk_Mgnt_Manager{
    private static String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
    private static String DB_URL = "jdbc:mysql://192.168.130.227/mgex_ops";
    private static String USER = "root";
    private static String PASS = "pass";
    private Connection connection;
    String message = null;
    boolean success = false;

    private JTextArea j;
    
    public ImportSettles(JTextArea j) {
       this.j = j;
    }
    
    public void DataTransfer(boolean a){
        if (a == true){
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 15);
        start.set(Calendar.MINUTE, 30);
        start.set(Calendar.SECOND, 0);
        Date time = start.getTime();
        
        Date currentTime = new Date();
        
        if(currentTime.after(time)){
            start.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask(){
     public void run(){
            ActivityLogger act = new ActivityLogger(j);
            act.logger("Settlement", 1, true);
     
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
                Class.forName("com.mysql.jdbc.Driver");

                connection = DriverManager.getConnection(DB_URL,USER,PASS);
                act.logger("sp_export_settles()", 3, true);
                cStmt = connection.prepareCall("call sp_export_settles()");
                cStmt.execute();

                //STEP 6: Clean-up environment
                cStmt.close();
                connection.close();
                } catch(SQLException sqle) {
              //     logger.logAction("SQLException : " + sqle);
		   act.logger("sp_export_settles()", 5, false);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    act.logger("sp_export_settles()", 5, false);
                }
            //    logger.logAction("Complete");
                act.logger("Settlement", 2, true);
                }
               },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // start.getTime()
            }
        }
 }
    

