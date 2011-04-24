package iwant.util.apns;

import java.net.URL;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;

public class ApnsTool {

	public ApnsTool() {
	}

	private String p12FileName;

	private String p12FilePath;

	private int port;

	private String host;

	private String p12FileKey;

	public void setP12FileName(String p12FileName) {
		this.p12FileName = p12FileName;
	}

	public String getP12FilePath() {
		return p12FilePath;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getP12FileKey() {
		return p12FileKey;
	}

	public void setP12FileKey(String p12FileKey) {
		this.p12FileKey = p12FileKey;
	}

	public void sendNotification(String id, String deviceToken, PayLoad payLoad)
			throws Exception {
		if (this.p12FilePath == null) {
			URL url = Thread.currentThread().getContextClassLoader()
					.getResource("");
			String path = url.getPath();
			this.p12FilePath = path + this.p12FileName;
		}
		Device client = null;
		PushNotificationManager pushManager = PushNotificationManager
				.getInstance();
		try {
			pushManager.addDevice(id, deviceToken);
			client = pushManager.getDevice(id);
			pushManager.initializeConnection(this.host, this.port,
					this.p12FilePath, this.p12FileKey,
					SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
			pushManager.sendNotification(client, payLoad);
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			pushManager.stopConnection();
			pushManager.removeDevice(id);
		}
	}

	public static void main(String[] args) throws Exception {
		ApnsTool apnsTool = new ApnsTool();
		apnsTool.setHost("gateway.sandbox.push.apple.com");
		apnsTool.setPort(2195);
		apnsTool.setP12FileKey("");
		apnsTool.setP12FileName("devpush.p12");
		PayLoad payLoad = new PayLoad();
		apnsTool.sendNotification("akwei", "", payLoad);
	}
}