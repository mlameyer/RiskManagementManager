/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import resources.ServerConn;

/**
 *
 * @author mlameyer <mlameyer@mgex.com>
 */
class ProductionTable {
    final static String table = "t_datarlt_trades_prd";
    ServerConn conn = new ServerConn();
    ServerConn csvconn = new ServerConn();
    
   public void exportToCSV() {
       final String file = "//mgexdc1/Departments/market admin/Risk_Mgnt/EndOfDay_RLT_Trades/Trades_" + getDate() + ".csv";
       
       Calendar start = Calendar.getInstance();
       start.set(Calendar.HOUR_OF_DAY, 18);
       start.set(Calendar.MINUTE, 0);
       start.set(Calendar.SECOND, 0);
       Date time = start.getTime();
       
       Date currentTime = new Date();
        
        if(currentTime.after(time)){
            start.add(Calendar.DAY_OF_MONTH, 1);
        }
        
       Timer timer = new Timer();
       timer.scheduleAtFixedRate(new TimerTask(){
           public void run(){
               Connection con = csvconn.getCMEServerConn();
               CSVWriter csvw = new CSVWriter(table, con, file);
               csvw.readDataTable();
               
               truncateTable();
            }
        },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); //start.getTime()
    }
   
   private void truncateTable() {
        Connection connection = null;
        PreparedStatement Stmt = null;
        
        connection = conn.getCMEServerConn();
        try{
            String query = "TRUNCATE" + table;

            Stmt = connection.prepareStatement(query);
            Stmt.executeUpdate();
 
            Stmt.close();
            connection.close();
        }catch(SQLException sqle){
            System.out.println("SQLException : " + sqle);
        }finally{
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductionTable.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    private String getDate() {
        String datestring = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        datestring = sdf.format(new Date());
        
        
        return datestring;
    }
    
}
