/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import java.sql.SQLException;
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
public class ImportPOEW{
    
    private JTextArea j;
    
    public ImportPOEW(JTextArea j) {
       this.j = j;
    }
    
    public void csvImportPOEW(boolean a) throws SQLException{
        if (a == true){         
        //System.out.println(file);
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 10);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        Date time = start.getTime();
        
        Date currentTime = new Date();
        
        if(currentTime.after(time)){
            start.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Calendar cal = Calendar.getInstance();
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeek != 1 && dayOfWeek != 7){
                    try {
                        ActivityLogger act = new ActivityLogger(j);
                        act.logger("POEW.csv", 1, true);
                        
                        CLRDataAS400 clr = new CLRDataAS400();
                        clr.POEWDataXFR();
                        
                        act.logger("POEW.csv", 2, true);
			
                    } catch (Exception e) {
                        ActivityLogger act = new ActivityLogger(j);
                        act.logger("POEW.csv", 4, false);
                    }

                }else{
                    ActivityLogger act = new ActivityLogger(j);
                    act.logger("POEW.csv", 6, true);
                }
            }           
        },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // start.getTime()
                }
    }
 
}
//Obselete code as of 2014-03-28
/*
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import resources.CLRDataAS400;

public class ImportPOEW{
   private static final String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
   private static final String DB_URL = "jdbc:mysql://192.168.130.212/mgex_riskmgnt";
   private static final String USER = "root";
   private static final String PASS = "mgex";
   private static String path = "K:\\FINANCE\\CLEARING\\DATA\\";
   private static final String table = "t_data_clr_poew";
   private static final String col_Names = "Exch,Firm,Type,Comm,Month,Year"
           + ",Strike,RegBot,RegSold,ExerBot,ExerSold,"
           + "SPDBot,SPDSold,TFRBot,TFRSold,XCFOBot,XCFOSold,GAPBot,GAPSold,"
           + "ZBot,ZSold,NoBot,NoSold,NetBegLong,NetBegShort,NetEndLong,"
           + "NetEndShort,GrossBegLong,GrossBegShort,GrossEndLong,GrossEndShort,"
           + "PosDate";
    
    private static final int id = 0;
    private static final boolean header = false;
    private String poew;
    
    private JTextArea j;
    
    public ImportPOEW(JTextArea j) {
       this.j = j;
    }
    
    public void csvImportPOEW(boolean a) throws SQLException{
        if (a == true){         
        //System.out.println(file);
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 10);
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
                Calendar cal = Calendar.getInstance();
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeek != 1 && dayOfWeek != 7){
                    try {
                        String filePath = null;
                        ActivityLogger act = new ActivityLogger(j);
                        act.logger("POEW.csv", 1, true);
                        //getDate();
                        filePath = path + poew;
                        CLRDataAS400 clr = new CLRDataAS400();
                        clr.POEWDataXFR();
                        //CSVLoader loader = new CSVLoader(getCon());
                        //loader.loadCSV(filePath, table, col_Names, id, header);
                        act.logger("POEW.csv", 2, true);
			
                    } catch (Exception e) {
                        ActivityLogger act = new ActivityLogger(j);
                        e.printStackTrace();
                        act.logger("POEW.csv", 4, false);
                    }
                    try {
                        updatePOEW();
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportPOEW.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    ActivityLogger act = new ActivityLogger(j);
                    act.logger("POEW.csv", 6, true);
                }
            }           
        },0, TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // start.getTime()
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
    
    private String getDate() throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        int sub = 0;
        String compare = null;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        //Calendar cal = new GregorianCalendar(2013, 12, 26);
        sdf.format(new Date());
        cal.add(Calendar.DATE, -1);
        compare = sdf.format(cal.getTime());
        
        String query = "SELECT Subtract_Day FROM Holiday_Schedule "
                + "WHERE Date = '" + compare + "'";       
        
        try {
			conn = getCon();
			stmt = conn.createStatement();
 
			//System.out.println(query);
 
			// execute select SQL stetement
			ResultSet rs = stmt.executeQuery(query);
 
			while (rs.next()) {
 
				sub = rs.getInt("Subtract_Day");
 
				//System.out.println("The number is : " + sub);
 
			}
 
		} catch (SQLException e) {
 
			System.out.println(e.getMessage());
 
		} finally {
 
			if (stmt != null) {
				stmt.close();
			}
 
			if (conn != null) {
				conn.close();
			}
        }
        
        if (sub != 0){
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            Calendar cal2 = Calendar.getInstance();
            //Calendar cal = new GregorianCalendar(2013, 12, 26);
            sdf2.format(new Date());
            cal2.add(Calendar.DATE, sub);
            poew = "poew" + sdf2.format(cal2.getTime());
        } else {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            Calendar cal2 = Calendar.getInstance();
            //Calendar cal = new GregorianCalendar(2013, 12, 26);
            sdf2.format(new Date());
            if (cal2.get(Calendar.DAY_OF_WEEK) == 2) {
                cal2.add(Calendar.DATE, -3);
                poew = "poew" + sdf2.format(cal2.getTime());
            } else {
                cal2.add(Calendar.DATE, -1);
                poew = "poew" + sdf2.format(cal2.getTime());
            }
        }
        return poew;
    }
    
    public void updatePOEW() throws SQLException{
        Connection conn = null;
        CallableStatement cstmt = null;
        String proc = "CALL sp_update_poew_spandata()";
        
        try {
            conn = getCon();
            cstmt = conn.prepareCall(proc);
            cstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
			if (cstmt != null) {
				cstmt.close();
			}
 
			if (conn != null) {
				conn.close();
			}
            }
    }  
}
*/
