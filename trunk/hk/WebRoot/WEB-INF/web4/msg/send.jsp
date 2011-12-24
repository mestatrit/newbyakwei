<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view2.sendmsg"/></c:set>
<c:set var="html_body_content" scope="request">
<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/msg.css" />
<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/jquery-ui-1.7.2.custom.min.js"></script>
<div class="hcenter" style="width: 700px">
	<div class="f_l" style="width: 700px">
		<div id="do" class="inactive_tips_tab"><a href="<%=path %>/h4/op/msg.do">所有对话录</a></div>
		<div id="todo" class="active_tips_tab"><a href="<%=path %>/h4/op/msg_send2.do"><hk:data key="view2.sendmsg"/></a></div>
		<div class="clr"></div>
		<div id="listbox" class="listbox"><a name="msg_top"></a>
			<br/>
			<div class="hcenter" style="width: 600px;">
				<form id="msgfrm" method="post" onsubmit="return submsgfrm(this.id)" action="<%=path %>/h4/op/msg_send2.do" target="hideframe">
					<input type="hidden" name="ch" value="1"/>
					<div class="rounded" style="background-color: #DDDDDD;padding: 10px;overflow: hidden;">
					<table class="nt reg" cellpadding="0" cellspacing="0">
						<tr>
						<td>
						<hk:data key="videw2.msg_receiver"/>：
						<select id="_receiverId" name="receiverId" style="padding: 3px;width: 150px;">
							<option value="0"><hk:data key="view2.please_select"/></option>
							<c:forEach var="f" items="${list}">
								<option value="${f.friendId }">${f.followUser.nickName }</option>
							</c:forEach>
						</select>
						</td>
						</tr>
						<tr>
							<td>
							<br/>
							<div  style="margin-bottom:10px;">
							<textarea onkeydown="submsgfrm2(event)" id="_content" name="msg" style="width: 500px;height: 100px;margin-bottom: 10px;"/></textarea>
							&nbsp;&nbsp;&nbsp;<span id="numcount" class="numcount">500</span>
							</div>
							<div id="contentwarn" class="infowarn"></div>
							</td>
						</tr>
						<tr>
							<td align="center"><hk:submit value="view2.submit" res="true" clazz="btn split-r"/>
							</td>
						</tr>
					</table>
					</div>
				</form>
			</div>
			<br/>
		</div>
	</div>
</div>
<script type="text/javascript">
function updateNumCount() {
	setHtml('numcount',(500 - getObj('_content').value.length));
	setTimeout(updateNumCount, 500);
}
function submsgfrm(frmid){
	if(getObj('_receiverId').value=="0"){
		alert('请选择收信人');
		return false;
	}
	setHtml('contentwarn','');
	showGlass(frmid);
	return true;
}
function submsgfrm2(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(submsgfrm("msgfrm")){
			getObj("msgfrm").submit();
		}
	}
}
function msgerror(error,msg,v){
	setHtml('contentwarn',error);
}
function msgok(error,msg,v){
	tourl("<%=path %>/h4/op/msg_chat.do?mainId="+v);
}
updateNumCount();
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>