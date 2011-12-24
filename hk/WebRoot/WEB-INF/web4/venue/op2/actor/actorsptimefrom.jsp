<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="java.util.Date"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="actorId" value="${actorId}"/>
	<hk:hide name="oid" value="${oid}"/>
	<div class="infowarn" id="naverror"></div>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="150px" align="right">
				日期：
			</td>
			<td>
				<fmt:formatDate var="day" pattern="yyyy-MM-dd" value="${cmpActorSpTime.beginTime}"/>
				<c:if test="${day==null}">
				<fmt:formatDate var="day" pattern="yyyy-MM-dd" value="<%=new Date() %>"/>
				</c:if>
				<hk:text name="day" clazz="text datepicker" value="${day}"/> 
				<div class="infowarn" id="_day"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td><div>如果时间为一天，可以不用填写开始于结束时间</div></td>
		</tr>
		<tr>
			<td width="150px" align="right">
				开始时间：
			</td>
			<td>
				<fmt:formatDate var="begint" pattern="HH:mm" value="${cmpActorSpTime.beginTime}"/>
				<hk:text name="begint" clazz="text2" value="${begint}"/>格式为HH:mm
			</td>
		</tr>
		<tr>
			<td width="150px" align="right">
				结束时间：
			</td>
			<td>
				<fmt:formatDate var="endt" pattern="HH:mm" value="${cmpActorSpTime.endTime}"/>
				<hk:text name="endt" clazz="text2" value="${endt}"/>格式为HH:mm
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/>
				<a href="<%=path %>/h4/op/venue/actor_sptimelist.do?companyId=${companyId}&actorId=${actorId}">返回</a>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.CMPACTORSPTIME_TIME_ERROR %>={objid:"_day"};
function subfrm(frmid){
	setHtml('_day','');
	showGlass(frmid);
	return true;
}
</script>
