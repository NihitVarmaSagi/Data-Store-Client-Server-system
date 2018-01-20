package utd.persistentDataStore.datastoreServer.commands;

import java.io.*;

import org.apache.log4j.Logger;

import utd.persistentDataStore.datastoreClient.*;
import utd.persistentDataStore.datastoreServer.*;
import utd.persistentDataStore.utils.*;

public class ReadCommand extends ServerCommand{
	
	
	public void run() throws IOException, ServerException, ClientException{
		String fileName = StreamUtil.readLine(inputStream);
		System.out.println(fileName);
		byte [] baos1 = FileUtil.readData(fileName);
		int len1 = baos1.length;
		String len2 = Integer.toString(len1);
		
		if (baos1 != null){
			this.sendOK();
			StreamUtil.writeLine(len2, outputStream);
			StreamUtil.writeData(baos1, outputStream);			
		}	else {
			String errMsg1 = "File not found!";
			this.sendError(errMsg1);
		}
	}
	
	

}
