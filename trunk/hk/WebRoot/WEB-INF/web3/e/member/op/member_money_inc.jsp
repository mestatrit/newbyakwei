<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<hk:form target="hideframe" oid="moneyfrm" onsubmit="return submoneyfrm(this.id)" action="/e/op/auth/member_addmoney.do">
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="memberId" value="${memberId}"/>
	<div>
		<span class="split-r">姓名：${m.name }</span>
		<span class="split-r">余额：${m.money }</span>
	</div>
	<table cellpadding="0" cellspacing="0" class="infotable">
		<tr>
			<td width="80px">输入金额</td>
			<td><hk:text name="money" clazz="text" /></td>
		</tr>
		<tr>
			<td></td>
			<td align="center">
			<span class="split-r">
			<hk:submit value="增加" clazz="btn" name="add"/>
			</span>
			<hk:submit value="减少" clazz="btn" name="noadd"/>
			</td>
		</tr>
	</table>
</hk:form>