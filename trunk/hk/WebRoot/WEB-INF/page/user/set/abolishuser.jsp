<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="合并帐号 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">合并帐号</div>
	<div class="hang">如果您在火酷有多个帐号,请使用帐号合并功能.输入另外一个帐号的用户名和密码完成帐号合并.</div>
	<div class="hang">
	<hk:form method="post" action="/user/set/set_abolishUser.do">
		Email,手机号或昵称:<br/>
		<hk:text name="input" value="${input}" maxlength="50"/><br/><br/>
		密码:<br/>
		<hk:pwd name="pwd" maxlength="16" /><br/>
		<div class="hang"><hk:submit value="提交合并" /></div>
	</hk:form>
	</div>
	<div class="hang"><hk:a href="/user/set/set.do">回到设置</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>