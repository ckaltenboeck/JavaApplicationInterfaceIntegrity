package mksinterface.connection.src;

import java.io.IOException;
import java.util.Iterator;

import com.mks.api.Command;

import com.mks.api.*;
import com.mks.api.response.*;

/**
 * For connecting to the Integrity interface this connection class is needed. 
 * You are able to connect, disconnect and execute all commands over this class. 
 * For every interaction with Integrity a full connected connection is necessary. 
 * The class is singleton because is is only necessary to have one connection for all interactions. 
 * Use this class with the getInstance() method instead of the private constructor
 * @author ckaltenboeck
 *
 */
public class Mksinterface_connection {
	//Command strings----------------------------------------------------------
	private static String MKSINTERFACE_CONNECTION_CMD_CONNECT = "connect";
	private static String MKSINTERFACE_CONNECTION_CMD_DISCONNECT ="disconnect";
	public static String MKSINTERFACE_CONNECTION_CMD_ISSUES = "issues";
	public static String MKSINTERFACE_CONNECTION_CMD_VIEWISSUE = "viewissue";
	public static String MKSINTERFACE_CONNECTION_CMD_EXTRACTATTACHMENTS = "extractattachments";
	public static String MKSINTERFACE_CONNECTION_CMD_CPS = "cps";
	private static int MKSINTERFACE_CONNECTION_APIMAJORVERSION = 4;
	private static int MKSINTERFACE_CONNECTION_APIMINORVERSION = 10;
	public static String MKSINTERFACE_CONNECTION_OPTION_GUI = "gui";
	private static String MKSINTERFACE_CONNECTION_OPTION_HOSTNAME = "hostname";
	private static String MKSINTERFACE_CONNECTION_OPTION_PORT = "port";
	public static String MKSINTERFACE_CONNECTION_OPTION_ISSSUE = "issue";
	public static String MKSINTERFACE_CONNECTION_OPTION_FIELDS = "fields";
	public static String MKSINTERFACE_CONNECTION_OPTION_QUERYDEFINITION = "querydefinition";
	public static String MKSINTERFACE_CONNECTION_OPTION_OUTPUTFILE = "outputfile";
	public static String MKSINTERFACE_CONNECTION_OPTION_ATTRIBUTES = "attributes";
	private static String MKSINTERFACE_CONNECTION_SERVER_HOSTNAME_MKSPROD = "mksprod";
	private static String MKSINTERFACE_CONNECTION_SERVER_HOSTNAME_MKSTEST = "mkstest";
	private static int MKSINTERFACE_CONNECTION_SERVER_PORT = 7001;
	
	//Variables---------------------------------------------------------------
	private static Mksinterface_connection instance_ = null;
	private boolean state_ = false;
	private IntegrationPointFactory intFact_;
	private IntegrationPoint integrationPoint_;
	private Session session_;
	private CmdRunner cmdRunner_;
	private String hostName_ = MKSINTERFACE_CONNECTION_SERVER_HOSTNAME_MKSPROD;
	private int port_ = MKSINTERFACE_CONNECTION_SERVER_PORT;
	
	/**
	 * Private Constructor for Singelton useage
	 */
	private Mksinterface_connection()
	{}
	
	/**
	 * Method for getting the instance of the Singelton class
	 * @return Instance of class
	 */
	public static Mksinterface_connection getInstance()
	{
		if(instance_ == null) {
	         instance_ = new Mksinterface_connection();
	    }
	    return instance_;
	}
	
