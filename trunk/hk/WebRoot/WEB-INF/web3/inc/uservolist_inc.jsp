<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%><a name="list_top"></a>
<c:set var="iteratoruser">
<c:forEach var="uservo" items="${uservolist}">
	<li class="user" onmouseout="this.className='user';" onmouseover="this.className='user show';">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td class="head">
					<a href="<%=path %>/home_web.do?userId=${uservo.user.userId }"><img src="${uservo.user.head48Pic }" /></a>
				</td>
				<td class="laba">
					<span class="ruo">${uservo.user.zoneDescr } 
					<c:if test="${uservo.user.hasSex}"><hk:data key="userotherinfo.sex_${uservo.user.sex}"/></c:if>
					</span><br />
					<div class="labacon">
						<c:if test="${uservo.labaVo!=null}">
						${uservo.labaVo.content }<br />
						<span class="ruo"><fmt:formatDate value="${uservo.labaVo.laba.createTime}" pattern="yy-MM-dd HH:mm"/></span>
						</c:if>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="user_info">
						<a class="user_name" href="<%=path %>/home_web.do?userId=${uservo.user.userId }">${uservo.user.nickName }</a>
						<c:if test="${uservo.user.friendCount>0}"><a href="<%=path %>/friend.do?userId=${uservo.user.userId }"><hk:data key="view.user.friendcount" arg0="${uservo.user.friendCount}"/></a></c:if>
						<c:if test="${uservo.user.fansCount>0}"><a href="<%=path %>/followed.do?userId=${uservo.user.userId }"><hk:data key="view.user.fanscount" arg0="${uservo.user.fansCount}"/></a></c:if>
						<ul class="user-action" id="user-action${uservo.user.userId }">
							<li><span class="ruo" id="op_${uservo.user.userId }"></span></li>
							<li>
								<c:if test="${!uservo.follow}">
									<a class="follow" id="follow${uservo.user.userId }" href="javascript:addfriend(${uservo.user.userId})"><hk:data key="view.user.op.follow"/></a>
								</c:if>
								<c:if test="${uservo.follow}">
									<a class="follow" id="follow${uservo.user.userId }" href="javascript:rmfriend(${uservo.user.userId})"><hk:data key="view.user.op.unfollow"/></a>
								</c:if>
							</li>
							<c:if test="${uservo.followme}">
							<li id="msgli${uservo.user.userId }">
								<a class="msg" id="msg${uservo.user.userId }" href="javascript:showmsgwin(${uservo.user.userId})"><hk:data key="view.user.op.pvtmsg"/></a>
							</li>
							</c:if>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</li>
</c:forEach>
</c:set>
	<ul id="userlist" class="userlist3">
		<c:if test="${iteratoruser!=null }">${iteratoruser }</c:if>
	</ul>
	<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
<script type="text/javascript">
function showmsgwin(userId){
	var html='<form target="hideframe" id="msg_frm" method="post" onsubmit="return checksend(this.id)" action="<%=path %>/msg/send_sendweb.do"><input type="hidden" name="receiverId" value="'+userId+'"/><table class="infotable" cellpadding="0" cellspacing="0"> <tr> <td> <div class="f_l"> <textarea onkeydown="onmsgkeydown(event)" name="msg" style="width:350px;height: 100px "></textarea> <div id="msg_msg_error" class="error"></div> </div> <div id="msg_msg_flag" class="flag"></div> <div class="clr"></div> </td></tr> <tr> <td> <div style="text-align: center"> <input type="submit" value="发送" class="btn"/> </div> </td> </tr> </table></form>';
	createCenterWindow('msgwin',420, 295, "发送私信", html,"hideWindow('msgwin')");
}
function checksend(frmid){
	showSubmitDiv(frmid);
	return true;
}
function onmsgkeydown(event){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(checksend("msg_frm")){
			getObj("msg_frm").submit();
		}
	}
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error==0){
		hideSubmitDiv();
		setHtml("msgwin_content",'<strong class="text_16 green">私信发送成功</strong>');
		delay('hideWindow("msgwin")',2000);
	}
	else{
		setHtml("msg_msg_error",error_msg);
		hideSubmitDiv();
	}
}
function addfriend(userId){
	var h=getHtml('user-action'+userId);
	var action_url='<%=path%>/follow/op/op_addweb.do?userId='+userId;
	setHtml('user-action'+userId,'数据提交中 ... ...');
	$.ajax({
		type:"POST",
		url:action_url,
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('user-action'+userId,h);
			getObj('follow'+userId).href="javascript:rmfriend("+userId+")";
			setHtml('follow'+userId,'取消关注');
			initmsg(data,userId);
		}
	});
}
function rmfriend(userId){
	var h=getHtml('user-action'+userId);
	var action_url='<%=path%>/follow/op/op_delweb.do?userId='+userId;
	setHtml('user-action'+userId,'数据提交中 ... ...');
	$.ajax({
		type:"POST",
		url:action_url,
		cache:false,
    	dataType:"html",
		success:function(data){
			setHtml('user-action'+userId,h);
			getObj('follow'+userId).href="javascript:addfriend("+userId+")";
			setHtml('follow'+userId,'关注');
			initmsg(data,userId);
		}
	});
}
function initmsg(data,userId){
	if(data==-1){//可以发私信
		if(getObj('msgli'+userId)==null){
			appendObj('user-action'+userId,'<li id="msgli'+userId+'"><a class="msg'+userId+'" href="javascript:showmsgwin("+userId+")">私信</a></li>');
		}
	}
	else{//不能发私信
		delObj('msgli'+userId);
	}
}
</script>