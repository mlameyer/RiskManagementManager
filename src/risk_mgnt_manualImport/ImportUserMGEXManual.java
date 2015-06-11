/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manualImport;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import resources.GlobexUsersFlatFile;

/**
 *
 * @author bgilbert
 */
public class ImportUserMGEXManual {
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
          ImportUserMGEX(inputValue);
    } 

    private void ImportUserMGEX(String inputValue) {
        String string, cme, globex, brokerid, mge, firmid, mge2, clearingmember, mge3, clearingmember2, cust, n;
        BufferedReader br = null;
        GlobexUsersFlatFile imp = new GlobexUsersFlatFile();
       try {
            br = new BufferedReader(new FileReader("K:\\Market Admin\\Risk_Mgnt\\DATA\\" + imp.RetrieveMissingFile(inputValue)));
       } catch (SQLException | JSchException | SftpException | IOException ex) {
           Logger.getLogger(ImportUserMGEXManual.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       try {
           while((string = br.readLine()) != null) {
               
               cme = string.substring(0, 5);
               globex = string.substring(5, 9);
               brokerid = string.substring(9, 14);
               mge = string.substring(14, 19);
               firmid = string.substring(19, 23);
               mge2 = string.substring(23, 27);
               clearingmember = string.substring(30, 45);
               mge3 = string.substring(45, 50);
               clearingmember2 = string.substring(50, 55);
               cust = string.substring(55, 60);
               n = string.substring(60, 61);
               
               imp.ImportData(cme, globex, brokerid, mge, firmid, mge2, clearingmember, mge3, clearingmember2, cust, n);
           }
       } catch (IOException ex) {
           Logger.getLogger(ImportUserMGEXManual.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
    }
}
