package mksinterface.query.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import mksinterface.connection.src.Mksinterface_connection;
import mksinterface.connection.src.Mksinterface_connectionException;
import mksinterface.connection.src.Mksinterface_executeException;
import mksinterface.mksitem.src.*;

import com.mks.api.Command;
import com.mks.api.Option;
import com.mks.api.response.APIException;
import com.mks.api.response.Field;
import com.mks.api.response.Response;
import com.mks.api.response.WorkItem;
import com.mks.api.response.WorkItemIterator;

/**
 * Class which parses the response object which is given from the interface and provide all different items for the user
 * Attention: This class do not check if the data is valid. If you add silly fields or other things be careful
 * @author ckaltenboeck
 *
 */
public class Mksinterface_result {
	//internal variables
	private ArrayList<Mksinterface_mksitem_project> projectList_;
	private ArrayList<Mksinterface_mksitem_release> releaseList_;
	private ArrayList<Mksinterface_mksitem_developmentOrder> developmentOrderList_;
	private ArrayList<Mksinterface_mksitem_task> taskList_;
	private ArrayList<Mksinterface_mksitem_requirement> requirementList_;
	private ArrayList<Mksinterface_mksitem_changeOrConcern> changeOrConcernList_;
	private ArrayList<Mksinterface_mksitem_requirementDocument> requirementDocumentList_;
	
	private Mksinterface_connection connection_;
	
	
	/**
	 * Constructor for creating the Mksinterface_result object
	 * ATTENTION: If there is no type information about the items, there will be an extra execute to 
	 * integrity which costs time. Please take care of adding the "Type" field to your execution.
	 * @param connection_
	 * @throws Mksinterface_parseResultException 
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 */
	public Mksinterface_result(Mksinterface_connection connection, Response response) throws Mksinterface_parseResultException, Mksinterface_itemException, Mksinterface_connectionException
	{
		if (connection == null)
			throw new Mksinterface_parseResultException("Connection not available");
		if (response == null)
			throw new Mksinterface_parseResultException("Response object is zero");
		
		connection_ = connection;
		
		projectList_ = new ArrayList<Mksinterface_mksitem_project>();
		releaseList_ = new ArrayList<Mksinterface_mksitem_release>();
		developmentOrderList_ = new ArrayList<Mksinterface_mksitem_developmentOrder>();
		taskList_ = new ArrayList<Mksinterface_mksitem_task>();
		requirementList_ = new ArrayList<Mksinterface_mksitem_requirement>();
		changeOrConcernList_ = new ArrayList<Mksinterface_mksitem_changeOrConcern>();
		requirementDocumentList_ = new ArrayList<Mksinterface_mksitem_requirementDocument>();
		
		WorkItemIterator workItems = response.getWorkItems();
		while(workItems.hasNext())
		{
			try {
				parseWorkItem(workItems.next());
			} catch (APIException e) {
				
			}
		}
	
	}
	
	/**
	 * Parses the Work Items from the response and add them to the respective Items
	 * @param workItems
	 * @throws APIException 
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_connectionException 
	 */
	private void parseWorkItem(WorkItem item) throws APIException, Mksinterface_itemException, Mksinterface_connectionException
	{
		HashMap<String, Mksinterface_mksfield> fieldMap = new HashMap<String, Mksinterface_mksfield>();
		@SuppressWarnings("unchecked")
		Iterator<Field> fieldIterator = item.getFields();
		while(fieldIterator.hasNext())
		{
			Field field = fieldIterator.next();
			try {
				Mksinterface_mksfield mksField = new Mksinterface_mksfield(field);
				fieldMap.put(mksField.getName(), mksField);
			} catch (Mksinterface_fieldException e) {
				//TODO: Error Log
				System.out.println("ERROR: Field couldn't be added: "+e.getMessage());
			}
		}
		
		Mksinterface_mksfield fieldType = fieldMap.get("Type");
		String itemType = null;
		if(fieldType == null)
		{
			try {
				itemType = getTypeFromIntegrity(item.getId());
			} catch (Mksinterface_executeException e) {
				throw new Mksinterface_itemException("Type couldn't be found. Maybe ID is not correct!");
			}
		} else
		{
			itemType = fieldType.getStringValue();
		}
		
		addItemToList(fieldMap, Integer.parseInt(item.getId()), itemType);
	}
	
