package com.iesFrancisco.captura.Services;

import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.Folder;
import com.microsoft.graph.models.Request;
import com.microsoft.graph.requests.GraphServiceClient;

public class OneDriveService {
	
	private static GraphServiceClient<Request> graphClient = null;
	
	/**
	 * Metodo que usaremos para crear una carpeta en OneDrive con el nombre que le pasemos de la obra
	 * el cual usaremos para crear las carpetas de las obras
	 * @param folderName Nombre de la carpeta que queremos crear en OneDrive
	 */
	public static void createObra(String folderName){
		DriveItem driveItem = new DriveItem();
		driveItem.name = folderName;
		Folder folder = new Folder();
		driveItem.folder = folder;
		
		graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().root().children().buildRequest()
		.post(driveItem);
	}
	/**
	 * Metodo que usaremos para crear una carpeta en OneDrive con el nombre que le pasemos de la visita y el nombre de la obra para 
	 * guardar la carpeta de la visita dentro de la capeta de la obra correspondeiente
	 * @param folderName nomre de la carpeta que queremos crear en OneDrive
	 * @param parentFolderId Id de la carpeta padre de la carpeta que queremos crear
	 */
	public static void createVisita(String folderName, String parentFolderId) {
		DriveItem driveItem = new DriveItem();
		driveItem.name = folderName;
		Folder folder = new Folder();
		driveItem.folder = folder;

		graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().items(parentFolderId).children()
				.buildRequest().post(driveItem);
	
	}
}
