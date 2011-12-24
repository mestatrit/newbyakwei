<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view.laba.title"/></c:set>
<c:set var="body_hk_content" scope="request">
	<table class="frame-3" cellpadding="0" cellspacing="0">
		<tr>
			<td class="l">
				<jsp:include page="../inc/userleftnav_inc.jsp"></jsp:include>
			</td>
			<td class="mid">
				<div class="mid_con">
					<div class="mod">
						<c:set var="nav_2_short_content" scope="request">
							<a href="<%=path%>/laba_userlaba.do?userId=${user.userId }" class="nav-a"><hk:data key="view.user.laba.title" arg0="${user.nickName}"/></a>
						</c:set>
						<jsp:include page="../inc/nav-2-short.jsp"></jsp:include>
					</div>
					<div class="mod">
						<c:if test="${labaVo.laba.userId!=loginUser.userId}">
						<div class="labaform">
							<div class="text_14">
								<hk:data key="view.laba.replytouser" arg0="${labaVo.laba.user.nickName}"/>
							</div>
							<form id="labafrm" method="post" onsubmit="return sublaba2(this.id)" action="<%=path %>/laba/op/op_createweb.do" target="hideframe">
								<textarea name="content" class="text_laba"></textarea><br />
								<div class="error" id="msg2_error"></div>
								<hk:hide name="labaId" value="${labaId}"/>
								<hk:hide name="labatype" value="2"/>
								<input type="submit" name="reply" value="评论" class="btn" />
								<input type="submit" name="via" value="评论并转发" class="btn" />
								<c:if test="${friend}">
									<input type="button" name="pvtmsg" value="私信" class="btn" onclick="sendmsg()" />
								</c:if>
							</form>
						</div>
						</c:if>
						<div class="mainlaba">
							<c:set var="labauser_url"><%=path%>/home_web.do?userId=${labaVo.laba.userId}</c:set>
							<div class="head"><a href="${labauser_url }"><img src="${labaVo.laba.user.head48Pic }" /></a></div>
							<div class="laba-body">
								<a href="${labauser_url }">${labaVo.laba.user.nickName }</a> <fmt:formatDate value="${labaVo.laba.createTime}" pattern="yy-MM-dd HH:mm"/>
								<span id="fav2${labaId }">
									<c:if test="${fav}"><a href="javascript:favlaba2(${labaId })">收藏</a></c:if>
									<c:if test="${!fav}"><a href="javascript:delfavlaba2(${labaId })">取消收藏</a></c:if>
								</span>
								<br />
								<c:set var="innerhtml">
									<c:if test="${pink}"><span class="s">(<hk:data key="view.pink"/>)</span> </c:if> 
									<c:if test="${labaVo.laba.replyCount>0}">${labaVo.laba.replyCount}个评论</c:if>
									<c:if test="${labaVo.laba.refcount>0}"><span class="text_12">${labaVo.laba.refcount}个转发</span></c:if>
								</c:set>
								<c:if test="${empty labaVo.longContent}">
									<c:if test="${empty labaVo.mainContent}">${labaVo.content } ${innerhtml }</c:if>
									<c:if test="${not empty labaVo.mainContent}"><div>${labaVo.mainContent }</div><div class="refcon">${labaVo.refContent } ${innerhtml }</div></c:if>
								</c:if> 
								<c:if test="${not empty labaVo.longContent}">
									<c:if test="${empty labaVo.mainLongContent}">${labaVo.longContent } ${innerhtml }</c:if>
									<c:if test="${not empty labaVo.mainLongContent}"><div>${labaVo.mainLongContent }</div><div class="refcon">${labaVo.refLongContent } ${innerhtml }</div></c:if>
								</c:if>
									<span id="taglist" class="text_12">
										<c:if test="${fn:length(taglist2)>0}">
											<br/>
											<hk:rmstr value="|">
												<c:forEach var="t" items="${taglist2}"><span id="tag${t.tagId }"><a href="<%=path %>/laba_taglaba.do?tagId=${t.tagId}" class="udline">${t.name}</a><c:if test="${labaVo.laba.userId==loginUser.userId || t.userId==loginUser.userId}"> <a href="javascript:deltag(${t.tagId })">x</a></c:if> | </span></c:forEach>
											</hk:rmstr>
										</c:if>
									</span> 
								<span id="tagopstat"></span>
								<span id="tagop">
									<c:if test="${canaddtag}"><br/>
										<form id="addtagfrm" onsubmit="return subaddtag(this.id)" method="post" target="hideframe" action="<%=path %>/laba/op/op_addtagweb.do">
											<hk:hide name="labaId" value="${labaId}"/>
											<hk:text name="name" size="10" clazz="text_short_1"/>
											<hk:submit value="添加频道" clazz="btn"/><br/>
											<div id="addtag_error" class="error"></div>
										</form>
									</c:if>
								</span>
							</div>
							<div class="clr"></div>
						</div>
						<c:set var="laba_url_add" scope="request">from=replylaba&sip=1&replylabaId=${labaId}</c:set>
						<c:if test="${fn:length(labacmtvolist)>0}">
							<h2 class="title">评论</h2>
