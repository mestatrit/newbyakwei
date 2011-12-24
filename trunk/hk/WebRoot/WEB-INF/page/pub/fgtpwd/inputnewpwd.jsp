<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="忘记密码 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">输入新的密码</div>
	<div>
		<hk:form action="/pub/fgtpwd/fgtpwd_saveNewPwd.do">
		新密码:<br/>
		<hk:pwd name="password"/><br/>
		<hk:submit value="提交"/>
		</hk:form>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>