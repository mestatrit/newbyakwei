<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
	</head>
	<body>
		我的资源文件：<br/>
		<hk:data key="hello.name" />
		<br />
		<hk:data key="hello.name2" arg0="${key}" />
		<br />
		<jsp:include page="inc.jsp"></jsp:include>
	</body>
</html>