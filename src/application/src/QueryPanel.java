package application.src;

import java.awt.*;


import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import jxl.Workbook;
import jxl.write.*;
import jxl.write.Label;

import mksinterface.connection.src.Mksinterface_connectionException;
import mksinterface.connection.src.Mksinterface_executeException;
import mksinterface.libary.src.Mksinterface_libary;
import mksinterface.mksitem.src.Mksinterface_mksfield;
import mksinterface.mksitem.src.Mksinterface_mksitem;
import mksinterface.query.src.Mksinterface_executeQueryException;
import mksinterface.query.src.Mksinterface_result;

/**
 * Panel for creating and executing a query. the result of the execution will be represented in 
 * a JTable and could be exported to Excel
 * @author u10e32
 *
 */
public class QueryPanel extends JPanel implements ActionListener, MouseListener{
	
	private static final long serialVersionUID = 1L;
	
	private JButton button_execute_ = new JButton("Execute");
	private JButton button_export_ = new JButton("Save as Excel");
	private MyTableModel table_model_;
	private JTable table_itemInfo_ = new JTable();
	private JScrollPane sp_table_ = new JScrollPane(table_itemInfo_);
	private JTextField textField_query_ = new JTextField("Please enter query!");
	
	private JPanel panel_top_ = new JPanel();
	
    private GridBagLayout gbl_ = new GridBagLayout();
    
    private Mksinterface_libary myLibary_;
    private Mksinterface_result actualResult_;
    
    private boolean clearLine_ = true;
    
	public QueryPanel(Mksinterface_libary myLibary)
	{
		myLibary_ = myLibary;
	
		this.setLayout(new BorderLayout());
		this.add(sp_table_, BorderLayout.CENTER);
		this.add(button_export_, BorderLayout.SOUTH);
		
		panel_top_.setLayout(gbl_);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx =5;
		panel_top_.add(textField_query_, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 1;
		panel_top_.add(button_execute_, c);
		
		this.add(panel_top_, BorderLayout.NORTH);
		
		
		
		button_execute_.addActionListener(this);
		button_export_.addActionListener(this);
		textField_query_.addMouseListener(this);
		table_itemInfo_.addMouseListener(this);
	
	}

	/**
	 * ActionListener for both buttons
	 */
	public void actionPerformed(ActionEvent e) {
		Object[][] testarray = {};
		if(e.getSource() == button_execute_)
		{
			
			try {
				actualResult_ = myLibary_.executeQuery(textField_query_.getText());
				ArrayList<Mksinterface_mksitem> allItems = actualResult_.getAllItems();
				for (int i=0; i < allItems.size(); i++)
				{
					HashMap<String, Mksinterface_mksfield> allFields = allItems.get(i).getAllExistingFields();
					Collection<Mksinterface_mksfield> fieldArray = allFields.values();
					if (i == 0)
					{
						Set<String> fieldSet = allFields.keySet();
						table_model_ = new MyTableModel(testarray, fieldSet.toArray());
						table_itemInfo_.setModel(table_model_);
					} 
					Object[] array = fieldArray.toArray();
					if (allItems.get(i).getId() == 306832)
					{
						for (int j=0; j < array.length; j++)
							System.out.print(array[j]+ "  ,  ");
						System.out.println("");
					}
					table_model_.addRow(fieldArray.toArray());
				}
			} catch (Mksinterface_executeQueryException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Execute Query Exception", JOptionPane.ERROR_MESSAGE);
			} catch (Mksinterface_connectionException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Connection Exception", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else if (e.getSource() == button_export_)
		{
			
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.showSaveDialog(this);
			File f = fc.getSelectedFile();
			if (f != null)
			{
				WritableWorkbook workbook;
				try {
					workbook = Workbook.createWorkbook(f);
					WritableSheet sheet = workbook.createSheet("Query Sheet", 0);
					
					for(int col = 0; col< table_model_.getColumnCount(); col++)
					{
						Label label = new Label(col, 0, table_model_.getColumnName(col));
						sheet.addCell(label);
					}
					for (int row = 0; row < table_model_.getRowCount(); row++)
					{
						for(int col = 0; col< table_model_.getColumnCount(); col++)
						{
							Label label = new Label(col, row+1, table_model_.getValueAt(row, col).toString());
							sheet.addCell(label);
						}
					}
					workbook.write();
					workbook.close();

				} catch (WriteException we) {
					JOptionPane.showMessageDialog(this, we.getMessage(), "Writing File", JOptionPane.ERROR_MESSAGE);
				} catch (IOException ioe) {
					JOptionPane.showMessageDialog(this, ioe.getMessage(), "Writing File", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	/**
	 *Mouse Listener for deleting Text in field
	 */
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == textField_query_)
		{
			if(clearLine_)
			{
				textField_query_.setText("");
				clearLine_ = false;
			}
		} else if( e.getSource() == table_itemInfo_ && e.getClickCount() == 2)
		{
			String id = table_model_.getID(table_itemInfo_.getSelectedRow());
			try {
				myLibary_.viewGUIItem(id);
			} catch (Mksinterface_executeException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Execute Exception", JOptionPane.ERROR_MESSAGE);
			} catch (Mksinterface_connectionException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Connection Exception", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {	
	}

	@Override
	public void mouseExited(MouseEvent arg0) {	
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {	
	}
}

/**
 * Table model for Item table
 * @author ckaltenboeck
 *
 */
class MyTableModel extends DefaultTableModel
{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor 
	 * @param testarray
	 * @param array
	 */
	public MyTableModel(Object[][] testarray, Object[] array) {
		super(testarray, array);
	}

	/**
	 * Overwrites the isCellEditable internal method to made all cells not editable
	 */
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}
	
	/**
	 * Returns the ID to the specified row
	 * @param row
	 * @return
	 */
	public String getID(int row)
	{
		int col =0;
		for (; col < this.getColumnCount(); col++)
		{
			if(this.getColumnName(col).equals("ID"))
					break;
		}
		return this.getValueAt(row, col).toString();
	}
	
}
