package application.src;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import mksinterface.connection.src.Mksinterface_connectionException;
import mksinterface.libary.src.Mksinterface_libary;
import mksinterface.mksitem.src.Mksinterface_itemException;
import mksinterface.mksitem.src.Mksinterface_mksitem;
import mksinterface.query.src.Mksinterface_executeQueryException;
import mksinterface.query.src.Mksinterface_result;

public class SWRequirementsPanel extends JPanel implements ItemListener{
	
	
	private static final long serialVersionUID = 1L;
	
	Mksinterface_libary myLib_;

	private JLabel l_project_ = new JLabel("                              Select Project                                   ");
	private JLabel l_release_ = new JLabel("                              Select Release                                   ");
	
	private JComboBox cb_project_ = new JComboBox();
	private JComboBox cb_release_ = new JComboBox();
	
	private JPanel p_diag_ = new JPanel(new GridLayout(1,2,20,50));
	private JPanel p_north_ = new JPanel(new GridLayout(2,2,20,10));
	
	
	DefaultCategoryDataset dataset_sw_system_ = new DefaultCategoryDataset();
	JFreeChart chart_sw_system_ = ChartFactory.createStackedBarChart("Software System Requirements", "States", "#Requirements", dataset_sw_system_);
	private ChartPanel p_sw_system_ = new ChartPanel(chart_sw_system_,true,true,true,true,true);
	
	DefaultCategoryDataset dataset_sw_component_ = new DefaultCategoryDataset();
	JFreeChart chart_sw_component_ = ChartFactory.createStackedBarChart("Software Component Requirements", "States", "#Requirements", dataset_sw_component_);
	private ChartPanel p_sw_conponent_ = new ChartPanel(chart_sw_component_);
	
	private ArrayList<Integer> releaseIDs_ = new ArrayList<Integer>();
	
	public SWRequirementsPanel(Mksinterface_libary mylib) throws Mksinterface_connectionException
	{
		myLib_ = mylib;
		p_north_.add(l_project_);
		p_north_.add(l_release_);
		p_north_.add(cb_project_);
		p_north_.add(cb_release_);
		
		p_diag_.add(p_sw_system_);
		p_diag_.add(p_sw_conponent_);
		
		this.add(p_north_,BorderLayout.NORTH);
		this.add(p_diag_,BorderLayout.CENTER);
		
		updateProjectList();
		
		
	}
	
	/**
	 * Updates the Combobox with all projects
	 * @throws Mksinterface_connectionException 
	 */
	private void updateProjectList() throws Mksinterface_connectionException
	{
		cb_project_.removeAllItems();
		try {
			Mksinterface_result  result = myLib_.executeQuery("{field[Type]=Project}.{ID,Type,Project}");
			ArrayList<Mksinterface_mksitem> allItems = result.getAllItems();
			for (int i=0; i < allItems.size(); i++)
				cb_project_.addItem(allItems.get(i).getField("Project"));
			cb_project_.addItemListener(this);
				
		} catch (Mksinterface_executeQueryException e) {
			System.out.println("ERROR: Not able to create good queries");
		} catch (Mksinterface_itemException e) {
			System.out.println("ERROR: Not able to create good queries");
		}
		
	}
	
