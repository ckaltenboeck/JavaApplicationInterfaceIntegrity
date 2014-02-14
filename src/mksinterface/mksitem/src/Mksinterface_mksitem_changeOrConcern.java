package mksinterface.mksitem.src;

import mksinterface.connection.src.Mksinterface_connection;
import mksinterface.connection.src.Mksinterface_connectionException;

/**
 * Item Class which represents a Change or concern item. 
 * INFO:
 * This class only represents the item, there are at the moment no possibilities to 
 * get other items with methods. Because there is no information how this item is in 
 * relationship with other items
 * @author ckaltenboeck
 *
 */
public class Mksinterface_mksitem_changeOrConcern  extends Mksinterface_mksitem{

	/**
	 * Constructor for change or concern item
	 * @param connect	connection object
	 * @param id		id of the item
	 * @throws Mksinterface_itemException 
	 * @throws Mksinterface_connectionException 
	 */
	public Mksinterface_mksitem_changeOrConcern(
			Mksinterface_connection connect, int id) throws Mksinterface_itemException, Mksinterface_connectionException {
		super(connect, id, Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_CHANGE_OR_CONCERN);
	}
}
