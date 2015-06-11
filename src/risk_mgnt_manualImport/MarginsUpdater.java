/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manualImport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import resources.ServerConn;

/**
 *
 * @author mlameyer
 */
public class MarginsUpdater {
    
    public MarginsUpdater(){
        
    }

    void marginHistCurrent(String s) {
        Connection conn = null;
        Statement statement = null;
        try {
            boolean hasRows = false;
            String selectTableSQL = "SELECT DISTINCT TradeDate FROM mgex_margins.t_mgn_marginhist_current WHERE TradeDate = " + s;
            
            ServerConn con = new ServerConn();
            conn = con.getMarginsDBConn();
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            
            while (rs.next()) {
                    hasRows = true;
                    String tradedate = rs.getString("TradeDate");
                    JOptionPane.showMessageDialog(null,"Date " + tradedate + " is in t_mgn_marginhist_current. Proceed to step 3.");
            }
                
            if(!hasRows){
                JOptionPane.showMessageDialog(null,"The date entered is not in "
                        + "the table t_mgn_marginhist_current. The settlements "
                        + "for " + s + " will be added to the table with the "
                        + "current margins");
                
                MarginDBStoreProcs sp = new MarginDBStoreProcs(s,1);
            }
       
        } catch (SQLException ex) {
            Logger.getLogger(MarginsUpdater.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                try{
			if (statement != null) {
				statement.close();
			}
 
			if (conn != null) {
				conn.close();
			}
                }catch(SQLException ex){
                   Logger.getLogger(MarginsRecovery.class.getName()).log(Level.SEVERE, null, ex); 
                }
 
		}
                
    }

    void marginHistSpread(String s) {
        Connection conn = null;
        Statement statement = null;
        try {
            boolean hasRows = false;
            String selectTableSQL = "SELECT DISTINCT TradeDate FROM mgex_margins.t_mgn_marginhist_spread WHERE TradeDate = " + s;
            
            ServerConn con = new ServerConn();
            conn = con.getMarginsDBConn();
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            
            while (rs.next()) {
                    hasRows = true;
                    String tradedate = rs.getString("TradeDate");
                    JOptionPane.showMessageDialog(null,"Date " + tradedate + " is in t_mgn_marginhist_spread.  Proceed to step 3.");
            }

            if(!hasRows){
                JOptionPane.showMessageDialog(null,"The date entered is not in "
                        + "the table t_mgn_marginhist_spread. The settlements "
                        + "for " + s + " will be added to the table with the "
                        + "current margins");
                
                MarginDBStoreProcs sp = new MarginDBStoreProcs(s,2);
             }
     
        } catch (SQLException ex) {
            Logger.getLogger(MarginsUpdater.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                try{
			if (statement != null) {
				statement.close();
			}
 
			if (conn != null) {
				conn.close();
			}
                }catch(SQLException ex){
                   Logger.getLogger(MarginsRecovery.class.getName()).log(Level.SEVERE, null, ex); 
                }
 
		}
        
    }

    void HistCurrent(String s) {
        Connection conn = null;
        Statement statement = null;
        try {
            boolean hasRows = false;
            String selectTableSQL = "SELECT DISTINCT TradeDate FROM mgex_margins.t_mgn_history_current WHERE TradeDate = " + s;
            
            ServerConn con = new ServerConn();
            conn = con.getMarginsDBConn();
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            
            while (rs.next()) {
                    hasRows = true;
                    String tradedate = rs.getString("TradeDate");
                    JOptionPane.showMessageDialog(null,"Date " + tradedate + " is in t_mgn_history_current.  Proceed to step 4.");
            }

            if(!hasRows){
                JOptionPane.showMessageDialog(null,"The date entered is not in "
                        + "the table t_mgn_history_current. The settlements "
                        + "for " + s + " will be added to the table with the "
                        + "current margins and calculations");
                
                MarginDBStoreProcs sp = new MarginDBStoreProcs(s,3);
             }
     
        } catch (SQLException ex) {
            Logger.getLogger(MarginsUpdater.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                try{
			if (statement != null) {
				statement.close();
			}
 
			if (conn != null) {
				conn.close();
			}
                }catch(SQLException ex){
                   Logger.getLogger(MarginsRecovery.class.getName()).log(Level.SEVERE, null, ex); 
                }
 
		}
    }

    void HistSpread(String s) {
        Connection conn = null;
        Statement statement = null;
        try {
            boolean hasRows = false;
            String selectTableSQL = "SELECT DISTINCT TradeDate FROM mgex_margins.t_mgn_history_spread WHERE TradeDate = " + s;
            
            ServerConn con = new ServerConn();
            conn = con.getMarginsDBConn();
            statement = conn.createStatement();
            ResultSet rs2 = statement.executeQuery(selectTableSQL);
            
            while (rs2.next()) {
                    hasRows = true;
                    String tradedate = rs2.getString("TradeDate");
                    JOptionPane.showMessageDialog(null,"Date " + tradedate + " is in t_mgn_history_spread.  Proceed to step 4.");
            }

            if(!hasRows){
                JOptionPane.showMessageDialog(null,"The date entered is not in "
                        + "the table t_mgn_history_spread. The settlements "
                        + "for " + s + " will be added to the table with the "
                        + "current margins");
                
                MarginDBStoreProcs sp = new MarginDBStoreProcs(s,4);
             }
     
        } catch (SQLException ex) {
            Logger.getLogger(MarginsUpdater.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                try{
			if (statement != null) {
				statement.close();
			}
 
			if (conn != null) {
				conn.close();
			}
                }catch(SQLException ex){
                   Logger.getLogger(MarginsRecovery.class.getName()).log(Level.SEVERE, null, ex); 
                }
 
		}
    }

    void SummaryCurrent(String s) {
        Connection conn = null;
        Statement statement = null;
        try {
            boolean hasRows = false;
            String selectTableSQL = "SELECT DISTINCT Date FROM mgex_margins.t_mgn_summary_current WHERE Date = " + s;
            
            ServerConn con = new ServerConn();
            conn = con.getMarginsDBConn();
            statement = conn.createStatement();
            ResultSet rs2 = statement.executeQuery(selectTableSQL);
            
            while (rs2.next()) {
                    hasRows = true;
                    String tradedate = rs2.getString("Date");
                    JOptionPane.showMessageDialog(null,"Date " + tradedate + " is in t_mgn_summary_current.");
            }

            if(!hasRows){
                JOptionPane.showMessageDialog(null,"The date entered is not in "
                        + "the table t_mgn_summary_current. The settlements "
                        + "for " + s + " will be added to the table with the "
                        + "current margins");
                
                MarginDBStoreProcs sp = new MarginDBStoreProcs(s,5);
             }
     
        } catch (SQLException ex) {
            Logger.getLogger(MarginsUpdater.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                try{
			if (statement != null) {
				statement.close();
			}
 
			if (conn != null) {
				conn.close();
			}
                }catch(SQLException ex){
                   Logger.getLogger(MarginsRecovery.class.getName()).log(Level.SEVERE, null, ex); 
                }
 
		}
    }

    void SummarySpread(String s) {
        Connection conn = null;
        Statement statement = null;
        try {
            boolean hasRows = false;
            String selectTableSQL = "SELECT DISTINCT Date FROM mgex_margins.t_mgn_summary_spread WHERE Date = " + s;
            
            ServerConn con = new ServerConn();
            conn = con.getMarginsDBConn();
            statement = conn.createStatement();
            ResultSet rs2 = statement.executeQuery(selectTableSQL);
            
            while (rs2.next()) {
                    hasRows = true;
                    String tradedate = rs2.getString("Date");
                    JOptionPane.showMessageDialog(null,"Date " + tradedate + " is in t_mgn_summary_spread.");
            }

            if(!hasRows){
                JOptionPane.showMessageDialog(null,"The date entered is not in "
                        + "the table t_mgn_summary_spread. The settlements "
                        + "for " + s + " will be added to the table with the "
                        + "current margins");
                
                MarginDBStoreProcs sp = new MarginDBStoreProcs(s,6);
             }
     
        } catch (SQLException ex) {
            Logger.getLogger(MarginsUpdater.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                try{
			if (statement != null) {
				statement.close();
			}
 
			if (conn != null) {
				conn.close();
			}
                }catch(SQLException ex){
                   Logger.getLogger(MarginsRecovery.class.getName()).log(Level.SEVERE, null, ex); 
                }
 
		}
    }
   
}
