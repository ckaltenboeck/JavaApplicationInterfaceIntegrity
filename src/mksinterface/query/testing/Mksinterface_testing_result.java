package mksinterface.query.testing;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.HashMap;



import mksinterface.connection.src.*;
import mksinterface.mksitem.src.Mksinterface_itemException;
import mksinterface.mksitem.src.Mksinterface_mksfield;
import mksinterface.mksitem.src.Mksinterface_mksitem;
import mksinterface.mksitem.src.Mksinterface_mksitem_release;
import mksinterface.query.src.*;

import org.junit.*;

import com.mks.api.*;
import com.mks.api.response.Response;

/**
 * This class shall test the Mksinterface_testing_result class
 * @author ckaltenboeck
 *
 */
public class Mksinterface_testing_result {
	
	//Testitems
	private static String MKSINTERFACE_TESTING_RESULT_PROJECT_STRING = "/Controls/3669/A191011";
	private int[] MKSINTERFACE_TESTING_RESULT_RELEASES ={110409, 123884, 186044, 186045, 186046, 186049, 186052, 302641, 309605};
	private static int MKSINTERFACE_TESTING_RESULT_PROJECT = 110397; //Project A191011
	
	//Connection
	private static Mksinterface_connection connection_ = Mksinterface_connection.getInstance();

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
	 * Testing null connection and null response
	 * @throws Mksinterface_parseResultException 
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 */
	@Test (expected = Mksinterface_parseResultException.class)
	public void TestEasyResultException() throws Mksinterface_parseResultException, Mksinterface_itemException, Mksinterface_connectionException
	{
		new Mksinterface_result(null, null);
	}
	
	/**
	 * Testing null connection
	 * @throws Mksinterface_executeException
	 * @throws Mksinterface_connectionException
	 * @throws Mksinterface_parseResultException 
	 * @throws Mksinterface_itemException 
	 */
	@Test (expected = Mksinterface_parseResultException.class)
	public void TestEasyResultException2() throws Mksinterface_executeException, Mksinterface_connectionException, Mksinterface_parseResultException, Mksinterface_itemException
	{
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addSelection(MKSINTERFACE_TESTING_RESULT_PROJECT+"");
		Response rs = connection_.execute(cmd);
		new Mksinterface_result(null, rs);
	}
	
	/**
	 * Testing null response
	 * @throws Mksinterface_parseResultException 
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 */
	@Test (expected = Mksinterface_parseResultException.class)
	public void TestEasyResultException3() throws Mksinterface_parseResultException, Mksinterface_itemException, Mksinterface_connectionException
	{
		new Mksinterface_result(connection_, null);
	}
	
