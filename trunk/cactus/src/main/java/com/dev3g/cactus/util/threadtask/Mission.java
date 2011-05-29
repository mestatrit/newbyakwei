package com.dev3g.cactus.util.threadtask;

public abstract class Mission implements Runnable {

	private String id;

	private TaskInovker taskInovker;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setTaskInovker(TaskInovker taskInovker) {
		this.taskInovker = taskInovker;
	}

	public TaskInovker getTaskInovker() {
		return taskInovker;
	}

	public abstract void execute();

	@Override
	public void run() {
		this.execute();
		this.getTaskInovker().addSuccessSize(1);
	}
}