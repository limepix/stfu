package org.devshots.stfu.controller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatusController {
	//private static final Logger logger = LoggerFactory.getLogger(StatusController.class);
	
	@RequestMapping("/ok")
	public String uploaded(){
		
		return "ok";
	}
}
