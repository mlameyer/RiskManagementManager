/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manualImport;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import resources.ServerConn;

/**
 *
 * @author bgilbert
 */
public class MarginsRecovery extends JFrame{
    JPanel PanelOne = new JPanel(new FlowLayout());
    JPanel PanelTwo = new JPanel(new FlowLayout());
    JPanel PanelThree = new JPanel(new FlowLayout());
    JPanel PanelFour = new JPanel(new FlowLayout());
    JTextField EntryField1;
    JTextField EntryField2;
    JTextField EntryField3;
    JTextField EntryField4;
    
    public MarginsRecovery() {
        setLayout(new GridLayout(0,1,5,5));
        
        JLabel notice1 = new JLabel("The First step is to determine if the "
                + "Settlement data for the missing date is in the table "
                + "mgex_riskmgnt.t_data_settlements");
        JLabel dateEntryField1 = new JLabel("Enter date, Format YYYYMMDD:");
        EntryField1 = new JTextField("",20);
        JButton dateButton1 = new JButton("Look up Date");
        
        JLabel notice2 = new JLabel("<html>The Second step is to determine if the "
                + "Settlement data was added to the tables t_marginhist_current<br>"
                + "and t_marginhist_spread with the current margin requirements<html>");
        JLabel dateEntryField2 = new JLabel("Enter date, Format YYYYMMDD:");
        EntryField2 = new JTextField("",20);
        JButton dateButton2 = new JButton("Look up Date");
        
        JLabel notice3 = new JLabel("<html>The Third step is to determine if the "
                + "Settlement data was added to the tables t_mgn_history_current<br>"
                + "and t_mgn_history_spread with the current margin requirements and calculations<html>");
        JLabel dateEntryField3 = new JLabel("Enter date, Format YYYYMMDD:");
        EntryField3 = new JTextField("",20);
        JButton dateButton3 = new JButton("Look up Date");
        
        JLabel notice4 = new JLabel("<html>The Forth step is to determine if the "
                + "Settlement data was added to the tables t_mgn_summary_current<br>"
                + "and t_mgn_summary_spread with the date's calculations completed<html>");
        JLabel dateEntryField4 = new JLabel("Enter date, Format YYYYMMDD:");
        EntryField4 = new JTextField("",20);
        JButton dateButton4 = new JButton("Look up Date");
        
        
        dateButton1.addActionListener(new queryTable());
        dateButton2.addActionListener(new queryTable2());
        dateButton3.addActionListener(new queryTable3());
        dateButton4.addActionListener(new queryTable4());
        
        PanelOne.add(notice1);
        PanelOne.add(dateEntryField1);
        PanelOne.add(EntryField1);
        PanelOne.add(dateButton1);
        
        PanelTwo.add(notice2);
        PanelTwo.add(dateEntryField2);
        PanelTwo.add(EntryField2);
        PanelTwo.add(dateButton2);
        
        PanelThree.add(notice3);
        PanelThree.add(dateEntryField3);
        PanelThree.add(EntryField3);
        PanelThree.add(dateButton3);
        
        PanelFour.add(notice4);
        PanelFour.add(dateEntryField4);
        PanelFour.add(EntryField4);
        PanelFour.add(dateButton4);
        
        add(PanelOne);
        add(PanelTwo);
        add(PanelThree);
        add(PanelFour);
    }

    void beginSession() {
        MarginFrame();
    }
    
    private void MarginFrame() {
        JFrame frame = new MarginsRecovery();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 700); //JFrame size set
        frame.setResizable(false);// won't allow JFrame to be resized
        frame.setLocationRelativeTo(null); //JFrame centered to center of screen
        frame.setVisible(true);
    }

    private class queryTable implements ActionListener {
        Statement statement = null;
        Connection conn = null;
        @Override
        public void actionPerformed(ActionEvent e) {
            startQuery(); 
        }

        private void startQuery() {
            try {
                String selectTableSQL = "SELECT DISTINCT TradeDate FROM mgex_riskmgnt.t_data_settlements WHERE TradeDate = " + EntryField1.getText();
                boolean hasRows = false;
                
                ServerConn con = new ServerConn();
                conn = con.getCMEServerConn();
                statement = conn.createStatement();
                //System.out.println(selectTableSQL);
                
                ResultSet rs = statement.executeQuery(selectTableSQL);
                
                while (rs.next()) {
                    hasRows = true;
                    String tradedate = rs.getString("TradeDate");
                    JOptionPane.showMessageDialog(null,"Date " + tradedate + " is in the table. Proceed to next step.");
                }
                
                if(!hasRows){
                       JOptionPane.showMessageDialog(null,"Date entered is not "
                               + "in the table.\nManually import the settlements "
                               + "with the Settlements button in the main menu of "
                               + "the Manual Load menu.");
                } 
                
            } catch (SQLException ex) {
                Logger.getLogger(MarginsRecovery.class.getName()).log(Level.SEVERE, null, ex);
            }finally {
                try{
			if (statement != null) {
				statement.close();
			}
 
			if (conn != null) {
				conn.close();
			}
                }catch(SQLException ex){
                   Logger.getLogger(MarginsRecovery.class.getName()).log(Level.SEVERE, null, ex); 
                }
 
		}
        }
    }
    
    private class queryTable2 implements ActionListener {
        Statement statement = null;
        Connection conn = null;
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = EntryField2.getText();
            MarginsUpdater mu = new MarginsUpdater();
            mu.marginHistCurrent(s);
            mu.marginHistSpread(s);
        }
    } 
    
    private class queryTable3 implements ActionListener {
        Statement statement = null;
        Connection conn = null;
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = EntryField3.getText();
            MarginsUpdater mu = new MarginsUpdater();
            mu.HistCurrent(s);
            mu.HistSpread(s);
        }
    }
    
    private class queryTable4 implements ActionListener {
        Statement statement = null;
        Connection conn = null;
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = EntryField4.getText();
            MarginsUpdater mu = new MarginsUpdater();
            mu.SummaryCurrent(s);
            mu.SummarySpread(s);
        }
    }
}
