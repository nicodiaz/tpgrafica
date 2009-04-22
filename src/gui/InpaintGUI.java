package gui;

import image.ImageManipulator;
import image.Mask;
import image.YanInpainter;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.dyno.visual.swing.layouts.GroupLayout;

//VS4E -- DO NOT REMOVE THIS LINE!
public final class InpaintGUI extends JFrame
{

	/*
	 * Window and Layout Variables
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem itemOpenImg;
	private JMenu jMenuFile;
	private JMenuBar jMenuBar0;
	private JMenuItem itemOpenMask;
	private JMenuItem itemInpaint;
	private JRadioButtonMenuItem jRadioYan;
	private JMenu jMenuInpainter;
	private JRadioButtonMenuItem jRadioOther;
	private ButtonGroup buttonGroup1;
	private JSeparator jSeparator0;
	/*
	 * Application variables
	 */
	private JFileChooser jFileChooser;
	private ImageManipulator theImage;
	private Mask theMask;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";

	JFileChooser getFileChooser()
	{
		if (jFileChooser == null)
		{
			jFileChooser = new JFileChooser(".");
		}

		return jFileChooser;
	}

	public InpaintGUI()
	{
		initComponents();
	}

	private void initComponents()
	{
		setLayout(new GroupLayout());
		setJMenuBar(getJMenuBar0());
		initButtonGroup1();
		setSize(710, 340);
	}

