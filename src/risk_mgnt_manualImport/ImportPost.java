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
class ImportPost {

  public void exeTask(String s) {
      try {
          int dateNumeric;
          String inputValue = s;
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
            ImportCSV(inputValue);
            updatePost();
      } catch (SQLException ex) {
          Logger.getLogger(ImportPost.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private void ImportCSV(String inputValue){
      try {
          
          String file = "K:\\FINANCE\\CLEARING\\DATA\\post" + inputValue;
          String table = "t_data_netclrpos";
          String col_Names = "Exch,Firm,Type,Comm,Month,Year,Strike,"
                  + "RegBot,RegSold,ExerBot,ExerSold,SPDBot,SPDSold,TFRBot,TFRSold,"
                  + "XCFOBot,XCFOSold,NetBegLong,NetBegShort,NetEndLong,"
                  + "NetEndShort,GrossBegLong,GrossBegShort,GrossEndLong,GrossEndShort,"
                  + "PosDate";
          
          final int id = 1;
          final boolean header = false;
          
          CSVLoader loader = new CSVLoader(getCon());
          loader.loadCSV(file, table, col_Names, id, header);
      } catch (Exception ex) {
          Logger.getLogger(ImportPost.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private static Connection getCon() {
        ServerConn con = new ServerConn();
	Connection conn = con.getCMEServerConn();
        return conn;
	}
    public void updatePost() throws SQLException{
        Connection conn = null;
        CallableStatement cstmt = null;
        String proc = "CALL sp_update_post_spandata()";
        
        try {
            conn = getCon();
            cstmt = conn.prepareCall(proc);
            //cstmt.executeUpdate();
            } catch (SQLException e) {

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
