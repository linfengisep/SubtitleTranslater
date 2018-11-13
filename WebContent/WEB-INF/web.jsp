<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xmlns="http://java.sun.com/xml/ns/javaee" 
xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" 
version="3.0">
	<servlet>
		<servlet-name>EditSubtitle</servlet-name>
		<servlet-class>com.subtitlor.servlets.EditSubtitle</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>EditSubtitle</servlet-name>
		<url-pattern>/edit</url-pattern>
	</servlet-mapping>
	
	<servlet>
		<servlet-name>UploadingFile</servlet-name>
		<servlet-class>com.subtitlor.servlets.UploadingFile</servlet-class>
		<multipart-config>
	    	<location>/Users/linfengwang/file_upload</location>
	    	<max-file-size>10485760</max-file-size> <!-- 10 Mo -->
	    	<max-request-size>52428800</max-request-size><!-- 5*10 Mo -->
	    	<file-size-threshold>1048576</file-size-threshold><!-- 1 Mo -->
	    </multipart-config>
	</servlet>
	<servlet-mapping>
		<servlet-name>UploadingFile</servlet-name>
		<url-pattern>/upload</url-pattern>
	</servlet-mapping>
	
  <jsp-config>
  	<jsp-property-group>
  		<url-pattern>*.jsp</url-pattern>
  		<include-prelude>/WEB-INF/taglib.jsp</include-prelude>
  	</jsp-property-group>
  </jsp-config>
  
</web-app>