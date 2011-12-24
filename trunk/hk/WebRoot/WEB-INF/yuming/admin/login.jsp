<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/yuming/a.css" />
		<title>${o.name }</title>
	</head>
	<body>
	<div id="hk">
		<div>
		<c:if test="${nopermission}">
		<strong style="color: red;">用户名或密码错误</strong>
		</c:if>
		</div>
		<form method="post" action="<%=request.getContextPath() %>/yuming/login.do">
			<input type="hidden" name="ch" value="1"/>
			用户名：<input type="text" name="name"/><br/>
			密码：<input type="password" name="pwd"/><br/>
			<input type="submit" value="登录"/>
		</form>
	</div>
	</body>
</html>