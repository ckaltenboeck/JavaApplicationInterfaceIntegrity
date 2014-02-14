package mksinterface.mksitem.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;


import mksinterface.connection.src.Mksinterface_connection;
import mksinterface.connection.src.Mksinterface_connectionException;
import mksinterface.connection.src.Mksinterface_executeException;
import mksinterface.mksitem.src.Mksinterface_fieldException;
import mksinterface.mksitem.src.Mksinterface_mksfield;

import org.junit.*;

import com.mks.api.Command;
import com.mks.api.Option;
import com.mks.api.response.APIException;
import com.mks.api.response.Field;
import com.mks.api.response.Response;
import com.mks.api.response.WorkItem;
import com.mks.api.response.WorkItemIterator;

/**
 * Tests the Mksinterface_mksfield class
 * @author ckaltenboeck
 *
 */
public class Mksinterface_testing_mksfield {
	
	private static Mksinterface_connection connection_ = Mksinterface_connection.getInstance();
	private static ArrayList<Field> fields_ = new ArrayList<Field>();
	
	private static final String MKSINTERFACE_TESTING_MKSFIELD_PROJECT = "/Controls/3669/A191011";
	private static final int MKSINTERFACE_TESTING_MKSFIELD_ID = 110397;
	private static Calendar Mksinterface_testing_mksfield_created_date= Calendar.getInstance();
	private static final String MKSINTERFACE_TESTING_MKSFIELD_SUMMARY = "DAE 6AT Software";
	

