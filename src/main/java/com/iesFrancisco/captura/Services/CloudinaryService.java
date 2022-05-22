package com.iesFrancisco.captura.Services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


@Service
public class CloudinaryService {

	Cloudinary cloudinary;

	private Map<String, String> valuesMap = new HashMap<String, String>();

	public CloudinaryService() {

		valuesMap.put( "cloud_name", "universidad-de-granada");
		valuesMap.put( "api_key", "934399457387251");
		valuesMap.put("api_secret", "Ni0kOLfhhEL3Amke3-9drCR_3ng");
		this.cloudinary = new Cloudinary(valuesMap);
	}
	
	public File convert(MultipartFile foto) throws IOException{
		File file = new File(foto.getOriginalFilename());
		FileOutputStream fo = new FileOutputStream(file);
		fo.write(foto.getBytes());
		fo.close();
		return file;
	}

	public Map upload(MultipartFile foto) throws IOException {
		File file = convert(foto);
		Map resulMap = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
		file.delete();
		return resulMap;
	}
	
	public Map delete(String id) throws IOException {
		Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;
	}

}