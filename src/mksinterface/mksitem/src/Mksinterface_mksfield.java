package mksinterface.mksitem.src;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mks.api.response.Field;
import com.mks.api.response.Item;

/**
 * This special field class represents a full field of an item in Integrity. 
 * You are able to feed the class with an "Field" element which is given directly 
 * from Integrity or feed it with your own parameters. Internal the fields will be 
 * saved with their correct types and values. 
 * @author ckaltenboeck
 *
 */
public class Mksinterface_mksfield {
	//Variable definitions
	private String stringValue_;
	private int intValue_;
	private Calendar calendarValue_;
	private double doubleValue_;
	private boolean booleanValue_;
	private ArrayList<Object> listValue_;
	private long longValue_;
	private int type_;
	private String name_;
	
	//Constants for all at the moment available Types of fields
	public static final int MKSINTERFACE_MKSFIELD_TYPE_NO_TYPE = 0;
	public static final int MKSINTERFACE_MKSFIELD_TYPE_STRING = 1;
	public static final int MKSINTERFACE_MKSFIELD_TYPE_INT = 2;
	public static final int MKSINTERFACE_MKSFIELD_TYPE_BOOLEAN = 3;
	public static final int MKSINTERFACE_MKSFIELD_TYPE_DOUBLE = 4;
	public static final int MKSINTERFACE_MKSFIELD_TYPE_CALENDAR = 5;
	public static final int MKSINTERFACE_MKSFIELD_TYPE_OBJECTLIST = 6;
	public static final int MKSINTERFACE_MKSFIELD_TYPE_LONG = 7;

	
	/**
	 * Constructor to create an internal field from a mksfield
	 * @param field mksfield object
	 */
	public Mksinterface_mksfield(Field field) throws Mksinterface_fieldException
	{
		if(field == null)
			throw new Mksinterface_fieldException(Mksinterface_fieldException.MKSINTERFACE_FIELD_EXCEPTION_FIELD_NULL);
		
		if (field.getDisplayName() == null)
			throw new Mksinterface_fieldException(Mksinterface_fieldException.MKSINTERFACE_FIELD_EXCEPTION_TYPE_NOT_AVAILABLE);
		else
			name_ = field.getDisplayName();
		
		if (field.getDataType() == null)
		{
			type_ = MKSINTERFACE_MKSFIELD_TYPE_NO_TYPE;
			return;
		}
	
		if (field.getDataType().equals(Field.BOOLEAN_TYPE))
		{
			booleanValue_ = field.getBoolean();
			type_ = MKSINTERFACE_MKSFIELD_TYPE_BOOLEAN;
		
		} else if (field.getDataType().equals(Field.DATE_TYPE))
		{
			calendarValue_ = Calendar.getInstance();
			calendarValue_.setTime(field.getDateTime());
			type_ = MKSINTERFACE_MKSFIELD_TYPE_CALENDAR;
		}
		else if (field.getDataType().equals(Field.DOUBLE_TYPE))
		{
			doubleValue_ = field.getDouble();
			type_ = MKSINTERFACE_MKSFIELD_TYPE_DOUBLE;
		}
		else if (field.getDataType().equals(Field.FLOAT_TYPE))
		{
			doubleValue_ = (double)field.getFloat();
			type_ = MKSINTERFACE_MKSFIELD_TYPE_DOUBLE;
		}
		else if (field.getDataType().equals(Field.INTEGER_TYPE))
		{
			intValue_ = field.getInteger();
			type_ = MKSINTERFACE_MKSFIELD_TYPE_INT;
		}
		else if (field.getDataType().equals(Field.LONG_TYPE))
		{
			longValue_ = field.getLong();
			type_ = MKSINTERFACE_MKSFIELD_TYPE_LONG;
		}
		else if (field.getDataType().equals(Field.ITEM_LIST_TYPE))
		{
			@SuppressWarnings("unchecked")
			List<Item> list = field.getList();
			listValue_ = new ArrayList<Object>();
			for(int i=0; i < list.size(); i++)
			{
				listValue_.add(new Integer(list.get(i).getId()));
			}
			type_ = MKSINTERFACE_MKSFIELD_TYPE_OBJECTLIST;
		}
		else if(field.getDataType().equals(Field.STRING_TYPE))
		{
			stringValue_ = field.getString();
			type_ = MKSINTERFACE_MKSFIELD_TYPE_STRING;
		}
		else if(field.getDataType().equals(Field.ITEM_TYPE))
		{
			if (field.getItem().toString() == null)
				stringValue_ = "";
			stringValue_ = field.getItem().toString();
			type_ = MKSINTERFACE_MKSFIELD_TYPE_STRING;
		}
		else if(field.getDataType().equals(Field.VALUE_LIST_TYPE))
		{
			stringValue_ = field.getItem().toString();
			type_ = MKSINTERFACE_MKSFIELD_TYPE_STRING;
		}
		else 
		{
			throw new Mksinterface_fieldException(Mksinterface_fieldException.MKSINTERFACE_FIELD_EXCEPTION_TYPE_NOT_AVAILABLE);
		}
	}
	
