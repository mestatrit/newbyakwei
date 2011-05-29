package com.dev3g.cactus.util.threadtask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dev3g.cactus.util.command.Invoker;
import com.dev3g.cactus.util.command.Receiver;

/**
 * 多线程任务执行，并发多个线程执行，然后合并结果返回
 * 
 * @author akwei
 */
public class TaskInovker implements Invoker, Receiver {

	private List<Mission> list = new ArrayList<Mission>();

	private Map<String, Mission> map = new HashMap<String, Mission>();

	private boolean missionCompleted;

	public void addMission(Mission mission) {
		map.put(mission.getId(), mission);
		list.add(mission);
	}

	public boolean isMissionCompleted() {
		return missionCompleted;
	}

	public void setMissionCompleted(boolean missionCompleted) {
		this.missionCompleted = missionCompleted;
	}

	public void execute(InvokeCallback invokeCallback) throws Exception {
		for (Mission mission : list) {
			new Thread(mission).start();
		}
		if (invokeCallback != null) {
			while (!this.isMissionCompleted()) {
			}
			invokeCallback.onComplete();
		}
	}
}