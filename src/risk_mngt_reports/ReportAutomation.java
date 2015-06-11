/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mngt_reports;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;


/**
 *
 * @author bgilbert
 */
public class ReportAutomation {
    private JTextArea j;
    SimpleDateFormat sdf = new SimpleDateFormat("kkmmssS");
    Calendar cal = Calendar.getInstance();
    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    
    public ReportAutomation(JTextArea j) {
        this.j = j;
    }
    
    public void fiveMinJob(){
        
    }
    
    public void tenMinJob(){
        
    }
    
    public void fifteenMinJob(){
        Timer timer = new Timer();

        
        timer.schedule(new TimerTask(){
            public void run(){
                if (dayOfWeek != 7){
                    
                    ServerConn conn = new ServerConn();
                    
                    rlt_cme_rpt rpt = new rlt_cme_rpt();
                    
                    ServerConn conn2 = new ServerConn();
                    
                    rlt_fcm_rpt rpt2 = new rlt_fcm_rpt();
                    
                    try {
                        
                        rpt.runReport(conn.getCMEServerConn());
                        rpt2.runReport(conn2.getCMEServerConn());
                        
                    } catch (SQLException ex) {
                        
                        Logger.getLogger(ReportAutomation.class.getName()).log(Level.SEVERE, null, ex);
                        
                    }
                    
                }
                
            }
        }, 500 * 1000, 500 * 1000); //500 * 1000
        
    }
    
    public void tewntyMinJob(){
        
    }
    
    public void elevenThirtyJob(){
        Timer timer = new Timer();
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 23);
        start.set(Calendar.MINUTE, 30);
        start.set(Calendar.SECOND, 0);

        timer.schedule(new TimerTask(){
            public void run(){

               ServerConn conn = new ServerConn();
                    
               cm_stress_rpt rpt1 = new cm_stress_rpt();
               cgm_stress_rpt rpt2 = new cgm_stress_rpt();
               ltr_stress_rpt rpt3 = new ltr_stress_rpt();
                    
               try {
                        
                    rpt1.runReport(conn.getCMEServerConn());
                    rpt2.runReport(conn.getCMEServerConn());
                    rpt3.runReport(conn.getCMEServerConn());
                        
               } catch (SQLException ex) {
                        
                    Logger.getLogger(ReportAutomation.class.getName()).log(Level.SEVERE, null, ex);
                        
               }
 
            }
        },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
    }
}
