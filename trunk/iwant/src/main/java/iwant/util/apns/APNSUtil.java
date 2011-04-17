package iwant.util.apns;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;

/**
 * ios push notification tool
 * 
 * @author akwei
 */
public class APNSUtil {

	// device token: 4df3198d 444d7b45 ff6d34c5 d83e8019 0c9eaac0 2b695c7d
	// 5ff3b8a6 c3884d71
	// 4df3198d444d7b45ff6d34c5d83e80190c9eaac02b695c7d5ff3b8a6c3884d71
	public static void sendNotification(String id, String deviceToken)
			throws Exception {
		PayLoad simplePayLoad = new PayLoad();
		simplePayLoad.addAlert("My alert 你好 oo");
		simplePayLoad.addBadge(5);
		simplePayLoad.addSound("default");
		Device client = null;
		PushNotificationManager pushManager = PushNotificationManager
				.getInstance();
		pushManager.addDevice(id, deviceToken);
		client = PushNotificationManager.getInstance().getDevice(id);
		PushNotificationManager.getInstance().initializeConnection(
				"gateway.sandbox.push.apple.com", 2195,
				"/Users/fire9/mykey/key2/devpush.p12", "asdasd",
				SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
		PushNotificationManager.getInstance().sendNotification(client,
				simplePayLoad);
	}

	public static void main(String[] args) throws Exception {
		String deviceToken = "4df3198d444d7b45ff6d34c5d83e80190c9eaac02b695c7d5ff3b8a6c3884d71";
		APNSUtil.sendNotification("akwei_iphone", deviceToken);
	}
}
