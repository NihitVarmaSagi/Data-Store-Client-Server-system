package utd.persistentDataStore.datastoreServer.commands;

import java.io.*;

import org.apache.log4j.Logger;

import utd.persistentDataStore.datastoreClient.*;
import utd.persistentDataStore.datastoreServer.*;
import utd.persistentDataStore.utils.*;

public class DeleteCommand extends ServerCommand{
	
	public void run() throws IOException, ServerException, ClientException{
		String fileName = StreamUtil.readLine(inputStream);
		
		if(FileUtil.deleteData(fileName)) {
			this.sendOK();
		}	else {
			String errMsg1 = "File not found!";
			this.sendError(errMsg1);
		}		
			
		
	}
		

}
