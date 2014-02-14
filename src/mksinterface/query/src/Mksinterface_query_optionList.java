package mksinterface.query.src;

import java.util.LinkedList;

import mksinterface.mksitem.src.Mksinterface_mksitem;

/**
 * This class saves all options of the query like a list. 
 * The user or query themself is able to add options to the "list". 
 * INFO: This class is not used for the moment
 *       I need it for a future query implementation
 * @author ckaltenboeck
 *
 */
public class Mksinterface_query_optionList {
	
	LinkedList<Mksinterface_query_option> projectOptionList_;
	LinkedList<Mksinterface_query_option> releaseOptionList_;
	LinkedList<Mksinterface_query_option> developmentOrderOptionList_;
	LinkedList<Mksinterface_query_option> taskOptionList_;
	LinkedList<Mksinterface_query_option> requirementOptionList_;
	LinkedList<Mksinterface_query_option> requirementDocumentOptionList_;
	LinkedList<Mksinterface_query_option> changeOrConcernOptionList_;
	
	/**
	 * Constructor, initializing the class
	 */
	public Mksinterface_query_optionList()
	{
		projectOptionList_ = new LinkedList<Mksinterface_query_option>();
		releaseOptionList_ = new LinkedList<Mksinterface_query_option>();
		developmentOrderOptionList_ = new LinkedList<Mksinterface_query_option>();
		taskOptionList_ = new LinkedList<Mksinterface_query_option>();
		requirementOptionList_ = new LinkedList<Mksinterface_query_option>();
		requirementDocumentOptionList_ = new LinkedList<Mksinterface_query_option>();
		changeOrConcernOptionList_ = new LinkedList<Mksinterface_query_option>();
	}
	
	/**
	 * Adds an option in the right order to the "list"
	 * @param option option to be added
	 * @throws Mksinterface_executeQueryException 
	 */
	public void addOptionToList(Mksinterface_query_option option) throws Mksinterface_executeQueryException
	{
		if(option.getItemtype().equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_PROJECT))
			projectOptionList_.add(option);
		else if(option.getItemtype().equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_RELEASE))
			releaseOptionList_.add(option);
		else if(option.getItemtype().equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_DEVELOPMENT_ORDER))
			developmentOrderOptionList_.add(option);
		else if(option.getItemtype().equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_TASK))
			taskOptionList_.add(option);
		else if(option.getItemtype().equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_REQUIREMENT))
			requirementOptionList_.add(option);
		else if(option.getItemtype().equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_REQUIREMENT_DOCUMENT))
			requirementDocumentOptionList_.add(option);
		else if(option.getItemtype().equals(Mksinterface_mksitem.MKSINTERFACE_MKSITEM_TYPE_CHANGE_OR_CONCERN))
			changeOrConcernOptionList_.add(option);
		else
			throw new Mksinterface_executeQueryException("Option: Itemtype not valid");
	}
	
	
	/**
	 * Returns a full sorted Linked List with all available options
	 * @return List with all Options. The Option with the highest order (e.g. Project) is the LAST object in the list.
	 * 		   With list.removeLast() you get the last option and also removes it from the list.
	 */
	public LinkedList<Mksinterface_query_option> getOptions()
	{
		LinkedList<Mksinterface_query_option> returnList= new LinkedList<Mksinterface_query_option>();
		returnList.addAll(changeOrConcernOptionList_);
		returnList.addAll(requirementDocumentOptionList_);
		returnList.addAll(requirementOptionList_);
		returnList.addAll(taskOptionList_);
		returnList.addAll(developmentOrderOptionList_);
		returnList.addAll(releaseOptionList_);
		returnList.addAll(projectOptionList_);
		return returnList;
	}
	
	
}
