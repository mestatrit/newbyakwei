<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="inc/top.jsp"></jsp:include>
	<div class="hang" onkeydown="submitLaba(event)">
		<hk:a clazz="s" href="/help_how.do?from=square&w=${w}" page="true"><hk:data key="view.laba.createlabatip"/></hk:a><br/>
		<hk:form name="labafrm" onsubmit="return confirmCreate();" method="post" action="/laba/op/op_create.do">
			<input type="hidden" name="lastUrl" value="/square.do?w=${w }"/>
			<textarea id="status" name="content" class="ipt2" rows="2"></textarea><br />
			<hk:submit value="提交" /> <span id="remaining" class="ruo s">140</span><span class="ruo s"><hk:data key="view.char"/></span>
		</hk:form>
		<jsp:include page="inc/labainputjs.jsp"></jsp:include>
	</div>
	<div class="hang">
		<hk:rmBlankLines rm="true">
			<c:if test="${w=='all'}"><hk:a href="/square.do?w=all" clazz="nn">全部</hk:a></c:if>
			<c:if test="${w!='all'}"><hk:a href="/square.do?w=all">全部</hk:a></c:if>|
			<c:if test="${w=='city'}"><hk:a href="/square.do?w=city" clazz="nn">本市</hk:a></c:if>
			<c:if test="${w!='city'}"><hk:a href="/square.do?w=city">本市</hk:a></c:if>|
			<c:if test="${w=='range'}"><hk:a href="/square.do?w=range" clazz="nn">周边</hk:a></c:if>
			<c:if test="${w!='range'}"><hk:a href="/square.do?w=range">周边</hk:a></c:if>|
			<c:if test="${w=='ip'}"><hk:a href="/square.do?w=ip" clazz="nn">旁边</hk:a></c:if>
			<c:if test="${w!='ip'}"><hk:a href="/square.do?w=ip">旁边</hk:a></c:if>
			<c:if test="${loginUser!=null}">
			|<c:if test="${w=='we'}"><hk:a href="/square.do?w=we" clazz="nn">关注</hk:a></c:if>
			<c:if test="${w!='we'}"><hk:a href="/square.do?w=we">关注</hk:a></c:if>
			</c:if>
		</hk:rmBlankLines>
	</div>
	<c:if test="${fn:length(labavolist)>0}">
		<c:set var="addlabastr" value="from=square&w=${w }&ipCityId=${ipCityId }" scope="request"/>
		<jsp:include page="inc/labavo2.jsp"></jsp:include>
		<hk:simplepage clazz="page" href="/square.do?w=${w}&ipCityId=${ipCityId }"/>
	</c:if>
	<c:if test="${fn:length(labavolist)==0}">没有数据显示</c:if>
	<jsp:include page="inc/foot.jsp"></jsp:include>
</hk:wap>