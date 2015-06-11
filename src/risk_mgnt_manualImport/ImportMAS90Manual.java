/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manualImport;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import resources.ServerConn;
import risk_mgnt_manager.ActivityLogger;
import risk_mgnt_manager.CSVLoader;


/**
 *
 * @author bgilbert
 */
class ImportMAS90Manual {

    public void exeTask(String a){
        int dateNumeric;
          String inputValue = a;
          boolean leg1 = false, leg2 = false, procced = false;
          while(procced == false){
              
              if(inputValue.length() != 8){
                  inputValue = null;
                  inputValue = JOptionPane.showInputDialog("Date entered must be 8 integers long"
                          + "\nUse Format YYYYMMDD. Example: 20140101");
              } else{
                  leg1 = true;
              }
              
              try{
                  dateNumeric = Integer.parseInt(inputValue);
                  leg2 = true;
              } catch(NumberFormatException i){
                  inputValue = null;
                  inputValue = JOptionPane.showInputDialog("Date must be entered as a Numeric Value"
                          + "\nUse Format YYYYMMDD. Example: 20140101");
              }
              
              if(leg1 == true && leg2 == true){
                  procced = true;
              }
              
          } System.out.println(inputValue);
          ImportMAS90(inputValue);
    }

    private void ImportMAS90(String inputValue) {
        final String Path = "K:\\FINANCE\\CLEARING\\Risk Management\\MAS90_TB\\";
        final String table = "tempMas90";
        final String col_Names = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,AA,AB,AC,AD,AE";
        final int id = 1;
        final boolean header = false;
        
        String file = "gl_trialbalance" + inputValue + ".csv";
        
        String PathandFile = Path + file;
        try {

            dropTable();
            createTable();
                    
            ServerConn con = new ServerConn();
            Connection conn = con.getCMEServerConn();
                    
            CSVLoader loader = new CSVLoader(conn);
            loader.loadCSV(PathandFile, table, col_Names, id, header);
                    
            runProcedure();
                    
            dropTable();
	} catch (Exception e) {

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
                Logger.getLogger(ImportMAS90Manual.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                if(cstmt != null){
                    try {
                        cstmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportMAS90Manual.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(conn != null){
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportMAS90Manual.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ImportMAS90Manual.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (pstmt != null) {
                    try {
                        pstmt.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportMAS90Manual.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
		}
		if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportMAS90Manual.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(ImportMAS90Manual.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
		}
		if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportMAS90Manual.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
		}
            }
        }
}