	/**
	 * Constructor to create an internal field with a String value and a name
	 * @param value Value of the field
	 * @param name Name of the field
	 */
	public Mksinterface_mksfield(String value, String name) throws Mksinterface_fieldException
	{
		if (value == null || name == null)
			throw new Mksinterface_fieldException(Mksinterface_fieldException.MKSINTERFACE_FIELD_EXCEPTION_PARAMETER_NULL);
		stringValue_ = value;
		name_ = name;
		type_ = MKSINTERFACE_MKSFIELD_TYPE_STRING;
	}
	
	/**
	 * Constructor to create an internal field with an int value and a name
	 * @param value Value of the field
	 * @param name Name of the field
	 */
	public Mksinterface_mksfield(int value, String name) throws Mksinterface_fieldException
	{
		if (name == null)
			throw new Mksinterface_fieldException(Mksinterface_fieldException.MKSINTERFACE_FIELD_EXCEPTION_PARAMETER_NULL);
		intValue_ = value;
		name_ = name;
		type_ = MKSINTERFACE_MKSFIELD_TYPE_INT;
	}
	
	/**
	 * Constructor to create an internal field with an boolean value and a name
	 * @param value Value of the field
	 * @param name Name of the field
	 */
	public Mksinterface_mksfield(boolean value, String name) throws Mksinterface_fieldException
	{
		if (name == null)
			throw new Mksinterface_fieldException(Mksinterface_fieldException.MKSINTERFACE_FIELD_EXCEPTION_PARAMETER_NULL);
		booleanValue_ = value;
		name_ = name;
		type_ = MKSINTERFACE_MKSFIELD_TYPE_BOOLEAN;
		
	}
	
	/**
	 * Constructor to create an internal field with an double value and a name
	 * @param value Value of the field
	 * @param name Name of the field
	 */
	public Mksinterface_mksfield(double value, String name) throws Mksinterface_fieldException
	{
		if (name == null)
			throw new Mksinterface_fieldException(Mksinterface_fieldException.MKSINTERFACE_FIELD_EXCEPTION_PARAMETER_NULL);
		doubleValue_ = value;
		name_ = name;
		type_ = MKSINTERFACE_MKSFIELD_TYPE_DOUBLE;
	}
	
	/**
	 * Constructor to create an internal field with an Calendar value and a name
	 * @param value Value of the field
	 * @param name Name of the field
	 */
	public Mksinterface_mksfield(Calendar value, String name) throws Mksinterface_fieldException
	{
		if (name == null || value == null)
			throw new Mksinterface_fieldException(Mksinterface_fieldException.MKSINTERFACE_FIELD_EXCEPTION_PARAMETER_NULL);
		calendarValue_ = value;
		name_ = name;
		type_ = MKSINTERFACE_MKSFIELD_TYPE_CALENDAR;
	}
	
