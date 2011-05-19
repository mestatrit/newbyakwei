<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld"
	prefix="hk"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>${name }</title>
	</head>
	<body>
		<form method="post" enctype="multipart/form-data" action="${appctx_path }/hello_upload">
			<input type="hidden" name="ch" value="1" />
			<input type="file" name="f" />
			<input type="submit" value="上传文件" />
		</form><br/>
		<form method="post" enctype="multipart/form-data" action="${appctx_path }/hello_upload2">
			<input type="hidden" name="ch" value="1" />
			<input type="file" name="f" />
			<input type="submit" value="上传文件2" />
		</form><br/>
		<jsp:include page="inc.jsp"></jsp:include>
	</body>
</html>