<ul class="labacmtlist text_14">
	<c:forEach var="cmtvo" items="${labacmtvolist}">
	<li id="cmt${cmtvo.labaCmt.cmtId }" onmouseout="this.className='';" onmouseover="this.className='bg1';">
		<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td width="48px">
			<a href="<%=path %>/home_web.do?userId=${cmtvo.labaCmt.userId}"><img src="${cmtvo.labaCmt.user.head32Pic }" style="vertical-align: top;"/></a>
			</td>
			<td>
				<a href="<%=path %>/home_web.do?userId=${cmtvo.labaCmt.userId}">${cmtvo.labaCmt.user.nickName }</a>：<br/>
				${cmtvo.content } <span class="ruo s"><fmt:formatDate value="${cmtvo.labaCmt.createTime}" pattern="yy-MM-dd HH:mm"/></span>
				<div align="right">
				<c:if test="${laba.userId==loginUser.userId || cmtvo.labaCmt.userId==loginUser.userId}">
				<a class="func" href="javascript:dellabacmt(${cmtvo.labaCmt.cmtId })">删除</a>
				</c:if>
				<a class="func" href="javascript:replylabacmt(${labaId },${cmtvo.labaCmt.cmtId },'${cmtvo.labaCmt.user.nickName }')">回复</a>
				</div>
			</td>
		</tr>
		</table>
	</li>
	</c:forEach>
	<li id="labacmtcon">
		<hk:form oid="labacmtfrm${labaId}" onsubmit="return sublabacmtfrm(this.id)" action="/op/labacmt_createweb.do" target="hideframe">
			<input id="replyLabaId${labaId }" type="hidden" name="labaId" value="${labaId }"/>
			<input id="replyCmtId${labaId }" type="hidden" name="replyCmtId"/>
			<div>
				<textarea id="cmt_content${labaId }" name="content" onkeydown="checkSubCmt(event,'labacmtfrm${labaId}');" class="cmtipt"></textarea>
				<hk:submit clazz="btn" value="提交"/>
				<div class="clr"></div>
			</div>
			<input id="cmtandlaba${labaId }" type="checkbox" name="cmtandlaba" value="1"/><label class="pointer" for="cmtandlaba${labaId }">同时发一条喇叭</label>
		</hk:form>
	</li>
</ul>
<c:set var="page_url" scope="request"><%=path%>/laba.do?labaId=${labaId}</c:set>
<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
						</c:if>
						<%request.setAttribute("labavolist",request.getAttribute("rlabavolist")); %>
						<c:if test="${fn:length(labavolist)>0}">
							<h2 class="title"><hk:data key="view.laba.labareplylist"/></h2>
							<!-- 喇叭普通回应-->
							<jsp:include page="../inc/labavolist_inc.jsp"></jsp:include>
						</c:if>
						<br class="linefix"/>
					</div>
				</div>
			</td>
			<td class="r">
				<div class="f_r"></div>
			</td>
		</tr>
	</table>
	<hk:form oid="msgfrm" action="/msg/send_sendweb.do" target="hideframe" clazz="hide">
		<hk:hide name="receiverId" value="${labaVo.laba.userId}"/>
		<hk:hide name="msg" value=""/>
	</hk:form>
