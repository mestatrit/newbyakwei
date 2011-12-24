<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">
	<hk:data key="epp.login" />
</c:set>
<c:set var="html_body_content" scope="request">
	<jsp:include page="../inc/leftinc.jsp"></jsp:include>
	<div class="pr">
		<div class="mod">
			<div class="mainnav">
				<a href="http://<%=request.getServerName() %>">首页</a>
				 &gt; <a href="<%=path %>/epp/web/cmparticle_search.do?companyId=${companyId }&key=${enc_key }">${key }</a>
			</div>
		</div>
		<div class="mod">
			<div class="mod_tit">${cmpArticleTag.name }</div>
			<div class="content">
				<div class="divrow">
					<form method="get" action="<%=path %>/epp/web/cmparticle_search.do">
						<hk:hide name="companyId" value="${companyId}"/>
						请输入关键词：<input type="text" name="key" value="${key }" class="text"/>
						<hk:submit value="epp.search" res="true"/>
					</form>
				</div>
				<c:if test="${fn:length(list)==0}"><hk:data key="epp.cmparticle.nodatalist"/></c:if>
				<c:if test="${fn:length(list)>0}">
					<ul class="datalist">
						<c:forEach var="article" items="${list}">
							<li>
								<span class="title"><a href="/article/${companyId }/${article.cmpNavOid}/${article.oid}.html">${article.title }</a></span>
								<span class="time"><fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/></span>
								<div class="clr"></div>
							</li>
						</c:forEach>
					</ul>
				</c:if>
				<div class="fr">
					<c:set var="page_url" scope="request"><%=path %>/epp/web/cmparticle_search.do?companyId=${companyId }&key=${enc_key }</c:set>
					<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
	<div class="clr"></div>
<script type="text/javascript">
$(document).ready(function(){
	$('.datalist li').each(function(i){
		$(this).bind('mouseover', function(){
			$(this).css('background-color', '#e5e6e8');
		}).bind('mouseout', function(){
			$(this).css('background-color', '#ffffff');
		});
	});
});
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>