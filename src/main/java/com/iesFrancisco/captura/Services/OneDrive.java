package com.iesFrancisco.captura.Services;

import com.microsoft.graph.models.DriveItem;
import com.microsoft.graph.models.Folder;
import com.microsoft.graph.models.Request;
import com.microsoft.graph.requests.GraphServiceClient;

public class OneDrive {
	
	private static GraphServiceClient<Request> graphClient = null;
	
	public static void createObra(String folderName){
		DriveItem driveItem = new DriveItem();
		driveItem.name = folderName;
		Folder folder = new Folder();
		driveItem.folder = folder;
		
		graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().root().children().buildRequest()
		.post(driveItem);
	}
	
	public static void createVisita(String folderName, String parentFolderId) {
		DriveItem driveItem = new DriveItem();
		driveItem.name = folderName;
		Folder folder = new Folder();
		driveItem.folder = folder;

		graphClient.users("b9e1d304-a6b1-4d09-aa66-ece2bd6fb7b6").drive().items(parentFolderId).children()
				.buildRequest().post(driveItem);
	
	}
}
