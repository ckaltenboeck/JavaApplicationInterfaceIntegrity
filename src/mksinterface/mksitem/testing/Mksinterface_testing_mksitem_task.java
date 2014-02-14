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
 * Tests the Mksinterface_mksitem_Task class
 * The tests for the methods "getDevelopmentOrder" and "getAllRequirements" 
 * are written at first and implemented.
 * For the test of "getRelease" other items are necessary for testing and implementing
 * @author ckaltenboeck
 *
 */
public class Mksinterface_testing_mksitem_task {

	private int MKSINTERFACE_TESTING_MKSITEM_TASK_TASK1 = 117817;
	private int MKSINTERFACE_TESTING_MKSITEM_TASK_TASK1_DEVELOPMENT_ORDER = 116919;
	private int MKSINTERFACE_TESTING_MKSITEM_TASK_TASK1_RELEASE = 110409;
	private int[] MKSINTERFACE_TESTING_MKSITEM_TASK_TASK1_REQUIREMENTS = {118619, 118710};
	
	private int MKSINTERFACE_TESTING_MKSITEM_TASK_TASK2 = 200896;
	private int MKSINTERFACE_TESTING_MKSITEM_TASK_TASK2_DEVELOPMENT_ORDER = 204249;
	private int MKSINTERFACE_TESTING_MKSITEM_TASK_TASK2_RELEASE = 123884;
	private int[] MKSINTERFACE_TESTING_MKSITEM_TASK_TASK2_REQUIREMENTS = {};
	
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
	 * Tests if getDevelopmentOrder() method returns correct developmentOrder item
	 * @throws Mksinterface_itemException
	 * @throws Mksinterface_connectionException
	 * @throws Mksinterface_executeException 
	 */
	@Test
	public void testGetDevelopmentOrderMethod1() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeException
	{
		Mksinterface_mksitem_task myTestTask = new Mksinterface_mksitem_task(connection_, MKSINTERFACE_TESTING_MKSITEM_TASK_TASK1);
		Mksinterface_mksitem_developmentOrder myTestDevelopmentOrder = myTestTask.getDevelopmentOrder();
		assertEquals("Tests if development Order of task: "+myTestTask.getId()+" is correct", 
				MKSINTERFACE_TESTING_MKSITEM_TASK_TASK1_DEVELOPMENT_ORDER, myTestDevelopmentOrder.getId());
	}
	
	/**
	 * Tests if getDevelopmentOrder() method returns correct developmentOrder item
	 * @throws Mksinterface_itemException
	 * @throws Mksinterface_connectionException
	 * @throws Mksinterface_executeException 
	 */
	@Test
	public void testGetDevelopmentOrderMethod2() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeException
	{
		Mksinterface_mksitem_task myTestTask = new Mksinterface_mksitem_task(connection_, MKSINTERFACE_TESTING_MKSITEM_TASK_TASK2);
		Mksinterface_mksitem_developmentOrder myTestDevelopmentOrder = myTestTask.getDevelopmentOrder();
		assertEquals("Tests if development Order of task: "+myTestTask.getId()+" is correct", 
				MKSINTERFACE_TESTING_MKSITEM_TASK_TASK2_DEVELOPMENT_ORDER, myTestDevelopmentOrder.getId());
	}
	
	
	/**
	 * Tests if getAllRequirments() method returns correct Requirements
	 * @throws Mksinterface_itemException
	 * @throws Mksinterface_connectionException
	 * @throws Mksinterface_executeException 
	 */
	@Test
	public void testGetAllRequirements() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeException
	{
		Mksinterface_mksitem_task myTestTask = new Mksinterface_mksitem_task(connection_, MKSINTERFACE_TESTING_MKSITEM_TASK_TASK1);
		ArrayList<Mksinterface_mksitem_requirement> myTestRequirements = myTestTask.getAllRequirements();
		assertEquals("Test if returned Requirment array is same size as test array", myTestRequirements.size(), 
				MKSINTERFACE_TESTING_MKSITEM_TASK_TASK1_REQUIREMENTS.length);
		for ( int i=0; i< myTestRequirements.size(); i++)
		{
			assertTrue("Tests if the Requirment is in the TestArray",checkIfInArray(myTestRequirements.get(i).getId(),
					MKSINTERFACE_TESTING_MKSITEM_TASK_TASK1_REQUIREMENTS));
		}
		
	}
	
	/**
	 * Tests if getAllRequirments() method returns correct zero size array
	 * @throws Mksinterface_itemException
	 * @throws Mksinterface_connectionException
	 * @throws Mksinterface_executeException 
	 */
	@Test
	public void testGetAllRequirements2() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeException
	{
		Mksinterface_mksitem_task myTestTask = new Mksinterface_mksitem_task(connection_, MKSINTERFACE_TESTING_MKSITEM_TASK_TASK2);
		ArrayList<Mksinterface_mksitem_requirement> myTestRequirements = myTestTask.getAllRequirements();
		assertEquals("Tests if returned Requirment array is same size as test array", 
				myTestRequirements.size(),MKSINTERFACE_TESTING_MKSITEM_TASK_TASK2_REQUIREMENTS.length);
	}
	
	/**
	 * Tests the getRelease method
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 */
	@Test
	public void testGetRelease() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Mksinterface_mksitem_task myTestTask = new Mksinterface_mksitem_task(connection_, MKSINTERFACE_TESTING_MKSITEM_TASK_TASK1);
		Mksinterface_mksitem_release myRelease = myTestTask.getRelease();
		assertEquals("Tests if returned release is correct", myRelease.getId(),MKSINTERFACE_TESTING_MKSITEM_TASK_TASK1_RELEASE);
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
