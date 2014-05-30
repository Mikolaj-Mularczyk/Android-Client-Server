package testServer;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;  
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;  
import java.io.InputStream;
import java.io.InputStreamReader;  
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;  
import java.net.InetAddress;
import java.net.ServerSocket;  
import java.net.Socket;  

import javax.imageio.ImageIO;
import javax.swing.JFrame;
  
public class testServer  
{  
    private static final int PORT_VISION = 50004;  
    private static final int PORT_CONTROL = 50003; 
    static boolean flaga = true;  
      
    private static ServerSocket serverSocketVision;  
    private static ServerSocket serverSocketControl;  
    private static Socket clientSocketVision;
    private static Socket clientSocketControl;
    
    static simpleInterface siIn;
      
    public static void main(String[] args) throws IOException  
    {   
    	JFrame frame = new JFrame( "Test Server" );
    	siIn = new simpleInterface();
    	frame.add( siIn );
    	frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    	frame.setSize( 500, 500 );
    	frame.setVisible( true );
    	
        serverSocketVision = null;  
        
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress();
        System.out.println("Ip: " + ip);
        
        //trying to create serverSockets

        //VISION
        try  
        {  
            serverSocketVision = new ServerSocket(PORT_VISION);  
            
        }  
        catch(IOException e)  
        {  
            System.err.println("Could not listen on port: "+PORT_VISION);  
            System.exit(1);  
        } 
        //CONTROL
        try  
        {  
        	serverSocketControl = new ServerSocket(PORT_CONTROL);
            
        }  
        catch(IOException e)  
        {  
            System.err.println("Could not listen on port: "+PORT_CONTROL);  
            System.exit(1);  
        } 
          
        System.out.print("Wating for connection...");  
        
        //Waiting for client response
        Thread t = new Thread(new Runnable()  
        {  
            public void run()  
            {  
                try  
                {  
                    while(flaga)  
                    {  
                        System.out.print(".");  
                        Thread.sleep(1000);  
                    }  
                }  
                catch(InterruptedException ie)  
                {  
                }  
                  
                System.out.println("\nClient connected on ports: "+PORT_VISION+" - Vision, " +PORT_CONTROL + " - Control");  
            }  
        });  
        t.start();  
          
        clientSocketVision = null;  
        clientSocketControl = null;
        
        //Sockets waiting for clients
        try  
        {  
            clientSocketVision = serverSocketVision.accept();  
        }  
        catch(IOException e)  
        {  
            System.err.println("Accept failed.");  
            t.interrupt();  
            System.exit(1);  
        }
        
        try  
        {  
            clientSocketControl = serverSocketControl.accept();  
            flaga = false;  
        }  
        catch(IOException e)  
        {  
            System.err.println("Accept failed.");  
            t.interrupt();  
            System.exit(1);  
        }
        //Sockets connected
        //setting up DataStreams        
        final DataInputStream dis = new DataInputStream(clientSocketVision .getInputStream());
        final BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(clientSocketControl.getInputStream()));
        t = new Thread(new Runnable()  
        {  
            public void run()  
            {  
                try  
                {  
                    while(true)  
                    {  
                        String input = in.readLine();
                        System.out.println(input); 
                        
                       if(input=="Up")  
                        {  
                            System.out.println(" Up");  
                        }
//                        else if(input=='L')  
//                        {  
//                            System.out.println(" Left");  
//                        }
//                        else if(input=='R')  
//                        {  
//                            System.out.println(" Right");  
//                        }
//                        else if(input=='D')  
//                        {  
//                            System.out.println(" Down");  
//                        }
                        else if(input=="Close")
                        {  
                        	input = in.readLine();
                            System.out.println(" Shutting Down");  
                              
                            in.close();
                            dis.close();
                            clientSocketControl.close();  
                            clientSocketVision.close();
                            break;  
                        	}
                        }
                    }   
                catch(Exception e)  
                {  
                    System.err.println(System.currentTimeMillis()+" Unexpected Error");  
                }   
            }
        }
        );
        t.start();
        VisionThread vt = (VisionThread) new VisionThread(dis,siIn);
        vt.start();  
    }  
}  