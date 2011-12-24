<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${box.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">${box.name}</div>
	<div class="hang">剩余${box.totalCount-box.openCount}个</div>
	<div class="hang odd">
		<div>${box.intro}</div>
		<div>${box.name}的奖品有:</div>
		<c:forEach var="p" items="${list}" varStatus="idx">
			<div class="${clazz_var}">${p.name} ${p.pcount}个</div>
		</c:forEach>
		<div>
			<c:if test="${onlysmsopen}">
				<div class="orange">提示:本宝箱仅仅支持短信开箱,请在活动现场通过短信参与.参与暗号请通过现场获得</div>
			</c:if>
			<c:if test="${!onlysmsopen && begin && !stop}">
				<hk:form action="/op/box/open.do">
					<hk:hide name="t" value="${t}"/>
					<hk:hide name="repage" value="${repage}"/>
					<hk:hide name="boxId" value="${boxId}"/>
					<hk:submit value="开箱子"/>
				</hk:form>
			</c:if>
		</div>
		<c:if test="${smsandweb && not empty box.boxKey}">
		<div>贴士:编辑<span class="orange">${box.boxKey }</span>发送短信到1066916025可直接开箱</div>
		</c:if>
	</div>
	<div class="hang"><hk:a href="/box/box_back.do?t=${t}&repage=${repage }">返回</hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>