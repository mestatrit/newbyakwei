<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="title_value" scope="request">${cmpProductSort.name }|${cmpNav.name }</c:set>
<c:set var="epp_other_value" scope="request">
<meta name="keywords" content="${cmpProductSort.name }|${cmpNav.name }|${o.name}"/>
<meta name="description" content="${cmpProductSort.name }|${cmpNav.name }|${o.name}"/>
</c:set>
<c:set var="html_body_content" scope="request">
<jsp:include page="../inc/publeftinc.jsp"></jsp:include>
<div class="page_r">
	<div class="mod">
		<h1 class="s1">${cmpNav.name }</h1>
		<div class="mod_content">
			<c:if test="${cmpProductSort!=null}">
				<div style="margin: 20px 0"><h1>${cmpProductSort.name }</h1></div>
			</c:if>
			<c:if test="${fn:length(list)==0}">
				<hk:data key="epp.productlist.nodata"/>
			</c:if>
			<br class="linefix"/>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<c:forEach var="product" items="${list}" varStatus="idx">
							<div class="productmod"><c:set var="product_url">/product/${companyId}/${navId}/${sortId}/${product.productId }.html</c:set>
								<div class="imgbox"><a href="${product_url }"><img title="${product.name }" alt="${product.name }" src="${product.head120 }"/></a></div>
								<div class="name"><a class="b" href="${product_url }">${product.name }</a></div>
							</div>
						</c:forEach>
						<div class="clr"></div>
					</td>
				</tr>
			</table>
			<div>
			<c:set var="url_rewrite" scope="request" value="true"/>
			<c:set var="page_url" scope="request">/products/${companyId}/${navId}</c:set>
			<jsp:include page="../inc/pagesupport_inc.jsp"></jsp:include>
			</div>
		</div>
	</div>
</div>
<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>