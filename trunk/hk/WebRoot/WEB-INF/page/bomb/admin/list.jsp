<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="爆破手管理 - 火酷" rm="false" style="/page/css/b.css">
	<jsp:include page="../../inc/top.jsp"></jsp:include>
	<div class="hang">爆破手管理</div>
	<c:if test="${fn:length(list)==0}">
	没有数据显示
	</c:if>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="b" items="${list}" varStatus="idx">
			<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="hang odd" /></c:if>
			<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="hang even" /></c:if>
			<div class="${clazz_var}">
				${b.user.nickName} 
				${b.remainCount }个炸弹 
				${b.remainPinkCount }个精华 
				<hk:a href="/adminbomb/bomb_clearbomb.do?userId=${b.userId}">没收炸弹</hk:a> 
				<hk:a href="/adminbomb/bomb_clearpink.do?userId=${b.userId}">没收精华</hk:a>
				<hk:a href="/bomb/bomb_list.do?userId=${b.userId}&from=list" page="true">爆破日志</hk:a>
				<hk:a href="/bomb/bomb_pinklabalist.do?userId=${b.userId}&from=list" page="true">精华日志</hk:a>
			</div>
		</c:forEach>
		<hk:simplepage clazz="page" href="/adminbomb/bomb_list.do"/>
	</c:if>
	<div class="hang"><hk:a href="/adminbomb/bomb.do">返回</hk:a></div>
	<jsp:include page="../../inc/foot.jsp"></jsp:include>
</hk:wap>