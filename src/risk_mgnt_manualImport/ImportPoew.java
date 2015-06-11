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
import javax.swing.JOptionPane;
import resources.ServerConn;
import risk_mgnt_manager.CSVLoader;

/**
 *
 * @author bgilbert
 */
class ImportPoew {

    void exeTask(String a) {
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
          ImportPoew(inputValue);
        try {
            updatePOEW();
        } catch (SQLException ex) {
            Logger.getLogger(ImportPoew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void ImportPoew(String inputValue){
        final String table = "t_data_clr_poew";
        final String col_Names = "Exch,Firm,Type,Comm,Month,Year"
           + ",Strike,RegBot,RegSold,ExerBot,ExerSold,"
           + "SPDBot,SPDSold,TFRBot,TFRSold,XCFOBot,XCFOSold,GAPBot,GAPSold,"
           + "ZBot,ZSold,NoBot,NoSold,NetBegLong,NetBegShort,NetEndLong,"
           + "NetEndShort,GrossBegLong,GrossBegShort,GrossEndLong,GrossEndShort,"
           + "PosDate,StrikeSign";
    
        final int id = 0;
        final boolean header = false;
        String poew = "K:\\FINANCE\\CLEARING\\DATA\\poew" + inputValue;
        
        CSVLoader loader = new CSVLoader(getCon());
        try {  
            loader.loadCSV(poew, table, col_Names, id, header);
        } catch (Exception ex) {
            Logger.getLogger(ImportPoew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static Connection getCon() {
        ServerConn con = new ServerConn();
	Connection conn = con.getCMEServerConn();
        return conn;
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
