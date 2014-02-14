package mksinterface.mksitem.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;


import mksinterface.connection.src.Mksinterface_connection;
import mksinterface.connection.src.Mksinterface_connectionException;
import mksinterface.mksitem.src.*;

import org.junit.*;


/**
 * This testing class tests all methods of the Mksinterface_mksitem class. Causing this class is an abstract class I use the Mksinterface_mksitem_release 
 * for testing the methods
 * @author ckaltenboeck
 *
 */
public class Mksinterface_testing_mksitem {

	private static int MKSINTERFACE_TESTING_MKSITEM_RELEASE1 = 110409; //Release from Project A191011
	private static int MKSINTERFACE_TESTING_MKSITEM_PROJECT1 = 110397; //Project A191011
	private static String MKSINTERFACE_TESTING_MKSITEM_RELEASE1_SUMMARY = "PS1 - Initial Bench Software";
	
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
	 * Tests creating an release item without exception
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 */
	@Test
	public void testConstructor() throws Mksinterface_connectionException, Mksinterface_itemException
	{
		new Mksinterface_mksitem_release(connection_, MKSINTERFACE_TESTING_MKSITEM_RELEASE1);
	}
	
	/**
	 * Tests if an exception occurs if the id has not the correct type
	 * @throws Mksinterface_itemException
	 * @throws Mksinterface_connectionException 
	 */
	@Test(expected=Mksinterface_itemException.class)
	public void testConstructorWithFalseId() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		new Mksinterface_mksitem_release(connection_,MKSINTERFACE_TESTING_MKSITEM_PROJECT1);
	}
	
	/**
	 * Tests if the getField method retrieves the correct Summary
	 * @throws Mksinterface_itemException
	 * @throws Mksinterface_connectionException 
	 */
	@Test
	public void testgetFieldMethod_1() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Mksinterface_mksitem_release irelease = new Mksinterface_mksitem_release(connection_, MKSINTERFACE_TESTING_MKSITEM_RELEASE1);
		Mksinterface_mksfield field = irelease.getField("Summary");
		assertEquals("Checks if getField is correct", field.getStringValue(), MKSINTERFACE_TESTING_MKSITEM_RELEASE1_SUMMARY);
		//Checking a second time for the hashMap --> only one access to integrity
		Mksinterface_mksfield field2 = irelease.getField("Summary");
		assertEquals("Checks if getField is correct", field2.getStringValue(), MKSINTERFACE_TESTING_MKSITEM_RELEASE1_SUMMARY);
	}
	
	/**
	 * Testing if an exception occurs while trying to get an impossible Field
	 * @throws Mksinterface_itemException
	 * @throws Mksinterface_connectionException 
	 */
	@Test(expected=Mksinterface_itemException.class)
	public void testgetFieldWithException_1() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Mksinterface_mksitem_release irelease = new Mksinterface_mksitem_release(connection_, MKSINTERFACE_TESTING_MKSITEM_RELEASE1);
		irelease.getField("TestField");
	}
	
	/**
	 * Testing the getFields method 
	 * @throws Mksinterface_itemException
	 * @throws Mksinterface_connectionException 
	 */
	@Test
	public void testgetFields_1() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Mksinterface_mksitem_release irelease = new Mksinterface_mksitem_release(connection_, MKSINTERFACE_TESTING_MKSITEM_RELEASE1);
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("ID");
		fields.add("Type");
		fields.add("Summary");
		ArrayList<Mksinterface_mksfield> mksfields = irelease.getFields(fields);
		assertEquals("Check if ID is correct", mksfields.get(0).getIntValue(),MKSINTERFACE_TESTING_MKSITEM_RELEASE1);
		assertEquals("Check if Type is correct", mksfields.get(1).getStringValue(),Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE);
		assertEquals("Check if Summary is correct", mksfields.get(2).getStringValue(), MKSINTERFACE_TESTING_MKSITEM_RELEASE1_SUMMARY);
	}
	
	/**
	 * Tests if an exception occurs while getting a false field
	 * @throws Mksinterface_itemException
	 * @throws Mksinterface_connectionException 
	 */
	@Test (expected=Mksinterface_itemException.class)
	public void testgetFieldsWithException_1() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Mksinterface_mksitem_release irelease = new Mksinterface_mksitem_release(connection_, MKSINTERFACE_TESTING_MKSITEM_RELEASE1);
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("ID");
		fields.add("Type");
		fields.add("Testfield");
		fields.add("Summary");
		irelease.getFields(fields);
	}
	
	/**
	 * Checks the easy getID Method
	 * @throws Mksinterface_itemException
	 * @throws Mksinterface_connectionException 
	 */
	@Test
	public void testgetID() throws Mksinterface_itemException, Mksinterface_connectionException 
	{
		Mksinterface_mksitem_release irelease = new Mksinterface_mksitem_release(connection_,MKSINTERFACE_TESTING_MKSITEM_RELEASE1);
		assertEquals("Check if ID is correct!", MKSINTERFACE_TESTING_MKSITEM_RELEASE1, irelease.getId());
	}
	/**
	 * Checks the easy getType Method
	 * @throws Mksinterface_itemException
	 * @throws Mksinterface_connectionException 
	 */
	@Test
	public void testgetType() throws Mksinterface_itemException, Mksinterface_connectionException 
	{
		Mksinterface_mksitem_release irelease = new Mksinterface_mksitem_release(connection_,MKSINTERFACE_TESTING_MKSITEM_RELEASE1);
		assertEquals("Check if ID Type is correct", Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE, irelease.getType());
	}
	
	/**
	 * Check the getProject Method
	 * @throws Mksinterface_itemException
	 */
	@Test
	public void testgetProject() throws Mksinterface_itemException 
	{
		//TODO implement if standard things work fine
	}
	
	
	/**
	 * Adds senseless fields to the item and checks them with getField 
	 * (getField first if there is an entry in the hash map then searches in Integrity)
	 * ATTENTION: Do not add senseless fields if not testing the class!!
	 * @throws Mksinterface_connectionException 
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_fieldException 
	 */
	@Test
	public void testAddFields() throws Mksinterface_itemException, Mksinterface_connectionException, Mksinterface_fieldException
	{
		Mksinterface_mksitem_release irelease = new Mksinterface_mksitem_release(connection_, MKSINTERFACE_TESTING_MKSITEM_RELEASE1);
		HashMap<String, Mksinterface_mksfield> fieldMap = new HashMap<String, Mksinterface_mksfield>();
		Mksinterface_mksfield field1 = new Mksinterface_mksfield("TestValue1","test1");
		Mksinterface_mksfield field2 = new Mksinterface_mksfield(123,"test2");
		Mksinterface_mksfield field3 = new Mksinterface_mksfield(123,"test3");
		fieldMap.put("test1", field1);
		fieldMap.put("test2", field2);
		fieldMap.put("test3", field3);
		irelease.addFields(fieldMap);
		assertEquals("Check first field", irelease.getField("test2"), field2);
		assertEquals("Check second field", irelease.getField("test3"), field3);
		assertEquals("Check third field", irelease.getField("test1"), field1);	
	}
	
	@Test
	public void testGetProject() throws Mksinterface_itemException, Mksinterface_connectionException
	{
		Mksinterface_mksitem_release myTestRelease = new Mksinterface_mksitem_release(connection_, MKSINTERFACE_TESTING_MKSITEM_RELEASE1);
		assertEquals("Project ID musst be correct", myTestRelease.getProject().getId(),MKSINTERFACE_TESTING_MKSITEM_PROJECT1);
	}
}
