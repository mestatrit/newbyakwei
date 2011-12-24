<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<div id="content">
<table cellpadding="0" cellspacing="0" class="infotable">
	<tr>
		<td>联系人：</td>
		<td>${info.name }</td>
	</tr>
	<tr>
		<td>手机号码：</td>
		<td>${info.mobile }</td>
	</tr>
	<tr>
		<td>座机：</td>
		<td>${info.tel }</td>
	</tr>
	<tr>
		<td>E-mail：</td>
		<td>${info.email }</td>
	</tr>
	
</table>
</div>
<script type="text/javascript">
parent.updateuserinfo(document.getElementById("content").innerHTML);
parent.resetuserinfoact();
parent.setinfo_oid(${oid});
</script>