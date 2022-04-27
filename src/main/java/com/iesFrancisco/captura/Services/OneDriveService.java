package com.iesFrancisco.captura.Services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
import org.apache.commons.io.IOUtils;
public class OneDriveService {

	static String clientId = "e26f46cb-16f5-4fab-9859-99c2290e3f62";
	static String clientSecret = "0sH7Q~4E.Vd_BZSUPyyB7s5lzMIvWcQKJyhAc";
	static String tenant = "8addfd3a-7010-48a8-a9c7-77f01500be22";
	private static GraphServiceClient<okhttp3.Request> graphClient = null;
	private static TokenCredentialAuthProvider authProvider = null;

	
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

	
	public static void borraObra(String folderName) {

		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);
		try {
			//si getFolderId devuelve null es que no existe la carpeta nos salimos del metodo
			if(getFolderId(folderName)==null) {
				return;
			}else{
				graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();
		
				graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().items(getFolderId(folderName)).buildRequest()
						.delete();
			}
		}catch (ClientException e) {
			throw new ClientException("Error al crear el cliente de OneDrive", e);
		}
	}

	
	public static void borrarVisita(String folderName, String parentFolderId) {

		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);
		try {
			//sino existe la ruta nos salimos del metodo
			if(getFolderId(folderName)==null && getFolderId(parentFolderId)==null) {
				return;
			}else{
				graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();
		
				DriveItem driveItem = new DriveItem();
				driveItem.name = folderName;
				Folder folder = new Folder();
				driveItem.folder = folder;
			
				graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().root().itemWithPath(parentFolderId+"/"+folderName).buildRequest()
				.delete();
			}
		} catch (ClientException e) {
			throw new ClientException("Error al crear el cliente de OneDrive", e);
		}
	}
	
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

	public static String getUrl(String fileName, String rootFolderName, String visitaFolderName) {
		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();

		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);

		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);
		try {
			graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();
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

	public static void updateFolderName(String newFolderName,String oldFolderName) {
		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
		.clientSecret(clientSecret).tenantId(tenant).build();
		
		authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
		credential);
		
		DefaultLogger logger = new DefaultLogger();
		logger.setLoggingLevel(LoggerLevel.ERROR);
		try {
		graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();
			DriveItem driveItem = new DriveItem();
			driveItem.name = newFolderName;
			graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().items(getFolderId(oldFolderName))
			.buildRequest()
			.patch(driveItem);
		}catch (ClientException e) {
			throw new ClientException("Error al crear el cliente de OneDrive", e);
		}
	}
	public byte[] getPhoto(String url) {
		byte[] result=null;
		ClientSecretCredential credential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenant).build();
				
				authProvider = new TokenCredentialAuthProvider(Arrays.asList("https://graph.microsoft.com/.default"),
				credential);
				
				DefaultLogger logger = new DefaultLogger();
				logger.setLoggingLevel(LoggerLevel.ERROR);
				try {
				graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).logger(logger).buildClient();
				URL in= getClass().getResource(url);
				if(in!=null)
					result=IOUtils.toByteArray(in);
					logger.logError(url, null);
				}catch (ClientException e) {
					throw new ClientException("Error al crear el cliente de OneDrive", e);
				} catch (IOException e) {
					throw new ClientException("Error al crear el cliente de OneDrive", e);
				}
		return result;
	}
	
}
