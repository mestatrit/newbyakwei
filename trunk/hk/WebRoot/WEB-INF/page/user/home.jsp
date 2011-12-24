<%@ page language="java" pageEncoding="UTF-8"%><%@page import="com.hk.web.util.HkWebConfig"%>
<%@page import="com.hk.web.util.JspDataUtil"%>
<%@page import="com.hk.web.util.HkWebUtil"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><%@ taglib uri="/WEB-INF/waphk.tld" prefix="hk"%><hk:wap title="${user.nickName }的首页 - 火酷" rm="false" bodyId="thepage">
	<jsp:include page="../inc/top.jsp"></jsp:include>
	<div>
	<table class="list"><tbody>
	<tr class="reply"><td>
	<hk:form method="post" action="/laba/op/op_create.do">
		<hk:hide name="userhome" value="1"/>
		<hk:hide name="userId" value="${userId}"/>
		<input type="hidden" name="lastUrl" value="/home.do?userId=${userId }"/>
		<c:if test="${!me && userLogin}">
			<hk:text name="content" clazz="ipt" maxlength="140" value="@${user.nickName} "/>
		</c:if>
		<c:if test="${me}">
			<hk:text name="content" clazz="ipt" maxlength="140"/>
		</c:if>
		<hk:submit value="提交" /> 
		<c:if test="${loginUser.userId!=userId && followed}">
			<hk:submit name="msg_submit" value="私信" />
			<c:if test="${canSms}"><hk:submit name="msgandsms_submit" value="短信" /></c:if>
		</c:if>
	</hk:form>
	<table>
		<tbody>
			<tr>
				<td><img src="${user.head48Pic }?${sessionScope.newhead }" alt="${user.nickName }" /></td>
				<td>
					${user.nickName } <hk:a href="/home_info.do?userId=${userId}">个人档案</hk:a><br/>
					<c:if test="${mayor_count>0}"><hk:a clazz="s" href="/home_mayor.do?userId=${userId}">地主(${mayor_count })</hk:a><br/></c:if>
					<c:if test="${hadge_count>0}"><hk:a clazz="s" href="/home_userbadge.do?userId=${userId}">徽章(${hadge_count })</hk:a></c:if>
					<div>
					<c:if test="${!me && !areFriend && !blocked}">
						<div><hk:form method="get" action="/follow/op/op_add.do">
							<hk:hide name="userId" value="${userId}"/>
							<hk:submit value="关注"/>
							<hk:a href="/op/award_selequ.do?userId=${userId}">使用道具</hk:a>
						</hk:form></div>
					</c:if>
					<c:if test="${!me && areFriend}">
						<div><hk:form method="get" action="/follow/op/op_del.do">
							<hk:hide name="userId" value="${userId}"/>
							<hk:submit value="取消关注"/>
							<hk:a href="/op/award_selequ.do?userId=${userId}">使用道具</hk:a>
						</hk:form></div>
					</c:if>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	</td>
	</tr>
	</tbody></table>
	</div>
	<div class="hang">
		<hk:a clazz="nn" href="/home_checkincmp.do?userId=${userId}">踪迹</hk:a> |
		<hk:a href="/follow/follow.do?userId=${userId}">关注</hk:a> |
		<hk:a href="/laba/userlabalist.do?userId=${userId}">喇叭</hk:a>
	</div>
	<c:if test="${fn:length(checkinloglist)==0}">
		<div class="hang">目前还没有任何踪迹</div>
	</c:if>
	<c:forEach var="log" items="${checkinloglist}" varStatus="idx">
		<c:if test="${idx.index%2==0}"><c:set var="clazz_var" value="odd" /></c:if>
		<c:if test="${idx.index%2!=0}"><c:set var="clazz_var" value="even" /></c:if>
		<div class="hang ${clazz_var }">
			<hk:a clazz="rowline" href="/e/cmp.do?companyId=${log.companyId}">
			<div class="ff">${log.company.name }<br/>
			<c:set var="createTime" scope="request" value="${log.createTime}"/>
			<span class="nn s"><%=JspDataUtil.outPutWeekAndTime(request) %></span>
			</div>
			<div class="fr nn">&gt;</div>
			<div class="clr"></div>
			</hk:a>
		</div>
	</c:forEach>
	<c:if test="${more_log}">
	<div class="hang"><hk:a href="/home_checkincmp.do?userId=${userId}&page=2"><hk:data key="view2.more"/></hk:a></div>
	</c:if>
	<jsp:include page="../inc/foot.jsp"></jsp:include>
</hk:wap>