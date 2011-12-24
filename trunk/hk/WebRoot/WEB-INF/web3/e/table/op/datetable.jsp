<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.mgr.optable"/></c:set>
<c:set var="css_value" scope="request"><link rel="stylesheet" type="text/css" href="<%=path%>/webst3/css/table.css" />
<link rel="stylesheet" type="text/css" media="screen" href="<%=path %>/webst3/js/date/datePicker.css"></c:set>
<c:set var="body_hk_content" scope="request">
<script type="text/javascript" src="<%=path %>/webst3/js/date/date.js"></script>
<script type="text/javascript" src="<%=path %>/webst3/js/date/jquery.datePicker.min-2.1.2.js"></script>
<jsp:include page="datetable_inc.jsp"></jsp:include>
<div class="mod_primary">
<c:if test="${booked_not_meal_count>0}">
<div class="warn2">
<a href="<%=path %>/e/op/auth/table_bookednotmeal.do?companyId=${companyId}"><hk:data key="view.company.cmpordertable.bookednotmealcount" arg0="${booked_not_meal_count}"/></a>
</div>
</c:if>
<div class="datetable">
	<div class="text_14">
		<hk:form method="get" action="/e/op/auth/table_datetable.do">
			<hk:hide name="companyId" value="${companyId}"/>
			<hk:hide name="tableId" value="${tableId}"/>
			<hk:hide name="dateflg" value="sel"/>
			查询时间：从<hk:text name="begin_date" clazz="text_short_1 date-pick4 split-r" value="${begin_date}"/> 
			到<hk:text name="end_date" clazz="text_short_1 date-pick5" value="${end_date}"/>
			<hk:submit value="查询" clazz="btn"/>
		</hk:form>
	</div>
	<div class="date_bar">
		<div class="date_title">桌号：${cmpTable.tableNum }</div>
		<div class="date_sel">
			<c:if test="${dateflg=='today'}"><c:set var="today_active" >active</c:set></c:if>
			<c:if test="${dateflg=='week'}"><c:set var="week_active" >active</c:set></c:if>
			<c:if test="${dateflg=='days7'}"><c:set var="days7_active" >active</c:set></c:if>
			<c:if test="${dateflg=='month'}"><c:set var="month_active" >active</c:set></c:if>
			<c:set var="baseurl"><%=path %>/e/op/auth/table_datetable.do?companyId=${companyId}&tableId=${tableId}</c:set>
			<a class="${today_active }" href="${baseurl }&dateflg=today">当天</a>
			<a class="${week_active }" href="${baseurl }&dateflg=week">本周</a>
			<a class="${days7_active }" href="${baseurl }&dateflg=days7">7天内</a>
			<a class="${month_active }" href="${baseurl }&dateflg=month">月</a>
		</div>
		<div class="clr"></div>
	</div>
	<div class="date_tablelist text_16">
		<div class="text_14 pad">
			<hk:form method="get" action="/e/op/auth/table_datetable.do">
				<hk:hide name="companyId" value="${companyId}"/>
				<hk:hide name="tableId" value="${tableId}"/>
				<hk:hide name="dateflg" value="${dateflg}"/>
				姓名：<hk:text name="name" clazz="text_short_1 split-r" value="${name}"/>
				联系电话：<hk:text name="tel" clazz="text_short_1" value="${tel}"/>
				<hk:submit value="搜索" clazz="btn"/>
			</hk:form>
		</div>
		<c:if test="${fn:length(cmpOrderTableList)==0}">
			<div class="text_16 heavy" align="center"><br/>没有查询到数据</div>
		</c:if>
		<c:if test="${fn:length(cmpOrderTableList)>0}">
			<ul>
				<li class="noline bg1">
					<table class="infotable2" cellpadding="0" cellspacing="0">
						<tr>
							<th width="250px">时间</th>
							<th width="100px">姓名</th>
							<th width="150px">联系电话</th>
							<th width="80px">人数</th>
							<th width="80px">状态</th>
							<th width="150px">
							</th>
						</tr>
					</table>
				</li>
				<c:forEach var="cot" items="${cmpOrderTableList}">
					<li onmouseout="this.className='';" onmouseover="this.className='bg2';">
						<table class="infotable" cellpadding="0" cellspacing="0">
							<tr>
								<td width="250px">
								<strong>
									<fmt:formatDate value="${cot.beginTime}" pattern="yy-MM-dd HH:mm"/> 
									- 
									<fmt:formatDate value="${cot.endTime}" pattern="yy-MM-dd HH:mm"/>
								</strong>
								</td>
								<td width="100px">
									${cot.name }
								</td>
								<td width="150px">
									${cot.tel }
								</td>
								<td width="80px">
									${cot.personNum }人
								</td>
								<td width="80px">
									<hk:data key="view.company.cmpordertable.bookflg${cot.objstatus}"/>
								</td>
								<td width="150px">
									<a id="del${cot.oid }" href="javascript:todel(${cot.oid })">取消</a> /
									<a id="edit${cot.oid }" href="javascript:toedit(${cot.oid })">修改</a>
									<c:if test="${!cot.havingMeals}">
										/ <a id="inuse${cot.oid }" href="javascript:setinuse(${cot.oid })">落座</a>
									</c:if>
								</td>
							</tr>
						</table>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<div>
			<hk:page midcount="10" url="/e/op/auth/table_datetable.do?companyId=${companyId }&tableId=${tableId}&name=${enc_name }&tel=${tel }&dateflg=${dateflg }&begin_date=${begin_date }&end_date=${end_date }"/>
			<div class="clr"></div>
		</div>
		<div align="center">
		<hk:button clazz="btn split-r" value="预约" onclick="tocreate()"/>
		<hk:button clazz="btn split-r" value="返回" onclick="tolist2()"/>
		</div>
	</div>
