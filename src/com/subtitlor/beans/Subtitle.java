package com.subtitlor.beans;

public class Subtitle {
	private int id;
	private int sceneId;
	private String subtitleVO;
	private String subtitleVT;
	
	public String getSubtitleVT() {
		return subtitleVT;
	}
	public void setSubtitleVT(String subtitleVT) {
		this.subtitleVT = subtitleVT;
	}
	public String getSubtitleVO() {
		return subtitleVO;
	}
	public void setSubtitleVO(String subtitleVO) {
		this.subtitleVO = subtitleVO;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSceneId() {
		return sceneId;
	}
	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

	
}
