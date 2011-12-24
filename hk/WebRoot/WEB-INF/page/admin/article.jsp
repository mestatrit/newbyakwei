<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="e.admin.addbizcle.title"/></c:set>
<hk:wap title="${o.title}相关文章审核" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		<hk:form action="/admin/article_check.do">
		文章标题:<br/>
		<hk:text name="article_title" value="${o.title}" maxlength="20"/><br/><br/>
		文章地址:<br/><a href="http://${o.url }" target="_blank">http://${o.url }</a> (<hk:data key="hkobjarticle.authorflg_${o.authorflg}"/>)<br/><br/>
		E-mail:<br/>${o.email }<br/><br/>
		<c:if test="${not empty o.tel}">
			联系电话:<br/>${o.tel }<br/><br/>
		</c:if>
		<c:if test="${not empty o.author}">
			作者姓名:<br/>${o.author }<br/><br/>
		</c:if>
		<c:if test="${not empty o.blog}">
			博客地址:<br/><a href="http://${o.blog }" target="_blank">http://${o.blog }</a><br/><br/>
		</c:if>
			<hk:hide name="search_checkflg" value="${search_checkflg}"/>
			<hk:hide name="articleId" value="${articleId}"/>
			<c:if test="${o.notCheck}">
				<hk:submit name="fail" value="审核不通过"/> 
				<hk:submit name="ok" value="审核通过"/> 
				<hk:submit name="ok_edit" value="审核通过并修改标题"/> 
			</c:if>
			<c:if test="${o.checkFail}">
				<hk:submit name="not" value="设为未审核"/> 
				<hk:submit name="ok" value="设为审核通过"/> 
				<hk:submit name="ok_edit" value="审核通过并修改标题"/> 
			</c:if>
			<c:if test="${o.checkOk}">
				<hk:submit name="not" value="设为未审核"/> 
				<hk:submit name="fail" value="设为审核不通过"/> 
				<hk:submit name="edit" value="修改标题"/> 
			</c:if>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/admin/article_list.do?checkflg=${search_checkflg}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>