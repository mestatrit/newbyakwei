package unittest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bean.User;

import com.dev3g.cactus.dao.query.HkObjQuery;
import com.dev3g.cactus.dao.query.param.InsertParam;
import com.dev3g.cactus.util.P;
import com.dev3g.cactus.util.threadtask.InvokeCallback;
import com.dev3g.cactus.util.threadtask.TaskInovker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/query-test3.xml" })
// @Transactional
public class DataTest {

	public static void insertOneThread() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"query-test3.xml");
		HkObjQuery hkObjQuery = (HkObjQuery) context.getBean("hkObjQuery");
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			User user = new User();
			user.setNick("akweiweiweiweiweiweiwei" + i);
			user
					.setAddr("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			user
					.setIntro("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			user.setCreatetime(new Date());
			user.setSex(2);
			InsertParam insertParam = new InsertParam();
			hkObjQuery.insertObj(insertParam, user);
		}
		long end = System.currentTimeMillis();
		P.println("time : " + (end - begin));
	}

	public static void insertNThread() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"query-test3.xml");
		HkObjQuery hkObjQuery = (HkObjQuery) context.getBean("hkObjQuery");
		final long begin = System.currentTimeMillis();
		TaskInovker taskInovker = new TaskInovker();
		for (int i = 0; i < 100; i++) {
			InsertMission mission = new InsertMission();
			mission.setHkObjQuery(hkObjQuery);
			mission.setId("mission" + i);
			mission.setTaskInovker(taskInovker);
			List<User> list = new ArrayList<User>();
			for (int k = 0; k < 1000; k++) {
				User user = new User();
				user.setNick("akweiweiweiweiweiweiwei" + i);
				user
						.setAddr("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				user
						.setIntro("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				user.setCreatetime(new Date());
				user.setSex(2);
				list.add(user);
			}
			mission.setUserList(list);
			taskInovker.addMission(mission);
		}
		taskInovker.execute(new InvokeCallback() {

			@Override
			public void onComplete() {
				long end = System.currentTimeMillis();
				P.println("time : " + (end - begin));
			}
		});
	}

	public static void main(String[] args) throws Exception {
		 insertOneThread();
//		insertNThread();
	}
}
