/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manualImport;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import resources.CLRDataAS400;

/**
 *
 * @author bgilbert
 */
public class ManualImportFrame extends JPanel{
    JTextField field,field2,field3,field4,field5,field6,field7,field8,field9;
    ImageIcon image = new ImageIcon("C:/Program Files/AutomationControlapp/src/horyshet.png");
    JLabel label = new JLabel("", image, JLabel.CENTER);
    
    JPanel ButtonPanel;
    JPanel DynamicPanel;
    JPanel CGMPost;
    JPanel IntraDay;
    JPanel Poew;
    JPanel Paycollect;
    JPanel PledgeReport;
    JPanel Settlements;
    JPanel StressTest;
    JPanel UserMGEX;
    JPanel MarginsUpdate;
    JPanel MAS90Update;
    
    JButton button1 = new JButton("CGM Post");
    JButton button2 = new JButton("Intraday");
    JButton button3 = new JButton("Poew");
    JButton button4 = new JButton("PayCollect");
    JButton button5 = new JButton("Pledge Report");
    JButton button6 = new JButton("Settlements");
    JButton button7 = new JButton("Stress Test");
    JButton button8 = new JButton("MGEX Users");
    JButton button9 = new JButton("Margin Updates");
    JButton button10 = new JButton("MAS90");

    public ManualImportFrame(){
        setLayout(new GridLayout(0,2,5,5));
        ButtonPanel = new JPanel(new GridLayout(0,1));
        DynamicPanel = new JPanel(new GridLayout(0,1));
        CGMPost = new JPanel(new FlowLayout());
        IntraDay = new JPanel();
        Poew = new JPanel();
        Paycollect = new JPanel();
        PledgeReport = new JPanel();
        Settlements = new JPanel();
        StressTest = new JPanel();
        UserMGEX = new JPanel();
        MarginsUpdate = new JPanel();
        MAS90Update = new JPanel();
        
        //POEW Panel setup --------------------------------------------
        JLabel postlabel = new JLabel("Enter Date of POST file to import. Format: YYYYMMDD "
                + "Example 20140120");
        field = new JTextField("",20);
        JButton button18 = new JButton("Execute");
        CGMPost.add(postlabel);
        CGMPost.add(field);
        CGMPost.add(button18);
        button18.addActionListener(new POSTExecute());
        //POEW ---------------------------------------------------------
        
        //POEW Panel setup --------------------------------------------
        JLabel poewlabel = new JLabel("Enter Date of POEW file to import. Format: YYYYMMDD "
                + "Example 20140120");
        field2 = new JTextField("",20);
        JButton button11 = new JButton("Execute");
        Poew.add(poewlabel);
        Poew.add(field2);
        Poew.add(button11);
        button11.addActionListener(new POEWExecute());
        //POEW ---------------------------------------------------------
        
        //Paycollect Panel setup --------------------------------------------
        JLabel payclabel = new JLabel("Enter Date of Paycollect file to import. Format: YYYYMMDD "
                + "Example 20140120");
        field3 = new JTextField("",20);
        JButton button12 = new JButton("Execute");
        Paycollect.add(payclabel);
        Paycollect.add(field3);
        Paycollect.add(button12);
        button12.addActionListener(new PayCExecute());
        //Paycollect ---------------------------------------------------------
        
        //PledgeReport Panel setup --------------------------------------------
        JLabel pledgelabel = new JLabel("Enter Date of Pledge Report file to import. Format: YYYYMMDD "
                + "Example 20140120");
        field4 = new JTextField("",20);
        JButton button13 = new JButton("Execute");
        PledgeReport.add(pledgelabel);
        PledgeReport.add(field4);
        PledgeReport.add(button13);
        button13.addActionListener(new PledgeExecute());
        //PledgeReport ---------------------------------------------------------
        
        //Settlements Panel setup --------------------------------------------
        JLabel Settlementslabel = new JLabel("Enter Date of Settlements to import. Format: YYYYMMDD "
                + "Example 20140120");
        field5 = new JTextField("",20);
        JButton button14 = new JButton("Execute");
        Settlements.add(Settlementslabel);
        Settlements.add(field5);
        Settlements.add(button14);
        button14.addActionListener(new SettleExecute());
        //Settlements ---------------------------------------------------------
        
        //UserMGEX Panel setup --------------------------------------------
        JLabel UserMGEXlabel = new JLabel("Enter Date of UserMGEX file to import. Format: YYYYMMDD "
                + "Example 20140120");
        field6 = new JTextField("",20);
        JButton button15 = new JButton("Execute");
        UserMGEX.add(UserMGEXlabel);
        UserMGEX.add(field6);
        UserMGEX.add(button15);
        button15.addActionListener(new UserExecute());
        //UserMGEX ---------------------------------------------------------
        
        //Margins Panel setup --------------------------------------------
        JLabel MarginsUpdatelabel = new JLabel("If data is missing, click the Proceed"
                + " button and follow instructions precisely.");
        JButton button16 = new JButton("Proceed");
        MarginsUpdate.add(MarginsUpdatelabel);
        MarginsUpdate.add(button16);
        button16.addActionListener(new MarginsUpdateExecute());
        //Margins ---------------------------------------------------------
        
        //Margins Panel setup --------------------------------------------
        JLabel MAS90label = new JLabel("Enter Date of MAS90 file to import. "
                + "Format: YYYYMMDD Example 20140120");
        field7 = new JTextField("",20);
        JButton button17 = new JButton("Execute");
        MAS90Update.add(MAS90label);
        MAS90Update.add(field7);
        MAS90Update.add(button17);
        button17.addActionListener(new MAS90Execute());
        //Margins ---------------------------------------------------------
        
        //Stress Panel setup ----------------------------------------------
        StressTest.add(label, BorderLayout.CENTER);
        //Stress ----------------------------------------------------------
        
        button1.addActionListener(new CGMActionPerformed());
        button2.addActionListener(new IntraActionPerformed());
        button3.addActionListener(new PoewActionPerformed());
        button4.addActionListener(new PayCActionPerformed());
        button5.addActionListener(new PledgeActionPerformed());
        button6.addActionListener(new SetActionPerformed());
        button7.addActionListener(new StressActionPerformed());
        button8.addActionListener(new UserActionPerformed());
        button9.addActionListener(new MarActionPerformed());
        button10.addActionListener(new MAS90ActionPerformed());
        
        ButtonPanel.add(button1);
        ButtonPanel.add(button2);
        ButtonPanel.add(button3);
        ButtonPanel.add(button4);
        ButtonPanel.add(button5);
        ButtonPanel.add(button6);
        ButtonPanel.add(button7);
        ButtonPanel.add(button8);
        ButtonPanel.add(button9);
        ButtonPanel.add(button10);
        
        add(ButtonPanel);
        add(DynamicPanel);
    }
    
