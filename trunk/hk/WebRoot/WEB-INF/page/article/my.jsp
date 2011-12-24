<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<hk:wap title="我提交的相关文章 - 火酷" rm="false" style="/page/css/b.css">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">我提交的相关文章</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="a" items="${list}" varStatus="idx">
			<c:if test="${idx.index%2==0}"><c:set var="css" value="odd" /></c:if>
			<c:if test="${idx.index%2!=0}"><c:set var="css" value="even" /></c:if>
			<div class="hang ${css }">
			<hk:a href="/op/article_toedit.do?articleId=${a.articleId}" page="true">${a.title}</hk:a> 
			<hk:a href="/op/article_del.do?articleId=${a.articleId}" page="true"><hk:data key="view.delete"/></hk:a>
			</div>
		</c:forEach>
	</c:if>
	<hk:simplepage href="/op/article_my.do"/>
	<c:if test="${fn:length(list)==0}"><hk:data key="nodatalist"/></c:if>
	<div class="hang"><hk:a href="/more.do"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>