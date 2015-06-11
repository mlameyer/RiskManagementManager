/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bgilbert
 */
public class CLRDataAS400 {
    
    ArrayList<String> list = new ArrayList<>();
    
    public void IntraPayCollectDataXFR() {
        final String table = "t_data_intradaypaycollect";
        final String query = "INSERT INTO t_data_intradaypaycollect (Exchange, Clearing_Member, "
                + "Reg_Seg, Yesterday_Cash_Balance, Yesterday_non_cash_Balance, "
                + "Yesterday_Margin_Requirement, Intraday_Variation, Option_Fluctuation, "
                + "UnKnown, NA, NA2, NA3, Current_Margin_Requirement, "
                + "NA4, End_of_Day_Pay, End_of_Day_Collect, New_Cash_Margin, "
                + "New_Noncash_Margin, Date2, Residual) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        final int colCount = 20;
        try {
            truncateTable(table);
            ServerConn c = new ServerConn();
            ResultSet rs;
            try (Connection con = c.getAS400Conn()) {
                Statement stmt;
                
                stmt = con.createStatement();
                rs = stmt.executeQuery("Select * from CLRDAT.CLRMGNT");
                
                while (rs.next()) {
                    
                    list.add(rs.getString("MGEXCH"));
                    list.add(rs.getString("MGFIRM"));
                    list.add(rs.getString("MGFRCH"));
                    list.add(rs.getString("MGYBCS"));
                    list.add(rs.getString("MGYBNC"));
                    list.add(rs.getString("MGYMRQ"));
                    list.add(rs.getString("MGTFLF"));
                    list.add(rs.getString("MGTFLO"));
                    list.add(rs.getString("MGTFCF"));
                    list.add(rs.getString("MGTFCO"));
                    list.add(rs.getString("MGTTCS"));
                    list.add(rs.getString("MGTTNC"));
                    list.add(rs.getString("MGTMRQ"));
                    list.add(rs.getString("MGTMOF"));
                    list.add(rs.getString("MGTCTI"));
                    list.add(rs.getString("MGTCTO"));
                    list.add(rs.getString("MGTBCS"));
                    list.add(rs.getString("MGTBNC"));
                    list.add(rs.getString("MGDATE"));
                    list.add(rs.getString("MGTGAR"));
                    
                }
                
                stmt.close();
            }
        rs.close();
        
        } catch (SQLException ex) {
            Logger.getLogger(CLRDataAS400.class.getName()).log(Level.SEVERE, null, ex);
        }
        importData(query, colCount);
    }
    
    public void PayCollectDataXFR(){
        final String query = "INSERT INTO t_dataclrpaycollect (Exchange, Clearing_Member, "
                + "Reg_Seg, Yesterday_Cash_Balance, Yesterday_non_cash_Balance, "
                + "Yesterday_Margin_Requirement, Futures_Fluctuation, Option_Fluctuation, "
                + "Intraday_Variation, NA, NA2, NA3, Current_Margin_Requirement, "
                + "NA4, End_of_Day_Pay, End_of_Day_Collect, New_Cash_Margin, "
                + "New_Noncash_Margin, Date2, Residual) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        final int colCount = 20;
        try {
            ServerConn c = new ServerConn();
            ResultSet rs;
            try (Connection con = c.getAS400Conn()) {
                Statement stmt;
                
                stmt = con.createStatement();
                rs = stmt.executeQuery("Select * from CLRDAT.CLRMGNT");
                
                while (rs.next()) {
                    
                    list.add(rs.getString("MGEXCH"));
                    list.add(rs.getString("MGFIRM"));
                    list.add(rs.getString("MGFRCH"));
                    list.add(rs.getString("MGYBCS"));
                    list.add(rs.getString("MGYBNC"));
                    list.add(rs.getString("MGYMRQ"));
                    list.add(rs.getString("MGTFLF"));
                    list.add(rs.getString("MGTFLO"));
                    list.add(rs.getString("MGTFCF"));
                    list.add(rs.getString("MGTFCO"));
                    list.add(rs.getString("MGTTCS"));
                    list.add(rs.getString("MGTTNC"));
                    list.add(rs.getString("MGTMRQ"));
                    list.add(rs.getString("MGTMOF"));
                    list.add(rs.getString("MGTCTI"));
                    list.add(rs.getString("MGTCTO"));
                    list.add(rs.getString("MGTBCS"));
                    list.add(rs.getString("MGTBNC"));
                    list.add(rs.getString("MGDATE"));
                    list.add(rs.getString("MGTGAR"));
                    
                }
                
                stmt.close();
            }
        rs.close();
        
        } catch (SQLException ex) {
            Logger.getLogger(CLRDataAS400.class.getName()).log(Level.SEVERE, null, ex);
        }
        importData(query, colCount);
    }
    
