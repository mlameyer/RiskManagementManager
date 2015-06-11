/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextArea;
import resources.CLRDataAS400;

/**
 *
 * @author MLaMeyer
 */
public class ImportPaycollect {

    private JTextArea j;
    
    public ImportPaycollect(JTextArea j) {
       this.j = j;
    }
    
    public boolean startTime(){
        boolean exe;
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        exe = dayOfWeek != 1 && dayOfWeek != 7;
        
        return exe;
    }
    
    public void csvImportPaycollect(boolean a){
        if (a == true){
            Timer timer = new Timer();
            Calendar start = Calendar.getInstance();
            start.set(Calendar.HOUR_OF_DAY, 23);
            start.set(Calendar.MINUTE, 0);
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
                            act.logger("Paycollect.csv", 1, true);

                            CLRDataAS400 clr = new CLRDataAS400();
                            clr.PayCollectDataXFR();
                            
                            CLRDataAS400 clr2 = new CLRDataAS400();
                            clr2.PayCollectDataXFR2();
                            act.logger("Paycollect.csv", 2, true);
                
                        } catch (Exception e) {
                            ActivityLogger act = new ActivityLogger(j);
                            act.logger("Paycollect.csv", 4, false);
                        }
                    }
                }
            },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); //start.getTime()
        }
    }
}
// obselete code as of 2014-03-28
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextArea;
import resources.CLRDataAS400;

public class ImportPaycollect {
   private static final String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
   private static final String DB_URL = "jdbc:mysql://192.168.130.212/mgex_riskmgnt";
   private static final String USER = "root";
   private static final String PASS = "mgex";
   private static final String file = "K:\\FINANCE\\CLEARING\\Risk Management\\Paycollect\\paycollect.csv";
   private static final String table = "t_dataclrpaycollect";
   private static final String table2 = "t_data_dailycollateral";
   private static final String col_Names = "Exchange,Clearing_Member,Reg_Seg,"
           + "Yesterday_Cash_Balance,Yesterday_non_cash_Balance,"
           + "Yesterday_Margin_Requirement,Futures_Fluctuation,Option_Fluctuation,"
           + "Intraday_Variation,NA,NA2,NA3,Current_Margin_Requirement,NA4,"
           + "End_of_Day_Pay,End_of_Day_Collect,New_Cash_Margin,New_Noncash_Margin,"
           + "Date2,Residual";
   private static final String col_Names2 = "Exchange,Clearing_Member,Reg_Seg,"
           + "Yesterday_Cash_Balance,Yesterday_non_cash_Balance,"
           + "Yesterday_Margin_Requirement,Futures_Fluctuation,Option_Fluctuation,"
           + "Intraday_Variation,NA,NA2,NA3,Today_Margin_Requirement,NA4,"
           + "End_of_Day_Pay,End_of_Day_Collect,New_Cash_Margin,New_Noncash_Margin,"
           + "Date2,Residual";
    
    private static final int id = 0;
    private static final int id2 = 1;
    private static final boolean header = false;

    private JTextArea j;
    
    public ImportPaycollect(JTextArea j) {
       this.j = j;
    }
    
    public boolean startTime(){
        boolean exe;
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        exe = dayOfWeek != 1 && dayOfWeek != 7;
        
        return exe;
    }
    
    public void csvImportPaycollect(boolean a){
        if (a == true){
        Timer timer = new Timer();
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 23);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        Date time = start.getTime();
        
        Date currentTime = new Date();
        
        if(currentTime.after(time)){
            start.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                if(startTime()){
        try {

                ActivityLogger act = new ActivityLogger(j);
                act.logger("Paycollect.csv", 1, true);
		//CSVLoader loader = new CSVLoader(getCon());
		//loader.loadCSV(file, table, col_Names, id, header);
                CLRDataAS400 clr = new CLRDataAS400();
                clr.PayCollectDataXFR();
                //CSVLoader loader2 = new CSVLoader(getCon());
                //loader2.loadCSV(file, table2, col_Names2, id2, header);
                act.logger("Paycollect.csv", 2, true);
                
	} catch (Exception e) {
		e.printStackTrace();
                ActivityLogger act = new ActivityLogger(j);
                act.logger("Paycollect.csv", 4, true);
	}
            }
            }
        },0, TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); //start.getTime()
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
}
*/