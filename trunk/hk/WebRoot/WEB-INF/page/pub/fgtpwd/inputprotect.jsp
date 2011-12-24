<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="忘记密码 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">输入你的E-mail</div>
	<div class="hang">
		<hk:form action="/pub/fgtpwd/fgtpwd_chekProtect.do">
		<hk:select name="pconfig" checkedvalue="${userProtect.pconfig}">
			<c:forEach var="p" items="${list}">
				<hk:option value="${p.id}" data="${p.name}" />
			</c:forEach>
		</hk:select><br/>
		密码保护答案:<br/>
		<hk:text name="pvalue" maxlength="50"/><br/>
		<hk:submit value="提交"/>
		</hk:form>
	</div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>