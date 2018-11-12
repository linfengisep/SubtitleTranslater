package com.subtitlor.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.SubtitleItem;
import com.subtitlor.dao.DaoFactory;
import com.subtitlor.dao.SubtitleDaoImpl;

public class SubtitlesHandler {
	private List<SubtitleItem> subtitleItems = new ArrayList<>();
	
	SubtitleDaoImpl subtitleDaoImpl = new SubtitleDaoImpl(DaoFactory.getInstance());
	
	public void saveFileDataToDB(String fileName) {
		new ArrayList<String>();
		new ArrayList<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line;
			ArrayList<String> itemlines = new ArrayList<>();
			int sceneNumberAppear = 0;
			while ((line = br.readLine()) != null) {
				if(! line.trim().isEmpty()) {
						if(analyseSubtitle(line) == SceneType.SCENE_ID) {
							sceneNumberAppear++;
						}
						itemlines.add(line);
						if(sceneNumberAppear==2) {
							subtitleItems.add(getSubtitleType(itemlines));
							itemlines.clear();//clean the array
							itemlines.add(line);//add this sceneId;
							sceneNumberAppear=1;
						}	
				}
			}
			br.close();
			//save the subtitle in database;
			subtitleDaoImpl.saveSubtitles(subtitleItems);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Subtitle> getSubtitles() {
		return subtitleDaoImpl.getSubtitles();
	}
	
	public void updateTranslatedSubtitle(HashMap<Integer,String> subtitleTranslatedList){
		subtitleDaoImpl.updateSubtitles(subtitleTranslatedList);
	}
	
	
	public SubtitleItem getSubtitleType (List<String> lines) {
		SubtitleItem item = new SubtitleItem();
		ArrayList<String>subtitles = new ArrayList<>();
		for(String line:lines) {
			switch(analyseSubtitle(line)) {
			case SCENE_ID:
				item.setId(Integer.parseInt(line));
				break;
			case TIME_LINE:
				item.setTimeLine(line);
				break;
			case SUBTITLE:
				subtitles.add(line);
				break;
				default:break;
			}
		}
		item.setSubtitles(subtitles);
		return item;
	}
	
	/*
	 * Analyse the subtitles.
	 * #param:every input line
	 * #renvoi:type
	 * */
	public SceneType analyseSubtitle(String line) {
	      String sceneNumber = "[0-9]*";
	      String sceneTimeLine="-->";
	      String sceneSubtitleContent="\\D*";
	      String patterns [] = {sceneNumber,sceneSubtitleContent};

	      for(String regex : patterns) {
	         if(Pattern.matches(regex,line)) {
	            if(regex.equals(sceneNumber)){ return SceneType.SCENE_ID; }
	            if(regex.equals(sceneSubtitleContent)){ return SceneType.SUBTITLE;}
	         }
	      }
	      if(line.contains(sceneTimeLine)){ return SceneType.TIME_LINE;}

	      return SceneType.OTHER;
	   }
}

enum SceneType{
	SCENE_ID,
	TIME_LINE,
	SUBTITLE,
	OTHER
}
