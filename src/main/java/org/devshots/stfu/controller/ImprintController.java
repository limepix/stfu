package org.devshots.stfu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ImprintController {
	
	@RequestMapping("/imprint")
	public String imprint(){
		return "imprint";
	}
}