</div>
</div>
<div class="clr"></div>
<script type="text/javascript">
$('.date-pick4').datePicker({clickInput:true,createButton:false,startDate:'2010-01-01'});
$('.date-pick5').datePicker({clickInput:true,createButton:false,startDate:'2010-01-01'});
var err_code_<%=Err.CMPORDERTABLE_NAME_RROR %>={objid:"name"};
var err_code_<%=Err.CMPORDERTABLE_PERSONNUM_ERROR %>={objid:"personNum"};
var err_code_<%=Err.CMPORDERTABLE_REMARK_ERROR %>={objid:"remark"};
var err_code_<%=Err.TIME_ERROR %>={objid:"time"};
var err_code_<%=Err.CMPTABLE_NOTEXIST %>={objid:"tableId"};
function todel(id){
	if(window.confirm("确实要删除？")){
		showSubmitDivForObj('del'+id);
		$.ajax({
			type:"POST",
			url:'<%=path %>/e/op/auth/table_delordertable.do?companyId=${companyId}&oid='+id,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
function setinuse(id){
	showSubmitDivForObj('inuse'+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/table_setinuse.do?companyId=${companyId}&oid='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			refreshurl();
		}
	});
}
function tolist2(){
	tourl('<%=path %>/e/op/auth/table_list2.do?companyId=${companyId}');
}
function tocreate(){
	createBg();
	var html='<form id="frm" onsubmit="return subcmpordertablefrm(this.id)" action="<%=path %>/e/op/auth/table_createcmpordertable.do" target="hideframe"> <input type="hidden" name="tableId" value="${tableId }" /> <input type="hidden" name="companyId" value="${companyId }" /> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="80px">姓名</td> <td> <div class="f_l"> <input name="name" type="text" class="text" value=""/><br/> <div id="name_error" class="error"></div> </div> <div id="name_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px">联系电话</td> <td> <div class="f_l"> <input name="tel" type="text" class="text" value=""/><br/> <div id="tel_error" class="error"></div> </div> <div id="tel_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px">人数</td> <td> <div class="f_l"> <input name="personNum" type="text" class="text" value=""/><br/> <div id="personNum_error" class="error"></div> </div> <div id="personNum_flag" class="flag"></div><div class="clr"></div> </td> </tr><tr> <td width="80px">餐桌</td> <td> <div class="f_l"> <hk:select name="sortId" onchange="loadtablelistbysortid(this.value)"> <hk:option value="0" data="选择分类"/> <c:forEach var="sort" items="${sortlist}"> <hk:option value="${sort.sortId}" data="${sort.name}"/> </c:forEach> </hk:select> <span id="edit_tablelist"> </span> <br/> <div id="tableId_error" class="error"></div> </div> <div id="tableId_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px">预约时间</td> <td> <div class="f_l"> 从<br/> 日期：<input name="bd" type="text" class="text_short_1 date-pick1" value=""/> 时间：<input name="bt" type="text" class="text_short_1" value=""/><br/> 到<br/> 日期：<input name="ed" type="text" class="text_short_1 date-pick2" value=""/> 时间：<input name="et" type="text" class="text_short_1" value=""/><br/> <div id="time_error" class="error"></div> </div> <div id="time_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px">备注</td> <td> <div class="f_l"> <textarea class="text_area" name="remark"></textarea><br/> <div id="remark_error" class="error"></div> </div> <div id="remark_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td></td> <td> <div align="center"><input type="submit" value="提交" class="btn split-r"><input type="submit" name="meal" value="提交并落座" class="btn split-r"></div> </td> </tr> </table> </form>';
	createCenterWindow("table_update_win",530,620,'创建预约',html,"hideWindow('table_update_win');clearBg();");
	$('.date-pick1').datePicker({clickInput:true,createButton:false,startDate:new Date().asString()}).val(new Date().asString());
	$('.date-pick2').datePicker({clickInput:true,createButton:false,startDate:new Date().asString()}).val(new Date().asString());
}
function toedit(id){
	showSubmitDivForObj('edit'+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/table_loadcmpordertable.do?companyId=${companyId}&oid='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			createBg();
			createCenterWindow("table_update_win",530,620,'修改信息',data,"hideWindow('table_update_win');clearBg();");
			hideSubmitDiv();
		}
	});
}
function subcmpordertablefrm(frmid){
	validateClear("name");
	validateClear("personNum");
	validateClear("tel");
	validateClear("remark");
	showSubmitDiv(frmid);
}
function oncmpordertablesuccess(error,error_msg,respValue){
	refreshurl();
}
function oncmpordertableerror(error,error_msg,respValue){
	validateErr(getoidparam(error),error_msg);
	hideSubmitDiv();
}
function loadtablelistbysortid(sortId){
	setHtml('edit_tablelist','加载数据中 ...');
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/table_loadcmptablelistbysortid.do?companyId=${companyId}&sortId='+sortId,
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('edit_tablelist',data);
		}
	});
}
</script>
</c:set>
<jsp:include page="../../../inc/cmpmgrframe.jsp"></jsp:include>