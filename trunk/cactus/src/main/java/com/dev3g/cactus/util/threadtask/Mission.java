package com.dev3g.cactus.util.threadtask;

public interface Mission extends Runnable {

	String getId();

	String setId(String id);

	void setTaskInovker(TaskInovker taskInovker);

	void execute();
}
