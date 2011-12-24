<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.CmpTable"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.mgr.table"/></c:set>
<c:set var="mgr_content" scope="request">
<script type="text/javascript">
var err_code_<%=Err.CMPTABLE_TABLENUM_ERROR %>={objid:"tableNum"};
var err_code_<%=Err.CMPTABLE_INTRO_ERROR %>={objid:"intro"};
var err_code_<%=Err.CMPTABLE_BESTPERSONNUM_ERROR %>={objid:"bestPersonNum"};
var err_code_<%=Err.CMPTABLE_MOSTPERSONNUM_ERROR %>={objid:"mostPersonNum"};
var err_code_<%=Err.CMPTABLE_OPNAME_ERROR %>={objid:"opname"};
var err_code_<%=Err.CMPTABLE_NETORDERFLG_ERROR %>={objid:"netOrderflg"};
var err_code_<%=Err.CMPTABLE_SORTID_ERROR %>={objid:"sort"};
function tocreate(){
	var html='<form id="frm" onsubmit="return subaddfrm(this.id)" action="<%=path %>/e/op/auth/table_createtable.do" target="hideframe"> <input type="hidden" name="tableId" value="${tableId }" /> <input type="hidden" name="companyId" value="${companyId }" /> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="100px">台号</td> <td> <div class="f_l"> <input name="tableNum" type="text" class="text" value="${o.tableNum }"/><br/> <div id="tableNum_error" class="error"></div> </div> <div id="tableNum_flag" class="flag"></div><div class="clr"></div> </td> </tr><tr> <td>显示顺序号</td> <td> <div class="f_l"> <input name="orderflg" type="text" class="text" value="${o.orderflg }"/><br/> <div id="orderflg_error" class="error"></div> </div> <div id="orderflg_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px">分类</td> <td> <div class="f_l"> <hk:select oid="id_sortid" name="sortId" checkedvalue="${o.sortId}"><hk:option value="0" data="请选择"/> <c:forEach var="s" items="${sortlist}"> <hk:option value="${s.sortId}" data="${s.name}"/> </c:forEach> </hk:select><br/> <div id="sort_error" class="error"></div> </div> <div id="sort_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px">理想就餐人数</td> <td> <div class="f_l"> <input name="bestPersonNum" type="text" class="text" value="${o.bestPersonNum }"/><br/> <div id="bestPersonNum_error" class="error"></div> </div> <div id="bestPersonNum_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px">最多就餐人数</td> <td> <div class="f_l"> <input name="mostPersonNum" type="text" class="text" value="${o.mostPersonNum }"/><br/> <div id="mostPersonNum_error" class="error"></div> </div> <div id="mostPersonNum_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px">责任人</td> <td> <div class="f_l"> <input name="opname" type="text" class="text" value="${o.opname }"/><br/> <div id="opname_error" class="error"></div> </div> <div id="opname_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px">客户网络预订标志</td> <td> <div class="f_l"> <c:set var="netorderflg0"><%=CmpTable.NETORDERFLG_N %></c:set> <c:set var="netorderflg1"><%=CmpTable.NETORDERFLG_Y %></c:set> <hk:radioarea name="netOrderflg" checkedvalue="${netorderflg1}"> <hk:radio value="${netorderflg1}" data="view.company.table.netorderflg1" res="true"/><br/> <hk:radio value="${netorderflg0}" data="view.company.table.netorderflg0" res="true"/><br/> </hk:radioarea> <div id="netOrderflg_error" class="error"></div> </div> <div id="netOrderflg_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td width="80px">描述</td> <td> <div class="f_l"> <textarea class="text_area" name="intro"></textarea><br/> <div id="intro_error" class="error"></div> </div> <div id="intro_flag" class="flag"></div><div class="clr"></div> </td> </tr> <tr> <td></td> <td> <div align="center"><input type="submit" value="提交" class="btn"></div> </td> </tr> </table> </form>';
	createBg();
	createCenterWindow("add_win",530,680,'创建台面',html,"hideWindow('add_win');clearBg();");
}
function subaddfrm(frmid){
	validateClear("tableNum");
	validateClear("intro");
	validateClear("bestPersonNum");
	validateClear("mostPersonNum");
	validateClear("opname");
	validateClear("netOrderflg");
	validateClear("sort");
	showSubmitDiv(frmid);
	return true;
}
function ontableerror(error,error_msg,respValue){
	validateErr(getoidparam(error),error_msg);
	hideSubmitDiv();
}
function ontablesuccess(error,error_msg,respValue){
	refreshurl();
}
function toedit(id){
	showSubmitDivForObj('update'+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/table_loadtable.do?companyId=${companyId}&tableId='+id,
		cache:false,
    	dataType:"html",
		success:function(data){
			createBg();
			createCenterWindow("table_update_win",530,680,'修改台面',data,"hideWindow('table_update_win');clearBg();");
			hideSubmitDiv();
		}
	});
}
function subupdatetablefrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function ontableupdateerror(error,error_msg,respValue){
	validateErr(getoidparam(error),error_msg);
	hideSubmitDiv();
}
function ontableupdatesuccess(error,error_msg,respValue){
	refreshurl();
}
function del(id){
	if(window.confirm("确定要删除?")){
		showSubmitDivForObj('del'+id);
		$.ajax({
			type:"POST",
			url:'<%=path %>/e/op/auth/table_deltable.do?companyId=${companyId}&tableId='+id,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
</script>
<div class="pad">
<hk:button value="创建台面" clazz="btn" onclick="tocreate()"/>
</div>
<div>
	<ul>
		<li class="pubrow3 bg1">
			<table class="infotable tdpad" cellpadding="0" cellspacing="0">
				<tr class="tr-title">
					<th width="100px">台号</th>
					<th width="100px">分类</th>
					<th width="100px">责任人</th>
					<th width="200px">描述</th>
					<th width="200px"></th>
				</tr>
			</table>
		</li>
		<c:forEach var="t" items="${list}">
			<li class="pubrow3" onmouseout="this.className='pubrow3';" onmouseover="this.className='pubrow3 bg2';">
				<table class="infotable tdpad" cellpadding="0" cellspacing="0">
					<tr>
						<td width="100px">${t.tableNum }</td>
						<td width="100px">${t.cmpTableSort.name } </td>
						<td width="100px">${t.opname }</td>
						<td width="200px">${t.simpleIntro } </td>
						<td width="200px">
						<a id="update${t.tableId }" href="javascript:toedit(${t.tableId })">修改</a> 
						/
						<a id="del${t.tableId }" href="javascript:del(${t.tableId })">删除</a> 
						/ 
						<a href="<%=path %>/e/op/auth/table/photo_photosetlist.do?companyId=${companyId}&tableId=${t.tableId}">选择图集</a>
						<c:if test="${t.setId>0}">
							<br/>图集：${t.cmpTablePhotoSet.title }
						</c:if>
						</td>
					</tr>
				</table>
			</li>
		</c:forEach>
	</ul>
</div>
<div>
	<hk:page midcount="10" url="/e/op/auth/table.do?companyId=${companyId}&sortId=${sortId }&name=${enc_name }"/>
	<div class="clr"></div>
</div>
</c:set>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>