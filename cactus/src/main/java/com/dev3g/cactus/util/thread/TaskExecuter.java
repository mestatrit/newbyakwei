package com.dev3g.cactus.util.thread;

public class TaskExecuter implements Runnable {

	private boolean success;

	private Mission mission;

	private String id;

	public TaskExecuter(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void acceptMission(Mission mission) {
		this.mission = mission;
	}

	public boolean isSuccess() {
		return success;
	}

	@Override
	public void run() {
		this.success = this.mission.execute();
	}
}