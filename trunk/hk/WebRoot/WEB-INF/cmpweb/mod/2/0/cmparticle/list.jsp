<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%
	String path = request.getContextPath();
%>
<c:set var="html_title" scope="request">
	<hk:data key="epp.login" />
</c:set>
<c:set var="html_body_content" scope="request">
	<jsp:include page="../inc/leftinc.jsp"></jsp:include>
	<div class="pr">
		<div class="mod">
			<div class="mainnav">
				<a href="http://<%=request.getServerName() %>">首页</a>
				<c:if test="${parent_cmpNav!=null}">
				 &gt; <a href="/column/${companyId }/${parent_cmpNav.oid }/">${parent_cmpNav.name }</a>
				</c:if>
				 &gt; <a href="/column/${companyId }/${cmpNav.oid }/">${cmpNav.name }</a>
			</div>
		</div>
		<div class="mod">
			<div class="mod_tit">${cmpNav.name }</div>
			<div class="content">
				<c:if test="${not empty cmpInfo.columnad}">${cmpInfo.columnad }</c:if>
				<c:if test="${fn:length(list)==0}"><hk:data key="epp.cmparticle.nodatalist"/></c:if>
				<c:if test="${!cmpNav.articleListWithImgShow}">
					<c:if test="${fn:length(list)>0}">
						<ul class="datalist">
							<c:forEach var="article" items="${list}">
								<li>
									<span class="title"><a href="/article/${companyId }/${navId}/${article.oid}.html">${article.title }</a></span>
									<span class="time"><fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/></span>
									<div class="clr"></div>
								</li>
							</c:forEach>
						</ul>
					</c:if>
				</c:if>
				<c:if test="${cmpNav.articleListWithImgShow}">
					<c:if test="${fn:length(list)>0}">
						<div style="padding-left:30px;">
							<ul class="piclist120">
								<c:forEach var="article" items="${list}">
									<li>
										<div class="imgbox">
											<a title="${article.title }" alt="${article.title }" href="/article/${companyId }/${navId}/${article.oid}.html"><img src="${article.cmpFilePic120 }"/></a>
										</div>
									</li>
								</c:forEach>
							</ul>
							<div class="clr"></div>
						</div>
					</c:if>
				</c:if>
				<div class="fr">
					<c:set var="page_url" scope="request">/articles/${companyId }/${cmpNav.oid }</c:set>
					<c:set var="url_rewrite" scope="request" value="true"/>
					<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
				</div>
				<div class="clr"></div>
			</div>
		</div>
		<c:if test="${cmpNav.kindId>0 && fn:length(studyadlist)>0}">
			<div class="mod">
				<div class="mod_tit">招生简章</div>
				<div class="content">
					<div>
						<h1></h1>
						<ul class="datalist">
							<c:forEach var="studyad" items="${studyadlist}">
								<li>
									<span class="title"><a target="_blank" href="/edu/${companyId }/${studyad.orgId }/zhaosheng/0/${studyad.adid }.html">${studyad.title }</a></span>
									<span class="time"><fmt:formatDate value="${studyad.createTime}" pattern="yyyy-MM-dd"/></span>
									<div class="clr"></div>
								</li>
							</c:forEach>
						</ul>
						<c:if test="${more_studyad}">
						<div align="right"><a href="/zhaosheng/${companyId }/${cmpNav.kindId}"><hk:data key="epp.more"/></a></div>
						</c:if>
					</div>
				</div>
			</div>
		</c:if>
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