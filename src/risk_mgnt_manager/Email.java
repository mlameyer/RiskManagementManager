/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk_mgnt_manager;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 *
 * @author bgilbert
 */
public class Email{
    private String log;
    private int type;
    private String errormessage;
    private String subject;
    public Email(String log, int type){
        this.log = log;
        this.type = type;
        
        
        selectMessage();
    }
    
    private void selectMessage(){
        switch (type){
            case 4: errormessage = "Importing " + log + " Failed " + new Date()
                    + " =====> Contact the Database Administrator or System Admin. "
                    + "Check for network errors and that the file being imported "
                    + "is in its correct location. Please refer to the document "
                    + "Automation Control Program technical details.docx located in "
                    + "K:\\IT\\Risk_mgnt_DB_documentation";
            
                    subject = "Importing " + log + " Failed " + new Date();
                break;
            case 5: errormessage = "Call to Stored Procedure " + log + " Failed " + new Date()
                    + " =====> Contact the Database Administrator or System Admin. "
                    + "Check for network errors and that the procedure is accessing "
                    + "the correct tables and databases. Please refer to the document "
                    + "Automation Control Program technical details.docx located in "
                    + "K:\\IT\\Risk_mgnt_DB_documentation";
            
                    subject = "Call to Stored Procedure " + log + " Failed " + new Date();
                break;
            default: errormessage = "No message to output, Funky Cold medina";
                break;
         }
        sendMail();
    }
    private void sendMail(){
        // Recipient's email ID needs to be mentioned.
      String to = "mlameyer@mgex.com";

      // Sender's email ID needs to be mentioned
      String from = "RiskmgntDBAutomation@mgex.com";

      // Assuming you are sending email from localhost
      String host = "mail.mgex.com";//mail.mgex.com, 76.12.106.204
      String port = "25";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.put("mail.smtp.port", port);
      properties.setProperty("mail.smtp.host", host);
      properties.put("mail.smtp.auth", false);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress(to));
         
         message.setSentDate(new Date());
         // Set Subject: header field
         message.setSubject(subject);

         // Now set the actual message
         message.setText(errormessage);

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
    }
   
}
