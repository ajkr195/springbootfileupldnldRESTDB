package com.spring.boot.rocks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.boot.rocks.model.AppUserDocument;
import com.spring.boot.rocks.repository.AppUserDocumentRepository;

@Controller
@RequestMapping("/")
public class AppUserDocumentWebController {

	@Autowired 
	AppUserDocumentRepository appUserDocumentRepository;
	
	
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
	
	
	
	
}
