<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<hk:form target="hideframe" oid="updatesortfrm" onsubmit="return subupdatesortfrm(this.id)" action="/e/op/auth/table_updatesort.do">
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="sortId" value="${sortId}"/>
	<table cellpadding="0" cellspacing="0" class="infotable">
		<tr>
			<td width="80px">名称</td>
			<td>
				<div class="f_l">
					<hk:text name="name" clazz="text" value="${sort.name}"/><br/>
					<div id="msg_update_error" class="error"></div>
				</div>
				<div id="msg_update_flag" class="flag"></div><div class="clr"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="center"><hk:submit value="提交" clazz="btn"/></td>
		</tr>
	</table>
</hk:form>