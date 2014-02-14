package mksinterface.query.testing;

import static org.junit.Assert.*;

import java.util.ArrayList;


import mksinterface.query.src.Mksinterface_query_option;

import org.junit.Test;

/**
 * Tests the Mksinterface_testing_query_option class
 * @author ckaltenboeck
 *
 */
public class Mksinterface_testing_query_option {

	
	/**
	 * Tests an Option with a normal String value
	 */
	@Test
	public void normalString() 
	{
		String testItemtype = "Project";
		String testField = "Status";
		String testValue = "Done";
		Mksinterface_query_option option = new Mksinterface_query_option(testItemtype, testField, testValue);
		assertTrue("Test String option field", option.getField().equals(testField) );
		assertTrue("Test String option itemtype", option.getItemtype().equals(testItemtype) );
		assertTrue("Test String option value", option.getValue().equals(testValue) );
	}
	
	/**
	 * Tests an Option with a calendar value
	 */
	@Test
	public void calendarOption() 
	{
		String testItemtype = "Project";
		String testField = "Created Date";
		int day = 9;
		int month = 10;
		int year = 2009;
		int hour = 9;
		int min = 7;
		int sec = 37;
		String period = "AM";
		String testValue = "Oct 9, 2009 9:7:37 AM";
		Mksinterface_query_option option = new Mksinterface_query_option(testItemtype, testField, year, month, day, hour, min, sec, period );
		assertTrue("Test String option field", option.getField().equals(testField) );
		assertTrue("Test String option itemtype", option.getItemtype().equals(testItemtype) );
		assertTrue("Test String option value", option.getValue().equals(testValue) );
	}
	
	/**
	 * Tests an Option with a calendar value
	 */
	@Test
	public void calendarOption2() 
	{
		String testItemtype = "Project";
		String testField = "Created Date";
		int day = 9;
		int month = 15;
		int year = 2009;
		int hour = 9;
		int min = 7;
		int sec = 37;
		String period = "AM";
		Mksinterface_query_option option = new Mksinterface_query_option(testItemtype, testField, year, month, day, hour, min, sec, period );
		assertTrue("Test String option field", option.getField().equals(testField) );
		assertTrue("Test String option itemtype", option.getItemtype().equals(testItemtype) );
		assertNull("Test String option value has to be null", option.getValue());
	}
	
	/**
	 * Tests an Option with a calendar value
	 */
	@Test
	public void calendarOption3() 
	{
		String testItemtype = "Project";
		String testField = "Created Date";
		int day = 9;
		int month = 10;
		int year = 2009;
		int hour = 9;
		int min = 7;
		int sec = 37;
		String period = "KM";
		Mksinterface_query_option option = new Mksinterface_query_option(testItemtype, testField, year, month, day, hour, min, sec, period );
		assertTrue("Test String option field", option.getField().equals(testField) );
		assertTrue("Test String option itemtype", option.getItemtype().equals(testItemtype) );
		assertNull("Test String option value has to be null", option.getValue());
	}
	/**
	 * Tests an Option with a calendar value
	 */
	@Test
	public void calendarOption4() 
	{
		String testItemtype = "Project";
		String testField = "Created Date";
		int day = 31;
		int month = 10;
		int year = 2009;
		int hour = 9;
		int min = 7;
		int sec = 37;
		String period = "KM";
		Mksinterface_query_option option = new Mksinterface_query_option(testItemtype, testField, year, month, day, hour, min, sec, period );
		assertTrue("Test String option field", option.getField().equals(testField) );
		assertTrue("Test String option itemtype", option.getItemtype().equals(testItemtype) );
		assertNull("Test String option value has to be null", option.getValue());
	}
	
	/**
	 * Tests an Option with a calendar value
	 */
	@Test
	public void calendarOption5() 
	{
		String testItemtype = "Project";
		String testField = "Created Date";
		int day = 9;
		int month = 10;
		int year = 2009;
		int hour = 13;
		int min = 7;
		int sec = 37;
		String period = "AM";
		Mksinterface_query_option option = new Mksinterface_query_option(testItemtype, testField, year, month, day, hour, min, sec, period );
		assertTrue("Test String option field", option.getField().equals(testField) );
		assertTrue("Test String option itemtype", option.getItemtype().equals(testItemtype) );
		assertNull("Test String option value has to be null", option.getValue());
	}
	
	
	/**
	 * Tests an Option with a boolean value
	 */
	@Test
	public void booleanValue()
	{
		String testItemtype = "Project";
		String testField = "isOkay";
		String testValue = "true";
		boolean boolValue = true;
		Mksinterface_query_option option = new Mksinterface_query_option(testItemtype, testField, boolValue);
		assertTrue("Test String option field", option.getField().equals(testField) );
		assertTrue("Test String option itemtype", option.getItemtype().equals(testItemtype) );
		assertTrue("Test String option value", option.getValue().equals(testValue) );
	}
	
	/**
	 * Tests an Option with a double value
	 */
	@Test
	public void doubleTest()
	{
		String testItemtype = "Project";
		String testField = "doubleValue";
		String testValue = "2.0";
		double boolValue = 2.0;
		Mksinterface_query_option option = new Mksinterface_query_option(testItemtype, testField, boolValue);
		assertTrue("Test String option field", option.getField().equals(testField) );
		assertTrue("Test String option itemtype", option.getItemtype().equals(testItemtype) );
		assertTrue("Test String option value", option.getValue().equals(testValue) );
	}
	
	@Test
	public void ListTest()
	{

		String testItemtype = "Project";
		String testField = "listValue";
		String testValue = "123, 567, 789, 543";
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(123);
		list.add(567);
		list.add(789);
		list.add(543);
		Mksinterface_query_option option = new Mksinterface_query_option(testItemtype, testField, list);
		assertTrue("Test String option field", option.getField().equals(testField) );
		assertTrue("Test String option itemtype", option.getItemtype().equals(testItemtype) );
		assertTrue("Test String option value", option.getValue().equals(testValue) );
	}
	
	@Test
	public void ListTest2()
	{

		String testItemtype = "Project";
		String testField = "listValue";
		String testValue = "";
		ArrayList<Object> list = new ArrayList<Object>();
		Mksinterface_query_option option = new Mksinterface_query_option(testItemtype, testField, list);
		assertTrue("Test String option field", option.getField().equals(testField) );
		assertTrue("Test String option itemtype", option.getItemtype().equals(testItemtype) );
		assertTrue("Test String option value", option.getValue().equals(testValue) );
	}

}
