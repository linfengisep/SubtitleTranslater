package com.subtitlor.dao;

import java.util.HashMap;
import java.util.List;

import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.SubtitleItem;
import com.subtitlor.beans.TranslatedSubtitles;

public interface SubtitleDao {
	List<Subtitle> getSubtitles();
	List<TranslatedSubtitles> getTranslatedSubtitles();
	void updateSubtitles(HashMap<Integer,String> translatedSubtitles);
	void saveSubtitles(List<SubtitleItem> subtitleList);
}
