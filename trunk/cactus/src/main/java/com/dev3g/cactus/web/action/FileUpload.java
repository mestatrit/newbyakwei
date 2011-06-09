package com.dev3g.cactus.web.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.dev3g.cactus.web.action.upload.cos.MultipartRequest;

public class FileUpload {

	private File[] files;

	private HkMultiRequest hkMultiRequest;

	private MultipartRequest multipartRequest;

	private UploadFile[] uploadFiles;

	private Map<String, UploadFile> uploadFileMap = new HashMap<String, UploadFile>();

	private static final String METHOD_POST = "POST";

	// 上传文件的总大小限制在15M，默认设置
	private int maxPostSize = 51 * 1024 * 1024;

	private static final int dv = 1024 * 1024;

	public FileUpload(HttpServletRequest request, String uploadFileTempPath)
			throws IOException {
		if (!(request.getMethod().equals(METHOD_POST) && HkMultiRequest
				.isMultipart(request))) {
			throw new RuntimeException(
					"not post method and multipart/form-data");
		}
		File dir = new File(uploadFileTempPath);
		if (!dir.isDirectory()) {
			if (!dir.mkdir()) {
				throw new IllegalArgumentException(
						"no permission on server to mkdir file [ "
								+ uploadFileTempPath + " ]");
			}
		}
		if (!dir.canWrite()) {
			throw new IllegalArgumentException(
					"no permission on server to operate file [ "
							+ uploadFileTempPath + " ]");
		}
		UploadFileCheckCnf checkCnf = (UploadFileCheckCnf) request
				.getAttribute(WebCnf.UPLOAD_LIMIT_SIZE_KEY);
		if (checkCnf == null) {
			multipartRequest = new MultipartRequest(request,
					uploadFileTempPath, maxPostSize, "utf-8");
		}
		else {
			multipartRequest = new MultipartRequest(request,
					uploadFileTempPath, checkCnf.getMaxSize() * dv, "utf-8");
		}
		this.hkMultiRequest = new HkMultiRequest(request, multipartRequest);
		Set<String> set = multipartRequest.getFileNames();
		List<File> fileList = new ArrayList<File>();
		List<UploadFile> uploadFileList = new ArrayList<UploadFile>();
		UploadFile uploadFile = null;
		for (String n : set) {
			File f = multipartRequest.getFile(n);
			if (f != null) {
				fileList.add(f);
				uploadFile = new UploadFile(n, f, this.getOriginalFileName(n));
				uploadFileList.add(uploadFile);
				uploadFileMap.put(n, uploadFile);
			}
		}
		if (fileList.size() > 0) {
			files = fileList.toArray(new File[fileList.size()]);
			uploadFiles = uploadFileList.toArray(new UploadFile[uploadFileList
					.size()]);
		}
	}

	public HkMultiRequest getHkMultiRequest() {
		return hkMultiRequest;
	}

	public File[] getFiles() {
		return files;
	}

	public File getFile(String name) {
		if (this.uploadFileMap.containsKey(name)) {
			return this.uploadFileMap.get(name).getFile();
		}
		return null;
	}

	public UploadFile[] getUploadFiles() {
		return uploadFiles;
	}

	public UploadFile getUploadFile(String name) {
		return this.uploadFileMap.get(name);
	}

	public String getOriginalFileName(String name) {
		if (this.uploadFileMap.containsKey(name)) {
			return this.uploadFileMap.get(name).getOriginName();
		}
		return null;
	}
}