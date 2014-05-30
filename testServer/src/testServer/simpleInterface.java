package testServer;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;


class simpleInterface extends JComponent
	implements MouseMotionListener
{
BufferedImage bufIm;
public simpleInterface(  ) {
	//this.bufIm = bufIm;
	//repaint();
}
public void addImage(BufferedImage bufIm){
	this.bufIm = bufIm;
	repaint();
}

public void paintComponent( Graphics g ) {
g.drawImage(bufIm,0,0,null);
}
public void mouseDragged(MouseEvent e) {
}
public void mouseMoved(MouseEvent e) { }


}