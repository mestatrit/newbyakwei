<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.bean.CompanyUserStatus"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="USERSTATUS_DONE"><%=CompanyUserStatus.USERSTATUS_DONE %></c:set>
<c:set var="USERSTATUS_WANT"><%=CompanyUserStatus.USERSTATUS_WANT %></c:set>
<c:set var="html_title" scope="request">${vo.company.name}</c:set>
<c:set var="css_value" scope="request"><link rel="stylesheet" type="text/css" href="<%=path%>/webst3/css/company.css" /></c:set>
<c:set var="meta_value" scope="request">
<meta name="keywords" content="${vo.company.name},${companyKind.name },${parentKind.name },火酷网,足迹"/>
<meta name="description" content="${simple_descr }"/>
</c:set>
<c:set var="body_hk_content" scope="request">
	<jsp:include page="inc/cmp_left_inc.jsp"></jsp:include>
	<div class="mod_primary">
		<c:set var="nav_2_path_content" scope="request">
			<ul>
				<li>
					<a class="home" href="http://<%=HkWebConfig.getWebDomain()%>"></a>
				</li>
				<li>
					<a class="nav-a" href="<%=path %>/cmp_pklist.do?parentId=${parentId }&${url_add }">${parentKind.name }</a>
				</li>
				<li>
					<a class="nav-a" href="<%=path %>/cmp_klist.do?kindId=${kindId }&${url_add }">${companyKind.name }</a>
				</li>
				<li>
					<a class="nav-a" href="<%=path %>/cmp.do?companyId=${companyId }&kindId=${kindId }&${url_add }">${vo.company.name }</a>
				</li>
			</ul>
		</c:set>
		<jsp:include page="../inc/nav-2.jsp"></jsp:include>
		<div class="cmpinfo">
			<div class="company_con">
				<div class="nav-2 nav-2-tit">
					<div class="subnav subnav-tit">
						<div class="l"></div>
						<div class="mid">
							<div class="f_l nobd">
								<br />
								${vo.company.name } 
								<c:if test="${zoneAdminUser}">
								<a id="cmd" class="text_14" href="javascript:createcmdcmp()">推荐此足迹</a>
								</c:if>
								<br />
								<br />
							</div>
							<div class="f_r stars">
								<img src="<%=path%>/webst3/img/stars/stars_a_${vo.company.starsLevel }.gif" />
							</div>
						</div>
						<div class="r"></div>
						<div class="clr"></div>
					</div>
					<div class="clr"></div>
				</div>
				<div class="maincmp">
					<br class="linefix"/>
					<table class="company" cellpadding="0" cellspacing="0">
						<tr>
							<td class="cmp_l">
								<div class="mainpic">
									<table class="maintable" cellpadding="0" cellspacing="0">
										<tr>
											<td class="pic">
												<div class="imgcon">
													<a id="bigimghref" class="img" href="<%=path%>/cmp_pic.do?companyId=${companyId }"><img id="bigimg" src="${vo.company.head240 }" /></a>
												</div>
												<div class="action">
													<a class="share">分享</a>
													<div class="split"></div>
													<a class="fav2">收藏</a>
													<div class="split"></div>
													<c:if test="${caneditcompany }">
														<a class="act" href="<%=path%>/e/op/op.do?companyId=${companyId }">管理</a>
													</c:if>
													<c:if test="${!caneditcompany && caneiteimg }">
														<a class="act" href="<%=path%>/op/uploadcmpphoto.do?companyId=${companyId }">上传图片</a>
													</c:if>
													<div class="clr"></div>
												</div>
												<c:if test="${fn:length(photolist)>0 }">
													<div class="smallimgcon">
														<div class="smallimg default">
															<div class="previous_button"></div>
															<div id="cmpslider" class="slider">
																<ul id="imgslider" style="margin: 0px; padding: 0px; position: relative; list-style-type: none; z-index: 1; width: 2890px; left: -510px;">
																</ul>
															</div>
															<div class="next_button"></div>
															<div class="clr"></div>
														</div>
													</div>
												</c:if>
											</td>
											<td class="info">
												<div class="func">
													<div class="func1">
														<div style="padding: 0px 0px 10px 0px;">
															<div id="status_content">
																<c:if test="${companyUserStatus!=null}">
																	<c:if test="${!companyUserStatus.done}">
																		<hk:button oid="done" onclick="setdone(this.id)" value="view.companyuserstatus.done" res="true" clazz="btn split-r"/>
																		<hk:button oid="want" value="view.companyuserstatus.want" res="true" clazz="btn_disabled split-r"/>
																	</c:if>
																	<c:if test="${companyUserStatus.done}">
																		<hk:button oid="done" value="view.companyuserstatus.done" res="true" clazz="btn_disabled split-r"/>
																		<hk:button oid="want" onclick="setwant(this.id)" value="view.companyuserstatus.want" res="true" clazz="btn split-r"/>
																	</c:if>
																</c:if>
																<c:if test="${companyUserStatus==null}">
																	<div style="text-align: center;">
																		<hk:button oid="done" onclick="setdone(this.id)" value="view.companyuserstatus.done" res="true" clazz="btn split-r"/>
																		<hk:button oid="want" onclick="setwant(this.id)" value="view.companyuserstatus.want" res="true" clazz="btn split-r"/>
																	</div>
																</c:if>
															</div>
														</div>
													</div>
													<div class="func2">
														<c:if test="${vo.company.rebate>0}">
															<span class="rebate"><fmt:formatNumber value="${vo.company.rebate}" pattern="#.#"></fmt:formatNumber><hk:data key="view.unit.rebate"/></span><br/>
														</c:if>
														<c:if test="${companyKind.priceTip>0}">
															<hk:data key="companykind.prizetip${companyKind.kindId}"/>:${vo.company.price }<hk:data key="view.unit.money"/><br />
														</c:if>
													</div>
													<div class="func3 text_14">
														<c:if test="${not empty vo.company.tel}">
															${vo.company.tel }<br/>
														</c:if>
														<c:if test="${not empty vo.company.addr}">
															${vo.company.addr }<br/>
														</c:if>
														<c:if test="${not empty vo.company.traffic}">${vo.company.traffic }<br/></c:if>
													</div>
													<c:if test="${fn:length(productvolist)>0}">
														<div class="func6">
															<h4><hk:data key="view.company.product"/><a class="view-all" href="<%=path %>/product_list.do?companyId=${companyId}">更多</a></h4>
															<ul class="product">
																<c:forEach var="vo" items="${productvolist}">
																	<li class="product_li">
																		<span class="split-r"><a href="<%=path%>/product.do?pid=${vo.cmpProduct.productId }">${vo.cmpProduct.name }</a></span><span class="split-r">￥${vo.cmpProduct.money }</span><c:if test="${vo.addToCard}"><span id="simcard${vo.cmpProduct.productId }" class="text_12">下单成功，<a href="<%=path%>/shoppingcard.do" class="text_12">马上确认</a></span></c:if><c:if test="${!vo.addToCard}"><span id="simcard${vo.cmpProduct.productId }" class="text_12"><a href="javascript:createtocard(${vo.cmpProduct.productId })" class="text_12">下单</a></span></c:if>
																	</li>
																</c:forEach>
															</ul>
														</div>
													</c:if>
												</div>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<c:if test="${not empty vo.company.intro}">
													<div class="intro">${vo.company.intro }</div>
												</c:if>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<div class="mod">
													<h2><hk:data key="view.company.review"/></h2>
													<div class="bdbtm"></div>
													<jsp:include page="../inc/companyreviewlistvo.jsp"></jsp:include>
													<%request.setAttribute("companyreviewvolist",request.getAttribute("myreviewvolist")); %>
													<jsp:include page="../inc/companyreviewlistvo.jsp"></jsp:include>
													<c:if test="${morereview}"><a href="<%=path %>/cmpreview.do?companyId=${companyId }&${url_add }" class="more"><hk:data key="view.more"/></a></c:if>
													<div>
														<h2>写新评论</h2>
														<c:set var="companyreview_form_action" scope="request">/review/op/op_add.do</c:set>
														<jsp:include page="../inc/companyreviewform.jsp"></jsp:include>
													</div>
												</div>
											</td>
										</tr>
									</table>
								</div>
							</td>
							<td class="cmp_r">
								<br class="cmp_r_top" />
								<c:if test="${vo.company.markerX!=0}">
									<div class="mod2">
										<div class="map">
											<div id="map_canvas" class="map_canvas">
												正在加载地图...
											</div>
											<div class="mapmodify">
												<a href="#">居中</a>
												<a href="#">还原</a>
												<a href="#">大图</a>
												<a href="#">修改</a>
											</div>
										</div>
										<div class="clr"></div>
									</div>
								</c:if>
								<c:if test="${fn:length(like_it_uservolist)>0}">
									<div class="mod-7" style="border-right: 0px">
										<div class="tit"><hk:data key="view.company.user_like_it"/></div>
										<div class="cont">
											<br class="linefix" />
											<div class="cmprefuserlist">
												<c:forEach var="uservo" items="${like_it_uservolist}">
													<div class="cmprefuser">
														<table cellpadding="0" cellspacing="0">
															<tr>
																<td class="user_image">
																	<a class="user" href="<%=path %>/home_web.do?userId=${uservo.user.userId }"><img src="${uservo.user.head48Pic }" />
																	</a>
																</td>
																<td class="user_info">
																	<a class="name" class="text_14" href="<%=path %>/home_web.do?userId=${uservo.user.userId }">${uservo.user.nickName }</a>
																	<c:if test="${!uservo.follow && loginUser.userId!=uservo.user.userId}">
																		<br /><span id="like_it_user${uservo.user.userId }"><a class="act" href="javascript:addfriend(${uservo.user.userId })">加关注</a></span>
																	</c:if>
																</td>
															</tr>
														</table>
													</div>
												</c:forEach>
												<c:if test="${more_like_it_userlist}">
													<a class="more" href="#">更多</a>
												</c:if>
											</div>
										</div>
									</div>
								</c:if>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div class="clr"></div>
