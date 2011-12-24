<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@page import="com.hk.web.util.HkWebConfig"%><%@page import="com.hk.bean.CmpTip"%><%@page import="com.hk.frame.util.DataUtil"%><%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><%String path = request.getContextPath();%>
<c:set var="js_value" scope="request">
<link rel="stylesheet" type="text/css" href="<%=path %>/webst4/css/box.css" />
</c:set>
<c:set var="html_title" scope="request"><hk:data key="view2.website.title" /></c:set>
<c:set var="html_body_content" scope="request">
<style>
.index_user{
	float: left;
	width: 60px;
	overflow: hidden;
	text-align: center;
	margin-right: 20px;
	line-height: 20px;
}
.index_user img{
width: 60px;
height: 60px;
}
.index_user .index_user_con{
text-align: left;
font-size: 12px;
}

</style>
<div class="hcenter" style="width: 820px;">
	<div>
		<div class="mod">
			<div class="f_l boxcon" style="width: 540px">
				<c:if test="${box!=null}">
					<div class="box_name">
					<h1><a href="/box/${box.boxId }">${box.name }</a></h1>
					</div>
					<div class="f_l" style="width: 200px">
						<div class="boxprize_tit">
							奖品
						</div>
						<div class="boxprize_con">
							<div class="boxprize">
								<c:if test="${fn:length(boxprizelist)==1}">
									<div style="text-align: center;padding: 10px 0;">
										 <c:forEach var="prize" items="${boxprizelist}">
										 ${prize.name }<br/><strong>${prize.pcount }个</strong>
										 </c:forEach>
									</div>
								</c:if>
								<c:if test="${fn:length(boxprizelist)==2}">
									 <c:forEach var="prize" items="${boxprizelist}" varStatus="idx">
									 	<c:if test="${idx.index==1}"><c:set var="bdlnone">border-right: 0px;</c:set></c:if>
										<div class="f_l" style="text-align: center;width: 49%;padding: 10px 0;border-right: 1px solid #cccccc;${bdlnone }">
										 ${prize.name }<br/><strong>${prize.pcount }个</strong>
										</div>
									 </c:forEach>
								</c:if>
								<c:if test="${fn:length(boxprizelist)==3}">
									 <c:forEach var="prize" items="${boxprizelist}" varStatus="idx">
									 	<c:if test="${idx.index==2}"><c:set var="bdlnone">border-right: 0px;</c:set></c:if>
										<div class="f_l" style="text-align: center;width: 32%;padding: 10px 0;border-right: 1px solid #cccccc;${bdlnone }">
										 ${prize.name }<br/><strong>${prize.pcount }个</strong>
										</div>
									 </c:forEach>
								</c:if>
								<div class="clr"></div>
							</div>
							<div class="box_tit">
								<div class="divrow">${box.totalCount-box.openCount }个</div>
								<form method="post" action="<%=path %>/h4/op/user_openbox.do">
									<hk:hide name="boxId" value="${box.boxId}"/>
									<hk:submit clazz="btn2" value="开箱子"/>
								</form>
							</div>
							<div class="box_time">
								宝箱提示信息
							</div>
						</div>
					</div>
					<div class="f_r">
						<div id=imgstore  style="display:none">
							<c:forEach var="prize" items="${boxprizelist}">
								<c:if test="${not empty prize.path}">
									<img src="${prize.h_1Pic }" title="${prize.name }" alt="${prize.name }" rel="/box/${box.boxId }"/>
								</c:if>
								<c:if test="${empty prize.path}">
									<img src="<%=path %>/webst4/img/baoxiang.png" rel="/box/${box.boxId }" title="${prize.name }" alt="${prize.name }" />
								</c:if>
							</c:forEach>
						</div>
						<div id="showhere">
							<c:if test="${piccount==1}">
								<c:forEach var="prize" items="${boxprizelist}">
									<c:if test="${not empty prize.path}">
										<img src="${prize.h_1Pic }" title="${prize.name }" alt="${prize.name }" rel="/box/${box.boxId }"/>
									</c:if>
									<c:if test="${empty prize.path}">
										<img src="<%=path %>/webst4/img/baoxiang.png" rel="/box/${box.boxId }" title="${prize.name }" alt="${prize.name }" />
									</c:if>
								</c:forEach>
							</c:if>
						</div>
					</div>
					<div class="clr"></div>
				</c:if>
				<c:if test="${box==null}">
					<table width="100%" cellpadding="0" cellspacing="0">
						<tr>
						<td valign="middle" align="center">
						<img src="<%=path %>/webst4/img/baoxiang.png"/><br/>
						更多惊喜，即将发布！
						</td>
						</tr>
					</table>
				</c:if>
			</div>
			<div class="f_r" style="width: 240px">
				<div style="margin-bottom: 10px">火酷可以告诉你大家喜欢去哪里，哪些地方最火，人气最旺。</div>
				<div style="margin-bottom: 10px">当你去一个地方的时候，通过手机报到一下，你将能结识经常去这里的朋友。</div>
				<div style="margin-bottom: 20px">通过报到，你可以得到徽章，还可以成为这个地方的地主，享受特别奖励。</div>
				<c:if test="${userLogin}">
					<div>1、<a href="/invite">邀请</a>一个朋友来火酷，获得点数。</div>
					<div>2、对你去过的地方进行报到，获得点数。</div>
					<div>3、消耗点数可以免费开宝箱，获得各种奖励！</div>
				</c:if>
				<c:if test="${!userLogin}">
					<div>
						<form id="loginfrm" method="post" onsubmit="return subloginfrm(this.id)" action="/login" target="hideframe">
							<hk:hide name="ch" value="1"/>
							<table cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<hk:data key="view2.login.input"/><br/>
									<input type="text" name="input" maxlength="50" class="text" style="width: 200px"/>
								</td>
							</tr>
							<tr>
								<td>
									<hk:data key="view2.password"/><br/>
									<input type="password" name="password" maxlength="50" class="text" style="width: 200px"/>
								</td>
							</tr>
							<tr>
								<td>
									<div id="logininfo" class="infowarn"></div>
									<div style="padding-left: 120px"><hk:submit value="view2.login" res="true" clazz="btn"/></div>
									<a class="split-r" href="/signup"><hk:data key="view2.nothaveid_pleasesignup"/></a><a href="<%=path %>/h4/pwd.do">忘记密码</a>
								</td>
							</tr>
							</table>
						</form>
					</div>
				</c:if>
			</div>
			<div class="clr"></div>
		</div>
	</div>
	<div class="index2">
	<c:if test="${fn:length(mayorlist)>0}">
		<div class="mod">
			<div class="mod_title"><hk:data key="view2.newmayors"/>，${sys_zone_pcity.name }</div>
			<div class="mod_content">
				<c:forEach var="m" items="${mayorlist}">
					<div class="index_user">
						<a href="/user/${m.userId }"><img src="${m.user.head80Pic }" alt="${m.user.nickName }" title="${m.user.nickName }"/></a>
						<div class="index_user_con">
							<a href="/user/${m.userId }">${m.user.nickName }</a> 在 
							<a href="/venue/${m.lastCmpCheckInUserLog.companyId }">${m.lastCmpCheckInUserLog.company.name }</a>
						</div>
					</div>
				</c:forEach>
				<div class="clr"></div>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(cmplist)>0}">
		<div class="mod">
			<div class="mod_title"><hk:data key="view2.cmp_of_userlike"/></div>
			<div class="mod_content">
				<c:forEach var="c" items="${cmplist}">
					<div class="index_user">
						<a href="/venue/${c.companyId }"><img src="${c.head60 }" alt="${c.name }" title="${c.name }"/></a>
						<div class="index_user_con">
							<a href="/venue/${c.companyId }">${c.name }</a>
						</div>
					</div>
				</c:forEach>
				<div class="clr"></div>
			</div>
		</div>
	</c:if>
	</div>
</div>
<script type="text/javascript" language="javascript" src="<%=path %>/webst4/js/jquery.myslide.js"></script>
<script type="text/javascript">
<c:if test="${piccount>1}">
$(document).ready(function(){
	$.init_slide('imgstore','showhere',0,0,1000,1,6000,0,'_self');
	});
</c:if>

function subloginfrm(frmid){
	setHtml('logininfo','');
	showGlass(frmid);
	return true;
}
function loginok(error,error_msg,respValue){
	//tourl('<%=path%>/h4/user_me.do');
	tourl('<%=path%>/h4/login_checkip.do?return_url=${return_url}');
}
function loginerror(error,error_msg,respValue){
	setHtml('logininfo',error_msg);
	hideGlass();
}
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>