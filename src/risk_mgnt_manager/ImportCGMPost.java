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
public class ImportCGMPost extends Risk_Mgnt_Manager{

    private JTextArea j;
    
    public ImportCGMPost(JTextArea j) {
       this.j = j;
    }
    
    public void csvImportCGMPost(boolean a) throws SQLException{
        if (a == true){
        Timer timer = new Timer();
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 18);
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
        try {
                ActivityLogger act = new ActivityLogger(j);
                act.logger("CGM_Post", 1, true);
                
                CLRDataAS400 clr = new CLRDataAS400();
                clr.POSTDataXFR();

                act.logger("CGM_Post", 2, true);
			
	} catch (Exception e) {
                ActivityLogger act = new ActivityLogger(j);
                act.logger("CGM_Post", 3, false);       
	}

            }
        },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));//,start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
                }
        
    }
}
//Obselete as of 2014-03-28
/*
import java.sql.CallableStatement;
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


public class ImportCGMPost extends Risk_Mgnt_Manager{
   private static String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
   private static String file = "K:\\Market Admin\\Gross Margins\\AS400 Net Positions\\CGM_POST.csv";
   private static String table = "t_data_netclrpos";
   private static String col_Names = "Exch,Firm,Type,Comm,Month,Year,Strike,"
           + "RegBot,RegSold,ExerBot,ExerSold,SPDBot,SPDSold,TFRBot,TFRSold,"
           + "XCFOBot,XCFOSold,NetBegLong,NetBegShort,NetEndLong,"
           + "NetEndShort,GrossBegLong,GrossBegShort,GrossEndLong,GrossEndShort,"
           + "PosDate";
    static final String DB_URL = "jdbc:mysql://192.168.130.212/mgex_riskmgnt";
    static final String USER = "root";
    static final String PASS = "mgex";
    static final int id = 1;
    private static final boolean header = false;

    private JTextArea j;
    
    public ImportCGMPost(JTextArea j) {
       this.j = j;
    }
    
    public void csvImportCGMPost(boolean a) throws SQLException{
        if (a == true){
        Timer timer = new Timer();
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 18);
        start.set(Calendar.MINUTE, 0);
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
                act.logger("CGM_Post", 1, true);
                
		CSVLoader loader = new CSVLoader(getCon());
		loader.loadCSV(file, table, col_Names, id, header);
                act.logger("CGM_Post", 2, true);
			
	} catch (Exception e) {
                ActivityLogger act = new ActivityLogger(j);
                act.logger("CGM_Post", 3, false);
		e.printStackTrace();
                
                
	}
                try {
                    updatePost();
                } catch (SQLException ex) {
                    Logger.getLogger(ImportCGMPost.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));//,start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
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
    public void updatePost() throws SQLException{
        Connection conn = null;
        CallableStatement cstmt = null;
        String proc = "CALL sp_update_post_spandata()";
        
        try {
            conn = getCon();
            cstmt = conn.prepareCall(proc);
            cstmt.executeUpdate();
            } catch (SQLException e) {
                ActivityLogger act = new ActivityLogger(j);
                act.logger("sp_update_post_spandata()", 5, false);
                //System.out.println(e.getMessage());
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
