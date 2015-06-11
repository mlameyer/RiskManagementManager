/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package resources;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author bgilbert
 */
class SFTPServer {
    Session     session     = null;
    Channel     channel     = null;
    ChannelSftp channelSftp = null;

    public void grabFile(String SFTPHOST, int SFTPPORT, String SFTPUSER, 
            String SFTPPASS, String SFTPWORKINGDIR, String filename, 
            String filepath) 
            throws JSchException, SftpException, FileNotFoundException, 
            IOException {

        
        JSch jsch = new JSch();
        session = jsch.getSession(SFTPUSER,SFTPHOST,SFTPPORT);
        session.setPassword(SFTPPASS);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        channel = session.openChannel("sftp");
        channel.connect();
        channelSftp = (ChannelSftp)channel;
        channelSftp.cd(SFTPWORKINGDIR);
        byte[] buffer = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(channelSftp.get(filename));
        File newFile = new File(filepath);
        OutputStream os = new FileOutputStream(newFile);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        int readCount;
        //System.out.println("Getting: " + theLine);

        while((readCount = bis.read(buffer)) > 0) {
            bos.write(buffer, 0, readCount);
        }
        
        bis.close();

        bos.close();
    }
    
}
