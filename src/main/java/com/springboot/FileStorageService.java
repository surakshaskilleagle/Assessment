package com.springboot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

//Defining a FileStorage as a component class for the application
@Component
public class FileStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileStorageService(StorageProperties properties) {
		//Retrieve the location of the folder where the files would be uploaded to
		this.rootLocation = Paths.get(properties.getLocation());
	}

	//Function to save the files to the given folder
	@Override
	public void store(MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
		} catch (IOException ioe) {
			throw new RuntimeException("Failed to store file: " + file.getOriginalFilename(), ioe);
		}
	}

	//Function to load a file as a resource
	@Override
	public Resource loadFileAsResource(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not find file: " + filename);
			}
		} catch (MalformedURLException mfe) {
			throw new RuntimeException("Could not read file: " + filename, mfe);
		}
	}

	//Function to delete the file
	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	//Init function to create a directory at the provided location
	@Override
	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException ioe) {
			throw new RuntimeException("Could not initialize storage ", ioe);
		}
	}
}
