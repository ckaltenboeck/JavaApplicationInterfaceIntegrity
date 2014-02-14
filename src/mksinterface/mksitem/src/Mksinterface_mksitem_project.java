package mksinterface.mksitem.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mks.api.Command;
import com.mks.api.Option;
import com.mks.api.response.APIException;
import com.mks.api.response.Field;
import com.mks.api.response.Response;
import com.mks.api.response.WorkItem;
import com.mks.api.response.WorkItemIterator;

import mksinterface.connection.src.Mksinterface_connection;
import mksinterface.connection.src.Mksinterface_connectionException;
import mksinterface.connection.src.Mksinterface_executeException;

/**
 * Item Class which represents a project item in Integrity. With this class you have access to all items 
 * which are in relationship with this project item.
 * @author ckaltenboeck
 *
 */
public class Mksinterface_mksitem_project extends Mksinterface_mksitem{

	/**
	 * Constructor for project item
	 * @param connect	connection object
	 * @param id		id of the item
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_connectionException 
	 */
	public Mksinterface_mksitem_project(Mksinterface_connection connect,
			int id) throws Mksinterface_itemException, Mksinterface_connectionException {
		super(connect, id, Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_PROJECT);
	}
	
	/**
	 * Returns all Release items which are in the relationship of the project
	 * @return release items
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeException 
	 */
	public ArrayList<Mksinterface_mksitem_release> getAllReleases() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,"Releases"));
		cmd.addSelection(id_+"");
			Response rs;
			try {
				rs = connection_.execute(cmd);
			} catch (Mksinterface_executeException e1) {
				throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_NOT_EXECUTED);
			}
			WorkItemIterator workItemIterator = rs.getWorkItems();
			WorkItem workItem;
			try {
				if (!workItemIterator.hasNext())
					throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_NO_ITEM);
				workItem = workItemIterator.next();
				@SuppressWarnings("unchecked")
				Iterator<Field> fieldIterator = workItem.getFields();
				ArrayList<Mksinterface_mksitem_release> allReleases = new ArrayList<Mksinterface_mksitem_release>();
				if (!fieldIterator.hasNext())
					throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_NO_FIELD);
				@SuppressWarnings("unchecked")
				List<Field> fieldList = fieldIterator.next().getList();
				for(int i=0; i < fieldList.size(); i++)
				{
					Integer releaseID = new Integer(fieldList.get(i)+"");
					try {
						allReleases.add(new Mksinterface_mksitem_release(connection_, releaseID));
					} catch (Mksinterface_itemException itemexc)
					{
						//TODO Error Log
						System.out.println("ERROR: Item with Id: " + releaseID+ " can't be created");
					}
				}
				return allReleases;
			} catch (APIException e) {
				throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_RESPONSE_NOT_CORRECT);
			}
	}
	
	/**
	 * Returns all Development Order items which are in the relationship of the release
	 * @return development Order items
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 */
	public ArrayList<Mksinterface_mksitem_developmentOrder> getAllDevelopmentOrders() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		ArrayList<Mksinterface_mksitem_release> allReleases = getAllReleases();
		ArrayList<Mksinterface_mksitem_developmentOrder> allDevelopmentOrders = new ArrayList<Mksinterface_mksitem_developmentOrder>();
		for(int i = 0; i < allReleases.size(); i++)
		{
			Mksinterface_mksitem_release actualRelease = allReleases.get(i);
			allDevelopmentOrders.addAll(actualRelease.getAllDevelopmentOrders());
		}
		return allDevelopmentOrders;
	}
	
	/**
	 * Returns all Task items which are in the relationship of the release
	 * @return task items
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 */
	public ArrayList<Mksinterface_mksitem_task> getAllTasks() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		ArrayList<Mksinterface_mksitem_developmentOrder> allDevelopmentOrders = getAllDevelopmentOrders();
		ArrayList<Mksinterface_mksitem_task> allTasks = new ArrayList<Mksinterface_mksitem_task>();
		for(int i = 0; i < allDevelopmentOrders.size(); i++)
		{
			Mksinterface_mksitem_developmentOrder actualDevelopmentOrder = allDevelopmentOrders.get(i);
			allTasks.addAll(actualDevelopmentOrder.getAllTasks());
		}
		return allTasks;
	}
	
	/**
	 * Returns all Requirement items which are in the relationship of the release
	 * @return requirement items
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeException 
	 * @throws Mksinterface_itemException 
	 */
	public ArrayList<Mksinterface_mksitem_requirement> getAllRequirements() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		ArrayList<Mksinterface_mksitem_task> allTasks = getAllTasks();
		ArrayList<Mksinterface_mksitem_requirement> allRequirements = new ArrayList<Mksinterface_mksitem_requirement>();
		for(int i = 0; i < allTasks.size(); i++)
		{
			Mksinterface_mksitem_task actualTask = allTasks.get(i);
			allRequirements.addAll(actualTask.getAllRequirements());
		}
		return allRequirements;
	}
	
	/**
	 * Overwrites the getProject Method for saving time
	 * @return this object
	 */
	public Mksinterface_mksitem_project getProject()
	{
		return this;
	}
	

}
