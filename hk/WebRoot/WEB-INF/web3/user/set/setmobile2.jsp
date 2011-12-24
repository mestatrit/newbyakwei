<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="html_title" scope="request">
	<hk:data key="view.user.mgr.bindmobile" />
</c:set>
<c:set var="mgr_content" scope="request">
	<div class="text_14">
		<c:if test="${userOtherInfo.mobileAlreadyBind}">
			<div class="hang">
				<h3 class="title3">您已经成功绑定了手机号${userOtherInfo.mobile}<br/></h3>
			</div>
		</c:if>
		<c:if test="${!userOtherInfo.mobileAlreadyBind}">
			<div class="hang">
				<h3 class="title3">绑定手机号</h3>
				您的手机验证码是<span id="code1" class="yzm">yz${ran.randvalue}</span>(本验证码10分钟内有效).<br/>
				请通过手机发送<span id="code2" class="yzm">yz${ran.randvalue}</span>到1066916025完成本帐号的手机号绑定,10分钟之内有效.发送完毕后请耐心等待1分钟,1分钟后再次点击下面发送完毕链接.<br/>
				不操作不会对已经绑定的手机号有任何影像<br/>
				<hk:a href="/user/set/set_tosetmobile2web.do?refresh=1">发送完毕</hk:a>
				<h3 class="title3">重新获取验证码</h3>
				<hk:a href="javascript:getnewcode()">重新获取验证码</hk:a><span class="ruo text_12" id="loading"></span>
			</div>
		</c:if>
	</div>
<script type="text/javascript">
function getnewcode(){
	setHtml("loading",'正在获取验证码 ... ...');
	$.ajax({
		type:"POST",
		url:'<%=request.getContextPath() %>/user/set/set_rechgmobileweb.do?ajax=1',
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml("code1","yz"+data);
			setHtml("code2","yz"+data);
			setHtml("loading","");
		},
		error:function(data){
			alert("系统出现错误，请稍后再操作");
			setHtml("loading","");
		}
	});
}
</script>
</c:set>
<jsp:include page="../../inc/usermgr_inc.jsp"></jsp:include>