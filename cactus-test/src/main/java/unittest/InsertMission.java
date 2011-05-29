package unittest;

import java.util.List;

import bean.User;

import com.dev3g.cactus.dao.query.HkObjQuery;
import com.dev3g.cactus.dao.query.param.InsertParam;
import com.dev3g.cactus.util.threadtask.Mission;

public class InsertMission extends Mission {

	private HkObjQuery hkObjQuery;

	private List<User> userList;

	public void setHkObjQuery(HkObjQuery hkObjQuery) {
		this.hkObjQuery = hkObjQuery;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	@Override
	public void execute() {
		for (User user : userList) {
			InsertParam insertParam = new InsertParam();
			this.hkObjQuery.insertObj(insertParam, user);
		}
	}
}