	/**
	 * Updates the release combobox for given project
	 * @param project
	 * @throws Mksinterface_connectionException 
	 */
	private void updateReleaseList(String project) throws Mksinterface_connectionException
	{
		cb_release_.removeAllItems();
		releaseIDs_.clear();
		try {
			Mksinterface_result  result = myLib_.executeQuery("{(field[Type]=Release) and (field[Project]="+project+")}.{ID,Type,Summary}");
			ArrayList<Mksinterface_mksitem> allItems = result.getAllItems();
			for (int i=0; i < allItems.size(); i++)
			{
				cb_release_.addItem(allItems.get(i).getField("Summary"));
				releaseIDs_.add(allItems.get(i).getId());
			}
				
		} catch (Mksinterface_executeQueryException e) {
			System.out.println("ERROR: Not able to create good queries");
		} catch (Mksinterface_itemException e) {
			System.out.println("ERROR: Not able to create good queries");
		}
		
	}
	
	
	/**
	 * Updates the chart which is given by the parameter requirementLevel
	 * @param releaseID			ID of the release, or zero if all Releases of the Project shall be calculated
	 * @param project       	The Project at which this shall be happen
	 * @param requirementLevel	Requirement Level, "SW system" and "SW component" are possible
	 * @throws Mksinterface_connectionException
	 */
	private void updateRequirements(int releaseID, String project, String requirementLevel) throws Mksinterface_connectionException
	{
		int[] stateCancelled = {0,0};
		int[] stateInWork = {0,0};
		int[] stateReview = {0,0};
		int[] stateDone = {0,0};
		int[] stateNew= {0,0};
		try {
			System.out.println(releaseID);
			Mksinterface_result result=null;
			if (releaseID == 0)
				result = myLib_.executeQuery("{((field[Type]=Requirement) and (field[Project]=\""+project+"\") and " +
						"(field[Category] =\"Functional Requirement\",\"Non-Functional Requirement\") and (field[Requirement Level]=\""+requirementLevel+"\"))}.{ID,Type,Requirement Level,State,Trace Status}");
			else {
				result = myLib_.executeQuery("{field[ID]=\""+releaseID+"\"}.{Development Orders}->" +
					"{}.{Tasks}->{}.{Affected Requirements}->" +
					"{((field[Category] =\"Functional Requirement\",\"Non-Functional Requirement\") and (field[Requirement Level]=\""+requirementLevel+"\"))}.{ID,Type,Requirement Level,State,Trace Status}");
			}
			if (result == null)
			{
				JOptionPane.showMessageDialog(this, "Not enough informations for calculating available", "Calculating Exception", JOptionPane.ERROR_MESSAGE);
				return;
			}
			ArrayList<Mksinterface_mksitem> allItems = result.getAllItems();
			for (int i=0; i  < allItems.size(); i++)
			{
				String field = allItems.get(i).getField("State").toString();
				if (field.equals("Cancelled"))
				{
					if(allItems.get(i).getField("Trace Status").toString().equals("none"))
						stateCancelled[0]++;
					else
						stateCancelled[1]++;
					
				} else if (field.equals("In Work"))
				{
					if(allItems.get(i).getField("Trace Status").toString().equals("none"))
						stateInWork[0]++;
					else
						stateInWork[1]++;
					
				} else if (field.equals("Review"))
				{
					if(allItems.get(i).getField("Trace Status").toString().equals("none"))
						stateReview[0]++;
					else
						stateReview[1]++;
					
				} else if (field.equals("Done"))
				{
					if(allItems.get(i).getField("Trace Status").toString().equals("none"))
						stateDone[0]++;
					else
						stateDone[1]++;
					
				} else if (field.equals("New"))
				{
					if(allItems.get(i).getField("Trace Status").toString().equals("none"))
						stateNew[0]++;
					else
						stateNew[1]++;
					
				} else
				{
					System.out.println("State of item "+allItems.get(i).getId() + "is not correct!");
				}
			}
			
			if(requirementLevel.equals("SW system"))
			{
				dataset_sw_system_.addValue(stateCancelled[0], "no trace", "Cancelled");
				dataset_sw_system_.addValue(stateCancelled[1], "trace", "Cancelled");
				dataset_sw_system_.addValue(stateReview[0], "no trace", "Review");
				dataset_sw_system_.addValue(stateReview[1], "trace", "Review");
				dataset_sw_system_.addValue(stateInWork[0], "no trace", "In Work");
				dataset_sw_system_.addValue(stateInWork[1], "trace", "In Work");
				dataset_sw_system_.addValue(stateNew[0], "no trace", "New");
				dataset_sw_system_.addValue(stateNew[1], "trace", "New");
				dataset_sw_system_.addValue(stateDone[0], "no trace", "Done");
				dataset_sw_system_.addValue(stateDone[1], "trace", "Done");
			}
			else if (requirementLevel.equals("SW component")){
				dataset_sw_component_.addValue(stateCancelled[0], "no trace", "Cancelled");
				dataset_sw_component_.addValue(stateCancelled[1], "trace", "Cancelled");
				dataset_sw_component_.addValue(stateReview[0], "no trace", "Review");
				dataset_sw_component_.addValue(stateReview[1], "trace", "Review");
				dataset_sw_component_.addValue(stateInWork[0], "no trace", "In Work");
				dataset_sw_component_.addValue(stateInWork[1], "trace", "In Work");
				dataset_sw_component_.addValue(stateNew[0], "no trace", "New");
				dataset_sw_component_.addValue(stateNew[1], "trace", "New");
				dataset_sw_component_.addValue(stateDone[0], "no trace", "Done");
				dataset_sw_component_.addValue(stateDone[1], "trace", "Done");
			}
			
		} catch (Mksinterface_executeQueryException e) {
			System.out.println("ERROR: Query is false!");
		} catch (Mksinterface_itemException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Item Exception", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Method to override for changed Items in the comboboxes
	 */
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == cb_project_ && e.getStateChange()== ItemEvent.SELECTED)
		{
			cb_release_.removeItemListener(this);
			try {
				updateReleaseList(cb_project_.getSelectedItem().toString());
				updateRequirements(0, cb_project_.getSelectedItem().toString(),"SW system");
				updateRequirements(0, cb_project_.getSelectedItem().toString(),"SW component");
				this.repaint();
				this.validate();
			} catch (Mksinterface_connectionException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Connection Exception", JOptionPane.ERROR_MESSAGE);
			}
			cb_release_.addItemListener(this);
		}
		else if(e.getSource() == cb_release_ && e.getStateChange()== ItemEvent.SELECTED)
		{
			cb_release_.removeItemListener(this);
			try {
				updateRequirements(releaseIDs_.get(cb_release_.getSelectedIndex()), cb_project_.getSelectedItem().toString(),"SW system");
				updateRequirements(releaseIDs_.get(cb_release_.getSelectedIndex()), cb_project_.getSelectedItem().toString(),"SW component");
			} catch (Mksinterface_connectionException e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Connection Exception", JOptionPane.ERROR_MESSAGE);
			}
			cb_release_.addItemListener(this);
		}
		
	}
	

}
