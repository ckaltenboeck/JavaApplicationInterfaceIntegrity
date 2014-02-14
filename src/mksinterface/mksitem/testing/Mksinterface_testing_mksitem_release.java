package mksinterface.mksitem.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import mksinterface.connection.src.Mksinterface_connection;
import mksinterface.connection.src.Mksinterface_connectionException;
import mksinterface.connection.src.Mksinterface_executeException;
import mksinterface.mksitem.src.Mksinterface_itemException;
import mksinterface.mksitem.src.Mksinterface_mksitem_developmentOrder;
import mksinterface.mksitem.src.Mksinterface_mksitem_release;
import mksinterface.mksitem.src.Mksinterface_mksitem_requirement;
import mksinterface.mksitem.src.Mksinterface_mksitem_task;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the Mksinterface_mksitem_release class
 * @author ckaltenboeck
 *
 */
public class Mksinterface_testing_mksitem_release {

	private int MKSINTERFACE_TESTING_MKSITEM_RELEASE = 110409;
	private int[] MKSINTERFACE_TESTING_RELEASE_DEVELOPMENT_ORDER = {116828, 116908, 116919, 117056, 117105};
	private int[] MKSINTERFACE_TESTING_RELEASE_ANY_TASKS = {117817, 138905, 117059,117783};
	private int[] MKSINTERFACE_TESTINT_RELEASE_ANY_REQUIREMENTS={117946, 117997, 164259, 164261, 164264};
	
	
    private static Mksinterface_connection connection_ = Mksinterface_connection.getInstance();
	
	@BeforeClass
	public static void init() throws Mksinterface_connectionException
	{
		//connection_.setTestHostname();
		connection_.connect();
	}
	
	@AfterClass
	public static void deinit() throws Mksinterface_connectionException
	{
		connection_.disconnect();
	}
	
	/**
	 * Checks the getDevelopmentOrders method
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_executeException 
	 */
	@Test
	public void testGetDevelopmentOrders() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeException {
		Mksinterface_mksitem_release mytestrelease = new Mksinterface_mksitem_release(connection_, 
				MKSINTERFACE_TESTING_MKSITEM_RELEASE);
		ArrayList<Mksinterface_mksitem_developmentOrder> myTestDevelopmentOrders = mytestrelease.getAllDevelopmentOrders();
		
		ArrayList<Integer> myDevelopmentOrdersIDarray = new ArrayList<Integer>();
		for (int i=0; i < myTestDevelopmentOrders.size(); i++)
			myDevelopmentOrdersIDarray.add(myTestDevelopmentOrders.get(i).getId());
		
		for(int i=0; i < MKSINTERFACE_TESTING_RELEASE_DEVELOPMENT_ORDER.length; i++)
		{
			assertTrue("Tests if the testarray values are contained in the result development Orders",myDevelopmentOrdersIDarray.contains(
					MKSINTERFACE_TESTING_RELEASE_DEVELOPMENT_ORDER[i]));
		}
	}
	
	/**
	 * Checks the getAllTasks method
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_executeException 
	 */
	@Test
	public void testGetTasks() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeException {
		Mksinterface_mksitem_release mytestrelease = new Mksinterface_mksitem_release(connection_, 
				MKSINTERFACE_TESTING_MKSITEM_RELEASE);
		ArrayList<Mksinterface_mksitem_task> myTestTask = mytestrelease.getAllTasks();
		
		ArrayList<Integer> myTaskIDarray = new ArrayList<Integer>();
		for (int i=0; i < myTestTask.size(); i++)
			myTaskIDarray.add(myTestTask.get(i).getId());
		
		for(int i=0; i < MKSINTERFACE_TESTING_RELEASE_ANY_TASKS.length; i++)
		{
			assertTrue("Tests if the testarray values are contained in the result task",myTaskIDarray.contains(
					MKSINTERFACE_TESTING_RELEASE_ANY_TASKS[i]));
		}
	}
	
	/**
	 * Checks the getAllRequirements method
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_executeException 
	 */
	@Test
	public void testGetRequirements() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeException {
		Mksinterface_mksitem_release mytestrelease = new Mksinterface_mksitem_release(connection_, 
				MKSINTERFACE_TESTING_MKSITEM_RELEASE);
		ArrayList<Mksinterface_mksitem_requirement> myTestRequirements = mytestrelease.getAllRequirements();
		
		ArrayList<Integer> myRequirementIDarray = new ArrayList<Integer>();
		for (int i=0; i < myTestRequirements.size(); i++)
			myRequirementIDarray.add(myTestRequirements.get(i).getId());
		
		for(int i=0; i < MKSINTERFACE_TESTINT_RELEASE_ANY_REQUIREMENTS.length; i++)
		{
			assertTrue("Tests if the testarray values are contained in the result requirement",myRequirementIDarray.contains(
					MKSINTERFACE_TESTINT_RELEASE_ANY_REQUIREMENTS[i]));
		}
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