<script type="text/javascript" language="javascript" src="<%=path %>/webst3/js/easySlider1.7.js"></script>
<script type="text/javascript" src="<%=path %>/webst3/js/jquery.easing.1.1.js"></script>
<script type="text/javascript" src="<%=path %>/webst3/js/jcarousellite_1.0.1.min.js"></script>
<script type="text/javascript">
var img=new Array();
<c:forEach var="p" items="${photolist}" varStatus="idx">
img[${idx.index }]=new Array(${p.photoId },'${p.pic60 }','${p.pic240 }');
</c:forEach>
var noimg=false;
if(img.length==0){
	noimg=true;
}
function changebig(idx){
	getObj("bigimg").src=img[idx][2];
	getObj('bigimghref').href="<%=path%>/cmp_pic.do?companyId=${companyId}&photoId="+img[idx][0];
}
function init_imgslider(){
	var s="";
	for(var i=0;i<img.length;i++){
		s+='<li style="overflow: hidden; float: left; width: 90px; height: 75px;"><a class="imga"><img src="'+img[i][1]+'" onclick="changebig('+i+')"/></a></li>';
	}
	if(img.length>0){
		setHtml("imgslider",s);
	}
}
init_imgslider();
$("#cmpslider").jCarouselLite({
    btnNext: " .next_button",
    btnPrev: " .previous_button",
    visible: 3
});
function loadGoogleMap(){
	document.write("<script src='http://ditu.google.cn/maps?file=api&amp;v=2&amp;key=<%=HkWebConfig.getGoogleApiKey() %>&hl=zh-CN' type='text/javascript'><\/script>");
}
function initialize() {
	
<c:if test="${vo.company.markerX!=0}">
	if (GBrowserIsCompatible()) {
		var map = new GMap2(document.getElementById("map_canvas"))
		var center=new GLatLng(${vo.company.markerX},${vo.company.markerY });
		map.setCenter(center, 14);
		map.addControl(new GSmallMapControl());
		var marker = new GMarker(center, {draggable: false});
		map.addOverlay(marker);
	}
</c:if>
}

