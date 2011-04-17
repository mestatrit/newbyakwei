package iwant.util.apns;

import javapns.data.PayLoad;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApnsToolMock extends ApnsTool {

	Log log = LogFactory.getLog(ApnsToolMock.class);

	@Override
	public void sendNotification(String id, String deviceToken, PayLoad payLoad)
			throws Exception {
		log.info("mock send notification");
	}
}