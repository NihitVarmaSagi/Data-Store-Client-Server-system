package utd.persistentDataStore.datastoreServer.commands;

import java.io.*;
import java.util.List;

import org.apache.log4j.Logger;

import utd.persistentDataStore.datastoreClient.*;
import utd.persistentDataStore.datastoreServer.*;
import utd.persistentDataStore.utils.*;

public class DirectoryCommand extends ServerCommand{
	
	public void run() throws IOException, ServerException{
		List<String> result1 = FileUtil.directory();
		
		int size1 = result1.size();
		
		this.sendOK();
		
		String msg = Integer.toString(size1);
		StreamUtil.writeLine(msg, outputStream);
		
		
		for (int i=0; i<size1; i++){
			String name1 = result1.get(i);
			StreamUtil.writeLine(name1, outputStream);			
			
		}
		
		
		
		
	}

}
