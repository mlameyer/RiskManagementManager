/*
 * This class handles the communication or reporting to the JTextArea for the  
 * Automation Reporting. A log of events is sent to the JTextArea for tracking
 * purposes. These logs are used to also identify errors in the procedures
 * should they fail. Upon failure the automation will call the email class to
 * send a warning message to the approriate recipiants
 *
 * This class also contains the method logRecords. The method is called by 
 * LogTheDay in the StartAutomation method. The method takes 24 hours of text
 * reported to the JTextArea and exports it to a .txt file and clears the JTextArea
 */
package risk_mgnt_manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextArea;

/**
 *
 * @author MLaMeyer
 */
public class ActivityLogger{
    private String message;
    private JTextArea m;
    public String fileout = "Start\n";
    final static String filepath = "K:\\Market Admin\\Risk_Mgnt\\AutoControlLogFiles\\AutomationControldailylog";
    
    
    public ActivityLogger(JTextArea m) {
       this.m = m;
       
    }
    
    // Classes send activity to be reported, type for switch statement, a boolean for email execution
    public void logger(String log, int type, boolean execution){
    // switch statement decides what generic message should be sent to JTextArea
                    switch (type){
                        case 1: message = "Importing " + log + ": " + new Date();
                            break;
                        case 2: message = "Importing " + log + " Complete";
                            break;
                        case 3: message = "Stored Procedure " + log + " Called";
                            break;
                        case 4: message = "Importing " + log + " Failed " + new Date();
                            break;
                        case 5: message = "Call to Stored Procedure " + log + " Failed " + new Date();
                            break;
                        case 6: message = "File " + log + " not available during weekend: " + new Date();  
                            break;
                        default: message = "No message to output, Funky Cold medina";
                            break;
                    }
                    if (execution == false) {
                        // if a class fails to execute an email is sent
                         Email e = new Email(log,type);
                    } 
                
            // print is called to print selected switch statement to JTextArea    
                print();
                
                
	}
    
    // appends text to JTextArea for record tracking
    public void print(){
      
          m.append(message + "\n"); 
          
    }
    
    // Exports text appended to JTextArea to a specified .txt file and clears JTextArea
    public void logRecords() {
        
        // method is called to perform task every 24 hrs at 2 pm CST
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 17);
        start.set(Calendar.MINUTE, 30);
        start.set(Calendar.SECOND, 0);
        Date time = start.getTime();
        
        Date currentTime = new Date();
        
        if(currentTime.after(time)){
            start.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        Timer timer = new Timer();

        timer.schedule(new TimerTask(){
            public void run(){
                try {   
                    
                        String f;
                        f = m.getText();
                        
                        // needed to make .txt file name with date appended to the end
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        Date date = new Date();
                        String day = sdf.format(date);
                        
                        // location for .txt file to be placed
			File file = new File(filepath + day + ".txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(f);
			bw.close();
 
			System.out.println("Done");
 
		}
                catch ( IOException e) {
                }
                
                m.setText(null);
            }
        },start.getTime(),TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));//start.getTime()
        
    }
}
