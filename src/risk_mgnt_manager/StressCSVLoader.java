/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import au.com.bytecode.opencsv.CSVReader;
/*
 * @author mLaMeyer
 */
public class StressCSVLoader {
    private static final 
		String SQL_INSERT = "INSERT INTO ${table}(${keys}) VALUES(${values})";
	private static final String TABLE_REGEX = "\\$\\{table\\}";
	private static final String KEYS_REGEX = "\\$\\{keys\\}";
	private static final String VALUES_REGEX = "\\$\\{values\\}";

	private Connection connection;
	private char seprator;

	/**
	 * Public constructor to build CSVLoader object with
	 * Connection details. The connection is closed on success
	 * or failure.
	 * @param connection
	 */
	public StressCSVLoader(Connection connection) {
		this.connection = connection;
		//Set default separator
		this.seprator = ',';
	}
	
	/**
	 * Parse CSV file using OpenCSV library and load in 
	 * given database table. 
	 * @param csvFile Input CSV file
	 * @param tableName Database table name to import data
	 * @throws Exception
	 */
	public void loadCSV(String csvFile, String tableName, String filename) throws Exception {

		CSVReader csvReader = null;
		if(null == this.connection) {
			throw new Exception("Not a valid connection.");
		}
		try {
			csvReader = new CSVReader(new FileReader(csvFile), this.seprator);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error occured while executing file. "
					+ e.getMessage());
		}

		String[] headerRow = csvReader.readNext();

		if (null == headerRow) {
			throw new FileNotFoundException(
					"No columns defined in given CSV file." +
					"Please check the CSV file format.");
		}

		String questionmarks = StringUtils.repeat("?,", headerRow.length);
		questionmarks = (String) questionmarks.subSequence(0, questionmarks
				.length() - 1);
                String col_Names = "Point_in_Time, Portfolio, Fut_Variation, Opt_Variation, Total_Variation, Percent_of_Ledger_Balance, Ledger_Balance";

		String query = SQL_INSERT.replaceFirst(TABLE_REGEX, tableName);
		//query = query.replaceFirst(KEYS_REGEX, StringUtils.join(headerRow, ","));
                query = query.replaceFirst(KEYS_REGEX, col_Names);
		query = query.replaceFirst(VALUES_REGEX, questionmarks);
                
                String update = "UPDATE mgex_riskmgnt." + tableName + 
                        " SET Imported_File_Name = '" + filename + "' Where " +
                        "Imported_File_Name IS NULL";

		System.out.println("Query: " + query);
                System.out.println("Update: " + update);

		String[] nextLine;
		Connection con = null;
                Connection con2 = null;
		PreparedStatement ps = null;
                PreparedStatement ps2 = null;
		try {
			con = this.connection;
			con.setAutoCommit(false);
			ps = con.prepareStatement(query);
                        
                        con2 = this.connection;
			con2.setAutoCommit(false);
                        ps2 = con2.prepareStatement(update);

			final int batchSize = 1000;
			int count = 0;
			Date date = null;
			while ((nextLine = csvReader.readNext()) != null) {

				if (null != nextLine) {
					int index = 1;
					for (String string : nextLine) {
						//date = DateUtill.convertToDate(string);
						if (null != date) {
							ps.setDate(index++, new java.sql.Date(date
									.getTime()));
						} else {
							ps.setString(index++, string);
						}
					}
					ps.addBatch();
				}
				if (++count % batchSize == 0) {
					ps.executeBatch();
				}
			}
			ps.executeBatch(); // insert remaining records
                        ps2.executeUpdate();
			con.commit();
                        con2.commit();
		} catch (Exception e) {
			con.rollback();
                        con2.rollback();
			e.printStackTrace();
			throw new Exception(
					"Error occured while loading data from file to database."
							+ e.getMessage());
		} finally {
			if (null != ps)
				ps.close();
                                ps2.close();
			if (null != con)
				con.close();
                                con2.close();

			csvReader.close();
		}
	}

	public char getSeprator() {
		return seprator;
	}

	public void setSeprator(char seprator) {
		this.seprator = seprator;
	}
}