    public void PayCollectDataXFR2(){
        final String table = "t_data_dailycollateral";
        final String query = "INSERT INTO t_data_dailycollateral (Exchange, Clearing_Member, "
                + "Reg_Seg, Yesterday_Cash_Balance, Yesterday_non_cash_Balance, "
                + "Yesterday_Margin_Requirement, Futures_Fluctuation, Option_Fluctuation, "
                + "Intraday_Variation, NA, NA2, NA3, Today_Margin_Requirement, "
                + "NA4, End_of_Day_Pay, End_of_Day_Collect, New_Cash_Margin, "
                + "New_Noncash_Margin, Date2, Residual) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        final int colCount = 20;
        
        try {
            
            truncateTable(table);
            
            ServerConn c = new ServerConn();
            ResultSet rs;
            try (Connection con = c.getAS400Conn()) {
                Statement stmt;
                
                stmt = con.createStatement();
                rs = stmt.executeQuery("Select * from CLRDAT.CLRMGNT");
                
                while (rs.next()) {
                    
                    list.add(rs.getString("MGEXCH"));
                    list.add(rs.getString("MGFIRM"));
                    list.add(rs.getString("MGFRCH"));
                    list.add(rs.getString("MGYBCS"));
                    list.add(rs.getString("MGYBNC"));
                    list.add(rs.getString("MGYMRQ"));
                    list.add(rs.getString("MGTFLF"));
                    list.add(rs.getString("MGTFLO"));
                    list.add(rs.getString("MGTFCF"));
                    list.add(rs.getString("MGTFCO"));
                    list.add(rs.getString("MGTTCS"));
                    list.add(rs.getString("MGTTNC"));
                    list.add(rs.getString("MGTMRQ"));
                    list.add(rs.getString("MGTMOF"));
                    list.add(rs.getString("MGTCTI"));
                    list.add(rs.getString("MGTCTO"));
                    list.add(rs.getString("MGTBCS"));
                    list.add(rs.getString("MGTBNC"));
                    list.add(rs.getString("MGDATE"));
                    list.add(rs.getString("MGTGAR"));
 //System.out.println(rs.getString("MGEXCH") + rs.getString("MGFIRM") + rs.getString("MGFRCH"));
                }
                
                stmt.close();
            }
        rs.close();
        
        } catch (SQLException ex) {
            Logger.getLogger(CLRDataAS400.class.getName()).log(Level.SEVERE, null, ex);
        }
        importData(query, colCount);
    }
    