	/**
	 * Tests if the Response returns a correct arraylist of release items
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeException 
	 * @throws Mksinterface_parseResultException 
	 * @throws Mksinterface_itemException 
	 */
	@Test
	public void testGettingReleaseItemsFromProject() throws Mksinterface_executeException, Mksinterface_connectionException, Mksinterface_parseResultException, Mksinterface_itemException
	{
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,"ID"));
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_QUERYDEFINITION,
				"((field[Type]=\"Release\") and " +
				"(field[Project]=\""+MKSINTERFACE_TESTING_RESULT_PROJECT_STRING+"\"))"));
		Response responseFromIntegrity = connection_.execute(cmd);
		Mksinterface_result result = new Mksinterface_result(connection_,responseFromIntegrity);
		assertEquals("Result contains the correct number of Release Item objects from the given Project", result.getItemCount(),MKSINTERFACE_TESTING_RESULT_RELEASES.length);
		ArrayList<Mksinterface_mksitem> releases = result.getAllItems();
		for (int i = 0; i < releases.size(); i++)
		{
			Mksinterface_mksitem_release release = (Mksinterface_mksitem_release) releases.get(i);
			assertTrue("Checks if Release with ID: "+ release.getId()+"is in the prepared Release list",checkIfInArray(release.getId(),MKSINTERFACE_TESTING_RESULT_RELEASES));
			assertEquals("Checks if of the Item in the Result is correct", release.getType(), Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE);
		}
	}
	
	
	/**
	 * Tests if the items contain Summary and 
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeException 
	 * @throws Mksinterface_parseResultException 
	 * @throws Mksinterface_itemException 
	 */
	@Test
	public void testGettingReleaseItemsFromProjectWithSummaryField() throws Mksinterface_executeException, Mksinterface_connectionException, Mksinterface_parseResultException, Mksinterface_itemException
	{
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,"ID,Summary,State"));
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_QUERYDEFINITION,
				"((field[Type]=\"Release\") and " +
				"(field[Project]=\""+MKSINTERFACE_TESTING_RESULT_PROJECT_STRING+"\"))"));
		Response responseFromIntegrity = connection_.execute(cmd);
		Mksinterface_result result = new Mksinterface_result(connection_,responseFromIntegrity);
		ArrayList<Mksinterface_mksitem> releases = result.getAllItems();
		for (int i = 0; i < releases.size(); i++)
		{
			Mksinterface_mksitem_release release = (Mksinterface_mksitem_release) releases.get(i);
			HashMap<String, Mksinterface_mksfield> existingFields = release.getAllExistingFields();
			assertTrue("Tests if the item contains a Summary field",existingFields.containsKey("Summary"));
			assertTrue("Tests if the item contains a Summary field",existingFields.containsKey("State"));
		}
	}
	
	/**
	 * Tests if the items are tasks and releases
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeException 
	 * @throws Mksinterface_parseResultException 
	 * @throws Mksinterface_itemException 
	 */
	@Test
	public void testDifferentTypes() throws Mksinterface_executeException, Mksinterface_connectionException, Mksinterface_parseResultException, Mksinterface_itemException
	{
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,"ID"));
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_QUERYDEFINITION,
				"(((field[Type]=\"Release\") or (field[Type]=\"Task\")) and " +
				"(field[Project]=\""+MKSINTERFACE_TESTING_RESULT_PROJECT_STRING+"\"))"));
		Response responseFromIntegrity = connection_.execute(cmd);
		Mksinterface_result result = new Mksinterface_result(connection_,responseFromIntegrity);
		boolean testTasksAreMoreThanZero = false;
		boolean testReleasesAreMoreThanZero = false;
		if (result.getItemCount(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_TASK) > 0) testTasksAreMoreThanZero = true;
		assertTrue("Tests if result contains one ore more tasks", testTasksAreMoreThanZero);
		if (result.getItemCount(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE) > 0) testReleasesAreMoreThanZero = true;
		assertTrue("Tests if result contains one ore more releases", testReleasesAreMoreThanZero);
	}
	
	/**
	 * Tests if the items are tasks and releases 
	 * It's the same test like the upper one but has to be executed much faster 
	 * because the type of the items is already known
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_executeException 
	 * @throws Mksinterface_parseResultException 
	 * @throws Mksinterface_itemException 
	 */
	@Test
	public void testDifferentTypesWithTypeKnowledge() throws Mksinterface_executeException, Mksinterface_connectionException, Mksinterface_parseResultException, Mksinterface_itemException
	{
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,"ID,Type"));
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_QUERYDEFINITION,
				"(((field[Type]=\"Release\") or (field[Type]=\"Task\")) and " +
				"(field[Project]=\""+MKSINTERFACE_TESTING_RESULT_PROJECT_STRING+"\"))"));
		Response responseFromIntegrity = connection_.execute(cmd);
		Mksinterface_result result = new Mksinterface_result(connection_,responseFromIntegrity);
		boolean testTasksAreMoreThanZero = false;
		boolean testReleasesAreMoreThanZero = false;
		if (result.getItemCount(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_TASK) > 0) testTasksAreMoreThanZero = true;
		assertTrue("Tests if result contains one ore more tasks", testTasksAreMoreThanZero);
		if (result.getItemCount(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE) > 0) testReleasesAreMoreThanZero = true;
		assertTrue("Tests if result contains one ore more releases", testReleasesAreMoreThanZero);
	}
	
	
	/**
	 * Checks the given parameter
	 * @param search
	 * @param array
	 * @return
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
}
