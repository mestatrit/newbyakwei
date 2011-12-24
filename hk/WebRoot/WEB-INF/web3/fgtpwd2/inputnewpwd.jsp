<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">忘记密码</c:set>
<c:set var="body_hk_content" scope="request">
	<div class="mod text_14">
		<hk:form oid="fgtpwdfrm" action="/fgtpwd2_savenewpwd.do" onsubmit="return subfgtpwdfrm(this.id)" target="hideframe">
			输入新的密码:<br/>
			<hk:pwd name="password" clazz="text"/> <hk:submit value="提交" clazz="btn"/>
		</hk:form>
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
		alert(error_msg);
	}
	else{
		tourl('<%=path%>/home_web.do');
	}
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>