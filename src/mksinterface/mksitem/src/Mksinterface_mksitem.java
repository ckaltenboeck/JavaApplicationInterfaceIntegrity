package mksinterface.mksitem.src;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
 * This class represents the base of every item in integrity. 
 * It is an abstract class, so it couldn't be created without defining a special type of the item.
 * All basic methods are implemented to access the item and all methods which are the same in all items
 * INFO: Please be careful by using addFields, the fields to add will not be checked if they are correct and possible
 *       because there is too much runtime loss
 * @author ckaltenboeck
 *
 */
public abstract class Mksinterface_mksitem {
	
	public static final String MKSINTERFACE_MKSITEM_TYPE_PROJECT = "Project";
	public static final String MKSINTERFACE_MKSITEM_TYPE_RELEASE = "Release";
	public static final String MKSINTERFACE_MKSITEM_TYPE_DEVELOPMENT_ORDER = "Development Order";
	public static final String MKSINTERFACE_MKSITEM_TYPE_TASK = "Task";
	public static final String MKSINTERFACE_MKSITEM_TYPE_REQUIREMENT = "Requirement";
	public static final String MKSINTERFACE_MKSITEM_TYPE_CHANGE_OR_CONCERN = "Change or concern";
	public static final String MKSINTERFACE_MKSITEM_TYPE_REQUIREMENT_DOCUMENT = "Requirement Document";
	
	
	protected int id_;
	protected HashMap<String, Mksinterface_mksfield> fieldMap_;
	protected Mksinterface_connection connection_;
	protected String type_;
	protected Mksinterface_mksitem_project project_;
	
	
	
	/**
	 * Protected Constructor only access from the child classes
	 * @param connect	connection Object
	 * @param id		id of the item
	 * @param type		type of the item
	 * @throws Mksinterface_connectionException 
	 */
	protected Mksinterface_mksitem(Mksinterface_connection connection, int id, String type) throws Mksinterface_itemException, Mksinterface_connectionException
	{
		connection_ = connection;
		fieldMap_ = new HashMap<String, Mksinterface_mksfield>();
		id_ = id;
		
		if(type == null)
			throw new Mksinterface_itemException("Type is not available");
		
		if(!type.equals(MKSINTERFACE_MKSITEM_TYPE_PROJECT) && 
				!type.equals(MKSINTERFACE_MKSITEM_TYPE_RELEASE) &&
				!type.equals(MKSINTERFACE_MKSITEM_TYPE_DEVELOPMENT_ORDER) &&
				!type.equals(MKSINTERFACE_MKSITEM_TYPE_TASK) &&
				!type.equals(MKSINTERFACE_MKSITEM_TYPE_REQUIREMENT) &&
				!type.equals(MKSINTERFACE_MKSITEM_TYPE_CHANGE_OR_CONCERN) &&
				!type.equals(MKSINTERFACE_MKSITEM_TYPE_REQUIREMENT_DOCUMENT))
			throw new Mksinterface_itemException("Type is not correct");
		
		
		Mksinterface_mksfield field = getField("Type");
		if (!field.getStringValue().equals(type))
			throw new Mksinterface_itemException("Type is not correct");
		
		//checking finished
		type_ = type;
		project_ = null;
	}
	
	/**
	 * Returns the wanted field
	 * @param	field field String
	 * @return	field represented in the Java class Mksinterface_mksfield
	 * @throws Mksinterface_connectionException 
	 */
	public Mksinterface_mksfield getField(String field) throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Mksinterface_mksfield mksfield;
		
