package com.dev3g.cactus.util.thread;

/**
 * 任务类，士兵接受并执行命令
 * 
 * @author akwei
 */
public interface Mission {

	/**
	 * 任务执行接口
	 * 
	 * @return
	 */
	public boolean execute();
}
