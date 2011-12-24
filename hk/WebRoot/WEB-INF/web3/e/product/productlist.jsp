<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<jsp:include page="../../inc/pub_inc.jsp"></jsp:include>
<c:set var="html_title" scope="request">
<c:if test="${cmpProductSort!=null}">${cmpProductSort.name}</c:if>
<c:if test="${cmpProductSort==null}">产品</c:if>
- ${company.name}</c:set>
<c:set var="body_hk_content" scope="request">
	<div class="mod_left">
		<jsp:include page="../../inc/left_search.jsp"></jsp:include>
		<div class="mod">
			<div class="mod-5 simple_nav">
				<%=Hkcss2Util.rd_bg %>
				<div class="cont">
					<div class="pad">
						<ul class="userset">
							<c:forEach var="k" items="${cmpproductsortlist}">
							<li>
								<a class="n1" href="<%=path %>/product_list.do?companyId=${companyId }&sortId=${k.sortId }">${k.name }<span>(${k.productCount })</span></a>
							</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<%=Hkcss2Util.rd_bg_bottom %>
			</div>
			<div class="clr"></div>
		</div>
	</div>
	<div class="mod_primary">
		<c:set var="nav_2_path_content" scope="request">
			<ul>
				<li>
					<a class="home" href="http://<%=HkWebConfig.getWebDomain()%>"></a>
				</li>
				<li>
					<a class="nav-a" href="<%=path %>/cmp_pklist.do?parentId=${parentKind.kindId }&${url_add }">${parentKind.name }</a>
				</li>
				<li>
					<a class="nav-a" href="<%=path %>/cmp_klist.do?kindId=${companyKind.kindId }&${url_add }">${companyKind.name }</a>
				</li>
				<li>
					<a class="nav-a" href="<%=path %>/cmp.do?companyId=${companyId}">${company.name }</a>
				</li>
				<li>
					<a class="nav-a" href="<%=path %>/product_list.do?companyId=${companyId}">产品</a>
				</li>
				<c:if test="${cmpProductSort!=null}">
				<li>
					<a class="nav-a" href="<%=path %>/cmp.do?companyId=${companyId}&sortId=${cmpProductSort.sortId}">${cmpProductSort.name }</a>
				</li>
				</c:if>
			</ul>
		</c:set>
		<jsp:include page="../../inc/nav-2.jsp"></jsp:include>
		<div class="mod_primary_l">
			<c:set var="page_url" scope="request"><%=path %>/product_list.do?companyId=${companyId}&sortId=${sortId}</c:set>
			<jsp:include page="../../inc/productlist_inc.jsp"></jsp:include>
		</div>
		<div class="mod_primary_r">
		</div>
		<div class="clr"></div>
	</div>
	<div class="clr"></div>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>