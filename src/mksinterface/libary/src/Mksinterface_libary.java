package mksinterface.libary.src;

import java.util.ArrayList;

import mksinterface.connection.src.Mksinterface_connection;
import mksinterface.connection.src.Mksinterface_connectionException;
import mksinterface.connection.src.Mksinterface_executeException;
import mksinterface.query.src.*;

/**
 * This class is the interface to all important functions of the mksinterface. 
 * The user of this class is able to connect, disconnect, create and 
 * execute queries and manipulate them. Users shall only use this class for working with the mksinterface
 * @author ckaltenboeck
 *
 */
public class Mksinterface_libary {
	/**
	 * Constructor which creates the libary
	 */
	
	private Mksinterface_connection connection_ = Mksinterface_connection.getInstance();
	
	public Mksinterface_libary()
	{
		
	}
	
	/**
	 * Connects the libary to mks
	 * @return	actual status of connection
	 * @throws Mksinterface_connectionException 
	 */
	public boolean connect() throws Mksinterface_connectionException
	{
		connection_.connect();
		return connection_.getState();
	}
	
	
	/**
	 * Disconnect the libary from mks
	 * @return actual status of connection
	 * @throws Mksinterface_connectionException 
	 */
	public boolean disconnect() throws Mksinterface_connectionException
	{
		connection_.disconnect();
		return connection_.getState();
	}
	
	
	/**
	 * Executes a query and returns the result of the query
	 * @param query
	 * @return
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeQueryException 
	 */
	public Mksinterface_result executeQuery(String query) throws Mksinterface_executeQueryException, Mksinterface_connectionException
	{
		return Mksinterface_query.executeWholeQuery(query, connection_);
	}
	
	
	public void viewGUIItem(String id) throws Mksinterface_executeException, Mksinterface_connectionException
	{
		Mksinterface_query.viewItemGUI(id, connection_);
	}
	
	
	//The following methods are uncommented because they are not used at the moment.
	//For a better usage of the query definition for the user I tried to make more options to hold the query and reuse them
	//At this point these points aren't neccessary
	
	
//	/**
//	 * Creates a new query in the libary. 
//	 * @param itemtypes		Itemtypes which shall be available if possible
//	 * @param options		List of options to add to the query
//	 * @return				Id of the query in the libary. It's possible to access the query with this id
//	 */
//	public int newQuery(ArrayList<String> itemtypes, ArrayList<Mksinterface_query_option> options)
//	{
//		return 0;
//	}
//	
//	/**
//	 * Creates a new query in the libary
//	 * @param itemtypes		Itemtypes which shall be available if possible
//	 * @param option		Option to add to the query
//	 * @return				Id of the query in the libary. It's possible to access the query with this id
//	 */
//	public int newQuery(ArrayList<String> itemtypes, Mksinterface_query_option option)
//	{
//		return 0;
//	}
//	
//	/**
//	 * Creates a new query in the libary
//	 * @param itemtype		Itemtype which shall be available if possible
//	 * @param options		List of options to add to the query
//	 * @return				Id of the query in the libary. It's possible to access the query with this id
//	 */
//	public int newQuery(String itemtype, ArrayList<Mksinterface_query_option> options)
//	{
//		return 0;
//	}
//	
//	/**
//	 * Creates a new query in the libary
//	 * @param itemtype		Itemtype which shall be available if possible
//	 * @param option		Option to add to the query
//	 * @return				Id of the query in the libary. It's possible to access the query with this id
//	 */
//	public int newQuery(String itemtype, Mksinterface_query_option option)
//	{
//		return 0;
//	}
//	
//	/**
//	 * Create a new query in the libary
//	 * @param itemtype		Itemtype which shall be available if possible
//	 * @param options		List of options to add to the query
//	 * @param fields		List of fields which are wanted in the items
//	 * @return				Id of the query in the libary. It's possible to access the query with this id
//	 */
//	public int newQuery(String itemtype, ArrayList<String> fields, ArrayList<Mksinterface_query_option> options)
//	{
//		return 0;
//	}
//	
//	/**
//	 * Creates a new query in the libary
//	 * @param itemtype		Itemtype which shall be available if possible
//	 * @param fields		List of fields which are wanted in the items
//	 * @param option		Option to add to the query
//	 * @return id of the query
//	 */
//	public int newQuery(String itemtype, ArrayList<String> fields, Mksinterface_query_option option)
//	{
//		return 0;
//	}
//	
//	/**
//	 * Adds an option to the specified query
//	 * @param option		Option to add
//	 * @param queryId		QueryId in the libary
//	 */
//	public void addOptionToQuery(Mksinterface_query_option option, int queryId)
//	{
//		
//	}
//	
//	/**
//	 * Results the result object for the query with the specified id
//	 * @param queryId		QueryId in the libary
//	 * @return				Result object of the query
//	 */
//	public Mksinterface_result getQueryResult(int queryId)
//	{
//		return null;
//	}
//	
}
