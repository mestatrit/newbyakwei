<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${cmpProduct.name}</c:set>
<c:set var="css_value" scope="request"><link rel="stylesheet" type="text/css" href="<%=path%>/webst3/css/product.css" /></c:set>
<c:set var="body_hk_content" scope="request">
<style>
body{
background:#F0F0F0 ;
}
.usercmpreviewlist2 .review table tr td.review-body {
	width: 530px;
	padding-left: 5px
}
</style>
	<div class="mod">
		<div class="phototop">
			<div class="f_l">
				<span class="product-name">${cmpProduct.name }</span>
				<span class="product-price">${cmpProduct.money }</span>
				<c:if test="${cmpProduct.score>0}">
				<span class="product-star"><img src="img/stars/stars_a_${cmpProduct.starsLevel }.gif" /> </span>
				</c:if>
			</div>
			<div class="clr"></div>
		</div>
		<div class="mod-8 mod-9">
			<%=Hkcss2Util.rd_bg%>
			<div class="cont">
				<div class="product">
					<c:if test="${fn:length(photolist)>0}">
						<div class="smallcon">
							<div class="smallcon-inner">
								<div class="previous_button"></div>
								<div id="photo-slider" class="slider">
									<ul id="imgslider" class="imgslider"></ul>
								</div>
								<div class="next_button"></div>
								<div class="clr"></div>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty cmpProduct.headPath}">
						<div class="product-photo"><img id="bigimg" src="${cmpProduct.head800 }" /></div>
					</c:if>
					<div class="product-act">
						<ul>
							<li>
								<a href="#">点餐</a>
							</li>
							<li>
								<a href="#">吃过</a>
							</li>
							<li>
								<a href="#">想吃</a>
							</li>
						</ul>
					</div>
					<div class="clr"></div>
					<div class="product-act2">
						<a href="#">分享</a>
						<div class="act2-split"></div>
						<a href="#">收藏</a>
						<div class="act2-split"></div>
						<a href="#">其他</a>
						<div class="clr"></div>
					</div>
				</div>
				<div class="product-body2">
                    <div class="f_l l-body">
                        <div>
                            <h3 class="title">
                            喜欢这个菜的人</h3>
                            <div class="imglist-1">
                                <a class="imgref" href="#"><img src="img/h48.jpg"/></a><a class="imgref" href="#"><img src="img/h48.jpg"/></a><a class="imgref" href="#"><img src="img/h48.jpg"/></a><a class="imgref" href="#"><img src="img/h48.jpg"/></a><a class="imgref" href="#"><img src="img/h48.jpg"/></a><a class="imgref" href="#"><img src="img/h48.jpg"/></a>
                                <div class="clr">
                                </div>
                            </div>
                        </div>
                        <div>
                            <h3 class="title">
                            哪里还有xxx</h3>
                            <div class="simple_product">
                                <div class="image">
                                    <a href="#"><img src="img/h60.jpg"/></a>
                                </div>
                                <div class="content">
                                    <img src="img/stars/star40.gif"/>
                                    <br/>
                                    <a href="#">足迹名称</a>
                                </div>
                                <div class="clr">
                                </div>
                            </div>
                            <a class="more">更多</a>
                        </div>
                    </div>
                    <div class="f_r r-body">
                        <div>
                            <h3 class="title">点评</h3>
                            <ul class="usercmpreviewlist2">
                                <li class="review">
                                    <table cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td class="head">
                                                <a href="#"><img src="img/h48.jpg" />
                                                    <br/>
                                                    袁akwei
                                                </a>
                                            </td>
                                            <td class="review-body">
                                                <ul>
                                                    <li class="f_l">
                                                        <img src="img/stars/star50.gif" />
                                                    </li>
                                                    <li class="f_r ruo">
                                                        09-12-27
                                                    </li>
                                                </ul>
                                                <div class="clr">
                                                </div>
                                                更值得期待的是Firefox 4，估计要等到2010年年底才能推出。Firefox 4将采用最新的UI，每个tab的进程相互独立，安装扩展不重启，拥有更快的解析速度。
                                                更值得期待的是Firefox 4，估计要等到2010年年底才能推出。Firefox 4将采用最新的UI，每个tab的进程相互独立，安装扩展不重启，拥有更快的解析速度。
                                                更值得期待的是Firefox 4，估计要等到2010年年底才能推出。Firefox 4将采用最新的UI，每个tab的进程相互独立，安装扩展不重启，拥有更快的解析速度。
                                            </td>
                                        </tr>
                                    </table>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="clr">
                    </div>
                </div>
			</div>
			<%=Hkcss2Util.rd_bg_bottom%>
		</div>
	</div>
<script type="text/javascript" src="<%=path %>/webst3/js/easySlider1.7.js"></script>
<script type="text/javascript" src="<%=path %>/webst3/js/jquery.easing.1.1.js"></script>
<script type="text/javascript" src="<%=path %>/webst3/js/jcarousellite_1.0.1.min.js"></script>
<script type="text/javascript">
var img=new Array();
<c:forEach var="photo" items="${photolist}" varStatus="idx">
img[${idx.index }]=new Array(${photo.photoId },'${photo.pic60 }','${photo.pic800 }');
</c:forEach>
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
	getObj("bigimg").src=img[idx][2];
}
init_imgslider();
$("#photo-slider").jCarouselLite({
    btnNext: " .next_button",
    btnPrev: " .previous_button",
    visible: 11
});
function touploadphoto(){
	createBg();
	var html='<div><strong>图片文件大小不能超过1M，否则不能上传成功</strong> <hk:form oid="photofrm" onsubmit="return subphotofrm(this.id)" enctype="multipart/form-data" action="/op/uploadproductphoto_upload.do" target="hideframe"> <hk:hide name="companyId" value="${cmpProduct.companyId}"/> <hk:hide name="productId" value="${pid}"/> <strong>产品最多上传30张图片</strong><br/> <%for (int i = 0; i < 6;i++){%> <input type="file" size="50" name="f<%=i%>" class="fileipt"/><br/> <%}%> <hk:submit value="view.upload" res="true" clazz="btn"/> </hk:form> </div>';
	createCenterWindow("photo_win",500,370,'上传图片',html,"hideWindow('photo_win');clearBg();");
}
function subphotofrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function onuploadok(error,error_msg,op_func,obj_id_param,respValue){
	if(parseInt(respValue)>0){
		alert("有"+respValue+"张图片没有上传成功");
	}
	refreshurl();
}
function onuploaderror(error,error_msg,op_func,obj_id_param,respValue){
	if(error==<%=Err.CMPPRODUCTPHOTO_OUT_OF_LIMIT %>){
		alert("由于图片超过30张，只有"+respValue+"张上传成功");
	}
	refreshurl();
}
</script>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>