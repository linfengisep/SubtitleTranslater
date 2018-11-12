package com.subtitlor.dao;

import java.util.HashMap;
import java.util.List;

import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.SubtitleItem;

public interface SubtitleDao {
	List<Subtitle> getSubtitles();
	void updateSubtitles(HashMap<Integer,String> translatedSubtitles);
	void saveSubtitles(List<SubtitleItem> subtitleList);
}
