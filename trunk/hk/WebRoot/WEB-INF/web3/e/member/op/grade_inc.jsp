<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<hk:form target="hideframe" oid="editgradefrm" onsubmit="return subeditgradefrm(this.id)" action="/e/op/auth/member_updategrade.do">
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="gradeId" value="${gradeId}"/>
	<table cellpadding="0" cellspacing="0" class="infotable">
		<tr>
			<td width="80px">名称</td>
			<td><hk:text name="name" clazz="text" value="${g.name}"/></td>
		</tr>
		<tr>
			<td width="80px">折扣</td>
			<td><hk:text name="rebate" clazz="text_short_4" value="${g.rebate}"/>折</td>
		</tr>
		<tr>
			<td></td>
			<td align="center">
			<hk:submit value="提交" clazz="btn"/>
			</td>
		</tr>
	</table>
</hk:form>