package unittest;

import com.dev3g.cactus.util.thread.Mission;

public class Mission1 implements Mission {

	private String name;

	public Mission1(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean execute() {
		for (int i = 0; i < 200; i++) {
			System.out.println(this.name + i);
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		return true;
	}
}
