package com.springboot;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

//Interface to perform creating and deleting operations of the uploaded file
public interface StorageService {
	
	public void store(MultipartFile file);

	public Resource loadFileAsResource(String filename);

	public void deleteAll();

	public void init();

}
