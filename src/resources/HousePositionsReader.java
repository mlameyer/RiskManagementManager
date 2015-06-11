/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mlameyer <mlameyer@mgex.com>
 */
public class HousePositionsReader {

    private final ServerConn connection = new ServerConn();
    private final Connection conn = connection.getCMEServerConn();
    private final Connection conn2 = connection.getCMEServerConn();
    //private final Connection conn3 = connection.getCMEServerConn();
    private final String truncquery = "TRUNCATE t_data_House_Positions_wrk";
    private final String query = "INSERT INTO t_data_House_Positions_wrk (FieldtoParse)"
                        + " VALUES (?)";
    private final String query2 = "INSERT INTO t_data_House_Positions_prd "
            + "(fileformat, created, Date, isSettle, Firm, AcctId, "
            + "AcctType, isCust, isNew, custPortUseLov, currency, ledgerbalance, "
            + "ote, securities, lue, ec, cc, Exch, pfCode, pfType, pe, UndPe, o, "
            + "k, `Long`, Short, Net)"
            + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String query3 = "SELECT FieldtoParse FROM mgex_riskmgnt.t_data_House_Positions_wrk ORDER BY id ASC";
    private final String query4 = "UPDATE mgex_riskmgnt.t_data_house_positions_prd AS a" +
         " INNER JOIN mgex_riskmgnt.t_cm_datacrossreference AS b ON a.Firm = b.Portfolio" +
         " SET a.Firm = b.Clearing_Member";
    private PreparedStatement stmt = null;
    private Statement truncateStmt = null;
    private PreparedStatement stmt2 = null;
    private Statement stmt3 = null;
    //private Statement stmt4 = null;

    public HousePositionsReader() {
        try {
            this.stmt = conn.prepareStatement(query);
            this.truncateStmt = conn.createStatement();
            this.stmt2 = conn2.prepareStatement(query2);
        } catch (SQLException ex) {
            Logger.getLogger(HousePositionsReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void execute(String fcPath) {
        BufferedReader br = null;
        try {
            System.out.println(fcPath);
            truncateStmt.executeQuery(truncquery);
            br = new BufferedReader(new FileReader(fcPath));
            String string;
            while((string = br.readLine()) != null)
            {  
                try{
                    conn.setAutoCommit(false);
                    stmt.setString(1, string);
                    stmt.addBatch();
                } catch(SQLException sqle) {
                    System.out.println("SQLException : " + sqle); 
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HousePositionsReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(HousePositionsReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(HousePositionsReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void closeConnection() {
        try {
            stmt.executeBatch();
            conn.commit();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(HousePositionsReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void parseRecords() {
        int lineMarker1 = 1, lineMarker2 = 2, lineMarker3 = 3, fileformat = 4, isSettle = 1;
        int isCust = 1, isNew = 1, custPortUseLov = 1, ledgerbalance = 0, ote = 0;
        int securities = 0, lue = 0;
        String created = null, Date = null, Firm = null, AcctId = null, AcctType = "S";
        String currency = "USD";

        try {
            
            stmt3 = conn2.createStatement();
            try (ResultSet rs = stmt3.executeQuery(query3)) {
                while(rs.next()){
                    
                    if(Character.valueOf(rs.getString("FieldtoParse").substring(0, 1).charAt(0)).equals(''))
                    {
                        System.out.println("EndOfFile"); 
                    }
                    else
                    {
                        if(lineMarker1 == Integer.parseInt(rs.getString("FieldtoParse").substring(0, 1)))
                        {
                            created = rs.getString("FieldtoParse").substring(3, 11);
                            Date = rs.getString("FieldtoParse").substring(3, 11);
                        }
                        if(lineMarker2 == Integer.parseInt(rs.getString("FieldtoParse").substring(0, 1)))
                        {
                            Firm = rs.getString("FieldtoParse").substring(1, 4);
                            AcctId = rs.getString("FieldtoParse").substring(4, 24);
                        }
                        if(lineMarker3 == Integer.parseInt(rs.getString("FieldtoParse").substring(0, 1)))
                        {
                            try{
                                conn2.setAutoCommit(false);
                                stmt2.setInt(1, fileformat);
                                stmt2.setString(2, created);
                                stmt2.setString(3, Date);
                                stmt2.setInt(4, isSettle);
                                stmt2.setString(5, Firm);
                                stmt2.setString(6, AcctId);
                                stmt2.setString(7, AcctType);
                                stmt2.setInt(8, isCust);
                                stmt2.setInt(9, isNew);
                                stmt2.setInt(10, custPortUseLov);
                                stmt2.setString(11, currency);
                                stmt2.setInt(12, ledgerbalance);
                                stmt2.setInt(13, ote);
                                stmt2.setInt(14, securities);
                                stmt2.setInt(15, lue);
                                stmt2.setString(16, rs.getString("FieldtoParse").substring(24, 27));//EC
                                stmt2.setString(17, rs.getString("FieldtoParse").substring(29, 35));//CC
                                stmt2.setString(18, rs.getString("FieldtoParse").substring(24, 27));//Exch
                                stmt2.setString(19, rs.getString("FieldtoParse").substring(35, 45));//pfCode
                                stmt2.setString(20, rs.getString("FieldtoParse").substring(45, 48));//pfType
                                stmt2.setString(21, rs.getString("FieldtoParse").substring(49, 57));//pe
                                stmt2.setString(22, rs.getString("FieldtoParse").substring(58, 66));//Undpe
                                stmt2.setString(23, rs.getString("FieldtoParse").substring(48, 49));//o
                                stmt2.setString(24, rs.getString("FieldtoParse").substring(67, 74));//k
                                stmt2.setString(25, rs.getString("FieldtoParse").substring(83, 90));//long
                                stmt2.setString(26, rs.getString("FieldtoParse").substring(91, 98));//Short
                                stmt2.setString(27, rs.getString("FieldtoParse").substring(74, 82));//Net
                                
                                stmt2.addBatch();
                            } catch(SQLException sqle) {
                                System.out.println("SQLException : " + sqle);
                            }
                            System.out.print("Batch: " + stmt2);
                        }
                    }
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(HousePositionsReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt2.executeBatch();
                conn2.commit();
                stmt2.close();
                stmt3.close(); 
                conn2.close();
            } catch (SQLException ex) {
                Logger.getLogger(HousePositionsReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
/*
    public void updateRecords() {
        try {
            stmt4 = conn3.createStatement();
            stmt4.executeUpdate(query4);
            
        } catch (SQLException ex) {
            Logger.getLogger(HousePositionsReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt4.close();
                conn3.close();
            } catch (SQLException ex) {
                Logger.getLogger(HousePositionsReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
*/   
}
