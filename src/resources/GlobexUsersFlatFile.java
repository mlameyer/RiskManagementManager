/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author mlameyer
 */
public class GlobexUsersFlatFile {
    
    ServerConn conn = new ServerConn(); 
    SFTPServer sftp = new SFTPServer();
    
    final String SFTPHOST = "167.204.41.33";
    final int    SFTPPORT = 22;
    final String SFTPUSER = "FMGEXFTP";
    final String SFTPPASS = "jRtZoHgF";
    final String SFTPWORKINGDIR = "/cme/ftp/MGEX/Outgoing";
    final String TOFILEPATH = "K:\\Market Admin\\Risk_Mgnt\\DATA\\";
    String filename;
    String filepath;

    public String RetrieveFile() throws SQLException, JSchException, SftpException, IOException{
        filename = getDate();
        
        filepath = TOFILEPATH + filename;
        
        sftp.grabFile(SFTPHOST,SFTPPORT,SFTPUSER,SFTPPASS,SFTPWORKINGDIR,filename,filepath);
        
        return filename;
    }
    
    public String RetrieveMissingFile(String date) throws SQLException, JSchException, SftpException, IOException{
        filename = "USERS_MGEX_" + date;
        
        filepath = TOFILEPATH + filename;
        
        sftp.grabFile(SFTPHOST,SFTPPORT,SFTPUSER,SFTPPASS,SFTPWORKINGDIR,filename,filepath);
        
        return filename;
    }
    
    public void ImportData(String cme, String globex, String brokerid, String mge, 
            String firmid, String mge2, String clearingmember, String mge3, 
            String clearingmember2, String cust, String n) {
            
                Connection connect = conn.getCMEServerConn();

                String query = "INSERT INTO t_data_globexusers (cme, globex, broker_id"
                        + ", mge, firm_id, mge2, clearing_member, mge3, clearing_member2, cust, n)"
                        + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            
            //System.out.println(cme + globex + brokerid + mge + firmid + mge2 + clearingmember + mge3 + clearingmember2 + cust + n);

                try{
                    connect.setAutoCommit(false);
                
                    PreparedStatement stmt = connect.prepareStatement(query);
                    stmt.setString(1, cme);
                    stmt.setString(2, globex);
                    stmt.setString(3, brokerid);
                    stmt.setString(4, mge);
                    stmt.setString(5, firmid);
                    stmt.setString(6, mge2);
                    stmt.setString(7, clearingmember);
                    stmt.setString(8, mge3);
                    stmt.setString(9, clearingmember2);
                    stmt.setString(10, cust);
                    stmt.setString(11, n);
                    stmt.addBatch();
           
                    
                    
                    stmt.executeBatch();
                    connect.commit();
                    stmt.close();
                
                
                    connect.close();
                    } catch(SQLException sqle) {
                        System.out.println("SQLException : " + sqle); 
                    }
    }
    
    private String getDate() throws SQLException{
        Connection connect = conn.getCMEServerConn();

        Statement stmt = null;
        int sub = 0;
        String compare = null;
        
        String filenamefinal;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        //Calendar cal = new GregorianCalendar(2013, 12, 26);
        sdf.format(new Date());
        cal.add(Calendar.DATE, -1);
        compare = sdf.format(cal.getTime());
        
        String query = "SELECT Subtract_Day FROM Holiday_Schedule "
                + "WHERE Date = '" + compare + "'";       
        
        try {
			
			stmt = connect.createStatement();
 
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
				connect.close();
			}
        }

        if (sub != 0){
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            Calendar cal2 = Calendar.getInstance();
            //Calendar cal = new GregorianCalendar(2013, 12, 26);
            sdf2.format(new Date());
            cal2.add(Calendar.DATE, sub);
            filenamefinal = "USERS_MGEX_" + sdf2.format(cal2.getTime());
        } else {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            Calendar cal2 = Calendar.getInstance();
            //Calendar cal = new GregorianCalendar(2013, 12, 26);
            sdf2.format(new Date());
            if (cal2.get(Calendar.DAY_OF_WEEK) == 2) {
                cal2.add(Calendar.DATE, -3);
                filenamefinal = "USERS_MGEX_" + sdf2.format(cal2.getTime());
            } else {
                cal2.add(Calendar.DATE, -1);
                filenamefinal = "USERS_MGEX_" + sdf2.format(cal2.getTime());
            }
        }

        return filenamefinal;
    }
  

}