    private void Execute(int i){
        switch(i){
            case 1: String s = field.getText();
                    ImportPost p = new ImportPost();
                    p.exeTask(s);
                break;
            case 2: String a = field2.getText();
                    ImportPoew v = new ImportPoew();
                    v.exeTask(a);
                break;
            case 3: String b = field3.getText();
                    ImportPayCollectManual u = new ImportPayCollectManual();
                    u.exeTask(b);
                break;
            case 4: String c = field4.getText();
                    ImportPledgeReportManual t = new ImportPledgeReportManual();
                    t.exeTask(c);
                break;
            case 5: String d = field5.getText();
                    ImportSettlesManual r = new ImportSettlesManual();
                    r.exeTask(d);
                break;
            case 6: String e = field7.getText();
                    ImportMAS90Manual o = new ImportMAS90Manual();
                    o.exeTask(e);
                break;
            case 7: String ee = field6.getText();
                    ImportUserMGEXManual oo = new ImportUserMGEXManual();
                    oo.exeTask(ee);
                break;
            default: System.out.print("Oh Snap!!!!");
                break;
        }
    }
    
    public void OpenWindow() {
        Frame();
    }

    private void Frame() {
        JFrame frame = new JFrame();
        JComponent newContentPane = new ManualImportFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(newContentPane);
        frame.setSize(1000, 700); //JFrame size set
        frame.setResizable(false);// won't allow JFrame to be resized
        frame.setLocationRelativeTo(null); //JFrame centered to center of screen
        frame.setVisible(true);
    }

    private class CGMActionPerformed implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            int i = -1;
            DynamicPanel.removeAll();
            DynamicPanel.revalidate();
            
            DynamicPanel.add(CGMPost);
            DynamicPanel.validate();
            DynamicPanel.repaint();
            
            i = JOptionPane.showConfirmDialog(CGMPost, "Do you want to import "
                    + "the Post file from the AS400?");
            if(i == 0){
               CLRDataAS400 exe = new CLRDataAS400(); 
               exe.POSTDataXFR();
            }
            
