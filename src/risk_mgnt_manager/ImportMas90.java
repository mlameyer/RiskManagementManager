/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import resources.ServerConn;

/**
 *
 * @author bgilbert
 */
class ImportMas90 {
    private JTextArea j;
    
    public ImportMas90(JTextArea j) {
       this.j = j;
    }

    void csvImportMas90(boolean begin) {
        if (begin == true){         
       
            Calendar start = Calendar.getInstance();
            start.set(Calendar.HOUR_OF_DAY, 1);
            start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0);
            Date time = start.getTime();
        
            Date currentTime = new Date();
        
            if(currentTime.after(time)){
                start.add(Calendar.DAY_OF_MONTH, 1);
            }
        
            Timer timer = new Timer();
            timer.scheduleAtFixedRate( new startTask(),start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // start.getTime()
                }
    }

    private class startTask extends TimerTask {
        final String Path = "K:\\FINANCE\\CLEARING\\Risk Management\\MAS90_TB\\";
        final String table = "tempMas90";
        final String col_Names = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,AA,AB,AC,AD,AE";
        final String filename = "gl_trialbalance";
        final int id = 1;
        final boolean header = false;
        
        String file = filename + getDate() + ".csv";
        
        String PathandFile = Path + file;
        
        @Override
        public void run() {
            Calendar cal = Calendar.getInstance();
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if(dayOfWeek != 1 && dayOfWeek != 2){
                try {
                    ActivityLogger act = new ActivityLogger(j);
                    act.logger("Mas90.csv", 1, true);
                    
                    dropTable();
                    createTable();
                    
                    ServerConn con = new ServerConn();
                    Connection conn = con.getCMEServerConn();
                    
                    CSVLoader loader = new CSVLoader(conn);
                    loader.loadCSV(PathandFile, table, col_Names, id, header);
                    
                    runProcedure();
                    
                    dropTable();
                        
                    act.logger("Mas90.csv", 2, true);
			
                    } catch (Exception e) {
                        ActivityLogger act = new ActivityLogger(j);
                        act.logger("Mas90.csv", 4, false);
                    }

                }else{
                    ActivityLogger act = new ActivityLogger(j);
                    act.logger("Mas90.csv", 6, true);
                }  
        }
        
        private void runProcedure(){
            ServerConn con = new ServerConn();
            Connection conn = con.getCMEServerConn();
            CallableStatement cstmt = null;
            try {
                
                cstmt = conn.prepareCall("CALL sp_parseMAS90()");
                
                cstmt.executeUpdate();
                
            } catch (SQLException ex) {
                Logger.getLogger(ImportMas90.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                if(cstmt != null){
                    try {
                        cstmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportMas90.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(conn != null){
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportMas90.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        private void createTable() {
            ServerConn con = new ServerConn();
            Connection conn = con.getCMEServerConn();
            PreparedStatement pstmt = null;
            
            try {

                final String createTableSQL = "CREATE TABLE tempMas90 ("
                        + "A VARCHAR(255),B VARCHAR(255),C VARCHAR(255),"
                        + "D VARCHAR(255),E VARCHAR(255),F VARCHAR(255),"
                        + "G VARCHAR(255),H VARCHAR(255),I VARCHAR(255),"
                        + "J VARCHAR(255),K VARCHAR(255),L VARCHAR(255),"
                        + "M VARCHAR(255),N VARCHAR(255),O VARCHAR(255),"
                        + "P VARCHAR(255),Q VARCHAR(255),R VARCHAR(255),"
                        + "S VARCHAR(255),T VARCHAR(255),U VARCHAR(255),"
                        + "V VARCHAR(255),W VARCHAR(255),X VARCHAR(255),"
                        + "Y VARCHAR(255),Z VARCHAR(255),AA VARCHAR(255),"
                        + "AB VARCHAR(255),AC VARCHAR(255),AD VARCHAR(255),"
                        + "AE VARCHAR(255))";
                System.out.println(createTableSQL);
                pstmt = conn.prepareStatement(createTableSQL);
                
                pstmt.execute();
                
            } catch (SQLException ex) {
                Logger.getLogger(ImportMas90.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportMas90.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
		}
		if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportMas90.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
		}
            }
        }

        private void dropTable() {
            ServerConn con = new ServerConn();
            Connection conn = con.getCMEServerConn();
            PreparedStatement pstmt = null;
            
            try{
                
                final String dropTableSQL = "DROP TABLE IF EXISTS tempMas90";
                System.out.println(dropTableSQL);
                pstmt = conn.prepareStatement(dropTableSQL);
                pstmt.execute();
                
            }catch(SQLException ex){
                
            }finally {
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportMas90.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
		}
		if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportMas90.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
		}
            }
        }

        private String getDate() {
            ServerConn con = new ServerConn();
            Connection conn = con.getCMEServerConn();
            Statement stmt = null;
            int sub = 0;
            String filedate;
            String compare;
        
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            //Calendar cal = new GregorianCalendar(2013, 12, 26);
            sdf.format(new Date());
            cal.add(Calendar.DATE, -1);
            compare = sdf.format(cal.getTime());
        
            String query = "SELECT Subtract_Day FROM Holiday_Schedule "
                    + "WHERE Date = '" + compare + "'";       
        
            try {
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
                            try {
                                stmt.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(ImportMas90.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
 
			if (conn != null) {
                            try {
                                conn.close();
                            } catch (SQLException ex) {
                                Logger.getLogger(ImportMas90.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
        }
        
        if (sub != 0){
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            Calendar cal2 = Calendar.getInstance();
            //Calendar cal = new GregorianCalendar(2013, 12, 26);
            sdf2.format(new Date());
            cal2.add(Calendar.DATE, sub);
            filedate = sdf2.format(cal2.getTime());
        } else {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            Calendar cal2 = Calendar.getInstance();
            //Calendar cal = new GregorianCalendar(2013, 12, 26);
            sdf2.format(new Date());
            if (cal2.get(Calendar.DAY_OF_WEEK) == 2) {
                cal2.add(Calendar.DATE, -3);
                filedate = sdf2.format(cal2.getTime());
            } else {
                cal2.add(Calendar.DATE, -1);
                filedate = sdf2.format(cal2.getTime());
            }
        }
        return filedate;
    }
    }
}