    public void PayCollectDataXFR3(){
        final String table = "t_data_DSROpaycollect";
        final String query = "INSERT INTO t_data_DSROpaycollect (Exchange, Clearing_Member, "
                + "Reg_Seg, Yesterday_Cash_Balance, Yesterday_non_cash_Balance, "
                + "Yesterday_Margin_Requirement, Futures_Fluctuation, Option_Fluctuation, "
                + "Intraday_Variation, NA, NA2, NA3, Current_Margin_Requirement, "
                + "NA4, End_of_Day_Pay, End_of_Day_Collect, New_Cash_Margin, "
                + "New_Noncash_Margin, Date2, Residual) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        final int colCount = 20;
        
        try {
            
            truncateTable(table);
            
            ServerConn c = new ServerConn();
            ResultSet rs;
            try (Connection con = c.getAS400Conn()) {
                Statement stmt;
                
                stmt = con.createStatement();
                rs = stmt.executeQuery("Select * from CLRDAT.CLRMGNT");
                
                while (rs.next()) {
                    
                    list.add(rs.getString("MGEXCH"));
                    list.add(rs.getString("MGFIRM"));
                    list.add(rs.getString("MGFRCH"));
                    list.add(rs.getString("MGYBCS"));
                    list.add(rs.getString("MGYBNC"));
                    list.add(rs.getString("MGYMRQ"));
                    list.add(rs.getString("MGTFLF"));
                    list.add(rs.getString("MGTFLO"));
                    list.add(rs.getString("MGTFCF"));
                    list.add(rs.getString("MGTFCO"));
                    list.add(rs.getString("MGTTCS"));
                    list.add(rs.getString("MGTTNC"));
                    list.add(rs.getString("MGTMRQ"));
                    list.add(rs.getString("MGTMOF"));
                    list.add(rs.getString("MGTCTI"));
                    list.add(rs.getString("MGTCTO"));
                    list.add(rs.getString("MGTBCS"));
                    list.add(rs.getString("MGTBNC"));
                    list.add(rs.getString("MGDATE"));
                    list.add(rs.getString("MGTGAR"));
                    
                }
                
                stmt.close();
            }
        rs.close();
        
        } catch (SQLException ex) {
            Logger.getLogger(CLRDataAS400.class.getName()).log(Level.SEVERE, null, ex);
        }
        importData(query, colCount);
    }
    
    public void POEWDataXFR(){
        final String procedure = "CALL sp_update_poew_spandata()";
        final String query = "INSERT INTO t_data_clr_poew(Exch,Firm,Type,Comm,"
                + "Month,Year,Strike,RegBot,RegSold,ExerBot,ExerSold,SPDBot,"
                + "SPDSold,TFRBot,TFRSold,XCFOBot,XCFOSold,GAPBot,GAPSold,ZBot,"
                + "ZSold,NoBot,NoSold,NetBegLong,NetBegShort,NetEndLong,"
                + "NetEndShort,GrossBegLong,GrossBegShort,GrossEndLong,"
                + "GrossEndShort,PosDate) VALUES "
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        final int colCount = 32;
        try {
            ServerConn c = new ServerConn();
            ResultSet rs;
            try (Connection con = c.getAS400Conn()) {
                Statement stmt;
                
                stmt = con.createStatement();
                rs = stmt.executeQuery("Select * from CLRDAT.CLRPOEW");
                
                while (rs.next()) {
                    
                    list.add(rs.getString("POEXCH"));
                    list.add(rs.getString("POFIRM"));
                    list.add(rs.getString("POFRCH"));
                    list.add(rs.getString("POCOMM"));
                    list.add(rs.getString("POMON"));
                    list.add(rs.getString("POYR4"));
                    list.add(rs.getString("POSTRK"));
                    list.add(rs.getString("PORBOT"));
                    list.add(rs.getString("PORSLD"));
                    list.add(rs.getString("POEBOT"));
                    list.add(rs.getString("POESLD"));
                    list.add(rs.getString("POSBOT"));
                    list.add(rs.getString("POSSLD"));
                    list.add(rs.getString("POTBOT"));
                    list.add(rs.getString("POTSLD"));
                    list.add(rs.getString("POXBOT"));
                    list.add(rs.getString("POXSLD"));
                    list.add(rs.getString("POGBOT"));
                    list.add(rs.getString("POGSLD"));
                    list.add(rs.getString("POZBOT"));
                    list.add(rs.getString("POZSLD"));
                    list.add(rs.getString("PO#BOT"));
                    list.add(rs.getString("PO#SLD"));
                    list.add(rs.getString("PONPBL"));
                    list.add(rs.getString("PONPBS"));
                    list.add(rs.getString("PONPEL"));
                    list.add(rs.getString("PONPES"));
                    list.add(rs.getString("POGPBL"));
                    list.add(rs.getString("POGPBS"));
                    list.add(rs.getString("POGPEL"));
                    list.add(rs.getString("POGPES"));
                    list.add(rs.getString("PODATE"));
                }
                
                stmt.close();
            }
        rs.close();
        
        } catch (SQLException ex) {
            Logger.getLogger(CLRDataAS400.class.getName()).log(Level.SEVERE, null, ex);
        }
        importData(query, colCount);
        runStoredProcedure(procedure);
    }
    
