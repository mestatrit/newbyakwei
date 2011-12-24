<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%@ taglib uri="http://www.opensymphony.com/oscache" prefix="cache"%>
<%String path = request.getContextPath();%>
<c:set scope="request" var="epp_other_value">
<link rel="stylesheet" type="text/css" href="<%=path %>/cmpwebst4/mod/pub/css/jcarousel/skins/tango/skin.css"/>
<script type="text/javascript" src="<%=path %>/cmpwebst4/mod/pub/js/jquery.jcarousel.min.js"></script>
</c:set>
<c:set scope="request" var="html_body_content">
	<div class="tpad">
		<div class="leftmenu">
			<div class="navbox">
				<a href="#">左边导航文字宽度</a>
			</div>
			<div class="navbox">
				<a href="#">左边导航</a>
			</div>
			<div class="navbox">
				<a href="#">左边导航</a>
			</div>
			<div class="navbox">
				<a href="#">左边导航</a>
			</div>
		</div>
		<div class="main">
			<div class="product">
				<div class="bigimg">
					<div align="center">
						<c:if test="${not empty cmpActor.picPath}">
							<img id="bigimg" src="${cmpActor.pic320Url }" />
						</c:if>
					</div>
					<div id="imgsilder"></div>
				</div>
				<div class="info">
					<div>
						<a href="http://<%=request.getServerName() %>">首页</a> &gt;
						<c:if test="${cmpActor.roleId>0}">
						<a href="/actors/${companyId }/0/role/${cmpActor.roleId}">${cmpActorRole.name }</a> &gt;
						</c:if>
						${cmpActor.name }
					</div>
					<h1>
						${cmpActor.name }
					</h1>
					<ul class="ulintro">
						<li>
							<a id="userinfo_a" href="javascript:showuserinfo();" class="cur">个人介绍</a>
						</li>
						<li>
							<a id="svrinfo_a" href="javascript:void(0)">服务介绍</a>
						</li>
					</ul>
					<div class="clr"></div>
					<div class="intro">
						<div class="inpad">
							<div id="info_txt" class="txtscroll">
								${cmpActor.intro }
							</div>
						</div>
					</div>
					<div class="svr">
						<div class="inpad">
							<div class="fl" style="width: 340px;">
								<ul class="svrlist">
									<c:if test="${fn:length(svrreflist)>0}">
										<c:forEach var="svrref" items="${svrreflist}">
											<li id="svr_${svrref.oid }" svrid="${svrref.svrId }" setid="${svrref.cmpSvr.photosetId }">
												<span class="name">${svrref.cmpSvr.name }</span>
												<span class="price">￥${svrref.cmpSvr.price }</span>
												<div class="clr"></div>
											</li>
										</c:forEach>
									</c:if>
								</ul>
							</div>
							<div class="fr">
								<div class="divrow">
									<input type="button" class="btn" value="马上预约" onclick="toreserve()"/>
								</div>
							</div>
							<div class="clr"></div>
						</div>
					</div>
				</div>
				<div class="clr"></div>
				<div class="review">
					<div class="tit">
						<h3>
							5个点评
						</h3>
					</div>
					<div class="content">
						<form method="post" action="#" target="_blank">
							<div class="divrow">
								<textarea class="text_area"></textarea>
							</div>
							<div class="divrow" align="right" style="padding-right: 20px;">
								<input type="submit" value="提交点评" class="btn" />
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="clr"></div>
	</div>
<script type="text/javascript">
var svrinfo=new Array();
<c:forEach var="svrref" items="${svrreflist}" varStatus="idx">
svrinfo[${svrref.svrId }]="${svrref.cmpSvr.intro }";
</c:forEach>
var current_svr_id = -1;
var currentsvrid=-1;
$(document).ready(function(){
	$('ul.svrlist li').bind('mouseover', function(){
		$(this).addClass('cur');
	}).bind('mouseout', function(){
		$(this).removeClass('cur');
	}).bind('click', function(){
		if (current_svr_id != -1) {
			$('#' + current_svr_id).removeClass('cur2');
		}
		current_svr_id = $(this).attr('id');
		currentsvrid=$(this).attr('svrid');
		$(this).addClass('cur2');
		$('#info_txt').html('service info introduce');
		showsvrinfo(currentsvrid);
		showimg($(this).attr('setid'));
	});
});
function showimg(setId){
	$('#imgsilder').html('');
	$.ajax({
		type:"POST",
		url:"<%=path%>/epp/web/svr_photoajax.do?companyId=${companyId}&setId="+setId,
		cache:false,
    	dataType:"html",
		success:function(data){
			if(data=='0'){
				return;
			}
			$('#imgsilder').html(data);
			$('#mycarousel').jcarousel({
				vertical: false,
				scroll: 1,
				visible: 3,
				buttonNextHTML: '<a id="next" class="divbtn">上一个</a>',
				buttonPrevHTML: '<a id="prev" class="divbtn">下一个</a>'
			});
		}
	});
}
function showsvrinfo(svrId){
	$('#info_txt').html(svrinfo[svrId]);
	$('#userinfo_a').removeClass('cur');
	$('#svrinfo_a').addClass('cur');
}
function showuserinfo(){
	$('#info_txt').html('${cmpActor.intro}');
	$('#userinfo_a').addClass('cur');
	$('#svrinfo_a').removeClass('cur');
	<c:if test="${not empty cmpActor.picPath}">
		getObj('bigimg').src="${cmpActor.pic320Url }";
	</c:if>
	
}
function showbigimg(url){
	getObj('bigimg').src=url;
}
function toreserve(){
	tourl("<%=path %>/epp/web/op/reserve_sel.do?companyId=${companyId}&actorId=${actorId}&svrId="+currentsvrid);
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>