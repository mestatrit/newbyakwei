package com.dev3g.cactus.util.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TaskExecutor {

	private boolean missionCompleted;

	private CompleteCallback completeCallback;
	private List<TaskExecuter> list = new ArrayList<TaskExecuter>();

	private Map<String, Mission> map = new HashMap<String, Mission>();
	public void setCompleteCallback(CompleteCallback completeCallback) {
		this.completeCallback = completeCallback;
	}
//	public void addTaskExecuter(Mission mission) {
//		map.put(taskExecuter.getId(), taskExecuter);
//		list.add(taskExecuter);
//	}
//
//	public void removeTaskExecuter(String id) {
//		TaskExecuter taskExecuter = map.remove(id);
//		if (taskExecuter != null) {
//			list.remove(taskExecuter);
//		}
//	}
}
