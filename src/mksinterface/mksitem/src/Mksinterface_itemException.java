package mksinterface.mksitem.src;


/**
 * Exception to handle all problems occured while creating and modifying items
 * @author ckaltenboeck
 *
 */
public class Mksinterface_itemException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public static final String MKSINTERFACE_ITEM_EXCEPTION_NO_ITEM = "No item available!";
	public static final String MKSINTERFACE_ITEM_EXCEPTION_NO_FIELD = "No field available!";
	public static final String MKSINTERFACE_ITEM_EXCEPTION_RESPONSE_NOT_CORRECT = "Response of Integrity was not correct!";
	public static final String MKSINTERFACE_ITEM_EXCEPTION_NOT_EXECUTED = "Executing could not be finished!";
	public static final String MKSINTERFACE_ITEM_EXCEPTION_FIELD_NOT_CORRECT = "Field is not correct!";
	public static final String MKSINTERFACE_ITEM_EXCEPTION_FIELD_EMPTY = "Field(s) are/is empty!";
	
	/**
	 * Exception Constructor leads the message to the upper class
	 * @param message	message
	 */
	public Mksinterface_itemException(String message)
	{
		super(message);
	}

}