            if(i == 1){
                DynamicPanel.add(CGMPost);
                DynamicPanel.validate();
                DynamicPanel.repaint(); 
            }
        }
    }
    
    public class POSTExecute implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 1;
            Execute(i);
        }
    }
    
    public class IntraActionPerformed implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            int i = -1;
            DynamicPanel.removeAll();
            DynamicPanel.revalidate();
        
            DynamicPanel.add(IntraDay);
            DynamicPanel.validate();
            DynamicPanel.repaint();
            
            i = JOptionPane.showConfirmDialog(IntraDay, "Do you want to import "
                    + "the IntraDay Paycollect file from the AS400?");
            if(i == 0){
               CLRDataAS400 exe = new CLRDataAS400(); 
               exe.IntraPayCollectDataXFR();
            }
        }
    }
    
    public class PoewActionPerformed implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            int i = -1;
            DynamicPanel.removeAll();
            DynamicPanel.revalidate();
            
            i = JOptionPane.showConfirmDialog(Poew, "Do you want to import "
                    + "the Post file from the AS400?");
            if(i == 0){
               CLRDataAS400 exe = new CLRDataAS400(); 
               exe.POSTDataXFR();
            }
            
            if(i == 1){
                DynamicPanel.add(Poew);
                DynamicPanel.validate();
                DynamicPanel.repaint(); 
            }
        }
    }
    
    public class POEWExecute implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 2;
            Execute(i);
        }
    }
    
    public class PayCActionPerformed implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            int i = -1;
            DynamicPanel.removeAll();
            DynamicPanel.revalidate();
            
            i = JOptionPane.showConfirmDialog(Paycollect, "Do you want to import "
                    + "the Paycollect file from the AS400?");
            if(i == 0){
               CLRDataAS400 exe = new CLRDataAS400(); 
               exe.POSTDataXFR();
            }
            
            if(i == 1){
                DynamicPanel.add(Paycollect);
                DynamicPanel.validate();
                DynamicPanel.repaint(); 
            }
        }
    }
    
    public class PayCExecute implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 3;
            Execute(i);
        }
    }
    
    public class PledgeActionPerformed implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            int i = -1;
            DynamicPanel.removeAll();
            DynamicPanel.revalidate();
            
            i = JOptionPane.showConfirmDialog(PledgeReport, "Do you want to import "
                    + "the PledgeReport file from the SFTP Server?");
            
            if(i == 0){
                DynamicPanel.add(PledgeReport);
                DynamicPanel.validate();
                DynamicPanel.repaint(); 
            }
        }
    }
    
    public class PledgeExecute implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 4;
            Execute(i);
        }
    }
    
    public class SetActionPerformed implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            int i = -1;
            DynamicPanel.removeAll();
            DynamicPanel.revalidate();
            
            i = JOptionPane.showConfirmDialog(Settlements, "Do you want to import "
                    + "the settlements from MGEX_OPS Database?");
            
            if(i == 0){
                DynamicPanel.add(Settlements);
                DynamicPanel.validate();
                DynamicPanel.repaint(); 
            }
        }
    }
    
    public class SettleExecute implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 5;
            Execute(i);
        }
    }
    
    public class StressActionPerformed implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            DynamicPanel.removeAll();
            DynamicPanel.revalidate();
        
            DynamicPanel.add(StressTest);
            DynamicPanel.validate();
            DynamicPanel.repaint();
        }
    }
    
    public class UserActionPerformed implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            int i = -1;
            DynamicPanel.removeAll();
            DynamicPanel.revalidate();
            
            i = JOptionPane.showConfirmDialog(UserMGEX, "Do you want to import "
                    + "the UserMGEX file from the FTP Server?");
            
            if(i == 0){
                DynamicPanel.add(UserMGEX);
                DynamicPanel.validate();
                DynamicPanel.repaint(); 
            }
        }
    }
    
    public class UserExecute implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 7;
            Execute(i);
        }
    }
    
    public class MarActionPerformed implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            DynamicPanel.removeAll();
            DynamicPanel.revalidate();
        
            DynamicPanel.add(MarginsUpdate);
            DynamicPanel.validate();
            DynamicPanel.repaint();
        }
    }
    
    public class MarginsUpdateExecute implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            MarginsRecovery rec = new MarginsRecovery();
            rec.beginSession();
        }
    }
    
    public class MAS90ActionPerformed implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            DynamicPanel.removeAll();
            DynamicPanel.revalidate();
        
            DynamicPanel.add(MAS90Update);
            DynamicPanel.validate();
            DynamicPanel.repaint();
        }
    }
    
    public class MAS90Execute implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int i = 6;
            Execute(i);
        }
    }
    
}
