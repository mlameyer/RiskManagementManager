/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextArea;
import resources.CLRDataAS400;

/**
 *
 * @author mlameyer
 */
public class ImportDSROPaycollect {
    
    private JTextArea j;
    
    public ImportDSROPaycollect(JTextArea j) {
       this.j = j;
    }
    
    public boolean startTime(){
        boolean exe;
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        exe = dayOfWeek != 1 && dayOfWeek != 7;
        
        return exe;
    }
    
    public void csvImportPaycollect(boolean a){
        if (a == true){
            Timer timer = new Timer();
            Calendar start = Calendar.getInstance();
            start.set(Calendar.HOUR_OF_DAY, 17);
            start.set(Calendar.MINUTE, 30);
            start.set(Calendar.SECOND, 0);
            Date time = start.getTime();
        
            Date currentTime = new Date();
        
            if(currentTime.after(time)){
                start.add(Calendar.DAY_OF_MONTH, 1);
            }
        
            timer.scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run(){
                    if(startTime()){
                        try {

                            ActivityLogger act = new ActivityLogger(j);
                            act.logger("DSRO Paycollect", 1, true);

                            CLRDataAS400 clr = new CLRDataAS400();
                            clr.PayCollectDataXFR3();
                
                        } catch (Exception e) {
                            ActivityLogger act = new ActivityLogger(j);
                            act.logger("Paycollect.csv", 4, false);
                        }
                    }
                }
            },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); //start.getTime()
        }
    }
}