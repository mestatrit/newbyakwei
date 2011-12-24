<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath(); %>
<c:set var="html_title" scope="request"><hk:data key="view.user.mgr.setmsn" /></c:set>
<c:set var="mgr_content" scope="request">
	<div>
		<hk:form oid="msnfrm" method="post" onsubmit="return subfrm(this.id)" action="/user/set/set_setmsnweb.do" target="hideframe">
		<table cellpadding="0" cellspacing="0" class="infotable">
			<tr>
				<td width="90px">输入MSN地址</td>
				<td>
					<hk:text name="msn" value="${info.msn}" maxlength="50"/><br/>
					<div class="error" id="msg_error"></div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td><div class="form_btn"><hk:submit value="保存" clazz="btn"/></div></td>
			</tr>
		</table>
		</hk:form>
	</div>
	<div class="text_14"><br/>
	1、将火酷msn机器人<span class="yzm">huoku.com@hotmail.com</span>添加成为自己的msn好友 <input type="button" onclick="cplink()" value="复制火酷机器人地址"/><br/>
	2、添加后,通过msn向火酷msn机器人发布的信息就变成火酷小喇叭了<br/>
	</div>
<script type="text/javascript">
var browser="";
if(navigator.userAgent.toLowerCase().indexOf('msie') != -1){browser="ie";}else{browser="n";}
function cplink(){
	if(browser!="ie"){
		alert("您的浏览器不支持复制，请手动选中文字，进行复制");
		return;
	}
  	window.clipboardData.setData("Text","huoku.com@hotmail.com");
  	window.alert("地址已复制到剪贴板，您可以通过MSN QQ发送给好友");
}
function subfrm(frmid){
	validateClear('msg');
	showSubmitDiv(frmid);
	return true;
}
function onmsnerror(error,error_msg,op_func,obj_id_param){
	validateErr('msg',error_msg);
	hideSubmitDiv();
}
function onmsnsuccess(error,error_msg,op_func,obj_id_param,respValue){
	tourl("<%=path %>/user/set/set_tosetmsnweb.do");
}
</script>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>