<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%>
<%@page import="com.hk.bean.CmpTip"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${company.name}</c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 700px;">
<form id="tipfrm" method="post" onsubmit="return subtipfrm(this.id)" action="<%=path %>/h4/op/user/venue_edittip.do" target="hideframe">
<hk:hide name="ch" value="1"/>
<hk:hide name="tipId" value="${tipId}"/>
<hk:hide name="companyId" value="${cmpTip.companyId}"/>
<table class="nt reg" cellpadding="0" cellspacing="0">
<tr>
	<td><h1 style="display:inline"><hk:data key="view2.add_tip"/> / <hk:data key="view2.add_todo"/></h1>  ... <hk:data key="view2.or"/>  <a href="/venue/search"><hk:data key="view2.create_new_venue"/></a></td>
</tr>
<tr>
	<td style="font-size: 14px;">
	<br/>
		<hk:radioarea name="doneflg" checkedvalue="${cmpTip.doneflg}">
			<span class="split-r">
				<hk:radio value="<%=CmpTip.DONEFLG_DONE %>" data="view2.i_did_this_and_it_was_awesome" res="true"/>
			</span>
			<span class="split-r">
				<hk:radio value="<%=CmpTip.DONEFLG_TODO %>" data="view2.add_this_to_my_todo_list" res="true"/>
			</span>
		</hk:radioarea>
	</td>
</tr>
</table>
<div class="rounded" style="background-color: #DDDDDD;padding: 10px;overflow: hidden;">
<table class="nt reg" cellpadding="0" cellspacing="0">
<tr>
	<td width="90px" class="b" align="right"><hk:data key="view2.where"/></td>
	<td>${company.name }</td>
</tr>
<tr>
	<td class="b" align="right"><hk:data key="view2.tip"/></td>
	<td>
	<div  style="margin-bottom:10px;">
	<textarea id="content" name="content" onkeydown="subtipfrm2(event)" class="text_area" style="height: 200px"/>${text_content }</textarea>&nbsp;&nbsp;&nbsp;<span id="numcount" class="numcount">500</span>
	</div>
	<div id="contentwarn" class="infowarn"></div>
	</td>
</tr>
<tr>
	<td></td>
	<td align="center"><hk:submit value="view2.submit" res="true" clazz="btn split-r"/>
	<a href="/venue/${cmpTip.companyId }/"><hk:data key="view2.return"/></a>
	</td>
</tr>
</table>
</div>
</form>
</div>
<script type="text/javascript">
var err_code_<%=Err.CMPTIP_CONTENT_ERROR %>={objid:"contentwarn"};
function subtipfrm(frmid){
	showGlass(frmid);
	return true;
}
function subtipfrm2(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		subtipfrm('tipfrm');
		getObj('tipfrm').submit();
	}
}
function editok(error,error_msg,v){
	tourl('/item/${tipId}');
}
function editerror(error,error_msg,v){
	setHtml(getoidparam(error),error_msg);
	hideGlass();
}
function updateNumCount() {
	setHtml('numcount',(500 - getObj('content').value.length));
	setTimeout(updateNumCount, 500);
}
updateNumCount();
</script>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>