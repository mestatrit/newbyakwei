package cactus.util.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskCommander {

	private boolean missionCompleted;

	private CompleteCallback completeCallback;

	public void setCompleteCallback(CompleteCallback completeCallback) {
		this.completeCallback = completeCallback;
	}

	private List<TaskExecuter> list = new ArrayList<TaskExecuter>();

	private Map<String, TaskExecuter> map = new HashMap<String, TaskExecuter>();

	public void addTaskExecuter(TaskExecuter taskExecuter) {
		map.put(taskExecuter.getId(), taskExecuter);
		list.add(taskExecuter);
	}

	public void removeTaskExecuter(String id) {
		TaskExecuter taskExecuter = map.remove(id);
		if (taskExecuter != null) {
			list.remove(taskExecuter);
		}
	}

	public void startMission() {
		for (TaskExecuter taskExecuter : list) {
			new Thread(taskExecuter).start();
		}
		if (this.completeCallback != null) {
			while (!this.isMissionCompleted()) {// 死循环直到所有任务完成
			}
			this.completeCallback.onComplete();
		}
	}

	public boolean isMissionCompleted() {
		for (TaskExecuter taskExecuter : this.list) {
			if (!taskExecuter.isSuccess()) {
				this.missionCompleted = false;
				break;
			}
			this.missionCompleted = true;
		}
		return missionCompleted;
	}
}
