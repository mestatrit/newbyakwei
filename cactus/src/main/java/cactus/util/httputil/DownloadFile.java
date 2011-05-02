package cactus.util.httputil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

public class DownloadFile {

	public static void download(HttpServletResponse response, File file,
			String fileName) {
		// get the name of file to download or read
		response.setContentType("application/OCTET-STREAM;charset=utf-8");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes(), "iso-8859-1"));
		}
		catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		FileInputStream fis = null;
		BufferedOutputStream out = null;
		try {
			fis = new FileInputStream(file);
			out = new BufferedOutputStream(response.getOutputStream());
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fis.read(buffer)) != -1) {// read from the file on
				// server
				out.write(buffer, 0, len); // write to client
				out.flush();
			}
		}
		catch (FileNotFoundException e) {
			try {
				/**//*
					 * Clears any data that exists in the buffer as well
					 * as the status code and headers
					 */
				response.reset();
				/**//* set content type once again */
				response.setContentType("text/html;charset=utf-8");
				/**//* give error message to client */
				response.getWriter().println("文件未找到");
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (out != null) {
					out.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}