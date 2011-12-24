<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.ZoneService"%><%@page import="com.hk.frame.util.HkUtil"%><%@page import="java.util.List"%><%@page import="com.hk.bean.Pcity"%><%@page import="com.hk.bean.Province"%><%@page import="com.hk.svr.CmpActService"%><%@page import="com.hk.bean.CmpActKind"%>
<%@page import="com.hk.bean.CmpAct"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:form oid="step_frm" onsubmit="return subcostfrm(this.id)" action="/e/op/auth/act_updatecost.do" target="hideframe">
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="costId" value="${costId}"/>
	<table class="infotable" cellpadding="0" cellspacing="0">
		<tr>
			<td width="90px">名称</td>
			<td>
				<hk:text name="name" clazz="text" maxlength="20" value="${o.name}"/>
				<div class="error" id="name_error"></div>
			</td>
		</tr>
		<tr>
			<td>费用</td>
			<td>
				<hk:text name="actCost" clazz="text_short_1" maxlength="10" value="${o.actCost}"/>元
				<div class="error" id="actCost_error"></div>
			</td>
		</tr>
		<tr>
			<td>说明</td>
			<td><span class="ruo">不能超过100字</span><br/>
				<hk:textarea name="intro" clazz="text_area" value="${o.intro}"/>
				<div class="error" id="intro_error"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="center"><hk:submit value="提交" clazz="btn"/></td>
		</tr>
	</table>
</hk:form>