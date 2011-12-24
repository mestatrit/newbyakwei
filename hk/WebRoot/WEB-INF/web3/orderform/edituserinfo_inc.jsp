<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<hk:form oid="userinfofrm" action="/op/orderform_updateuserinfo.do" target="hideframe" onsubmit="return subuserinfofrm(this.id)">
<div id="userinfo">
<div>
<hk:select name="list" onchange="changeuserinfo(this.value)">
	<c:forEach var="info" items="${infolist}">
		<hk:option value="${info.oid}" data="${info.title}"/>
	</c:forEach>
</hk:select> <a href="javascript:tocreateuserinfo()">新增联系信息</a>  <span id="userinfo2_tip"></span> 
</div>
<div id="userinfo2">
<hk:hide name="oid" value="${maininfo.oid}"/>
<table cellpadding="0" cellspacing="0" class="infotable">
	<tr>
		<td>联系人：</td>
		<td><hk:text name="name" clazz="text" maxlength="20" value="${maininfo.name}"/></td>
	</tr>
	<tr>
		<td>手机号码：</td>
		<td><hk:text name="mobile" clazz="text" maxlength="15" value="${maininfo.mobile}"/></td>
	</tr>
	<tr>
		<td>座机：</td>
		<td><hk:text name="tel" clazz="text" maxlength="15" value="${maininfo.tel}"/></td>
	</tr>
	<tr>
		<td>E-mail：</td>
		<td><hk:text name="email" clazz="text" maxlength="15" value="${maininfo.email}"/></td>
	</tr>
	<tr>
		<td></td>
		<td><hk:submit value="保存个人信息" clazz="btn"/></td>
	</tr>
	<tr>
		<td></td>
		<td><div class="error" id="userinfo_msg_error"></div></td>
	</tr>
</table>
</div>
</div>
</hk:form>