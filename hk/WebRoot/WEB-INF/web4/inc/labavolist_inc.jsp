<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<ul class="labalist">
	<c:forEach var="vo" items="${labavolist}" varStatus="idx">
		<c:set var="laba_id" value="${vo.laba.labaId}" />
		<c:set var="laba_url">/laba/${vo.laba.labaId }</c:set>
		<li id="laba${laba_id }" onmouseout="this.className='';" onmouseover="this.className='mouse_over'">
			<div class="status"><input id="show_labacmt${laba_id }" type="hidden" value="0"/>
			<input type="hidden" id="labacmtcount${laba_id }" value="${vo.laba.replyCount }"/>
				<div class="user">
					<a href="/user/${vo.laba.userId }/"><img src="${vo.laba.user.head48Pic }"/><br/>
					${vo.laba.user.nickName }</a>
				</div>
				<div class="laba-body"><a name="laba_hide_pos${laba_id }"></a>
					<c:set var="innerhtml">
						<c:if test="${not empty vo.laba.longContent}"><a class="line dot" href="${laba_url}">...</a></c:if> 
					</c:set>
					<c:if test="${not empty vo.mainContent}">
						<div>${vo.mainContent } ${innerhtml}</div>
						<div class="s">${vo.refContent }</div>
					</c:if> 
					<c:if test="${empty vo.mainContent}">${vo.content }	${innerhtml}</c:if>
					<div class="ftp">
						<span class="f_l">
							<c:if test="${vo.laba.createAtToday}">
								<c:set var="laba_createtime" scope="request" value="${vo.laba.createTime}" />
								<a href="${laba_url }" class="ruo"><%=JspDataUtil.outLabaTime(request,"laba_createtime")%></a>
							</c:if>
							<c:if test="${!vo.laba.createAtToday}">
								<a href="${laba_url }" class="ruo"><fmt:formatDate value="${vo.laba.createTime}" pattern="yy-MM-dd HH:mm" /></a>
							</c:if>
						</span>
						<c:if test="${userLogin}">
							<span id="labaact_${laba_id }" class="act">
								<c:if test="${vo.laba.userId==loginUser.userId}"><a href="javascript:dellaba(${laba_id })" class="split-r">删除</a></c:if>
								<c:if test="${!show_reply_link}"><span id="reply${laba_id }" class="split-r"><a id="reply_link${laba_id }" class="s" href="javascript:void(0)" onclick="showcmt(${laba_id })">评论<c:if test="${vo.laba.replyCount>0}">(${vo.laba.replyCount})</c:if></a></span></c:if>
								<c:if test="${!vo.refed && loginUser.userId!=vo.laba.userId}"><span id="rt${laba_id }" class="split-r"><a href="javascript:reflaba(${laba_id })">转发<c:if test="${vo.laba.refcount>0}">(${vo.laba.refcount })</c:if></a></span></c:if>
								<c:if test="${vo.refed && loginUser.userId!=vo.laba.userId}"><span id="rt${laba_id }" class="split-r"><a href="javascript:delreflaba(${laba_id })">取消转发<c:if test="${vo.laba.refcount>0}">(${vo.laba.refcount })</c:if></a></span></c:if>
								<c:if test="${vo.fav}"><a id="fav${vo.laba.labaId }" href="javascript:delfavlaba(${vo.laba.labaId })">取消收藏</a></c:if>
								<c:if test="${!vo.fav}"><a id="fav${vo.laba.labaId }" href="javascript:favlaba(${vo.laba.labaId })">收藏</a></c:if>
							</span>
						</c:if>
						<div class="clr"></div>
					</div>
					<div id="labacmtlist${laba_id }"></div>
				</div>
				<div class="clr"></div>
			</div>
		</li>
	</c:forEach>
