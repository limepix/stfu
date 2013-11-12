package org.devshots.stfu.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.devshots.stfu.UnknownFileException;
import org.devshots.stfu.service.FileKeeper;
import org.devshots.stfu.util.Config;
import org.devshots.stfu.util.OnlineFile;
import org.devshots.stfu.util.UploadBean;
import org.devshots.stfu.util.WebUtils;

@Controller
public class UploadController{
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@Autowired
	private FileKeeper fileKeeper;
	
	@Autowired
	private Config config;
	
	@RequestMapping(value="/file", method=RequestMethod.POST)
	public String upload(UploadBean uploadBean, 
			BindingResult bindingResult,
			HttpServletResponse response,
			HttpServletRequest request,
			Model model,
			RedirectAttributes redirectAttributes){
		
		logger.debug("receiving file...");
		
		if(bindingResult.hasErrors()){
			logger.warn("Errors occured during file upload / save request: ");
			for(ObjectError e: bindingResult.getAllErrors()){
				logger.info(e.getCode() + " - " + e.getDefaultMessage());
			}
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);			
			return "error";
		}
		
		//Recaptcha Check
		String remoteAddr = request.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("TODO");

        String challenge = request.getParameter("recaptcha_challenge_field");
        String uresponse = request.getParameter("recaptcha_response_field");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

        if (!reCaptchaResponse.isValid()) {
            redirectAttributes.addFlashAttribute("error", "please try again");
            return "redirect:/";
        }
		
		OnlineFile onlineFile;
		
		try {
			onlineFile = fileKeeper.add(uploadBean.getFile(), request);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return "error";
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
				
		return "redirect:/status/" + onlineFile.getId();
	}
	
	@RequestMapping("/status/{id}")
	public String preview(@PathVariable("id") String id,
			HttpServletResponse response,
			HttpServletRequest request,
			Model model) throws UnknownFileException{		
		logger.debug("viewing status from file " + id);
		
		OnlineFile onlineFile = fileKeeper.get(id);
		
		model.addAttribute("id", onlineFile.getId());
		Date validTill = new Date(onlineFile.getCreated().getTime() + config.getMaxTimeOnline());
		model.addAttribute("validTill", validTill.toString());
		
		model.addAttribute("webAppPath", WebUtils.getWebAppPath(request));
		
		return "uploaded";
	}
}
