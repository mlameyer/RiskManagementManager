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
/**
 *
 * @author bgilbert
 */
public class ImportSettlesManual {
    
    
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
          ImportSettles(inputValue);
    }
    
    private void ImportSettles(String InputValue){
        try {
            ServerConn con = new ServerConn();
            try (Connection conn = con.getOPSServerConn()) {
                CallableStatement cStmt;
                cStmt = conn.prepareCall("{call sp_export_settles_by_specifiedDate(?)}");
                cStmt.setString(1, InputValue);
                cStmt.execute();
                
                cStmt.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImportSettlesManual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
