<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.Hkcss2Util"%>
<%@page import="com.hk.bean.CompanyUserStatus"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${company.name}</c:set>
<c:set var="css_value" scope="request"><link rel="stylesheet" type="text/css" href="<%=path%>/webst3/css/product.css" /></c:set>
<c:set var="body_hk_content" scope="request">
<style>
body {
	background: #F0F0F0;
}
.right-arrow{
	cursor: url(http://<%=HkWebConfig.getWebDomain() %>/webst3/img/right.cur), default;
}
.left-arrow{
	cursor: url(http://<%=HkWebConfig.getWebDomain() %>/webst3/img/left.cur), default;
}
.usercmpreviewlist2 .review table tr td.review-body {
	width: 530px;
	padding-left: 5px;
}
</style>
	<div class="mod">
		<div class="phototop">
			<div class="f_l">
			</div>
			<div class="f_r text_16">
				<a href="<%=path %>/cmp.do?companyId=${companyId}">${company.name }</a>
			</div>
			<div class="clr"></div>
		</div>
		<div class="mod-8 mod-9">
			<%=Hkcss2Util.rd_bg%>
			<div class="cont">
				<div class="product">
					<c:if test="${fn:length(list)>0}">
						<div class="smallcon">
							<div class="smallcon-inner">
								<c:if test="${fn:length(list)>11}"><div class="previous_button"></div></c:if>
								<div id="photo-slider" class="slider">
									<ul id="imgslider" class="imgslider"></ul>
								</div>
								<c:if test="${fn:length(list)>11}"><div class="next_button"></div></c:if>
								<div class="clr"></div>
							</div>
						</div>
					</c:if>
					<div id="imgarrow" class="product-photo" onclick="changeimg()">
						
						<div id="imgbg" class="imgarrow"></div>
					</div>
					<div class="product-act">
						<ul>
						<li id="status_done">
							<c:if test="${companyUserStatus.done}">
								<hk:data key="view.companyuserstatus.userdone"/>
							</c:if>
							<c:if test="${!companyUserStatus.done}">
								<hk:button onclick="" clazz="btn" value="view.companyuserstatus.userdone" res="true"/>
							</c:if>
						</li>
						<li id="status_want">
						<c:if test="${companyUserStatus.done}">
							<hk:button onclick="setstatus_want()" clazz="btn" value="view.companyuserstatus.userwant" res="true"/>
						</c:if>
						<c:if test="${!companyUserStatus.done}">
							<hk:data key="view.companyuserstatus.userwant"/>
						</c:if>
						</li>
						</ul>
						<c:if test="${fn:length(cmpproductlist)>0}">
							<h3 class="title">产品</h3>
							<ul class="product-list2">
								<c:forEach var="product" items="${cmpproductlist}">
								<li class="hasimg"><a href="<%=path %>/product.do?pid=${product.productId}">
								<img src="${product.head60 }"/>
								${product.name }</a></li>
								</c:forEach>
							</ul>
							<div><a class="more" href="<%=path %>/product_list.do?companyId=${companyId}"><hk:data key="view.more"/></a></div>
						</c:if>
					</div>
					<div class="clr"></div>
					<div class="product-act2">
						<a href="#">分享</a>
						<div class="act2-split"></div>
						<span id="favspan">
						<c:if test="${hasfav}"><a id="fav-act" href="javascript:delfav()">取消收藏</a></c:if>
						<c:if test="${!hasfav}"><a id="fav-act" href="javascript:fav()">收藏</a></c:if>
						</span>
						<c:if test="${!caneditcompany && caneiteimg}">
							<div class="act2-split"></div>
							<a href="#">上传图片</a>
						</c:if>
						<c:if test="${caneditcompany}">
							<div class="act2-split"></div>
							<a href="<%=path%>/e/op/op.do?companyId=${companyId }">管理</a>
						</c:if>
						<div class="clr"></div>
					</div>
				</div>
			</div>
			<%=Hkcss2Util.rd_bg_bottom%>
		</div>
	</div>
<script type="text/javascript" src="<%=path%>/webst3/js/easySlider1.7.js"></script>
<script type="text/javascript" src="<%=path%>/webst3/js/jquery.easing.1.1.js"></script>
<script type="text/javascript" src="<%=path%>/webst3/js/jcarousellite_1.0.1.min.js"></script>
<script type="text/javascript">
var img=new Array();
var amount=0;
var currentIdx=0;
<c:forEach var="photo" items="${list}" varStatus="idx">
img[${idx.index }]=new Array(${photo.photoId },'${photo.pic60 }','${photo.pic800 }');
</c:forEach>
function changeimg(){
	changebig(currentIdx+amount);
}
function init_imgslider(){
	var s="";
	for(var i=0;i<img.length;i++){
		s+='<li id="li_'+i+'" class="smallphoto"><a href="javascript:void(0)" onclick="changebig('+i+')"><img src="'+img[i][1]+'"/></a></li>';
	}
	if(img.length>0){
		setHtml("imgslider",s);
	}
}
function changebig(idx){
	if(idx<0){
		idx=img.length-1;
	}
	if(idx>img.length-1){
		idx=0;
	}
	var image=new Image();
	image.src=img[idx][2];
	getObj("bigimg").src=img[idx][2];
	currentIdx=idx;
}
init_imgslider();
insertObjBefore('<img id="bigimg" src="'+img[0][2]+'"/>','imgbg');
if(img.length>11){
	$("#photo-slider").jCarouselLite({
	    btnNext: " .next_button",
	    btnPrev: " .previous_button",
	    visible: 11
	});
}
else {
	$("#photo-slider").jCarouselLite({
	    visible: img.length
	});
}
function initarrow(idx){
	$("#imgarrow").bind("mousemove", function(e){
		if(e.pageX<$("#imgarrow").offset().left+400){
			amount=-1;
			if(isIE){
				getObj("imgarrow").className="product-photo left-arrow";
			}
			else{
				getObj("imgarrow").style.cssText='cursor:url(http://<%=HkWebConfig.getWebDomain() %>/webst3/img/left.cur),auto;';
			}
		}
		else{
			amount=1;
			if(isIE){
				getObj("imgarrow").className="product-photo right-arrow";
			}
			else{
				getObj("imgarrow").style.cssText='cursor:url(http://<%=HkWebConfig.getWebDomain() %>/webst3/img/right.cur),auto;';
			}
		}
	}); 
}
initarrow(0);
function setstatus_done(){
	if(!user_login){
		alert("请先登录");
		return;
	}
	setHtml('status_done','数据提交中 ...');
	$.ajax({
		type:"POST",
		url:'<%=request.getContextPath() %>/cmpuserstatus/op/op_web.do',
		data:"status=<%=CompanyUserStatus.USERSTATUS_DONE %>&companyId=${companyId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('status_done','<hk:data key="view.companyuserstatus.userdone"/>');
			setHtml('status_want','<hk:button onclick="setstatus_want()" clazz="btn" value="view.companyuserstatus.userwant" res="true"/>');
		}
	});
}
function setstatus_want(){
	if(!user_login){
		alert("请先登录");
		return;
	}
	setHtml('status_want','数据提交中 ...');
	$.ajax({
		type:"POST",
		url:'<%=request.getContextPath() %>/cmpuserstatus/op/op_web.do',
		data:"status=<%=CompanyUserStatus.USERSTATUS_WANT %>&companyId=${companyId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('status_want','<hk:data key="view.companyuserstatus.userwant"/>');
			setHtml('status_done','<hk:button onclick="setstatus_done()" clazz="btn" value="view.companyuserstatus.userdone" res="true"/>');
		}
	});
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>