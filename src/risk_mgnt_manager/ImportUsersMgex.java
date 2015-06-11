/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import com.ancientprogramming.fixedformat4j.format.FixedFormatManager;
import com.ancientprogramming.fixedformat4j.format.impl.FixedFormatManagerImpl;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import resources.GlobexUsersFlatFile;

/**
 *
 * @author bgilbert
 */

public class ImportUsersMgex {
    private JTextArea j;
    BufferedReader br = null;
    private static FixedFormatManager manager = new FixedFormatManagerImpl();
    
    public ImportUsersMgex(JTextArea j) {
       this.j = j;
    }

    public void grabUsersMgex(boolean a) throws FileNotFoundException, IOException, SQLException, JSchException, SftpException{
       
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 18);
        start.set(Calendar.MINUTE, 30);
        start.set(Calendar.SECOND, 0);
        Date time = start.getTime();
        
        Date currentTime = new Date();
        
        if(currentTime.after(time)){
            start.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                Calendar cal = Calendar.getInstance();
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeek != 1 && dayOfWeek != 7){
            
                    GlobexUsersFlatFile imp = new GlobexUsersFlatFile();
                    ActivityLogger act = new ActivityLogger(j);
                    act.logger("USER_MGEX", 1, true);
                    try {

                        br = new BufferedReader(new FileReader("K:\\Market Admin\\Risk_Mgnt\\DATA\\" + imp.RetrieveFile()));
                        
                    } catch (FileNotFoundException ex) {
                        act.logger("USER_MGEX", 4, false);
                        Logger.getLogger(ImportUsersMgex.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (            SQLException | JSchException | SftpException | IOException ex) {
                        act.logger("USER_MGEX", 4, false);
                        Logger.getLogger(ImportUsersMgex.class.getName()).log(Level.SEVERE, null, ex);
                    }
        
        String string, cme, globex, brokerid, mge, firmid, mge2, clearingmember, mge3, clearingmember2, cust, n;

        try{
            
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
        }catch (IOException e) {
			//e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
                                ex.printStackTrace();
                                act.logger("USER_MGEX", 4, false);
			}
		}
            act.logger("USER_MGEX", 2, true);
                }
            }
        },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // start.getTime()
        
    }
    
}
