package com.subtitlor.beans;

import java.util.List;

public class SubtitleItem {
	private int id;
	private String timeLine;
	private List<String> subtitles;
	
	public List<String> getSubtitles() {
		return subtitles;
	}
	public void setSubtitles(List<String> subtitles) {
		this.subtitles = subtitles;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTimeLine() {
		return timeLine;
	}
	public void setTimeLine(String timeLine) {
		this.timeLine = timeLine;
	}
	
	
	
}
