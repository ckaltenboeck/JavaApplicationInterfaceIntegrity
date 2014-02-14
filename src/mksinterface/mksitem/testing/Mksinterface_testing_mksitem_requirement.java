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
 * Tests the Mksinterface_mksitem_requirement class
 * @author ckaltenboeck
 *
 */
public class Mksinterface_testing_mksitem_requirement {

	
	private int MKSINTERFACE_TESTING_MKSITEM_REQUIREMENT_REQUIRMENT = 270988;
	private int[] MKSINTERFACE_TESTING_MKSITEM_REQUIREMENT_TASKS = {270931,270932,285541};
	private int MKSINTERFACE_TESTING_MKSITEM_DEVELOPMENT_ORDER = 270754;
	private int MKSINTERFACE_TESTING_MKSITEM_RELEASE = 123884;
	
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
	 * Checks the getTasks method
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_executeException 
	 */
	@Test
	public void testGetTasks() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeException {
		Mksinterface_mksitem_requirement myRequirement = new Mksinterface_mksitem_requirement(connection_, MKSINTERFACE_TESTING_MKSITEM_REQUIREMENT_REQUIRMENT);
		ArrayList<Mksinterface_mksitem_task> myTestTasks = myRequirement.getAllTasks();
		for ( int i=0; i< myTestTasks.size(); i++)
		{
			assertTrue("Tests if the Requirment is in the TestArray",checkIfInArray(myTestTasks.get(i).getId(),
					MKSINTERFACE_TESTING_MKSITEM_REQUIREMENT_TASKS));
		}
	}
	
	/**
	 * Tests the getDevelopmentOrders method
	 * @throws Mksinterface_itemException
	 * @throws Mksinterface_connectionException
	 */
	@Test
	public void testGetDevelopmentOrders() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Mksinterface_mksitem_requirement myRequirement = new Mksinterface_mksitem_requirement(connection_, MKSINTERFACE_TESTING_MKSITEM_REQUIREMENT_REQUIRMENT);
		ArrayList<Mksinterface_mksitem_developmentOrder> myTestDevelopmentOrder = myRequirement.getAllDevelopmentOrders();
		assertEquals("Size of Array is correct 1",myTestDevelopmentOrder.size(),1);
		assertEquals("Development Order is correct", myTestDevelopmentOrder.get(0).getId(), MKSINTERFACE_TESTING_MKSITEM_DEVELOPMENT_ORDER);
	}
	
	/**
	 * Tests the getRelease method
	 * @throws Mksinterface_itemException
	 * @throws Mksinterface_connectionException
	 */
	@Test
	public void testGetRelease() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Mksinterface_mksitem_requirement myRequirement = new Mksinterface_mksitem_requirement(connection_, MKSINTERFACE_TESTING_MKSITEM_REQUIREMENT_REQUIRMENT);
		ArrayList<Mksinterface_mksitem_release> myTestRelease = myRequirement.getAllReleases();
		assertEquals("Size of Array is correct 1",myTestRelease.size(),1);
		assertEquals("Relesae is correct", myTestRelease.get(0).getId(), MKSINTERFACE_TESTING_MKSITEM_RELEASE);
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
