<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.User"%>
<%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">忘记密码</c:set>
<c:set var="body_hk_content" scope="request">
	<div class="mod text_14">
		<hk:form oid="fgtpwdfrm" action="/fgtpwd2_checkemail.do" onsubmit="return subfgtpwdfrm(this.id)" target="hideframe">
			输入你的E-mail<br/>
			<hk:text name="email" clazz="text"/>
			<hk:submit value="提交" clazz="btn"/>
		</hk:form>
		<div class="yzm"><br/>手机号码绑定的会员请用手机发送新密码到${number }来修改账号密码</div>
		<div><a href="<%=path %>/reg_toregweb.do">回到登录页</a></div>
	</div>
<script type="text/javascript">
function subfgtpwdfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error!="0"){
		hideSubmitDiv();
		if(error=="<%=Err.FGTPWD_EMAIL_SEND_OK %>"){
			alert(error_msg);
		}
		else if(error=="<%=Err.FGTPWD_PROTECT_INPUT %>"){
			tourl('<%=path %>/fgtpwd2_toinputprotect.do');
		}
		else{
			alert(error_msg);
		}
	}
	else{
	}
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>