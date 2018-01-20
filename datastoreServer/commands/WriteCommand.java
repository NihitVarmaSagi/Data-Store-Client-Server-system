package utd.persistentDataStore.datastoreServer.commands;

import java.io.*;

import org.apache.log4j.Logger;

import utd.persistentDataStore.datastoreClient.*;
import utd.persistentDataStore.datastoreServer.*;
import utd.persistentDataStore.utils.*;

public class WriteCommand extends ServerCommand{
	
	public void run() throws IOException, ServerException{
		
		String fileName = StreamUtil.readLine(inputStream);
		System.out.println(fileName);
		String size1 = StreamUtil.readLine(inputStream);
		int size2 = Integer.parseInt(size1);
		System.out.println(size1);
		byte [] baos2 = StreamUtil.readData(size2, inputStream);
		System.out.println(baos2);
		
		FileUtil.writeData(fileName,baos2);
		
		this.sendOK();
		
		
	}
	

}
