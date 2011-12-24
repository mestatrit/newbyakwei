<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.Hkcss2Util"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%String path = request.getContextPath();%>
<%JspDataUtil.loadCompanyScore(request);%>
<c:set var="html_title" scope="request">${cmpProduct.name}</c:set>
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
	padding-left: 20px;
}
.normal{
	font-weight: normal;
}
</style>
	<div class="mod">
		<div class="phototop">
			<div class="f_l">
				<span class="product-name">${cmpProduct.name }</span>
				<span class="product-price">${cmpProduct.money }</span>
				<c:if test="${cmpProduct.score!=0}">
					<span class="product-star"><img src="<%=path %>/webst3/img/stars/stars_a_${cmpProduct.starsLevel }.gif" /> </span>
				</c:if>
			</div>
			<div class="f_r text_16">
				返回 <a href="<%=path %>/cmp.do?companyId=${cmpProduct.companyId}">${company.name }</a>主页
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
								<c:if test="${fn:length(photolist)>11}"><div class="previous_button"></div></c:if>
								<div id="photo-slider" class="slider">
									<ul id="imgslider" class="imgslider"></ul>
								</div>
								<c:if test="${fn:length(photolist)>11}"><div class="next_button"></div></c:if>
								<div class="clr"></div>
							</div>
						</div>
					</c:if>
					<c:if test="${not empty cmpProduct.headPath}">
						<div id="imgarrow" class="product-photo" onclick="changeimg()">
							<img id="bigimg" src="${cmpProduct.head800 }"/>
							<div class="imgarrow"></div>
						</div>
					</c:if>
					<div class="product-act">
						<div id="carddiv" class="text_14" style="padding-left: 10px">
							<c:if test="${addtocard}">
								下单成功，<a href="<%=path%>/shoppingcard.do">马上确认</a>
							</c:if>
							<c:if test="${!addtocard}">
								<hk:button clazz="btn" value="下单" onclick="createtocard(${pid},0)"/>
							</c:if>
						</div>
						<ul>
							<c:if test="${userStatus!=null}">
								<c:if test="${!userStatus.done}">
									<li id="done_li">
										<hk:button value="view.product.done${company.parentKindId}" clazz="btn" onclick="setStatusDone()" res="true"/>
									</li>
									<li id="want_li">
										<hk:button value="view.product.want${company.parentKindId}" clazz="btn_disabled" res="true"/>
									</li>
								</c:if>
								<c:if test="${userStatus.done}">
									<li id="done_li">
										<hk:button value="view.product.done${company.parentKindId}" clazz="btn_disabled" res="true"/>
									</li>
									<li id="want_li">
										<hk:button value="view.product.want${company.parentKindId}" clazz="btn" onclick="setStatusWant()" res="true"/>
									</li>
								</c:if>
							</c:if>
							<c:if test="${userStatus==null}">
								<li id="done_li">
									<hk:button value="view.product.done${company.parentKindId}" clazz="btn" onclick="setStatusDone()" res="true"/>
								</li>
								<li id="want_li">
									<hk:button value="view.product.want${company.parentKindId}" clazz="btn" onclick="setStatusWant()" res="true"/>
								</li>
							</c:if>
						</ul>
						<h3 class="title">更多产品</h3>
						<div id="productlistdiv"></div>
					</div>
					<div class="clr"></div>
					<div class="product-act2">
						<a href="#">分享</a>
						<div class="act2-split"></div>
						<span id="favspan">
						<c:if test="${hasfav}"><a id="fav-act" href="javascript:delfav()">取消收藏</a></c:if>
						<c:if test="${!hasfav}"><a id="fav-act" href="javascript:fav()">收藏</a></c:if>
						</span>
						<c:if test="${(canuploadphoto && !canmgrcmp) || (canuploadphoto && canmgrcmp==null)}">
							<div class="act2-split"></div>
							<a href="javascript:touploadphoto()">上传图片</a>
						</c:if>
						<c:if test="${canmgrcmp}">
							<div class="act2-split"></div>
							<a href="<%=path%>/e/op/cmpproductphoto.do?companyId=${cmpProduct.companyId }&productId=${pid }">管理</a>
						</c:if>
						<div class="act2-split"></div>
						<a href="<%=path %>/cmp_pic.do?companyId=${cmpProduct.companyId}">足迹图片</a>
						<c:if test="${zoneAdminUser}">
						<div class="act2-split"></div>
						<a id="cmdproduct" href="javascript:createcmdproduct()">推荐此产品</a>
						</c:if>
						<div class="clr"></div>
					</div>
				</div>
				<div class="product-body2">
					<div class="f_l l-body">
						<c:if test="${fn:length(cmpProductUserScoreList)>0}">
						<div>
							<h3 class="title">喜欢这个产品的人</h3>
							<div class="imglist-1">
								<c:forEach  var="like_user" items="${cmpProductUserScoreList}">
								<a class="imgref" href="<%=path %>/home_web.do?userId=${like_user.userId }"><img src="${like_user.user.head48Pic }" title="${like_user.user.nickName }"/></a>
								</c:forEach>
								<div class="clr"></div>
							</div>
						</div>
						</c:if>
						<c:if test="${fn:length(tagreflist)>0}">
						<div>
							<h3 class="title">哪里还有${cmpProduct.name }</h3>
							<c:forEach var="tagref" items="${tagreflist}">
							<c:set var="cmp_url"><%=path %>/cmp.do?companyId=${tagref.companyId}</c:set>
							<div class="simple_product simple_product2">
								<div class="image"><a href="${cmp_url }"><img src="${tagref.company.head60 }" /></a></div>
								<div class="content content2">
									<c:if test="${tagref.company.totalScore>0}">
									<img src="<%=path %>/webst3/img/stars/star${tagref.company.starsLevel }.gif" /><br />
									</c:if>
									<a href="${cmp_url }">${tagref.company.name }</a>
								</div>
								<div class="clr"></div>
							</div>
							</c:forEach>
							<c:if test="${moretagref}">
								<a class="more" href="<%=path %>/product_cmp.do?pid=${pid}">更多</a>
							</c:if>
						</div>
						</c:if>
					</div>
					<div class="f_r r-body">
						<div>
							${cmpProduct.intro }
						</div>
						<div>
							<h3 class="title">点评</h3>
							<a name="review_anchor"></a>
							<c:set var="page_url" scope="request"><%=path %>/product.do?pid=${pid}</c:set>
							<jsp:include page="../../inc/cmpproductreviewlist_inc.jsp"></jsp:include>
							<div  id="createcmpproductreviewcon" onkeydown="keydown(event)">
							<c:set var="cmpproductreview_form_action" scope="request">/op/product_createreview.do</c:set>
							<jsp:include page="../../inc/cmpproductreviewform.jsp"></jsp:include>
							</div>
						</div>
					</div>
					<div class="clr"></div>
				</div>
			</div>
			<%=Hkcss2Util.rd_bg_bottom%>
		</div>
	</div>
