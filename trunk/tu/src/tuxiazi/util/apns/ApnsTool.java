package tuxiazi.util.apns;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;

public class ApnsTool {

	private String p12FilePath;

	private int port;

	private String host;

	private String p12FileKey;

	public String getP12FilePath() {
		return p12FilePath;
	}

	public void setP12FilePath(String p12FilePath) {
		this.p12FilePath = p12FilePath;
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
		Device client = null;
		PushNotificationManager pushManager = PushNotificationManager
				.getInstance();
		pushManager.addDevice(id, deviceToken);
		client = PushNotificationManager.getInstance().getDevice(id);
		PushNotificationManager.getInstance().initializeConnection(this.host,
				this.port, this.p12FilePath, this.p12FileKey,
				SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
		PushNotificationManager.getInstance().sendNotification(client, payLoad);
	}
}