		mksfield = fieldMap_.get(field);
		if (mksfield != null)
			return mksfield;
		
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,field));
		cmd.addSelection(id_+"");
		try {
			Response rs = connection_.execute(cmd);
			WorkItemIterator workItemIterator = rs.getWorkItems();
			WorkItem workItem = workItemIterator.next();
			@SuppressWarnings("unchecked")
			Iterator<Field> fieldIterator = workItem.getFields();
			try {
				mksfield =  new Mksinterface_mksfield(fieldIterator.next());
				fieldMap_.put(field, mksfield);
				return mksfield;
			} catch (Mksinterface_fieldException e) {
				throw new Mksinterface_itemException("Can't create "+field+" Field, Field Message: "+e.getMessage());
			}
		} catch (Mksinterface_executeException e) {
			throw new Mksinterface_itemException("Executing the "+field+" Field finding failed, Message: "+e.getMessage());
		} catch (APIException e) {
			throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_RESPONSE_NOT_CORRECT);
		}	
	}
	
	/**
	 * Returns the wanted fields
	 * @param  fields	ArrayList of String fields
	 * @return fields represented in the Java class Mksinterface_mksfield
	 * @throws Mksinterface_connectionException 
	 */
	public ArrayList<Mksinterface_mksfield> getFields(ArrayList<String> fields) throws Mksinterface_itemException, Mksinterface_connectionException
	{
		String fieldString = "";
		ArrayList<Mksinterface_mksfield> mksfields = new ArrayList<Mksinterface_mksfield>();
		Mksinterface_mksfield mksfield;
		
		if(fields.size() == 0)
			throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_FIELD_EMPTY);
		fieldString = fields.get(0);
		for (int i=1; i < fields.size(); i++)
			fieldString = fieldString + "," + fields.get(i);
		
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,fieldString));
		cmd.addSelection(id_+"");
		try {
			Response rs = connection_.execute(cmd);
			WorkItemIterator workItemIterator = rs.getWorkItems();
			WorkItem workItem = workItemIterator.next();
			@SuppressWarnings("unchecked")
			Iterator<Field> fieldIterator = workItem.getFields();
			for(int i=0; fieldIterator.hasNext();i++)
			{
				mksfield = new Mksinterface_mksfield(fieldIterator.next());
				if (!fieldMap_.containsValue(mksfield))
					fieldMap_.put(fields.get(i), mksfield);
				mksfields.add(mksfield);
			}
		} catch (Mksinterface_executeException e) {
			throw new Mksinterface_itemException("Executing the "+fieldString+" Fields finding failed, Message: "+e.getMessage());
		} catch (APIException e) {
			throw new Mksinterface_itemException("Access to Response failed, Message:"+ e.getMessage());
		} catch (Mksinterface_fieldException e) {
			throw new Mksinterface_itemException("Can't create "+fieldString+" Fields, Field Message: "+e.getMessage());
		}	
		return mksfields;
		
	}
	
	/**
	 * Returns the project of the item
	 * @return	Mksinterface_mks_project item
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 * @throws  
	 */
	public Mksinterface_mksitem_project getProject() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		if (project_ != null)
			return project_;
		Mksinterface_mksfield projectField = getField("Project");
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,"ID"));
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_QUERYDEFINITION,
				"((field[Type]=\"Project\") and (field[Project]=\""+projectField.getStringValue()+"\"))"));
		try {
			Response rs = connection_.execute(cmd);
			WorkItemIterator workItemIterator = rs.getWorkItems();
			if (!workItemIterator.hasNext())
				throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_NO_ITEM);
			WorkItem workItem;
			workItem = workItemIterator.next();
			@SuppressWarnings("unchecked")
			Iterator<Field> fieldIterator = workItem.getFields();
			if (!fieldIterator.hasNext())
				throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_NO_FIELD);
			project_ = new Mksinterface_mksitem_project(connection_, fieldIterator.next().getInteger());
		} catch (APIException e) {
			throw new Mksinterface_itemException("Project Field can't be reached");
		} catch (Mksinterface_executeException e) {
			throw new Mksinterface_itemException(Mksinterface_itemException.MKSINTERFACE_ITEM_EXCEPTION_NOT_EXECUTED);
		}
		return project_;
	}
	
	/**
	 * Returns the id of the field
	 * @return integer id
	 */
	public int getId()
	{
		return id_;
	}
	
	/**
	 * Returns the type of the Item
	 * @return
	 */
	public String getType()
	{
		return type_;
	}
	
	/**
	 * This method adds fields to the given Item.
	 * ATTENTION: The fields are unchecked so be careful which things you add to the item
	 * @param fieldMap
	 * @throws Mksinterface_itemException 
	 */
	public void addFields(HashMap<String, Mksinterface_mksfield> fieldMap) throws Mksinterface_itemException
	{
		if (fieldMap == null)
			throw new Mksinterface_itemException("Fieldmap is empty");
		fieldMap_.putAll(fieldMap);
	}
	
	
	/**
	 * Returns all existing Field elements without entering the integrity interface
	 * @return
	 */
	public HashMap<String, Mksinterface_mksfield> getAllExistingFields()
	{
		return fieldMap_;
	}
	
}
