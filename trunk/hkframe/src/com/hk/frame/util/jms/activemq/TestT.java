package com.hk.frame.util.jms.activemq;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sendPostRequest("d://test2.xml",
				"http://219.142.222.130:9580/mig/hbsqt/sendmailinterface?act=sendEmail");
	}

	public static String sendPostRequest(String filepath, String url) {
		URL u;
		StringBuffer sb = new StringBuffer();
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		BufferedOutputStream out = null;
		try {
			u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			conn.setConnectTimeout(3000);
			conn.setReadTimeout(3000);
			conn.setDoOutput(true);
			out = new BufferedOutputStream(conn.getOutputStream());
			File file = new File(filepath);
			// �½��ļ���������������л���
			FileInputStream input = new FileInputStream(file);
			BufferedInputStream inBuff = new BufferedInputStream(input);
			byte[] buf = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
			out.flush();
			reader = new BufferedReader(new InputStreamReader(conn
					.getInputStream()));
			String s = null;
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
			input.close();
			inBuff.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (null != reader)
					reader.close();
				if (null != out)
					out.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
