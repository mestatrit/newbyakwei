package unittest;

import com.dev3g.cactus.util.threadtask.Mission;
import com.dev3g.cactus.util.threadtask.TaskInovker;

public class TestMission implements Mission {

	private TaskInovker taskInovker;

	private String id;

	@Override
	public String setId(String id) {
		return this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setTaskInovker(TaskInovker taskInovker) {
		this.taskInovker = taskInovker;
	}

	@Override
	public void execute() {
		for (int i = 0; i < 10; i++) {
			System.out.println(this.id + i);
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		this.taskInovker.addSuccessSize(1);
	}

	@Override
	public void run() {
		this.execute();
	}
}