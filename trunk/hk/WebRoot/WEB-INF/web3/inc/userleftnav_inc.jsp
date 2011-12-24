<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path=request.getContextPath();%><c:if test="${userLogin}"><%JspDataUtil.loadLoginUser(request);%></c:if>
<c:if test="${userLogin}">
<div class="mod_left">
	<div class="mod-1">
		<%=Hkcss2Util.rd_bg%>
		<div class="tit"><hk:data key="view.user.mgr.title"/></div>
		<div class="cont"> 
			<div class="subtit">个人信息</div>
			<ul class="userset">
				<li><a id="op_1" class="n1" href="<%=path %>/user/set/set_tosetheadweb.do"><hk:data key="view.user.mgr.head"/></a></li>
				<li><a id="op_2" class="n1" href="<%=path %>/user/set/set_tosetinfoweb.do"><hk:data key="view.user.mgr.info"/></a></li>
			</ul>
			<div class="subtit">绑定设置</div>
			<ul class="userset">
				<c:if test="${validateemail}">
				<li><a id="op_3" class="n1" href="<%=path %>/user/set/set_tosetemailweb.do"><hk:data key="view.user.mgr.changeemail"/></a></li>
				</c:if>
				<c:if test="${!validateemail}">
				<li><a id="op_3" class="n1" href="<%=path %>/user/set/set_tosetemailweb.do"><hk:data key="view.user.mgr.validateemail"/></a></li>
				</c:if>
				<c:if test="${!mobileBind}">
				<li><a id="op_4" class="n1" href="<%=path %>/user/set/set_tosetmobile2web.do"><hk:data key="view.user.mgr.bindmobile"/></a></li>
				</c:if>
				<c:if test="${mobileBind}">
				<li><a id="op_4" class="n1" href="<%=path %>/user/set/set_tochgmobileweb.do"><hk:data key="view.user.mgr.chagenmobile"/></a></li>
				</c:if>
				<li><a id="op_5" class="n1" href="<%=path %>/user/set/set_tosetmsnweb.do"><hk:data key="view.user.mgr.setmsn"/></a></li>
			</ul>
			<div class="subtit">密码</div>
			<ul class="userset">
				<li><a id="op_6" class="n1" href="<%=path %>/user/set/set_tosetpwdweb.do"><hk:data key="view.user.mgr.setpwd"/></a></li>
				<li><a id="op_7" class="n1" href="<%=path %>/user/set/set_tosetprotectweb.do"><hk:data key="view.user.mgr.setprotect"/></a></li>
			</ul>
			<div class="subtit">通知</div>
			<ul class="userset">
				<li><a id="op_8" class="n1" href="<%=path %>/user/set/set_tosetnoticeinfoweb.do"><hk:data key="view.user.mgr.msgnotice"/></a></li>
				<li><a id="op_9" class="n1" href="<%=path %>/user/set/set_tosetlabartflgweb.do"><hk:data key="view.user.mgr.labart"/></a></li>
			</ul>
			<div class="subtit">宝箱功能</div>
			<ul class="userset">
				<li><a id="op_10" class="n1" href="<%=path %>/box/op/op_web.do"><hk:data key="view.box.create"/></a></li>
				<li><a id="op_11" class="n1" href="<%=path %>/box/op/op_my.do"><hk:data key="view.user.box"/></a></li>
			</ul>
			<div class="subtit">订单</div>
			<ul class="userset">
				<li><a id="op_12" class="n1" href="<%=path %>/op/orderform.do"><hk:data key="view.user.order"/></a></li>
			</ul>
		</div>
		<%=Hkcss2Util.rd_bg_bottom%>
	</div>
</div>
</c:if>