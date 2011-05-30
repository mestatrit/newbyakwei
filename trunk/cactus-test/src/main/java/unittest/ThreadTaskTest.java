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
				public void onComplete() {
					System.out.println("<<<< === mission complete === >>>>");
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
