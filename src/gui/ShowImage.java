package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class ShowImage extends JComponent
{
	private JFrame theApp;
	private Image img;
	
	public ShowImage(JFrame theApp)
	{
		this.theApp = theApp;
		
		img = Toolkit.getDefaultToolkit().getImage("inpainted.jpg");
	}

	
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawString("lalalala", 50, 50);
	}

}
