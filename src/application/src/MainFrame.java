package application.src;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import mksinterface.connection.src.Mksinterface_connectionException;
import mksinterface.libary.src.Mksinterface_libary;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Creating the Mainframe
	 */
	public MainFrame()
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screen);
		this.setTitle("Test GUI!");
		
		Mksinterface_libary myLib = new Mksinterface_libary();
		JTabbedPane tabs = new JTabbedPane();
		
		try {
			myLib.connect();
		} catch (Mksinterface_connectionException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Connection Exception", JOptionPane.ERROR_MESSAGE);
		}
		
		tabs.addTab("Query",new QueryPanel(myLib));
		try {
			tabs.addTab("SW Requirements", new SWRequirementsPanel(myLib));
		} catch (Mksinterface_connectionException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Connection Exception", JOptionPane.ERROR_MESSAGE);
		}
		
		this.add(tabs);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * Main method for creating the window
	 * @param args
	 */
	public static void main(String [] args)
	{
		new MainFrame();
	}

}
