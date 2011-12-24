<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.CompanyUserStatus"%><%@page import="com.hk.web.util.HkWebConfig"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${cmpActor.name } - ${company.name}</c:set>
<c:set var="js_value" scope="request">
<meta name="keywords" content="${cmpActor.name }|${company.name }|<hk:data key="view2.website.title"/>"/>
<script type="text/javascript" language="javascript" src="<%=path %>/webst4/js/reserve.js"></script>
</c:set>
<c:set var="html_body_content" scope="request">
	<div class="mod">
		<div class="f_l">
			<h1>
				${cmpActor.name } <hk:data key="view.cmpactor.gender${cmpActor.gender}"/> ${cmpActorRole.name }
			</h1>
		</div>
		<div class="f_r" style="width: 300px">
			<div class="mod">
				<a href="<%=path %>/h4/venue.do?companyId=${companyId}&mf=1">回到店铺首页</a>
			</div>
			<input type="button" class="btn" value="预约" style="margin-right: 30px;" onclick="reserve_actor(${companyId},${actorId})"/>
			<input type="button" class="btn" value="返回" onclick="tourl('/cmp/${companyId}/actor/list')"/>
		</div>
		<div class="clr"></div>
	</div>
	<div class="mod">
		<div class="f_l" style="width: 500px; text-align: center;">
			<c:if test="${not empty cmpActor.picPath}">
				<img src="${cmpActor.pic500Url }" />
			</c:if>
		</div>
		<div class="f_r" style="width: 340px">
			<c:if test="${admin}">
				<div class="mod">
					<c:if test="${cmpActorPink!=null}">
					<a href="javascript:delcmpactorpink(${cmpActorPink.oid })">取消推荐</a>
					</c:if>
					<c:if test="${cmpActorPink==null}">
					<a href="javascript:createcmpactorpink()">推荐</a>
					</c:if>
				</div>
				<script type="text/javascript">
				function delcmpactorpink(oid){
					var url="<%=path%>/h4/admin/actor_delcmpactorpink.do?oid="+oid;
					doAjax(url,function(data){
						refreshurl();
					});
				}
				function createcmpactorpink(){
					var url="<%=path%>/h4/admin/actor_createcmpactorpink.do?companyId=${cmpActor.companyId}&actorId=${actorId}";
					doAjax(url,function(data){
						refreshurl();
					});
				}
				</script>
			</c:if>
			<div class="mod">
				<div class="mod_title">个人介绍</div>
				<div class="mod_content">
					${cmpActor.intro }
				</div>
			</div>
			<c:if test="${fn:length(svrreflist)>0}">
				<div class="mod">
					<div class="mod_title">产品服务</div>
					<div class="mod_content">
						<ul class="datalist svrlist">
							<c:forEach var="svrref" items="${svrreflist}">
								<li idx="${svrref.oid }">
									<div class="svrname">
										<a href="/cmp/${companyId }/service/${svrref.svrId}">${svrref.cmpSvr.name }</a>
									</div>
									<div class="svrprice">
										<c:if test="${not empty svrref.cmpSvr.price}">￥${svrref.cmpSvr.price }</c:if>
									</div>
									<div id="svr_reserve_${svrref.oid }" class="svr_reserve">
										<input type="button" class="btn_reserve" value="预约" onclick="reserve_svrfromactor(${companyId},${svrref.svrId },${actorId })"/>
									</div>
									<div class="clr"></div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</c:if>
		</div>
		<div class="clr"></div>
	</div>
	<div class="pl">
		<div class="mod">
			<div class="mod_title">点评<a name="to_cmt"></a></div>
			<div class="mod_content">
				<ul class="reviewlist">
					<c:forEach var="cmt" items="${cmtlist}">
						<li idx="${cmt.cmtId }">
							<div class="head"><a href="/user/${cmt.userId }"><img src="${cmt.user.head80Pic }" /></a><a href="/user/${cmt.userId }">${cmp.user.nickName }</a></div>
							<div class="review bd">
								<div class="bd">
									<div class="f_l"><fmt:formatDate value="${cmt.createTime}" pattern="yyyy-MM-dd"/></div>
									<div id="cmt_op${cmt.cmtId }" class="f_r hide">
										<a class="split-r" href="javascript:updatecmt(${cmt.cmtId })">修改</a>
										<a class="split-r" href="javascript:delcmt(${cmt.cmtId })">删除</a>
									</div>
									<div class="clr"></div>
								</div>
								${cmt.content }
							</div>
							<div class="clr"></div>
						</li>
					</c:forEach>
					<c:if test="${userLogin}">
						<li>
							<div class="head"><img src="${loginUser.head80Pic }" /></div>
							<div class="review">
							<form id="cmtfrm" method="post" onsubmit="return subcmtfrm(this.id)" action="<%=path %>/h4/venue/actorcmt_prvcreate.do" target="hideframe">
								<hk:hide name="actorId" value="${actorId}"/>
								<div class="divrow">
									<select name="score">
										<option value="0">选择打分</option>
										<option value="-2">很差</option>
										<option value="-1">差</option>
										<option value="1">一般</option>
										<option value="2">好</option>
										<option value="3">很好</option>
									</select>
								</div>
								<div class="divrow"><hk:textarea oid="_content" name="content" style="width:470px;height:100px;"/></div>
								<div class="infowarn" id="msginfo"></div>
								<div class="divrow">
									<div class="f_l b" style="font-size: 18px;"><span id="numcount">1000</span>字</div>
									<div class="f_r">
										<hk:submit clazz="btn" value="提交点评" oid="sub"/>
									</div>
									<div class="clr"></div>
								</div>
							</form>
							</div>
							<div class="clr"></div>
						</li>
					</c:if>
					<c:if test="${!userLogin}">
						请先 <a class="b" href="javascript:login()">登录</a> 再发布点评
					</c:if>
				</ul>
			</div>
		</div>
		<c:if test="${more_cmt}">
			<a class="more2" href="/cmp/${cmpActor.companyId }/actor/${actorId}/comment">更多点评</a>
		</c:if>
	</div>
	<div class="pr">
		<c:if test="${fn:length(actorlist)>0}">
			<div class="mod">
				<div class="mod_title">其他美发师</div>
				<div class="mod_content">
					<ul class="datalist actor">
						<c:forEach var="actor" items="${actorlist}">
							<li idx="${actor.actorId }">
								<div class="svrname">
									<a href="/cmp/${companyId }/actor/${actor.actorId}">${actor.name } ${actor.cmpActorRole.name }</a>
								</div>
								<div id="actor_reserve_${actor.actorId }" class="svr_reserve">
									<input type="button" class="btn_reserve" value="预约" onclick="reserve_actor(${companyId},${actor.actorId})"/>
								</div>
								<div class="clr"></div>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</c:if>
	</div>
	<div class="clr"></div>
