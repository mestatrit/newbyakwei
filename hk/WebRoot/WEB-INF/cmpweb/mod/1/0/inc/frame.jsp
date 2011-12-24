<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.hk.bean.CmpInfo"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%><!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%EppViewUtil.loadCmpProductSortList(request); %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<link rel="stylesheet" type="text/css" href="<%=path %>/cmpwebst4/mod/1/${cmpInfo.tmlflg }/css/a.css" />
		<script type="text/javascript" language="javascript" src="<%=path %>/cmpwebst4/mod/pub/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" language="javascript" src="<%=path %>/cmpwebst4/mod/pub/js/pub.js"></script>
		<script type="text/javascript" src="<%=path %>/cmpwebst4/mod/1/0/js/site_nav.js"></script>
		<script type="text/JavaScript" src="<%=path %>/cmpwebst4/mod/1/0/js/org.js"></script>
		<script type="text/JavaScript" src="<%=path %>/cmpwebst4/mod/1/0/js/org_002.js"></script>
		<c:if test="${epp_other_value!=null}">${epp_other_value}</c:if>
		<title>${o.name }</title>
	</head>
	<body>
		<div class="hk">
			<br class="linefix" />
			<div class="userop">
				<c:if test="${sys_cmpadminuser}"><a href="<%=path %>/epp/web/op/webadmin/admincmpnav.do?companyId=${companyId}">网站管理</a></c:if>
			</div>
			<div class="top">
				<div class="topleft">
					<c:if test="${not empty o.logopath}">
					<div class="logo">
						<a href="http://<%=request.getServerName() %>"><img src="${o.logoPic }" /></a>
					</div>
					</c:if>
					<div class="clr"></div>
					<div class="site-nav" style="margin-top: 10px;">
						<div id="floor_nav">
							<ul class="floors">
								<li class="navli" id="floor_0">
									<a name="t-label-h" href="http://<%=request.getServerName() %>"><span class="rd_l"></span><span class="rd_mid"><hk:data key="epp.home"/></span><span class="rd_r"></span></a>
								</li>
								<c:forEach var="o_1_sort" items="${l_1_list}">
									<li class="navli" id="floor_${o_1_sort.sortId }">
									<c:if test="${o_1_sort.hasChildren}">
										<a href="javascript:void(0)" class="track"><span class="rd_l"></span><span class="rd_mid">${o_1_sort.name }</span><span class="rd_r"></span></a>
									</c:if>
									<c:if test="${!o_1_sort.hasChildren}">
										<a href="<%=path %>/epp/web/product.do?companyId=${companyId}&sortId=${o_1_sort.sortId}" class="track"><span class="rd_l"></span><span class="rd_mid">${o_1_sort.name }</span><span class="rd_r"></span></a>
									</c:if>
									</li>
								</c:forEach>
							</ul>
						</div>
						<div class="sub-floor-menus" id="sub_floor_menus">
							<c:forEach var="o_1_sort" items="${l_1_list}">
								<c:if test="${o_1_sort.hasChildren}">
									<div class="menu" id="sub_floor_${o_1_sort.sortId }">
										<div class="dht">
											<c:forEach var="o_2_sort" items="${l_2_list}">
												<c:if test="${o_2_sort.parentId == o_1_sort.sortId}">
													<c:if test="${!o_2_sort.hasChildren}">
														<a href="<%=path %>/epp/web/product.do?companyId=${companyId}&sortId=${o_2_sort.sortId}">${o_2_sort.name }</a>
													</c:if>
													<c:if test="${o_2_sort.hasChildren}">
														<p><strong>${o_2_sort.name }</strong></p>
														<c:forEach var="o_3_sort" items="${l_3_list}">
															<c:if test="${o_3_sort.parentId==o_2_sort.sortId}">
																<a href="<%=path %>/epp/web/product.do?companyId=${companyId}&sortId=${o_3_sort.sortId}">${o_3_sort.name }</a>
															</c:if>
														</c:forEach>
													</c:if>
												</c:if>
											</c:forEach>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="topright"></div>
				<div class="clr"></div>
			</div>
			<div class="mod">
				<div class="fl" style="width: 820px; height: 60px;">
					<div class="hotkind">
						热门分类：
						<a href="#" class="split-r">分类</a>
						<a href="#" class="split-r">分类</a>
						<a href="#" class="split-r">分类</a>
					</div>
					<div class="search">
						<div class="inner">
							<form method="get" action="<%=path %>/epp/web/product_searchname.do">
								<hk:hide name="companyId" value="${companyId}"/>
								<input type="text" name="key" value="请输入关键词" class="text" onfocus="search_onfucus(this)" onblur="search_onblur(this)"/>
								<hk:submit value="epp.search" res="true"/>
							</form>
						</div>
					</div>
				</div>
				<!--
				<div class="shopcard">
					购物车中有件
					<span class="imp">1</span> 商品 合 计
					<span class="imp">29</span> 元
				</div> 
				 -->
				<div class="clr"></div>
			</div>
			${html_body_content }
			<%Calendar now=Calendar.getInstance();
				CmpInfo cmpInfo=(CmpInfo)request.getAttribute("cmpInfo");
				if(cmpInfo!=null){
					String domain=cmpInfo.getDomain();
					int idx=domain.indexOf('.');
					String info_tmp_domain=domain.substring(0,idx);
					request.setAttribute("info_tmp_domain",info_tmp_domain);
				}
				%>
			<div class="foot">* Copyright ® <%=now.get(Calendar.YEAR) %> ${info_tmp_domain } All rights reserved</div>
		</div>
<script>
function search_onfucus(o){
	if(o.value=='请输入关键词'){
		o.value='';
	}
}
function search_onblur(o){
	if(o.value==''){
		o.value='请输入关键词';
	}
}
</script>
	</body>
</html>