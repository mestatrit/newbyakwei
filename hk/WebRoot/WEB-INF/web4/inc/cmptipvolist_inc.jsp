<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.svr.ZoneService"%><%@page import="com.hk.frame.util.HkUtil"%><%@page import="com.hk.svr.pub.ZoneUtil"%><%@page import="java.util.List"%><%@page import="com.hk.bean.Country"%><%@page import="com.hk.bean.Province"%><%@page import="com.hk.bean.City"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%>
<c:if test="${fn:length(cmptipvolist)>0}">
	<c:forEach var="vo" items="${cmptipvolist}" varStatus="idx">
		<c:set var="onmouseover"><c:if test="${idx.index%2!=0}">this.className='block bg1 bg2'</c:if><c:if test="${idx.index%2==0}">this.className='block bg2'</c:if></c:set>
		<c:set var="onmouseout"><c:if test="${idx.index%2!=0}">this.className='block bg1'</c:if><c:if test="${idx.index%2==0}">this.className='block'</c:if></c:set>
		<div onmouseover="${onmouseover}" onmouseout="${onmouseout}" class="block <c:if test="${idx.index%2!=0}">bg1</c:if> <c:if test="${force_me && loginUser.userId==vo.cmpTip.userId}">bg2</c:if>">
			<c:if test="${user_view}">
				<div class="num">${idx.index+1 }.</div>
			</c:if>
			<c:if test="${!user_view }">
				<div class="pic">
					<a href="/user/${vo.cmpTip.userId }/"><img title="${vo.cmpTip.user.nickName }" alt="${vo.cmpTip.user.nickName }" src="${vo.cmpTip.user.head32Pic }" /></a>
				</div>
			</c:if>
			<div class="action">
				<c:if test="${vo.cmpTip.userId!=loginUser.userId}">
					<div id="tipaction${vo.cmpTip.tipId }">
						<c:if test="${vo.addDone}">
							<div class="tip_checked">
							<input style="margin: 0;padding: 0;" id="tip_done_${vo.cmpTip.tipId }" type="checkbox" onclick="changdonestatus(this,${vo.cmpTip.tipId})" checked="checked"/><label for="tip_done_${vo.cmpTip.tipId }"><hk:data key="view2.i_done_this2"/></label>
							</div>
						</c:if>
						<c:if test="${!vo.addDone}">
							<div class="tip_done_unchecked">
							<c:set var="done_data"><c:if test="${vo.addToDo}"><hk:data key="view2.i_done_this2"/></c:if><c:if test="${!vo.addToDo}"><hk:data key="view2.i_done_this"/></c:if></c:set>
							<input style="margin: 0;padding: 0;" id="tip_done_${vo.cmpTip.tipId }" type="checkbox"  onclick="changdonestatus(this,${vo.cmpTip.tipId})"/><label for="tip_done_${vo.cmpTip.tipId }">${done_data }</label>
							</div>
						</c:if>
						<c:if test="${!vo.addDone}">
							<div class="<c:if test="${vo.addToDo}">tip_checked</c:if><c:if test="${!vo.addToDo}">tip_todo_unchecked</c:if>">
							<input style="margin: 0;padding: 0;" id="tip_todo${vo.cmpTip.tipId }" type="checkbox" onclick="changtodostatus(this,${vo.cmpTip.tipId})"<c:if test="${vo.addToDo}"> checked="checked"</c:if>/><label for="tip_todo${vo.cmpTip.tipId }"><hk:data key="view2.add_as_todo"/></label>
							</div>
						</c:if>
					</div>
				</c:if>
			</div>
			<div class="content">
				<c:if test="${user_view}">
					<a class="b" href="/venue/${vo.cmpTip.companyId }/">${vo.cmpTip.company.name }</a>: 
				</c:if>
				<c:if test="${!user_view}">
					<a class="b" href="/user/${vo.cmpTip.userId }/">${vo.cmpTip.user.nickName }</a>
					<c:if test="${vo.cmpTip.toDo}"><hk:data key="view2.wantto"/></c:if>
					<c:if test="${vo.cmpTip.done}"><hk:data key="view2.didthis"/></c:if>
					<a class="b" href="/venue/${vo.cmpTip.companyId }/">${vo.cmpTip.company.name }</a>
					<br/>
				</c:if>
				${vo.cmpTip.content } <a class="s ruo" href="/item/${vo.cmpTip.tipId }"><hk:time value="${vo.cmpTip.createTime}"/></a>
				<c:if test="${vo.cmpTip.userId==loginUser.userId}">
					<span class="ruo">。( 
						<a class="s ruo" id="delcmptip${vo.cmpTip.tipId }" href="javascript:deletecmptip(${vo.cmpTip.tipId })"><hk:data key="view2.delete"/></a>
						| <a class="s ruo" id="delcmptip${vo.cmpTip.tipId }" href="javascript:toedittip(${vo.cmpTip.tipId })"><hk:data key="view2.edit"/></a> 
						)
					</span>
				</c:if>
			</div>
			<div class="clr"></div>
		</div>
	</c:forEach>
</c:if>