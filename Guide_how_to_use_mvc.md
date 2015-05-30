<b>web.xml的相关配置</b>

```

<!-- 请在 web.xml中添加 -->
	<!-- 1，加载spring applicationContext.xml，文件名称可自定义 -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/classes/applicationContext.xml</param-value>
	</context-param>

	<!-- 2，启动spring -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

	<!-- 3，添加过滤器，目的是为了对上传文件与HttpServletRequest,HttpServletResponse进行包装 -->
	<filter>
		<filter-name>HttpWrapperFilter</filter-name>
		<filter-class>cactus.web.action.HttpWrapperFilter</filter-class>
	</filter>

	<!-- 4，添加运行过滤器，进行程序解析 -->
	<filter>
		<filter-name>ActionFilter</filter-name>
		<filter-class>cactus.web.action.ActionFilter</filter-class>
	</filter>

	<!-- 5，添加过滤器的url匹配，此匹配应该放到编码过滤器之后，其他过滤器之前 -->
	<filter-mapping>
		<filter-name>HttpWrapperFilter</filter-name>
		<url-pattern>*.do</url-pattern>
	</filter-mapping>

	<!-- 6，添加过滤器的url匹配，此匹配应该放到所有过滤器之后 -->
	<filter-mapping>
		<filter-name>ActionFilter</filter-name>
		<url-pattern>*.do</url-pattern>
		<dispatcher>REQUEST</dispatcher>
		<dispatcher>FORWARD</dispatcher>
	</filter-mapping>


-------------------------------------------------------------------------------



	

```