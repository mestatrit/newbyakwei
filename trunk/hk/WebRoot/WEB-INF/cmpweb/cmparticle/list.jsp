<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="title_value" scope="request">${cmpNav.name }</c:set>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="${cmpNav.name }|${o.name}"/>
<meta name="description" content="${cmpNav.name }|${o.name}"/>
</c:set>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
<div class="page_r">
	<div class="mod">
		<h1 class="s1">${cmpNav.name }</h1>
		<div class="mod_content">
			<c:if test="${!cmpNav.articleListWithImgShow}">
				<ul class="article2">
					<c:forEach var="article" items="${list}">
						<li onmouseover="this.className='bg3'" onmouseout="this.className=''">
							<div class="cn">
								<div class="time">
									<fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd"/>
								</div>
								<div class="txt">
								<a href="/article/${companyId}/${navId}/${article.oid}.html" class="split-r">${article.title }</a>
								</div>
								<div class="clr"></div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${cmpNav.articleListWithImgShow}">
				<c:forEach var="article" items="${list}">
					<div class="imglistmod">
					<a title="${article.title }" href="/article/${companyId}/${navId}/${article.oid}.html">
						<c:if test="${not empty article.filepath}"><img src="${article.cmpFilePic120_2 }" title="${article.title }"/></c:if>
						<c:if test="${empty article.filepath}"><span class="nopic">暂无图片</span></c:if>
					</a>
					</div>
				</c:forEach>
				<div class="clr"></div>
			</c:if>
			<div>
				<c:set var="url_rewrite" scope="request" value="true"/>
				<c:set var="page_url" scope="request">/articles/${companyId}/${navId}</c:set>
				<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>