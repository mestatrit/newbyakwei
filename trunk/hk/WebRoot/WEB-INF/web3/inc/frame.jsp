<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.frame.util.ServletUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();
ServletUtil.removeSessionValue(request, "op_func_msg");
String meta=(String)ServletUtil.getRequestValue(request,"meta_value");
String css=(String)ServletUtil.getRequestValue(request,"css_value");
String js=(String)ServletUtil.getRequestValue(request,"js_value");
String body_attr_value=(String)ServletUtil.getRequestValue(request,"body_attr_value");%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${html_title }</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /><%if(meta!=null){%><%=meta %><%} %>
		<link rel="stylesheet" type="text/css" href="<%=path%>/webst3/css/nav.css" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/webst3/css/mod.css" />
		<link type="text/css" rel="stylesheet" href="<%=path %>/webst3/css/hovertip.css" />
		<link rel="stylesheet" type="text/css" href="<%=path%>/webst3/css/hk3.css" />
		<%if(css!=null){%><%=css %><%} %>
		<script type="text/javascript" language="javascript" src="<%=path%>/webst3/js/pub.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path%>/webst3/js/jquery-1.3.2.min.js"></script><%if(js!=null){%><%=js %><%} %>
		<script type="text/javascript" language="javascript" src="<%=path%>/webst3/js/png.js"></script>
		<script src="<%=path %>/webst3/js/hovertip.min.js" type="text/javascript"></script>
		<!--[if IE 6]>
            <script type="text/javascript" src="js/png.js"></script>
            <script type="text/javascript">
            DD_belatedPNG.fix('.nav-a');
            DD_belatedPNG.fix('.home');
            DD_belatedPNG.fix('.left');
            DD_belatedPNG.fix('.right');
            DD_belatedPNG.fix('.fav');
            DD_belatedPNG.fix('.reply');
            DD_belatedPNG.fix('.rt');
            DD_belatedPNG.fix('.msg');
            DD_belatedPNG.fix('.follow');
            </script>
        <![endif]-->
        <c:if test="${script_content!=null }">${script_content }</c:if>
        <script type="text/javascript">
		$(document).ready(function(){
		    window.setTimeout(hovertipInit, 1);
		});
		</script>
	</head>
	<body <%if(body_attr_value!=null){ %><%=body_attr_value %><%} %> >
		<div id="body_div">
			<br class="linefix"/>
			<jsp:include page="top.jsp"></jsp:include>
			<div class="hk">${body_hk_content }</div>
			<jsp:include page="foot.jsp"></jsp:include>
		</div>
	</body>
</html>