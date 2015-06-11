/*
 * Automation control Program is a Java based ETL for importing data from 
 * various sources into the MGEX_RISKMGNT database located on the CGM server.
 * The Start Automation method contains all the classes that act as the working
 * components for the different ETL's of the program. Each of these classes are
 * set to execute on different time increments.
 */

package risk_mgnt_manager;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import risk_mgnt_manualImport.ManualImportFrame;
import risk_mngt_reports.ReportAutomation;

public class Risk_Mgnt_Manager extends JFrame{
    boolean begin = false;
    String message = null;
    JTextArea text = new JTextArea();
    JButton Start = new JButton("Start");
    JButton Exit = new JButton("Exit Program");
    JButton HousePositionImport = new JButton("Import House position");
    String fileout; 
    
    JMenuBar menuBar;
    JMenu menu, submenu;
    JMenuItem menuItem, fileitem, About;

    ManualImportFrame man = new ManualImportFrame();
    ImportHousePositions ihp = new ImportHousePositions(text);
    
    public Risk_Mgnt_Manager(){
        //LayoutManager Setup
        setLayout(new GridLayout(2, 2, 5, 5)); 
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(3,0));
        JPanel TextAreaPanel = new JPanel(new GridLayout(1, 1));
        JPanel menuPanel = new JPanel(new GridLayout(1, 1));
        
        menuBar = new JMenuBar();
 
        //Build the first menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu exits program");
        
        fileitem = new JMenuItem("Exit Program", KeyEvent.VK_T);
        menu.add(fileitem);
        menuBar.add(menu);
        
        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_M);
        menu.getAccessibleContext().setAccessibleDescription(
                "Loads new window for manual loading");
        menuBar.add(menu);
        
        menuItem = new JMenuItem("Manual Load", KeyEvent.VK_T);
        menu.add(menuItem);
        
        menu = new JMenu("About");
        menu.setMnemonic(KeyEvent.VK_O);
        menu.getAccessibleContext().setAccessibleDescription(
                "Program Discription");
        menuBar.add(menu);
        
        // JTextArea features
        text.setEditable(false);
        text.setWrapStyleWord(true);
        text.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(text);
        
        //Panels added to JFrame
        menuPanel.add(menuBar);
        
        buttonPanel.add(new JLabel("Start Automated Processes: "));
        buttonPanel.add(Start);

        
        buttonPanel.add(new JLabel("End Automated Processes: "));
        buttonPanel.add(Exit);
        buttonPanel.add(HousePositionImport);
        TextAreaPanel.add(scroll);
        
        //Listeners for JButtons
        Start.addActionListener(new startActions());//Listener for button 1
        Exit.addActionListener(new Quit());//Listener for button 2
        HousePositionImport.addActionListener(new importFile());
        menuItem.addActionListener(new manualProcedures());
        fileitem.addActionListener(new Quit());
        
        mainPanel.add(menuPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        add(TextAreaPanel);
    }
    
    public void StartAutomation(boolean start) throws SAXException, 
            ParserConfigurationException, IOException, SQLException, 
            FileNotFoundException, JSchException, SftpException {
            /*
                This method houses the individual components (classes) that perform
                the different ETL's for the database. Each component is called to
                perform its task as soon as the "Start" button is pressed
            */

            // Step one import settlement data from FIX 1 settlement tables
            ImportSettles tbl = new ImportSettles(text);
            //tbl.DataTransfer(begin);

            // Step two import Real-Time price data from t_span_price on FIX 1
            ImportSpanPrice tbl2 = new ImportSpanPrice(text);
            //tbl2.DataTransfer1(begin);

            // Step three import pledge report from Wells Fargo
            ImportPledgeReport tbl3 = new ImportPledgeReport(text);
            //tbl3.SFTPImport(begin);

            // Step four not used as of 11/26/2013
            ImportMas90 tbl4 = new ImportMas90(text);
            //tbl4.csvImportMas90(begin);

            // Step five import poew.csv file
            ImportPOEW tbl5 = new ImportPOEW(text);
            //tbl5.csvImportPOEW(begin);
   
            // Step six import paycollect.csv file
            ImportPaycollect tbl6 = new ImportPaycollect(text);
            //tbl6.csvImportPaycollect(begin);
       
            // Step seven import data from RISK 1 db
            ImportSecDeposit tbl7 = new ImportSecDeposit(text);
            //tbl7.DataTransfer2(begin);

            // Step nine import CGM_post.csv file
            ImportCGMPost tbl9 = new ImportCGMPost(text);
            //tbl9.csvImportCGMPost(begin);

            // Step ten import RM_Intraday_paycollect.csv
            ImportIntraday tbl10 = new ImportIntraday(text);
            //tbl10.csvImportIntra(begin);  
            
            // Step eleven execute .bat and import results via .csv files
            ImportStressTest tbl11 = new ImportStressTest(text);
            //tbl11.csvImport(begin);
            
            // Step twelve import USER_MGEX file from CME SFTP and import to DB
            ImportUsersMgex tbl12 = new ImportUsersMgex(text);
            //tbl12.grabUsersMgex(begin);
            
            ImportTradeData tbl13 = new ImportTradeData(text);
            //tbl13.TradeData(begin);
            //tbl13.MoveTradestoPRDtable();
            
            MarginRequirement_Update tbl14 = new MarginRequirement_Update(text);
            //tbl14.getMargins();
            
            ImportDSROPaycollect tbl15 = new ImportDSROPaycollect(text);
            tbl15.csvImportPaycollect(begin);
            
            // Step twelve execute reports for MGEX_RISKMGNT db
            ReportAutomation rpt1 = new ReportAutomation (text);
            //rpt1.fifteenMinJob();
            //rpt1.elevenThirtyJob();
            
            // calls class to export the JTextarea text to a .txt file and clears 
            // JTextarea for next days logs
            LogTheDay log = new LogTheDay(text);
            //log.execute(); 
            
    }
    
    // JFrame setup and features
    private static void ProjectFrame() throws IOException{              
        Risk_Mgnt_Manager projectFrame = new Risk_Mgnt_Manager();
        //projectFrame.setIconImage(new ImageIcon("src/resources/X.png").getImage());
        ImageIcon icon = new ImageIcon(Risk_Mgnt_Manager.class.getResource("/resources/X.png"));
        projectFrame.setIconImage(icon.getImage());
        projectFrame.setSize(500, 300); //JFrame size set
        projectFrame.setResizable(false);// won't allow JFrame to be resized
        projectFrame.setLocationRelativeTo(null); //JFrame centered to center of screen
        projectFrame.setTitle("Automation Control"); //JFrame Title
        projectFrame.setVisible(true);//JFrame is visible upon start of program
        projectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }    
    
    // Main
    public static void main(String[] args) throws IOException, InterruptedException {
        ProjectFrame();
        
    }
    
    // Exit button action
    static class Quit implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                //Once Exit JButton is pressed the program exits
                System.exit(0);
            }
        }

    public class manualProcedures implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
               man.OpenWindow();
            }
    }
    
    public class importFile implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
               ihp.importData();
            }
    }
    
    // Start button action
    public class startActions implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                
                begin = true;
                try {
                    StartAutomation(begin); // calls method StartAutomation
                } catch (        SAXException | ParserConfigurationException | IOException | SQLException | JSchException | SftpException ex) {
                    Logger.getLogger(Risk_Mgnt_Manager.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        }
}
