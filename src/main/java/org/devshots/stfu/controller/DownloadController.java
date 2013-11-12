package org.devshots.stfu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.devshots.stfu.UnknownFileException;
import org.devshots.stfu.service.FileKeeper;
import org.devshots.stfu.util.OnlineFile;

//TODO: Exception Analyse - Was muss geworfen werden? Welche Fehler bekommt der User?
@Controller
public class DownloadController {
	private static final Logger logger = LoggerFactory.getLogger(DownloadController.class);
	
	@Autowired
	private FileKeeper fileKeeper;
	
	@RequestMapping("/preview/{id}")
	public @ResponseBody String preview(@PathVariable("id") String id,
			HttpServletResponse response) throws UnknownFileException{		
		logger.debug("preview file " + id);
		handleRequest(id, response, true);
		return "";
	}
	
	@RequestMapping("/download/{id}")
	public @ResponseBody String download(@PathVariable("id") String id,
			HttpServletResponse response) throws UnknownFileException{
		
		logger.debug("download file " + id);		
		handleRequest(id, response, false);		
		return "";		
	}
	
	private String handleRequest(String id, HttpServletResponse response, boolean preview) throws UnknownFileException{
		
		OnlineFile onlineFile = fileKeeper.get(id);
		
		File file = new File(onlineFile.getFilename());
		
		response.setContentType(onlineFile.getContentType());
		response.setHeader("Content-Length", String.valueOf(onlineFile.getSize()));
		if(!preview)
			response.setHeader("Content-Disposition", "attachment; filename=\"" + onlineFile.getOriginalFileName() + "\"");
		
		FileInputStream iS;
		try {
			iS = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			logger.error("cant get file " + onlineFile.getOriginalFileName());
			throw new UnknownFileException();
		}
		
		try {
			IOUtils.copy(iS, response.getOutputStream());
			iS.close();
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (IOException e) {
			logger.error("error during copy stream from file to requesting client: " + e.getMessage());
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return "internal server error";
		}
		
		return "";
	}
}
