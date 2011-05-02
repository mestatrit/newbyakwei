package cactus.web.action;

import java.util.Map;

import org.springframework.beans.BeansException;

import cactus.util.HkUtil;

public class ActionUtil {

	public static Map<String, Action> getBeansOfType() {
		Map<String, Action> map = null;
		try {
			map = HkUtil.getWebApplicationContext().getBeansOfType(
					Action.class, true, true);
		}
		catch (BeansException e) {
			try {
				map = HkUtil.getAnnotationconfigapplicationcontext()
						.getBeansOfType(Action.class, true, true);
			}
			catch (BeansException e1) {
				throw new RuntimeException(e);
			}
		}
		return map;
	}
}
