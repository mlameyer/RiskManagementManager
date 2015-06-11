package risk_mgnt_manager;
   
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import resources.TradeDataAS400;


public class ImportTradeData {
    
    private JTextArea j;
    
    public ImportTradeData(JTextArea j) {
       this.j = j;
    }
    
    public void MoveTradestoPRDtable(){

        ProductionTable prd = new ProductionTable();
        prd.exportToCSV();
        
    }
    
    public boolean startTime(){
        int timeCompare;
        boolean start;

        SimpleDateFormat sdf = new SimpleDateFormat("kkmmssS");
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        timeCompare = Integer.parseInt(sdf.format(cal.getTime()));      
        
        if (dayOfWeek != 7) {
            if(timeCompare >= 190000000 && timeCompare < 235959999){
                //System.out.println("7 to 1159 if statement");
                start = true;
            } else if (timeCompare >= 000000000 && timeCompare < 74500000){
                //System.out.println("12 to 745 if statement");
                start = true; 
            } else if (timeCompare >= 83000000 && timeCompare < 154500000){
                //System.out.println("830 to 5 if statement");
                start = true; 
            } else {
                start = false;
            }
        } else {
            start = false;
        }
        
        return start;
    }
    
    public void TradeData(boolean a) throws SQLException {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            public void run(){
                if(startTime()){
                    ActivityLogger act = new ActivityLogger(j);
                    act.logger("Trades", 1, true);
                
                    TradeDataAS400 trd = new TradeDataAS400();
                    try {

                        trd.Dataxfr();
                    
                    } catch (SQLException ex) {
                        Logger.getLogger(ImportTradeData.class.getName()).log(Level.SEVERE, null, ex);
                        act.logger("Trades", 4, false);
                    }
                    act.logger("Trades", 2, true);
                }
            }
        },0, 900 * 1000);
        
    }
}

/*


package risk_mgnt_manager;
   
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;


public class ImportTradeData extends DefaultHandler {
    final private static String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
    final private static String DB_URL = "jdbc:mysql://192.168.130.212/mgex_riskmgnt";
    final private static String USER = "root";
    final private static String PASS = "mgex";
    private String sqlStmt = "DELETE FROM t_datarlt_trades_wrk";
    private Connection connection;
    private List<Portfolio> empList = null;
    
    private JTextArea j;
    
    public ImportTradeData(JTextArea j) {
       this.j = j;
    }
    
    public void file(){
        
    }
    
    public List<Portfolio> getEmpList() {
        return empList;
    }
    
    public boolean startTime(){
        int timeCompare;
        boolean start;

        SimpleDateFormat sdf = new SimpleDateFormat("kkmmssS");
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        timeCompare = Integer.parseInt(sdf.format(cal.getTime()));      
        
        if (dayOfWeek != 7) {
            if(timeCompare >= 190000000 || timeCompare < 74500000){
                start = true;
            } else if (timeCompare >= 83000000 && timeCompare < 133000000){
                start = true; 
            } else {
                start = false;
            }
        } else {
            start = false;
        }
        
        return start;
    }
    
    public void parseXML(boolean a) {
        if (a == true){
        Timer timer = new Timer();

        timer.schedule(new TimerTask(){
            public void run(){
                if(startTime()){
                ActivityLogger act = new ActivityLogger(j);
                act.logger("Trades", 1, true);
     
            connection = null;
            try {
                    Class.forName(JDBC_CONNECTION_URL);
                    connection = DriverManager.getConnection(DB_URL,USER,PASS);
                  
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            PreparedStatement Stmt = null;

            try{

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL,USER,PASS);

                Stmt = connection.prepareStatement(sqlStmt);
                Stmt.executeUpdate();

                Stmt.close();
                connection.close();
                } catch(SQLException sqle) {
		   System.out.println("SQLException : " + sqle); 
                   act.logger("Trades", 4, false);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            
          //  Parser call for XML file
            
                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                try {
                    SAXParser saxParser = saxParserFactory.newSAXParser();
                    MyHandler handler = new MyHandler();
                    saxParser.parse(new File("K:\\Market Admin\\Risk_Mgnt\\DATA\\Risktrade.xml"), handler); 
                    //Get Employees list
                    List<Portfolio> tradeList = handler.getEmpList();
                    } catch (ParserConfigurationException | SAXException | IOException e) {
                    e.printStackTrace();
                    act.logger("Trades", 4, false);
                    }
                act.logger("Trades", 2, true);
            }
              
            }
        },60 * 1000, 900 * 1000);//Initial start 60 seconds, updates every 15 mins after
        }
    }
}
*/
        
/*
package risk_mgnt_manager;
   
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;


public class ImportTradeData extends DefaultHandler {
    final private static String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
    final private static String DB_URL = "jdbc:mysql://192.168.130.212/mgex_riskmgnt";
    final private static String USER = "root";
    final private static String PASS = "mgex";
    private String sqlStmt = "TRUNCATE t_datarlt_trades_wrk";
    private Connection connection;
    
    public void parseXML() throws IOException, InterruptedException{
           
           fileWatcher();

    }
    
    public ImportTradeData() {
       
    }
    
    private void fileWatcher() throws IOException, InterruptedException {
       
       Path myDir = Paths.get("C:\\Risk_Trades\\RiskTrade");
       WatchService watcher = FileSystems.getDefault().newWatchService();
       myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, 
       StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
       
            boolean valid = true;
            while (valid) {
                
                try {
                    
                     WatchKey watchKey = watcher.take();
                     
                     List<WatchEvent<?>> events = watchKey.pollEvents();
                     
                     for (WatchEvent event : events) {
                         
                         if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                             System.out.println("Created: " + event.context().toString());

                             truncateTable();
                             beginParse();  
                         }
                         
                         if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                             System.out.println("Modified: " + event.context().toString());

                             

                             truncateTable();
                             beginParse();  
                         }
                     }
                     
                     valid = watchKey.reset();
                    
                } catch (Exception e) {
                        System.out.println("Error: " + e.toString());
                }
                
            };
       
    }
    
    private void truncateTable() throws IOException {


        connection = null;
        try {
             Class.forName(JDBC_CONNECTION_URL);
             connection = DriverManager.getConnection(DB_URL,USER,PASS);
                  
        } catch (ClassNotFoundException e) {
             e.printStackTrace();
        } catch (SQLException e) {
             e.printStackTrace();
        }
        
        PreparedStatement Stmt = null;

        try{

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL,USER,PASS);

            Stmt = connection.prepareStatement(sqlStmt);
            Stmt.executeUpdate();

            Stmt.close();
            connection.close();
                                    
        } catch(SQLException sqle) {
             System.out.println("SQLException : " + sqle); 

        } catch (ClassNotFoundException e) {
             e.printStackTrace();
        }

    }
    
    private void beginParse() throws IOException {
        
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
             SAXParser saxParser = saxParserFactory.newSAXParser();
             MyHandler handler = new MyHandler();
             saxParser.parse(new File("K:\\Market Admin\\Risk_Mgnt\\DATA\\Risktrade.xml"), handler); 
                    
             List<Portfolio> tradeList = handler.getEmpList();
             
        } catch (ParserConfigurationException | SAXException | IOException e) {
             e.printStackTrace();
        }
        
    }
}
*/

