package mksinterface.query.src;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.mks.api.Command;
import com.mks.api.Option;
import com.mks.api.response.Response;

import mksinterface.connection.src.*;
import mksinterface.mksitem.src.Mksinterface_itemException;
import mksinterface.mksitem.src.Mksinterface_mksitem;

/**
 * Class for creating, handling and executing a mksquery
 * @author ckaltenboeck
 *
 */
public class Mksinterface_query {
	
	/**
	 * This method executes the whole query which is given with the 
	 * parameter "query" and returns the resulted items in an Mksinterface_result object
	 * Querynotation:
	 * {Query1}.{field1,field2,...}->{Query2}.{field1,field2}->{Query3}.{field1,field2}->....
	 * Query2 will be executed of the resulting items from Query1 and Query3 will be executed of the 
	 * resulting items from Query2....
	 * At the end all resulted items with the last field definitions will be returned
	 * If there is no more information (e.g.: want to know task from all development Orders of release, but there are no development Orders) 
	 * the result returns zero
	 * @param query
	 * @return
	 * @throws Mksinterface_executeQueryException 
	 * @throws Mksinterface_connectionException 
	 */
	public static Mksinterface_result executeWholeQuery(String query, Mksinterface_connection connection) throws Mksinterface_executeQueryException, Mksinterface_connectionException
	{
		ArrayList<Object> actualItemList = new ArrayList<Object>();
		Mksinterface_result endResult = null;
		//Parse query String and send exception if not correct
		String[] pairs = query.split(Pattern.quote( "}->{" ));
		if (pairs.length == 0)
		{
			pairs = new String[1];
			pairs[0] = query;
		}
		
		//parse pairs
		for(int pairIt = 0; pairIt < pairs.length; pairIt++)
		{
			String[] tempSeperated = pairs[pairIt].split(Pattern.quote( "}.{"));
			if (tempSeperated.length != 2)
				throw new Mksinterface_executeQueryException("Pair is not seperate able to a query and field term!");
			tempSeperated[0]= tempSeperated[0].replace("{","");
			tempSeperated[1]= tempSeperated[1].replace("}", "");
			if(tempSeperated[0].indexOf('{')!=-1)
				throw new Mksinterface_executeQueryException("Query part is not correct!");
			if(tempSeperated[0].indexOf('}')!=-1)
				throw new Mksinterface_executeQueryException("Query part is not correct!");
			if(tempSeperated[1].indexOf('{')!=-1)
				throw new Mksinterface_executeQueryException("Field part is not correct!");
			if(tempSeperated[1].indexOf('}')!=-1)
				throw new Mksinterface_executeQueryException("Field part is not correct!");
			
			if (actualItemList.isEmpty() && tempSeperated[0].equals(""))
				return null;
			
			String fullQueryPart = createFullQueryString(tempSeperated[0],actualItemList);
			Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
			cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,tempSeperated[1]));
			cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_QUERYDEFINITION, fullQueryPart));
			try {
				Response response = connection.execute(cmd);
				endResult = new Mksinterface_result(connection, response);
				
				if (pairIt == (pairs.length-1) || pairs.length == 1)
					break;
				
				actualItemList = getNextIDs(endResult.getAllItems(),tempSeperated[1].split(Pattern.quote( "," )));
				
				
			} catch (Mksinterface_executeException e) {
				throw new Mksinterface_executeQueryException(e.getMessage());
			} catch (Mksinterface_parseResultException e) {
				throw new Mksinterface_executeQueryException(e.getMessage());
			} catch (Mksinterface_itemException e) {
				throw new Mksinterface_executeQueryException(e.getMessage());
			}
		}
		return endResult;
	}
	
	/**
	 * This method returns the full Query to execute in Integrity. So the actual query which is given by the user
	 * it extended with the item selection 
	 * @param query
	 * @param actualItemList
	 * @return
	 */
	private static String createFullQueryString(String query,ArrayList<Object> actualItemList)
	{
		String fullQuery = "";
		if (!query.equals("") && query != null)
			fullQuery = "("+query+") and (";
		else
			fullQuery = "(";
		
		if(actualItemList.size()==0 || actualItemList == null)
			return "("+query+")";
		
		
		for(int index = 0; index < actualItemList.size(); index++)
		{
			fullQuery += "(field[ID]=\""+actualItemList.get(index)+"\")";
			if (actualItemList.size()!=1 && index != (actualItemList.size()-1))
				fullQuery += " or ";
		}
		fullQuery += ")";
		return "("+fullQuery+")";
	}
	
	/**
	 * This method searches all given fields for the new items to query and returns the ID's of the items
	 * @param actualItemList
	 * @return
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 */
	private static ArrayList<Object> getNextIDs(ArrayList<Mksinterface_mksitem> actualItemList, String[] fieldList) throws Mksinterface_itemException, Mksinterface_connectionException
	{
		ArrayList<Object> nextIds = new ArrayList<Object>();
		for (int itemIndex = 0; itemIndex < actualItemList.size(); itemIndex++)
		{
			for (int fieldIndex = 0; fieldIndex < fieldList.length; fieldIndex++)
			{
				nextIds.addAll((actualItemList.get(itemIndex).getField(fieldList[fieldIndex]).getListValue()));
			}
		}
		return nextIds;
	}
	
	/**
	 * Shows the selected ID in the GUI Window of Integrity
	 * @param id selected ID
	 * @throws Mksinterface_connectionException 
	 */
	public static void viewItemGUI (String id, Mksinterface_connection connection) throws Mksinterface_executeException, Mksinterface_connectionException
	{
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_VIEWISSUE);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_GUI));
		cmd.addSelection(id);
		connection.execute(cmd);
	}
	
	//Following uncommented Methods are not used yet but will be used in the future for a better query implementation
	
