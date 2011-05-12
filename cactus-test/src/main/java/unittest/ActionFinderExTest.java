package unittest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import action.TestAction;
import cactus.web.action.Action;
import cactus.web.action.NoActionException;
import cactus.web.action.ex.ActionFinderEx;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/hkframe-web.xml" })
public class ActionFinderExTest {

	@Test
	public void test1() {
		ActionFinderEx ex = new ActionFinderEx();
		ex.setBasePath("action");
		try {
			Action action = ex.findAction("/test");
			if (!(action instanceof TestAction)) {
				Assert.fail();
			}
		}
		catch (NoActionException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void test2() {
		ActionFinderEx ex = new ActionFinderEx();
		ex.setBasePath("action");
		try {
			Action action = ex.findAction("/user/app/test");
			if (!(action instanceof action.user.app.TestAction)) {
				Assert.fail();
			}
		}
		catch (NoActionException e) {
			Assert.fail(e.getMessage());
		}
	}
}
