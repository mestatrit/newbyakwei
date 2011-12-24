<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:form oid="tablefrm" onsubmit="return subtablefrm(this.id)" action="/op/orderform_ordertable.do" target="hideframe">
	<hk:hide name="oid" value="${oid}"/>
	<hk:hide name="tableId" value="${tableId}"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<table class="infotable" cellpadding="0" cellspacing="0">
		<tr>
			<td width="90px">就餐人数</td>
			<td>
				<hk:text name="num" clazz="text_short_2"/>
				<div class="error" id="num_error"></div>
			</td>
		</tr>
		<tr>
			<td width="90px">预计结束时间</td>
			<td>
				<hk:text name="time" clazz="text_short_2"/>时间格式为HH:mm
				<div class="error" id="time_error"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit value="view.submit" res="true" clazz="btn"/>
			</td>
		</tr>
	</table>
</hk:form>