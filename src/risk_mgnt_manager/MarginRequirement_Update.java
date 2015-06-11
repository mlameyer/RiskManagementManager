/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextArea;
import resources.ServerConn;

/**
 *
 * @author bgilbert
 */
public class MarginRequirement_Update {
    private JTextArea j;
    
   public MarginRequirement_Update(JTextArea j){
       this.j = j;
   } 
   
   public void getMargins(){
       Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 17);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        Date time = start.getTime();
        
        Date currentTime = new Date();
        
        if(currentTime.after(time)){
            start.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                ActivityLogger act = new ActivityLogger(j);
                act.logger("MarginRequirement_Update", 1, true);
                try{
                Calendar cal = Calendar.getInstance();
                int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeek != 1 && dayOfWeek != 7){
                    CallableStatement cStmt = null;
                    Connection dbcon;
                    ServerConn conn = new ServerConn();
                    dbcon = conn.getMarginsDBConn();
                    act.logger("sp_Update_settlements()", 3, true);
                    cStmt = dbcon.prepareCall("CALL sp_Update_settlements()");
                    cStmt.execute();
                    
                    cStmt.close();
                    dbcon.close();
                }
                }catch(SQLException ex){
                    act.logger("sp_Update_settlements()", 5, false);
                }
                act.logger("MarginRequirement_Update", 2, true);
            }
        },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // start.getTime()
   }
}
