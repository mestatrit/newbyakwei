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

	private int successSize = 0;

	private boolean missionCompleted;

	private InvokeCallback invokeCallback;

	public void addMission(Mission mission) {
		map.put(mission.getId(), mission);
		list.add(mission);
	}

	public synchronized void addSuccessSize(int add) {
		this.successSize = this.successSize + add;
		if (this.successSize == list.size()) {
			this.missionCompleted = true;
			this.notify();
		}
		else {
			this.missionCompleted = false;
		}
	}

	public boolean isMissionCompleted() {
		return this.missionCompleted;
	}

	public synchronized void execute(InvokeCallback invokeCallback)
			throws Exception {
		this.invokeCallback = invokeCallback;
		for (Mission mission : list) {
			new Thread(mission).start();
		}
		if (invokeCallback != null) {
			while (!this.isMissionCompleted()) {
				this.wait();
			}
			this.invokeCallback.onComplete();
		}
	}
}