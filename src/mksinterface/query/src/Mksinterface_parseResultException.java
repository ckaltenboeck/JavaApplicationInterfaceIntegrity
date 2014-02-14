package mksinterface.query.src;

/**
 * Exception occurs if parsing the Response to a result failed
 * @author ckaltenboeck
 *
 */
public class Mksinterface_parseResultException extends Exception{

	private static final long serialVersionUID = 1L;

	public Mksinterface_parseResultException(String message)
	{
		super(message);
	}
}
