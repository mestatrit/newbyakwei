<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="配置 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
	<hk:form method="post" action="/user/set/set_setclient.do">
		选择主题:<br/>
		<hk:select name="cssColorId" checkedvalue="${hkStatus.cssColorId}">
			<c:forEach var="c" items="${csslist}">
				<hk:option value="${c.cssColorId}" data="${c.name}"/>
			</c:forEach>
		</hk:select><br/><br/>
		选择模式:<br/>
		<hk:select name="showModeId" checkedvalue="${hkStatus.showModeId}">
			<c:forEach var="c" items="${showlist}">
				<hk:option value="${c.modeId}" data="${c.name}"/>
			</c:forEach>
		</hk:select><br/><br/>
		打开连接方式:<br/>
		<hk:select name="urlModeId" checkedvalue="${hkStatus.urlModeId}">
			<c:forEach var="c" items="${urlModelist}">
				<hk:option value="${c.urlModeId}" data="${c.name}"/>
			</c:forEach>
		</hk:select><br/>
		<hk:submit value="保存"/>
	</hk:form>
	</div>
	<div class="hang"><hk:a href="/user/set/set.do">进入个人设置</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>