//	/**
//	 * Constructor which defines the query with the itemtypes which shall result
//	 * @param itemtypes	The result object shall contain all these itemtypes if there are any possible matches
//	 * @param connection	Connection object
//	 * @throws Mksinterface_executeQueryException 
//	 * @throws Mksinterface_connectionException 
//	 */
//	public Mksinterface_query (ArrayList<String> itemtypes, Mksinterface_connection connection) throws Mksinterface_executeQueryException, Mksinterface_connectionException
//	{
//		if (itemtypes == null)
//			throw new Mksinterface_executeQueryException("Executing the Query failed: No itemtypes defined");
//		if (connection == null)
//			throw new Mksinterface_connectionException("Connection missing");
//		
//		init(connection,itemtypes, null);
//	}
//	
//	/**
//	 * Constructor which defines the query with one resulting itemtype
//	 * @param itemtype	The result object shall contain these itemtype if there are any possible matches
//	 * @param connection	Connection object
//	 * @throws Mksinterface_executeQueryException 
//	 * @throws Mksinterface_connectionException 
//	 */
//	public Mksinterface_query (String itemtype, Mksinterface_connection connection) throws Mksinterface_executeQueryException, Mksinterface_connectionException
//	{
//		if (itemtype == null)
//			throw new Mksinterface_executeQueryException("Executing the Query failed: No itemtypes defined");
//		if (connection == null)
//			throw new Mksinterface_connectionException("Connection missing");
//		
//		ArrayList<String> itemtypes = new ArrayList<String>();
//		itemtypes.add(itemtype);
//		init(connection,itemtypes, null);
//		
//	}
//	
//	/**
//	 * Constructor which defines the query with one resulting itemtype and the option to define resulting fields
//	 * @param itemtype	The result object shall contain these itemtype if there are any possible matches
//	 * @param fields	The items in the result object shall contain these fields
//	 * @param connection	Connection object
//	 * @throws Mksinterface_executeQueryException 
//	 * @throws Mksinterface_connectionException 
//	 */
//	public Mksinterface_query (String itemtype, ArrayList<String> fields, Mksinterface_connection connection) throws Mksinterface_executeQueryException, Mksinterface_connectionException
//	{
//		if (itemtype == null)
//			throw new Mksinterface_executeQueryException("Executing the Query failed: No itemtypes defined");
//		if (connection == null)
//			throw new Mksinterface_connectionException("Connection missing");
//		if (fields == null)
//			throw new Mksinterface_executeQueryException("Executing the Query failed: No fields available, choose another constructor");
//		
//		ArrayList<String> itemtypes = new ArrayList<String>();
//		itemtypes.add(itemtype);
//		init(connection,itemtypes, fields);
//	}
//	
//	/**
//	 * Initializes the whole class
//	 * @param connection
//	 * @param itemtypes
//	 * @param fields
//	 */
//	private void init(Mksinterface_connection connection, ArrayList<String> itemtypes, ArrayList<String> fields)
//	{
//		connection_ = connection;
//		itemtypes_ = itemtypes;
//		
//		if (fields != null)
//			fields_ = fields;
//		else
//			fields_ = new ArrayList<String>();
//		
//		optionList_ = new Mksinterface_query_optionList();
//	}
//	
//	/**
//	 * Adds an option for the query to the list
//	 * @param itemtype	over which item the option shall be defined
//	 * @param field		over which field in the item shall the option be queried
//	 * @param value		the value of the field
//	 * @throws Mksinterface_executeQueryException 
//	 */
//	public void addOption(String itemtype, String field, String value) throws Mksinterface_executeQueryException
//	{
//		optionList_.addOptionToList(new Mksinterface_query_option(itemtype,field,value));
//	}
//	
//	/**
//	 * Adds an option for the query to the list
//	 * @param option
//	 * @throws Mksinterface_executeQueryException 
//	 */
//	public void addOption(Mksinterface_query_option option) throws Mksinterface_executeQueryException
//	{
//		optionList_.addOptionToList(option);
//	}
//	
//	/**
//	 * Executes and returns the correct Result
//	 * @return Result of the query
//	 */
//	public Mksinterface_result getResult()
//	{
//		return null;
//	}
//	
//	/**
//	 * Executes the whole query
//	 */
//	public void execute()
//	{
//		
//	}
}
