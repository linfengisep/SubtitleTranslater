package com.subtitlor.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.subtitlor.beans.Subtitle;
import com.subtitlor.beans.TranslatedSubtitles;
import com.subtitlor.utilities.SubtitlesHandler;

@WebServlet("/edit")
@MultipartConfig
public class EditSubtitle extends HttpServlet {
	private static final int SIZE = 10240;
	private static final long serialVersionUID = 1L;
	//private static final String FILE_PATH= "../Subtitlor/WebContent/WEB-INF/";
	private static final String FILE_PATH= "/Users/linfengwang/Applications/subtitleTranslaterGit/Subtitlor/WebContent/WEB-INF/";
	private String filePathWithName = null;
	
	ServletContext context =null;
    SubtitlesHandler handler = new SubtitlesHandler();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		context = getServletContext();
		request.setAttribute("subtitles", loadingDataFromDB());
		this.getServletContext().getRequestDispatcher("/WEB-INF/edit_subtitle.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("export") != null) {
			exportTranslation();
		}else if(request.getParameter("uploading") !=null) {
			uploading(request,response);
			request.setAttribute("desc", request.getParameter("desc"));
			
			loadingFileIntoDB(filePathWithName);
		}else{
			handler.updateTranslatedSubtitle(getTableValues(request));	
		}
		doGet(request,response);
	}
	
	public void loadingFileIntoDB(String fileName) {
		handler.saveFileDataToDB(fileName);
	}
	
	public List<Subtitle> loadingDataFromDB() {
		return handler.getSubtitles();
	}
	
	public void exportTranslation() {
		String exportFileName = "subtitle_translation.srt";
		List<TranslatedSubtitles> listTranslatedSubtitles = new ArrayList<>();
		listTranslatedSubtitles.addAll(handler.getTranslatedSubtitles());
		List<String> listTranslatedSubtitleContents = new ArrayList<>();
		TranslatedSubtitles lastTranslatedSubtitles = listTranslatedSubtitles.get(0);
		
		if(listTranslatedSubtitles.get(0).getSubtitleVT() !=null) {
			//System.out.println(listTranslatedSubtitles.get(0).getSubtitleVT());
			listTranslatedSubtitleContents.add(String.valueOf(listTranslatedSubtitles.get(0).getSceneId()));
			listTranslatedSubtitleContents.add(listTranslatedSubtitles.get(0).getTimeLine());
			listTranslatedSubtitleContents.add(listTranslatedSubtitles.get(0).getSubtitleVT());
		}
		
		for(int i=1;i< listTranslatedSubtitles.size();i++) {
			if(listTranslatedSubtitles.get(i).getSceneId() == lastTranslatedSubtitles.getSceneId()) {
				//same scene; append this subtitle to the string list
				if(listTranslatedSubtitles.get(i).getSubtitleVT() !=null) {
					listTranslatedSubtitleContents.add(listTranslatedSubtitles.get(i).getSubtitleVT());
					//System.out.println(listTranslatedSubtitles.get(i).getSubtitleVT());
				}
			}else {
				//if the translation is not empty; add this object to string to the string list;
				if(listTranslatedSubtitles.get(i).getSubtitleVT() !=null) {
					//System.out.println(listTranslatedSubtitles.get(i).getSubtitleVT());
					listTranslatedSubtitleContents.add(String.valueOf(listTranslatedSubtitles.get(i).getSceneId()));
					listTranslatedSubtitleContents.add(listTranslatedSubtitles.get(i).getTimeLine());
					listTranslatedSubtitleContents.add(listTranslatedSubtitles.get(i).getSubtitleVT());
					
				}
			}
			lastTranslatedSubtitles=listTranslatedSubtitles.get(i);
		}
			
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		for(String s:listTranslatedSubtitleContents) {
			try {
				bao.write((String.format("%s \n", s)).getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		byte[] bytes = bao.toByteArray();
		InputStream inputStream = new ByteArrayInputStream(bytes);
		
		try {
			saveFileLocal(inputStream,exportFileName,FILE_PATH);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void uploading(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		try {
			if(request.getPart("myfile").getSize() != 0) {
				String desc = request.getParameter("desc");
				request.setAttribute("desc", desc);
				
				Part part = request.getPart("myfile");
				String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
				StringBuffer sb = new StringBuffer(FILE_PATH);
				sb.append(fileName);
				filePathWithName = sb.toString();
				request.setAttribute("fileName", fileName);
				saveFileLocal(request,part,fileName,FILE_PATH);	
			}else {
				System.out.println("file size:0");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveFileLocal(HttpServletRequest request,Part part,String fileName,String path) throws ServletException, IOException {
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			input = new BufferedInputStream(part.getInputStream(),SIZE);
			output = new BufferedOutputStream(new FileOutputStream(new File(path + fileName)), SIZE);
			byte[] byteBuffer = new byte[SIZE];
			int length;
			while((length = input.read(byteBuffer))>0) {
				output.write(byteBuffer,0,length);
			}
		}finally {
			try {
				if(input !=null) input.close();
			}catch(IOException ignore) {}
			try {
				if(output !=null) output.close();
			}catch(IOException ignore) {}
		}
	}

	public HashMap<Integer,String> getTableValues(HttpServletRequest request) throws ServletException, IOException{
		List<String> attributeNames = new ArrayList<>();
		HashMap<Integer,String> linesTranslated = new HashMap<Integer,String>();
		Enumeration paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) {
			attributeNames.add((String)paramNames.nextElement());
		}
		
		for(String s : attributeNames) {
			if(!s.isEmpty()) {
				String subtitleTranslated = (String)request.getParameter(s);
				//if(! subtitleTranslated.trim().isEmpty()) {}
				if(Pattern.matches("[0-9]?", s)) {
					if(! linesTranslated.containsKey(Integer.parseInt(s))) {
						linesTranslated.put(Integer.parseInt(s), subtitleTranslated);
					}	
				}
			}
		}
		
		return linesTranslated;
	}
	
	private void saveFileLocal(InputStream inputStream,String fileName,String path) throws ServletException, IOException {
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			input = new BufferedInputStream(inputStream,SIZE);
			output = new BufferedOutputStream(new FileOutputStream(new File(path + fileName)), SIZE);
			byte[] byteBuffer = new byte[SIZE];
			int length;
			while((length = input.read(byteBuffer))>0) {
				output.write(byteBuffer,0,length);
			}
		}finally {
			try {
				if(input !=null) input.close();
			}catch(IOException ignore) {}
			try {
				if(output !=null) output.close();
			}catch(IOException ignore) {}
		}
	}

}
