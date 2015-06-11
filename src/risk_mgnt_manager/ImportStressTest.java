/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import resources.CleanDirectory;

/**
 *
 * @author bgilbert
 */
public class ImportStressTest {
    private JTextArea j;
    private int count;
    private String csvfile;
    private static final String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://192.168.130.212/mgex_riskmgnt";
    static final String USER = "root";
    static final String PASS = "mgex";
    private static final String cgmcsv = "VarSummary_CGM";
    private static final String cmcsv = "VarSummary_CM";
    private static final String lgtcsv = "VarSummary_LTR";
    private static final String cgmtbl = "t_data_stress_CGMresults";
    private static final String cmtbl = "t_data_stress_CMresults";
    private static final String lgttbl = "t_data_stress_lgtraderResults";
    private static final String path = "K:\\Market Admin\\Risk_Mgnt\\Stress_Tests\\Daily_Results\\";
    private File dir;
    
    public ImportStressTest(JTextArea j) {
       this.j = j;
    }
    
    public void csvImport(boolean a) throws IOException{
        if (a == true){
        Timer timer = new Timer();
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 23);
        start.set(Calendar.MINUTE, 30);
        start.set(Calendar.SECOND, 0);
        Date time = start.getTime();
        
        Date currentTime = new Date();
        
        if(currentTime.after(time)){
            start.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
        
       try{
            ActivityLogger act = new ActivityLogger(j);
            act.logger("Stress Test", 1, true);
            Runtime.getRuntime().exec("cmd /c start SPAN_StressTesting.bat", null, new File("K:\\Market Admin\\Risk_Mgnt\\Stress_Tests\\Batch"));
            Thread.sleep(120 * 1000);
            count = new File("K:\\Market Admin\\Risk_Mgnt\\Stress_Tests\\Daily_Results").listFiles().length; //Span_Results or Daily_Results
            
            System.out.println(count);
            
            dir = new File("K:\\Market Admin\\Risk_Mgnt\\Stress_Tests\\Daily_Results");
            List<String> list = Arrays.asList(dir.list(
            new FilenameFilter() {
                @Override public boolean accept(File dir, String name) {
                    return name.endsWith(".csv");
                }
            }
            ));
            
            for(int i = 0; i < list.size(); i++){
              csvfile = list.get(i); 
                if (csvfile.contains(cgmcsv)){
                    System.out.println(list.get(i) + "\n");
                    try {

			StressCSVLoader loader = new StressCSVLoader(getCon());
			loader.loadCSV(path + csvfile, cgmtbl, csvfile);
			
                    } catch (Exception e) {
			e.printStackTrace();
                    }
                } else if (csvfile.contains(cmcsv)){
                    
                    try {
                        System.out.println(list.get(i) + "\n");
			StressCSVLoader loader = new StressCSVLoader(getCon());
			loader.loadCSV(path + csvfile, cmtbl, csvfile);
			
                    } catch (Exception e) {
			e.printStackTrace();
                    }
                } else if (csvfile.contains(lgtcsv)){
                    System.out.println(list.get(i) + "\n");
                    try {

			StressCSVLoader loader = new StressCSVLoader(getCon());
			loader.loadCSV(path + csvfile, lgttbl, csvfile);
			
                    } catch (Exception e) {
			e.printStackTrace();
                    }
                } else {
                    System.out.println(list.get(i) + "\n");
                }     
            }
          
            act.logger("Stress Test", 2, true);
          
        }catch(IOException e){
            System.err.println("Caught IOException: " + e.getMessage());
            ActivityLogger act = new ActivityLogger(j);
            act.logger("Stress Test", 4, false);
        }       catch (InterruptedException ex) {
                    Logger.getLogger(ImportStressTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    CleanDirectory.cleanDirectory(dir);
                } catch (IOException ex) {
                    Logger.getLogger(ImportStressTest.class.getName()).log(Level.SEVERE, null, ex);
                }
           
        }
        },start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));//,start.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
                }
    }
    private static Connection getCon() {
		Connection connection = null;
		try {
			Class.forName(JDBC_CONNECTION_URL);
			connection = DriverManager.getConnection(DB_URL,USER,PASS);
                  

		} catch (ClassNotFoundException | SQLException e) {
		}

		return connection;
    }
}
