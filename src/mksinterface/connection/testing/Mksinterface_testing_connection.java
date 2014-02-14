package mksinterface.connection.testing;

import static org.junit.Assert.*;
import mksinterface.connection.src.Mksinterface_connection;
import mksinterface.connection.src.Mksinterface_connectionException;
import mksinterface.connection.src.Mksinterface_executeException;

import org.junit.*;

/**
 * Testing the class "Mksinterface_connection"
 * @author ckaltenboeck
 *
 */
public class Mksinterface_testing_connection {

	/**
	 * Test the connect method
	 * @throws Mksinterface_connectionException 
	 */
	@Test
	public void testConnection() throws Mksinterface_connectionException {
		Mksinterface_connection connection;
		connection = Mksinterface_connection.getInstance();
		assertEquals("must be connected", true, connection.connect());
	}
	
	/**
	 * Test the disconnect method
	 * @throws Mksinterface_connectionException 
	 */
	@Test
	public void testDisconnect() throws Mksinterface_connectionException
	{
		Mksinterface_connection connection;
		connection = Mksinterface_connection.getInstance();
		assertEquals("must be disconnected", false, connection.disconnect());
	}
	
	/**
	 * Tests the state of the connection
	 * @throws Mksinterface_connectionException
	 */
	@Test
	public void testState() throws Mksinterface_connectionException
	{
		Mksinterface_connection connection;
		connection = Mksinterface_connection.getInstance();
		connection.connect();
		assertEquals("is connected", true, connection.getState());
		connection.disconnect();
		assertEquals("is disconnected", false, connection.getState());
	}
	
	
	/**
	 * Tests if an exception occurs when null shall be executed
	 * Other tests for the executeException shall happen on a higher level
	 * @throws Mksinterface_connectionException
	 * @throws Mksinterface_executeException
	 */
	@Test(expected = Mksinterface_executeException.class)
	public void testexecuteException() throws Mksinterface_connectionException, Mksinterface_executeException
	{
		Mksinterface_connection connection;
		connection = Mksinterface_connection.getInstance();
		connection.connect();
		connection.execute(null);
	}
}
