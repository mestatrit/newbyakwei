对于数据库分布式访问的spring xml配置如下：

```

<context:annotation-config />
        <!-- 配置需要spring扫描的目录 -->
	<context:component-scan base-package="demo.haloweb.dev3g.model">
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Component" />
	</context:component-scan>
	<bean id="dataSource" class="halo.dao.sql.HkDataSourceWrapper">
		<property name="dataSourceMap">
			<map>
				<!-- 数据源对应的key -->
				<entry key="mysql_haloweb">
					<bean class="com.mchange.v2.c3p0.ComboPooledDataSource">
						<property name="driverClass" value="com.mysql.jdbc.Driver" />
						<property name="jdbcUrl" value="jdbc:mysql://127.0.0.1:3306/dbname?useUnicode=true&amp;characterEncoding=UTF-8" />
						<property name="user" value="root" />
						<property name="password" value="root" />
						<property name="idleConnectionTestPeriod" value="60" />
						<property name="maxPoolSize" value="20" />
						<property name="initialPoolSize" value="5" />
						<property name="minPoolSize" value="5" />
					</bean>
				</entry>
			</map>
		</property>
	</bean>
	<bean id="hkObjQuery" class="halo.dao.query.HkObjQuery">
		<property name="querySupport">
			<bean class="halo.dao.query.MysqlQuerySupport">
				<property name="dataSource" ref="dataSource" />
			</bean>
		</property>
	</bean>
	<bean id="query" class="halo.dao.query.SimpleQuery">
		<property name="hkObjQuery" ref="hkObjQuery" />
	</bean>

```