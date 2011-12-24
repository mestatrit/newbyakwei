<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request">忘记密码</c:set>
<c:set var="html_body_content" scope="request">
<div>
<form id="frm" method="post" onsubmit="return submailfrm(this.id)" action="<%=path %>/h4/pwd.do" target="hideframe">
<hk:hide name="ch" value="1"/>
<table cellpadding="0" cellspacing="0">
<tr>
	<td width="150px" align="right">
	</td>
	<td><span class="b"><hk:data key="view2.pwd.mailok" arg0="${email}"/></span></td>
</tr>
<tr>
	<td width="150px" align="right"></td>
	<td align="center">
		<br/>
		<a class="b" style="font-size: 20px;" href="/index">返回首页</a>
	</td>
</tr>
</table>
</form>
</div>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>