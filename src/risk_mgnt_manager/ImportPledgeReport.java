/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import javax.swing.JTextArea;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
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

/**
 *
 * @author bgilbert
 */
class ImportPledgeReport {
    private static final String SFTPHOST = "ftp.mgex.com";
    private static final int    SFTPPORT = 22;
    private static final String SFTPUSER = "WF";
    private static final String SFTPPASS = "$D_dA&4%U&";
    private static final String SFTPWORKINGDIR = "/";
 
    Session     session     = null;
    Channel     channel     = null;
    ChannelSftp channelSftp = null;
    
    private static final String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://192.168.130.212/mgex_riskmgnt";
    private static final String USER = "root";
    private static final String PASS = "mgex";
    private static final String filename = "MINNE577PledgeReport_";
    private static final String path = "K:\\Market Admin\\Risk_Mgnt\\DATA";
    private static final String table = "t_data_pledge_report";
    private static final String col_Names = "AccountName, BroadridgeId, CUSIP,"
            + " PledgeAmount, MarketValue, Description, DateDate";
    
    private static final int id = 0;
    private static final boolean header = true;
    private JTextArea j;
    private String pledgereport;
    
    ImportPledgeReport(JTextArea j) {
        this.j = j;
    }

    void SFTPImport(boolean begin) {
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 8);
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
                try{
                
                    ActivityLogger act = new ActivityLogger(j);
                    act.logger("MINNE577PledgeReport.csv", 1, true);
                
                    getDate();
            
                    String file = pledgereport;
                    String filePath = path + pledgereport;

                    JSch jsch = new JSch();
                    session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
                    session.setPassword(SFTPPASS);
                    java.util.Properties config = new java.util.Properties();
                    config.put("StrictHostKeyChecking", "no");
                    session.setConfig(config);
                    session.connect();
                    channel = session.openChannel("sftp");
                    channel.connect();
                    channelSftp = (ChannelSftp)channel;
                    channelSftp.cd(SFTPWORKINGDIR);
                    byte[] buffer = new byte[1024];
                    BufferedInputStream bis = new BufferedInputStream(channelSftp.get(file));
                    File newFile = new File(filePath);
                    OutputStream os = new FileOutputStream(newFile);
                    BufferedOutputStream bos = new BufferedOutputStream(os);
                    int readCount;
                    //System.out.println("Getting: " + theLine);

                    while((readCount = bis.read(buffer)) > 0) {
                        System.out.println("Writing: " );
                        bos.write(buffer, 0, readCount);
                    }
        
                    bis.close();

                    bos.close();
            
                    CSVLoader loader = new CSVLoader(getCon());
                    loader.loadCSV(filePath, table, col_Names, id, header);

                    }catch(Exception ex){
    
                        ex.printStackTrace();
                        ActivityLogger act = new ActivityLogger(j);
                        act.logger("MINNE577PledgeReport.csv", 4, false);
        
                    }
            
                ActivityLogger act = new ActivityLogger(j);
                act.logger("MINNE577PledgeReport.csv", 2, true);
                }
            }
        },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));//start.getTime()
        
        
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
            pledgereport = filename + sdf2.format(cal2.getTime()) + ".csv";
        } else {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            Calendar cal2 = Calendar.getInstance();
            //Calendar cal = new GregorianCalendar(2013, 12, 26);
            sdf2.format(new Date());
            if (cal2.get(Calendar.DAY_OF_WEEK) == 2) {
                cal2.add(Calendar.DATE, -3);
                pledgereport = filename + sdf2.format(cal2.getTime()) + ".csv";
            } else {
                cal2.add(Calendar.DATE, -1);
                pledgereport = filename + sdf2.format(cal2.getTime()) + ".csv";
            }
        }

        return pledgereport;
    }
    
}
