package com.subtitlor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.SubtitleItem;

public class SubtitleDaoImpl implements SubtitleDao {
	DaoFactory daoFactory;
	
	public SubtitleDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public List<Subtitle> getSubtitles() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Subtitle> subtitles = new ArrayList<>();
		try {
			connection = daoFactory.getInstance().getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM Subtitles");
			while(resultSet.next()) {
				Subtitle subtitle = new Subtitle();
				subtitle.setId(resultSet.getInt("id"));
				subtitle.setSceneId(resultSet.getInt("sceneId"));
				subtitle.setSubtitleVO(resultSet.getString("subtitle"));
				subtitle.setSubtitleVT(resultSet.getString("subtitleTranslated"));
				subtitles.add(subtitle);
			}
		}catch(SQLException e) {	
		}finally {
			try {
				if(connection !=null) {
					connection.close();
				}
			}catch(SQLException e) {	
			}
		}
		return subtitles;
	}

	@Override
	public void updateSubtitles(HashMap<Integer,String> translatedSubtitles) {
		Connection connection =null;
		PreparedStatement preparedStatement = null;
		String queryUpdateSubtitle="update Subtitles set Subtitles.subtitleTranslated = ? where Subtitles.id = ?";
		try {
			connection = daoFactory.getInstance().getConnection();
				for(HashMap.Entry<Integer, String> entry : translatedSubtitles.entrySet()) {
					preparedStatement = connection.prepareStatement(queryUpdateSubtitle);
					preparedStatement.setString(1,entry.getValue());
					preparedStatement.setInt(2, entry.getKey());
					preparedStatement.executeUpdate();
					//System.out.println("entry value:"+entry.getValue()+"entry kay:"+entry.getKey());
				}
				connection.commit();
		}catch(SQLException e) {
			
		}finally {
			if(connection !=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void saveSubtitles(List<SubtitleItem> subtitleList) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		String queryTimeId = "INSERT INTO TimeId(sceneId,timeLine) VALUES(?,?)";
		String querySubtitle="INSERT INTO Subtitles(subtitle,subtitleTranslated,sceneId) VALUES(?,NULL,?)";
		try {
			connection = daoFactory.getInstance().getConnection();
			for(SubtitleItem item : subtitleList) {
				//save time and id;
				preparedStatement = connection.prepareStatement(queryTimeId);
				preparedStatement.setInt(1,item.getId());
				preparedStatement.setString(2, item.getTimeLine());
				preparedStatement.executeUpdate();
				//save subtitle;
				for(String s: item.getSubtitles()) {
					preparedStatement1 = connection.prepareStatement(querySubtitle);
					preparedStatement1.setString(1, s);	
					preparedStatement1.setInt(2, item.getId());
					preparedStatement1.executeUpdate();
				}
				connection.commit();
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			if(connection !=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