var hasReview=${hasReview};
function toreview(){
	window.location.hash = "write_review";
}
function setCmpuserstatus(v,id){
	if(!user_login){
		alert("请先登录");
		return;
	}
	showSubmitDivForObj(id);
	$.ajax({
		type:"POST",
		url:'<%=request.getContextPath() %>/cmpuserstatus/op/op_web.do',
		data:'status='+v+"&companyId=${companyId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			hideSubmitDiv(id);
			if(v==${USERSTATUS_DONE}){//去过
				var s='<span class="text_14" style="font-weight:bold; ">去过</span> ';
				if(!hasReview){
					s+='<input type="button" value="写新评论" class="btn size1" onclick="toreview()"/>';
				}
				setHtml("status_content",s);
			}
			else{//想去
				setHtml("status_content",'<span class="text_14" style="font-weight:bold; ">想去</span> <input id="done" type="button" value="去过" class="btn size1" onclick="setCmpuserstatus(${USERSTATUS_DONE},this.id)"/>');
			}
		}
	})
}
function setwant(id){
	if(!user_login){
		alert("请先登录");
		return;
	}
	$.ajax({
		type:"POST",
		url:'<%=request.getContextPath() %>/cmpuserstatus/op/op_web.do',
		data:"status=<%=CompanyUserStatus.USERSTATUS_WANT %>&companyId=${companyId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			hideSubmitDiv(id);
			var html='<hk:button oid="done" onclick="setdone(this.id)" value="view.companyuserstatus.done" res="true" clazz="btn split-r"/>';
			html+='<hk:button oid="want" value="view.companyuserstatus.want" res="true" clazz="btn_disabled split-r"/>';
			setHtml('status_content',html);
		}
	})
}
function setdone(id){
	if(!user_login){
		alert("请先登录");
		return;
	}
	$.ajax({
		type:"POST",
		url:'<%=request.getContextPath() %>/cmpuserstatus/op/op_web.do',
		data:"status=<%=CompanyUserStatus.USERSTATUS_DONE %>&companyId=${companyId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			hideSubmitDiv(id);
			var html='<hk:button oid="done" value="view.companyuserstatus.done" res="true" clazz="btn_disabled split-r"/>';
			html+='<hk:button oid="want" onclick="setwant(this.id)" value="view.companyuserstatus.want" res="true" clazz="btn split-r"/>';
			setHtml('status_content',html);
		}
	})
}
function addfriend(userId){
	if(!user_login){
		alert("请先登录");
		return;
	}
	var action_url='<%=path%>/follow/op/op_addweb.do?userId='+userId;
	setHtml('like_it_user'+userId,'操作中 ... ');
	$.ajax({
		type:"POST",
		url:action_url,
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('like_it_user'+userId,'加好友成功');
		}
	});
}
function createtocard(pid,chgflg){
	setHtml('simcard'+pid,'下单中 ...');
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
					setHtml('simcard'+pid,'<a class="text_16" href="javascript:createtocard('+pid+')"><hk:data key="view.product.addtocard"/></a>');
				}
			}
			else{
				setHtml('simcard'+pid,'下单成功，<a href="<%=path%>/shoppingcard.do">马上确认</a>');
			}
		}
	});
}
loadGoogleMap();
setTimeout("initialize()",4000);
function createcmdcmp(){
	showSubmitDivForObj('cmd');
	$.ajax({
		type:"POST",
		url:'<%=path %>/op/cmd/cmd_createcmdcmp.do?companyId=${companyId}',
		cache:false,
    	dataType:"html",
		success:function(data){
			hideSubmitDiv();
			getObj('cmd').href="javascript:void(0)";
			setHtml('cmd','推荐成功');
		}
	});
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>