	/**
	 * Connect to Integrity
	 * @return actual status
	 * @throws Mksinterface_connectionException 
	 */
	public boolean connect() throws Mksinterface_connectionException
	{
		if(state_)
			return state_;
		
		intFact_= IntegrationPointFactory.getInstance();
		@SuppressWarnings("unchecked")
		Iterator<IntegrationPoint> integrationIt = 
				intFact_.getIntegrationPoints();
		if (integrationIt.hasNext())
		{
			integrationPoint_ = integrationIt.next();
		}
		else
		{
			try {
				integrationPoint_ = intFact_.createLocalIntegrationPoint(
						MKSINTERFACE_CONNECTION_APIMAJORVERSION, MKSINTERFACE_CONNECTION_APIMINORVERSION);
			} catch (APIException e) {
				throw new Mksinterface_connectionException(Mksinterface_connectionException.MKSINTERFACE_CONNECTION_EXCEPTION_CONNECTION_FAILED + e.getMessage());
			}
		}
		
		integrationPoint_.setAutoStartIntegrityClient(true);
		
		Iterator<Session> sessionIt = integrationPoint_.getSessions();
		if (sessionIt.hasNext())
		{
		    session_ = sessionIt.next();
		}
		else
		{
			try {
				session_ = integrationPoint_.getCommonSession();
			} catch (APIException e) {
				throw new Mksinterface_connectionException(Mksinterface_connectionException.MKSINTERFACE_CONNECTION_EXCEPTION_CONNECTION_FAILED + e.getMessage());
			}
		}
		
		try {
			cmdRunner_ = session_.createCmdRunner();
			Command command = new Command(Command.SI,MKSINTERFACE_CONNECTION_CMD_CONNECT);
			command.addOption(new Option(MKSINTERFACE_CONNECTION_OPTION_GUI));
			command.addOption(new Option(MKSINTERFACE_CONNECTION_OPTION_HOSTNAME,hostName_));
			command.addOption(new Option(MKSINTERFACE_CONNECTION_OPTION_PORT,port_+""));
			cmdRunner_.execute(command);
		} catch (APIException e) {
			throw new Mksinterface_connectionException(Mksinterface_connectionException.MKSINTERFACE_CONNECTION_EXCEPTION_CONNECTION_FAILED + e.getMessage());
		}
	    
	    state_=true;
	    return state_;
	}
	
	/**
	 * Disconnect from Integrity
	 * @return actual status
	 * @throws Mksinterface_connectionException 
	 */
	public boolean disconnect() throws Mksinterface_connectionException
	{
		if(!state_) 
			return state_;
	
		try {
			cmdRunner_.execute(new Command(Command.SI,MKSINTERFACE_CONNECTION_CMD_DISCONNECT));
		} catch (APIException e) {
			throw new Mksinterface_connectionException(
					Mksinterface_connectionException.MKSINTERFACE_CONNECTION_EXCEPTION_NO_DISCONNECTION+e.getMessage());
		}
		
		
		state_=false;

		
		Iterator<CmdRunner> cmdr_it = session_.getCmdRunners();
        while(cmdr_it.hasNext())
        {
            try {
				cmdr_it.next().release();
			} catch (APIException e) {
				throw new Mksinterface_connectionException(
						Mksinterface_connectionException.MKSINTERFACE_CONNECTION_EXCEPTION_NO_DISCONNECTION+e.getMessage());
			}
		}        
        try {
			session_.release();
		} catch (APIException e) {
			throw new Mksinterface_connectionException(
					Mksinterface_connectionException.MKSINTERFACE_CONNECTION_EXCEPTION_NO_DISCONNECTION+e.getMessage());
		} catch (IOException e) {
			throw new Mksinterface_connectionException(
					Mksinterface_connectionException.MKSINTERFACE_CONNECTION_EXCEPTION_NO_DISCONNECTION+e.getMessage());	
		}     
        intFact_.removeIntegrationPoint(integrationPoint_);
        return state_;
	}
	
	/**
	 * Executing a command 
	 * @param cmd Command Object
	 * @return Response of the exeuted command
	 * @throws Mksinterface_executeException 
	 * @throws Mksinterface_connectionException 
	 */
	public Response execute(Command cmd) throws Mksinterface_executeException, Mksinterface_connectionException
	{
		Response response=null;
		
		if(!state_) throw new Mksinterface_connectionException(Mksinterface_connectionException.MKSINTERFACE_CONNECTION_EXCEPTION_NOT_CONNECTED);

		try {
			if (cmd == null)
				throw new Mksinterface_executeException(Mksinterface_executeException.MKSINTERFACE_EXECUTE_EXCEPTION_EXECUTION_FAILED + "Command is null");
			response = cmdRunner_.execute(cmd);
		} catch (APIException e) {
			throw new Mksinterface_executeException(Mksinterface_executeException.MKSINTERFACE_EXECUTE_EXCEPTION_EXECUTION_FAILED + e.getMessage());
		}
		return response;
	}
	
	/**
	 * Returns the state of the connection
	 * @return state of the connection
	 */
	public boolean getState()
	{
		return state_;
	}
	
}
