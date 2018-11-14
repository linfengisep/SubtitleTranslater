package com.subtitlor.beans;

public class TranslatedSubtitles {
	private int sceneId;
	private String timeLine;
	private String subtitleVT;
	
	public String getSubtitleVT() {
		return subtitleVT;
	}
	public void setSubtitleVT(String subtitleVT) {
		this.subtitleVT = subtitleVT;
	}
	
	public String getTimeLine() {
		return timeLine;
	}
	public void setTimeLine(String timeLine) {
		this.timeLine = timeLine;
	}
	public int getSceneId() {
		return sceneId;
	}
	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

}
