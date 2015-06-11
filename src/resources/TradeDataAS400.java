/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

/**
 *
 * @author bgilbert
 */
public class TradeDataAS400 {
    Date date;
    Time time;
    String Insertstring;
    String field1,field2,field3,field3a,field4,field5,field6,field7,fieldec,
            fieldexch,field8,field9,field10,field11,field13,field14,field15;
    String Mon, Day, Yr;

    float trdprice, field12;
    ServerConn conn = new ServerConn();
    ServerConn conn2 = new ServerConn();
    ServerConn conn3 = new ServerConn();
    ServerConn conn4 = new ServerConn();
    ServerConn conn5 = new ServerConn();
    
    public void Dataxfr() throws SQLException{
        truncateTable();
        ResultSet rs;
        try {
            Connection connect = conn.getAS400Conn();

            Statement stmt;
            CallableStatement cStmt = connect.prepareCall("CALL CLMPGM.SPCNVTRP( ' ') ");
            cStmt.execute();
            stmt = connect.createStatement();
            rs = stmt.executeQuery("Select * from CLMDAT.TEMPSPTRD join CLRDAT.CLRPRFM on trexch = prexch and trcomm = prcomm and tryr2 = substr(pryr4,3,2) and trmon = prmon and trstrk = prstrk join CLRDAT.SPFIRM on trfirm = frfirm order by trpfir, trfrch");
            while (rs.next()) {
                
                if(rs.getString("trfrch").trim().equals("S")){
                    
                    field3 = rs.getString("frsnum").trim() + "1";
                    
                }else{
                    
                    field3 = rs.getString("frsnum").trim() + "2";
                    
                }
                
                if(rs.getString("trfrch").trim().equals("S")){
                    
                    field3a = "SEG";
                    
                }else{
                    
                    field3a = "REG";
                    
                }
                
                field4 = rs.getString("tracct").trim();
                
                field5 = rs.getString("trpbro").trim();
                
                Yr = rs.getString("trdate").substring(3, 7).trim();
                
                if(rs.getString("trdate").substring(0, 1).length() < 2){
                    
                    Mon = "0" + rs.getString("trdate").substring(0, 1).trim();
                    
                }else { 
                    
                    Mon = rs.getString("trdate").substring(0, 1).trim(); 
                    
                }
                
                Day = rs.getString("trdate").substring(1, 3).trim();
                
                field6 = Yr + "-" + Mon + "-" + Day; 
                
                field7 = rs.getString("trtime");
                
                fieldec = "MGE";
                
                fieldexch = "MGE";
                
                field8 = "M"+rs.getString("trcomm").substring(0, 1).trim();
                
                if(rs.getString("trstrk").length() > 2){
                    
                    field9 = "OOF"; 
                    
                } else{
                    
                    field9 = "FUT";
                    
                }
                
                if(rs.getString("prmon").trim().length() == 1){
                    
                    field10 = rs.getString("pryr4").trim() + "0" + rs.getString("prmon").trim();
                    
                }else{
                    
                    field10 = rs.getString("pryr4").trim() + rs.getString("prmon").trim();
                    
                }
                
                if(rs.getString("trbs").trim().equals("B")){
                    
                    field11 = rs.getString("trqty").trim();
                     
                }else{
                    
                    field11 = "-" + rs.getString("trqty").trim();
                    
                }
                
                trdprice = Float.parseFloat(rs.getString("trpric").trim());
                
                trdprice = trdprice / 100000;
                
                field12 = trdprice;
                
                field13 = rs.getString("trisrc").trim();
                
                field14 = rs.getString("trelec").trim();
                
                field15 = rs.getString("trtrid").trim();
                
                /*
                System.out.println(field3 + " " + field3a + " " + field4
                + " " + field5 + " " + field6 + " " + field7 + " " + fieldec
                + " " + fieldexch + " " + field8 + " " + field9 + " " + field10
                + " " + field11 + " " + field12 + " " + field13 + " " + field14
                + " " + field15);
                */
                
                
                importData(field3,field4,field5,field3a,field6,field7,fieldec,fieldexch,
                        field8,field9,field10,field11,field12,field13,field14,field15);
                        
            }   
            
            stmt.close();
            cStmt.close();
            rs.close();
            connect.close();
        
        }catch(SQLException sqle){
            System.out.println("SQLException : " + sqle);
        }
            runExceptions();
            moveTradestoPRDtable();
    }
    public void importData(String firm,String acctid,String userid,String seg,String trdDate,String trdTime,
            String ec,String exch,String pfcode,String pftype,String pe,String trdQty,float trdPrice,
            String trdSrc, String trdElec, String trdID) throws SQLException{
        
        Connection connection = null;
     
        connection = conn2.getCMEServerConn();
       
        String query = "INSERT INTO t_datarlt_trades_wrk (Firm, AcctId, UserID"
                    + ", seg, tradedate, tradetime, ec, exch, pfcode, pftype, pe"
                    + ", tradeqty, tradeprice, TEMS_Source, Electronic, TradeID) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            try{

                connection.setAutoCommit(false);
                PreparedStatement stmt = connection.prepareStatement(query); 
 
                   stmt.setString(1, firm);
                   stmt.setString(2, acctid);
                   stmt.setString(3, userid);
                   stmt.setString(4, seg);
                   stmt.setString(5, trdDate);
                   stmt.setString(6, trdTime);
                   stmt.setString(7, ec);
                   stmt.setString(8, exch);
                   stmt.setString(9, pfcode);
                   stmt.setString(10, pftype);
                   stmt.setString(11, pe);
                   stmt.setString(12, trdQty);
                   stmt.setFloat(13, trdPrice);
                   stmt.setString(14, trdSrc);
                   stmt.setString(15, trdElec);
                   stmt.setString(16, trdID);
                   stmt.addBatch();
                 

			
                stmt.executeBatch();
                connection.commit();
                //STEP 6: Clean-up environment
                stmt.close();
                connection.close();
                } catch(SQLException sqle) {
		   System.out.println("SQLException : " + sqle); 
                }finally{
                    connection.close();
                }
    }

    private void truncateTable() throws SQLException {
        Connection connection = null;
        PreparedStatement Stmt = null;
        
        connection = conn3.getCMEServerConn();
        try{
            String query = "TRUNCATE t_datarlt_trades_wrk";

            Stmt = connection.prepareStatement(query);
            Stmt.executeUpdate();
 
            Stmt.close();
            connection.close();
        }catch(SQLException sqle){
            System.out.println("SQLException : " + sqle);
        }finally{
            connection.close();
        }
        
    }

    private void runExceptions() throws SQLException {
        try {
            Connection dbconn;
            CallableStatement cStmt;
            dbconn = conn4.getCMEServerConn();
            
            cStmt = dbconn.prepareCall("CALL sp_FCMExceptions()");
            cStmt.executeUpdate();
            
            cStmt.close();
            dbconn.close();
        } catch (SQLException ex) {

        }
    }
    
    private void moveTradestoPRDtable() throws SQLException {
        try {
            Connection dbconn;
            CallableStatement cStmt;
            dbconn = conn5.getCMEServerConn();
            
            cStmt = dbconn.prepareCall("CALL sp_MoveTradestoPRDtable()");
            cStmt.executeUpdate();
            
            cStmt.close();
            dbconn.close();
        } catch (SQLException ex) {

        }
    }
    
}