<script type="text/javascript">
function deltag(tagId){
	setHtml('tagopstat','信息提交中 ... ...');
	$.ajax({
		type:"POST",
		url:"<%=path%>/laba/op/op_deltagweb.do?labaId=${labaId}&tagId="+tagId+"&ajax=1",
		cache:false,
		dataType:"html",
		success:function(data){
			delObj('tag'+tagId);
			setHtml('tagopstat','');
		},
		error:function(data){
			alert("操作出现错误");
			setHtml('tagopstat','');
		}
	});
}
function subaddtag(frmid){
	validateClear('addtag');
	showSubmitDiv(frmid);
	return true;
}
function onaddtagerror(error,error_msg,op_func,obj_id_param){
	validateErr('addtag',error_msg);
	hideSubmitDiv();
}
function onaddtagsuccess(error,error_msg,op_func,obj_id_param,respValue){
	var tagId=respValue;
	var html='<span id="tag'+tagId+'"> | <a href="<%=path %>/laba_taglaba.do?tagId='+tagId+'" class="udline">'+getObj("addtagfrm").name.value+'</a><a href="javascript:deltag('+tagId+')">x</a></span>';
	appendObj(html,'taglist');
	hideSubmitDiv();
	getObj("addtagfrm").name.value="";
}
function sublaba2(frmid){
	showSubmitDiv(frmid);
	return true;
}
function sendmsg(){
	getObj('msgfrm').msg.value=getObj('labafrm').content.value;
	getObj('msgfrm').submit();
}
function afterSubmit(error,error_msg,op_func,obj_id_param){
	if(error==0){
		hideSubmitDiv();
		alert('私信发送成功');
	}
	else{
		validateErr('msg2',error_msg);
		hideSubmitDiv();
	}
}
function onlabaerror2(error,error_msg,op_func,obj_id_param){
	validateErr('msg2',error_msg);
	hideSubmitDiv();
}
function onlabasuccess2(error,error_msg,op_func,obj_id_param,respValue){
	tourl("<%=path%>/laba.do?${query_string}");
}
function favlaba2(labaId){
	setHtml('fav2'+labaId,'数据提交中 ... ...');
	$.ajax({
		type:"POST",
		url:"<%=path%>/laba/op/op_favweb.do?labaId="+labaId,
		cache:false,
		dataType:"html",
		success:function(data){
			setHtml("fav2"+labaId,'<a href="javascript:delfavlaba2('+labaId+')">取消收藏</a>');
		},
		error:function(data){
			alert("操作出现错误");
		}
	});
}
function delfavlaba2(labaId){
	setHtml('fav2'+labaId,'数据提交中 ... ...');
	$.ajax({
		type:"POST",
		url:"<%=path%>/laba/op/op_delfavweb.do?labaId="+labaId,
		cache:false,
		dataType:"html",
		success:function(data){
			setHtml("fav2"+labaId,'<a href="javascript:favlaba2('+labaId+')">收藏</a>');
		},
		error:function(data){
			alert("操作出现错误");
		}
	});
}
function onlabaerror(error,error_msg,op_func,obj_id_param){
	validateErr('msg',error_msg);
	hideSubmitDiv();
}
function onlabasuccess(error,error_msg,op_func,obj_id_param,respValue){
	setHtml('reply_win_content',"信息提交成功");
	hideSubmitDiv();
	delay("oklabaok()",2000);
}
function oklabaok(){
	hideWindow('reply_win');
	clearBg();
}
function checkSubCmt(event,frmid){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(sublabacmtfrm(frmid)){
			getObj(frmid).submit();
		}
	}
}
function sublabacmtfrm(frmid){
	showSubmitDiv(frmid);
	return true;
}
function labacmterror(error,error_msg,respValue){
	hideSubmitDiv();
	alert(error_msg);
}
function dellabacmt(cmtId){
	if(confirm("确实要删除？")){
		showSubmitDiv('cmt'+cmtId);
		$.ajax({
			type:"POST",
			url:"<%=path%>/op/labacmt_delweb.do?cmtId="+cmtId,
			cache:false,
			dataType:"html",
			success:function(data){
				delObj("cmt"+cmtId);
				hideSubmitDiv();
			},
			error:function(data){
				alert("系统出现错误");
				hideSubmitDiv();
			}
		});
	}
}
function replylabacmt(labaId,cmtId,nickName){
	getObj('labacmtfrm'+labaId).replyCmtId=cmtId;
	getObj('labacmtfrm'+labaId).content.value='回复@'+nickName+':';
	getObj('labacmtfrm'+labaId).content.focus();
}
function sharecmtok(error,error_msg,respValue){
	hideSubmitDiv();
	var html='<li><table cellpadding="0" cellspacing="0"><tr><td>转发评论成功</td></tr></table></li>';
	insertObjBefore(html,'labacmtcon');
}
function loadnewcmt(labaId,html){
	insertObjBefore(html,'labacmtcon');
	getObj('labacmtfrm'+labaId).content.value='';
	getObj('labacmtfrm'+labaId).replyCmtId.value='0';
	getObj('labacmtfrm'+labaId).cmtandlaba.checked=false;
	hideSubmitDiv();
}
</script>
</c:set>
<jsp:include page="../inc/frame.jsp"></jsp:include>