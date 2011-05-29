package unittest;

import org.junit.Assert;
import org.junit.Test;

import com.dev3g.cactus.util.threadtask.InvokeCallback;
import com.dev3g.cactus.util.threadtask.TaskInovker;

public class ThreadTaskTest {

	@Test
	public void testMission() {
		TaskInovker taskInovker = new TaskInovker();
		for (int i = 0; i < 10; i++) {
			TestMission testMission = new TestMission();
			testMission.setId(i + " akwei ");
			testMission.setTaskInovker(taskInovker);
			taskInovker.addMission(testMission);
		}
		try {
			taskInovker.execute(new InvokeCallback() {

				@Override
				public void onComplete() throws Exception {
					try {
						Thread.sleep(2000);
						System.out
								.println("<<<< === mission complete === >>>>");
					}
					catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			});
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
