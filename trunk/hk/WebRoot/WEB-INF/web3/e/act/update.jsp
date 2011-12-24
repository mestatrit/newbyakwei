<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.ZoneService"%><%@page import="com.hk.frame.util.HkUtil"%><%@page import="java.util.List"%><%@page import="com.hk.bean.Pcity"%><%@page import="com.hk.bean.Province"%><%@page import="com.hk.svr.CmpActService"%><%@page import="com.hk.bean.CmpActKind"%>
<%@page import="com.hk.bean.CmpAct"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();
CmpActService cmpActService = (CmpActService) HkUtil.getBean("cmpActService");
List<CmpActKind> kindlist = cmpActService.getCmpActKindList();
request.setAttribute("kindlist", kindlist);%>
<c:set var="html_title" scope="request">修改活动 - ${act.name}</c:set>
<c:set var="mgr_content" scope="request">
<script type="text/javascript" src="<%=path %>/webst3/js/date/date.js"></script>
<script type="text/javascript" src="<%=path %>/webst3/js/date/jquery.datePicker.min-2.1.2.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/webst3/js/date/datePicker.css">
<div>
<hk:form oid="actfrm" onsubmit="return subactfrm(this.id)" action="/e/op/auth/act_update.do" target="hideframe">
	<hk:hide name="actId" value="${actId}"/>
	<hk:hide name="companyId" value="${companyId}"/>
	<table class="infotable" cellpadding="0" cellspacing="0">
		<tr>
			<td width="90px"></td>
			<td>
				<div class="text_14 yzm">*为必填项</div>
			</td>
		</tr>
		<tr>
			<td>名称<span class="needipt">*</span></td>
			<td>
				<hk:text name="name" clazz="text" value="${act.name}"/>
				<div class="error" id="name_error"></div>
			</td>
		</tr>
		<tr>
			<td>类型<span class="needipt">*</span></td>
			<td>
				<hk:select name="kindId" checkedvalue="${act.kindId}">
					<hk:option value="0" data=""/>
					<c:forEach var="k" items="${kindlist}">
					<hk:option value="${k.kindId}" data="${k.name}"/>
					</c:forEach>
				</hk:select>
				<div class="error" id="name_error"></div>
 			</td>
		</tr>
		<tr>
			<td>地区<span class="needipt">*</span></td>
			<td>
				<jsp:include page="../../../web4/inc/zonesel.jsp"></jsp:include>
				<script type="text/javascript">
				initselected(${pcityId});
				</script>
				<br/>
				<div id="zone_error" class="error"></div>
			</td>
		</tr>
		<tr>
			<td>人数</td>
			<td>
				<hk:text name="userLimitCount" clazz="text" value="${act.userLimitCount}"/>
				<div class="error" id="userLimitCount_error"></div>
			</td>
		</tr>
		<tr>
			<td>地址</td>
			<td>
				<hk:textarea name="addr" clazz="text_area" value="${act.addr}"/>
				<div class="error" id="addr_error"></div>
			</td>
		</tr>
		<tr>
			<td>短信关键字</td>
			<td>
				<span class="ruo"><hk:data key="view.cmddata.name.tip"/></span><br/>
				<hk:text name="actKey" clazz="text" value="${act.actKey}"/>
				<div class="error" id="actKey_error"></div>
			</td>
		</tr>
		<tr>
			<td>费用</td>
			<td>
				<hk:text name="actCost" clazz="text_short_1" value="${act.actCost}"/>元/每人
				<div class="error" id="actCost_error"></div>
			</td>
		</tr>
		<tr>
			<td>审核方式<span class="needipt">*</span></td>
			<td>
				<hk:radioarea name="userNeedCheckflg" checkedvalue="${act.userNeedCheckflg}">
					<span class="split-r"><hk:radio value="<%=CmpAct.USERNEEDCHECKFLG_N %>" data="view.cmpact.userneedcheckflg0" res="true"/></span>
					<span class="split-r"><hk:radio value="<%=CmpAct.USERNEEDCHECKFLG_Y %>" data="view.cmpact.userneedcheckflg1" res="true"/></span>
				</hk:radioarea>
			</td>
		</tr>
		<tr>
			<td>时间<span class="needipt">*</span></td>
			<td>
				<div>
					开始时间：<br/>
					<fmt:formatDate var="bd" value="${act.beginTime}" pattern="yyyy-MM-dd"/>
					<fmt:formatDate var="bt" value="${act.beginTime}" pattern="HH:mm"/>
					<hk:text name="bd" clazz="text_short_1 date-pick1" value="${bd}"/>日 
					<hk:text name="bt" clazz="text_short_1" value="${bt}"/> <span class="ruo">格式为HH:mm</span>
				</div>
				<div>
					结束时间：<br/>
					<fmt:formatDate var="ed" value="${act.endTime}" pattern="yyyy-MM-dd"/>
					<fmt:formatDate var="et" value="${act.endTime}" pattern="HH:mm"/>
					<hk:text name="ed" clazz="text_short_1 date-pick2" value="${ed}"/>日 
					<hk:text name="et" clazz="text_short_1" value="${et}"/> <span class="ruo">格式为HH:mm</span>
				</div>
				<div class="error" id="time_error"></div>
			</td>
		</tr>
		<tr>
			<td>活动内容<span class="needipt">*</span></td>
			<td>
				<span class="ruo">最多3000字</span><br/>
				<hk:textarea name="intro" clazz="text_area" value="${act.intro}" style="width:500px;height:300px"/>
				<div class="error" id="intro_error"></div>
			</td>
		</tr>
		<tr>
			<td>注意事项</td>
			<td>
				<span class="ruo">最多100字</span><br/>
				<hk:textarea name="spintro" clazz="text_area" value="${act.spintro}"/>
				<div class="error" id="spintro_error"></div>
			</td>
		</tr>
		<tr>
			<td></td>
			<td align="center"><hk:submit value="提交" clazz="btn split-r"/><a href="<%=path %>/e/op/auth/act.do?companyId=${companyId}&actId=${actId}">返回</a> </td>
		</tr>
	</table>
