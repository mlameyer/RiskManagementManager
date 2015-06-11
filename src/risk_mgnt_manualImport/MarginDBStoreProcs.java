/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manualImport;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import resources.ServerConn;
/**
 *
 * @author mlameyer
 */
public class MarginDBStoreProcs{

    
    public MarginDBStoreProcs(String date, int i){     
        switch(i){
            case 1: marginhistcur(date);
                break;
            case 2: marginhistsrd(date);
                break;
            case 3: histcur(date);
                break;
            case 4: histsrd(date);
                break;
            case 5: sumcur(date);
                break;
            case 6: sumsrd(date);
                break;
            default: System.out.print("Oh Snap!!!!");
                break;
        }
    }
    
    private void marginhistcur(String s){
        Connection conn = null;
        CallableStatement cStmt = null;
        
        try {
            ServerConn con = new ServerConn();
            conn = con.getMarginsDBConn();

            cStmt = conn.prepareCall("{call sp_MarginManual_Updater(?,?)}");
            cStmt.setString(1, s);
            cStmt.setInt(2, 0);
            cStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(MarginDBStoreProcs.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try{
                
                if (cStmt != null){
                    cStmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            } catch(SQLException ex){
                Logger.getLogger(MarginDBStoreProcs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void marginhistsrd(String s){
        Connection conn = null;
        CallableStatement cStmt = null;
        
        try {
            ServerConn con = new ServerConn();
            conn = con.getMarginsDBConn();

            cStmt = conn.prepareCall("call sp_MarginManual_Updater(?,?)");
            cStmt.setString(1, s);
            cStmt.setInt(2, 1);
            cStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(MarginDBStoreProcs.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try{
                
                if (cStmt != null){
                    cStmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            } catch(SQLException ex){
                Logger.getLogger(MarginDBStoreProcs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void histcur(String s){
        Connection conn = null;
        CallableStatement cStmt = null;
        
        try {
            ServerConn con = new ServerConn();
            conn = con.getMarginsDBConn();

            cStmt = conn.prepareCall("{call sp_MarginManual_Updater(?,?)}");
            cStmt.setString(1, s);
            cStmt.setInt(2, 2);
            cStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(MarginDBStoreProcs.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try{
                
                if (cStmt != null){
                    cStmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            } catch(SQLException ex){
                Logger.getLogger(MarginDBStoreProcs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void histsrd(String s){
        Connection conn = null;
        CallableStatement cStmt = null;
        
        try {
            ServerConn con = new ServerConn();
            conn = con.getMarginsDBConn();

            cStmt = conn.prepareCall("{call sp_MarginManual_Updater(?,?)}");
            cStmt.setString(1, s);
            cStmt.setInt(2, 3);
            cStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(MarginDBStoreProcs.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try{
                
                if (cStmt != null){
                    cStmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            } catch(SQLException ex){
                Logger.getLogger(MarginDBStoreProcs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void sumcur(String s) {
        Connection conn = null;
        CallableStatement cStmt = null;
        
        try {
            ServerConn con = new ServerConn();
            conn = con.getMarginsDBConn();

            cStmt = conn.prepareCall("{call sp_MarginManual_Updater(?,?)}");
            cStmt.setString(1, s);
            cStmt.setInt(2, 4);
            cStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(MarginDBStoreProcs.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try{
                
                if (cStmt != null){
                    cStmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            } catch(SQLException ex){
                Logger.getLogger(MarginDBStoreProcs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void sumsrd(String s) {
        Connection conn = null;
        CallableStatement cStmt = null;
        
        try {
            ServerConn con = new ServerConn();
            conn = con.getMarginsDBConn();

            cStmt = conn.prepareCall("{call sp_MarginManual_Updater(?,?)}");
            cStmt.setString(1, s);
            cStmt.setInt(2, 5);
            cStmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(MarginDBStoreProcs.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try{
                
                if (cStmt != null){
                    cStmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            } catch(SQLException ex){
                Logger.getLogger(MarginDBStoreProcs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
