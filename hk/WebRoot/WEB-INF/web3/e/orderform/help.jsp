<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.Hkcss2Util"%>
<%@page import="com.hk.bean.Company"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<jsp:include page="../../inc/pub_inc.jsp"></jsp:include>
<c:set var="html_title" scope="request"><hk:data key="view.company.product.notfound"/></c:set>
<c:set var="script_content" scope="request">
</c:set>
<c:set var="body_hk_content" scope="request">
	<div class="mod_left">
		<div class="mod">
			<div class="mod-5 simple_nav">
				<%=Hkcss2Util.rd_bg %>
				<div class="cont">
					<div class="pad">
						<ul class="userset">
							<li>
								<a class="n1" href="<%=path %>/e/op/orderform_productlist.do?companyId=${companyId }&oid=${oid}&return_url=${return_url}"><hk:data key="view.all"/></a>
							</li>
							<c:forEach var="k" items="${cmpproductsortlist}">
							<li>
								<a class="n1" href="<%=path %>/e/op/orderform_productlist.do?companyId=${companyId }&oid=${oid}&sortId=${k.sortId }&return_url=${return_url}">${k.name }<span>(${k.productCount })</span></a>
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
				<li><a class="home" href="http://<%=HkWebConfig.getWebDomain()%>"></a></li>
				<li><a class="nav-a" href="#">${company.name }</a></li>
				<li><a class="nav-a" href="<%=path %>/e/op/orderform_productlist.do?companyId=${companyId}&oid=${oid}&return_url=${return_url}">产品</a></li>
				<c:if test="${cmpProductSort!=null}">
				<li><a class="nav-a" href="<%=path %>/cmp.do?companyId=${companyId}&sortId=${cmpProductSort.sortId}&oid=${oid}&return_url=${return_url}">${cmpProductSort.name }</a></li>
				</c:if>
				<li><a class="nav-a" href="${denc_return_url}">返回</a></li>
			</ul>
		</c:set>
		<jsp:include page="../../inc/nav-2.jsp"></jsp:include>
		<div class="mod_primary_l">
			<div>
				<h3 class="title2">为什么搜不到</h3>
				<div class="text_14"><hk:data key="view.company.orderform.help"/></div>
			</div>
		</div>
		<div class="mod_primary_r"></div>
		<div class="clr"></div>
	</div>
	<div class="clr"></div>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>