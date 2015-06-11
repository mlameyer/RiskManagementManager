/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mngt_reports;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author bgilbert
 */
class rlt_fcm_rpt {
    private Connection connection;
    
    void runReport(Connection c) throws SQLException {
        connection = c;
        CallableStatement cStmt = null;
            try {
                cStmt = connection.prepareCall("call sp_rlt_FCM_rpt()");
                cStmt.execute();

                //Clean-up environment
                cStmt.close();
                connection.close();
                } catch(SQLException sqle) {
		   System.out.println("SQLException : " + sqle);             
                }
        
    }
}
