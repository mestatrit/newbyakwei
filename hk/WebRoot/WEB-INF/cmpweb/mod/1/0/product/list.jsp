<%@ page language="java" pageEncoding="UTF-8"%><%@page import="web.pub.util.EppViewUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set scope="request" var="html_body_content">
	<div class="pl"><%EppViewUtil.loadCmpProductSortList(request); %>
		<c:if test="${fn:length(l_1_list)>0}">
			<div class="leftnav">
				<div class="tit"><hk:data key="epp.product.sort"/></div>
				<div class="con">
					<c:set var="l_1_listendidx" value="${fn:length(l_1_list)}"/>
					<c:forEach var="o_1_sort" items="${l_1_list}" varStatus="idx">
						<div class="inner<c:if test="${idx.index==l_1_listendidx}"> end</c:if>">
							<ul class="nav_1">
								<li>
									<c:if test="${!o_1_sort.hasChildren}">
										<a href="<%=path %>/epp/web/product.do?companyId=${companyId }&sortId=${o_1_sort.sortId}"<c:if test="${sortId==o_1_sort.sortId}"> class="sel"</c:if>>${o_1_sort.name }</a>
									</c:if>
									<c:if test="${o_1_sort.hasChildren}">
										<c:set var="current_sortId" value="${o_1_sort.sortId}" scope="request"/><%EppViewUtil.loadCurrentSort(request); %>
										<span<c:if test="${is_current_sort}"> class="sel"</c:if>>${o_1_sort.name }</span>
										<%request.removeAttribute("current_sortId");request.removeAttribute("is_current_sort");%>
										<ul class="nav_2">
											<c:forEach var="o_2_sort" items="${l_2_list}">
												<c:if test="${o_2_sort.parentId==o_1_sort.sortId}">
													<li>
														<c:if test="${!o_2_sort.hasChildren}"><a href="<%=path %>/epp/web/product.do?companyId=${companyId }&sortId=${o_2_sort.sortId}"<c:if test="${sortId==o_2_sort.sortId}"> class="sel"</c:if>>${o_2_sort.name }</a></c:if>
														<c:if test="${o_2_sort.hasChildren}">
															<c:set var="current_sortId" value="${o_2_sort.sortId}" scope="request"/><%EppViewUtil.loadCurrentSort(request); %>
															<span<c:if test="${is_current_sort}"> class="sel"</c:if>>${o_2_sort.name }</span>
															<%request.removeAttribute("current_sortId");request.removeAttribute("is_current_sort");%>
															<ul class="nav_3">
																<c:forEach var="o_3_sort" items="${l_3_list}">
																	<c:if test="${o_3_sort.parentId==o_2_sort.sortId}">
																		<li><a href="<%=path %>/epp/web/product.do?companyId=${companyId }&sortId=${o_3_sort.sortId}"<c:if test="${sortId==o_3_sort.sortId}"> class="sel"</c:if>>${o_3_sort.name }</a></li>
																	</c:if>
																</c:forEach>
															</ul>
														</c:if>
													</li>
												</c:if>
											</c:forEach>
										</ul>
									</c:if>
								</li>
							</ul>
						</div>
					</c:forEach>
				</div>
			</div>
		</c:if>
	</div>
	<div class="pr">
		<div class="mod">
			<div class="mainnav">
				<a href="<%=request.getServerName() %>"><hk:data key="epp.home"/></a>
				<c:forEach var="p" items="${parentlist}" varStatus="idx">
					 &gt; ${p.name }
				</c:forEach>
				 &gt; <a href="<%=path %>/epp/web/product.do?companyId=${companyId}&sortId=${sortId}">${cmpProductSort.name }</a>
			</div>
		</div>
		<div class="mod">
			<c:if test="${fn:length(list)==0}">
			<hk:data key="epp.productlist.nodata"/>
			</c:if>
			<c:if test="${fn:length(list)>0}">
				<div id="_plist" class="plist nomgtop">
					<div style="padding-left: 30px;">
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<ul>
										<c:forEach var="product" items="${list}" varStatus="idx">
											<li><c:set var="product_url"><%=path %>/epp/web/product_view.do?companyId=${companyId}&productId=${product.productId}</c:set>
												<c:if test="${empty product.headPath}"><a href="${product_url }"><span class="nopic">暂无图片</span></a></c:if>
												<c:if test="${not empty product.headPath}">
													<div id="p${idx.index }_pdetail" class="pdetail">
														<div id="p${idx.index }_jt" class="jt"></div>
														<img class="big" src="${product.head320 }" />
													</div>
													<div class="imgbox"><a href="${product_url }"><img id="p${idx.index }" class="ps" src="${product.head120 }" title="${product.name }" alt="${product.name }"/></a></div>
												</c:if>
												<p class="n"><a href="${product_url }">${product.name }</a></p> 
												<p class="n"><hk:data key="epp.product.price"/>￥${product.money }</p>
											</li>
										</c:forEach>
									</ul>
									<div class="clr"></div>
								</td>
							</tr>
						</table>
						<div class="fr">
							<c:set var="page_url" scope="request"><%=path%>/epp/web/product.do?companyId=${companyId}&sortId=${sortId}</c:set>
							<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
							<div class="clr"></div>
						</div>
					</div>
				</div>
				<script type="text/javascript">
					$(document).ready(function() {
						$(".ps").bind("mouseenter", function() {
							var li_obj = $('#' + this.id + '_pdetail').parent();
							li_obj.css('position', 'relative');
							makepdetailpos(this, li_obj);
							makejtpos(this, li_obj);
						}).bind('mouseleave', function() {
							$('#' + this.id + '_pdetail').css('display', 'none');
							$('#' + this.id + '_pdetail').parent().css('position', 'static');
						});
				
					});
					function makejtpos(obj, li_obj) {
						var max_right = document.documentElement.clientWidth;
						var max_height = document.documentElement.clientHeight;
						var li_obj_right = li_obj.offset().left + li_obj.width();
						var jt_obj = $('#' + obj.id + '_jt');
						var left = 0;
						var top = 0;
						if (li_obj_right + 360 < max_right) {
							jt_obj.css("background-position", "0 0");
							left = -12;
						} else {
							jt_obj.css("background-position", "0 -29px");
							left = 339;
						}
						if (li_obj.offset().top + 350 - document.documentElement.scrollTop <= max_height) {
							top = 50;
						} else {
							top = (li_obj.offset().top + 350 - max_height)
									- document.documentElement.scrollTop + 50;
						}
						jt_obj.css('top', top + 'px');
						jt_obj.css('left', left + 'px');
					}
				
					function makepdetailpos(obj, li_obj) {
						var max_right = document.documentElement.clientWidth;
						var max_height = document.documentElement.clientHeight;
						var li_obj_right = li_obj.offset().left + li_obj.width();
						var pdetail = $('#' + obj.id + '_pdetail');
						if (li_obj_right + 360 < max_right) {
							pdetail.css('left', '150px');
						} else {
							pdetail.css('left', '-350px');
						}
						if (li_obj.offset().top + 350 - document.documentElement.scrollTop <= max_height) {
							pdetail.css('top', '0');
						} else {
							var top = -(li_obj.offset().top + 350 - max_height)
									+ document.documentElement.scrollTop;
							pdetail.css('top', top + 'px');
						}
						pdetail.css('display', 'block');
					}
				</script>
			</c:if>
		</div>
	</div>
	<div class="clr"></div>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>