	/**
	 * Creates a correct item with the specified type and id and adds them to the list
	 * @param fieldMap	Map of fields which the item contains
	 * @param id		Id of the item
	 * @param type		Type of the item
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 */
	private void addItemToList(HashMap<String, Mksinterface_mksfield> fieldMap, int id, String type) throws Mksinterface_itemException, Mksinterface_connectionException
	{
		if (type.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_PROJECT))
		{
			Mksinterface_mksitem_project myProject = new Mksinterface_mksitem_project(connection_, id);
			myProject.addFields(fieldMap);
			projectList_.add(myProject);
			return;
		}
		else if (type.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE))
		{
			Mksinterface_mksitem_release myRelease = new Mksinterface_mksitem_release(connection_, id);
			myRelease.addFields(fieldMap);
			releaseList_.add(myRelease);
			return;
		}
		else if (type.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_DEVELOPMENT_ORDER))
		{
			Mksinterface_mksitem_developmentOrder mydevelopmentOrder = new Mksinterface_mksitem_developmentOrder(connection_, id);
			mydevelopmentOrder.addFields(fieldMap);
			developmentOrderList_.add(mydevelopmentOrder);
			return;
		}
		else if (type.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_TASK))
		{
			Mksinterface_mksitem_task myTask = new Mksinterface_mksitem_task(connection_, id);
			myTask.addFields(fieldMap);
			taskList_.add(myTask);
			return;
		}
		else if (type.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_REQUIREMENT))
		{
			Mksinterface_mksitem_requirement myRequirement = new Mksinterface_mksitem_requirement(connection_, id);
			myRequirement.addFields(fieldMap);
			requirementList_.add(myRequirement);
			return;
		}
		else if (type.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_REQUIREMENT_DOCUMENT))
		{
			Mksinterface_mksitem_requirementDocument myRequirementDocument = new Mksinterface_mksitem_requirementDocument(connection_, id);
			myRequirementDocument.addFields(fieldMap);
			requirementDocumentList_.add(myRequirementDocument);
			return;
		}
		else if( type.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_CHANGE_OR_CONCERN))
		{
			Mksinterface_mksitem_changeOrConcern myChangeOrConcern = new Mksinterface_mksitem_changeOrConcern(connection_, id);
			myChangeOrConcern.addFields(fieldMap);
			changeOrConcernList_.add(myChangeOrConcern);
			return;
		}
		//TODO: ERROR log
		System.out.println("ERROR: Itemtype of Item: "+id+" is not available");
	}
	
	/**
	 * If there is no type field in the response. 
	 * The type has to be found out with the help of the id. 
	 * This method returns the correct type of the id
	 * @param id
	 * @return Type of the id, null if not found or another error occurred
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeException 
	 */
	private String getTypeFromIntegrity(String id) throws Mksinterface_executeException, Mksinterface_connectionException
	{
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,"Type"));
		cmd.addSelection(id);
		Response rs = connection_.execute(cmd);
		WorkItemIterator workItemIterator = rs.getWorkItems();
		WorkItem workItem;
		try {
			workItem = workItemIterator.next();
			Field field = workItem.getField("Type");
			return field.getItem().toString();
		} catch (APIException e) {
			return null;
		}
	}
	
	
	/**
	 * Returns all available items
	 * @return all items
	 */
	public ArrayList<Mksinterface_mksitem> getAllItems()
	{
		ArrayList<Mksinterface_mksitem> fullItemList = new ArrayList<Mksinterface_mksitem>();
		fullItemList.addAll(changeOrConcernList_);
		fullItemList.addAll(projectList_);
		fullItemList.addAll(requirementDocumentList_);
		fullItemList.addAll(developmentOrderList_);
		fullItemList.addAll(releaseList_);
		fullItemList.addAll(requirementList_);
		fullItemList.addAll(taskList_);
		return fullItemList;
	}
	
	/**
	 * Returns the number of items in the result
	 * @return number of items
	 */
	public int getItemCount()
	{
		return projectList_.size()+releaseList_.size()
				+developmentOrderList_.size()+taskList_.size()
				+requirementList_.size()+changeOrConcernList_.size();
	}
	
	/**
	 * Returns the number of items of the given itemtype
	 * @param itemtype	interesting itemtype
	 * @return number of items
	 */
	public int getItemCount(String itemtype)
	{
		if (itemtype.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_PROJECT))
			return projectList_.size();
		else if (itemtype.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE))
			return releaseList_.size();
		else if (itemtype.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_DEVELOPMENT_ORDER))
			return developmentOrderList_.size();
		else if (itemtype.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_TASK))
			return taskList_.size();
		else if (itemtype.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_REQUIREMENT))
			return requirementList_.size();
		else if (itemtype.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_REQUIREMENT_DOCUMENT))
			return requirementDocumentList_.size();
		else if( itemtype.equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_CHANGE_OR_CONCERN))
			return changeOrConcernList_.size();
		return 0;
	}
}
