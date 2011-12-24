<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang even">
	<hk:rmBlankLines rm="true">
		<hk:a href="/user/list2.do">全部</hk:a>|
	<hk:rmstr value="|">
		<c:forEach var="g" items="${glist}">
		<c:if test="${gid==g.groupId}">
			<hk:a href="/group/gulist.do?gid=${g.groupId}" clazz="nn">${g.name}</hk:a>|
		</c:if>
		<c:if test="${gid!=g.groupId}">
			<hk:a href="/group/gulist.do?gid=${g.groupId}">${g.name}</hk:a>|
		</c:if>
		</c:forEach>
	</hk:rmstr>
	</hk:rmBlankLines>
	</div>
	<div class="hang odd">
		<hk:form method="get" action="/user/search.do">
		<hk:hide name="sfrom" value="userlist2"/>
		昵称:<hk:text name="sw"/>
		<hk:submit value="搜索"/>
	</hk:form>
	</div>
	<div class="hang even">
	<c:if test="${userLogin && !joined}">
		<hk:form action="/group/op/op_adduser.do">
			<hk:hide name="gid" value="${gid}"/>
			<hk:submit value="加入这个圈子"/>
		</hk:form>
	</c:if>
	<c:if test="${userLogin && joined}">
		<hk:form action="/group/op/op_deluser.do">
			<hk:hide name="gid" value="${gid}"/>
			<hk:submit value="退出这个圈子"/>
		</hk:form>
	</c:if>
	</div>
	<c:if test="${fn:length(uservolist)==0}">没有数据显示</c:if>
	<c:if test="${fn:length(uservolist)>0}">
	<c:set var="addStr" value="from=gulist&ogid=${gid}" scope="request"/>
	<jsp:include page="../inc/uservo.jsp"></jsp:include>
	<div class="hang odd">
	<hk:rmBlankLines rm="true">
		<c:if test="${s=='0'}"><hk:a href="/group/gulist.do?gid=${gid}&s=0" clazz="nn">关注度</hk:a>|</c:if>
		<c:if test="${s!='0'}"><hk:a href="/group/gulist.do?gid=${gid}&s=0">关注度</hk:a>|</c:if>
		<c:if test="${s=='1'}"><hk:a href="/group/gulist.do?gid=${gid}&s=1" clazz="nn">活跃度</hk:a>|	</c:if>
		<c:if test="${s!='1'}"><hk:a href="/group/gulist.do?gid=${gid}&s=1">活跃度</hk:a>|</c:if>
		<c:if test="${s=='2'}"><hk:a href="/group/gulist.do?gid=${gid}&s=2" clazz="nn">最新加入</hk:a></c:if>
		<c:if test="${s!='2'}"><hk:a href="/group/gulist.do?gid=${gid}&s=2">最新加入</hk:a></c:if>
	</hk:rmBlankLines>
	</div>
	<hk:simplepage href="/group/gulist.do?gid=${gid}&s=${s }"/>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>