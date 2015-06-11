/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import java.io.File;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import resources.CleanDirectory;
import resources.SendFileEmail;
import resources.ServerConn;

class ImportSpanPrice {
    private Connection connection;
    private Connection connection2;
    private File dir = new File("C:\\Risk_Reports\\RLTPrice_Alerts");
    private final String toRisk = "Risk@mgex.com";
    private final String toCompliance = "Compliance@mgex.com";
    private final String from = "Risk@mgex.com";
    private final String host = "mail.mgex.com";
    private final String subject = "RealTime Price Notification";
    private final String messagebody = "See attached file for RealTime Price Notification";
    private final String FileName = "C:\\Risk_Reports\\RLTPrice_Alerts\\RealTime_Price_Notification.csv";
    private final String FName = "RealTime_Price_Notification.csv";//C:\\Risk_Reports\\RLTPrice_Alerts\\RealTime_Price_Notification.csv
    
    private JTextArea j;
    
    public ImportSpanPrice(JTextArea j) {
       this.j = j;
    }
    
    public boolean startTime(){
        int timeCompare;
        boolean start;

        SimpleDateFormat sdf = new SimpleDateFormat("kkmmssS");
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        timeCompare = Integer.parseInt(sdf.format(cal.getTime()));      
        
        if (dayOfWeek != 7) {
            if(timeCompare >= 190000000 && timeCompare < 235959999){
                System.out.println("7 to 1159 if statement ImportSpanPrice");
                start = true;
            } else if (timeCompare >= 000000000 && timeCompare < 74500000){
                System.out.println("12 to 745 if statement ImportSpanPrice");
                start = true; 
            } else if (timeCompare >= 83000000 && timeCompare < 170000000){
                System.out.println("830 to 5 if statement ImportSpanPrice");
                start = true; 
            } else {
                start = false;
            }
        } else {
            start = false;
        }
        
        return start;
    }
    
    public void DataTransfer1(boolean a){
        if (a == true){
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask(){
     public void run(){
         if(startTime()){
             
          SendFileEmail email = new SendFileEmail(toRisk, toCompliance, from, host, subject, messagebody, FileName, FName);
      
          ActivityLogger act = new ActivityLogger(j);
          act.logger("Span Price", 1, true);
          
          ServerConn conFix = new ServerConn();
          ServerConn conRisk = new ServerConn();

            connection = conFix.getOPSServerConn();
            connection2 = conRisk.getCMEServerConn();
                  
            CallableStatement cStmt = null;
            CallableStatement cStmt2 = null;

            try{
                act.logger("sp_export_span_price()", 3, true);
                cStmt = connection.prepareCall("call sp_export_span_price()");
                cStmt.execute();
                
                cStmt.close();
                connection.close();
                
                cStmt2 = connection2.prepareCall("call sp_rlt_price_alert()");
                cStmt2.execute();

                cStmt2.close();
                connection2.close();
                
                } catch(SQLException sqle) {
		   System.out.println("SQLException : " + sqle);
                   act.logger("sp_export_span_price()", 5, false);                
                }
            
             try {
                 
                 File f = new File(FileName);
                 
                 if(f.exists()){
                    System.out.println("File existed");
                    email.send();
                    CleanDirectory.cleanDirectory(dir);
                  }else{
                    System.out.println("File not found!");
                    }
                 
             } catch (IOException ex) {
                 Logger.getLogger(ImportSpanPrice.class.getName()).log(Level.SEVERE, null, ex);
             }

                act.logger("Span Price", 2, true);
          }
                }
               },5 * 1000, 900 * 1000); //5 * 1000)
        }
     }
 }
