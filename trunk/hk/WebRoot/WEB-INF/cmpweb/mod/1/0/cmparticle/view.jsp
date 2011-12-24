<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="epp_other_value" scope="request">
	<meta name="keywords" content="${cmpArticle.title }|${cmpProduct.name }|${o.name}" />
	<meta name="description" content="${cmpArticle.title }|${cmpProduct.name }|${o.name}" />
</c:set>
<c:set var="html_body_content" scope="request">
<div class="mod">
<div style="margin-bottom: 10px;"><h1>${cmpArticle.title }</h1></div>
<c:if test="${cmpProduct!=null}">
	<div class="article_product">
	<c:if test="${not empty cmpProduct.headPath}"><a href="<%=path %>/epp/web/product_view.do?companyId=${companyId}&productId=${cmpProduct.productId}"><img src="${cmpProduct.head320 }"/></a><br/></c:if>
	${cmpProduct.name }<br/>
	<hk:data key="epp.product.price"/> ：￥${cmpProduct.money }
	</div>
</c:if>
${cmpArticleContent.content }
</div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>