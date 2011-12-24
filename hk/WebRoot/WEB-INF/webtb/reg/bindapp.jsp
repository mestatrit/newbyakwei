<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"
%><c:set var="html_head_title" scope="request">绑定应用
</c:set><c:set var="html_body_content" scope="request">
	<div class="reg_l">
		<div class="step"><h2>注册</h2></div>
		<div class="step"><h2>关注朋友</h2></div>
		<div class="step selected"><h2>绑定应用</h2><img class="selected" src="${ctx_path }/webtb/img/signup_arrow.gif" /></div>
	</div>
	<div class="reg_r">
		<ul class="rowlist t">
			<li>
				<c:if test="${!sina_binded}">
					<div class="row">
						<input type="button" class="btn" value="安全验证新浪微博" onclick="bindsina()"/>
					</div>
					<div class="row">
						认证后，我们将推荐顾问家的网友关注您的微博；同时您的新浪粉丝通过您来到顾问家，也将会自动关注您。
					</div>
				</c:if>
				<c:if test="${sina_binded}">
					<div class="row">
						已绑定新浪微博
					</div>
					<div class="row">
						认证后，我们将推荐顾问家的网友关注您的微博；同时您的新浪粉丝通过您来到顾问家，也将会自动关注您。
					</div>
				</c:if>
			</li>
		</ul>
		<div class="row">
		<input type="button" class="btn" value="下一步" onclick="tourl('${ctx_path}/tb/user_home')"/>
		</div>
	</div>
<div class="clr"></div>
<script type="text/javascript">
function bindsina(){
	tourl('/tb/op/guide_bindsina');
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>