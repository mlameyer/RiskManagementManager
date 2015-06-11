/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mlameyer <mlameyer@mgex.com>
 */
public class CSVWriter {
    private String table = null;
    private Connection connect = null;
    private String file = null;
    private Statement statment = null;
    private ResultSet resultSet = null;
    
    public CSVWriter(String table, Connection connect, String file){
        this.table = table;
        this.connect = connect;
        this.file = file;
    }
    
    public void readDataTable() {
        try {
            statment = connect.createStatement();
            // resultSet gets the result of the SQL query
            resultSet = statment
                    .executeQuery("select * from " + table); 
            writetofile(resultSet, file);
        } catch (SQLException ex) {
            Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void writetofile(ResultSet resultSet, String file) {
        OutputStream output = null;
        File filepath = new File(file);
        byte[] data;
        String seperator = ",";
        String fileout = null;
        String field1,field2,field3,field4,field5,field6,field7,field8,field9,
                field10,field11,field12,field13,field14,field15,field16,field17,
                field18;
        
        try {
            
            if (!filepath.exists()) 
            {    
		filepath.createNewFile();	
            }
            
            output = new BufferedOutputStream(new FileOutputStream(filepath,true));
            
            
            
            while(resultSet.next()) {
                field1 = resultSet.getString(1) + seperator;
                field2 = resultSet.getString(2) + seperator;
                field3 = resultSet.getString(3) + seperator;
                field4 = resultSet.getString(4) + seperator;
                field5 = resultSet.getString(5) + seperator;
                field6 = resultSet.getString(6) + seperator;
                field7 = resultSet.getString(7) + seperator;
                field8 = resultSet.getString(8) + seperator;
                field9 = resultSet.getString(9) + seperator;
                field10 = resultSet.getString(10) + seperator;
                field11 = resultSet.getString(11) + seperator;
                field12 = resultSet.getString(12) + seperator;
                field13 = resultSet.getString(13) + seperator;
                field14 = resultSet.getString(14) + seperator;
                field15 = resultSet.getString(15) + seperator;
                field16 = resultSet.getString(16) + seperator;
                field17 = resultSet.getString(17) + seperator;
                field18 = resultSet.getString(18) + "\n";
                
                fileout = field1 + field2 + field3 + field4 + field5 + field6 +
                        field7 + field8 + field9 + field10 + field11 + field12 +
                        field13 + field14 + field15 + field16 + field17 + field18;
                
                data = fileout.getBytes();
                output.write(data);
            }         
            
            output.close();
            statment.close();
            connect.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
