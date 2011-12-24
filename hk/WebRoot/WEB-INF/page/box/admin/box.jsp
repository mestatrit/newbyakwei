<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="${box.name} - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">
		宝箱名称:<br/>
		${box.name}<br/><br/>
		宝箱介绍:<br/>
		${box.intro}<br/><br/>
		宝箱数量:<br/>
		${box.totalCount}<br/><br/>
		剩余数量:<br/>
		${box.totalCount-box.openCount}<br/><br/>
		时间:<br/>
		<fmt:formatDate value="${box.beginTime}" pattern="yyyy-MM-dd HH:mm"/>开始<br/>
		<fmt:formatDate value="${box.endTime}" pattern="yyyy-MM-dd HH:mm"/>结束
		<br/><br/>
		短信开箱暗号:<br/>
		${box.boxKey}<br/><br/>
		宝箱类别:<br/>
		${boxType.name }<br/><br/>
		开箱限制:<br/>
		<c:if test="${box.pretype==0}">不限制</c:if>
		<c:if test="${box.pretype!=0}">${box.precount }个/每人每${boxPretype.name }</c:if>
		<br/><br/>
		参与方式:<br/>
		<hk:rmBlankLines rm="true">
			<c:if test="${box.opentype==0}">网站和短信</c:if>
			<c:if test="${box.opentype==1}">短信</c:if>
			<c:if test="${box.opentype==2}">网站</c:if>
		</hk:rmBlankLines>
		<br/><br/>
		奖品列表:<br/>
		<c:forEach var="p" items="${list}" varStatus="idx">
			${idx.count }. ${p.name}/${p.pcount}个/剩余${p.remain }个
		</c:forEach>
		<br/>
		<hk:form action="/box/admin/adminbox_optbox.do">
			<hk:hide name="boxId" value="${boxId}"/>
			<hk:hide name="repage" value="${repage}"/>
			<hk:hide name="t" value="${t}"/>
			<c:if test="${normal}">
				<hk:submit name="pause" value="停止"/>
				<hk:submit name="stop" value="作废"/>
				<hk:submit name="editbox" value="修改宝箱信息"/>
				<hk:submit name="editkey" value="修改开箱暗号"/>
			</c:if>
			<c:if test="${pause}">
				<hk:submit name="cont" value="继续"/>
				<hk:submit name="stop" value="作废"/>
				<hk:submit name="editbox" value="修改宝箱信息"/>
				<hk:submit name="editkey" value="修改开箱暗号"/>
			</c:if>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/box/admin/adminbox_back.do?t=${t}&repage=${repage }">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>