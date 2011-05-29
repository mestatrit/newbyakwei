package com.dev3g.cactus.util.threadtask;

public interface Mission extends Runnable {

	String getId();

	void setTaskInovker(TaskInovker taskInovker);
}
