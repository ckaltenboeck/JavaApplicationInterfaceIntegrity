package mksinterface.query.src;

/**
 * Exception which occurs if the execution of the query fails
 * @author ckaltenboeck
 *
 */
public class Mksinterface_executeQueryException  extends Exception {

	
	private static final long serialVersionUID = 1L;

	/**
	 * Exception Constructor leads the message to the upper class
	 * @param message	message
	 */
	public Mksinterface_executeQueryException(String message)
	{
		super(message);
	}
}
