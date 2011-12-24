<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path=request.getContextPath(); %>
<hk:form oid="photofrm" onsubmit="return subphotofrm(this.id)" action="/e/op/auth/table/photo_updatephotoname.do" target="hideframe">
<hk:hide name="companyId" value="${companyId}"/>
<hk:hide name="oid" value="${oid}"/>
<table class="infotable" cellpadding="0" cellspacing="0">
	<tr>
		<td width="80px">标题</td>
		<td>
			<div class="f_l">
				<hk:text name="name" clazz="text" value="${cmpTablePhoto.name}"/>
				<div id="name_error" class="error"></div>
			</div>
			<div id="name_flag" class="flag"></div><div class="clr"></div>
		</td>
	</tr>
	<tr>
		<td></td>
		<td><hk:submit value="提交" clazz="btn"/>
		</td>
	</tr>
</table>
</hk:form>