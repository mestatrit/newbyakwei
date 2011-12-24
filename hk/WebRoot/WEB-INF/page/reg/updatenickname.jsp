<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
		<hk:form method="post" action="/next/op/op_updateNickName.do">
			<div class="hang">请输入您的网名或昵称:</div>
			<div class="hang"><hk:text name="nickName"/></div>
			<div class="hang"><hk:submit value="提交"/> 
			<hk:submit name="jump" value="跳过"/></div>
		</hk:form>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>