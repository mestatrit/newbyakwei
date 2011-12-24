<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.company.mgr.memberinfo"/></c:set>
<c:set var="mgr_content" scope="request">
<div>
<div class="line">
<hk:button value="添加会员" clazz="btn" onclick="tocreate()"/>
</div>
<div>
<br/>
<hk:form method="get" action="/e/op/auth/member.do">
<hk:hide name="companyId" value="${companyId}"/>
姓名：<hk:text name="name" clazz="text_short_1" value="${enc_name}"/> 
手机：<hk:text name="mobile" clazz="text_short_1" value="${mobile}"/> 
E-mail：<hk:text name="email" clazz="text_short_1" value="${email}"/> 
会员级别：<hk:select name="gradeId" checkedvalue="${gradeId}">
	<hk:option value="0" data="所有"/>
	<c:forEach var="g" items="${gradelist}">
	<hk:option value="${g.gradeId}" data="${g.name}"/>
	</c:forEach>
</hk:select>
<hk:submit value="搜索" clazz="btn"/>
</hk:form>
</div>
<table class="infotable" cellpadding="0" cellspacing="0">
<tr align="left">
<th width="100px">姓名</th>
<th width="150px">手机</th>
<th width="200px">E-mail</th>
<th width="100px">余额</th>
</tr>
<c:forEach var="m" items="${memberlist}">
<tr>
<td>${m.name }</td>
<td>${m.mobile }</td>
<td>${m.email }</td>
<td>￥${m.money }</td>
<td>
<a id="edit${m.memberId }" href="javascript:toedit(${m.memberId })">修改</a> / 
<a id="del${m.memberId }" href="javascript:todel(${m.memberId })">删除</a> / 
<a id="money${m.memberId }" href="javascript:toupdatemoney(${m.memberId })">充值</a>
</td>
</tr>
</c:forEach>
</table>
<div>
<hk:page midcount="10" url="/e/op/auth/member.do?companyId=${companyId}&gradeId=${gradeId }&mobile=${mobile }&email=${email }&name=${enc_name }"/>
</div>
</div>
<hk:form oid="del_frm" clazz="hide" action="/e/op/auth/member_delete.do" target="hideframe">
<hk:hide name="companyId" value="${companyId}"/><input type="hidden" id="id_memberId" name="memberId"/>
</hk:form>
<script type="text/javascript">
function submoneyfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function toupdatemoney(id){
	showSubmitDivForObj("money"+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/member_loadmember.do?memberId='+id+"&companyId=${companyId}&t=1",
		cache:false,
    	dataType:"html",
		success:function(data){
			createBg();
			createCenterWindow("member_win",500,230,'会员充值',data,"hideWindow('member_win');clearBg();");
			hideSubmitDiv();
		}
	});
}
function tocreate(){
	var html='<hk:form target="hideframe" oid="addmemberfrm" onsubmit="return subaddmemberfrm(this.id)" action="/e/op/auth/member_create.do"> <hk:hide name="companyId" value="${companyId}"/> <table cellpadding="0" cellspacing="0" class="infotable"> <tr> <td width="80px">级别</td> <td> <hk:select name="gradeId"> <hk:option value="0" data="无"/> <c:forEach var="g" items="${gradelist}"> <hk:option value="${g.gradeId}" data="${g.name}"/> </c:forEach> </hk:select> </td> </tr> <tr> <td width="80px">姓名</td> <td><hk:text name="name" clazz="text" maxlength="10"/></td> </tr> <tr> <td width="80px">手机</td> <td><hk:text name="mobile" clazz="text" maxlength="15"/></td> </tr> <tr> <td width="80px">E-mail</td> <td><hk:text name="email" clazz="text" maxlength="50"/></td> </tr> <tr> <td></td> <td> <hk:submit value="提交" clazz="btn"/> </td> </tr> </table> </hk:form>';
	createBg();
	createCenterWindow("member_win",500,330,'添加会员',html,"hideWindow('member_win');clearBg();");
}
function subaddmemberfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function subeditmemberfrm(frmid){
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
		getObj("id_memberId").value=id;
		getObj("del_frm").submit();
	}
}
function toedit(id){
	showSubmitDivForObj("edit"+id);
	$.ajax({
		type:"POST",
		url:'<%=path %>/e/op/auth/member_loadmember.do?memberId='+id+"&companyId=${companyId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			createBg();
			createCenterWindow("member_win",500,330,'修改会员',data,"hideWindow('member_win');clearBg();");
			hideSubmitDiv();
		}
	});
}
</script>
</c:set>
<jsp:include page="../../inc/mgr_inc.jsp"></jsp:include>