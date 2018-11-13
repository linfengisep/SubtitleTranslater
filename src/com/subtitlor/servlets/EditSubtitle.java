package com.subtitlor.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
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
import javax.servlet.http.Part;

import com.subtitlor.beans.Subtitle;
import com.subtitlor.utilities.SubtitlesHandler;

@WebServlet("/edit")
public class EditSubtitle extends HttpServlet {
	private static final int SIZE = 10240;
	private static final long serialVersionUID = 1L;
	private static final String FILE_NAME = "/WEB-INF/password_presentation.srt";
	private static final String FILE_PATH= "/Users/linfengwang/file_upload/";
	ServletContext context =null;
    SubtitlesHandler handler = new SubtitlesHandler();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		context = getServletContext();
		request.setAttribute("subtitles", loadingDataFromDB());
		this.getServletContext().getRequestDispatcher("/WEB-INF/edit_subtitle.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean first =( request.getParameter("desc") == null);
		System.out.println("desc:?"+request.getParameter("desc"));
		uploading(request,response);
		handler.updateTranslatedSubtitle(getTableValues(request));
		doGet(request,response);
	}
	
	public void uploading(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
			try {
				if(request.getPart("myfile").getSize() != 0) {
					String desc = request.getParameter("desc");
					request.setAttribute("desc", desc);
					
					Part part = request.getPart("myfile");
					String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					request.setAttribute("fileName", fileName);
					
					saveFileLocal(request,part,fileName,FILE_PATH);	
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				if(! subtitleTranslated.trim().isEmpty()) {
					if(! linesTranslated.containsKey(Integer.parseInt(s))) {
						linesTranslated.put(Integer.parseInt(s), subtitleTranslated);
						System.out.println("id:"+s+","+"Subtitle:"+subtitleTranslated);
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

}
