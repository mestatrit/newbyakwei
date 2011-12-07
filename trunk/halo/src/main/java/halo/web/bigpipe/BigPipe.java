package halo.web.bigpipe;

import halo.util.P;
import halo.web.action.HttpStringResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BigPipe {

	private HttpServletRequest request;

	private HttpServletResponse response;

	private PrintWriter printWriter;

	private final List<Callable<Boolean>> taskList = new ArrayList<Callable<Boolean>>();

	public List<Callable<Boolean>> getTaskList() {
		return taskList;
	}

	public BigPipe(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		try {
			this.printWriter = response.getWriter();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void addView(final String key, final String path) {
		taskList.add(new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				if (printWriter.checkError()) {
					return false;
				}
				HttpStringResponse httpStringResponse = new HttpStringResponse(
						response);
				request.getRequestDispatcher(path).forward(request,
						httpStringResponse);
				P.println(path);
				String value = httpStringResponse.getString();
				P.println(value);
				writeValue(key, value);
				return true;
			}
		});
	}

	public void addValue(final String key, final String value) {
		taskList.add(new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				if (printWriter.checkError()) {
					return false;
				}
				writeValue(key, value);
				return true;
			}
		});
	}

	protected synchronized void writeValue(String key, String value) {
		printWriter.write("<script>halo_bp_write(\"" + key + "\",\"" + value
				+ "\")</script>");
		printWriter.flush();
	}
}
