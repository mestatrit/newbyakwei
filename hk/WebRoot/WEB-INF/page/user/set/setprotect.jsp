<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="修改密码保护 - 火酷" rm="false">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form method="post" action="/user/set/set_setProtect.do">
			<hk:select name="pconfig" checkedvalue="${userProtect.pconfig}">
				<c:forEach var="p" items="${list}">
					<hk:option value="${p.id}" data="${p.name}" />
				</c:forEach>
			</hk:select><br/><br/>
			密码保护答案:<br/>
			<hk:text name="pvalue" value="${userProtect.pvalue}" maxlength="50"/><br/>
			<hk:submit value="保存"/>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/user/set/set.do">回到设置</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>