package utd.persistentDataStore.datastoreServer;


import java.io.*;
import java.net.*;

import org.apache.log4j.Logger;

import utd.persistentDataStore.datastoreServer.commands.*;
import utd.persistentDataStore.utils.*;
import utd.persistentDataStore.datastoreClient.*;

public class DatastoreServer
{
	private static Logger logger = Logger.getLogger(DatastoreServer.class);

	static public final int port = 10023;

	public void startup() throws IOException
	{
		logger.debug("Starting Service at port " + port);

		ServerSocket serverSocket = new ServerSocket(port);
		Socket clientSocket = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		
		while (true) {
			try {
				logger.debug("Waiting for request");
				// The following accept() will block until a client connection 
				// request is received at the configured port number
				clientSocket = serverSocket.accept();
				logger.debug("Request received");
				
				System.out.println("connected!");

				inputStream = clientSocket.getInputStream();
				outputStream = clientSocket.getOutputStream();

				ServerCommand command = dispatchCommand(inputStream);
				logger.debug("Processing Request: " + command);
				command.setInputStream(inputStream);
				command.setOutputStream(outputStream);
				command.run();
				
				StreamUtil.closeSocket(inputStream);
			}
			catch (ServerException ex) {
				String msg = ex.getMessage();
				logger.error("Exception while processing request. " + msg);
				StreamUtil.sendError(msg, outputStream);
				StreamUtil.closeSocket(inputStream);
			}
			catch (Exception ex) {
				logger.error("Exception while processing request. " + ex.getMessage());
				ex.printStackTrace();
				StreamUtil.closeSocket(inputStream);
			}
		}
	}

	private ServerCommand dispatchCommand(InputStream inputStream) throws ServerException, IOException
	{
		try{
			String operationName = StreamUtil.readLine(inputStream);
			System.out.println(operationName);
			ServerCommand returnCommand;
		
			if (operationName.equals("write")){
				returnCommand = new WriteCommand();			
				}	else if (operationName.equals("read")){
					returnCommand = new ReadCommand();
				}	else if (operationName.equals("delete")){
					returnCommand = new DeleteCommand();
				}	else if (operationName.equals("directory")){
					returnCommand = new DirectoryCommand();
				}	else{
					returnCommand = null;
				}		
		
			return returnCommand;}
		catch(IOException e)
			{
				e.printStackTrace();
			}
		return null;
	}

	public static void main(String args[])
	{
		DatastoreServer server = new DatastoreServer();
		try {
			server.startup();
		}
		catch (IOException ex) {
			logger.error("Unable to start server. " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
