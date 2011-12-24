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
<div class="datetable">
	<div class="date_tablelist">
		<c:if test="${fn:length(list)==0}">
			<div class="text_16 heavy" align="center"><br/>没有查询到数据</div>
		</c:if>
		<c:if test="${fn:length(list)>0}">
			<ul>
				<li>
					<table class="infotable2" cellpadding="0" cellspacing="0">
						<tr>
							<th width="250px">时间</th>
							<th width="100px">姓名</th>
							<th width="150px">联系电话</th>
							<th width="80px">人数</th>
							<th width="80px">桌号</th>
							<th width="80px">状态</th>
							<th width="130px">
							</th>
						</tr>
					</table>
				</li>
				<c:forEach var="cot" items="${list}">
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
									${cot.cmpTable.tableNum }
								</td>
								<td width="80px">
									<hk:data key="view.company.cmpordertable.bookflg${cot.objstatus}"/>
								</td>
								<td width="130px">
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
			<hk:page midcount="10" url="/e/op/auth/table_datetable.do?companyId=${companyId }&tableId=${tableId}&name=${enc_name }&tel=${tel }&dateflg=${dateflg }"/>
			<div class="clr"></div>
		</div>
		<div>
		<hk:button clazz="btn split-r" value="返回" onclick="tolist2()"/>
		</div>
	</div>
</div>
</div>
<div class="clr"></div>
<script type="text/javascript">
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