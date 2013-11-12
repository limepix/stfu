package org.devshots.stfu.util;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {
	
	public static String getWebAppPath(HttpServletRequest req){
		String scheme = req.getScheme();             // http
	    String serverName = req.getServerName();     // hostname.com
	    int serverPort = req.getServerPort();        // 80
	    String contextPath = req.getContextPath();   // /mywebapp

	    StringBuffer url =  new StringBuffer();
	    url.append(scheme).append("://").append(serverName);

	    if ((serverPort != 80) && (serverPort != 443)) {
	        url.append(":").append(serverPort);
	    }

	    url.append(contextPath);

	    return url.toString();
	}
}
