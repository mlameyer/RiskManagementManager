/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

/**
 *
 * @author mlameyer <mlameyer@mgex.com>
 */
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendFileEmail
{
    private String to = null;
    private String to2 = null;
    private String from = null;
    private String host = null;
    private String subject = null;
    private String messagebody = null;
    private String FileName = null;
    private String FName = null;
    
   public SendFileEmail(String to, String to2, String from, String host, String subject, String messagebody, String FileName, String FName){
      this.to = to;
      this.to2 = to2;
      this.from = from;
      this.host = host;
      this.subject = subject;
      this.messagebody = messagebody;
      this.FileName = FileName;
      this.FName = FName;
   }
   
   public void send()
   {
      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.put("mail.smtp.port", "25");
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
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress("dkrump@mgex.com"));
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress("jgreen@mgex.com"));
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress("bhamer@mgex.com"));
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress("bcasey@mgex.com"));
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress("tcratty@mgex.com"));
         message.addRecipient(Message.RecipientType.TO,
                                  new InternetAddress("lcarlson@mgex.com"));

         // Set Subject: header field
         message.setSubject(subject);

         // Create the message part 
         BodyPart messageBodyPart = new MimeBodyPart();

         // Fill the message
         messageBodyPart.setText(messagebody);
         
         // Create a multipar message
         Multipart multipart = new MimeMultipart();

         // Set text message part
         multipart.addBodyPart(messageBodyPart);

         // Part two is attachment
         messageBodyPart = new MimeBodyPart();
         DataSource source = new FileDataSource(FileName);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(FName);
         multipart.addBodyPart(messageBodyPart);

         // Send the complete message parts
         message.setContent(multipart );

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }
   }
}
