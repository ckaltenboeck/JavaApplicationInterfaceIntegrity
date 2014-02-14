package mksinterface.mksitem.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import mksinterface.connection.src.Mksinterface_connection;
import mksinterface.connection.src.Mksinterface_connectionException;
import mksinterface.connection.src.Mksinterface_executeException;
import mksinterface.mksitem.src.Mksinterface_itemException;
import mksinterface.mksitem.src.Mksinterface_mksitem_developmentOrder;
import mksinterface.mksitem.src.Mksinterface_mksitem_project;
import mksinterface.mksitem.src.Mksinterface_mksitem_release;
import mksinterface.mksitem.src.Mksinterface_mksitem_requirement;
import mksinterface.mksitem.src.Mksinterface_mksitem_task;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the Mksinterface_mksitem_project class
 * @author ckaltenboeck
 *
 */
public class Mksinterface_testing_mksitem_project {

	private int[] MKSINTERFACE_TESTING_PROJECT_RELEASES = {110409, 123884, 186044, 186045, 186046, 186049, 186052, 302641, 309605};
	private int[] MKSINTERFACE_TESTING_PROJECT_ANY_DEVELOPMENT_ORDERS = {116919, 204140, 117056, 187029};
	private int[] MKSINTERFACE_TESTING_PROJECT_ANY_TASKS = {201017, 203352, 206619, 190034, 201016, 201136, 202962};
	private int[] MKSINTERFACE_TESTING_PROJECT_ANY_REQUIREMENTS = {189987, 203638, 142459, 118654, 118658};
	private int MKSINTERFACE_TESTING_PROJECT = 110397;
	
	
    private static Mksinterface_connection connection_ = Mksinterface_connection.getInstance();
	
	@BeforeClass
	public static void init() throws Mksinterface_connectionException
	{
		connection_.connect();
	}
	
	@AfterClass
	public static void deinit() throws Mksinterface_connectionException
	{
		connection_.disconnect();
	}
	
	/**
	 * Checks the getAllReleases method
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_executeException 
	 */
	@Test
	public void testGetReleases() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeException {
		Mksinterface_mksitem_project myProject = new Mksinterface_mksitem_project(connection_, MKSINTERFACE_TESTING_PROJECT);
		ArrayList<Mksinterface_mksitem_release> myTestReleases = myProject.getAllReleases();
		for ( int i=0; i< myTestReleases.size(); i++)
		{
			assertTrue("Tests if the Requirment is in the TestArray",checkIfInArray(myTestReleases.get(i).getId(),
					MKSINTERFACE_TESTING_PROJECT_RELEASES));
		}
	}
	
	/**
	 * Tests if any of the developmentOrders from the releases of 
	 * the project result with this method
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 */
	@Test
	public void testGetDevelopmentOrders() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Mksinterface_mksitem_project mytestproject = new Mksinterface_mksitem_project(connection_, 
				MKSINTERFACE_TESTING_PROJECT);
		ArrayList<Mksinterface_mksitem_developmentOrder> myTestDevelopmentOrders = mytestproject.getAllDevelopmentOrders();
		
		ArrayList<Integer> myDevelopmentOrdersIDarray = new ArrayList<Integer>();
		for (int i=0; i < myTestDevelopmentOrders.size(); i++)
			myDevelopmentOrdersIDarray.add(myTestDevelopmentOrders.get(i).getId());
		
		for(int i=0; i < MKSINTERFACE_TESTING_PROJECT_ANY_DEVELOPMENT_ORDERS.length; i++)
		{
			assertTrue("Tests if the testarray values are contained in the result development Orders",myDevelopmentOrdersIDarray.contains(
					MKSINTERFACE_TESTING_PROJECT_ANY_DEVELOPMENT_ORDERS[i]));
		}
		
	}
	
	/**
	 * Tests if any of the tasks from the releases of 
	 * the project result with this method
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 */
	@Test
	public void testGetTasks() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Mksinterface_mksitem_project mytestproject = new Mksinterface_mksitem_project(connection_, 
				MKSINTERFACE_TESTING_PROJECT);
		ArrayList<Mksinterface_mksitem_task> myTestTasks = mytestproject.getAllTasks();
		
		ArrayList<Integer> myTaskArray = new ArrayList<Integer>();
		for (int i=0; i < myTestTasks.size(); i++)
			myTaskArray.add(myTestTasks.get(i).getId());
		
		for(int i=0; i < MKSINTERFACE_TESTING_PROJECT_ANY_TASKS.length; i++)
		{
			assertTrue("Tests if the testarray values are contained in the result tasks",myTaskArray.contains(
					MKSINTERFACE_TESTING_PROJECT_ANY_TASKS[i]));
		}
	}
	
	/**
	 * Tests if any of the requirments from the releases of 
	 * the project result with this method
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 */
	@Test
	public void testGetRequirements() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Mksinterface_mksitem_project mytestproject = new Mksinterface_mksitem_project(connection_, 
				MKSINTERFACE_TESTING_PROJECT);
		ArrayList<Mksinterface_mksitem_requirement> myTestRequirements = mytestproject.getAllRequirements();
		
		ArrayList<Integer> myRequirementArray = new ArrayList<Integer>();
		for (int i=0; i < myTestRequirements.size(); i++)
			myRequirementArray.add(myTestRequirements.get(i).getId());
		
		for(int i=0; i < MKSINTERFACE_TESTING_PROJECT_ANY_REQUIREMENTS.length; i++)
		{
			assertTrue("Tests if the testarray values are contained in the result tasks",myRequirementArray.contains(
					MKSINTERFACE_TESTING_PROJECT_ANY_REQUIREMENTS[i]));
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
