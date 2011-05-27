package cactus.util.thread;

/**
 * 多线程任务结束后的回调接口
 * 
 * @author akwei
 */
public interface CompleteCallback {

	/**
	 * 当所有任务执行完毕时，回调接口方法
	 */
	void onComplete();
}