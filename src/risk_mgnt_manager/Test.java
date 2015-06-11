/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import com.ancientprogramming.fixedformat4j.format.FixedFormatManager;
import com.ancientprogramming.fixedformat4j.format.impl.FixedFormatManagerImpl;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JTextArea;
import resources.GlobexUsersFlatFile;

/**
 *
 * @author bgilbert
 */

public class Test {
    private JTextArea j;
    BufferedReader br = null;
    private static FixedFormatManager manager = new FixedFormatManagerImpl();
    
    public Test(JTextArea j) {
       this.j = j;
    }

    public void mainTest(boolean a) throws FileNotFoundException, IOException, SQLException, JSchException, SftpException{
        GlobexUsersFlatFile imp = new GlobexUsersFlatFile();
        
        //imp.RetrieveFile();
        
        br = new BufferedReader(new FileReader("K:\\Market Admin\\Risk_Mgnt\\DATA\\" + imp.RetrieveFile()));
        
        String string, cme, globex, brokerid, mge, firmid, mge2, clearingmember, mge3, clearingmember2, cust, n;

        try{
            
        while((string = br.readLine()) != null) {

            cme = string.substring(0, 5);
            globex = string.substring(5, 9);
            brokerid = string.substring(9, 14);
            mge = string.substring(14, 19);
            firmid = string.substring(19, 23);
            mge2 = string.substring(23, 27);
            clearingmember = string.substring(30, 45);
            mge3 = string.substring(45, 50);
            clearingmember2 = string.substring(50, 55);
            cust = string.substring(55, 60);
            n = string.substring(60, 61);

            imp.ImportData(cme, globex, brokerid, mge, firmid, mge2, clearingmember, mge3, clearingmember2, cust, n);
            
        }
        }catch (IOException e) {
			//e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }
    
}
