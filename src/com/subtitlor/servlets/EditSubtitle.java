package com.subtitlor.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.TranslatedSubtitle;
import com.subtitlor.utilities.SubtitlesHandler;

@WebServlet("/EditSubtitle")
public class EditSubtitle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FILE_NAME = "/WEB-INF/password_presentation.srt";
	ServletContext context =null;
    SubtitlesHandler handler = new SubtitlesHandler();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		context = getServletContext();
		request.setAttribute("subtitles", loadingDataFromDB());
		this.getServletContext().getRequestDispatcher("/WEB-INF/edit_subtitle.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<TranslatedSubtitle> translatedSubtitles = new ArrayList<>();
		handler.updateTranslatedSubtitle(getTableValues(request));
		doGet(request,response);
	}
	
	public HashMap<Integer,String> getTableValues(HttpServletRequest request) {
		List<String> attributeNames = new ArrayList<>();
		HashMap<Integer,String> linesTranslated = new HashMap<Integer,String>();
		
		Enumeration paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) {
			attributeNames.add((String)paramNames.nextElement());
		}
		
		for(String s : attributeNames) {
			if(!s.isEmpty()) {
				String subtitleTranslated = (String)request.getParameter(s);
				if(! subtitleTranslated.trim().isEmpty()) {
					if(! linesTranslated.containsKey(Integer.parseInt(s))) {
						linesTranslated.put(Integer.parseInt(s), subtitleTranslated);
						//System.out.println("id:"+s+","+"Subtitle:"+subtitleTranslated);
					}
				}
			}
			
		}
		
		return linesTranslated;
	}
	
	public void loadingFileIntoDB(String fileName) {
		handler.saveFileDataToDB(fileName);
	}
	
	public List<Subtitle> loadingDataFromDB() {
		return handler.getSubtitles();
	}

}
