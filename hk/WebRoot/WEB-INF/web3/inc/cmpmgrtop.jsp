<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.frame.util.MessageUtil"%>
<%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.web.util.JspDataUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();
String msg = MessageUtil.getMessage(request);JspDataUtil.loadCompany(request);%>
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
				<a class="logo" href="http://<%=HkWebConfig.getWebDomain()%>">火酷</a>
			</li>
			<li>
				<a href="<%=path %>/e/op/op.do?companyId=${companyId }">企业信息</a>
			</li>
			<li >
				<a href="<%=path %>/e/op/product/op_productlistweb2.do?companyId=${companyId }">产品日常管理</a>
			</li>
			<li >
				<a href="<%=path %>/e/op/auth/member.do?companyId=${companyId }">会员管理</a>
			</li>
			<li >
				<a href="<%=path %>/e/op/orderform.do?companyId=${companyId }">订单管理</a>
			</li>
			<c:if test="${jsp_company.parentKindId==1}">
				<li >
					<a href="<%=path %>/e/op/auth/table_list2.do?companyId=${companyId }">安排座位</a>
				</li>
				<li >
					<a href="<%=path%>/e/op/auth/table_countinfo.do?companyId=${companyId}&dateflg=today">翻台率</a>
				</li>
			</c:if>
			<c:if test="${jsp_company.parentKindId!=1}">
				<li >
					<a href="#"></a>
				</li>
				<li >
					<a href="#"></a>
				</li>
			</c:if>
			<li class="end">
			</li>
		</ul>
	</div>
	<div class="clr"></div>
</div>
<%if (msg != null) { %>
<div class="alert_info">
<table cellpadding="0" cellspacing="0">
	<tr>
		<td class="l">信息提示</td>
		<td class="r"><%=msg %></td>
	</tr>
</table>
</div>
<br class="linefix"/>
<%}%>