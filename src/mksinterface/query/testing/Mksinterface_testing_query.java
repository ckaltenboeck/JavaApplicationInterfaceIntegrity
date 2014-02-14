package mksinterface.query.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import mksinterface.connection.src.Mksinterface_connection;
import mksinterface.connection.src.Mksinterface_connectionException;
import mksinterface.connection.src.Mksinterface_executeException;
import mksinterface.mksitem.src.Mksinterface_itemException;
import mksinterface.mksitem.src.Mksinterface_mksfield;
import mksinterface.mksitem.src.Mksinterface_mksitem;
import mksinterface.mksitem.src.Mksinterface_mksitem_developmentOrder;
import mksinterface.mksitem.src.Mksinterface_mksitem_release;
import mksinterface.query.src.Mksinterface_executeQueryException;
import mksinterface.query.src.Mksinterface_query;
import mksinterface.query.src.Mksinterface_result;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the Mksinterface_query class
 * @author ckaltenboeck
 *
 */
public class Mksinterface_testing_query {

	private static Mksinterface_connection connection_ = Mksinterface_connection.getInstance();
	//private static String MKSINTERFACE_TESTING_RESULT_PROJECT_STRING = "/Controls/3669/A191011";
	private int[] MKSINTERFACE_TESTING_QUERY_RELEASES ={110409, 123884, 186044, 186045, 186046, 186049, 186052};
	private int[] MKSINTERFACE_TESTING_QUERY_SOME_REQUIREMENTS_FOR_RELEASE_186045 = {203011,232233,144013};
	private int[] MKSINTERFACE_TESTING_QUERY_SOME_TASKS_FOR_RELEASE_110409_WITH_ASSIGNED_USER_BUGUR = {116831, 118244, 118949, 165200};
	private static int MKSINTERFACE_TESTING_RESULT_PROJECT = 110397; //Project A191011
	/**
	 * Will be executed before all Testcases. Only shall connect to Integrity
	 */
	@BeforeClass
	public static void init() {
		try {
			connection_.connect();
		} catch (Mksinterface_connectionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Will be executed after all Testcases. Only shall disconnect from Integrity
	 */
	@AfterClass
	public static void deinit()
	{
		try {
			connection_.disconnect();
		} catch (Mksinterface_connectionException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Query for getting all Releases of the project
	 * @throws Mksinterface_executeQueryException 
	 * @throws Mksinterface_connectionException 
	 */
	@Test
	public void easyGetAllReleasesQuery() throws Mksinterface_executeQueryException, Mksinterface_connectionException
	{
		String query = "{field[ID]=\"110397\"}.{Releases}->{}.{ID,Type}";
		Mksinterface_result result = Mksinterface_query.executeWholeQuery(query,connection_);
		ArrayList<Mksinterface_mksitem> allItems = result.getAllItems();
		for(int i=0; i < allItems.size(); i++ )
		{
			assertEquals("Test if type is correct!",allItems.get(i).getType(), Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE);
			assertTrue("Test if id of release is in the release list!", checkIfInArray(allItems.get(i).getId(), MKSINTERFACE_TESTING_QUERY_RELEASES));
		}
	}
	
	/**
	 * Tests if the requirements which are from the release 186045 are correct
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeQueryException 
	 */
	@Test
	public void testRequirementsForRelease() throws Mksinterface_connectionException, Mksinterface_executeQueryException
	{
		String query = "{field[ID]=\"186045\"}.{Development Orders}->{}.{Tasks}->{}.{Affected Requirements}->{}.{ID,Type}";
		Mksinterface_result result = Mksinterface_query.executeWholeQuery(query,connection_);
		ArrayList<Mksinterface_mksitem> allItems = result.getAllItems();
		for(int i=0; i < allItems.size(); i++ )
		{
			assertEquals("Test if type is correct!",allItems.get(i).getType(), Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_REQUIREMENT);
		}
		
		ArrayList<Integer> myRequirementsIDarray = new ArrayList<Integer>();
		for (int i=0; i < allItems.size(); i++)
			myRequirementsIDarray.add(allItems.get(i).getId());
		
		for(int i=0; i < MKSINTERFACE_TESTING_QUERY_SOME_REQUIREMENTS_FOR_RELEASE_186045.length; i++)
		{
			assertTrue("Test if Test Requirement "+MKSINTERFACE_TESTING_QUERY_SOME_REQUIREMENTS_FOR_RELEASE_186045[i]+ 
					" is in the whole Requirement list",myRequirementsIDarray.contains(
					MKSINTERFACE_TESTING_QUERY_SOME_REQUIREMENTS_FOR_RELEASE_186045[i]));
		}	
	}
	
	
	/**
	 * Tests if the resulted Tasks from the Requirement all have the correct Assigned User
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_executeQueryException 
	 */
	@Test
	public void testTasksWithAssignedUserFromReleaes() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeQueryException
	{
		String query = "{field[ID]=\"110409\"}.{Development Orders}->{}.{Tasks}->{field[Assigned User]=\"BUGURO\"}.{ID,Type,Assigned User}";
		Mksinterface_result result = Mksinterface_query.executeWholeQuery(query,connection_);
		ArrayList<Mksinterface_mksitem> allItems = result.getAllItems();
		
		ArrayList<Integer> myTaskIDarray = new ArrayList<Integer>();
		for (int i=0; i < allItems.size(); i++)
			myTaskIDarray.add(allItems.get(i).getId());
		
		for(int i=0; i < MKSINTERFACE_TESTING_QUERY_SOME_TASKS_FOR_RELEASE_110409_WITH_ASSIGNED_USER_BUGUR.length; i++)
		{
			assertTrue("Tests if all Tasks from the list are in the result",myTaskIDarray.contains(
					MKSINTERFACE_TESTING_QUERY_SOME_TASKS_FOR_RELEASE_110409_WITH_ASSIGNED_USER_BUGUR[i]));
		}	
	}
	
	/**
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_executeQueryException 
	 * 
	 */
	@Test
	public void testAllTasksfromDevelopmentOrderFieldState() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeQueryException
	{
		String query = "{field[ID]=\"116828\"}.{Tasks}->{field[State]=\"Done\"}.{ID,Type,State}";
		Mksinterface_result result = Mksinterface_query.executeWholeQuery(query,connection_);
		ArrayList<Mksinterface_mksitem> allItems = result.getAllItems();
		
		for(int i=0; i < allItems.size(); i++ )
		{
			assertEquals("Test if State is correct!",allItems.get(i).getField("State").getStringValue(), "Done");
		}
	}
	
	
	//Exception Test
	
	/**
	 * Tests if exception occurs when Query is not correct
	 * @throws Mksinterface_executeQueryException 
	 * @throws Mksinterface_connectionException 
	 */
	@Test (expected = Mksinterface_executeQueryException.class)
	public void testExceptionFalseQuery() throws Mksinterface_executeQueryException, Mksinterface_connectionException
	{
		String query = "{field[ID]=\"116828\"}.{Tasks}->{field[State]=\"Done\"}";
		Mksinterface_query.executeWholeQuery(query,connection_);
	}
	
	@Test (expected = Mksinterface_executeQueryException.class)
	public void testExceptionFalseQuery2() throws Mksinterface_executeQueryException, Mksinterface_connectionException
	{
		String query = "{.{Tasks}->{field[State]=\"Done\"}";
		Mksinterface_query.executeWholeQuery(query,connection_);
	}
	
	@Test (expected = Mksinterface_executeQueryException.class)
	public void testExceptionFalseQuery3() throws Mksinterface_executeQueryException, Mksinterface_connectionException
	{
		String query = "{field[ID]=\"116828\"}.{Tasks}{field[State]=\"Done\"}.{ID,Type,State}";
		Mksinterface_query.executeWholeQuery(query,connection_);
	}
	
	@Test (expected = Mksinterface_executeQueryException.class)
	public void testExceptionFalseQuery4() throws Mksinterface_executeQueryException, Mksinterface_connectionException
	{
		String query = "{field[ID]=\"116828\"}.{Tasks}->field[State]=\"Done\"}.{ID,Type,State}";
		Mksinterface_query.executeWholeQuery(query,connection_);
	}
	
	
	/**
	 * Easy testing the viewItemGUI method with valid ID
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeException 
	 */
	@Test
	public void testViewItemNoError() throws  Mksinterface_executeException, Mksinterface_connectionException
	{
		Mksinterface_query.viewItemGUI(MKSINTERFACE_TESTING_RESULT_PROJECT+"",connection_);
	}
	
	@Test (expected = Mksinterface_executeException.class)
	public void testViewItemStringError() throws Mksinterface_executeQueryException, Mksinterface_executeException, Mksinterface_connectionException
	{
		Mksinterface_query.viewItemGUI("ErrorID", connection_);
	}
	
	/**
	 * Method to help checking integer Arrays
	 * @param search int to search
	 * @param array  int array for searching
	 * @return true if found, false if not found
	 */
	private boolean checkIfInArray(int search, int[] array)
	{
		for(int i = array.length-1; i >= 0; i-- )
		{
			if (array[i] == search)
				return true;
		}
		return false;
	}
	
	
//	//EXCEPTION TESTS-------------------------------------------------------------------------------------------------------------------
//	
//	/**
//	 * Checks if query sets Exception if a wrong itemtype is given 
//	 * @throws Mksinterface_connectionException 
//	 * @throws Mksinterface_executeQueryException 
//	 */
//	@Test (expected = Mksinterface_executeQueryException.class)
//	public void checkQueryExceptionWrongItemtype() throws Mksinterface_executeQueryException, Mksinterface_connectionException
//	{
//		new Mksinterface_query("Strange Wrong itemtype",connection_);
//	}
//	
//	
//	/**
//	 * Checks if query sets Exception if there is no connection
//	 * @throws Mksinterface_connectionException 
//	 * @throws Mksinterface_executeQueryException 
//	 */
//	@Test (expected = Mksinterface_executeQueryException.class)
//	public void checkQueryExceptioNoConnection() throws Mksinterface_executeQueryException, Mksinterface_connectionException
//	{
//		new Mksinterface_query(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_CHANGE_OR_CONCERN,null);
//	}
//	
//	/**
//	 * Checks if query sets Exception if querytype is not available
//	 * @throws Mksinterface_connectionException 
//	 * @throws Mksinterface_executeQueryException 
//	 */
//	@Test (expected = Mksinterface_executeQueryException.class)
//	public void checkQueryExceptionOptionFalse() throws Mksinterface_executeQueryException, Mksinterface_connectionException
//	{
//		Mksinterface_query myTestQuery = new Mksinterface_query(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_DEVELOPMENT_ORDER,connection_);
//		myTestQuery.addOption("Some strange itemtype", "Strange field", "Testvalue");
//	}
//	
//	
//	
//	//FUNCTION TESTS-------------------------------------------------------------------------------------------------------------------
//	
//
//	/**
//	 * Creates a query which gets all Releases of project
//	 * @throws Mksinterface_connectionException 
//	 * @throws Mksinterface_executeQueryException 
//	 */
//	@Test
//	public void testEasyAllReleasesQuery() throws Mksinterface_executeQueryException, Mksinterface_connectionException
//	{
//		Mksinterface_query myReleaseQuery = new Mksinterface_query(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE,connection_);
//		myReleaseQuery.addOption(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE, "Project", MKSINTERFACE_TESTING_RESULT_PROJECT_STRING);
//		myReleaseQuery.execute();
//		Mksinterface_result myReleaseResult = myReleaseQuery.getResult();
//		ArrayList<Mksinterface_mksitem> myReleases = myReleaseResult.getAllItems();
//		for(int i=0; i < myReleases.size(); i++ )
//		{
//			assertTrue("Checks if ID has possible match",checkIfInArray(myReleases.get(i).getId(), MKSINTERFACE_TESTING_QUERY_RELEASES));
//		}
//	}
//	
//	/**
//	 * Creates a query which gets all Releases of Project 
//	 * the Query only knows that we want Releases and that an option is the Project
//	 * @throws Mksinterface_connectionException 
//	 * @throws Mksinterface_executeQueryException 
//	 */
//	@Test
//	public void testEasyAllReleasesQuery2() throws Mksinterface_executeQueryException, Mksinterface_connectionException
//	{
//
//		Mksinterface_query myReleaseQuery = new Mksinterface_query(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE,connection_);
//		myReleaseQuery.addOption(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_PROJECT, "Project", MKSINTERFACE_TESTING_RESULT_PROJECT_STRING);
//		myReleaseQuery.execute();
//		Mksinterface_result myReleaseResult = myReleaseQuery.getResult();
//		ArrayList<Mksinterface_mksitem> myReleases = myReleaseResult.getAllItems();
//		for(int i=0; i < myReleases.size(); i++ )
//		{
//			assertTrue("Checks if ID has possible match",checkIfInArray(myReleases.get(i).getId(), MKSINTERFACE_TESTING_QUERY_RELEASES));
//		}
//	}
//	
//	/**
//	 * Creates a query which gets all Tasks from a Release which are done
//	 * @throws Mksinterface_connectionException 
//	 * @throws Mksinterface_itemException 
//	 * @throws Mksinterface_executeQueryException 
//	 */
//	@Test
//	public void testGetAllTasksFromReleaseWhichDone() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeQueryException
//	{
//		Mksinterface_query myTaskQuery = new Mksinterface_query(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_TASK, connection_);
//		myTaskQuery.addOption(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE, "ID", MKSINTERFACE_TESTING_QUERY_RELEASES[0]+"");
//		myTaskQuery.addOption(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_TASK, "Status", "State");
//		myTaskQuery.execute();
//		Mksinterface_result myReleaseResult = myTaskQuery.getResult();
//		ArrayList<Mksinterface_mksitem> myTasks = myReleaseResult.getAllItems();
//		for(int i=0; i < myTasks.size(); i++ )
//		{
//			assertEquals("Check if task number "+i+" has correct Task type", myTasks.get(i).getType(), Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_TASK);
//			Mksinterface_mksfield stateField = myTasks.get(i).getField("State");
//			assertEquals("Check if task number "+i+", State is done","Done",stateField.getStringValue());
//		}
//	}
//	
//	/**
//	 * Create query which gets all Requirements from a release
//	 * Testing if the four requirements are in the array
//	 * @throws Mksinterface_connectionException 
//	 * @throws Mksinterface_executeQueryException 
//	 */
//	@Test
//	public void testAllRequirementsFromRelease() throws Mksinterface_executeQueryException, Mksinterface_connectionException
//	{
//
//		Mksinterface_query myRequirementQuery = new Mksinterface_query(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_REQUIREMENT, connection_);
//		myRequirementQuery.addOption(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE, "ID", MKSINTERFACE_TESTING_QUERY_RELEASES[3]+"");
//		myRequirementQuery.execute();
//		Mksinterface_result myRequirementResult = myRequirementQuery.getResult();
//		ArrayList<Mksinterface_mksitem> myRequirements = myRequirementResult.getAllItems();
//		
//		ArrayList<Integer> myRequirementsIDarray = new ArrayList<Integer>();
//		for (int i=0; i < myRequirements.size(); i++)
//			myRequirementsIDarray.add(myRequirements.get(i).getId());
//		
//		for(int i=0; i < MKSINTERFACE_TESTING_QUERY_SOME_REQUIREMENTS_FOR_RELEASE_186045.length; i++)
//		{
//			assertTrue("Check if Test Requirement "+MKSINTERFACE_TESTING_QUERY_SOME_REQUIREMENTS_FOR_RELEASE_186045[i]+ 
//					" is in the whole Requirement list",myRequirementsIDarray.contains(
//					MKSINTERFACE_TESTING_QUERY_SOME_REQUIREMENTS_FOR_RELEASE_186045[i]));
//		}
//	}
//	
//	/**
//	 * Create Query which gets all Tasks which Assigned User is "Bugur, Orhan AVL/TR (BUGURO)"
//	 * and is from release 110409
//	 * @throws Mksinterface_connectionException 
//	 * @throws Mksinterface_executeQueryException 
//	 */
//	@Test
//	public void testFieldQuery() throws Mksinterface_executeQueryException, Mksinterface_connectionException
//	{
//		ArrayList<String> fields = new ArrayList<String>();
//		fields.add("State");
//		fields.add("ID");
//		Mksinterface_query myTaskQuery = new Mksinterface_query(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_TASK,fields, connection_);
//		myTaskQuery.addOption(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_DEVELOPMENT_ORDER, "Asssigned User", "Bugur, Orhan AVL/TR (BUGURO)");
//		myTaskQuery.addOption(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE, "ID", 110409+"");
//		myTaskQuery.execute();
//		Mksinterface_result myTaskResult = myTaskQuery.getResult();
//		ArrayList<Mksinterface_mksitem> myRequirements = myTaskResult.getAllItems();
//		
//		ArrayList<Integer> myTaskIDarray = new ArrayList<Integer>();
//		for (int i=0; i < myTaskIDarray.size(); i++)
//			myTaskIDarray.add(myRequirements.get(i).getId());
//		
//		for(int i=0; i < MKSINTERFACE_TESTING_QUERY_SOME_TASKS_FOR_RELEASE_110409_WITH_ASSIGNED_USER_BUGUR.length; i++)
//		{
//			assertTrue("Check if Test Requirement "+ 
//					MKSINTERFACE_TESTING_QUERY_SOME_TASKS_FOR_RELEASE_110409_WITH_ASSIGNED_USER_BUGUR[i]+ 
//					" is in the whole Task list",myTaskIDarray.contains(
//					MKSINTERFACE_TESTING_QUERY_SOME_TASKS_FOR_RELEASE_110409_WITH_ASSIGNED_USER_BUGUR[i]));
//		}
//	}
	
	
}
