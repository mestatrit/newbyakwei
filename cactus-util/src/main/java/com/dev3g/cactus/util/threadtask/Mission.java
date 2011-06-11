package com.dev3g.cactus.util.threadtask;

public abstract class Mission implements Runnable {

	private String id;

	private TaskInovker taskInovker;

	public Mission(String id, TaskInovker taskInovker) {
		super();
		this.id = id;
		this.taskInovker = taskInovker;
	}

	public String getId() {
		return id;
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