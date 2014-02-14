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
 * Item Class which represents a requirement item in Integrity. With this class you have access to all items 
 * which are in relationship with this requirement item.
 * @author ckaltenboeck
 *
 */
public class Mksinterface_mksitem_requirement  extends Mksinterface_mksitem{
	/**
	 * Constructor for requirement item
	 * @param connect	connection object
	 * @param id		id of the item
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_connectionException 
	 */
	public Mksinterface_mksitem_requirement(Mksinterface_connection connect,
			int id) throws Mksinterface_itemException, Mksinterface_connectionException {
		super(connect, id, Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_REQUIREMENT);
	}
	
	/**
	 * Returns the release of the Requirement
	 * @return Mksinterface_mksitem_release item
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 */
	public ArrayList<Mksinterface_mksitem_release> getAllReleases() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		ArrayList<Mksinterface_mksitem_developmentOrder> developmentOrders = getAllDevelopmentOrders();
		ArrayList<Mksinterface_mksitem_release> allReleases = new ArrayList<Mksinterface_mksitem_release>();
		for (int i=0; i < developmentOrders.size(); i++)
		{
			Mksinterface_mksitem_release temprelease = developmentOrders.get(i).getRelease();
			int id = temprelease.getId();
			for(int j=0; j  < allReleases.size(); j++)
			{
				int idExisting = allReleases.get(j).getId();
				if (idExisting == id)
				{
					allReleases.remove(j);
					break;
				}
			}
			allReleases.add(temprelease);
		}
		return allReleases;
	}
	
	/**
	 * Returns the Development Order item of this Requirement
	 * @return Mksiternface_mksitem_developmentOrder item
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeException 
	 * @throws Mksinterface_itemException 
	 */
	public ArrayList<Mksinterface_mksitem_developmentOrder> getAllDevelopmentOrders() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		ArrayList<Mksinterface_mksitem_task> tasks = getAllTasks();
		ArrayList<Mksinterface_mksitem_developmentOrder> allDevelopmentOrders = new ArrayList<Mksinterface_mksitem_developmentOrder>();
		for (int i=0; i < tasks.size(); i++)
		{
			Mksinterface_mksitem_developmentOrder tempdevelopmentOrder = tasks.get(i).getDevelopmentOrder();
			int id = tempdevelopmentOrder.getId();
			for(int j=0; j  < allDevelopmentOrders.size(); j++)
			{
				int idExisting = allDevelopmentOrders.get(j).getId();
				if (idExisting == id)
				{
					allDevelopmentOrders.remove(j);
					break;
				}
			}
			allDevelopmentOrders.add(tempdevelopmentOrder);
		}
		return allDevelopmentOrders;
	}
	
	
	/**
	 * Return the Task item of this Requirement
	 * @return Mksinterfac_mksitem_task item
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeException 
	 */
	public ArrayList<Mksinterface_mksitem_task> getAllTasks() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,"Related Development Tasks"));
		cmd.addSelection(id_+"");
			Response rs;
			try {
				rs = connection_.execute(cmd);
			} catch (Mksinterface_executeException e1) {
				throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_RESPONSE_NOT_CORRECT);
			}
			WorkItemIterator workItemIterator = rs.getWorkItems();
			WorkItem workItem;
			try {
				if (!workItemIterator.hasNext())
					throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_NO_ITEM);
				workItem = workItemIterator.next();
				@SuppressWarnings("unchecked")
				Iterator<Field> fieldIterator = workItem.getFields();
				ArrayList<Mksinterface_mksitem_task> allTasks = new ArrayList<Mksinterface_mksitem_task>();
				if (!fieldIterator.hasNext())
					throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_NO_FIELD);
				@SuppressWarnings("unchecked")
				List<Field> fieldList = fieldIterator.next().getList();
				for(int i=0; i < fieldList.size(); i++)
				{
					Integer taskID = new Integer(fieldList.get(i)+"");
					try {
						allTasks.add(new Mksinterface_mksitem_task(connection_, taskID));
					} catch(Mksinterface_itemException itemexc)
					{
						//TODO Error log
						System.out.println("ERROR: Item with Id: " + taskID+ " can't be created");
					}
				}
				return allTasks;
			} catch (APIException e) {
				throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_RESPONSE_NOT_CORRECT);
			}
	}
	
}
