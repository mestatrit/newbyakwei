<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path=request.getContextPath();%>
<c:set var="html_title" scope="request">首页模块显示顺序</c:set>
<c:set var="mgr_content" scope="request">
<script type="text/javascript">
var mod=new Array();
<c:forEach var="order" items="${cmpUnionSite.cmpUnionSiteOrderList}" varStatus="idx">
mod[${idx.index }]=new Array(${order.module},'<hk:data key="view.sitemod_${order.module}"/>');
</c:forEach>
function showMod(){
	var s="";
	for(var i=0;i<mod.length;i++){
		s+='<li onmouseover="this.className=\'bg1\';" onmouseout="this.className=\'\';"> <hk:hide name="mod" value="'+mod[i][0]+'"/> <table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td width="200px">'+mod[i][1]+'</td> <td> <a href="javascript:up('+i+')">上移</a> </td> </tr> </table> </li>';
	}
	setHtml('modlist',s);
}
function up(idx){
	if(idx==0){
		return;
	}
	var t=new Array();
	t[0]=new Array(mod[idx-1][0],mod[idx-1][1]);
	mod[idx-1]=new Array(mod[idx][0],mod[idx][1]);
	mod[idx]=t[0];
	showMod();
}
function submodfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function updatemodok(error,error_msg,respValue){
	refreshurl();
}
</script>
<div>
<hk:form oid="modfrm" onsubmit="return submodfrm(this.id)" action="/cmpunion/op/union_updatemod.do" target="hideframe">
	<hk:hide name="uid" value="${uid}"/>
	<ul class="orderlist">
		<li id="datamod" class="bg1">
			<table class="infotable" cellpadding="0" cellspacing="0">
			<tr>
				<td width="200px">模块名称</td>
				<td></td>
			</tr>
			</table>
		</li>
	</ul>
	<ul id="modlist" class="orderlist">
	</ul>
	<ul class="orderlist">
		<li class="noline">
			<div align="center">
			<hk:submit value="保存显示顺序" clazz="btn2"/>
			</div>
		</li>
	</ul>
</hk:form>
</div>
<script type="text/javascript">
showMod();
</script>
</c:set>
<jsp:include page="mgr_inc.jsp"></jsp:include>