package svrtest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.springframework.core.io.Resource;

public class HkSpringResource implements Resource {

	public Resource createRelative(String relativePath) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public File getFile() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFilename() {
		// TODO Auto-generated method stub
		return null;
	}

	public URI getURI() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public URL getURL() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isReadable() {
		// TODO Auto-generated method stub
		return false;
	}

	public long lastModified() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public InputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}