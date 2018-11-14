package com.subtitlor.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.subtitlor.*;

public class DaoFactory {
	private String url;
	private String username;
	private String password;
	DaoFactory(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	public static DaoFactory getInstance() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
		}
		//TODO configure the dabasebase name, localhost and password according to your mysql configuration.
		//DaoFactory instance = new DaoFactory("jdbc:mysql://localhost:3306/Nom_BD?","Utilisateur?","mot de pass?");
		DaoFactory instance = new DaoFactory("jdbc:mysql://localhost:3306/SubtitlesDatabase","root","wlf5165804");
		return instance;
	}
	
	public Connection getConnection() throws SQLException {
		Connection connection =  DriverManager.getConnection(url, username, password);
		connection.setAutoCommit(false);
		return connection;
	}
	
	public SubtitleDao getSubtitleDao() {
		return new SubtitleDaoImpl(this);
	}
}
