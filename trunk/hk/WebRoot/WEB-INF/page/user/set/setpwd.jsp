<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="修改密码 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
	<hk:form method="post" action="/user/set/set_setPwd.do">
		现在使用的密码:<br/>
		<hk:pwd name="oldPwd" maxlength="16"/><br/><br/>
		新密码:<br/>
		<hk:pwd name="newPwd" maxlength="16"/><br/>
		<hk:submit value="保存"/>
	</hk:form>
	</div>
	<div class="hang">
	通过短信重置密码:<br/>
	如果您已经输入了手机号，请直接发送新密码到${number }，完成密码修改
	</div>
	<div class="hang"><hk:a href="/user/set/set.do">回到设置</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>