<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request">${user.nickName}</c:set>
<c:set var="js_value" scope="request">
<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/js4/tip.js"></script>
</c:set>
<c:set var="html_body_content" scope="request">
	<div class="userbody">
		<div class="f_l"  style="width: 300px">
			<div class="head"><img src="${user.head80Pic }" /></div>
			<div class="info">
				<div><h1 style="display: inline;" class="split-r">${user.nickName }</h1><c:if test="${hasfriend}">√ <hk:data key="view2.isfriend"/></c:if></div>
				<c:if test="${user.pcityId>0}"><hk:data key="view2.user_currentlocation"/>：${user.pcity.name }</c:if>
				<c:if test="${me}">
				<div><input type="button" value="<hk:data key="view2.addvenueandtip"/>" onclick="tourl('/venue/search')" class="btn"/></div>
				</c:if>
				<c:if test="${!me && userLogin}">
					<div style="padding-top: 10px">
						<div class="f_l">
							<div style="border: 1px solid #cccccc;background-color: #e5e5e5;width: 120px;padding:0px 0px 0px 10px;">
								<c:if test="${hasfriend}">
									<c:if test="${followed}">
										<a href="javascript:showmsgwin()"><hk:data key="view2.pvtmsg"/></a> | 
									</c:if>
								</c:if>
								<a href="<%=path %>/h4/op/user_selequ.do?userId=${userId}">使用道具</a>
							</div>
							<c:if test="${!hasfriend && !blocked && !blockedUser}">
								<div><a class="b" href="javascript:uaddfriend()"><hk:data key="view2.addfriend"/></a></div>
							</c:if>
						</div>
						<div class="f_r" style="position: relative;">
							<a href="javascript:showmoremenu()"><hk:data key="view2.more_action"/><span style="color: #000;font-size: 12px;">▼</span></a>
							<div id="user_menu" onmouseover="cleartime(event)" onmouseout="settime(event)" class="user_menu">
								<c:if test="${hasfriend}">
									<a href="javascript:delfriend()"><hk:data key="view2.delfriend2"/></a>
								</c:if>
								<c:if test="${!blockedUser}">
									<a class="line" href="javascript:blockuser()"><hk:data key="view2.block"/></a>
								</c:if>
								<c:if test="${!hasfriend && blockedUser}">
									<a class="line" href="javascript:unblockuser()"><hk:data key="view2.unblock"/></a>
								</c:if>
								<div style="position: relative;padding-top: 20px">
									<a href="javascript:hidemoremenu()" style="position: absolute;right: 5px;bottom: 0px;">x</a>
								</div>
							</div>
						</div>
						<div class="clr"></div>
					</div>
				</c:if>
			</div>
			<div class="clr"></div>
			<div>
			<div style="padding-top: 10px;">
				<div class="f_l">
					<ul class="user_num">
						<li class="rightline">
							<div><a class="b" href="/user/${userId }/friend"><hk:data key="view2.user.friends"/></a></div>
							<a href="/user/${userId }/friend">${user.friendCount }</a>
						</li>
						<li class="rightline" style="padding-left: 10px;">
							<div><a class="b" href="/user/${userId }/fans"><hk:data key="view2.user.fans"/></a></div>
							<a href="/user/${userId }/fans">${user.fansCount }</a>
						</li>
						<li style="padding-left: 10px;">
							<div><a class="b" href="/overview#points"><hk:data key="veiw2.userpoints"/></a></div>
							<a href="/overview#points">${userOtherInfo.points }</a>
						</li>
					</ul>
				</div>
				<div class="clr"></div>
			</div>
			</div>
		</div>
		<div class="f_r" style="width: 560px">
			<div class="f_r">
				<div class="statbox">
					<div class="title">
						<hk:data key="view2.total_things_done"/>
					</div>
					<div class="content">${doneCount }</div>
				</div>
				<div class="statbox">
					<div class="title"><hk:data key="view2.checkin.total"/></div>
					<div class="content">${checkInCount }</div>
				</div>
				<div class="statbox">
					<div class="title"><hk:data key="view2.checkin.nigthtotal"/></div>
					<div class="content">${nightCheckInCount }</div>
				</div>
			</div>
			<div class="clr"></div>
		</div>
		<div class="clr"></div>
	</div>
	<div class="b_l">
		<div id="do" class="active_tips_tab"><a href="javascript:loaddone(getObj('do'))"><hk:data key="view2.last"/></a></div>
		<div id="todo" class="inactive_tips_tab"><a href="javascript:loadtodo(getObj('todo'))"><hk:data key="view2.todo"/></a></div>
		<div id="laba" class="inactive_tips_tab"><a href="javascript:loadlaba(getObj('laba'))"><hk:data key="view2.laba"/></a></div>
		<div class="clr"></div>
		<div id="listbox" class="listbox">
			<c:if test="${fn:length(cmptipvolist)==0}"><hk:data key="view2.no_tips_in_this_place"/></c:if>
			<%request.setAttribute("user_view",true); %>
			<jsp:include page="../inc/cmptipvolist_inc.jsp"></jsp:include>
			<c:if test="${more_done}">
			<div>
				<a href="/user/${userId }/done" class="more2"><hk:data key="view2.more"/></a>
			</div>
			</c:if>
		</div>
	</div>
	<div class="b_r">
		<c:if test="${fn:length(userbadgelist)>0}">
			<div class="mod">
				<div class="mod_title">
					<a href="/userbadge/${firstbadge }"><hk:data key="view2.badge"/>(${hadge_count })</a>
				</div>
				<div class="mod_content">
					<table class="badge_grid">
						<tr>
							<td>
								<c:forEach var="b" items="${userbadgelist}">
								<a href="/userbadge/${b.oid }"><img title="${b.name }:${b.intro}" src="${b.pic57 }" alt="${b.name }" /></a>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</c:if>
		<c:if test="${fn:length(mayorlist)>0}">
			<div class="mod">
				<div class="mod_title"><hk:data key="view2.majorship"/></div>
				<div class="mod_content">
					<c:forEach var="mj" items="${mayorlist}">
					<div>
						<a href="/venue/${mj.companyId }/" class="b split-r">${mj.company.name }</a>
						<c:if test="${loginUser.userId==userId}"><a class="ruo2" href="javascript:delmayor(${mj.mayorId })">X</a></c:if>
					</div>
					</c:forEach>
				</div>
			</div>
		</c:if>
		<c:if test="${fn:length(checkinloglist)>0}">
			<div class="mod">
				<div class="mod_title">
					<hk:data key="view2.user.checkin.track"/>
				</div>
				<div class="mod_content">
					<c:forEach var="log" items="${checkinloglist}">
					<div class="ul">
						<img width="15" height="15" src="<%=path %>/webst4/img/icon_feed_checkin.png" />
						<a href="/venue/${log.companyId }/" class="b">${log.company.name }</a>
						<span class="ruo">(<hk:time value="${log.createTime}"/>)</span>
					</div>
					</c:forEach>
				</div>
			</div>
		</c:if>
		<div class="mod">
			<div class="mod_title">
				<a href="/user/${userId }/friend"><hk:data key="view2.friend"/></a>
				<c:if test="${morefriend}"><a class="more" href="/${userId }/friend"><hk:data key="view2.more"/></a></c:if>
			</div>
			<div class="mod_content">
				<c:if test="${fn:length(frienduserlist)==0}">
					<hk:data key="view2.nofriend"/>
				</c:if>
				<c:if test="${fn:length(frienduserlist)>0}">
					<ul class="smallhead">
					<c:forEach var="follow" items="${frienduserlist}">
					<li><a href="/user/${follow.userId }/"><img width="30" height="30" title="${follow.nickName }" src="${follow.head32Pic }" alt="${follow.nickName }" /></a></li>
					</c:forEach>
					</ul>
					<div class="clr"></div>
				</c:if>
			</div>
		</div>
	</div>
	<div class="clr"></div>
