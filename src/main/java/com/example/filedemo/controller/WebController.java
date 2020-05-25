package com.example.filedemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.filedemo.model.DBFile;
import com.example.filedemo.repository.DBFileRepository;

@Controller
@RequestMapping("/")
public class WebController {

	@Autowired 
	DBFileRepository dBFileRepository;
	@RequestMapping(value = { "listfiles" }, method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		List<DBFile> allfiles = dBFileRepository.findAll();
		model.addAttribute("allfiles", allfiles);
		return "listfiles";
	}
	
	@GetMapping("/")
	public String homePage() {
		return "index";
	}
	
	
	
	
}
