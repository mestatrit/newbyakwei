<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="epp_other_value" scope="request">
<script type="text/javascript">
var product_area_arr=new Array();
var pimg=new Array();
<c:forEach var="product" items="${cmpproductlist}" varStatus="idx">
pimg[${idx.index}]=new Image();
pimg[${idx.index}].src='${product.head240 }';
</c:forEach>
<c:forEach var="product" items="${cmpproductlist}" varStatus="idx">
product_area_arr[${idx.index }]='<div id="p${idx.index+1 }" class="idx_product"><div class="pimg"><c:if test="${not empty product.headPath}"><a href="/product/${companyId}/${product_cmpnavid }/0/${product.productId }.html"><img src="'+pimg[${idx.index}].src+'"/></a></c:if></div><div id="id_pcontent" class="pcontent"><div class="name"><a href="/product/${companyId}/${product_cmpnavid }/0/${product.productId }.html">${product.name }</a></div>${product.simpleIntro }</div><div class="clr"></div></div>';
</c:forEach>
var delay = 5000;
var begin=0;
var end=${fn:length(cmpproductlist)-1};
function show(i){
	var h=$('#idx_main_product').height();
	$('#idx_main_product').css('height',h+'px');
	setHtml('idx_main_product',product_area_arr[i]);
	$('#idx_main_product').css('height','auto');
}
var i=1;
</script>
</c:set>
<c:set var="html_body_content" scope="request">
	<div class="idx_l">
		<c:if test="${fn:length(cmpcontactlist)>0}">
			<div class="mod bg1 rd" style="padding-left: 5px;">
				<c:forEach var="contact" items="${cmpcontactlist}">
				<div class="con_qq">${contact.qqhtml }</div>
				</c:forEach>
				<div class="clr"></div>
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
						</div>
						<script type="text/javascript">
						show(0);
						</script>
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