<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<div class="page_l">
<c:if test="${fn:length(children)>0}">
	<div class="mod nav_2_mod">
		<c:if test="${empty parent_cmpNav.filepath}">
			<div class="nav_2_parent">${parent_cmpNav.name }</div>
		</c:if>
		<c:forEach var="child_nav" items="${children}">
			<div class="nav_2">
				<a class="nav_2_a <c:if test="${navId==child_nav.oid}">nav2_active</c:if>" href="/column/${companyId}/${child_nav.oid}">${child_nav.name }</a>
				<c:if test="${child_nav.oid==navId}">
					<c:if test="${fn:length(before_cmparticle_list)>0 || fn:length(after_cmparticle_list)>0}">
						<div>
							<c:forEach var="other_article" items="${before_cmparticle_list}">
								<div class="nav2_content">
									<a href="/article/${companyId}/${navId}/${other_article.oid}.html">${other_article.title }</a>
								</div>
							</c:forEach>
							<c:forEach var="other_article" items="${after_cmparticle_list}">
								<div class="nav2_content">
									<a href="/article/${companyId}/${navId}/${other_article.oid}.html">${other_article.title }</a>
								</div>
							</c:forEach>
						</div>
					</c:if>
				</c:if>
			</div>
		</c:forEach>
		<c:if test="${fn:length(productsortlist)>0}">
			<div class="nav_2">
				<c:forEach var="nav_sort" items="${productsortlist}">
					<div class="nav2_content">
						<a class="<c:if test="${sortId==nav_sort.sortId}">nav2_active</c:if>" href="/products/${companyId}/${navId}/sort/${nav_sort.sortId}/">${nav_sort.name }</a>
						<c:if test="${sortId==nav_sort.sortId && (fn:length(before_other_product_list)>0 || fn:length(after_other_product_list)>0)}">
							<div style="padding-left: 20px;" class="bdtm">
								<c:forEach var="other_product" items="${before_other_product_list}">
								<c:set var="product_url">/product/${companyId}/${navId}/${nav_sort.sortId}/${other_product.productId }.html</c:set>
								<div class="nav2_content">
									<a href="${product_url }">${other_product.name }</a>
								</div>
								</c:forEach>
								<c:forEach var="other_product" items="${after_other_product_list}">
								<c:set var="product_url">/product/${companyId}/${navId}/${nav_sort.sortId}/${other_product.productId }.html</c:set>
								<div class="nav2_content">
									<a href="${product_url }">${other_product.name }</a>
								</div>
								</c:forEach>
							</div>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</c:if>
	</div>
</c:if>
</div>