<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.mgr.membergradeinfo"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
<div class="line">
<hk:button value="添加级别" clazz="btn" onclick="tocreate()"/>
</div>
<table class="infotable" cellpadding="0" cellspacing="0">
<tr align="left">
<th width="100px">名称</th>
<th width="150px">折扣</th>
</tr>
<c:forEach var="g" items="${gradelist}">
<tr>
<td>${g.name }</td>
<td>${g.rebate }</td>
<td>
<a id="edit${g.gradeId }" href="javascript:toedit(${g.gradeId })">修改</a> / 
<a href="javascript:todel(${g.gradeId })">删除</a>
</td>
</tr>
</c:forEach>
</table>
<div>
<hk:page midcount="10" url="/e/op/auth/member_gradelist.do?companyId=${companyId}"/>
</div>
</div>
<hk:form oid="del_frm" clazz="hide" action="/e/op/auth/member_deletegrade.do" target="hideframe">
<hk:hide name="companyId" value="${companyId}"/><input id="id_gradeId" name="gradeId"/>
</hk:form>
<script type="text/javascript">
function tocreate(){
	var html='<hk:form target="hideframe" oid="editgradefrm" onsubmit="return subeditgradefrm(this.id)" action="/e/op/auth/member_creategrade.do"> <hk:hide name="companyId" value="${companyId}"/> <hk:hide name="gradeId" value="${gradeId}"/> <table cellpadding="0" cellspacing="0" class="infotable"> <tr> <td width="80px">名称</td> <td><hk:text name="name" clazz="text" value="${g.name}"/></td> </tr> <tr> <td width="80px">折扣</td> <td><hk:text name="rebate" clazz="text_short_4" value="${g.rebate}"/>折</td> </tr> <tr> <td></td> <td align="center"> <hk:submit value="提交" clazz="btn"/> </td> </tr> </table> </hk:form>';
	createBg();
	createCenterWindow("member_win",500,210,'添加级别',html,"hideWindow('member_win');clearBg();");
}
function subaddgradefrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function subeditgradefrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	clearBg();
	hideSubmitDiv();
	if(error=="0"){
		refreshurl();
	}
	else{
		alert(error_msg);
	}
}
function todel(id){
	if(window.confirm("确实要删除？")){
		showSubmitDivForObj("del"+id);
		getObj("id_gradeId").value=id;
		getObj("del_frm").submit();
	}
}
function toedit(id){
	showSubmitDivForObj("edit"+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/member_loadgrade.do?gradeId='+id+"&companyId=${companyId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			createBg();
			createCenterWindow("member_win",500,210,'修改级别',data,"hideWindow('member_win');clearBg();");
			hideSubmitDiv();
		}
	});
}
</script>
</c:set>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>