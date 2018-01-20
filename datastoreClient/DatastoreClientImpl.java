package utd.persistentDataStore.datastoreClient;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.*;

import org.apache.log4j.Logger;

import utd.persistentDataStore.datastoreServer.*;
import utd.persistentDataStore.datastoreServer.commands.*;
import utd.persistentDataStore.utils.*;

public class DatastoreClientImpl implements DatastoreClient
{
	private static Logger logger = Logger.getLogger(DatastoreClientImpl.class);

	private InetAddress address;
	private int port;
	public Socket socket = null;
	public InputStream inputStream = null;
	public OutputStream outputStream = null;
	
	public DatastoreClientImpl(InetAddress address, int port) throws IOException
	{
		this.address = address;
		this.port = port;
		
		//SocketAddress saddr = new InetSocketAddress(address, port);
		//socket = new Socket();
		//socket.connect(saddr);
				
		//inputStream = socket.getInputStream();
		//outputStream = socket.getOutputStream();
		    		
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#write(java.lang.String, byte[])
	 */
	@Override
    public void write(String name, byte data[]) throws ClientException, ConnectionException, IOException
	{
		SocketAddress saddr = new InetSocketAddress(address, port);
		socket = new Socket();
		socket.connect(saddr);
				
		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();
		
		logger.debug("Executing Write Operation");
		
		String name1 = "write";
		StreamUtil.writeLine(name1, outputStream);
		
		String name2 = name;
		StreamUtil.writeLine(name2, outputStream);
		
		byte [] name3 = data;
		int len1 = name3.length;
		String len2 = Integer.toString(len1);
		StreamUtil.writeLine(len2, outputStream);
		
		StreamUtil.writeData(data, outputStream);
		
		outputStream.flush();
				
		String fromServer;
		
		fromServer=StreamUtil.readLine(inputStream);
		System.out.println(fromServer);		
		
	}

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#read(java.lang.String)
	 */
	@Override
    public byte[] read(String name) throws ClientException, ConnectionException, IOException
	{
		try{
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket = new Socket();
			socket.connect(saddr);
				
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
		
			logger.debug("Executing Read Operation");
		
			String name1 = "read";
			StreamUtil.writeLine(name1, outputStream);
		
			String name2 = name;
			StreamUtil.writeLine(name2, outputStream);	
		
			outputStream.flush();
				
			byte [] fromServer;
		
			String name3 = StreamUtil.readLine(inputStream);
				
			if (name3.equalsIgnoreCase("ok")){
			
				String len1 = StreamUtil.readLine(inputStream);
				int len2 = Integer.parseInt(len1);
				fromServer = StreamUtil.readData(len2,inputStream);
			
				System.out.println(name3);
				System.out.println(len1);
			
				String name4 = new String(fromServer, "UTF-8");
				System.out.println(name4);			
			
				return fromServer;}
			
			else{
				throw new ClientException("Failed");
				}
		}
		
		catch(Exception ex)
			{
				throw new ClientException(ex.getMessage());
				//return null;
			}
		}
	

	/* (non-Javadoc)
	 * @see utd.persistentDataStore.datastoreClient.DatastoreClient#delete(java.lang.String)
	 */
	@Override
    public void delete(String name) throws ClientException, ConnectionException, IOException
	{ 
		try{
			
			SocketAddress saddr = new InetSocketAddress(address, port);
			socket = new Socket();
			socket.connect(saddr);
				
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
		
			logger.debug("Executing Delete Operation");
		
			String name1 = "delete";
			StreamUtil.writeLine(name1, outputStream);
		
			String name2 = name;
			StreamUtil.writeLine(name2, outputStream);	
		
			outputStream.flush();
				
			String fromServer;
		
			fromServer=StreamUtil.readLine(inputStream);
		
			System.out.println(fromServer);
			
			if ((!fromServer.equalsIgnoreCase("ok")))
				{throw new ClientException("Failed");}
		}
		catch(Exception ex)
			{
				throw new ClientException(ex.getMessage());
			}
	
	}

	
	@Override
    public List<String> directory() throws ClientException, ConnectionException, IOException
	{
		SocketAddress saddr = new InetSocketAddress(address, port);
		socket = new Socket();
		socket.connect(saddr);
				
		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();
		
		logger.debug("Executing Directory Operation");
		
		String name1 = "directory";
		StreamUtil.writeLine(name1, outputStream);
		
		outputStream.flush();
		
		List<String> result1 = new ArrayList<String>();
		
		String name2 = StreamUtil.readLine(inputStream);
		
		if (name2.equalsIgnoreCase("ok")){
			String len1 = StreamUtil.readLine(inputStream);
			int len2 = Integer.parseInt(len1);
			
			System.out.println(name2);
			System.out.println(len1);
			
			for (int i=0; i<len2; i++){
				String name3 = StreamUtil.readLine(inputStream);
				System.out.println(name3);
				result1.add(name3);				
			}			
			return result1;
		} else {
			return null;
		}
		
		
	}

}
