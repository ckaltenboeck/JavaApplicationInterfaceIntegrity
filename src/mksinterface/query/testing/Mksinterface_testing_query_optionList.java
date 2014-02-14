package mksinterface.query.testing;

import static org.junit.Assert.*;

import java.util.LinkedList;

import mksinterface.mksitem.src.Mksinterface_mksitem;
import mksinterface.query.src.Mksinterface_executeQueryException;
import mksinterface.query.src.Mksinterface_query_option;
import mksinterface.query.src.Mksinterface_query_optionList;

import org.junit.Test;

/**
 * Tests the Mksinterface_query_optionList class
 * @author ckaltenboeck
 *
 */
public class Mksinterface_testing_query_optionList {

	
	/**
	 * Exception has to occur because of nonsense parameters of the option
	 * @throws Mksinterface_executeQueryException 
	 */
	@Test (expected = Mksinterface_executeQueryException.class)
	public void testEasyMksexecuteQueryExceptionWithFalseParameters() throws Mksinterface_executeQueryException
	{
		Mksinterface_query_option myTestOption = new Mksinterface_query_option("Nonsense Type testing", null, 0.00);
		Mksinterface_query_optionList myTestOptionList = new Mksinterface_query_optionList();
		myTestOptionList.addOptionToList(myTestOption);
	}
	
	/**
	 * Tests if the ranking of the options will be done perfect
	 * @throws Mksinterface_executeQueryException 
	 */
	@Test
	public void testEasyRankingOfOptions() throws Mksinterface_executeQueryException
	{
		Mksinterface_query_option myTestOptionDevelopment
			= new Mksinterface_query_option(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_DEVELOPMENT_ORDER, "Summary", "TestSummary");
		Mksinterface_query_option myTestOptionProject 
			= new Mksinterface_query_option(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_PROJECT, "Type", "Project");
		Mksinterface_query_option myTestOptionTask
			= new Mksinterface_query_option(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_TASK, "State", "Done");
		
		Mksinterface_query_optionList myTestOptionList = new Mksinterface_query_optionList();
		myTestOptionList.addOptionToList(myTestOptionDevelopment);
		myTestOptionList.addOptionToList(myTestOptionProject);
		myTestOptionList.addOptionToList(myTestOptionTask);
		
		LinkedList<Mksinterface_query_option> listToTest = myTestOptionList.getOptions();
		assertEquals("The first option which we get shall be the project option", listToTest.removeLast(), myTestOptionProject);
		assertEquals("The second option which we get shall be the development Order option", listToTest.removeLast(), myTestOptionDevelopment);
		assertEquals("The third option which we get shall be the Task option", listToTest.removeLast(), myTestOptionTask);
		assertTrue("Tests if the List is empty", listToTest.isEmpty());
	}
	
	/**
	 * Test with more than one option for a itemtype
	 * @throws Mksinterface_executeQueryException 
	 */
	@Test
	public void testDifficultRankingOfOptions() throws Mksinterface_executeQueryException
	{
		Mksinterface_query_option myTestOptionDevelopment
			= new Mksinterface_query_option(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_DEVELOPMENT_ORDER, "Summary", "TestSummary");
		Mksinterface_query_option myTestOptionProject 
			= new Mksinterface_query_option(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_PROJECT, "Type", "Project");
		Mksinterface_query_option myTestOptionProject2
			= new Mksinterface_query_option(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_PROJECT, "ID", 1234567);
		Mksinterface_query_option myTestOptionRelease
		= new Mksinterface_query_option(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE, "ID", 73456);
	
		Mksinterface_query_optionList myTestOptionList = new Mksinterface_query_optionList();
		myTestOptionList.addOptionToList(myTestOptionDevelopment);
		myTestOptionList.addOptionToList(myTestOptionProject);
		myTestOptionList.addOptionToList(myTestOptionRelease);
		myTestOptionList.addOptionToList(myTestOptionProject2);
		
		LinkedList<Mksinterface_query_option> listToTest = myTestOptionList.getOptions();
		assertEquals("The first option which we get shall be the project option", listToTest.removeLast().getItemtype(), Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_PROJECT);
		assertEquals("The first option which we get shall be the second project option", listToTest.removeLast().getItemtype(), Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_PROJECT);
		
		assertEquals("The second option which we get shall be the relese option", listToTest.removeLast(), myTestOptionRelease);
		assertEquals("The third option which we get shall be the DevelopmentOrder option", listToTest.removeLast(), myTestOptionDevelopment);
		assertTrue("Tests if the List is empty", listToTest.isEmpty());
	}

}
