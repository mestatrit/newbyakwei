package unittest;

import org.junit.Test;

import cactus.util.thread.CompleteCallback;
import cactus.util.thread.TaskCommander;
import cactus.util.thread.TaskExecuter;

public class TaskText {

	@Test
	public void runTest() {
		Mission1 mission1 = new Mission1("mission - akwei");
		Mission1 mission12 = new Mission1("mission - apple jin");
		TaskExecuter taskExecuter = new TaskExecuter("akwei");
		taskExecuter.acceptMission(mission1);
		TaskExecuter taskExecuter2 = new TaskExecuter("apple jin");
		taskExecuter2.acceptMission(mission12);
		TaskCommander commander = new TaskCommander();
		commander.addTaskExecuter(taskExecuter);
		commander.addTaskExecuter(taskExecuter2);
		CompleteCallback callback = new CompleteCallback() {

			@Override
			public void onComplete() {
				try {
					Thread.sleep(2000);
					System.out.println("<<<< === mission complete === >>>>");
				}
				catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		};
		commander.setCompleteCallback(callback);
		commander.startMission();
	}
}
