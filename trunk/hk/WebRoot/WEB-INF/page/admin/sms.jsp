<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="短信发送 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
		短信发送<br/>
		<hk:form action="/admin/smssend_send.do">
			手机号:<br/>
			<hk:text name="mobile" maxlength="11"/><br/><br/>
			内容:<br/>
			<hk:textarea name="content"/><br/>
			<hk:submit value="view.submit" res="true"/>
		</hk:form>
	</div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>