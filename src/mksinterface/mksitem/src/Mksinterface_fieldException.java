package mksinterface.mksitem.src;

/**
 * Exception which occures if a field update of an item failed
 * @author ckaltenboeck
 *
 */
public class Mksinterface_fieldException extends Exception {

	private static final long serialVersionUID = 1L;

	public static String MKSINTERFACE_FIELD_EXCEPTION_FIELD_NULL = "Field is null!";
	public static String MKSINTERFACE_FIELD_EXCEPTION_FIELDTYPE_NULL = "Field type is null!";
	public static String MKSINTERFACE_FIELD_EXCEPTION_PARAMETER_NULL = "One or more Parameters are null!";
	public static String MKSINTERFACE_FIELD_EXCEPTION_TYPE_NOT_AVAILABLE = "Type is not available!";
	
	/**
	 * Exception Constructor leads the message to the upper class
	 * @param message	message
	 */
	public Mksinterface_fieldException(String message)
	{
		super(message);
	}
}
