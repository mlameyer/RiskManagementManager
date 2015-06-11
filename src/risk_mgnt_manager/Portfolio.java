
package risk_mgnt_manager;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


 
public class Portfolio implements Serializable {
    final private static String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
    final private static String DB_URL = "jdbc:mysql://192.168.130.212/mgex_riskmgnt";
    final private static String USER = "root";
    final private static String PASS = "mgex";
    
    private Connection connection;
    private String firm = null;
    private String acctId = null;
    private String UserId = null;
    private String seg = null;
    private String tradedate = null;
    private String tradetime = null;
    private String ec = null;
    private String exch = null;
    private String pfcode = null;
    private String pftype = null;
    private String pe = null;
    private String tradeqty = null;
    private String tradeprice = null;
    
    private String ffirm = null;
    private String facctId = null;
    private String fUserId = null;
    private String fseg = null;
    private String ftradedate = null;
    private String ftradetime = null;
    private String fec = null;
    private String fexch = null;
    private String fpfcode = null;
    private String fpftype = null;
    private String fpe = null;
    private String ftradeqty = null;
    private String ftradeprice = null;
    private List results;
    
    
    public String getFirm() {
        return firm;
    }
    public void setFirm(String firm) {
        this.firm = firm;
        if(firm == null){
            ffirm = ffirm;
        } else {
            ffirm = firm;
        }
    }
    public String getAcctId() {
        return acctId;
    }
    public void setAcctId(String acctId) {
        this.acctId = acctId;
        if(acctId == null){
            facctId = facctId;
        } else {
            facctId = acctId;
        }
    }
    public String getUserId() {
        return UserId;
    }
    public void setUserId(String UserId) {
        this.UserId = UserId;
        if(UserId == null){
            fUserId = fUserId;
        } else {
            fUserId = UserId;
        }
    }
    public String getSeg() {
        return seg;
    }
    public void setSeg(String seg) {
        this.seg = seg;
        if(seg == null){
            fseg = fseg;
        } else {
            fseg = seg;
        }
    }
    // no if else statement from here on out. this is the child node data and 
    // it is always present
    public String getTradedate() {
        return tradedate;
    }
    public void setTradedate(String tradedate) {
        this.tradedate = tradedate;
        ftradedate = tradedate;
    }
    public String getTradetime() {
        return tradetime;
    }
    public void setTradetime(String tradetime) {
        this.tradetime = tradetime;
        ftradetime = tradetime;
    }
    public String getEC() {
        return ec;
    }
    public void setEC(String ec) {
        this.ec = ec;
        fec = ec;
    }
    public String getExch() {
        return exch;
    }
    public void setExch(String exch) {
        this.exch = exch;
        fexch = exch;
    }
    public String getPFCode() {
        return pfcode;
    }
    public void setPFCode(String pfcode) {
        this.pfcode = pfcode;
        fpfcode = pfcode;
    }
    public String getPFType() {
        return pftype;
    }
    public void setPFType(String pftype) {
        this.pftype = pftype;
        fpftype = pftype;
    }
    public String getPE() {
        return pe;
    }
    public void setPE(String pe) {
        this.pe = pe;
        fpe = pe;
    }
    public String getTradeQty() {
        return tradeqty;
    }
    public void setTradeQty(String tradeqty) {
        this.tradeqty = tradeqty;
        ftradeqty = tradeqty;
    }
    public String getTradePrice() {
        return tradeprice;
    }
    public void setTradePrice(String tradeprice) {
        this.tradeprice = tradeprice;
        ftradeprice = tradeprice;
        // data from parser is sent to list
        toList(ffirm,facctId,fUserId,fseg,ftradedate,tradetime,fec,fexch,
                fpfcode,fpftype,fpe,ftradeqty,ftradeprice);

    }
    
    /*
    This will ultimately be the setup to import the list into my database
    I only printed the results to make sure I am getting the correct output
    */
    public void toList(String a,String b,String c,String d,String e,String f,
            String g,String h,String i,String j,String k,String l,String m){
        
        importData(ffirm,facctId,fUserId,fseg,ftradedate,tradetime,fec,fexch,
                fpfcode,fpftype,fpe,ftradeqty,ftradeprice);
    }
    
  //  public void importData(final List<String> results){
    public void importData(String firm,String acctid,String userid,String seg,String trdDate,String trdTime,
            String ec,String exch,String pfcode,String pftype,String pe,String trdQty,String trdPrice){

            connection = null;
            try {
                    Class.forName(JDBC_CONNECTION_URL);
                    connection = DriverManager.getConnection(DB_URL,USER,PASS);
                  
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            String query = "INSERT INTO t_datarlt_trades_wrk (Firm, AcctId, UserID"
                    + ", seg, tradedate, tradetime, ec, exch, pfcode, pftype, pe"
                    + ", tradeqty, tradeprice) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            try{
                //STEP 2: Register JDBC driver
                Class.forName("com.mysql.jdbc.Driver");
                
                //STEP 3: Open a connection
                connection.setAutoCommit(false);
                PreparedStatement stmt = connection.prepareStatement(query); 
 
                  // for(String result : results){
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
                   stmt.setString(13, trdPrice);
                   stmt.addBatch();
                 //  }

			
                stmt.executeBatch();
                connection.commit();
                //STEP 6: Clean-up environment
                stmt.close();
                connection.close();
                } catch(SQLException sqle) {
		   System.out.println("SQLException : " + sqle); 
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }          
    }
            
}
     

