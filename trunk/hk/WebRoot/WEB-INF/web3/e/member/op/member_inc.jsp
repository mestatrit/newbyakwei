<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<hk:form target="hideframe" oid="editmemberfrm" onsubmit="return subeditmemberfrm(this.id)" action="/e/op/auth/member_update.do">
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="memberId" value="${memberId}"/>
	<table cellpadding="0" cellspacing="0" class="infotable">
		<tr>
			<td width="80px">级别</td>
			<td>
			<hk:select name="gradeId" checkedvalue="${m.gradeId}">
				<hk:option value="0" data="无"/>
				<c:forEach var="g" items="${gradelist}">
				<hk:option value="${g.gradeId}" data="${g.name}"/>
				</c:forEach>
			</hk:select>
			</td>
		</tr>
		<tr>
			<td width="80px">姓名</td>
			<td><hk:text name="name" clazz="text" value="${m.name}"/></td>
		</tr>
		<tr>
			<td width="80px">手机</td>
			<td><hk:text name="mobile" clazz="text" value="${m.mobile}"/></td>
		</tr>
		<tr>
			<td width="80px">E-mail</td>
			<td><hk:text name="email" clazz="text" value="${m.email}"/></td>
		</tr>
		<tr>
			<td></td>
			<td align="center">
			<hk:submit value="提交" clazz="btn"/>
			</td>
		</tr>
	</table>
</hk:form>