</hk:form>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPACT_NAME_ERROR %>={objid:"name"};
var err_code_<%=Err.CMPACT_KINDID_ERROR %>={objid:"kindId"};
var err_code_<%=Err.CMPACT_INTRO_ERROR %>={objid:"intro"};
var err_code_<%=Err.CMPACT_SPINTRO_ERROR %>={objid:"spintro"};
var err_code_<%=Err.CMPACT_BEGINTIME_ERROR %>={objid:"time"};
var err_code_<%=Err.CMPACT_ENDTIME_ERROR %>={objid:"time"};
var err_code_<%=Err.CMPACT_TIME_ERROR %>={objid:"time"};
var err_code_<%=Err.CMPACT_ADDR_ERROR %>={objid:"addr"};
var err_code_<%=Err.CMPACT_PCITYID_ERROR %>={objid:"pcityId"};
var err_code_<%=Err.CMDDATA_NAME_DUPLICATE %>={objid:"actKey"};
$('.date-pick1').datePicker({clickInput:true,createButton:false});
$('.date-pick2').datePicker({clickInput:true,createButton:false});
function subactfrm(frmid){
	showSubmitDiv(frmid);
	validateClear('name');
	validateClear('addr');
	validateClear('zone');
	validateClear('userLimitCount');
	validateClear('actKey');
	validateClear('actCost');
	validateClear('time');
	validateClear('intro');
	validateClear('spintro');
	return true;
}
function acterror(error,error_msg,respValue){
	validateErr(getoidparam(error),error_msg);
	hideSubmitDiv();
}
function actok(error,error_msg,respValue){
	tourl('<%=path %>/e/op/auth/act.do?companyId=${companyId}&actId=${actId}');
}
</script>
</c:set>
<jsp:include page="../inc/mgr_inc.jsp"></jsp:include>