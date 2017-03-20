package com.springboot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

//Controller class to perform Get, Post operations on the uploaded file
@Controller
@RequestMapping("/")
public class UploadController {

	private final StorageService storageService;

	@Autowired
	public UploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	List<String> files = new ArrayList<String>();

	@RequestMapping(method = RequestMethod.GET)
	public String listUploadedFiles(Model model) {
		return "uploadForm";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
		try {
			storageService.store(file);
			model.addAttribute("message", "Successfully uploaded file: " + file.getOriginalFilename());
			files.add(file.getOriginalFilename());
		} catch (Exception ex) {
			model.addAttribute("message", "Failed to upload file: " + file.getOriginalFilename());
		}
		return "uploadForm";
	}
}
