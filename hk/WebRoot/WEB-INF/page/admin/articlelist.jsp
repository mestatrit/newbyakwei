<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.bean.HkObjArticle"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title">查看<hk:data key="hkobjarticle.checkflg_${checkflg}"/>的数据</c:set>
<hk:wap title="${view_title} - 相关文章审核" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form method="get" action="/admin/article_list.do">
		查看<hk:data key="hkobjarticle.checkflg_${checkflg}"/>的数据<br/>
			<hk:select name="checkflg" checkedvalue="${checkflg}">
				<hk:option value="<%=HkObjArticle.CHECKFLG_N %>" data="hkobjarticle.checkflg_0" res="true"/>
				<hk:option value="<%=HkObjArticle.CHECKFLG_FAIL %>" data="hkobjarticle.checkflg_1" res="true"/>
				<hk:option value="<%=HkObjArticle.CHECKFLG_Y %>" data="hkobjarticle.checkflg_2" res="true"/>
			</hk:select>
			<hk:submit value="搜索"/>
		</hk:form>
	</div>
	<c:if test="${fn:length(list)>0}">
		<c:forEach var="a" items="${list}" varStatus="idx">
			<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if><c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
			<div class="hang ${clazz_var }">
				<hk:a href="/admin/article.do?articleId=${a.articleId}&search_checkflg=${checkflg }">${a.title}</hk:a>
			</div>
		</c:forEach>
		<hk:simplepage clazz="page" href="/admin/article_list.do?checkflg=${checkflg}"/>
	</c:if>
	<c:if test="${fn:length(list)==0}"><div class="hang"><hk:data key="nodatalist"/></div></c:if>
	<div class="hang"><hk:a href="/more.do"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>