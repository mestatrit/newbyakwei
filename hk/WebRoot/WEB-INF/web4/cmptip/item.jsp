<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.bean.User"%><%@page import="com.hk.svr.pub.Err"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<%String path = request.getContextPath();%>
<c:set var="html_title" scope="request"><hk:data key="view2.website.title"/></c:set>
<c:set var="html_body_content" scope="request">
<div class="hcenter" style="width: 700px;">
	<div class="f_l">
		<a href="/user/${vo.cmpTip.userId }/"><img width="75" height="75" alt="${vo.cmpTip.user.nickName }" title="${vo.cmpTip.user.nickName }" src="${vo.cmpTip.user.head80Pic }"/></a>
	</div>
	<div class="f_l">
		<h1 style="display: inline;"><a href="/user/${vo.cmpTip.userId }/">${vo.cmpTip.user.nickName }</a>
		<c:if test="${vo.cmpTip.toDo}"><hk:data key="view2.wantto"/></c:if>
		<c:if test="${vo.cmpTip.done}"><hk:data key="view2.didthis"/></c:if>
		</h1>
	</div>
		<div class="f_r">
			<div class="action">
				<c:if test="${vo.cmpTip.userId!=loginUser.userId}">
					<div id="tipaction${vo.cmpTip.tipId }">
						<c:if test="${vo.addDone}">
							<div class="tip_checked">
							<input id="tip_done_${vo.cmpTip.tipId }" type="checkbox" onclick="changdonestatus(this,${vo.cmpTip.tipId})" checked="checked"/>
							 <hk:data key="view2.i_done_this"/>
							</div>
						</c:if>
						<c:if test="${!vo.addDone}">
							<div class="tip_done_unchecked">
							<input id="tip_done_${vo.cmpTip.tipId }" type="checkbox"  onclick="changdonestatus(this,${vo.cmpTip.tipId})"/>
							<hk:data key="view2.i_done_this"/>
							</div>
						</c:if>
						<c:if test="${!vo.addDone}">
							<div class="<c:if test="${vo.addToDo}">tip_checked</c:if><c:if test="${!vo.addToDo}">tip_todo_unchecked</c:if>">
							<input id="tip_todo${vo.cmpTip.tipId }" type="checkbox" onclick="changtodostatus(this,${vo.cmpTip.tipId})"<c:if test="${vo.addToDo}"> checked="checked"</c:if>/>
							<hk:data key="view2.add_as_todo"/>
							</div>
						</c:if>
					</div>
				</c:if>
				<c:if test="${vo.cmpTip.userId==loginUser.userId}"><a href="javascript:deletecmptip()"><hk:data key="view2.delete"/></a></c:if>
				<c:if test="${bomber.remainCount>0}">
					<div><a id="bombtip" href="javascript:bombtip()">炸掉</a></div>
				</c:if>
			</div>
		</div>
	<div class="clr"></div>
	<hr/>
	<div class="gray_content" style="font-weight: bold;">
	<a href="/venue/${vo.cmpTip.companyId }/">${company.name }</a>:${vo.cmpTip.content } <span class="s" style="font-weight: normal;">(<hk:time value="${vo.cmpTip.createTime}"/>)</span>
	<br/>
	<a class="more2" href="/venue/${vo.cmpTip.companyId}/"><hk:data key="view2.return"/></a>
	<br/>
	<br/>
	</div>
</div>
<script type="text/javascript">
var view2_add_as_todo="<hk:data key="view2.add_as_todo"/>";
var view2_i_done_this2="<hk:data key="view2.i_done_this2"/>";
var view2_i_done_this="<hk:data key="view2.i_done_this"/>";
<c:if test="${bomber.remainCount>0}">
function bombtip(){
	if(window.confirm("确实要炸掉？")){
		showObjGlass('bombtip');
		$.ajax({
			type:"POST",
			url:path+"/h4/op/user/venue_bombtip.do?tipId=${tipId}",
			cache:false,
	    	dataType:"html",
			success:function(data){
				tourl("/venue/${vo.cmpTip.companyId}");
			},
			error:function(data){
				alert('服务器错误，请稍后再试');
			}
		});
	}
}
</c:if>
</script>
<script type="text/javascript" language="javascript" src="<%=path%>/webst4/js/js4/tip.js"></script>
</c:set><jsp:include page="../inc/frame.jsp"></jsp:include>