<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" version="3.0">
	<servlet>
		<servlet-name>EditSubtitle</servlet-name>
		<servlet-class>com.subtitlor.servlets.EditSubtitle</servlet-class>
	</servlet>
	
	<servlet-mapping>
		<servlet-name>EditSubtitle</servlet-name>
		<url-pattern>/edit</url-pattern>
	</servlet-mapping>
	
	<servlet>
		<servlet-name>Test</servlet-name>
		<servlet-class>com.subtitlor.servlets.Test</servlet-class>
	</servlet>
	
	<servlet-mapping>
		<servlet-name>Test</servlet-name>
		<url-pattern>/test</url-pattern>
	</servlet-mapping>
  
  <jsp-config>
  	<jsp-property-group>
  		<url-pattern>*.jsp</url-pattern>
  		<include-prelude>/WEB-INF/taglib.jsp</include-prelude>
  	</jsp-property-group>
  </jsp-config>
  
</web-app>