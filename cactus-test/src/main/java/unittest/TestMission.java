package unittest;

import com.dev3g.cactus.util.threadtask.Mission;
import com.dev3g.cactus.util.threadtask.TaskInovker;

public class TestMission extends Mission {

	public TestMission(String id, TaskInovker taskInovker) {
		super(id, taskInovker);
	}

	@Override
	public void execute() {
		for (int i = 0; i < 10; i++) {
			System.out.println(this.getId() + i);
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}
}