</ul>
<script type="text/javascript">
function favlaba(labaId){
	showGlass('laba'+labaId);
	$.ajax({
		type:"POST",
		url:"<%=path%>/laba/op/op_favweb.do?labaId="+labaId,
		cache:false,
		dataType:"html",
		success:function(data){
			getObj("fav"+labaId).href="javascript:delfavlaba("+labaId+")";
			setHtml('fav'+labaId,'取消收藏');
			hideGlass('laba'+labaId);
		},
		error:function(data){
			alert("操作出现错误");
		}
	});
}
function delfavlaba(labaId){
	showGlass('laba'+labaId);
	$.ajax({
		type:"POST",
		url:"<%=path%>/laba/op/op_delfavweb.do?labaId="+labaId,
		cache:false,
		dataType:"html",
		success:function(data){
			getObj("fav"+labaId).href="javascript:favlaba("+labaId+")";
			setHtml('fav'+labaId,'收藏');
			hideGlass('laba'+labaId);
		},
		error:function(data){
			alert("操作出现错误");
		}
	});
}
function dellaba(labaId){
	if(window.confirm('确实要删除?')){
		setopt(labaId);
		showGlass('laba'+labaId);
		$.ajax({
			type:"POST",
			url:"<%=path%>/laba/op/op_delweb.do?labaId="+labaId,
			cache:false,
			dataType:"html",
			success:function(data){
				delObj("laba"+labaId);
				hideGlass();
			},
			error:function(data){
				alert("操作出现错误");
			}
		});
	}
}
function setopt(labaId){
	getObj("laba"+labaId).className="bg2";
	getObj("laba"+labaId).onmouseover="";
	getObj("laba"+labaId).onmouseout="";
}
function clearopt(labaId){
	getObj("laba"+labaId).className="bg2";
	getObj("laba"+labaId).onmouseout=lionmouseout;
	getObj("laba"+labaId).onmouseover=lionmouseover;
}
function lionmouseout(obj){
	this.className='';
}
function lionmouseover(obj){
	this.className='bg2';
}
function reflaba(labaId){
	setopt(labaId);
	showGlass('laba'+labaId);
	setopt(labaId);
	$.ajax({
		type:"POST",
		url:"<%=path%>/laba/op/op_refweb.do?labaId="+labaId,
		cache:false,
		dataType:"html",
		success:function(data){
			clearopt(labaId);
			setHtml("rt"+labaId,'<a class="delrt" href="javascript:delreflaba('+labaId+')">取消转发</a>');
			//setHtml('refdesc'+labaId,'你已经成功转发该喇叭');
			hideGlass();
		},
		error:function(data){
			alert("操作出现错误");
			clearopt(labaId);
			hideGlass();
		}
	});
}
function delreflaba(labaId){
	setopt(labaId);
	showGlass('laba'+labaId);
	$.ajax({
		type:"POST",
		url:"<%=path%>/laba/op/op_delrefweb.do?labaId="+labaId,
		cache:false,
		dataType:"html",
		success:function(data){
			clearopt(labaId);
			setHtml("rt"+labaId,'<a class="rt" href="javascript:reflaba('+labaId+')">转发</a>');
			//setHtml('refdesc'+labaId,'');
			hideGlass();
		},
		error:function(data){
			alert("操作出现错误");
			clearopt(labaId);
			hideGlass();
		}
	});
}
function sublaba(frmid){
	showSubmitDiv(frmid);
	return true;
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
function showcmt(labaId){
	if(getObj('show_labacmt'+labaId).value=='1'){
		setHtml('labacmtlist'+labaId,'');
		getObj('show_labacmt'+labaId).value='0';
		return;
	}
	$.ajax({
		type:"POST",
		url:"<%=path%>/laba_loadlabacmtlist4.do?labaId="+labaId,
		cache:false,
		dataType:"html",
		success:function(data){
			setHtml('labacmtlist'+labaId,data);
			getObj('replyLabaId'+labaId).value=labaId;
			getObj('show_labacmt'+labaId).value=1;
		},
		error:function(data){
			alert("系统出现错误");
		}
	});
}
function checkSubCmt(event,frmid){
	if((event.ctrlKey)&&(event.keyCode==13)){
		if(sublabacmtfrm(frmid)){
			getObj(frmid).submit();
		}
	}
}
function sublabacmtfrm(frmid){
	showGlass(frmid);
	return true;
}
function loadnewcmt(labaId,html){
	insertObjAfter(html,'cmt_first'+labaId);
	getObj('labacmtfrm'+labaId).content.value='';
	getObj('labacmtfrm'+labaId).replyCmtId.value='0';
	getObj('labacmtfrm'+labaId).cmtandlaba.checked=false;
	hideSubmitDiv();
	window.location.hash="laba_hide_pos"+labaId;
	var c=parseInt(getObj('labacmtcount'+labaId).value)+1;
	getObj('labacmtcount'+labaId).value=c;
	setHtml('reply_link'+labaId,'评论('+c+')');
	delay("showcmt("+labaId+")", 2000);
}
function labacmterror(error,error_msg,respValue){
	hideSubmitDiv();
	alert(error_msg);
}
function dellabacmt(labaId,cmtId){
	if(confirm("确实要删除？")){
		showSubmitDiv('cmt'+cmtId);
		$.ajax({
			type:"POST",
			url:"<%=path%>/op/labacmt_delweb.do?cmtId="+cmtId,
			cache:false,
			dataType:"html",
			success:function(data){
				delObj("cmt"+cmtId);
				var c=parseInt(getObj('labacmtcount'+labaId).value)-1;
				getObj('labacmtcount'+labaId).value=c;
				if(c==0){
					setHtml('reply_link'+labaId,'评论');
				}
				else{
					setHtml('reply_link'+labaId,'评论('+c+')');
				}
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
	getObj('labacmtfrm'+labaId).replyCmtId.value=cmtId;
	getObj('labacmtfrm'+labaId).content.value='回复@'+nickName+':';
	getObj('labacmtfrm'+labaId).content.focus();
}
function sharecmtok(error,error_msg,respValue){
	hideSubmitDiv();
	//var html='<li><table cellpadding="0" cellspacing="0"><tr><td>转发评论成功</td></tr></table></li>';
	//insertObjBefore(html,'labacmtcon');
}
</script>