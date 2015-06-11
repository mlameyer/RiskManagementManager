/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import resources.CLRDataAS400;
import resources.ServerConn;

/**
 *
 * @author MLaMeyer
 */
public class ImportIntraday {

    private JTextArea j;
    private String table = "t_data_intradaypaycollect";
    
    public ImportIntraday(JTextArea j) {
       this.j = j;
    }
    
    public boolean startTime(){
        boolean exe;
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        exe = dayOfWeek != 1 && dayOfWeek != 7;
        
        return exe;
    }
    
    public void csvImportIntra(boolean a) throws SQLException{
        if (a == true){
            Timer timer = new Timer();
            Calendar start = Calendar.getInstance();
            start.set(Calendar.HOUR_OF_DAY, 11);
            start.set(Calendar.MINUTE, 30);
            start.set(Calendar.SECOND, 0);
            Date time = start.getTime();
        
            Date currentTime = new Date();
        
            if(currentTime.after(time)){
                start.add(Calendar.DAY_OF_MONTH, 1);
            }
        
            timer.scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run(){
                    if(startTime()){
                        try {

                            ActivityLogger act = new ActivityLogger(j);
                            act.logger("Intraday", 1, true);

                            CLRDataAS400 clr = new CLRDataAS400();
                            clr.IntraPayCollectDataXFR();
                
                            act.logger("Intraday", 2, true);
                
                        } catch (Exception e) {
                            ActivityLogger act = new ActivityLogger(j);
                            act.logger("Intraday", 4, false);
                        }
                    }
                    try {
                        Thread.sleep(6 * 60 * 60 * 1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ImportIntraday.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    deleteRecordsAfter5();
                }

                private void deleteRecordsAfter5() {
                    PreparedStatement Stmt = null;
                    Connection conn = null;
                    String query = "TRUNCATE" + table;
                    
                    try{
                        ServerConn con = new ServerConn();
                        conn = con.getCMEServerConn();
                        
                        Stmt = conn.prepareStatement(query);
                        Stmt.executeQuery();
                        
                    }catch(SQLException e){
                        
                    }finally{
                        try {
                            Stmt.close();
                            conn.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(ImportIntraday.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                        
                }
            },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); //start.getTime()
        }
    }
}
// Obselete as of 20140403
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public class ImportIntraday {
   private static final String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
   private static final String DB_URL = "jdbc:mysql://192.168.130.212/mgex_riskmgnt";
   private static final String USER = "root";
   private static final String PASS = "mgex";
   private static final String file = "K:\\Market Admin\\Risk_Mgnt\\DATA\\RM_Intraday_paycollect.csv";
   private static final String table = "t_data_intradaypaycollect";
   private static final String col_Names = "Exchange,Clearing_Member,Reg_Seg,"
           + "Yesterday_Cash_Balance,Yesterday_non_cash_Balance,"
           + "Yesterday_Margin_Requirement,Intraday_Variation,Option_Fluctuation,"
           + "UnKnown,NA,NA2,NA3,Current_Margin_Requirement,NA4,"
           + "End_of_Day_Pay,End_of_Day_Collect,New_Cash_Margin,New_Noncash_Margin,"
           + "Date2,Residual";
    
    private static final int id = 1;
    private static final boolean header = false;

    private JTextArea j;
    
    public ImportIntraday(JTextArea j) {
       this.j = j;
    }
    
    public void csvImportIntra(boolean a) throws SQLException{
        if (a == true){
        Timer timer = new Timer();
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 11);
        start.set(Calendar.MINUTE, 30);
        start.set(Calendar.SECOND, 0);
        Date time = start.getTime();
        
        Date currentTime = new Date();
        
        if(currentTime.after(time)){
            start.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        timer.scheduleAtFixedRate(new TimerTask(){
        public void run(){
        try {

                ActivityLogger act = new ActivityLogger(j);
                act.logger("Intraday", 1, true);
		CSVLoader loader = new CSVLoader(getCon());
		loader.loadCSV(file, table, col_Names, id, header);
                act.logger("Intraday", 2, true);
			
            } catch (Exception e) {
                ActivityLogger act = new ActivityLogger(j);
                
		e.printStackTrace();
                act.logger("Intraday" + new Date(), 4, false);
            }
        
            truncateIntra(getCon());
        
        }
        },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));//start.getTime()
        }
    }
    private static Connection getCon() {
		Connection connection = null;
		try {
			Class.forName(JDBC_CONNECTION_URL);
			connection = DriverManager.getConnection(DB_URL,USER,PASS);
                  

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}
    private static void truncateIntra(final Connection connection) {
        final Connection con = connection;
        
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 18);
        start.set(Calendar.MINUTE, 30);
        start.set(Calendar.SECOND, 0);
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                try{
                    
                    con.createStatement().execute("TRUNCATE " + table);
                 
                } catch(SQLException e){
                    e.printStackTrace();
                } finally {
                    if (null != con)
			try {
                            con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportIntraday.class.getName()).log(Level.SEVERE, null, ex);
                    }	
		}
            }
        }, start.getTime());
    }
}
*/