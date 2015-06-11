/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manualImport;

import java.sql.Connection;
import java.sql.DriverManager;
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
public class ImportPayCollectManual {

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
          ImportPayCollect(inputValue);

    }
    
    private void ImportPayCollect(String inputValue){
        final String table = "t_dataclrpaycollect";
        final String table2 = "t_data_dailycollateral";
        final String col_Names = "Exchange,Clearing_Member,Reg_Seg,"
           + "Yesterday_Cash_Balance,Yesterday_non_cash_Balance,"
           + "Yesterday_Margin_Requirement,Futures_Fluctuation,Option_Fluctuation,"
           + "Intraday_Variation,NA,NA2,NA3,Current_Margin_Requirement,NA4,"
           + "End_of_Day_Pay,End_of_Day_Collect,New_Cash_Margin,New_Noncash_Margin,"
           + "Date2,Residual";
        final String col_Names2 = "Exchange,Clearing_Member,Reg_Seg,"
           + "Yesterday_Cash_Balance,Yesterday_non_cash_Balance,"
           + "Yesterday_Margin_Requirement,Futures_Fluctuation,Option_Fluctuation,"
           + "Intraday_Variation,NA,NA2,NA3,Today_Margin_Requirement,NA4,"
           + "End_of_Day_Pay,End_of_Day_Collect,New_Cash_Margin,New_Noncash_Margin,"
           + "Date2,Residual";
    
        final int id = 0;
        final int id2 = 1;
        final boolean header = false;
        String file = "K:\\FINANCE\\CLEARING\\Risk Management\\Paycollect\\paycollect" + inputValue + ".csv";
        
        CSVLoader loader = new CSVLoader(getCon());
        CSVLoader loader2 = new CSVLoader(getCon());
        try {  
            loader.loadCSV(file, table, col_Names, id, header);
            loader2.loadCSV(file, table2, col_Names2, id2, header);
        } catch (Exception ex) {
            Logger.getLogger(ImportPoew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static Connection getCon() {
        ServerConn con = new ServerConn();
	Connection conn = con.getCMEServerConn();
        return conn;
	}
    
}
