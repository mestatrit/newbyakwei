package svrtest;

import org.springframework.test.AbstractTransactionalSpringContextTests;

@SuppressWarnings("deprecation")
public class HkServiceTest extends AbstractTransactionalSpringContextTests {

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "app-core.xml" };
	}

	protected void commit() {
		this.transactionManager.commit(this.transactionStatus);
	}
}