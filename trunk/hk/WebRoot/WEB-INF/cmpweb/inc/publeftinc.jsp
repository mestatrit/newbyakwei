<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<div class="page_l">
<c:if test="${fn:length(children)==0}">
<%EppViewUtil.loadCmpAd(request); %>
<c:if test="${fn:length(cmpadlist)>0}">
	<c:forEach var="cmpad" items="${cmpadlist}">
		<div class="mod">
			<div>
			<a href="http://${cmpad.url }" target="_blank"><img src="${cmpad.picUrl }"/></a>
			</div>
			<c:if test="${not empty cmpad.name}">
				<div style="margin-top: 2px">
					<a href="http://${cmpad.url }" target="_blank">${cmpad.name }</a>
				</div>
			</c:if>
		</div>
	</c:forEach>
</c:if>
</c:if>
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
		<c:if test="${fn:length(l_1_list)>0}">
			<div class="nav_2">
				<c:forEach var="l_1_sort" items="${l_1_list}">
					<div class="nav2_content">
						<c:if test="${l_1_sort.hasChildren}">
							<c:set var="current_sortId" value="${l_1_sort.sortId}" scope="request"/>
							<%EppViewUtil.loadCurrentSort(request); %>
							<div <c:if test="${is_current_sort}">class="nav2_active"</c:if>>${l_1_sort.name }</div>
							<%request.removeAttribute("current_sortId");request.removeAttribute("is_current_sort");%>
						</c:if>
						<c:if test="${!l_1_sort.hasChildren}">
							<a class="<c:if test="${sortId==l_1_sort.sortId}">nav2_active</c:if>" href="/products/${companyId}/${navId}/sort/${l_1_sort.sortId}/">${l_1_sort.name }</a>
							<c:set var="current_each_sortId" value="${l_1_sort.sortId}" scope="request"/>
							<jsp:include page="product_lef_inc.jsp"></jsp:include>
						</c:if>
						<c:if test="${fn:length(l_1_sort.children)>0}">
							<div style="padding-left: 20px;" class="bdtm">
								<c:forEach var="l_2_sort" items="${l_1_sort.children}">
									<div class="nav2_content">
										<c:if test="${l_2_sort.hasChildren}">
											<c:set var="current_sortId" value="${l_2_sort.sortId}" scope="request"/>
											<%EppViewUtil.loadCurrentSort(request); %>
											<div <c:if test="${is_current_sort}">class="nav2_active"</c:if>>${l_2_sort.name }</div>
											<%request.removeAttribute("current_sortId");request.removeAttribute("is_current_sort");%>
										</c:if>
										<c:if test="${!l_2_sort.hasChildren}">
											<a class="<c:if test="${sortId==l_2_sort.sortId}">nav2_active</c:if>" href="/products/${companyId}/${navId}/sort/${l_2_sort.sortId}/">${l_2_sort.name }</a>
											<c:set var="current_each_sortId" value="${l_2_sort.sortId}" scope="request"/>
											<jsp:include page="product_lef_inc.jsp"></jsp:include>
										</c:if>
										<c:if test="${fn:length(l_2_sort.children)>0}">
											<div style="padding-left: 20px;" class="bdtm">
												<c:forEach var="l_3_sort" items="${l_2_sort.children}">
													<div class="nav2_content">
														<a class="<c:if test="${sortId==l_3_sort.sortId}">nav2_active</c:if>" href="/products/${companyId}/${navId}/sort/${l_3_sort.sortId}/">${l_3_sort.name }</a>
														<c:set var="current_each_sortId" value="${l_3_sort.sortId}" scope="request"/>
														<jsp:include page="product_lef_inc.jsp"></jsp:include>
													</div>
												</c:forEach>
											</div>
										</c:if>
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