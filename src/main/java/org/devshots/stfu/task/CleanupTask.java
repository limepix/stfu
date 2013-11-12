package org.devshots.stfu.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.devshots.stfu.service.FileKeeper;

public class CleanupTask{
	private static final Logger logger = LoggerFactory.getLogger(CleanupTask.class);
	
	@Autowired
	private FileKeeper fileKeeper;	
	
	public void run(){
		logger.debug("cleanup task triggering filekeeper cleanup");
		fileKeeper.cleanUp();
	}
}
