<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.Hkcss2Util"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${user.nickName}</c:set>
<c:set var="css_value" scope="request"><link type="text/css" rel="stylesheet" href="<%=path%>/webst3/css/user.css" /></c:set>
<c:set var="meta_value" scope="request"><meta name="keywords" content="${user.nickName}"/><meta name="description" content="${user.nickName}"/></c:set>
<c:set var="body_hk_content" scope="request">
	<table class="frame-3" cellpadding="0" cellspacing="0">
		<tr>
			<td class="l">
				<div class="mod_left">
					<c:if test="${fn:length(cmplist)>0}">
						<div class="mod">
							<div class="mod-4">
								<%=Hkcss2Util.rd_bg%>
								<div class="tit">
									<a href="<%=path %>/usercmp.do?userId=${userId }"><hk:data key="view.user_company.title" arg0="${user.nickName}" /></a>
								</div>
								<div class="cont">
									<br style="line-height: 0px;" />
									<c:forEach var="c" items="${cmplist}">
										<div class="simple_product">
											<div class="image">
												<a href="<%=path%>/cmp.do?companyId=${c.companyId }"><img src="${c.head60 }" /> </a>
											</div>
											<div class="content">
												<c:if test="${c.totalScore>0}">
													<img src="<%=path%>/webst3/img/stars/star${c.starsLevel }.gif" /><br />
												</c:if>
												<a href="<%=path%>/cmp.do?companyId=${c.companyId }">${c.name }</a>
											</div>
											<div class="clr"></div>
										</div>
									</c:forEach>
									<c:if test="${more_cmplist}">
										<a class="more" href="<%=path %>/usercmp.do?userId=${userId }"><hk:data key="view.more" /> </a>
									</c:if>
								</div>
								<%=Hkcss2Util.rd_bg_bottom%>
							</div>
							<div class="clr"></div>
						</div>
					</c:if>
				</div>
				<div class="mod_left">
					<c:if test="${fn:length(favproductlist)>0}">
						<div class="mod">
							<div class="mod-4">
								<%=Hkcss2Util.rd_bg%>
								<div class="tit">
									<a href="<%=path %>/userproductfav.do?userId=${userId }"><hk:data key="view.user_fav_product" arg0="${user.nickName}" /></a>
								</div>
								<div class="cont">
									<br style="line-height: 0px;" />
									<c:forEach var="p" items="${favproductlist}">
										<div class="simple_product">
											<div class="image">
												<a href="<%=path%>/product.do?pid=${p.productId }"><img src="${p.head60 }" /> </a>
											</div>
											<div class="content">
												<c:if test="${p.score>0}">
													<img src="<%=path%>/webst3/img/stars/star${p.starsLevel }.gif" /><br />
												</c:if>
												<a href="<%=path%>/product.do?pid=${p.productId }">${p.name }</a>
											</div>
											<div class="clr"></div>
										</div>
									</c:forEach>
									<c:if test="${morefavproduct}">
										<a class="more" href="<%=path %>/userproductfav.do?userId=${userId }"><hk:data key="view.more" /> </a>
									</c:if>
								</div>
								<%=Hkcss2Util.rd_bg_bottom%>
							</div>
							<div class="clr"></div>
						</div>
					</c:if>
				</div>
			</td>
			<td class="mid">
				<div class="mid_con">
					<div class="mod">
						<div class="userinfo">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td class="headimg">
										<div class="f_l">
											<div id="imgcondiv">
											</div>
											<script type="text/javascript">
											function showdefimg(obj){
												obj.src='${user.head80Pic}';
											}
											function touploadheadpic(){
												tourl('<%=path%>/user/set/set_tosetheadweb.do');
											}
											var imghtml='<img id="userhead" src="${user.head80Pic}?v=<%=Math.random()%>" onerror="showdefimg(this)"';
											if(user_login){
												imghtml+=' onclick="touploadheadpic()"/>';
											}
											else{
												imghtml+='/>';
											}
											setHtml("imgcondiv",imghtml);
											if(user_login){
												getObj("userhead").style.cssText="cursor: pointer;";
											}
											</script>
											<a href="#">${user.nickName}</a>
										</div>
										<div class="f_l usertopname">
											
										</div>
									</td>
									<td class="info">
										<div>
											${userinfointro }，<a href="<%=path %>/friend.do?userId=${userId}"><hk:data key="view.followinfo" arg0="${user.friendCount}"/></a>
											，<a href="<%=path %>/followed.do?userId=${userId}"><hk:data key="view.fansinfo" arg0="${user.fansCount}"/></a>
											<br />
											${userOtherInfo.intro }
										</div>
									</td>
								</tr>
								<c:if test="${loginUser.userId!=userId && userLogin}">
								<tr>
									<td colspan="2">
										<div id="userop" class="useropt">
										<hk:rmBlankLines rm="true">
											<c:if test="${areFriend==null || !areFriend}">
												<a id="friendop" href="javascript:addfriend()" class="split-r">关注</a>
											</c:if>
											<c:if test="${areFriend}">
												<a id="friendop" href="javascript:rmfriend()" class="split-r">取消关注</a>
											</c:if> 
											<c:if test="${followedByUser}">
											<a id="msgop" href="javascript:void(0)" onclick="showmsgwin()" class="split-r">私信</a>
											</c:if>
											<a href="<%=path %>/laba_fav.do?userId=${userId}" class="split-r"><hk:data key="view.user.favlaba1"/></a>
										</hk:rmBlankLines>
										</div>
									</td>
								</tr>
								</c:if>
							</table>
						</div>
					</div>
					<c:if test="${fn:length(usercmpreviewvolist)>0}">
						<div class="mod">
							<h3 class="title">足迹点评
							<a class="more" href="<%=path %>/usercmpreview.do?userId=${userId }"><hk:data key="view.more" /></a>
							</h3>
							<jsp:include page="usercmpreviewlist_inc.jsp"></jsp:include>
						</div>
					</c:if>
					<c:if test="${fn:length(cmpproductreviewvolist)>0}">
						<div class="mod">
							<h3 class="title">产品点评
							<a class="more" href="<%=path %>/usercmpproductreview.do?userId=${userId }"><hk:data key="view.more" /></a>
							</h3>
							<jsp:include page="usercmpproductreviewlist_inc.jsp"></jsp:include>
						</div>
					</c:if>
					<c:if test="${fn:length(labavolist)>0}">
						<div class="mod">
							<h3 class="title">喇叭
							<c:if test="${more_laba}">
								<a class="more" href="<%=path %>/laba_userlaba.do?userId=${userId }"><hk:data key="view.more" /> </a>
							</c:if>
							</h3>
							<c:set var="laba_url_add" scope="request">from=home&ouserId=${userId}</c:set>
							<c:set var="labavo_not_show_head" value="true" scope="request"/>
							<jsp:include page="../inc/labavolist_inc.jsp"></jsp:include>
						</div>
					</c:if>
				</div>
			</td>
			<td class="r">
				<div class="f_r">
					<c:if test="${fn:length(feedvolist)>0}">
						<div class="mod">
							<div class="mod-4 r_mod3">
								<%=Hkcss2Util.rd_bg%>
								<div class="tit">
									<a href="<%=path %>/userfeed.do?userId=${userId }"><hk:data key="view.user_feed.title" arg0="${user.nickName}" /></a>
								</div>
								<div class="cont">
									<div class="nor_cont">
										<c:forEach var="feedvo" items="${feedvolist}">
											<div class="row text_12">
												${feedvo.content }
											</div>
										</c:forEach>
									</div>
									<c:if test="${more_feedlist}">
										<a class="more" href="<%=path %>/userfeed.do?userId=${userId }"><hk:data key="view.more" /> </a>
									</c:if>
								</div>
								<%=Hkcss2Util.rd_bg_bottom%>
							</div>
							<div class="clr"></div>
						</div>
					</c:if>
					<c:if test="${fn:length(frienduserlist)>0}">
						<div class="mod">
							<div class="mod-4 r_mod3">
								<%=Hkcss2Util.rd_bg%>
								<div class="tit">
									<a href="<%=path %>/friend.do?userId=${userId }"><hk:data key="view.user_friend.title" arg0="${user.nickName}" /></a>
								</div>
								<div class="cont">
								<br class="clearfix"/>
									<div class="imglist-1">
										<c:forEach var="fu" items="${frienduserlist}">
											<a class="imgref" href="<%=path%>/home_web.do?userId=${fu.userId }"><img src="${fu.head48Pic }" title="${fu.nickName }" /> </a>
										</c:forEach>
										<div class="clr"></div>
									</div>
									<c:if test="${more_frienduserlist}">
										<a class="more" href="<%=path %>/friend.do?userId=${userId }"><hk:data key="view.more" /> </a>
									</c:if>
								</div>
								<%=Hkcss2Util.rd_bg_bottom%>
							</div>
							<div class="clr"></div>
						</div>
					</c:if>
					<c:if test="${fn:length(visitoruserlist)>0}">
						<div class="mod">
							<div class="mod-4 r_mod3">
								<%=Hkcss2Util.rd_bg%>
								<div class="tit">
									<hk:data key="view.user_recentvisitor" />
								</div>
								<div class="cont">
									<div class="imglist-1">
										<c:forEach var="fu" items="${visitoruserlist}">
											<a class="imgref" href="<%=path%>/home_web.do?userId=${fu.userId }"><img src="${fu.head48Pic }" title="${fu.nickName }" /> </a>
										</c:forEach>
										<div class="clr"></div>
									</div>
								</div>
								<%=Hkcss2Util.rd_bg_bottom%>
							</div>
							<div class="clr"></div>
						</div>
					</c:if>
				</div>
			</td>
		</tr>
	</table>
