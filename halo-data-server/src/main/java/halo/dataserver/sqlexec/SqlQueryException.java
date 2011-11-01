package halo.dataserver.sqlexec;

public class SqlQueryException extends RuntimeException {

	private static final long serialVersionUID = -8333257204418273331L;

	public SqlQueryException() {
	}

	public SqlQueryException(String arg0) {
		super(arg0);
	}

	public SqlQueryException(Throwable arg0) {
		super(arg0);
	}

	public SqlQueryException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
