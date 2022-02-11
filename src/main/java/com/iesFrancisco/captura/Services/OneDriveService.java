package com.iesFrancisco.captura.Services;

import java.time.LocalDate;
import java.util.Arrays;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.logger.DefaultLogger;
import com.microsoft.graph.logger.LoggerLevel;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.Folder;
import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;

public class OneDriveService {

	static String clientId = "e26f46cb-16f5-4fab-9859-99c2290e3f62";
	static String clientSecret = "0sH7Q~4E.Vd_BZSUPyyB7s5lzMIvWcQKJyhAc";
	static String tenant = "8addfd3a-7010-48a8-a9c7-77f01500be22";
	private static GraphServiceClient<okhttp3.Request> graphClient = null;
	private static TokenCredentialAuthProvider authProvider = null;

	/**
	 * Metodo que usaremos para crear una carpeta en OneDrive con el nombre que le
	 * pasemos de la obra el cual usaremos para crear las carpetas de las obras
	 * 
	 * @param folderName Nombre de la carpeta que queremos crear en OneDrive
	 * @throws ClientException si el usuario no existe
	 */
	public static void createObra(String folderName) throws ClientException {

		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);

		graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();

		DriveItem driveItem = new DriveItem();
		driveItem.name = folderName;
		Folder folder = new Folder();
		driveItem.folder = folder;

		graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().root().children().buildRequest()
				.post(driveItem);
	}

	/**
	 * Metodo que usaremos para crear una carpeta en OneDrive con el nombre que le
	 * pasemos de la visita y el nombre de la obra para guardar la carpeta de la
	 * visita dentro de la capeta de la obra correspondeiente
	 * 
	 * @param folderName     nomre de la carpeta que queremos crear en OneDrive
	 * @param parentFolderId Id de la carpeta padre de la carpeta que queremos crear
	 * @throws ClientException si el usuario no existe
	 */
	public static void createVisita(String folderName, String parentFolderName) throws ClientException {

		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);

		graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();

		DriveItem driveItem = new DriveItem();
		driveItem.name = LocalDate.now()+folderName;
		Folder folder = new Folder();
		driveItem.folder = folder;

		graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().items(getFolderId(parentFolderName)).children()
				.buildRequest().post(driveItem);

	}
	/**
	 * Metodo que usaremos para obtener el id de la carpeta pasando el nombre de la carpeta
	 * @param folderName Nombre de la carpeta que queremos obtener
	 * @return Id de la carpeta
	 * @throws ClientException si el usuario no existe
	 */
	public static String getFolderId(String folderName) throws ClientException {

		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);

		graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();

		DriveItemCollectionPage me = graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().root().children()
				.buildRequest().get();
		for (DriveItem di : me.getCurrentPage()) {
			if (di.name.equals(folderName)) {
				return di.id;
			}
		}
		return null;
	}

	/**
	 * Metodo que usaremos para borra una carpeta de One Drive introduciendo el
	 * nombre de la carpeta
	 * 
	 * @param folderName Nombre de la carpeta que queremos borrar
	 * @throws ClientException si el usuario no existe
	 */
	public static void borraObra(String folderName) throws ClientException {
		
		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);

		graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();
		
		graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive()
		.items("01YB5ORGMXQCKRCEY45VG32KVBAZNZQZKK").buildRequest().delete();
	}

	/**
	 * Metodo que usaremos para borrar una carpeta de One Drive introduciendo el
	 * nombre de la carpeta y el id de la carpeta padre
	 * 
	 * @param folderName     Nombre de la carpeta que queremos borrar
	 * @param parentFolderId Id de la carpeta padre de la carpeta que queremos
	 *                       borrar
	 * @throws ClientException si el usuario no existe
	 */
	public static void borrarVisita(String folderName, String parentFolderId) throws ClientException {

	}
}
