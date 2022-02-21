package com.iesFrancisco.captura.Services;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.logger.DefaultLogger;
import com.microsoft.graph.logger.LoggerLevel;
import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.DriveItemCreateUploadSessionParameterSet;
import com.microsoft.graph.models.DriveItemUploadableProperties;
import com.microsoft.graph.models.Folder;
import com.microsoft.graph.models.UploadSession;
import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.tasks.IProgressCallback;
import com.microsoft.graph.tasks.LargeFileUploadTask;

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
	public static void createObra(String folderName) {

		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);
		try {
			graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();
	
			DriveItem driveItem = new DriveItem();
			driveItem.name = folderName;
			Folder folder = new Folder();
			driveItem.folder = folder;
	
			graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().root().children().buildRequest()
					.post(driveItem);
		} catch (ClientException e) {
			throw new ClientException("Error al crear el cliente de OneDrive", e);
		}
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
	public static void createVisita(String folderName, String parentFolderName) {

		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);
		try {
			graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();
	
			DriveItem driveItem = new DriveItem();
			driveItem.name = LocalDate.now()+folderName;
			Folder folder = new Folder();
			driveItem.folder = folder;
	
			graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().items(getFolderId(parentFolderName))
					.children().buildRequest().post(driveItem);
		} catch (ClientException e) {
			throw new ClientException("Error al crear el cliente de OneDrive", e);
		}

	}

	/**
	 * Metodo que usaremos para obtener el id de la carpeta pasando el nombre de la
	 * carpeta
	 * 
	 * @param folderName Nombre de la carpeta que queremos obtener
	 * @return Id de la carpeta
	 * @throws ClientException si el usuario no existe
	 */
	public static String getFolderId(String folderName) {

		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);
		try {
			graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();
	
			DriveItemCollectionPage me = graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().root().children()
					.buildRequest().get();
			for (DriveItem di : me.getCurrentPage()) {
				if (di.name.equals(folderName)) {
					return di.id;
				}
			}
		}catch (ClientException e) {
			throw new ClientException("Error al crear el cliente de OneDrive", e);
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
	public static void borraObra(String folderName) {

		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);
		try {
			graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();
	
			graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().items(getFolderId(folderName)).buildRequest()
					.delete();
		}catch (ClientException e) {
			throw new ClientException("Error al crear el cliente de OneDrive", e);
		}
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
	public static void borrarVisita(String folderName, String parentFolderId) {

		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);
		try {
			graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();
	
			DriveItem driveItem = new DriveItem();
			driveItem.name = folderName;
			Folder folder = new Folder();
			driveItem.folder = folder;
		
			graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().root().itemWithPath(parentFolderId+"/"+folderName).buildRequest()
			.delete();
			
		} catch (ClientException e) {
			throw new ClientException("Error al crear el cliente de OneDrive", e);
		}
	}
	/**
	 * Metodo que usaremos para subir un fichero a OneDrive con el nombre que le pasemos de la visita y el
	 * nombre de la obra para guardar la carpeta de la visita dentro de la capeta de la obra correspondeiente
	 * @param fileName Nombre del fichero que queremos subir
	 * @param rootFolderName Nombre de la carpeta padre de la carpeta que queremos subir 
	 * @param visitaFolderName Nombre de la carpeta que queremos subir
	 * @param file Fichero que queremos subir
	 * @throws IOException
	 */
	public static void uploadFile(String fileName, String rootFolderName, String visitaFolderName, InputStream file) {
		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);
		try {
			graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();
			
			InputStream fileStream =  file;
			long streamSize =  fileStream.available();
	
			IProgressCallback callback = new IProgressCallback() {
				@Override
				// Called after each slice of the file is uploaded
				public void progress(final long current, final long max) {
					System.out.println(String.format("Uploaded %d bytes of %d total bytes", current, max));
				}
			};
			DriveItemCreateUploadSessionParameterSet uploadParams = DriveItemCreateUploadSessionParameterSet.newBuilder()
					.withItem(new DriveItemUploadableProperties()).build();
			
			// Create an upload session
			UploadSession uploadSession = graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().root()
					// itemPath like "/Folder/file.txt"
					// does not need to be a path to an existing item
					.itemWithPath(rootFolderName+"/"+visitaFolderName+"/"+fileName).createUploadSession(uploadParams).buildRequest().post();
	
			LargeFileUploadTask<DriveItem> largeFileUploadTask = new LargeFileUploadTask<DriveItem>(uploadSession,
					graphClient, fileStream, streamSize, DriveItem.class);
	
			// Do the upload
			try {
				largeFileUploadTask.upload(0, null, callback);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch (ClientException e) {
			throw new ClientException("Error al crear el cliente de OneDrive", e);
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	/**
	 * Metodo que devuelve la url de la imagen que queremos subir
	 * @param fileName Nombre del fichero que queremos subir
	 * @param rootFolderName Nombre de la carpeta padre de la carpeta que queremos subir
	 * @param visitaFolderName Nombre de la carpeta que queremos subir
	 * @return url de la imagen que queremos subir
	 */
	public static String getUrl(String fileName, String rootFolderName, String visitaFolderName) {
		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);
		try {
			graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();
			String folderId = getFolderId(rootFolderName);
			String folderId2 = getFolderId(visitaFolderName);
			DriveItemCollectionPage me = graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().root().itemWithPath(rootFolderName+"/"+visitaFolderName)
					.children().buildRequest().get();
			for (DriveItem di : me.getCurrentPage()) {
				if (di.name!=null && di.name.equals(fileName)) {
					return di.webUrl;
				}
			}
			return null;
		} catch (ClientException e) {
			throw new ClientException("Error al crear el cliente de OneDrive", e);
		}
		
	}
}
