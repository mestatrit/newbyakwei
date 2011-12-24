<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CmpNav"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpBbsKind"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<script type="text/javascript" src="<%=path %>/cmpwebst4/js/date/date.js"></script>
<script type="text/javascript" src="<%=path %>/cmpwebst4/js/date/jquery.datePicker.min-2.1.2.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/cmpwebst4/js/date/datePicker.css">
<form id="frm" method="post" onsubmit="return subfrm(this.id)" action="${form_action }" target="hideframe">
	<hk:hide name="ch" value="1"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<hk:hide name="boxId" value="${boxId}"/>
	<table class="nt all" cellpadding="0" cellspacing="0">
		<tr>
			<td width="160px" align="right">
				名称：
			</td>
			<td>
				<hk:text name="name" clazz="text" value="${box.name}"/> <br/>
				<div class="infowarn" id="_name"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				地区：
			</td>
			<td>
				<div style="margin-bottom: 10px">
				<jsp:include page="../inc/zonesel.jsp"></jsp:include>
				<script type="text/javascript">
				initselected(${box.cityId});
				</script>
				</div>
				<div>（不选择默认为全国发布）</div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				总数量：
			</td>
			<td>
				<hk:text name="totalCount" clazz="text" value="${box.totalCount}"/><br/>
				<div class="infowarn" id="_totalcount"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				开始时间：
			</td>
			<td>
				<c:set var="bdate"><fmt:formatDate value="${box.beginTime}" pattern="yyyy-MM-dd"/></c:set>
				<c:set var="bhour"><fmt:formatDate value="${box.beginTime}" pattern="HH"/></c:set>
				<c:set var="bmin"><fmt:formatDate value="${box.beginTime}" pattern="mm"/></c:set>
				<hk:text name="bdate" value="${bdate}" maxlength="12" clazz="text2 date-pick1"/>
				<hk:text name="bhour" maxlength="2" value="${bhour}" clazz="text2" style="width:50px"/>时
				<hk:text name="bemin" maxlength="2" value="${bmin}" clazz="text2" style="width:50px"/>分<br/>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				结束时间：
			</td>
			<td>
				<c:set var="edate"><fmt:formatDate value="${box.endTime}" pattern="yyyy-MM-dd"/></c:set>
				<c:set var="ehour"><fmt:formatDate value="${box.endTime}" pattern="HH"/></c:set>
				<c:set var="emin"><fmt:formatDate value="${box.endTime}" pattern="mm"/></c:set>
				<hk:text name="edate" value="${edate}" maxlength="12" clazz="text2 date-pick1"/>
				<hk:text name="ehour" maxlength="2" value="${ehour}" clazz="text2" style="width:50px"/>时
				<hk:text name="emin" maxlength="2" value="${emin}" clazz="text2" style="width:50px"/>分<br/>
				<div class="infowarn" id="_time"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				每用户周期内开箱限制：
			</td>
			<td>
				<hk:text name="precount" size="5" clazz="text2" style="width:100px" value="${box.precount}"/>个/
				<hk:select name="pretype" checkedvalue="${box.pretype}">
					<hk:option value="0" data="不限"/>
					<c:forEach var="pre" items="${prelist}">
					<hk:option value="${pre.typeId}" data="${pre.name}"/>
					</c:forEach>					
				</hk:select><br/>
				<div class="infowarn" id="_precount"></div>
			</td>
		</tr>
		<tr>
			<td width="90px" align="right">
				箱子介绍：
			</td>
			<td>
				<hk:textarea name="intro" value="${box.intro}" style="width:270px;height:100px;"/> 
				<div class="infowarn" id="_intro"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<hk:submit clazz="btn split-r" value="提交"/> 
				<a href="<%=path %>/epp/web/op/webadmin/box.do?companyId=${companyId}&navoid=${navoid }">返回</a>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
var err_code_<%=Err.BOX_NAME_ERROR %>={objid:"_name"};
var err_code_<%=Err.BOX_TOTALCOUNT_OUT_OF_LENGTH %>={objid:"_totalcount"};
var err_code_<%=Err.BOX_TIME_ERROR %>={objid:"_time"};
var err_code_<%=Err.BOX_CONTENT_LENG_TOO_LONG %>={objid:"_intro"};
var err_code_<%=Err.BOX_PRECOUNT_ERROR %>={objid:"_precount"};
function subfrm(frmid){
	setHtml('_name','');
	setHtml('_totalcount','');
	setHtml('_time','');
	setHtml('_intro','');
	setHtml('_precount','');
	showGlass(frmid);
	return true;
}
</script>