package org.Kader.controller;

import org.Kader.entities.EmailMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
	
	@RequestMapping(value="/send",method = RequestMethod.POST)
	public String sendEail(@RequestBody EmailMessage emailMessage) {
		
		return "Email send successufly ";
		
	}

}