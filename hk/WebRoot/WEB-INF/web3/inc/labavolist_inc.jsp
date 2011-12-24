<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.hk.web.util.JspDataUtil"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="iteraotrlaba">
	<c:forEach var="vo" items="${labavolist}">
		<c:set var="laba_id" value="${vo.laba.labaId}" />
		<c:set var="laba_url"><%=path %>/laba.do?labaId=${vo.laba.labaId }</c:set>
		<li id="laba${laba_id }" onmouseout="this.className='';" onmouseover="this.className='show';">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<c:if test="${labavo_not_show_head==null || !labavo_not_show_head}">
						<td class="head"><a href="<%=path%>/home_web.do?userId=${vo.laba.userId }"><img src="${vo.laba.user.head48Pic }" /></a></td>
					</c:if>
					<td><input id="show_labacmt${laba_id }" type="hidden" value="0"/>
						<c:set var="innerhtml">
							<c:if test="${not empty vo.laba.longContent}"><a class="line dot" href="${laba_url}">...</a></c:if> 
							<a class="text_12" href="javascript:void(0)" onclick="showcmt(${laba_id })"><c:if test="${vo.laba.replyCount>0}">${vo.laba.replyCount}评论</c:if></a>
						</c:set>
						<c:if test="${not empty vo.mainContent}">
							<div>${vo.mainContent } ${innerhtml}</div>
							<div class="text_12">${vo.refContent }</div>
						</c:if> 
						<c:if test="${empty vo.mainContent}">${vo.content }	${innerhtml}</c:if> 
						<div class="ruo">
							<c:if test="${vo.laba.createAtToday}">
								<c:set var="laba_createtime" scope="request" value="${vo.laba.createTime}" />
								<a target="_blank" href="${laba_url }" class="ruo"><%=JspDataUtil.outLabaTime(request,"laba_createtime")%></a>
							</c:if>
							<c:if test="${!vo.laba.createAtToday}">
								<a target="_blank" href="${laba_url }" class="ruo"><fmt:formatDate value="${vo.laba.createTime}" pattern="yy-MM-dd HH:mm" /></a>
							</c:if>
						</div>
						<div id="booter${laba_id }" class="booter">
							<div id="refdesc${laba_id }" class="f_l ruo"><c:if test="${vo.refed}">你已经转发过该喇叭</c:if></div>
							<div class="f_r">
								<div class="action">
									<c:if test="${vo.laba.userId==loginUser.userId}">
										<a href="javascript:dellaba(${laba_id })" class="delete split-r">删除</a>
									</c:if>
									<span id="reply${laba_id }" class="split-r"><a href="javascript:void(0)" onclick="showcmt(${laba_id })" class="reply">评论</a></span>
									<c:if test="${!vo.refed}">
										<span id="rt${laba_id }" class="split-r"><a href="javascript:reflaba(${laba_id })" class="rt">转发</a></span>
									</c:if>
									<c:if test="${vo.refed}">
										<span id="rt${laba_id }" class="split-r"><a href="javascript:delreflaba(${laba_id })" class="rt">取消转发</a></span>
									</c:if>
								</div>
							</div>
							<div class="clr"></div>
						</div>
						<span id="labacmtlist${laba_id }"></span>
					</td>
					<td width="40px" valign="top">
						<span class="action2">
							<c:if test="${vo.fav}">
								<a id="fav${laba_id }" class="fav favok" href="javascript:delfavlaba(${laba_id })"></a>
							</c:if>
							<c:if test="${!vo.fav}">
								<a id="fav${laba_id }" class="fav" href="javascript:favlaba(${laba_id })"></a>
							</c:if>
						</span>
					</td>
				</tr>
			</table>
		</li>
	</c:forEach>
</c:set>
<ul class="labalist">
	<c:if test="${fn:length(labavolist)>0}">${iteraotrlaba }</c:if>
</ul>
	<jsp:include page="../inc/pagesupport_inc2.jsp"></jsp:include>
	<script type="text/javascript">
function favlaba(labaId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/laba/op/op_favweb.do?labaId="+labaId,
		cache:false,
		dataType:"html",
		success:function(data){
			getObj("fav"+labaId).className="fav favok";
			getObj("fav"+labaId).href="javascript:delfavlaba("+labaId+")";
		},
		error:function(data){
			alert("操作出现错误");
		}
	});
}
function delfavlaba(labaId){
	$.ajax({
		type:"POST",
		url:"<%=path%>/laba/op/op_delfavweb.do?labaId="+labaId,
		cache:false,
		dataType:"html",
		success:function(data){
			getObj("fav"+labaId).className="fav";
			getObj("fav"+labaId).href="javascript:favlaba("+labaId+")";
		},
		error:function(data){
			alert("操作出现错误");
		}
	});
}
function dellaba(labaId){
	var h=getHtml("labaaction"+labaId);
	setopt(labaId);
	setHtml("labaaction"+labaId,'<li>操作提交中 ... ...</li>');
	$.ajax({
		type:"POST",
		url:"<%=path%>/laba/op/op_delweb.do?labaId="+labaId,
		cache:false,
		dataType:"html",
		success:function(data){
			delObj("laba"+labaId);
		},
		error:function(data){
			alert("操作出现错误");
		}
	});
}
function setopt(labaId){
	getObj("laba"+labaId).className="show";
	getObj("laba"+labaId).onmouseover="";
	getObj("laba"+labaId).onmouseout="";
}
function clearopt(labaId){
	getObj("laba"+labaId).className="show";
	getObj("laba"+labaId).onmouseout=lionmouseout;
	getObj("laba"+labaId).onmouseover=lionmouseover;
}
function lionmouseout(obj){
	this.className='';
}
function lionmouseover(obj){
	this.className='show';
}
function reflaba(labaId){
	setopt(labaId);
	showSubmitDiv('laba'+labaId);
	setopt(labaId);
	$.ajax({
		type:"POST",
		url:"<%=path%>/laba/op/op_refweb.do?labaId="+labaId,
		cache:false,
		dataType:"html",
		success:function(data){
			clearopt(labaId);
			setHtml("rt"+labaId,'<a class="delrt" href="javascript:delreflaba('+labaId+')">取消转发</a>');
			setHtml('refdesc'+labaId,'你已经成功转发该喇叭');
			hideSubmitDiv();
		},
		error:function(data){
			alert("操作出现错误");
			clearopt(labaId);
			hideSubmitDiv();
		}
	});
}
function delreflaba(labaId){
	setopt(labaId);
	showSubmitDiv('laba'+labaId);
	$.ajax({
		type:"POST",
		url:"<%=path%>/laba/op/op_delrefweb.do?labaId="+labaId,
		cache:false,
		dataType:"html",
		success:function(data){
			clearopt(labaId);
			setHtml("rt"+labaId,'<a class="rt" href="javascript:reflaba('+labaId+')">转发</a>');
			setHtml('refdesc'+labaId,'');
			hideSubmitDiv();
		},
		error:function(data){
			alert("操作出现错误");
			clearopt(labaId);
			hideSubmitDiv();
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
		url:"<%=path%>/laba_loadlabacmtlist.do?labaId="+labaId,
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
	showSubmitDiv(frmid);
	return true;
}
function loadnewcmt(labaId,html){
	insertObjBefore(html,'labacmtcon');
	getObj('labacmtfrm'+labaId).content.value='';
	getObj('labacmtfrm'+labaId).replyCmtId.value='0';
	getObj('labacmtfrm'+labaId).cmtandlaba.checked=false;
	hideSubmitDiv();
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
</script>