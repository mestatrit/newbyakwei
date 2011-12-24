<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">
	<hk:data key="view.user.msg.title" arg0="${loginUser.nickName}" />
</c:set>
<c:set var="body_hk_content" scope="request">
	<table class="frame-3" cellpadding="0" cellspacing="0">
		<tr>
			<td class="l">
				<jsp:include page="../inc/userleftnav_inc.jsp"></jsp:include>
			</td>
			<td class="mid">
				<div class="mid_con">
					<div class="mod"><jsp:include page="../inc/nav-2-short-msg.jsp"></jsp:include></div>
					<div class="mod">
						<div class="msgform" onkeydown="keydown(event)">
							<hk:form oid="msgfrm" action="/msg/send_sendweb.do" onsubmit="return submsgfrm(this.id)" target="hideframe">
								<hk:hide name="sendtype" value="3"/>
								<table class="infotable" cellpadding="0" cellspacing="0">
									<tr>
										<td width="80px">收信人</td>
										<td>
											<div class="f_l">
												<select id="receiverId" name="receiverId">
													<option value="0">选择收信人</option>
													<c:forEach var="f" items="${list}">
													<option value="${f.friendId }">${f.followUser.nickName }</option>
													</c:forEach>
												</select><br/>
												<div id="receiverId_error" class="error"></div>
											</div>
											<div id="receiverId_flag" class="flag"></div><div class="clr"></div>
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<div class="f_l" style="width:430px;">
												<textarea name="msg"></textarea><br/>
												<div id="msg_error" class="error"></div>
											</div>
											<div id="msg_flag" class="flag"></div><div class="clr"></div>
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<div class="form_btn">
												<hk:submit value="发送私信" clazz="btn"/>
												<hk:submit name="msgandsms_submit" value="发送短信" clazz="btn"/>
											</div>
										</td>
									</tr>
								</table>
							</hk:form>
						</div>
					</div>
				</div>
			</td>
			<td class="r">
				<div class="f_r"></div>
			</td>
		</tr>
	</table>
<script type="text/javascript">
function keydown(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(submsgfrm("msgfrm")){
			getObj("msgfrm").submit();
		}
	}
}
function submsgfrm(frmid){
	validateClear("msg");
	validateClear("receiverId");
	if(getObj("receiverId").value=="0"){
		validateErr("receiverId","请选择收信人");
		return false;
	}
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error!="0"){
		validateErr("msg",error_msg);
		hideSubmitDiv();
	}
}
function afterSuccess(value,op_func){
	tourl('<%=path %>/msg/chat_web.do?mainId='+value);
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>