<script type="text/javascript" src="<%=path%>/webst3/js/easySlider1.7.js"></script>
<script type="text/javascript" src="<%=path%>/webst3/js/jquery.easing.1.1.js"></script>
<script type="text/javascript" src="<%=path%>/webst3/js/jcarousellite_1.0.1.min.js"></script>
<script type="text/javascript">
var err_code_<%=Err.LABA_CONTENT_ERROR %>={objid:"review_content_2"};
var err_code_<%=Err.LABA_CONTENT_LEN_TOOLONG %>={objid:"review_content_2"};
var img=new Array();
var amount=0;
var currentIdx=0;
<c:forEach var="photo" items="${photolist}" varStatus="idx">
img[${idx.index }]=new Array(${photo.photoId },'${photo.pic60 }','${photo.pic800 }');
</c:forEach>
function showarrow(event){
	
}
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
function touploadphoto(){
	createBg();
	var html='<div><strong>图片文件大小不能超过1M，否则不能上传成功</strong> <hk:form oid="photofrm" onsubmit="return subphotofrm(this.id)" enctype="multipart/form-data" action="/op/uploadproductphoto_upload.do" target="hideframe"> <hk:hide name="companyId" value="${cmpProduct.companyId}"/> <hk:hide name="productId" value="${pid}"/> <strong>产品最多上传30张图片</strong><br/> <%for (int i = 0; i < 6; i++) {%> <input type="file" size="50" name="f<%=i%>" class="fileipt"/><br/> <%}%> <hk:submit value="view.upload" res="true" clazz="btn"/> </hk:form> </div>';
	createCenterWindow("photo_win",500,400,'上传图片',html,"hideWindow('photo_win');clearBg();");
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
	if(error==<%=Err.CMPPRODUCTPHOTO_OUT_OF_LIMIT%>){
		alert("由于图片超过30张，只有"+respValue+"张上传成功");
	}
	refreshurl();
}
function fav(){
	if(!user_login){
		alert("请先登录");
		return;
	}
	showSubmitDivForObj("fav-act");
	$.ajax({
		type:"POST",
		url:'<%=path %>/op/product_fav.do?pid=${pid}',
		cache:false,
    	dataType:"html",
		success:function(data){
			hideSubmitDiv("fav-act");
			getObj('fav-act').href="javascript:void(0)";
			getObj('fav-act').onclick="";
			setHtml('fav-act','收藏成功');
			getObj('fav-act').style.cssText="cursor: text;";
		}
	})
}
function delfav(){
	if(!user_login){
		alert("请先登录");
		return;
	}
	showSubmitDivForObj("fav-act");
	$.ajax({
		type:"POST",
		url:'<%=path %>/op/product_delfav.do?pid=${pid}',
		cache:false,
    	dataType:"html",
		success:function(data){
			hideSubmitDiv("fav-act");
			getObj('fav-act').href="javascript:void(0)";
			getObj('fav-act').onclick="";
			setHtml('fav-act','取消收藏成功');
			getObj('fav-act').style.cssText="cursor: text;";
		}
	})
}
function setStatusDone(){
	if(!user_login){
		alert("请先登录");
		return;
	}
	setHtml("done_li","操作提交中 ... ...");
	$.ajax({
		type:"POST",
		url:'<%=path %>/op/product_done.do?pid=${pid}',
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('done_li','<hk:button value="view.product.done${company.parentKindId}" clazz="btn_disabled" res="true"/>');
			setHtml('want_li','<hk:button value="view.product.want${company.parentKindId}" clazz="btn" onclick="setStatusWant()" res="true"/>');
			if(data=='0'){//如果没有评论，提示写评论
				//window.location.hash="review_anchor";
				createBg();
				var html='<div onkeydown="keydownproductreviewfrm2(event)"><hk:form oid="review_frm_2" onsubmit="return subproductreviewfrm2(this.id)" action="/op/product_createreview.do" target="hideframe"><hk:hide name="optflg" value="1"/><hk:hide name="productId" value="${pid}"/> <hk:hide name="labaId" value="${labaId}"/> <table class="infotable" cellpadding="0" cellspacing="0" width="600px"> <tr> <td> <div class="f_l" style="width: 500px;"> 打分 <hk:select oid="id_score_2" name="score"> <hk:option value="0" data="view.notsetscore" res="true"/> <c:forEach var="conf" items="${companyScoreConfigList}"> <hk:option value="${conf.score}" data="company.score_${conf.score}" res="true"/> </c:forEach> </hk:select><span id="review_score_2_error" class="error"></span><br/> </div> </td> </tr> <tr> <td> <textarea name="content" style="width:500px;height:100px"></textarea><br/> <div id="review_content_2_error" class="error"></div> </td> </tr> <tr> <td align="center"> <hk:submit value="写新评论" clazz="btn"/> </td> </tr> </table> </hk:form></div>';
				createCenterWindow("review_win_2",600,300,"写下你的点评",html,"hideWindow('review_win_2');clearBg();");
			}
		}
	})
}
function setStatusWant(){
	if(!user_login){
		alert("请先登录");
		return;
	}
	setHtml("want_li","操作提交中 ... ...");
	$.ajax({
		type:"POST",
		url:'<%=path %>/op/product_want.do?pid=${pid}',
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('want_li','<hk:button value="view.product.want${company.parentKindId}" clazz="btn_disabled" res="true"/>');
			setHtml('done_li','<hk:button value="view.product.done${company.parentKindId}" clazz="btn" onclick="setStatusDone()" res="true"/>');
		}
	})
}
function subproductreviewfrm2(frmid){
	validateClear("review_content_2");
	validateClear("review_score_2");
	showSubmitDiv(frmid);
	return true;
}
function keydownproductreviewfrm2(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(subproductreviewfrm2('review_frm_2')){
			getObj("review_frm_2").submit();
		}
	}
}
function opcreatereview2error(error,error_msg,respValue){
	validateErr(getoidparam(error), error_msg);
	hideSubmitDiv();
}
function opcreatereview2success(error,error_msg,respValue){
	refreshurl();
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
function loadproductforpage(page){
	setHtml('productlistdiv','正在加载产品数据 ...');
	$.ajax({
		type:"POST",
		url:'<%=path %>/product_listforpage.do?pid=${pid}&companyId=${cmpProduct.companyId}&sortId=${sortId}&page='+page,
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('productlistdiv',data);
		}
	})
}
function createtocard(pid,chgflg){
	setHtml('carddiv','下单中 ...');
	$.ajax({
		type:"POST",
		url:'<%=path %>/product_addtocard.do?pid='+pid+"&chgflg="+chgflg,
		cache:false,
    	dataType:"html",
		success:function(data){
			if(data==-1){
				if(window.confirm("您预订的产品与购物车中的产品不在同一个足迹中，是否要更换足迹？")){
					createtocard(pid,1);
				}
				else{
					setHtml('carddiv','<a class="text_16" href="javascript:createtocard('+pid+')"><hk:data key="view.product.addtocard"/></a>');
				}
			}
			else{
				setHtml('carddiv','下单成功，<a href="<%=path%>/shoppingcard.do">马上确认</a>');
			}
		}
	});
}
function createcmdproduct(){
	showSubmitDivForObj('cmdproduct');
	$.ajax({
		type:"POST",
		url:'<%=path %>/op/cmd/cmd_createcmdproduct.do?pid=${pid}',
		cache:false,
    	dataType:"html",
		success:function(data){
			hideSubmitDiv();
			getObj('cmdproduct').href="javascript:void(0)";
			setHtml('cmdproduct','推荐成功');
		}
	});
}
initarrow(0);
loadproductforpage(1);
</script>
</c:set>
<jsp:include page="../../inc/frame.jsp"></jsp:include>