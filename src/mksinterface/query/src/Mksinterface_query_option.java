package mksinterface.query.src;

import java.util.ArrayList;

/**
 * This class saves a Option for a query
 * INFO: This class is not used for the moment
 *       I need it for a future query implementation
 * @author ckaltenboeck
 *
 */
public class Mksinterface_query_option {
	//internal Variables
	private String itemtype_ =  "";
	private String field_ = "";
	private String value_ = "";
	
	
	/**
	 * Constructor which defines the option with a String value
	 * @param itemtype
	 * @param field
	 * @param value
	 */
	public Mksinterface_query_option(String itemtype, String field, String value)
	{
		itemtype_ = itemtype;
		field_ = field;
		value_ = value;
	}
	
	/**
	 * Constructor which defines the option with a double value
	 * @param itemtype
	 * @param field
	 * @param value
	 */
	public Mksinterface_query_option(String itemtype, String field, double value)
	{
		itemtype_ = itemtype;
		field_ = field;
		value_ = value+"";
	}
	
	/**
	 * Constructor which defines the option with a Calendar value
	 * @param itemtype
	 * @param field
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 */
	 public Mksinterface_query_option(String itemtype, String field, int year, int month, int day, int hour, int minute, int second, String period)
	 {
		 itemtype_ = itemtype;
	     field_ = field;
	     switch(month)
	     {
	     	case 1:
	     		value_ = "Jan ";
	     		break;
	     	case 2:
	     		value_ = "Feb ";
	     		break;
	     	case 3:
	     		value_ = "Mar ";
	     		break;
	     	case 4:
	     		value_ = "Apr ";
	     		break;
	     	case 5:
	     		value_ = "May ";
	     		break;
	     	case 6:
	     		value_ = "Jun ";
	     		break;
	     	case 7:
	     		value_ = "Jul ";
	     		break;
	     	case 8:
	     		value_ = "Aug ";
	     		break;
	     	case 9:
	     		value_ = "sep ";
	     		break;
	     	case 10:
	     		value_ = "Oct ";
	     		break;
	     	case 11:
	     		value_ = "Nov ";
	     		break;
	     	case 12:
	     		value_ = "Dec ";
	     		break;
	     	default:
	     		value_ = null;
	     		return;
	     }
	     
	     if ((month == 1 || month == 3 || month == 5 || month == 7 || month==8 || month == 10 || month == 12) && day > 31)
	     {
	    	 value_ = null;
	    	 return;
	     } else if ((month == 4 || month == 6 || month == 9 || month == 11 ) && day > 30)
	     {
	    	 value_ = null;
	    	 return;
	     }
	     else if (month == 2 && day > 29)
	     {
	     	 value_ = null;
	     	 return;
	 	 }
	     else if (day < 1 )
	     {
	    	 value_ = null;
	     	 return;
	     }
	     else
	    	 value_ = value_ + day + ", ";
	     
	     value_ = value_ + year + " ";
	     
	     if (hour <= 12 && hour >= 1 && minute <= 59 && minute >= 0 && second <= 59 && second >= 0 && (period.equals("AM") || period.equals("PM")))
	     {
	    	 value_ =  value_ + hour + ":" +  minute + ":" + second + " "+ period;
	     } else 
	     {
	    	 value_ = null;
	    	 return;
	     }
	 }
	 
	 /**
	  * Constructor which defines the option with a boolean value
	  * @param itemtype
	  * @param field
	  * @param value
	  */
	 public Mksinterface_query_option(String itemtype, String field, boolean value)
     {
		 itemtype_ = itemtype;
	     field_ = field;
	     value_ = value+"";	
     }
	 
	 /**
	  * Constructor which defines the option with an Object list
	  * @param itemtype
	  * @param field
	  * @param value
	  */
	 public Mksinterface_query_option(String itemtype, String field, ArrayList<Object> value)
     {
		itemtype_ = itemtype;
		field_ = field;
		if ( value.size() >= 1)
			value_ = value_ + value.get(0);
		for(int i=1; i < value.size(); i++)
		{
			value_ = value_ + ", "+ value.get(i); 
		}
     }
	
	/**
	 * Returns the itemtpye of the option
	 * @return itemtype of the option
	 */
	public String getItemtype()
	{
		return itemtype_;
	}
	
	
	/**
	 * Return the field of the option
	 * @return field of the option
	 */
	public String getField()
	{
		return field_;
	}
	
	
	/**
	 * Returns the Value of the option
	 * @return value of the option
	 */
	public String getValue()
	{
		return value_;
	}
}
