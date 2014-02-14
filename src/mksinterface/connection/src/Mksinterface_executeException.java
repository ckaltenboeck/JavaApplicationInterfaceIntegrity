package mksinterface.connection.src;

/**
 * Exception which occurs if there is a problem with executing the command
 * @author ckaltenboeck
 *
 */
public class Mksinterface_executeException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public static final String MKSINTERFACE_EXECUTE_EXCEPTION_EXECUTION_FAILED = "Executing the command failed";
	/**
	 * Exception Constructor leads the message to the upper class
	 * @param message	message to be sent
	 */
	public Mksinterface_executeException(String message)
	{
		super(message);
	}
}
