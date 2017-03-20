package com.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;

//Property class to maintain the uploaded file wrt to the parent folder of the application
@ConfigurationProperties("storage")
public class StorageProperties {
	//Name of the folder where the uploaded files are located
	private String location = "FilesUploaded";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
