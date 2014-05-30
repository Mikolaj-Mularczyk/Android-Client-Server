package com.example.klient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;

public class Client {
    	private static final int PORT_VISION = 50004;  
    	private static final int PORT_CONTROL = 50003; 
	    private static final String HOST = "192.168.137.1";  
	    public static String status;
	    static Socket socketVision = null;
	    static Socket socketControl = null;
	    static boolean running = true;
	    PrintWriter pw;
	    DataOutputStream out;
	    

	    
	    public void runClient() throws IOException  
	    {  
	    	Thread thread = new Thread(new Runnable(){
	    	    @Override
	    	    public void run() {
	    	        	try  
	    		        {  
	    		            socketVision = new Socket(HOST, PORT_VISION);
	    		            running = true;

	    		        }  
	    		        catch(UnknownHostException e)  
	    		        {  
	    		        	status = "UnknownHostException";

	    		        }
	    		        catch(IOException e)  
	    		        {  
	    		        	status = "Could not connect to " + HOST + ":" + PORT_VISION;
	    		        }
	    	        	try  
	    		        {  
	    		            socketControl = new Socket(HOST, PORT_CONTROL);
	    		            running = true;

	    		        }  
	    		        catch(UnknownHostException e)  
	    		        {  
	    		        	status = "UnknownHostException";

	    		        }
	    		        catch(IOException e)  
	    		        {  
	    		        	status = "Could not connect to " + HOST + ":" + PORT_CONTROL;
	    		        }
	    	    }

	    	});
	    	thread.start();
	    }
	    public void send(String string){
	    	try {
				pw = new PrintWriter(new OutputStreamWriter(socketControl.getOutputStream()));
				pw.println(string);
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    public void sendImage(File file){
	    	 try {
	    	        out = new DataOutputStream(
	    	                socketVision.getOutputStream());
	    	        out.writeChar('I');
	    	        out.writeChar('m');
	    	        DataInputStream dis = new DataInputStream(new FileInputStream(file));
	    	        ByteArrayOutputStream ao = new ByteArrayOutputStream();
	    	        int read = 0;
	    	        byte[] buf = new byte[1024];
	    	        while ((read = dis.read(buf)) > -1) {
	    	            ao.write(buf, 0, read);
	    	        }
	    	        
	    	        out.writeLong(ao.size());
	    	        out.write(ao.toByteArray());
	    	        out.flush();
	    	        dis.close();
	    	    } catch (IOException e) {
	    	        e.printStackTrace();
	    	    }
	    }
	    public void close(){
	    	
            try {
				pw.println("Close");
				pw.close();
				out.close();
				socketVision.close();
				socketControl.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
            running = false;
	    }
	    
	    
	    
}
