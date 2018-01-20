package utd.persistentDataStore.datastoreServer.commands;

import java.io.*;

import org.apache.log4j.Logger;

import utd.persistentDataStore.datastoreClient.*;
import utd.persistentDataStore.datastoreServer.*;
import utd.persistentDataStore.utils.*;


public abstract class ServerCommand
{
	private static Logger logger = Logger.getLogger(ServerCommand.class);

	protected InputStream inputStream;
	protected OutputStream outputStream;
	
	abstract public void run() throws IOException, ServerException, ClientException;
	
	public void setInputStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	public void setOutputStream(OutputStream outputStream)
	{
		this.outputStream = outputStream;
	}
	
	protected void sendOK() throws IOException
    {
		String msg = "OK\n";
		outputStream.write(msg.getBytes());
		outputStream.flush();
    }

	protected void sendError(String errMsg) throws ClientException
    {
		StreamUtil.sendError(errMsg, outputStream);
    }
	
}
