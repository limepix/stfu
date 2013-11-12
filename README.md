stfu.
=====

 - Short Term File Uploader ...and not what you think...

What it is
----------

Once deployed on an application server like tomcat, stfu. is just a simple spring webservice where you can upload files to. They are only stored for a very short time - handy when you just want to share something via an url and don´t want to waste disk space on an online server. 

How to use it
-------------

* You can import this project in eclipse as "Maven Project". Eclipse should do the rest for you
* You have to create a recaptcha account and set the right keys (private and public) in UploadController.java and home.jsp
* Run "mvn package" and you´re ready to deploy the .war on a tomcat application server

Already deployed
----------------

The service is already running on my devshot machine. Feel free to use the service on http://devshots.org/stfu