package com.hk.svr;

import java.util.List;
import com.hk.bean.InfoSmsPort;
import com.hk.svr.user.exception.NoSmsPortException;

public interface InfoSmsPortService {
	void createSmsPort(InfoSmsPort smsPort);

	InfoSmsPort getSmsPort(String portNumber);

	List<InfoSmsPort> getUserSmsPortList(long userId);

	// void createUserSmsPort(long userId, long portId, Date overTime);
	void clearUserId(long portId);

	InfoSmsPort getAvailableInfoSmsPort() throws NoSmsPortException;

	void updateInfoSmsPort(InfoSmsPort infoSmsPort);

	InfoSmsPort getInfoSmsPort(long portId);
}