package org.devshots.stfu.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.devshots.stfu.UnknownFileException;
import org.devshots.stfu.util.Config;
import org.devshots.stfu.util.OnlineFile;

@Service
public class FileKeeper implements InitializingBean{
	private static final Logger logger = LoggerFactory.getLogger(FileKeeper.class);
	
	@Autowired
	private Config config;
	
	private Map<String, OnlineFile> onlineFiles;
	
	private String TEMP_DIR; 
	
	public OnlineFile add(MultipartFile multipartFile,
			HttpServletRequest request) throws IllegalStateException, IOException{
		
		String id = UUID.randomUUID().toString();
		String fileName = TEMP_DIR + id + ".tmp";
				
		File file = new File(fileName);
		
		multipartFile.transferTo(file);
		
		OnlineFile onlineFile = new OnlineFile();	
		onlineFile.setId(id);
		onlineFile.setCreated(new Date());
		onlineFile.setContentType(multipartFile.getContentType());
		onlineFile.setOriginalFileName(multipartFile.getOriginalFilename());
		onlineFile.setSize(multipartFile.getSize());
		onlineFile.setFilename(fileName);		
		onlineFile.setRemoteAddr(request.getRemoteAddr());
		onlineFile.setHash(DigestUtils.md5Hex(multipartFile.getInputStream()));
		
		onlineFiles.put(id, onlineFile);
		
		logger.info("keeping file: " + onlineFile.toString());
		
		return onlineFile;
	}
	
	public OnlineFile get(String id) throws UnknownFileException{
		OnlineFile of = this.onlineFiles.get(id);
		if(of == null){
			throw new UnknownFileException();
		}		
		return of;
	}
	
	/**
	 * Läuft über alle Einträge und entfernt Einträge aus der internen Map
	 * <b>UND</b> löscht die Dateien im Dateisystem, die ihre Gültigkeit
	 * überschritten haben.
	 */
	public void cleanUp(){
		logger.debug("start cleanUp");
		List<String> keysToRemove = new ArrayList<String>();
		
		for(String key: this.onlineFiles.keySet()){
			if(this.onlineFiles.get(key).getTimeOnlineMillis() > config.getMaxTimeOnline()){
				keysToRemove.add(key);
			}
		}
		
		if(keysToRemove.size() > 0){
			logger.debug("removing " + keysToRemove.size() + " files");
			
			for(String keyToRemove: keysToRemove){
				OnlineFile onlineFile = this.onlineFiles.get(keyToRemove);
				File fileToRemove = new File(onlineFile.getFilename());
				fileToRemove.delete();
				synchronized (this.onlineFiles) {
					this.onlineFiles.remove(keyToRemove);
				}
			}		
			logger.info(keysToRemove.size() + " files removed");
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.onlineFiles = Collections.synchronizedMap(new HashMap<String, OnlineFile>());
		
		TEMP_DIR = System.getProperty("java.io.tmpdir");
		if(!TEMP_DIR.endsWith(File.separator)){
			TEMP_DIR += File.separator;
		}
		
		logger.info("temp dir will be: " + TEMP_DIR);
		
		logger.info("cleaning temp directory from previous runs...");
		FileUtils.cleanDirectory(new File(TEMP_DIR));
		
		logger.info("FileKeeper initialized");
		
		
	}
}
