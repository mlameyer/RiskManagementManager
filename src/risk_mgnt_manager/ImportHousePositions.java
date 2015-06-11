package risk_mgnt_manager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import resources.HousePositionsReader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mlameyer <mlameyer@mgex.com>
 */
public class ImportHousePositions {
    private final JTextArea j;
    private final String path = "K:\\Market Admin\\Gross Margins\\SPAN_Positions_Files\\House";
    private final String pathAll = "K:\\Market Admin\\Gross Margins\\SPAN_Positions_Files\\House\\AllHouseImport";
    private File file = null;
    final JFileChooser fc = new JFileChooser();

    public ImportHousePositions(JTextArea j) {
        this.j = j;
    }
    
    public void importData(){
        
        HousePositionsReader hpxr = new HousePositionsReader();
        
        int returnVal = JOptionPane.showConfirmDialog(null,"Import all?", "Import many files", JOptionPane.YES_NO_OPTION);
        
        if(returnVal == 0)
        {
            int count = new File(pathAll).listFiles().length;
            if(count == 0)
            {
               JOptionPane.showMessageDialog (null, "Did you forget to add files to " + pathAll + "?", "Missing Files", JOptionPane.INFORMATION_MESSAGE); 
            }
            else
            {
                file = new File(pathAll);
                List<String> list = Arrays.asList(file.list(
                new FilenameFilter() {
                    @Override public boolean accept(File dir, String name) {
                        return name.endsWith(".txt");
                    }
                }
                ));

                for(int i = 0; i < list.size(); i++){
                    try
                    {
                        String fileName = list.get(i).toString();
                        hpxr.execute(pathAll + "\\" + fileName);
                    }
                    catch(Exception e)
                    {
                    }
                }

                hpxr.closeConnection(); 
                hpxr.parseRecords();
                //hpxr.updateRecords();
            }
           
        } 
        else 
        {
            fc.setCurrentDirectory(new File(path));
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                String fcPath = fc.getSelectedFile().getPath();

                try {
                    
                    hpxr.execute(fcPath);

                } catch (Exception e) {
                }
                finally
                {
                    hpxr.closeConnection();
                }
                
                hpxr.parseRecords();
                //hpxr.updateRecords();
            
            }
            else {
                    System.out.println("No file selected");
            } 
        }
    }
}
