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
 * Item Class which represents a task item in Integrity. With this class you have access to all items 
 * which are in relationship with this task item.
 * @author ckaltenboeck
 *
 */
public class Mksinterface_mksitem_task  extends Mksinterface_mksitem{
	/**
	 * Constructor for task item
	 * @param connect	connection object
	 * @param id		id of the item
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_connectionException 
	 */
	public Mksinterface_mksitem_task(Mksinterface_connection connect, int id) throws Mksinterface_itemException, Mksinterface_connectionException
	{
		super(connect,id,Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_TASK);
	}
	
	/**
	 * Returns the Release item of this task
	 * @return Mksinterface_mksitem_release item
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_connectionException 
	 */
	public Mksinterface_mksitem_release getRelease() throws Mksinterface_connectionException, Mksinterface_itemException
	{
		Mksinterface_mksitem_developmentOrder developmentOrder = getDevelopmentOrder();
		return developmentOrder.getRelease();
	}
	
	/**
	 * Returns the Development Order item of this task
	 * @return Mksiternface_mksitem_developmentOrder item
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeException 
	 * @throws Mksinterface_itemException 
	 */
	public Mksinterface_mksitem_developmentOrder getDevelopmentOrder() throws Mksinterface_connectionException, Mksinterface_itemException
	{
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,"Task For"));
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
				if (!fieldIterator.hasNext())
					throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_NO_FIELD);
				@SuppressWarnings("unchecked")
				List<Field> fieldList = fieldIterator.next().getList();
				if (fieldList.size()!=1)
					throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_FIELD_NOT_CORRECT);
				Integer developmentOrderID = new Integer(fieldList.get(0)+"");
				try {
					return new Mksinterface_mksitem_developmentOrder(connection_, developmentOrderID);
				} catch (Mksinterface_itemException itemexc)
				{
					//TODO Error Log
					System.out.println("ERROR: Item with Id: " + developmentOrderID+ " can't be created");
					return null;
				}
			} catch (APIException e) {
				throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_RESPONSE_NOT_CORRECT);
			}
	}
	
	/**
	 * Returns all containing Requirements of the task
	 * @return ArrayList of Mksinterface_mksitem_requirement items
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeException 
	 */
	public ArrayList<Mksinterface_mksitem_requirement> getAllRequirements() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,"Affected Requirements"));
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
				ArrayList<Mksinterface_mksitem_requirement> allRequirements = new ArrayList<Mksinterface_mksitem_requirement>();
				if (!fieldIterator.hasNext())
					throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_NO_FIELD);
				@SuppressWarnings("unchecked")
				List<Field> fieldList = fieldIterator.next().getList();
				for(int i=0; i < fieldList.size(); i++)
				{
					Integer requirementID = new Integer(fieldList.get(i)+"");
					try{
						allRequirements.add(new Mksinterface_mksitem_requirement(connection_, requirementID));
					} catch(Mksinterface_itemException itemexc)
					{
						//TODO: ERROR log
						System.out.println("ERROR: Item with Id: " + requirementID+ " can't be created");
					}
				}
				return allRequirements;
			} catch (APIException e) {
				throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_RESPONSE_NOT_CORRECT);
			}
	}
	
	
	
}
