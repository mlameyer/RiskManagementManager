/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author bgilbert
 */
public class ServerConn {


    public Connection getCMEServerConn() {
        Connection connection = null;
        String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://192.168.130.212/mgex_riskmgnt";
        String USER = "root";
        String PASS = "mgex";
        
	try {
		Class.forName(JDBC_CONNECTION_URL);
		connection = DriverManager.getConnection(DB_URL,USER,PASS);
                  

	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}

	return connection;
    }
    
    public Connection getMarginsDBConn() {
        Connection connection = null;
        String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://192.168.130.212/mgex_margins";
        String USER = "root";
        String PASS = "mgex";
        
	try {
		Class.forName(JDBC_CONNECTION_URL);
		connection = DriverManager.getConnection(DB_URL,USER,PASS);
                  

	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}

	return connection;
    }

    public Connection getOPSServerConn() {
        Connection connection = null;
        String JDBC_CONNECTION_URL = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://192.168.130.227/mgex_ops";
        String USER = "root";
        String PASS = "pass";
        
	try {
		Class.forName(JDBC_CONNECTION_URL);
		connection = DriverManager.getConnection(DB_URL,USER,PASS);
                  

	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}

	return connection;
    }
    
    public Connection getAS400Conn(){
        Connection connection = null;
        String JDBC_CONNECTION_URL = "com.ibm.as400.access.AS400JDBCDriver";
        String DB_URL = "jdbc:as400://192.168.130.238";
        String USER = "secofr";
        String PASS = "secofr";
        
        try {
		Class.forName(JDBC_CONNECTION_URL);
		connection = DriverManager.getConnection(DB_URL,USER,PASS);
                  

	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}

        return connection;
    }   
    
}
