/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manualImport;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import resources.ServerConn;
import risk_mgnt_manager.CSVLoader;

/**
 *
 * @author bgilbert
 */
public class ImportPledgeReportManual {
    private static final String SFTPHOST = "ftp.mgex.com";
    private static final int    SFTPPORT = 22;
    private static final String SFTPUSER = "WF";
    private static final String SFTPPASS = "$D_dA&4%U&";
    private static final String SFTPWORKINGDIR = "/";
 
    Session     session     = null;
    Channel     channel     = null;
    ChannelSftp channelSftp = null;
   
    private static final String filename = "MINNE577PledgeReport_";
    private static final String path = "K:\\Market Admin\\Risk_Mgnt\\DATA";
    private static final String table = "t_data_pledge_report";
    private static final String col_Names = "AccountName, BroadridgeId, CUSIP,"
            + " PledgeAmount, MarketValue, Description, DateDate";
    
    private static final int id = 0;
    private static final boolean header = true;
    private JTextArea j;
    private String pledgereport = filename;
    
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
          ImportPledge(inputValue);
    }
    
    private void ImportPledge(String inputValue){
        try {
            String file = pledgereport + inputValue + ".csv";
            String filePath = path + pledgereport + inputValue + ".csv";

            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
            session.setPassword(SFTPPASS);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            channelSftp.cd(SFTPWORKINGDIR);
            byte[] buffer = new byte[1024];
            BufferedInputStream bis = new BufferedInputStream(channelSftp.get(file));
            File newFile = new File(filePath);
            OutputStream os = new FileOutputStream(newFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            int readCount;
            //System.out.println("Getting: " + theLine);
            
            while((readCount = bis.read(buffer)) > 0) {
                System.out.println("Writing: " );
                bos.write(buffer, 0, readCount);
            }
            
            bis.close();
            
            bos.close();
            
            CSVLoader loader = new CSVLoader(getCon());
            loader.loadCSV(filePath, table, col_Names, id, header);
        } catch (JSchException ex) {
            Logger.getLogger(ImportPledgeReportManual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SftpException ex) {
            Logger.getLogger(ImportPledgeReportManual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportPledgeReportManual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImportPledgeReportManual.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ImportPledgeReportManual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static Connection getCon() {
        ServerConn con = new ServerConn();
	Connection conn = con.getCMEServerConn();
        return conn;
	}
}