<script type="text/javascript">
function loaddone(o){
	if(o.className=='active_tips_tab'){
		return;
	}
	o.className='active_tips_tab';
	getObj('todo').className="inactive_tips_tab";
	getObj('laba').className="inactive_tips_tab";
	setHtml('listbox','<hk:data key="view2.data_loading"/>');
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/user_dolist.do?userId=${userId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('listbox',data);
		}
	});
}
function loadtodo(o){
	if(o.className=='active_tips_tab'){
		return;
	}
	o.className='active_tips_tab';
	getObj('do').className="inactive_tips_tab";
	getObj('laba').className="inactive_tips_tab";
	setHtml('listbox','<hk:data key="view2.data_loading"/>');
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/user_todolist.do?userId=${userId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('listbox',data);
		}
	});
}
function loadlaba(o){
	if(o.className=='active_tips_tab'){
		return;
	}
	o.className='active_tips_tab';
	getObj('do').className="inactive_tips_tab";
	getObj('todo').className="inactive_tips_tab";
	setHtml('listbox','<hk:data key="view2.data_loading"/>');
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/user_laba.do?userId=${userId}",
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('listbox',data);
		}
	});
}
function uaddfriend(){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/op/user_createfriend.do?userId=${userId}",
		cache:false,
    	dataType:"html",
		success:function(data){
		refreshurl();
		}
	});
}
function delfriend(){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/op/user_deletefriend.do?userId=${userId}",
		cache:false,
    	dataType:"html",
		success:function(data){
		refreshurl();
		}
	});
}
function blockuser(){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/op/user_block.do?userId=${userId}",
		cache:false,
    	dataType:"html",
		success:function(data){
		refreshurl();
		}
	});
}
function unblockuser(){
	$.ajax({
		type:"POST",
		url:"<%=path%>/h4/op/user_unblock.do?userId=${userId}",
		cache:false,
    	dataType:"html",
		success:function(data){
		refreshurl();
		}
	});
}
//opera 不知弹出窗口的textarea?
function showmsgwin(){
	var html='<div id="msghtml"></div>';
	createSimpleCenterWindow('msgwin',420, 300, "发送私信", html,"hideWindow('msgwin')");
	setHtml('msghtml','<form target="hideframe" id="msgfrm" method="post" onsubmit="return submsgfrm(this.id)" action="<%=path %>/msg/send_sendweb2.do"><input type="hidden" name="receiverId" value="${userId}"/><table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td> <div class="f_l"> <textarea id="_msgcontent" onkeydown="keydown(event)" name="msg" style="width:350px;height: 100px;margin:0 0 20px 0;padding:0;display:block; "></textarea> <div id="msg_msg_error" class="infowarn"></div> </div><div class="clr"></div> </td></tr> <tr> <td> <div style="text-align: center"> <input type="submit" value="发送" class="btn"/> </div> </td> </tr> </table></form>');
	getObj('msgfrm').msg.focus();
}
function keydown(event){
	var currKey=0,e=e||event;
	currKey=e.keyCode||e.which||e.charCode;//支持IE、FF
	if((e.ctrlKey)&&(currKey==13)){
		if(submsgfrm("msgfrm")){
			getObj("msgfrm").submit();
		}
	}
}
function submsgfrm(frmid){
	showGlass(frmid);
	return true;
}
function msgok(error,error_msg,v){
	hideGlass();
	setHtml("msgwin_content",'<strong class="text_16 green">私信发送成功</strong>');
	delay('hideWindow("msgwin")',3000);
}
function msgerror(error,error_msg,v){
	setHtml("msg_msg_error",error_msg);
	hideGlass();
}
function delmayor(id){
	if(window.confirm('确实要删除？')){
		$.ajax({
			type:"POST",
			url:"<%=path%>/h4/op/user/venue_delmayor.do?mayorId="+id,
			cache:false,
	    	dataType:"html",
			success:function(data){
				refreshurl();
			}
		});
	}
}
var time_id='';
function showmoremenu(){
	getObj('user_menu').style.display='block';
	clearInterval(time_id);
}
function hidemoremenu(){
	getObj('user_menu').style.display='none';
	clearInterval(time_id);
}
function cleartime(event){
	clearInterval(time_id);
}
function settime(event){
	time_id=setInterval('hidemoremenu()',500);
}
</script>
<script type="text/javascript">
var view2_add_as_todo="<hk:data key="view2.add_as_todo"/>";
var view2_i_done_this2="<hk:data key="view2.i_done_this2"/>";
var view2_i_done_this="<hk:data key="view2.i_done_this"/>";
</script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>