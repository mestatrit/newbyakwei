<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
	</head>
	<body>
		输入数据:
		<br />
		uid:
		<input type="text" name="name" value="<hk:value value="${uid}" onerow="true"/>"/>
		<br />
		name:
		<input type="text" name="name" value="<hk:value value="${name}" onerow="true"/>"/>
		<br />
		intro:
		<textarea rows="5" cols="50"><hk:value value="${intro}" textarea="true"/> </textarea>
		<br />
		select:
		<hk:select name="gender" checkedvalue="${gender}">
			<hk:option value="1" data="男"/>
			<hk:option value="2" data="女"/>
		</hk:select>
		<br />
		radio:
		<hk:radioarea name="aihao" checkedvalue="${aihao}">
			<hk:radio value="1" />足球
		<hk:radio value="2" />篮球
		</hk:radioarea>
		<br />
		checkbox:
		<hk:checkbox name="language" value="1" checkedvalues="${languagesvalue}" />
		英语
		<hk:checkbox name="language" value="2" checkedvalues="${languagesvalue}" />
		法语
		<hk:checkbox name="language" value="3" checkedvalues="${languagesvalue}" />
		德语
		<hk:checkbox name="language" value="4" checkedvalues="${languagesvalue}" />
		简体中文
		<br />
		<jsp:include page="inc.jsp"></jsp:include>
	</body>
</html>