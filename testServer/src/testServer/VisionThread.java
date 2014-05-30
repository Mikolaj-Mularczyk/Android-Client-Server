package testServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

public class VisionThread extends Thread{
	
	DataInputStream dis;
	simpleInterface siIn;
	
	public VisionThread(DataInputStream dis, simpleInterface siIn){
		this.dis = dis;
		this.siIn = siIn;
	}

    public void run()  
    {  
        try  
        {  
            while(true)  
            {  
                char input = dis.readChar();
                System.out.println(input);
                if(input =='I'){
                	input = dis.readChar();
                	if(input == 'm'){
                	long length = dis.readLong();
                	File to = new File("filename.jpg");
                	DataOutputStream dos = new DataOutputStream(
                            new FileOutputStream(to));
                	byte[] buffer = new byte[2048];
                    int len, current = 0;
                    System.out.println(length);
                    while ( current < length) {
                    	len = dis.read(buffer);
                        dos.write(buffer, 0, len);
                        current += len;
                        System.out.println(current);
                    }
                    dos.close();
                    siIn.addImage(ImageIO.read(to));
                	}
                }                
                //Thread.sleep(50);  
            }  
        }  
        catch(Exception e)  
        {  
            System.err.println(System.currentTimeMillis()+" Unexpected Error");  
        }  
    }  

}
