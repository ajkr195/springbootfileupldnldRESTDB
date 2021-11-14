package com.spring.boot.rocks.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.rocks.model.AppUserDocument;
import com.spring.boot.rocks.model.UploadedFile;
import com.spring.boot.rocks.repository.AppUserDocumentRepository;
import com.spring.boot.rocks.service.AppUserDocumentStorageService;

@Controller
@RequestMapping("/")
public class AppUserDocumentWebController {

	@Autowired
	AppUserDocumentRepository appUserDocumentRepository;
	
    @Autowired
    private AppUserDocumentStorageService appUserDocumentStorageService;
    
    private static final Logger logger = LoggerFactory.getLogger(AppUserDocumentWebController.class);

	@RequestMapping(value = { "listfiles" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		List<AppUserDocument> allfiles = appUserDocumentRepository.findAll();
		model.addAttribute("allfiles", allfiles);
		return "listfiles";
	}

	@GetMapping("/")
	public String homePage() {
		return "index";
	}
	
	@GetMapping("favicon.ico")
	@ResponseBody
	public void disableFavicon() {
	 //Method is void to avoid browser 404 issue by returning nothing in the response.
	}

	@RequestMapping("/upoad-withprogressbar")
	public void saveFile(HttpServletRequest servletRequest, @ModelAttribute UploadedFile uploadedFile,
			BindingResult bindingResult, Model model) {

		MultipartFile multipartFile = uploadedFile.getMultipartFile();
		String fileName = multipartFile.getOriginalFilename();
		try {
			AppUserDocument dbFile = appUserDocumentStorageService.storeFile(multipartFile);
			logger.info(dbFile.getFileName()  + " - Loaded to DB successfully.");
//			File file = new File(servletRequest.getServletContext().getRealPath("C:\\Users\\Ajay Kumar\\Desktop\\temp\\"), fileName);
//			multipartFile.transferTo(file);
//			AppUserDocument dbFile = appUserDocumentStorageService.storeFile(multipartFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/withprogressbar")
	public String inputProduct(Model model) {
		return "upoad-withprogressbar";
	}
	
	
}
