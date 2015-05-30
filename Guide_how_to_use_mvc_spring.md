
```

<!-- 在spring的配置文件中添加 -->
<context:annotation-config />
	<!-- 通过annotation 扫描的根目录 -->
	<context:component-scan base-package="demo.haloweb.dev3g.web">
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Component" />
	</context:component-scan>
	<!-- must config -->
	<bean id="haloUtil" class="halo.util.HaloUtil" />
	<bean class="halo.web.action.ExceptionConfig">
		<property name="exceptionMap">
			<map>
				<entry key="java.lang.Exception" value="/web/error.jsp">
				</entry>
			</map>
		</property>
	</bean>
	<bean id="webCnf" class="halo.web.util.WebCnf">
		<!-- 设置文件上传的临时目录 -->
		<property name="uploadFileTempPath" value="/Users/fire9/temp/" />
		<!-- 是否需要进行字符编码转换 -->
		<property name="needCharsetEncode" value="true" />
		<!-- 原编码 -->
		<property name="sourceCharset" value="iso-8859-1" />
		<!-- 目标编码 -->
		<property name="targetCharset" value="utf-8" />
		<!-- 强制url进行上传文件检查，不通过配置的url不能接收文件上传 -->
		<property name="mustCheckUpload" value="true" />
		<!-- 接收文件检查的url 格式：/actionname/method:[file size](单位为M) -->
		<property name="fileUploadCheckUriCnfList">
			<list>
				<value>/hello/upload:20</value>
			</list>
		</property>
	</bean>

```