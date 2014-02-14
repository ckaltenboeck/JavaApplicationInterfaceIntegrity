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
 * Tests the Mksinterface_mksitem_developmentOrder class
 * @author ckaltenboeck
 *
 */
public class Mksinterface_testing_mksitem_developmentOrder {

	private int MKSINTERFACE_TESTING_MKSITEM_DEVELOPTMENT_ORDER = 116919;
	private int[] MKSINTERFACE_TESTING_MKSITEM_DEVELOPMENT_ORDER_TASKS = {117817, 117925, 117926, 117927, 117928, 138905} ;
	private int[] MKSINTERFACE_TESTING_MKSITEM_DEVELOPMENT_ORDER_ANY_REQUIREMENTS = {151516, 151520, 151522, 118654, 118658};
	private int MKSINTERFACE_TESTING_MKSITEM_DEVELOPTMENT_ORDER_RELEASE = 110409;
	
	
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
	 * Checks the getRelease method
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_executeException 
	 */
	@Test
	public void testGetRelease() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeException {
		Mksinterface_mksitem_developmentOrder mydevelopmentOrder = new Mksinterface_mksitem_developmentOrder(connection_, 
				MKSINTERFACE_TESTING_MKSITEM_DEVELOPTMENT_ORDER);
		Mksinterface_mksitem_release myTestRelease = mydevelopmentOrder.getRelease();
		assertEquals("Tests if release is correct", 
				MKSINTERFACE_TESTING_MKSITEM_DEVELOPTMENT_ORDER_RELEASE, myTestRelease.getId());
	}
	
	/**
	 * Tests the getTasks method
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_executeException 
	 */
	@Test
	public void testGetTasks() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeException {
		Mksinterface_mksitem_developmentOrder mydevelopmentOrder = new Mksinterface_mksitem_developmentOrder(connection_, 
				MKSINTERFACE_TESTING_MKSITEM_DEVELOPTMENT_ORDER);
		ArrayList<Mksinterface_mksitem_task> myTestTasks = mydevelopmentOrder.getAllTasks();
		for ( int i=0; i< myTestTasks.size(); i++)
		{
			assertTrue("Tests if the Requirment is in the TestArray",checkIfInArray(myTestTasks.get(i).getId(),
					MKSINTERFACE_TESTING_MKSITEM_DEVELOPMENT_ORDER_TASKS));
		}
	}
	
	/**
	 * Tests the getAllRequirements method
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_executeException 
	 */
	@Test
	public void testGetRequirements() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_executeException {
		Mksinterface_mksitem_developmentOrder mydevelopmentOrder = new Mksinterface_mksitem_developmentOrder(connection_, 
				MKSINTERFACE_TESTING_MKSITEM_DEVELOPTMENT_ORDER);
		ArrayList<Mksinterface_mksitem_requirement> myTestRequirements = mydevelopmentOrder.getAllRequirements();
		
		ArrayList<Integer> myRequirementIDarray = new ArrayList<Integer>();
		for (int i=0; i < myTestRequirements.size(); i++)
			myRequirementIDarray.add(myTestRequirements.get(i).getId());
		
		for(int i=0; i < MKSINTERFACE_TESTING_MKSITEM_DEVELOPMENT_ORDER_ANY_REQUIREMENTS.length; i++)
		{
			assertTrue("Tests if the testarray values are contained in the result requirement",myRequirementIDarray.contains(
					MKSINTERFACE_TESTING_MKSITEM_DEVELOPMENT_ORDER_ANY_REQUIREMENTS[i]));
		}
	}
	
	
	/**
	 * Tests the given parameter
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