	/**
	 * Initializes the testcase 
	 * connecting to Integrity, getting some fields
	 * @throws Mksinterface_connectionException 
	 * @throws APIException 
	 */
	@BeforeClass
	public static void init() throws Mksinterface_connectionException {
		connection_.connect();
		Command cmd = new Command(Command.IM,Mksinterface_connection.MKSINTERFACE_CONNECTION_CMD_ISSUES);
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_FIELDS,"ID,Created Date,Summary,Releases,Type"));
		cmd.addOption(new Option(Mksinterface_connection.MKSINTERFACE_CONNECTION_OPTION_QUERYDEFINITION,"((field[Project]=\""+MKSINTERFACE_TESTING_MKSFIELD_PROJECT+"\") and (field[Type]=\"Project\"))"));
		try {
			Response rs = connection_.execute(cmd);
			WorkItemIterator workItemIterator = rs.getWorkItems();
			WorkItem workItem = workItemIterator.next();
			Iterator<Field> fieldIterator = workItem.getFields();
			while(fieldIterator.hasNext())
				fields_.add(fieldIterator.next());	
		} catch (Mksinterface_executeException e) {
			fail("Initialization faild causing ConnectionException or ExecutionException");
		} catch (APIException e) {
			fail("Initialization faild causing Response failure");
		}
	}
	
	@AfterClass
	public static void deinit() throws Mksinterface_connectionException 
	{
		connection_.disconnect();
	}
	
	/**
	 * Tests if the first entry in the field array is the correct ID
	 * @throws Mksinterface_fieldException 
	 */
	@Test
	public void testIntegerField() throws Mksinterface_fieldException
	{
		Mksinterface_mksfield mksfield = new Mksinterface_mksfield(fields_.get(0));
		assertEquals("Correct ID number of the field",mksfield.getIntValue(),MKSINTERFACE_TESTING_MKSFIELD_ID);
		assertEquals("Correct ID",mksfield.getName(),"ID");
		assertNull("String value had to be null",mksfield.getStringValue());
	}
	
	/**
	 * Test the String field
	 * @throws Mksinterface_fieldException
	 */
	@Test
	public void testStringField() throws Mksinterface_fieldException
	{
		Mksinterface_mksfield mksfield = new Mksinterface_mksfield(fields_.get(2));
		assertEquals("Field name correct",mksfield.getName(), "Summary" );
		assertEquals("Field value is correct",MKSINTERFACE_TESTING_MKSFIELD_SUMMARY, mksfield.getStringValue());
	}
	
	/**
	 * Test the calendar field
	 * @throws Mksinterface_fieldException
	 */
	@Test
	public void testCalendarField() throws Mksinterface_fieldException
	{
		Mksinterface_mksfield mksfield = new Mksinterface_mksfield(fields_.get(1));
		Calendar Cal = Calendar.getInstance();
	    Cal.set(2012, 1, 20, 13, 25, 50);
	    Calendar MksCal = mksfield.getCalendarValue();
		assertEquals("Test day",Cal.get(Calendar.DAY_OF_MONTH), MksCal.get(Calendar.DAY_OF_MONTH));
		assertEquals("Test Year",Cal.get(Calendar.YEAR), MksCal.get(Calendar.YEAR));
		assertEquals("Test hour",Cal.get(Calendar.HOUR), MksCal.get(Calendar.HOUR));
		assertEquals("Test minute",Cal.get(Calendar.MINUTE), MksCal.get(Calendar.MINUTE));
		assertEquals("Test second",Cal.get(Calendar.SECOND), MksCal.get(Calendar.SECOND));
		assertEquals("Test month",Cal.get(Calendar.MONTH), MksCal.get(Calendar.MONTH));
		assertTrue("Test the field name",mksfield.getName().equals("Created Date"));
	}
	
	/**
	 * Tests the ListType field
	 * @throws Mksinterface_fieldException
	 */
	@Test
	public void testListField() throws Mksinterface_fieldException
	{
		Mksinterface_mksfield mksfield = new Mksinterface_mksfield(fields_.get(3));
		ArrayList<Integer> releases = new ArrayList<Integer>();
		releases.add(110409);
		releases.add(123884);
		releases.add(186044);
		releases.add(186045);
		releases.add(186046);
		releases.add(186049);
		releases.add(186052);
		assertArrayEquals("List has to be correct", releases.toArray(), mksfield.getListValue().toArray());
	}
	
	/**
	 * Tests the ItemType field of Integrity as String
	 * @throws Mksinterface_fieldException
	 */
	@Test
	public void testItemField() throws Mksinterface_fieldException 
	{
		Mksinterface_mksfield mksfield = new Mksinterface_mksfield(fields_.get(4));
		assertEquals("Test ItemField as String", mksfield.getStringValue(), "Project");
	}
	/**
	 * Test easy Integer Field
	 * @throws Mksinterface_fieldException
	 */
	@Test
	public void testEasyIntegerField() throws Mksinterface_fieldException
	{
		Mksinterface_mksfield mksfield = new Mksinterface_mksfield(123, "ID");
		assertEquals("Must be the same ID",mksfield.getIntValue(),123);
		assertTrue("Must be the correct Name", mksfield.getName().equals("ID"));
		assertNull("Must be null", mksfield.getStringValue());
	}
	
	/**
	 * Test easy String Field
	 * @throws Mksinterface_fieldException
	 */
	@Test
	public void testEasyStringField() throws Mksinterface_fieldException
	{
		Mksinterface_mksfield mksfield = new Mksinterface_mksfield("Test Summary", "Summary");
		assertTrue("Must be the same ID",mksfield.getStringValue().equals("Test Summary"));
		assertTrue("Must be the correct Name", mksfield.getName().equals("Summary"));
	}
	
	/**
	 * Test easy double Field
	 * @throws Mksinterface_fieldException
	 */
	@Test
	public void testEasydoubleField() throws Mksinterface_fieldException
	{
		Mksinterface_mksfield mksfield = new Mksinterface_mksfield(2.0, "TestDouble");
		assertTrue("Must be the correct Name", mksfield.getName().equals("TestDouble"));
		assertEquals("Correct Double value with delta",2.0, mksfield.getDoubleValue(),0.0000000001);
	}
	
	/**
	 * Test easy calendar Field
	 * @throws Mksinterface_fieldException
	 */
	@Test
	public void testEasycalendarField() throws Mksinterface_fieldException
	{
		Mksinterface_mksfield mksfield = new Mksinterface_mksfield(Mksinterface_testing_mksfield_created_date, "Created Date");
		assertTrue("Must be the correct Name", mksfield.getName().equals("Created Date"));
		assertEquals("Correct Calendar value",Mksinterface_testing_mksfield_created_date, mksfield.getCalendarValue());
	}
	
	/**
	 * Test easy list Field
	 * @throws Mksinterface_fieldException
	 */
	@Test
	public void testEasylistField() throws Mksinterface_fieldException
	{
		ArrayList<Object> IDlist = new ArrayList<Object>();
		IDlist.add(123);
		IDlist.add(245);
		IDlist.add(346);
		Mksinterface_mksfield mksfield = new Mksinterface_mksfield(IDlist, "Development Orders");
		assertTrue("Must be the correct Name", mksfield.getName().equals("Development Orders"));
		assertEquals("Correct Calendar value",IDlist, mksfield.getListValue());
	}
	
	/**
	 * Test easy boolean Field
	 * @throws Mksinterface_fieldException
	 */
	@Test
	public void testEasybooleanField() throws Mksinterface_fieldException
	{
		Mksinterface_mksfield mksfield = new Mksinterface_mksfield(true, "Some boolean field");
		assertTrue("Must be the correct Name", mksfield.getName().equals("Some boolean field"));
		assertEquals("Correct Calendar value",true, mksfield.getBooleanValue());
	}
	
	/**
	 * Test easy long Field
	 * @throws Mksinterface_fieldException
	 */
	@Test
	public void testEasylongField() throws Mksinterface_fieldException
	{
		long testvalue = 67000000000L;
		Mksinterface_mksfield mksfield = new Mksinterface_mksfield(testvalue, "Some long field");
		assertTrue("Must be the correct Name", mksfield.getName().equals("Some long field"));
		assertEquals("Correct Long",testvalue, mksfield.getLongValue());
	}
	
	
	
	
	
	//Test Mksinterface_fieldException.............................................................................
	
	/**
	 * Testing the exception if input is null
	 * @throws Mksinterface_fieldException 
	 */
	@Test(expected = Mksinterface_fieldException.class)
	public void testFieldExceptionNull() throws Mksinterface_fieldException
	{
		new Mksinterface_mksfield(null);
	}
}
