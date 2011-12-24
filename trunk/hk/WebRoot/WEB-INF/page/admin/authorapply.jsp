<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:set var="view_title"><hk:data key="e.admin.addbizcle.title"/></c:set>
<hk:wap title="${o.name }加入火眼金睛俱乐部申请" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div class="hang">
		${o.name }<br/><br/>
		E-mail:<br/>${o.email }<br/><br/>
		<c:if test="${not empty o.tel}">
			联系电话:<br/>${o.tel }<br/><br/>
		</c:if>
		<c:if test="${not empty o.blog}">
			博客地址:<br/><a href="http://${o.blog }" target="_blank">http://${o.blog }</a><br/><br/>
		</c:if>
			申请原因:<br/>${o.content }<br/><br/>
		<hk:form action="/admin/authorapply_check.do">
			分类:<hk:text name="tag_name" value="${authorTag.name}"/>
			<hk:hide name="search_checkflg" value="${search_checkflg}"/><br/><br/>
			<hk:hide name="oid" value="${oid}"/>
			<c:if test="${o.notCheck}">
				<hk:submit name="fail" value="设为审核不通过"/> 
				<hk:submit name="ok" value="设为审核通过"/> 
			</c:if>
			<c:if test="${o.checkFail}">
				<hk:submit name="not" value="设为未审核"/> 
				<hk:submit name="ok" value="设为审核通过"/> 
			</c:if>
			<c:if test="${o.checkOk}">
				<hk:submit name="not" value="设为未审核"/> 
				<hk:submit name="fail" value="设为审核不通过"/> 
				<hk:submit name="edit" value="修改分类"/> 
			</c:if>
		</hk:form>
	</div>
	<div class="hang"><hk:a href="/admin/article_list.do?checkflg=${search_checkflg}"><hk:data key="view.return"/></hk:a></div>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>