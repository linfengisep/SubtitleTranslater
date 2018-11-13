package com.subtitlor.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig
public class UploadingFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FILE_PATH= "/Users/linfengwang/file_upload/";
	private static final int SIZE = 10240;
     
    public UploadingFile() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/upload_file.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		uploading(request,response);
		doGet(request, response);
	}
	
	public void uploading(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
		System.out.println("button clicked.");
		try {
			System.out.println("entering try block.");
			if(request.getPart("myfile").getSize() != 0) {
				String desc = request.getParameter("desc");
				request.setAttribute("desc", desc);
				
				Part part = request.getPart("myfile");
				String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
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


}
