<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<c:if test="${fn:length(glist)>0}">
	<div class="hang even">
	<hk:rmBlankLines rm="true">
		<hk:a href="/user/list2.do" clazz="nn">全部</hk:a>|
	<hk:rmstr value="|">
		<c:forEach var="g" items="${glist}">
		<hk:a href="/group/gulist.do?gid=${g.groupId}">${g.name}</hk:a>|
		</c:forEach>
	</hk:rmstr>
	</hk:rmBlankLines>
	</div>
	</c:if>
	<div class="hang odd">
	<hk:form method="get" action="/user/search.do">
		<hk:hide name="sfrom" value="userlist1"/>
		昵称:<hk:text name="sw"/>
		<hk:submit value="搜索"/>
	</hk:form>
	</div>
	<div class="hang even">
		<hk:rmBlankLines rm="true">
				<c:if test="${w=='all'}"><hk:a href="/user/list1.do?w=all" clazz="nn">全部</hk:a></c:if>
				<c:if test="${w!='all'}"><hk:a href="/user/list1.do?w=all">全部</hk:a></c:if>|
				<c:if test="${w=='city'}"><hk:a href="/user/list1.do?w=city" clazz="nn">本市</hk:a></c:if>
				<c:if test="${w!='city'}"><hk:a href="/user/list1.do?w=city">本市</hk:a></c:if>|
				<c:if test="${w=='range'}"><hk:a href="/user/list1.do?w=range" clazz="nn">附近</hk:a></c:if>
				<c:if test="${w!='range'}"><hk:a href="/user/list1.do?w=range">附近</hk:a></c:if>|
				<c:if test="${w=='ip'}"><hk:a href="/user/list1.do?w=ip" clazz="nn">旁边</hk:a></c:if>
				<c:if test="${w!='ip'}"><hk:a href="/user/list1.do?w=ip">旁边</hk:a></c:if>
		</hk:rmBlankLines>
	</div>
	<c:if test="${fn:length(uservolist)==0}">没有数据显示</c:if>
	<c:if test="${fn:length(uservolist)>0}">
	<c:set var="addStr" value="from=fcuserlist" scope="request"/>
	<jsp:include page="../inc/uservo.jsp"></jsp:include>
	<div class="hang"><hk:a href="/user/list2.do">关注度</hk:a>|<hk:a href="/user/list1.do" clazz="nn">最新注册</hk:a></div>
	
	<hk:simplepage href="/user/list1.do?w=${w}"/>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>