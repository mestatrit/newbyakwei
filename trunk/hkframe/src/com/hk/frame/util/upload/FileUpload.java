package com.hk.frame.util.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.hk.frame.util.upload.cos.MultipartRequest;

public class FileUpload {

	public static final String TEMP_PATH = "/temp/";

	private File[] files;

	private File dir_ = new File(TEMP_PATH);

	private HkMultiRequest hkMultiRequest;

	private MultipartRequest multipartRequest;

	private final String METHOD_POST = "POST";

	// 上传文件的总大小限制在15M
	private int maxPostSize = 51 * 1024 * 1024;

	public FileUpload(HttpServletRequest request) throws IOException {
		if (!(request.getMethod().equals(METHOD_POST) && HkMultiRequest
				.isMultipart(request))) {
			throw new RuntimeException(
					"not post method and multipart/form-data");
		}
		if (!dir_.isDirectory())
			dir_.mkdir();
		if (!dir_.canWrite())
			throw new IllegalArgumentException(
					"no permission on server to operate file [ "
							+ FileUpload.TEMP_PATH + " ]");
		multipartRequest = new MultipartRequest(request, TEMP_PATH,
				maxPostSize, "utf-8");
		this.hkMultiRequest = new HkMultiRequest(request, multipartRequest);
		Set<String> set = multipartRequest.getFileNames();
		List<File> fileList = new ArrayList<File>();
		// for (String n : set) {
		// fileList.add(multipartRequest.getFile(n));
		// }
		// files = fileList.toArray(new File[fileList.size()]);
		for (String n : set) {
			File f = multipartRequest.getFile(n);
			if (f != null) {
				fileList.add(f);
			}
		}
		if (fileList.size() > 0) {
			files = fileList.toArray(new File[fileList.size()]);
		}
	}

	public HkMultiRequest getHkMultiRequest() {
		return hkMultiRequest;
	}

	public File[] getFiles() {
		return files;
	}

	public File getFile(String name) {
		return multipartRequest.getFile(name);
	}

	public String getOriginalFileName(String name) {
		return this.multipartRequest.getOriginalFileName(name);
	}
}