    public void POSTDataXFR(){
        final String table = "t_data_netclrpos";
        final String procedure = "CALL sp_update_post_spandata()";
        final String query = "INSERT INTO t_data_netclrpos(Exch,Firm,Type,Comm,Month,Year,Strike, "
          + "RegBot,RegSold,ExerBot,ExerSold,SPDBot,SPDSold,TFRBot,TFRSold, " 
          + "XCFOBot,XCFOSold,NetBegLong,NetBegShort,NetEndLong, "
          + "NetEndShort,GrossBegLong,GrossBegShort,GrossEndLong,GrossEndShort, " 
          + "PosDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        final int colCount = 26;
        try {
            
            truncateTable(table);
            
            ServerConn c = new ServerConn();
            ResultSet rs;
            try (Connection con = c.getAS400Conn()) {
                Statement stmt;
                
                stmt = con.createStatement();
                rs = stmt.executeQuery("Select * from CLRDAT.CLRPOST");
                
                while (rs.next()) {
                    
                    list.add(rs.getString("POEXCH").trim());
                    list.add(rs.getString("POFIRM").trim());
                    list.add(rs.getString("POFRCH").trim());
                    list.add(rs.getString("POCOMM").trim());
                    list.add(rs.getString("POMON").trim());
                    list.add(rs.getString("POYR4").trim());
                    list.add(rs.getString("POSTRK").trim());
                    list.add(rs.getString("PORBOT").trim());
                    list.add(rs.getString("PORSLD").trim());
                    list.add(rs.getString("POEBOT").trim());
                    list.add(rs.getString("POESLD").trim());
                    list.add(rs.getString("POSBOT").trim());
                    list.add(rs.getString("POSSLD").trim());
                    list.add(rs.getString("POTBOT").trim());
                    list.add(rs.getString("POTSLD").trim());
                    list.add(rs.getString("POXBOT").trim());
                    list.add(rs.getString("POXSLD").trim());
                    list.add(rs.getString("PONPBL").trim());
                    list.add(rs.getString("PONPBS").trim());
                    list.add(rs.getString("PONPEL").trim());
                    list.add(rs.getString("PONPES").trim());
                    list.add(rs.getString("POGPBL").trim());
                    list.add(rs.getString("POGPBS").trim());
                    list.add(rs.getString("POGPEL").trim());
                    list.add(rs.getString("POGPES").trim());
                    list.add(rs.getString("PODATE").trim());
                }
                
                stmt.close();
            }
        rs.close();
        
        } catch (SQLException ex) {
            Logger.getLogger(CLRDataAS400.class.getName()).log(Level.SEVERE, null, ex);
        }
        importData(query, colCount);
        runStoredProcedure(procedure);
    }
    
    private void importData(String query, int cnt){
        ServerConn c = new ServerConn();
        Connection con = c.getCMEServerConn();
        int checkCount = 1;
        
        try{
            con.setAutoCommit(false);
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                int i = 0, size = list.size();
                while(i < size){

                    if(checkCount <= cnt){
                        stmt.setString(checkCount, (String) list.get(i));
                        //System.out.println(checkCount + ", " + list.get(i));
                        i++;
                        checkCount++;
                    } else{
                        stmt.addBatch();
                        checkCount = 1;
                    }
                    if(i == size){
                        stmt.addBatch();
                    }
                }   
            stmt.executeBatch();
            con.commit();
            }
            con.close();
        } catch(SQLException sqle) {
		   System.out.println("SQLException : " + sqle); 
                }
    }
    private void truncateTable(String table){
        try {
            ServerConn c = new ServerConn();
            try (Connection con = c.getCMEServerConn()) {
                con.createStatement().execute("Truncate " + table);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CLRDataAS400.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void runStoredProcedure(String procedure){
        try {
            ServerConn c = new ServerConn();
            try (Connection con = c.getCMEServerConn(); CallableStatement cstmt = con.prepareCall(procedure)) {
                cstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CLRDataAS400.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