	/**
	 * Constructor to create an internal field with an long value and a name
	 * @param value Value of the field
	 * @param name Name of the field
	 */
	public Mksinterface_mksfield(long value, String name) throws Mksinterface_fieldException
	{
		if (name == null)
			throw new Mksinterface_fieldException(Mksinterface_fieldException.MKSINTERFACE_FIELD_EXCEPTION_PARAMETER_NULL);
		longValue_ = value;
		name_ = name;
		type_ = MKSINTERFACE_MKSFIELD_TYPE_CALENDAR;
	}
	
	/**
	 * Constructor to create an internal field with an List Object value and a name
	 * @param value Value of the field
	 * @param name Name of the field
	 */
	public Mksinterface_mksfield(ArrayList<Object> value, String name) throws Mksinterface_fieldException
	{
		if (name == null)
			throw new Mksinterface_fieldException(Mksinterface_fieldException.MKSINTERFACE_FIELD_EXCEPTION_PARAMETER_NULL);
		if (value.size() == 0)
			throw new Mksinterface_fieldException(Mksinterface_fieldException.MKSINTERFACE_FIELD_EXCEPTION_PARAMETER_NULL);
		listValue_ = value;
		name_ = name;
		type_ = MKSINTERFACE_MKSFIELD_TYPE_BOOLEAN;
	}
	
	/**
	 * Returns the value of the field as string
	 * @return value
	 */
	public String getStringValue()
	{
		return stringValue_;
	}
	
	/**
	 * Returns the value of the field as int
	 * @return value
	 */
	public int getIntValue()
	{
		return intValue_;
	}
	
	/**
	 * Returns the value of the field as double
	 * @return value
	 */
	public double getDoubleValue()
	{
		return doubleValue_;
	}
	
	/**
	 * Returns the value of the field as boolean
	 * @return value
	 */
	public boolean getBooleanValue()
	{
		return booleanValue_;
	}
	
	/**
	 * Returns the value of the field as Calendar object
	 * @return value
	 */
	public Calendar getCalendarValue()
	{
		return calendarValue_;
	}
	
	/**
	 * Returns the value of the field as a List Object
	 * @return value
	 */
	public List<Object> getListValue()
	{
		return listValue_;
	}
	
	public long getLongValue()
	{
		return longValue_;
	}
	
	/**
	 * Converts a list of mks fields to a list of internal java fields
	 * @param fields input fields of the interface
	 * @return java representation fields
	 */
	public static ArrayList<Mksinterface_mksfield> convertField(ArrayList<Field> fields) throws Mksinterface_fieldException
	{
		ArrayList<Mksinterface_mksfield> mksfields = new ArrayList<Mksinterface_mksfield>();
		for(int i= 0; i < fields.size(); i++)
		{
			mksfields.add(new Mksinterface_mksfield(fields.get(i)));
		}
		return mksfields;
	}
	
	/**
	 * Returns the name of the field
	 * @return Name
	 */
	public String getName()
	{
		return name_;
	}
	
	/**
	 * Returns the type of the field as an integer value. 
	 * Look at the constants for type definition
	 * @return Type of the field
	 */
	public int getType()
	{
		return type_;
	}
	
	/**
	 * Returns value as string
	 */
	public String toString()
	{
		switch(type_)
		{
		case MKSINTERFACE_MKSFIELD_TYPE_STRING:
			return stringValue_;
		case MKSINTERFACE_MKSFIELD_TYPE_INT:
			return intValue_+"";
		case MKSINTERFACE_MKSFIELD_TYPE_BOOLEAN:
			if (booleanValue_)
				return "true";
			else 
				return "false";
		case MKSINTERFACE_MKSFIELD_TYPE_DOUBLE:
			return doubleValue_+"";
		case MKSINTERFACE_MKSFIELD_TYPE_CALENDAR:
			return calendarValue_.toString();
		case MKSINTERFACE_MKSFIELD_TYPE_OBJECTLIST:
			return listValue_.toString();
		case  MKSINTERFACE_MKSFIELD_TYPE_LONG:
			return longValue_+"";
		default:
			return "";
		}
	}
	
	
}
