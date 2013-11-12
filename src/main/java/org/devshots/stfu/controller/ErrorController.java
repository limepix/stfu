package org.devshots.stfu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.devshots.stfu.UnknownFileException;

@Controller
public class ErrorController implements HandlerExceptionResolver{
	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
	
	@RequestMapping("/notFound")
	public String errorPage(Model model, HttpServletResponse response){
		logger.debug("displaying error page");
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		model.addAttribute("message", "not found");
		return "error";
	}
	
	@RequestMapping("/wrongRequestType")
	public String wrongRequestType(Model model, HttpServletResponse response){
		logger.debug("displaying wrong RequestType page");
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		model.addAttribute("message", "wrong request type");
		return "error";
	}
	
	@RequestMapping("/internalServerError")
	public String internalServerError(Model model, HttpServletResponse response){
		logger.debug("displaying wrong RequestType page");
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		model.addAttribute("message", "internal server error");
		return "error";
	}


	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		
		Map<String, Object> model = new HashMap<String, Object>();
        if (exception instanceof MaxUploadSizeExceededException){
            model.put("message", "the file is too big");
        } 
        else if(exception instanceof UnknownFileException){
        	model.put("message", "not found");
        }
        else{
            model.put("message", "unexpected error: " + exception.getMessage());
        }
        
        return new ModelAndView("error", model);
	}
}
