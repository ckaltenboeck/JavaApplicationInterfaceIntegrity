package mksinterface.connection.src;

/**
 * Exception which occurs if there is a problem with the connection to mks
 * @author ckaltenboeck
 *
 */
public class Mksinterface_connectionException  extends Exception {
	private static final long serialVersionUID = 1L;
	public static final String MKSINTERFACE_CONNECTION_EXCEPTION_CONNECTION_FAILED = "Not able to connect to MKS: ";
	public static final String MKSINTERFACE_CONNECTION_EXCEPTION_NO_DISCONNECTION = "Can not disconnect from MKS: ";
	public static final String MKSINTERFACE_CONNECTION_EXCEPTION_NOT_CONNECTED = "Not connected to MKS";
	
	/**
	 * Exception Constructor leads the message to the upper class
	 * @param message	Message to be sent
	 */
	public Mksinterface_connectionException(String message)
	{
		super(message);
	}
}
