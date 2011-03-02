package com.hk.frame.datasource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection代理,并不是实际的Connection,内部存储了多个实际的Connection
 * 
 * @author yuanwei
 */
public interface ConnectionProxy extends Connection {
	/**
	 * 获得当前使用的Connection
	 * 
	 * @return
	 * @see Connection
	 */
	Connection getCurrentConnection() throws SQLException;
}