<script type="text/javascript">
$(document).ready(function(){
	$('ul.svrlist li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
		var idx = $(this).attr('idx');
		$('#svr_reserve_' + idx).css('display', 'block');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
		var idx = $(this).attr('idx');
		$('#svr_reserve_' + idx).css('display', 'none');
	});
	$('ul.actor li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
		var idx = $(this).attr('idx');
		$('#actor_reserve_' + idx).css('display', 'block');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
		var idx = $(this).attr('idx');
		$('#actor_reserve_' + idx).css('display', 'none');
	});
	$('ul.reviewlist li').bind('mouseenter', function(){
		$(this).css('background-color','#ffffcc');
		$('#cmt_op'+$(this).attr('idx')).css('display', 'block');
	}).bind('mouseleave', function(){
		$(this).css('background-color','#ffffff');
		$('#cmt_op'+$(this).attr('idx')).css('display', 'none');
	});
});
function login(){
	tourl("<%=path%>/h4/login.do?return_url="+encodeLocalURL());
}
function subcmtfrm(frmid){
	getObj('sub').disabled=true;
	setHtml('msginfo','');
	showGlass(frmid);
	return true;
}
function createerr(e,msg,v){
	getObj('sub').disabled=false;
	setHtml('msginfo',msg);
	hideGlass();
}
function createok(e,msg,v){
	refreshurl();
}
function updateNumCount() {
	setHtml('numcount',(1000 - getObj('_content').value.length));
	setTimeout(updateNumCount, 500);
}
updateNumCount();
getObj('sub').disabled=false;
<c:if test="${to_cmt}">
window.location.hash = "to_cmt";
</c:if>
</script>
<c:remove var="to_cmt" scope="session"/>
</c:set><jsp:include page="../../inc/frame.jsp"></jsp:include>