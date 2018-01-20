package utd.persistentDataStore.datastoreClient;

import java.io.IOException;
import java.util.List;

public interface DatastoreClient
{

	void write(String name, byte data[]) throws ClientException, ConnectionException, IOException;

	byte[] read(String name) throws ClientException, ConnectionException, IOException;

	void delete(String name) throws ClientException, ConnectionException, IOException;

	List<String> directory() throws ClientException, ConnectionException, IOException;

}