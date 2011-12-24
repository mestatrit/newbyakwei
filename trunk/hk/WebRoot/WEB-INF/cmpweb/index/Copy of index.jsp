<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="html_body_content" scope="request">
	<div class="idx_l">
		<c:if test="${fn:length(cmpcontactlist)>0}">
			<div class="mod bg1 rd">
				<div class="divrow">
					<c:forEach var="contact" items="${cmpcontactlist}">
						<div class="divrow">
							${contact.qqhtml }
						</div>
					</c:forEach>
				</div>
			</div>
		</c:if>
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
	</div>
	<div class="idx_r">
		<div class="inner2">
			<div class="inner_l">
				<c:if test="${fn:length(cmpproductlist)>0}">
					<div class="innermod top product_area_bd rd" style="border: none;border: 0;">
						<div id="idx_main_product" class="idx_main_product">
							<c:forEach var="product" items="${cmpproductlist}" varStatus="idx">
								<div id="p${idx.index+1 }" class="idx_product">
									<div class="pimg">
											<c:if test="${not empty product.headPath}">
												<a href="/product/${companyId}/${product_cmpnavid }/0/${product.productId }.html"><img src="${product.head240 }"/></a>
											</c:if>
									</div>
									<div class="pcontent">
										<div class="name">
											<a href="/product/${companyId}/${product_cmpnavid }/0/${product.productId }.html">${product.name }</a>
										</div>
										${product.intro }
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:if>
				<c:if test="${home_cmparticle!=null}">
					<div class="innermod">
						<div class="">
							<h2 class="home_mod_title"><a href="/article/${companyId}/${home_cmparticle.cmpNavOid}/${home_cmparticle.oid}.html">${home_cmparticle.title }</a></h2>
							<c:if test="${topcmpfile!=null}">
								<div style="text-align: center; padding-top: 10px;">
									<a href="/article/${companyId}/${home_cmparticle.cmpNavOid}/${home_cmparticle.oid}.html"><img src="${topcmpfile.cmpFilePic600 }" width="385px"/></a>
								</div>
							</c:if>
						</div>
					</div>
				</c:if>
				<c:forEach var="index_right_nav" items="${middle_navlist}">
					<c:set var="right_nav" value="${index_right_nav}" scope="request"/>
					<jsp:include page="index_middle_${index_right_nav.reffunc}.jsp"></jsp:include>
				</c:forEach>
			</div>
			<div class="inner_r">
				<c:forEach var="index_right_nav" items="${right_navlist}">
					<c:set var="right_nav" value="${index_right_nav}" scope="request"/>
					<jsp:include page="index_right_${index_right_nav.reffunc}.jsp"></jsp:include>
				</c:forEach>
			</div>
			<div class="clr"></div>
		</div>
	</div>
	<div class="clr"></div>
<script type="text/javascript">
var delay = 5000;
var begin=1;
var end=${fn:length(cmpproductlist)};
var current_i=1;
function show(i){
	getObj('p'+current_i).style.cssText="display:none";
	getObj('p'+i).style.cssText="display:block";
	current_i=i;
}
var i=1;
<c:if test="${fn:length(cmpproductlist)>0}">
$(document).ready(function() {
	setInterval(function(){
	if(i>end){
		i=begin;
	}
	show(i);
	i++;
},5000);
});
</c:if>
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>