<script type="text/javascript">
var userId=${userId};
function addfriend(){
	var h=getHtml('userop');
	var action_url='<%=path%>/follow/op/op_addweb.do?userId='+userId;
	setHtml('userop','数据提交中 ... ...');
	$.ajax({
		type:"POST",
		url:action_url,
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('userop',h);
			getObj('friendop').href="javascript:rmfriend()";
			setHtml('friendop','取消关注');
			initmsg(data);
		}
	});
}
function rmfriend(){
	var h=getHtml('userop');
	var action_url='<%=path%>/follow/op/op_delweb.do?userId='+userId;
	setHtml('userop','数据提交中 ... ...');
	$.ajax({
		type:"POST",
		url:action_url,
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('userop',h);
			getObj('friendop').href="javascript:addfriend()";
			setHtml('friendop','关注');
			initmsg(data);
		}
	});
}
function initmsg(data){
	if(data==-1){//可以发私信
		if(getObj('msgop')==null){
			appendObj('userop','<a href="javascript:showmsgwin()">私信</a>');
		}
	}
	else{//不能发私信
		delObj('msgop');
	}
}
function showmsgwin(){
	var html='<form target="hideframe" onkeydown="keydown(event)" id="msg_frm" method="post" onsubmit="return checksend(this.id)" action="<%=path %>/msg/send_sendweb.do"><input type="hidden" name="receiverId" value="'+userId+'"/><table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td> <div class="f_l"> <textarea name="msg" style="width:350px;height: 100px;margin:0;padding:0; "></textarea> <div id="msg_msg_error" class="error"></div> </div> <div id="msg_msg_flag" class="flag"></div> <div class="clr"></div> </td></tr> <tr> <td> <div style="text-align: center"> <input type="submit" value="发送" class="btn"/> </div> </td> </tr> </table></form>';
	createSimpleCenterWindow('msgwin',420, 300, "发送私信", html,"hideWindow('msgwin')");
}
function keydown(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(checksend("msg_frm")){
			getObj("msg_frm").submit();
		}
	}
}
function checksend(frmid){
	showSubmitDiv(frmid);
	return true;
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error==0){
		hideSubmitDiv();
		setHtml("msgwin_content",'<strong class="text_16 green">私信发送成功</strong>');
		delay('hideWindow("msgwin")',3000);
	}
	else{
		setHtml("msg_msg_error",error_msg);
		hideSubmitDiv();
	}
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>