	private JMenuItem getItemInpaint()
	{
		if (itemInpaint == null)
		{
			itemInpaint = new JMenuItem();
			itemInpaint.setText("Inpaint");
			itemInpaint.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event)
				{
					itemInpaintActionActionPerformed(event);
				}
			});
		}
		return itemInpaint;
	}

	private JSeparator getJSeparator0()
	{
		if (jSeparator0 == null)
		{
			jSeparator0 = new JSeparator();
		}
		return jSeparator0;
	}

	private void initButtonGroup1()
	{
		buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(getJRadioYan());
		buttonGroup1.add(getJRadioOther());
	}

	private JRadioButtonMenuItem getJRadioOther()
	{
		if (jRadioOther == null)
		{
			jRadioOther = new JRadioButtonMenuItem();
			jRadioOther.setText("Other");
		}
		return jRadioOther;
	}

	private JMenu getJMenuInpainter()
	{
		if (jMenuInpainter == null)
		{
			jMenuInpainter = new JMenu();
			jMenuInpainter.setText("Inpainter");
			jMenuInpainter.add(getJRadioYan());
			jMenuInpainter.add(getJRadioOther());
		}
		return jMenuInpainter;
	}

	private JRadioButtonMenuItem getJRadioYan()
	{
		if (jRadioYan == null)
		{
			jRadioYan = new JRadioButtonMenuItem();
			jRadioYan.setSelected(true);
			jRadioYan.setText("Yan");
		}
		return jRadioYan;
	}

	private JMenuItem getItemOpenMask()
	{
		if (itemOpenMask == null)
		{
			itemOpenMask = new JMenuItem();
			itemOpenMask.setText("Open Mask...");
			itemOpenMask.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event)
				{
					itemOpenMaskActionActionPerformed(event);
				}
			});
		}
		return itemOpenMask;
	}

	private JMenuBar getJMenuBar0()
	{
		if (jMenuBar0 == null)
		{
			jMenuBar0 = new JMenuBar();
			jMenuBar0.add(getJMenuFile());
			jMenuBar0.add(getJMenuInpainter());
		}
		return jMenuBar0;
	}

	private JMenu getJMenuFile()
	{
		if (jMenuFile == null)
		{
			jMenuFile = new JMenu();
			jMenuFile.setText("File");
			jMenuFile.setOpaque(false);
			jMenuFile.add(getItemOpenImg());
			jMenuFile.add(getItemOpenMask());
			jMenuFile.add(getJSeparator0());
			jMenuFile.add(getItemInpaint());
		}
		return jMenuFile;
	}

	private JMenuItem getItemOpenImg()
	{
		if (itemOpenImg == null)
		{
			itemOpenImg = new JMenuItem();
			itemOpenImg.setText("Open Image...");
			itemOpenImg.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent event)
				{
					itemOpenImgActionActionPerformed(event);
				}
			});
		}
		return itemOpenImg;
	}

	private static void installLnF()
	{
		try
		{
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e)
		{
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL + " on this platform:"
				+ e.getMessage());
		}
	}

	/**
	 * Main entry of the class. Note: This class is only created so that you can
	 * easily preview the result at runtime. It is not expected to be managed by
	 * the designer. You can modify it as you like.
	 */
	public static void main(String[] args)
	{
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run()
			{
				InpaintGUI frame = new InpaintGUI();
				frame.setDefaultCloseOperation(InpaintGUI.EXIT_ON_CLOSE);
				frame.setTitle("InpaintGUI");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

			}
		});
	}

	private void itemOpenImgActionActionPerformed(ActionEvent event)
	{

		JFileChooser fc = getFileChooser();
		File fileTmp = null;

		int returnval = fc.showOpenDialog(null);
		if (returnval == JFileChooser.APPROVE_OPTION)
		{
			fileTmp = fc.getSelectedFile();
		}

		try
		{
			theImage = new ImageManipulator(fileTmp);
			JOptionPane.showMessageDialog(this, "Image information loaded successfully",
				"Load Image", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, "The image from the file cannot be generated",
				"Load Image", JOptionPane.ERROR_MESSAGE);
		}

		return;
	}

	private void itemOpenMaskActionActionPerformed(ActionEvent event)
	{
		JFileChooser fc = getFileChooser();
		File fileTmp = null;

		int[] whiteArray = { 255, 255, 255 };
		int[] blackArray = { 0, 0, 0 };

		int returnval = fc.showOpenDialog(null);
		if (returnval == JFileChooser.APPROVE_OPTION)
		{
			fileTmp = fc.getSelectedFile();
		}

		try
		{
			theMask = new Mask(fileTmp.getCanonicalPath(), whiteArray, blackArray);
			JOptionPane.showMessageDialog(this, "Mask information loaded successfully",
				"Load Mask", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, "The mask from the file cannot be generated",
				"Load Mask", JOptionPane.ERROR_MESSAGE);
		}

		return;
	}

	private void itemInpaintActionActionPerformed(ActionEvent event)
	{

		// Precondition, we must both the image and the mask
		if (theImage == null)
		{
			JOptionPane.showMessageDialog(this, "The image was not loaded", "Inpaint",
				JOptionPane.ERROR_MESSAGE);
			return;
		} else if (theMask == null)
		{
			JOptionPane.showMessageDialog(this, "The mask was not loaded", "Inpaint",
				JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Now see which method we use to inpaint
		ImageManipulator imgInpainted = null;
		if (jRadioYan.isSelected())
		{
			YanInpainter yaniImpaint = new YanInpainter(false);
			imgInpainted = yaniImpaint.inpaint(theImage, theMask);

			try
			{
				imgInpainted.saveToFile("inpainted.jpg");
				theMask.saveToFile("maskResult.jpg");
			} catch (Exception e)
			{
				JOptionPane.showMessageDialog(this, "Error saving the image", "Load Mask",
					JOptionPane.ERROR_MESSAGE);
				return;
			}
		} else
		{
			JOptionPane.showMessageDialog(this, "The inpaint method is not implemented", "Inpaint",
				JOptionPane.ERROR_MESSAGE);
			return;
		}

		// If reach here, everything was OK
		JOptionPane.showMessageDialog(this, "Inpaint successfully done", "Inpaint",
			JOptionPane.INFORMATION_MESSAGE);
		// Graphics g = imgInpainted.getImageSrc().getGraphics();
		 ShowImage si = new ShowImage(this);
		 si.repaint();
		
//		img2Draw = Toolkit.getDefaultToolkit().getImage("inpainted.jpg");
//		repaint();

	}
}
