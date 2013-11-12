package org.devshots.stfu.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
	
	@Value("$(config.maxTimeOnline)")
	private long maxTimeOnline;
	
	@Value("$(config.maxUploadSize)")
	private long maxUploadSize;

	@Value("$(config.maxInMemorySize)")
	private long maxInMemorySize;

	@Value("$(config.checkInterval)")
	private long checkInterval;

	public long getMaxTimeOnline() {
		return maxTimeOnline;
	}

	public void setMaxTimeOnline(long maxTimeOnline) {
		this.maxTimeOnline = maxTimeOnline;
	}

	public long getMaxUploadSize() {
		return maxUploadSize;
	}

	public void setMaxUploadSize(long maxUploadSize) {
		this.maxUploadSize = maxUploadSize;
	}

	public long getMaxInMemorySize() {
		return maxInMemorySize;
	}

	public void setMaxInMemorySize(long maxInMemorySize) {
		this.maxInMemorySize = maxInMemorySize;
	}

	public long getCheckInterval() {
		return checkInterval;
	}

	public void setCheckInterval(long checkInterval) {
		this.checkInterval = checkInterval;
	}
	
	
}
