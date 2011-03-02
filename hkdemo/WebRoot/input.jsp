<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
	</head>
	<body>
		<form action="<%=request.getContextPath()%>/hello_method1" method="post">
			输入数据:
			<br />
			uid:
			<input type="text" name="uid" />
			<br />
			name:
			<input type="text" name="name" />
			<br />
			intro:
			<textarea rows="5" cols="50" name="intro"></textarea>
			<br />
			select:
			<select name="gender">
				<option value="1">
					男
				</option>
				<option value="2">
					女
				</option>
			</select>
			<br />
			radio:
			<input type="radio" name="aihao" value="1" />
			足球
			<input type="radio" name="aihao" value="2" />
			篮球
			<br />
			checkbox:
			<input type="checkbox" name="language" value="1" />
			英语
			<input type="checkbox" name="language" value="2" />
			法语
			<input type="checkbox" name="language" value="3" />
			德语
			<input type="checkbox" name="language" value="4" />
			简体中文
			<br />
			<input type="submit" value="提交" />
		</form>
		<br />
		<jsp:include page="inc.jsp"></jsp:include>
	</body>
</html>