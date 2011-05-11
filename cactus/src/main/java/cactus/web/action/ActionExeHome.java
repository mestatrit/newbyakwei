package cactus.web.action;

public class ActionExeHome {

	private static ActionExe actionExe;

	public static ActionExe getActionExe() {
		return actionExe;
	}

	public static void setActionExe(ActionExe actionExe) {
		ActionExeHome.actionExe = actionExe;
	}
}