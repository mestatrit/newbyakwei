<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.frame.util.MessageUtil"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.web.util.JspDataUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();
JspDataUtil.loadCmpUnionMessage(request);%>
<script type="text/javascript">
var path="<%=path%>";
var user_login=false;
var ERR_CODE_USER_NOT_LOGIN=<%=Err.USER_NOT_LOGIN %>
<c:if test="${loginUser!=null}">
user_login=true;
var current_url=document.location;
</c:if>
</script>
<div class="user_mod">
</div>
<div class="top_nav">
	<div class="nav-1">
		<ul>
			<li>
				<a class="logo" href="#"></a>
			</li>
			<li class="${p1 }">
				<a href="#"></a>
			</li>
			<li class="${p2 }">
				<a href="#"></a>
			</li>
			<li class="${p3 }">
				<a href="#"></a>
			</li>
			<li class="${p4 }">
				<a href="#"></a>
			</li>
			<li>
				<a href="#"></a>
			</li>
			<li>
				<a href="#"></a>
			</li>
			<li class="end">
			</li>
		</ul>
	</div>
	<div class="clr"></div>
</div>
<c:if test="${not empty sys_msg || req_count>0 || notice_count>0}">
<div class="alert_info">
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="l">信息提示</td>
		<td class="r">
		<c:if test="${not empty sys_msg}"><div>${sys_msg }</div></c:if>
		<c:if test="${req_count>0}"><div><a href="<%=path %>/cmpunion/op/message_req.do?uid=${uid}&dealflg=0"><hk:data key="view.cmpunion.mgr.req_count" arg0="${req_count}"/></a></div></c:if>
		<c:if test="${notice_count>0}"><div><hk:data key="view.cmpunion.mgr.notice_count" arg0="${notice_count}"/></div></c:if>
		</td>
	</tr>
</table>
</div>
<br class="linefix"/>
</c:if>